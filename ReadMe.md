# KAFKA DEMO - PRODUCER
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

This is simple Kafka Producer Spring boot app.

  - produce the message to the topic in rest end point

#### Java Kafka Producers: Writing Messages to Kafka
you can use kafka as queue, message bus or data storage platform. In this example, Java Producer write some messages to kafka and print those messages in kafka built-in client consumer.

for running this spring boot app. follow this steps

- maven build :
    ```
    mvn clean build
    ```
- Running spring boot 
    ```
    java -jar <boot.jar> or Run DemoKafkaApplication
    ```
- send the message via rest end point to kafka consumer 
    ```
    http://localhost:8888/kafka-web/producer?message=hi
    ```

- check in kafka consumer : 

   - 'hi' message send to the topic and consumer who listen the particular topic print the message. 
![kaafkaConsumer](https://i.imgur.com/xqtzZnW.png)


#### Deeper look into the code:

######  Java Producer to Kafka broker 
There are many reasons an application might need to write messages to Kafka:
recording user activities for auditing or analysis, recording metrics, storing log messages,
recording information from smart appliances, communicating asynchronously
with other applications, buffering information before writing to a database, and much
more.

The different requirements will influence the way you use the producer API to write
messages to Kafka and the configuration you use. eg is every message critical, or can we tolerate loss of messages?
Are we OK with accidentally duplicating messages? Are there any strict latency or throughput requirements we need to support?

code level sending message as following :
 ```
        Properties properties= new Properties();
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks","all");
        properties.put("bootstrap.servers","localhost:9092");
        KafkaProducer<String,String > producer= new KafkaProducer<String, String>(properties);
        ProducerRecord data = new ProducerRecord<String,String>("test","hello");
        producer.send(data);
        producer.close();
  ```
  
  or 
  
  simply using 
   ```
  kafkaSender.send(message); 
 ```
 
It contains two part. one is kafkaTemplate and other is sending msg

###### kafkaTemplate

basically we send two things. 
1. Topic 
2. Message/Value 

optionally, we can speicify key or/And partition. If we didn’t, the partitioner will choose a partition for us, usually based 
on the ProducerRecord key. Once a partition is selected, the producer know which topic and which partition to go.
so combine those thing call it as ProducerRecord

![ProducerRecors](https://i.imgur.com/2mW6GyK.png) 

In kafkaTemplate, we need to set three mandatory properties
 ```
properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
properties.put("bootstrap.servers","localhost:9092");
 ```
 
**bootstrap.servers**

 List of host:port pairs of brokers that the producer will use to establish initial
 connection to the Kafka cluster. This list doesn’t need to include all brokers, since
 the producer will get more information after the initial connection. 
 
**key.serializer**

Name of a class that will be used to serialize the keys of the records we will produce
 to Kafka. Kafka brokers expect byte arrays as keys and values of messages.
 
**value.serializer**
 
 Name of a class that will be used to serialize the values of the records we will produce
 to Kafka.
 
 ###### kafkaTemplate.send(Topic, value)

Once we instantiate a producer, it is time to start sending messages. There are three
 primary methods of sending messages:
 
 - **Fire-and-forget**
 We send a message to the server and don’t really care if it arrives succesfully or
 not.
 ```
 try {
 producer.send(record);
 } catch (Exception e) {
 //catching up before sending exception, Those can be aSerializationException when it fails to serialize the message, a BufferExhaus
tedException or TimeoutException if the buffer is full, or an InterruptException if the sending thread was interrupted
 e.printStackTrace();
 }

 ```
 
- **Synchronous send**
 We send a message, the send() method returns a Future object, and we use get()
 to wait on the future and see if the send() was successful or not.

 ``` 
RecordMetadata recordMeta = (RecordMetadata) producer.send(data).get();
System.out.println("Message Offset : "+recordMeta.offset());
  ```
  
- **Asynchronous send**w
 We call the send() method with a callback function, which gets triggered when it
 receives a response from the Kafka broker.
  ``` 
 producer.send(data,new ProducerCallback());
   ```