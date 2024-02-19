package com.capstone.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capstone.producer.kafka.KafkaProducer;

@Service
public class TfrHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TfrHandler.class);
    /**
     * Keeps track of all TFRs Injected
     */
    private static Map<String, String[]> receivedNotams;

    public TfrHandler() {
        receivedNotams = new ConcurrentHashMap<>();
    }

    /**
     * Facilitates the creation and tracking of TFRs
     *
     * @param tfrNotam Receives the full NOTAM to start parsing
     * @return The message that was sent through Kafka, or part was added to the list for parsing
     * later when all parts make it in
     * 
     * @throws InterruptedException Sending a message using Kafka can trigger an InterruptedException
     */
    public String handleTfrAddition(String tfrNotam) throws InterruptedException {
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
            //test if array is filled out
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

    private static boolean pointRadiusParse(String notamNumber, String message) throws InterruptedException{
        Pattern pattern = Pattern.compile("(WI\\s*AN\\s*AREA\\s*DEFINED\\s*AS\\s*(\\d*.?\\d*)NM\\s*RADIUS\\s*OF\\s*(\\d+[NS]\\d+[EW])(.*?)EFFECTIVE\\s*(\\d{10})\\s*UTC.*?UNTIL\\s*(\\d{10})?)");
        Matcher matcher = pattern.matcher(message);
        boolean successfulMatching = false;
        while(matcher.find()){ //Allows for multiple finds.
            LOGGER.debug("String matched Radius test: {}", matcher.group(0));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("notamNumber", notamNumber);
            jsonObject.put("type", "RADIUS");
            jsonObject.put("latlong", matcher.group(3));
            jsonObject.put("radius", matcher.group(2));
            jsonObject.put("startTime", matcher.group(5));
            if(matcher.group(6) != null) {
                jsonObject.put("endTime", matcher.group(6));
            } else {
                jsonObject.put("endTime", "PERM");
            }

            KafkaProducer.runProducer(jsonObject.toString(), "TFRData");
            successfulMatching = true;
        }
        return successfulMatching; //doesn't appear to be a straight circle
    }

    private static boolean boundaryParse(String notamNumber, String message) throws InterruptedException{
        Pattern boundaryPattern = Pattern.compile("WI\\s*AN\\s*AREA\\s*DEFINED\\s*AS\\s*\\d+[NS]\\d+[EW].*?TO.*?ORIGIN.*?EFFECTIVE\\s*(\\d{10}).*?UNTIL\\s*(\\d{10})?");
        Pattern latlongPattern = Pattern.compile("\\d+[NS]\\d+[EW]");
        Matcher boundaryMatch = boundaryPattern.matcher(message);
        boolean successfulMatching = false;
        while(boundaryMatch.find()) {
            Matcher latlongMatch = latlongPattern.matcher(boundaryMatch.group(0));
            List<String> latlong = new ArrayList<String>();
            while(latlongMatch.find()){
                latlong.add(latlongMatch.group(0));
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("notamNumber", notamNumber);
            jsonObject.put("type", "BOUNDARY");
            jsonObject.put("latlong", latlong);
            jsonObject.put("startTime", boundaryMatch.group(1));
            if(boundaryMatch.group(2) != null) {
                jsonObject.put("endTime", boundaryMatch.group(2));
            } else {
                jsonObject.put("endTime", "PERM");
            }

            KafkaProducer.runProducer(jsonObject.toString(), "TFRData");

            successfulMatching = true;
        }
        return successfulMatching; //doesn't appear to be a boundary
    }
    
}
