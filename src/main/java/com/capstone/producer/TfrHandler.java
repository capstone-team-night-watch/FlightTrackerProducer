package com.capstone.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capstone.producer.common.bindings.TfrNotam;
import com.capstone.producer.kafka.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TfrHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TfrHandler.class);
    /**
     * Keeps track of all TFRs Injected
     */
    private static Map<String, String[]> receivedNotams = new ConcurrentHashMap<>();

    /**
     * Facilitates the creation and tracking of TFRs
     *
     * @param tfrNotam Receives the full NOTAM to start parsing
     * @return The message that was sent through Kafka, or part was added to the list for parsing
     * later when all parts make it in
     * 
     * @throws InterruptedException Sending a message using Kafka can trigger an InterruptedException
     * @throws JsonProcessingException 
     */
    public String handleTfrAddition(String tfrNotam) throws InterruptedException, JsonProcessingException {
        LOGGER.debug("Received TFR: {}", tfrNotam);
        String notamNumber = extractNotamNumber(tfrNotam);
        if (notamNumber == null){
            return "Unable to parse NOTAM";
        }
        //True means all parts have been received.
        //False means we are waiting on other parts
        if(addToHashMap(notamNumber, tfrNotam)) {
            //combine all parts of the notam into one for easier TFR parsing.
            String fullMessage = combineNotamMessages(notamNumber);
            
            if(pointRadiusParse(notamNumber, fullMessage)){
                return "Point Radius Parsed";
            }
            if(boundaryParse(notamNumber, fullMessage)){
                return "Boundary Parsed";
            }
            return "Unable to parse :(";
        }
        //No further processing needed
        return "Received Notam: " + notamNumber + " waiting on further parts";
    }

    private static String extractNotamNumber(String notam) {
        Pattern pattern = Pattern.compile("\\b\\d/\\d{4}\\b");
        Matcher matcher = pattern.matcher(notam);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private static boolean addToHashMap(String notamNumber, String notam){
        String[] arryBuilder = null;
        //check if notam currently exists in map
        if (receivedNotams.containsKey(notamNumber)){
            arryBuilder = receivedNotams.get(notamNumber);
        }

        Pattern pattern = Pattern.compile("(PART\s)(\\d+)(\sOF\s)(\\d+)");
        Matcher matcher = pattern.matcher(notam);
        

        // if matcher has a string, we have a multipart notam.
        // if not, it is a single part notam
        if(matcher.find()){
            LOGGER.debug("Processing TFR NOTAM PART {} of {}", matcher.group(2), matcher.group(4));
            if(arryBuilder == null){
                arryBuilder = new String[Integer.parseInt(matcher.group(4))];
            }
            arryBuilder[Integer.parseInt(matcher.group(2)) - 1] = notam;
            receivedNotams.put(notamNumber, arryBuilder);
            // test if array is filled out
            for(int i = 0; i < arryBuilder.length; i++) {
                if(arryBuilder[i] == null)
                    return false;
            }
            return true;
        }
        // single message NOTAM
        arryBuilder = new String[1];
        arryBuilder[0] = notam;
        receivedNotams.put(notamNumber, arryBuilder );
        return true;
    }

    private static String combineNotamMessages(String notamNumber) {
        String[] allMessages = receivedNotams.get(notamNumber);
        String fullMessage = "";
        Pattern pattern = Pattern.compile("(FDC\\s*\\d/\\d{4}\\s*.*..TEMPORARY\\s*(FLIGHT)?\\s*(RESTRICTIONS.)?)(.*)(\\b\\d{10}-\\d{10})");
        String stringToParse = "";
        for(int i = 0; i < allMessages.length; i++) {
            LOGGER.debug("AllMessage of message: {}", allMessages[i]);
            stringToParse = allMessages[i].replaceAll(System.getProperty("line.separator"), " ");
            stringToParse = stringToParse.replaceAll("[\\n\\r]", " ");
            LOGGER.debug("String to parse: {}", stringToParse);
            Matcher matcher = pattern.matcher(stringToParse);
            if(matcher.find()) {
                fullMessage += matcher.group(4);
            }
        }
        return fullMessage;
    }

    private static boolean pointRadiusParse(String notamNumber, String message) throws InterruptedException, JsonProcessingException {
        Pattern pattern = Pattern.compile("(WI\\s*AN\\s*AREA\\s*DEFINED\\s*AS\\s*(\\d*.?\\d*)NM\\s*RADIUS\\s*OF\\s*(\\d+[NS]\\d+[EW])(.*?)EFFECTIVE\\s*(\\d{10})\\s*UTC.*?UNTIL\\s*(\\d{10})?)");
        Matcher matcher = pattern.matcher(message);
        ObjectMapper objectMapper = new ObjectMapper();
        boolean successfulMatching = false;
        while(matcher.find()){ //Allows for multiple finds.
            LOGGER.debug("String matched Radius test: {}", matcher.group(0));

            String endString;
            if(matcher.group(6) != null) {
                endString = matcher.group(6);
            } else {
                endString = "PERM";
            }

            List<Double> latlong = new ArrayList<>();
            Double[] arry = convertDmsToDd(matcher.group(3));
            latlong.add(arry[0]);
            latlong.add(arry[1]);

            // Nauticle miles to meters is 1:1852
            Double meters = Double.parseDouble(matcher.group(2)) * 1852;
            
            TfrNotam notamObject = new TfrNotam(notamNumber, "RADIUS", latlong, meters, List.of(0), matcher.group(5), endString);
            KafkaProducer.runProducer(objectMapper.writeValueAsString(notamObject), "TFRData");
            successfulMatching = true;
        }
        return successfulMatching; //doesn't appear to be a straight circle
    }

    private static boolean boundaryParse(String notamNumber, String message) throws InterruptedException, JsonProcessingException{
        Pattern boundaryPattern = Pattern.compile("WI\\s*AN\\s*AREA\\s*DEFINED\\s*AS\\s*\\d+[NS]\\d+[EW].*?TO.*?ORIGIN\\s(\\d)*FT\\s.*?EFFECTIVE\\s*(\\d{10}).*?UNTIL\\s*(\\d{10})?");
        Pattern latlongPattern = Pattern.compile("\\d+[NS]\\d+[EW]");
        Pattern altitudePattern = Pattern.compile("ORIGIN\\s(\\d)*FT\\s.*?MSL.*?(\\d)*FT");
        Matcher boundaryMatch = boundaryPattern.matcher(message);
        ObjectMapper objectMapper = new ObjectMapper();
        boolean successfulMatching = false;
        while(boundaryMatch.find()) {
            Matcher latlongMatch = latlongPattern.matcher(boundaryMatch.group(0));
            Matcher altitudeMatch = altitudePattern.matcher(boundaryMatch.group(0));

            List<Double> latlong = new ArrayList<>();
            while(latlongMatch.find()){
                Double[] arry = convertDmsToDd(latlongMatch.group(0));
                latlong.add(arry[0]);
                latlong.add(arry[1]);
            }

            List<Integer> altitudes = new ArrayList<>();
            while(altitudeMatch.find()) {
                Integer[] alt = convertAltitudeOrigin(altitudeMatch.group(0));
                altitudes.add(alt[0]);
                altitudes.add(alt[1]);
            }


            String endString;
            if(boundaryMatch.group(2) != null) {
                endString = boundaryMatch.group(2);
            } else {
                endString = "PERM";
            }

            TfrNotam notamObject = new TfrNotam(notamNumber, "BOUNDARY", latlong, 0, altitudes, boundaryMatch.group(1), endString);
            KafkaProducer.runProducer(objectMapper.writeValueAsString(notamObject), "TFRData");

            successfulMatching = true;
        }
        return successfulMatching; //doesn't appear to be a boundary
    }

    //Converts Degrees minutes seconds into Decimal Degrees
    //Expects input of XXXXXXN/SXXXXXXXE/W
    private static Double[] convertDmsToDd(String dms){
        Pattern latlongPattern = Pattern.compile("(\\d{2})(\\d{2})(\\d{2})([NS])(\\d{3})(\\d{2})(\\d{2})([EW])");
        Matcher matcher = latlongPattern.matcher(dms);

        if (matcher.find()){
            //convert latitude
            Double latitude = Double.parseDouble(matcher.group(1));
            latitude += Double.parseDouble(matcher.group(2)) / 60;
            latitude += Double.parseDouble(matcher.group(3)) / 3600;
            if (matcher.group(4).equals("S"))
                latitude *= -1;

            //convert longitude
            Double longitude = Double.parseDouble(matcher.group(5));
            longitude += Double.parseDouble(matcher.group(6)) / 60;
            longitude += Double.parseDouble(matcher.group(7)) / 3600;
            if (matcher.group(8).equals("W"))
                longitude *= -1;

            return new Double[] {longitude, latitude};
        }
        
        return null;
    }

    /**
     * Converts a string message that contains the altitude into an Integer[]
     * @param msg altitude string containing the height
     * @return Integer[] containing the values for heights
     */
    private static Integer[] convertAltitudeOrigin(String msg) {
        Pattern originPattern = Pattern.compile("(\\d)*FT\\sMSL.*?(\\d)*FT");
        Matcher matcher = originPattern.matcher(msg);

        if (matcher.find()) {
            String temp = matcher.group(0);
            String[] altitudes = matcher.group(0).split(" ");

            int originHeight = Integer.parseInt(altitudes[0].substring(0, altitudes[0].length() - 2));
            int mslHeight = Integer.parseInt(altitudes[1].substring(3, altitudes[1].length() - 2));

            return new Integer[] { originHeight, mslHeight };
        }
        return null;
    }
    
}
