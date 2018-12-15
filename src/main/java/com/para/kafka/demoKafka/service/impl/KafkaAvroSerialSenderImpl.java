package com.para.kafka.demoKafka.service.impl;

import com.para.kafka.demoKafka.service.KafkaSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("kafkaAvroSerialSender")
public class KafkaAvroSerialSenderImpl  implements KafkaSender {
    @Override
    public void send(String message) {

    }

    @Override
    public void sendBulk() {

    }
}
