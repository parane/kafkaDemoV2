# KAFKA DEMO - CONSUMER CONFIG
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)
** No Code Sample**
#### Consumers config
properties—just the mandatory bootstrap.servers, group.id,
key.deserializer, and value.deserializer.

**fetch.min.bytes**
This property allows a consumer to specify the minimum amount of data that it
wants to receive from the broker when fetching records. If a broker receives a request
for records from a consumer but the new records amount to fewer bytes than
min.fetch.bytes, the broker will wait until more messages are available before sending
the records back to the consumer.

**fetch.max.wait.ms**
By setting fetch.min.bytes, you tell Kafka to wait until it has enough data to send
before responding to the consumer. fetch.max.wait.ms lets you control how long to
wait.

**max.partition.fetch.bytes**
This property controls the maximum number of bytes the server will return per partition.
The default is 1 MB, which means that when KafkaConsumer.poll() returns
ConsumerRecords, the record object will use at most max.partition.fetch.bytes
per partition assigned to the consumer.

**partition.assignment.strategy**
We learned that partitions are assigned to consumers in a consumer group. A
PartitionAssignor is a class that, given consumers and topics they subscribed to,
decides which partitions will be assigned to which consumer
[link](https://medium.com/@anyili0928/what-i-have-learned-from-kafka-partition-assignment-strategy-799fdf15d3ab)

##### Range
Assigns to each consumer a consecutive subset of partitions from each topic it
subscribes to.
![Range](https://i.imgur.com/MJgRTG5.png)

Because each topic has an uneven number of partitions and the assignment
is done for each topic independently, the first consumer ends up with more
partitions than the second. This happens whenever Range assignment is used and
the number of consumers does not divide the number of partitions in each topic
neatly.


#####  RoundRobin

Takes all the partitions from all subscribed topics and assigns them to consumers
sequentially,

![](https://i.imgur.com/09oepZ2.png)

RoundRobin assignment will end up with all consumers having
the same number of partitions (or at most 1 partition difference).

The partition.assignment.strategy allows you to choose a partition-assignment
strategy. The default is org.apache.kafka.clients.consumer.RangeAssignor,
which implements the Range strategy described above. You can replace it with
org.apache.kafka.clients.consumer.RoundRobinAssignor.

**client.id**
This can be any string, and will be used by the brokers to identify messages sent from
the client. It is used in logging and metrics, and for quotas.