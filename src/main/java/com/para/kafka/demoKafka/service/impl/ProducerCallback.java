package com.para.kafka.demoKafka.service.impl;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        System.out.println("Async recordmeta data :"+recordMetadata.offset());
    }
}
