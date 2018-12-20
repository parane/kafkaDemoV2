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

#### Using Avro Records with Kafka

Unlike Avro files, where storing the entire schema in the data file is associated with a
fairly reasonable overhead, storing the entire schema in each record will usually more
than double the record size. However, Avro still requires the entire schema to be
present when reading the record, so we need to locate the schema elsewhere. To achieve
this, we follow a common architecture pattern and use a **Schema Registry**.


#### Schema Registry

The idea is to store all the schemas used to write data to Kafka in the registry. Then
we simply store the identifier for the schema in the record we produce to Kafka. The
consumers can then use the identifier to pull the record out of the schema

![](https://i.imgur.com/CEAM6qU.png)

!! Schema Registry server (official) is not supported in window
if you want to run plz use this batch file: https://github.com/renukaradhya/confluentplatform/tree/master/bin/windows

Running Registry server localhost:8081

![](https://i.imgur.com/CGaHTA4.png)

Running Avro Serialized message

```
http://localhost:8888/kafka-web/schema/avro/producer
```
Avro schema define in resources/avro

```
{
"namespace": "customerManagement.avro",
"type": "record",
"name": "CustomerAvro",
"fields": [
{"name": "id", "type": "int"},
{"name": "name", "type": "string"},
{"name": "email", "type": ["null", "string"], "default": "null"}
]
}
```
//generate CustomerAvro in src location
 ```
mvn generate-sources
 ```

```
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer",KafkaAvroSerializer.class.getName());
        properties.put("bootstrap.servers","localhost:9092");
        properties.setProperty("schema.registry.url", registry);


        KafkaProducer<String, customerManagement.avro.CustomerAvro> producer= new KafkaProducer<String, customerManagement.avro.CustomerAvro>(properties);
        customerManagement.avro.CustomerAvro avroCustomer = customerManagement.avro.CustomerAvro.newBuilder()
     
        ProducerRecord data = new ProducerRecord<String,customerManagement.avro.CustomerAvro>("test",avroCustomer);
        producer.send(data);
        
```

kafka-avro consumer also not supported in window

There is one option for run it in docker for verified the avro msg processed
in consumer side also.

[Next :Producer Partition](https://github.com/parane/kafkaDemoV2/tree/4_producer_partition)
