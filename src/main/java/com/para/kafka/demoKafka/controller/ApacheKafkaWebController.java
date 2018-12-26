package com.para.kafka.demoKafka.controller;


import com.para.kafka.demoKafka.service.KafkaReceiver;
import com.para.kafka.demoKafka.service.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka-web/")
public class ApacheKafkaWebController {
    @Autowired
    @Qualifier("kafkaSender")
    KafkaSender kafkaSender;

    @Autowired
    @Qualifier("kafkaSyncOffsetReceiverImpl")
    KafkaReceiver kafkaSyncOffsetReceiverImpl;


    @GetMapping(value = "/send/producer")
    public String producer() {
        kafkaSender.sendBulk();

        return "Message sent to the Kafka Topic  Successfully";
    }

    @GetMapping(value = "/receive/consumer")
    public String consumer() {
        kafkaSyncOffsetReceiverImpl.receive();

        return "Terminated ";
    }
  }
