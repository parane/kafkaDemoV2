# KAFKA DEMO
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

This is simple Kafka Consumer and Producer Spring boot app.

  - produce the message to the topic in rest end point
  - Listen those message as consumer 

#### Set up kafka

1. choose operating system. poc purpose i choose window.
2. Installing java 
3. Installing zookeeper
- Apache Kafka uses Zookeeper to store metadata about the Kafka cluster, as well as
consumer client details
![zookeeper](https://i.imgur.com/X6p2II3.png)
 
- plz follow for basic installation : https://dzone.com/articles/running-apache-kafka-on-windows-os

- basic config param (plz refer zoo.cfg file)
    ```
    tickTime=2000
    dataDir=/var/lib/zookeeper
    clientPort=2181
    initLimit=20
    syncLimit=5
    server.1=zoo1.example.com:2888:3888
    server.2=zoo2.example.com:2888:3888
    server.3=zoo3.example.com:2888:3888
    ```
  In this configuration, 
    **initLimit** (initial sync phase) is the amount of time to allow followers to connect with a leader. 
    
    **syncLimit** (how long times between req and ack) value limits how out-of-sync followers can be with
    the leader. 
    
    Both values are a number of **tickTime** units, which makes the initLimit
    20 * 2000 ms, or 40 seconds. 
    
    The configuration also lists each server in the **ensemble**. which means Due to the algorithm used, it is recommended that ensembles contain an odd number of servers (e.g., 3, 5, etc.) as a majority
    of ensemble members (a quorum) must be working in order for Zookeeper to  respond to requests.

  
- running zookeeper 
     ```
    zkserver
     ```
     
     ![zookeeper1](https://i.imgur.com/zAGUZ7Z.png)
     
4. Installing a Kafka Broker

- plz follow for basic installation : https://dzone.com/articles/running-apache-kafka-on-windows-os

    ```
    .\bin\windows\kafka-server-start.bat .\config\server.properties
    ```
    
     Kafka broker up :
     
     ![kafkaServerUp](https://i.imgur.com/s2WXZze.png)
     
     - basic config param (plz refer server.property file)
          ```
         broker.id=0
         zookeeper.connect=localhost:2181
         log.dirs=C:\\research\\kafka_2.11-2.0.0\\kafka-logs
         num.io.threads=8
         num.recovery.threads.per.data.dir=1
         log.retention.hours=168
         log.segment.bytes=1073741824
         log.retention.check.interval.ms=300000
          ```
          
         **broker.id** The most important thing is that the integer must be unique within a single Kafka cluster.
         
         **port** The example configuration file starts Kafka with a listener on TCP port 9092.Keep in mind that if a port 
         lower than 1024 is chosen, Kafka must be started as root. Running Kafka as root is not a recommended configuration.
         
         **zookeeper.connect** location of zookeeper location for save broker meta data. 
         
         **log.dirs** This is a comma-separated list of paths onthe local system. If more than 
         one path is specified, the broker will store partitions on them in a “least-used” 
         fashion with one partition’s log segments stored within the same path.
         
         **num.recovery.threads.per.data.dir**By default, only one thread per log directory is used. As these threads are only used
                                              during startup and shutdown, it is reasonable to set a larger number of threads in
                                              order to parallelize operations. Specifically, when recovering from an unclean shutdown,
                                              this can mean the difference of several hours when restarting a broker with a
                                              large number of partitions! When setting this parameter, remember that the number
                                              configured is per log directory specified with log.dirs. This means that if num.recov
                                              ery.threads.per.data.dir is set to 8, and there are 3 paths specified in log.dirs,
                                              this is a total of 24 threads.
         
         **auto.create.topics.enable** The default Kafka configuration specifies that the broker should automatically create
         a topic under the following circumstances
         
         **num.partitions** decide no of partition per topic. this will decide 
         on throughtput.
         
         **log.retention.ms**            The most common configuration for how long Kafka will retain messages is by time.
                                         The default is specified in the configuration file using the log.retention.hours
                                         parameter, and it is set to 168 hours, or one week
                                         
          **log.retention.bytes**
                                         Another way to expire messages is based on the total number of bytes of messages
                                         retained.
                                         
         **message.max.bytes**      The Kafka broker limits the maximum size of a message that can be produced
         
         
4.1  create topic 

4.2 create producer 
  ```
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test
  ```        
  ![PRODUCER](https://i.imgur.com/EabdI1V.png)   
  
4.3 create consumer
 ```
  >.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test
   ```  
![Consumer](https://i.imgur.com/4ZuWzNW.png)  

## Dive into Code:

so far, we have done initial setup for running kafka in the window machine and set up simple producer & consumer example using kafka api. now we go into set up spring boot app for producer api for sending message to consumer.

please follow this branch for next step:
[Java Producer](https://github.com/parane/kafkaDemoV2/tree/1_producer)


         
     
