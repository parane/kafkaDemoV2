package com.para.kafka.demoKafka.service.impl;

import com.para.kafka.demoKafka.service.KafkaSender;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
@Qualifier("kafkaAsyncSender")
public class KafkaAsyncSender  implements KafkaSender {
    @Override
    public void send(String message) {

    }

    @Override
    public void sendBulk() {
        Properties properties= new Properties();
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks","all");
        properties.put("bootstrap.servers","localhost:9092");
        KafkaProducer<String,String > producer= new KafkaProducer<String, String>(properties);
        ProducerRecord data = new ProducerRecord<String,String>("test","hello");

        producer.send(data,new ProducerCallback());


    }
}
