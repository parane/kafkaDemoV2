package com.para.kafka.demoKafka.service.impl;

import com.para.kafka.demoKafka.dto.Customer;
import com.para.kafka.demoKafka.service.KafkaSender;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Properties;

@Service
@Qualifier("kafkaSender")
public class KafkaSenderImpl implements KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    String kafkaTopic = "test";

    public void send(String message) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaTopic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println(result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    @Override
    public void sendBulk() {
        Properties properties= new Properties();
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","com.para.kafka.demoKafka.util.CustomerSerializer");
        properties.put("acks","0");
        properties.put("bootstrap.servers","localhost:9092");

        Customer c1 = new Customer(12,"Sin");
        KafkaProducer<String,Customer > producer= new KafkaProducer<String, Customer>(properties);
        ProducerRecord data = new ProducerRecord<String,Customer>("test",c1);
        producer.send(data);
        producer.close();
    }
}
