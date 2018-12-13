package com.para.kafka.demoKafka.controller;


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
    @Qualifier("kafkaSyncSender")
    KafkaSender kafkaSyncSender;

    @Autowired
    @Qualifier("kafkaAsyncSender")
    KafkaSender kafkaAsyncSender;


    @GetMapping(value = "/producer")
    public String producer(@RequestParam("message") String message) {
        kafkaSender.send(message);

        return "Message sent to the Kafka Topic  Successfully";
    }

    @GetMapping(value = "/bulk")
    public String producerBulk() {
        kafkaSender.sendBulk();

        return "Message sent to the Kafka Topic  Successfully";
    }
    @GetMapping(value = "/sync")
    public String producerSyncMsg() {
        kafkaSyncSender.sendBulk();

        return "Sync Message sent to the Kafka Topic  Successfully";
    }

    @GetMapping(value = "/async")
    public String producerAsyncMsg() {
        kafkaAsyncSender.sendBulk();

        return "Async Message sent to the Kafka Topic  Successfully";
    }
}
