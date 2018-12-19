# KAFKA DEMO - PRODUCER Serializers
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

#### Producer Partition 
the ProducerRecord objects we created included a topic name,
key, and value. Kafka messages are key-value pairs and while it is possible to create a
ProducerRecord with just a topic and a value, with the key set to null by default,
most applications produce records with keys.

Keys serve two goals: 

1. they are additional information that gets stored with the message, 
2. **they are also used to decide which one of the topic partitions the message will be written to.**

**All messages with the same key will go to the same partition.** 

Some cases We are not mentioned the key like this :
```
 ProducerRecord data = new ProducerRecord<String,Customer>("test1",c1);
 ```

create new topic with four partition 
```
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 4 --topic test2
```

#### Implementing a custom partitioning strategy
So far, we have discussed the traits of the default partitioner, which is the one most
commonly used. However, Kafka does not limit you to just hash partitions, and
sometimes there are good reasons to partition data differently. For example, suppose
that you are a B2B vendor and your biggest customer is a company that generate same key messages
CustomPartition to decide the which partition.
