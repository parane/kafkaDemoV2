package com.para.kafka.demoKafka.service.impl;

import com.para.kafka.demoKafka.dto.Customer;
import com.para.kafka.demoKafka.service.KafkaSender;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
@Qualifier("kafkaSender")
public class KafkaSenderImpl implements KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    String kafkaTopic = "test";

    public void send(String message) {

        Properties properties= new Properties();
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","com.para.kafka.demoKafka.util.CustomerSerializer");
        properties.put("acks","0");
        properties.put("bootstrap.servers","localhost:9092");
        Customer c1 = new Customer(12,"Sin");
        KafkaProducer<String,Customer > producer= new KafkaProducer<String, Customer>(properties);
        for(int i=0;i<5;i++){
            ProducerRecord data = new ProducerRecord<String,Customer>("test3","key1",c1);
            try {
                RecordMetadata recordMeta = (RecordMetadata) producer.send(data).get();
                System.out.println(recordMeta.partition());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        producer.close();
    }

    @Override
    public void sendBulk() {
        Properties properties= new Properties();
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","com.para.kafka.demoKafka.util.CustomerSerializer");
        properties.put("acks","0");
        properties.put("bootstrap.servers","localhost:9092");
        properties.put("partitioner.class",
                "com.para.kafka.demoKafka.util.CustomPartition");
        Customer c1 = new Customer(12,"Sin");
        KafkaProducer<String,Customer > producer= new KafkaProducer<String, Customer>(properties);
        for(int i=0;i<5;i++){
            ProducerRecord data = new ProducerRecord<String,Customer>("test3","key1",c1);
            try {
                RecordMetadata recordMeta = (RecordMetadata) producer.send(data).get();
                System.out.println(recordMeta.partition());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        producer.close();
    }
}
