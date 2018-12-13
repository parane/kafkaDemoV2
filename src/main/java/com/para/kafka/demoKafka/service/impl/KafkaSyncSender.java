package com.para.kafka.demoKafka.service.impl;

import com.para.kafka.demoKafka.service.KafkaSender;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
@Qualifier("kafkaSyncSender")
public class KafkaSyncSender  implements KafkaSender {
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
        try {
            //Here, we are using Future.get() to wait for a reply from Kafka.
            RecordMetadata recordMeta = (RecordMetadata) producer.send(data).get();
            System.out.println("Message Offset : "+recordMeta.offset());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        producer.close();
    }
}
