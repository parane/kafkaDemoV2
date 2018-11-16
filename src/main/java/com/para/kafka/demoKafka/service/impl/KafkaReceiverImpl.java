package com.para.kafka.demoKafka.service.impl;

import com.para.kafka.demoKafka.service.KafkaReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaReceiverImpl implements KafkaReceiver {
    @KafkaListener(topics = "test",groupId = "groupIdConfig")
    public void consume(String message){

        System.out.println(message);
    }
}
