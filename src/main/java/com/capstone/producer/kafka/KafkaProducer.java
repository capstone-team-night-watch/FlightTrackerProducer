package com.capstone.producer.kafka;

import com.capstone.producer.shared.JsonHelper;
import com.capstone.producer.shared.bindings.CircularNoFlyZone;
import com.capstone.producer.shared.bindings.FlightInformation;
import com.capstone.producer.shared.bindings.PolygonNoFlyZone;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;


/**
 * Class that handles setting up the Kafka production functionality
 */
@Service
public class KafkaProducer {
    
    private static String BOOTSTRAP_SERVER = "kafka:9092";

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private KafkaProducer() {
    }

    @Value("${kafka.host}")
    private void setBootstrap(String bootstrap) {
        BOOTSTRAP_SERVER = bootstrap;
    }

    private static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "flight-tracker-producer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new org.apache.kafka.clients.producer.KafkaProducer<>(props);
    }

    /**
     * Creates a new Producer Object that is then used to send a ProducerRecord,
     * created from the set Topic name (FlightData) and provided message, through the Kafka broker.
     *
     * @param message The provided message
     * @return A RecordMetadata Object that relates to the ProducerRecord sent through Kafka in some way
     * @throws InterruptedException Calling get() on the result of Producer.send(ProducerRecord) can trigger a InterruptedException
     */
    public static RecordMetadata runProducer(String message, String topicName) throws InterruptedException {
        final Producer<Long, String> producer = createProducer();

        try {
            LOGGER.info("Sending message to Topic");
            final ProducerRecord<Long, String> producerRecord = new ProducerRecord<>(topicName, message);
            return producer.send(producerRecord).get();
        } catch (ExecutionException exEx) {
            LOGGER.error("An ExecutionException was caught when trying to sent this message: {}. " +
                    "Rethrowing exception as a RuntimeException...", message);
            throw new RuntimeException(exEx);

        } catch (InterruptedException intEx) {
            LOGGER.error("An InterruptedException was caught when trying to sent this message: {}. Rethrowing exception...", message);
            throw intEx;
        } finally {
            LOGGER.info("Flushing and closing Producer");
            producer.close();
            producer.flush();
        }
    }

    public static void emitCircularNoFlyZone(CircularNoFlyZone circularNoFlyZone) throws InterruptedException {
        runProducer(JsonHelper.toJson(circularNoFlyZone), "CircularNoFlyZone");
    }

    public static void emitPolygonNoFlyZone(PolygonNoFlyZone polygonNoFlyZone) throws InterruptedException {
        runProducer(JsonHelper.toJson(polygonNoFlyZone), "PolygonNoFlyZone");
    }

    public static void emitFlightInformationUpdate(FlightInformation flightInformation) throws InterruptedException {
        runProducer(JsonHelper.toJson(flightInformation), "FlightLocationData");
    }
}
