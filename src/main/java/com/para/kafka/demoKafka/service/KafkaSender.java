package com.para.kafka.demoKafka.service;

public interface KafkaSender {
    public void send(String message);
    public void sendBulk();
}
