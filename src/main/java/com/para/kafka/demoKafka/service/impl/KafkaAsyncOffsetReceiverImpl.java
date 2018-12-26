package com.para.kafka.demoKafka.service.impl;

import com.para.kafka.demoKafka.service.KafkaReceiver;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Properties;

@Service
@Qualifier("kafkaAsyncOffsetReceiverImpl")
public class KafkaAsyncOffsetReceiverImpl  implements KafkaReceiver {
    @Override
    public void receive() {
        Properties properties= new Properties();
        properties.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id","TestG1");
        properties.put("bootstrap.servers","localhost:9092");
        properties.put("auto.commit.offset","false");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String,
                String>(properties);


        consumer.subscribe(Collections.singletonList("test"));


        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records)
                {
                    System.out.println("***************************************");
                    System.out.println(record.topic());
                    System.out.println(record.offset());
                    System.out.println("***************************************");
                }
                //the asynchronous commit API. Instead of waiting for the broker to
                //respond to a commit, we just send the request and continue on
                consumer.commitAsync();

            }
        } finally {
            consumer.close();
        }
    }
}
