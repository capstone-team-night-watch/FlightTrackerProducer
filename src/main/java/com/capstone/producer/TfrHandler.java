package com.capstone.producer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
            return "Received all of NOTAM, processing now";
        }
        return "Received Notam: " + notamNumber + "waiting on further parts";
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

        Pattern pattern = Pattern.compile("PART (?<part>\\d) OF (?<of>\\d)");
        Matcher matcher = pattern.matcher(notam);
        LOGGER.debug("Processing TFR NOTAM PART {} of {}", matcher.group("part"), matcher.group("of"));

        // if matcher has a string, we have a multipart notam.
        // if not, it is a single part notam
        if(matcher.find()){
            if(arryBuilder == null){
                arryBuilder = new String[Integer.parseInt(matcher.group("of"))];
            }
            arryBuilder[Integer.parseInt(matcher.group("part"))] = notam;
            receivedNotams.put(notamNumber, arryBuilder);
            //test if array is filled out
            for(int i = 0; i < arryBuilder.length; i++) {
                if(arryBuilder[i] == null)
                    return false;
            }
            return true;
        }
        //single message NOTAM
        arryBuilder = new String[1];
        arryBuilder[0] = notam;
        receivedNotams.put(notamNumber, arryBuilder );
        return true;
        
    }
    
}
