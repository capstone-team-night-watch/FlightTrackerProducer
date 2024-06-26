package com.capstone.producer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capstone.producer.shared.bindings.CircularNoFlyZone;
import com.capstone.producer.shared.bindings.GeographicCoordinates2D;
import com.capstone.producer.shared.bindings.PolygonNoFlyZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capstone.producer.kafka.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Handler class for temporary flight restrictions
 */
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
     * @throws InterruptedException    Sending a message using Kafka can trigger an InterruptedException
     * @throws JsonProcessingException
     */
    public String handleTfrAddition(String tfrNotam) throws InterruptedException, JsonProcessingException {
        LOGGER.debug("Received TFR: {}", tfrNotam);
        String notamNumber = extractNotamNumber(tfrNotam);
        if (notamNumber == null) {
            return "Unable to parse NOTAM";
        }
        //True means all parts have been received.
        //False means we are waiting on other parts
        if (addToHashMap(notamNumber, tfrNotam)) {
            //combine all parts of the notam into one for easier TFR parsing.
            String fullMessage = combineNotamMessages(notamNumber);

            if (pointRadiusParse(notamNumber, fullMessage)) {
                return "Point Radius Parsed";
            }
            if (boundaryParse(notamNumber, fullMessage)) {
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

    /**
     * adds objects to the hash map
     * @param notamNumber notam being processed
     * @param notam notam text
     * @return
     */
    private static boolean addToHashMap(String notamNumber, String notam) {
        String[] arryBuilder = null;
        //check if notam currently exists in map
        if (receivedNotams.containsKey(notamNumber)) {
            arryBuilder = receivedNotams.get(notamNumber);
        }

        Pattern pattern = Pattern.compile("(PART\s)(\\d+)(\sOF\s)(\\d+)");
        Matcher matcher = pattern.matcher(notam);


        // if matcher has a string, we have a multipart notam.
        // if not, it is a single part notam
        if (matcher.find()) {
            LOGGER.debug("Processing TFR NOTAM PART {} of {}", matcher.group(2), matcher.group(4));
            if (arryBuilder == null) {
                arryBuilder = new String[Integer.parseInt(matcher.group(4))];
            }
            arryBuilder[Integer.parseInt(matcher.group(2)) - 1] = notam;
            receivedNotams.put(notamNumber, arryBuilder);
            // test if array is filled out
            for (int i = 0; i < arryBuilder.length; i++) {
                if (arryBuilder[i] == null)
                    return false;
            }
            return true;
        }
        // single message NOTAM
        arryBuilder = new String[1];
        arryBuilder[0] = notam;
        receivedNotams.put(notamNumber, arryBuilder);
        return true;
    }

    /**
     * combines notam messages from the hash map
     * @param notamNumber notam to be combined
     * @return combined text from the notam
     */
    private static String combineNotamMessages(String notamNumber) {
        String[] allMessages = receivedNotams.get(notamNumber);
        String fullMessage = "";
        Pattern pattern = Pattern.compile("(FDC\\s*\\d/\\d{4}\\s*.*..TEMPORARY\\s*(FLIGHT)?\\s*(RESTRICTIONS.)?)(.*)(\\b\\d{10}-\\d{10})");
        String stringToParse = "";
        for (int i = 0; i < allMessages.length; i++) {
            LOGGER.debug("AllMessage of message: {}", allMessages[i]);
            stringToParse = allMessages[i].replaceAll(System.getProperty("line.separator"), " ");
            stringToParse = stringToParse.replaceAll("[\\n\\r]", " ");
            LOGGER.debug("String to parse: {}", stringToParse);
            Matcher matcher = pattern.matcher(stringToParse);
            if (matcher.find()) {
                fullMessage += matcher.group(4);
            }
        }
        return fullMessage;
    }

    /**
     * Parses radius object
     * @param notamNumber notam being parsed
     * @param message full text of notam
     * @return boolean indicating succeded or failed
     * @throws InterruptedException
     * @throws JsonProcessingException
     */
    private static boolean pointRadiusParse(String notamNumber, String message) throws InterruptedException, JsonProcessingException {
        Pattern pattern = Pattern.compile("(WI\\s*AN\\s*AREA\\s*DEFINED\\s*AS\\s*(\\d*.?\\d*)NM\\s*RADIUS\\s*OF\\s*(\\d+[NS]\\d+[EW])(.*?)(\\d*)FT(.*?)EFFECTIVE\\s*(\\d{10})\\s*UTC.*?UNTIL\\s*(\\d{10}))");
        Matcher matcher = pattern.matcher(message);
        boolean successfulMatching = false;
        Integer numFound = 0;
        while (matcher.find()) { //Allows for multiple finds.
            LOGGER.debug("String matched Radius test: {}", matcher.group(0));

            Double[] latlong = convertDmsToDd(matcher.group(3));

            // Nauticle miles to meters is 1:1852
            Double meters = Double.parseDouble(matcher.group(2)) * 1852;

            Integer height = Integer.parseInt(matcher.group(5));

            var circularNoFlyZone = new CircularNoFlyZone();

            circularNoFlyZone
                    .setRadius(meters)
                    .setCenter(new GeographicCoordinates2D(latlong[0], latlong[1]))
                    .setAltitude(height)
                    .setNotamNumber(notamNumber + "-" + numFound.toString())
                    .setDescription(message)
                    .setCreatedAt(new Date());

            KafkaProducer.emitCircularNoFlyZone(circularNoFlyZone);
            numFound += 1;
            successfulMatching = true;
        }
        return successfulMatching; //doesn't appear to be a straight circle
    }

    /**
     * Boundary to parse
     * @param notamNumber notam being parsed
     * @param message text being parsed
     * @return boolean indicating success or fail
     * @throws InterruptedException
     * @throws JsonProcessingException
     */
    private static boolean boundaryParse(String notamNumber, String message) throws InterruptedException, JsonProcessingException {
        Pattern boundaryPattern = Pattern.compile("WI\\s*AN\\s*AREA\\s*DEFINED\\s*AS\\s*\\d+[NS]\\d+[EW].*?TO.*?ORIGIN\\s.*?EFFECTIVE\\s*(\\d{10}).*?UNTIL\\s*(\\d{10})?");
        Pattern latlongPattern = Pattern.compile("\\d+[NS]\\d+[EW]");
        Pattern altitudePattern = Pattern.compile("ORIGIN\\s(.*?)(\\d+)");
        Matcher boundaryMatch = boundaryPattern.matcher(message);
        
        boolean successfulMatching = false;
        while (boundaryMatch.find()) {
            if (boundaryMatch.group(0).contains("CENTERED ON")){
                continue;
            }
            Matcher latlongMatch = latlongPattern.matcher(boundaryMatch.group(0));
            Matcher altitudeMatch = altitudePattern.matcher(boundaryMatch.group(0));

            var vertices = new ArrayList<GeographicCoordinates2D>();
            while (latlongMatch.find()) {
                Double[] latlong = convertDmsToDd(latlongMatch.group(0));
                vertices.add(new GeographicCoordinates2D(latlong[0], latlong[1]));
            }
            
            var polygonNoFlyZone = new PolygonNoFlyZone();

            polygonNoFlyZone
                    .setVertices(vertices)
                    .setDescription(message)
                    .setNotamNumber(notamNumber)
                    .setCreatedAt(new Date());

            if (altitudeMatch.find()) {
                polygonNoFlyZone
                    .setAltitude(Integer.parseInt(altitudeMatch.group(2)));
            } else {
                polygonNoFlyZone
                    .setAltitude(25000);
            }


            KafkaProducer.emitPolygonNoFlyZone(polygonNoFlyZone);

            successfulMatching = true;
        }
        return successfulMatching; //doesn't appear to be a boundary
    }

    //Converts Degrees minutes seconds into Decimal Degrees
    //Expects input of XXXXXXN/SXXXXXXXE/W
    private static Double[] convertDmsToDd(String dms) {
        Pattern latlongPattern = Pattern.compile("(\\d{2})(\\d{2})(\\d{2})([NS])(\\d{3})(\\d{2})(\\d{2})([EW])");
        Matcher matcher = latlongPattern.matcher(dms);

        if (matcher.find()) {
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

            return new Double[]{latitude, longitude};
        }

        return null;
    }

}
