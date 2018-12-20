# KAFKA DEMO - PRODUCER CONFIG
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

In here,  explore some config entries in kafka producer.

As in previous section, we have seen some mandatory config entries like key.serializer, value.serializer, bootstrap.servers
here we look into some configuring param which have siginificant impact on memory use , performance and reliability msg transfer

1. **acks**
how many partion replica must receive the record before the producer can consider the write sucessful. there are 3 allowed
value:
- **ack=0** producer not wait for reply from the broker, assume the msg was sent sucessfully. dont care error.
- **ack=1** producer will receive a sucess  response from the broker the moment the leader replica received the msg.
- **ack=all** the producer will receive a success response from the broker once all
in-sync replicas received the message 

2. **buffer.memory**
This sets the amount of memory the producer will use to buffer messages waiting to
be sent to brokers.

3. **compression.type**
By default, messages are sent uncompressed. This parameter can be set to snappy,
gzip, or lz4, in which case the corresponding compression algorithms will be used to
compress the data before sending it to the brokers.

4.**batch.size**
  When multiple records are sent to the same partition, the producer will batch them
  together. This parameter controls the amount of memory in bytes (not messages!)
  that will be used for each batch.

5.**linger.ms**
  linger.ms controls the amount of time to wait for additional messages before sending
  the current batch. KafkaProducer sends a batch of messages either when the current
  batch is full or when the linger.ms limit is reached.
  
6.**max.in.flight.requests.per.connection**
  This controls how many messages the producer will send to the server without
  receiving responses.
  
7. **max.request.size**
  This setting controls the size of a produce request sent by the producer. It caps both
  the size of the largest message that can be sent and the number of messages that the
  producer can send in one request.
  
[Next Topic Producer Schema](https://github.com/parane/kafkaDemoV2/tree/3_producer_schema)
