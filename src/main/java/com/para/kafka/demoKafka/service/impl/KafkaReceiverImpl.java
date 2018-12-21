package com.para.kafka.demoKafka.service.impl;

import com.para.kafka.demoKafka.service.KafkaReceiver;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Properties;

@Service
public class KafkaReceiverImpl implements KafkaReceiver {

    @KafkaListener(topics = "test",groupId = "groupIdConfig")
    public void consume(String message){

        System.out.println("Msg :"+message);
    }

    @Override
    public void receive() {
        Properties properties= new Properties();
        properties.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id","TestG1");
        properties.put("bootstrap.servers","localhost:9092");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String,
                String>(properties);

        /*
        Once we create a consumer, the next step is to subscribe to one or more topics. The
        subcribe() method takes a list of topics as a parameter, so it’s pretty simple to use:
         */
        consumer.subscribe(Collections.singletonList("test"));

       /* At the heart of the consumer API is a simple loop for polling the server for more data.
        Once the consumer subscribes to topics, the poll loop handles all details of coordination,
        partition rebalances, heartbeats, and data fetching, leaving the developer with a
        clean API that simply returns available data from the assigned partitions.*/
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records)
                {
                    System.out.println("***************************************");
                    System.out.println(record.topic());
                    System.out.println(record.value());
                    System.out.println("***************************************");
                }
            }
        } finally {
            consumer.close();
        }
    }
}
