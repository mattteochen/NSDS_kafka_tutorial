package it.polimi.nsds.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Collections;

public class PopularTopicConsumer {
    private static final String topic = "inputTopic";
    private static final String serverAddr = "localhost:9092";

    public static void main(String[] args) {
        String groupId = args[0];

        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddr);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        // TODO: complete the implementation by adding code here
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, String.valueOf(true));
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, String.valueOf(15000));

        //The auto offset reset config is by default "latest"
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());

        HashMap<String, Integer> popularityMap = new HashMap<>();
        KafkaConsumer<String, Integer> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        while (true) {
            final ConsumerRecords<String, Integer> records = consumer.poll(Duration.of(5, ChronoUnit.MINUTES));
                for (final ConsumerRecord<String, Integer> record : records) {
                  String key = record.key();
                  popularityMap.put(key, popularityMap.getOrDefault(key, 0)+1);
                  Integer max = 0;
                  for (Integer value : popularityMap.values()) {
                    if (value  > max) {
                      max = value;
                    }
                  }
                  System.out.print("Most popular keys:\t");
                  String winnerKeys = "";
                  for (Map.Entry<String, Integer> entry : popularityMap.entrySet()) {
                    if (entry.getValue() >= max) {
                      winnerKeys += entry.getKey() + "\t";
                    } 
                  }
                  System.out.print(winnerKeys + "\tpopularity: " + max + "\n");
                }
            }
        }
}
