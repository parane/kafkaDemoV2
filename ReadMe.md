# KAFKA DEMO - PRODUCER Serializers
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

#### Serializers
Kafka message are send as byte array. but in our app we have handled different type of data which need to 
be converted as byte array for sending kafka broker. for that we have used key and value serializer.

In previous section, We have looked into send string message with kafka default serializer. 
Now We look into some custom (ref : CustomerSerializer.java)serializer for sending customer object to kafka.


Send Customer Object 
```
Properties properties= new Properties();
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","com.para.kafka.demoKafka.util.CustomerSerializer");
       properties.put("bootstrap.servers","localhost:9092");

        Customer c1 = new Customer(12,"Sin");
        
        KafkaProducer<String,Customer > producer= new KafkaProducer<String, Customer>(properties);
        ProducerRecord data = new ProducerRecord<String,Customer>("test",c1);
        producer.send(data);
```
so run in producer side: 
```
http://localhost:8888/kafka-web/schema/producer
```

In kafka consumer side:
![](https://i.imgur.com/SpiMErq.png)


using custom serializer, has some draw back.you can see how fragile the code is. If we ever have too many customers, for example, and need to
change customerID to Long, or if we ever decide to add a startDate field to Customer, we will have a serious issue in maintaining compatibility between old and new
messages. Debugging compatibility issues between different versions of serializers
 and deserializers is fairly challenging :(
 
so It is recommend to use exsiting serializer uch as JSON, Apache Avro, Thrift, or Protobuf.

#### Serializing Using Apache Avro

Apache Avro is a language-neutral data serialization format.
The scschema is usually described in JSON and the serialization is usually to binary files.


although serializing to JSON is also supported. Avro assumes that the schema is present when reading and
writing files, usually by embedding the schema in the files themselves.

kafka mostly used apache avoro which has many adavantages  that when the application that is writing messages witches to a new schema, the applications reading the data can continue processing
messages without requiring any change or update.

Suppose the original schema was:
```
{
"namespace": "customerManagement.avro",
"type": "record",
"name": "Customer",
"fields": [
            {"name": "id", "type": "int"},
            {"name": "name", "type": "string""},
            {"name": "faxNumber", "type": ["null", "string"], "default": "null"}
           ]
}
```
then after we changed to as 
```
{"namespace": "customerManagement.avro",
"type": "record",
"name": "Customer",
"fields": [
    {"name": "id", "type": "int"},
    {"name": "name", "type": "string"},
    {"name": "email", "type": ["null", "string"], "default": "null"}
]
}
```

Now, after upgrading to the new version, old records will contain “faxNumber” and
new records will contain “email.” In many organizations, upgrades are done slowly
and over many months. So we need to consider how preupgrade applications that still
use the fax numbers and postupgrade applications that use email will be able to handle
all the events in Kafka.