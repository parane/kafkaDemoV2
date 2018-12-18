package com.para.kafka.demoKafka.service.impl;


import com.para.kafka.demoKafka.service.KafkaSender;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Qualifier("kafkaAvroSerialSender")
public class KafkaAvroSerialSenderImpl  implements KafkaSender {

    @Value("${registry.url}")
    String registry;
    @Override
    public void send(String message) {

    }

    @Override
    public void sendBulk() {
        Properties properties= new Properties();
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer",KafkaAvroSerializer.class.getName());
        properties.put("bootstrap.servers","localhost:9092");
        properties.setProperty("schema.registry.url", registry);


        KafkaProducer<String, customerManagement.avro.CustomerAvro> producer= new KafkaProducer<String, customerManagement.avro.CustomerAvro>(properties);
        customerManagement.avro.CustomerAvro avroCustomer = customerManagement.avro.CustomerAvro.newBuilder()
                                                                             .setId(1)
                                                                             .setEmail("cdd@gmai")
                                                                             .setName("Parane").build();

        ProducerRecord data = new ProducerRecord<String,customerManagement.avro.CustomerAvro>("test",avroCustomer);
        producer.send(data);
        producer.close();
    }
}
