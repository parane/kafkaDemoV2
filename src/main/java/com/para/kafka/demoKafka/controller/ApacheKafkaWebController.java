package com.para.kafka.demoKafka.controller;


import com.para.kafka.demoKafka.service.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka-web/")
public class ApacheKafkaWebController {
    @Autowired
    KafkaSender kafkaSender;

    @GetMapping(value = "/producer")
    public String producer(@RequestParam("message") String message) {
        kafkaSender.send(message);

        return "Message sent to the Kafka Topic java_in_use_topic Successfully";
    }

    @GetMapping(value = "/bulk")
    public String producerBulk() {
        kafkaSender.sendBulk();

        return "Message sent to the Kafka Topic java_in_use_topic Successfully";
    }

}
