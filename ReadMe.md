# KAFKA DEMO - CONSUMER 
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

#### Consumers and Consumer Groups

Kafka consumers are typically part of a consumer group. When multiple consumers
are subscribed to a topic and belong to the same consumer group, each consumer in
the group will receive messages from a different subset of the partitions in the topic.

![](https://i.imgur.com/lDhqXyg.png)

Let’s take topic T1 with four partitions. Now suppose we created a new consumer, C1,
which is the only consumer in group G1, and use it to subscribe to topic T1. Consumer
C1 will get all messages from all four t1 partitions.

![](https://i.imgur.com/3OnCUAU.png)

If we add another consumer, C2, to group G1, each consumer will only get messages
from two partitions. Perhaps messages from partition 0 and 2 go to C1 and messages
from partitions 1 and 3 go to consumer C2.


like wise add 4 consumers in group

![](https://i.imgur.com/pc3OPo0.png)

If we add more consumers to a single group with a single topic than we have partitions,
some of the consumers will be idle and get no messages at all.

![](https://i.imgur.com/NMfTW3O.png)

The main way we scale data consumption from a Kafka topic is by adding more consumers
to a consumer group. It is common for Kafka consumers to do high-latency
operations such as write to a database or a time-consuming computation on the data.
In these cases, a single consumer can’t possibly keep up with the rate data flows into a
topic, and adding more consumers that share the load by having each consumer own
just a subset of the partitions and messages is our main method of scaling.
 
![](https://i.imgur.com/NMfTW3O.png)

 **This is a good reason to create topics with a large number of partitions** —> it allows adding more
consumers when the load increases. 

**Keep in mind that there is no point in adding
more consumers than you have partitions in a topic—some of the consumers will just
be idle.**

In the previous example, if we add a new consumer group G2 with a single consumer,
this consumer will get all the messages in topic T1 independent of what G1 is doing.
G2 can have more than a single consumer, in which case they will each get a subset of
partitions, just like we showed for G1, but G2 as a whole will still get all the messages
regardless of other consumer groups.

To summarize, you create a new consumer group for each application that needs all
the messages from one or more topics. You add consumers to an existing consumer
group to scale the reading and processing of messages from the topics, so each additional
consumer in a group will only get a subset of the messages.

#### Consumer Groups and Partition Rebalance

As we saw in the previous section, consumers in a consumer group share ownership
of the partitions in the topics they subscribe to. When we add a new consumer to the
group, it starts consuming messages from partitions previously consumed by another
consumer. The same thing happens when a consumer shuts down or crashes; it leaves
the group, and the partitions it used to consume will be consumed by one of the
remaining consumers. Reassignment of partitions to consumers also happen when
the topics the consumer group is consuming are modified

**Moving partition ownership from one consumer to another is called a rebalance.**
Rebalances are important because they provide the consumer group with high availability
and scalability.

handle rebalances little bit tricy (state changes one consumer to another)

The way consumers maintain membership in a consumer group and ownership of
the partitions assigned to them is by sending heartbeats to a Kafka broker designated
as the group coordinator

If the consumer stops sending heartbeats for long enough, its session will time out
and the group coordinator will consider it dead and trigger a rebalance. If a consumer
crashed and stopped processing messages, it will take the group coordinator a few
seconds without heartbeats to decide it is dead and trigger the rebalance. During
those seconds, no messages will be processed from the partitions owned by the dead
consumer. When closing a consumer cleanly, the consumer will notify the group
coordinator that it is leaving, and the group coordinator will trigger a rebalance
immediately, reducing the gap in processing.

Running Demo:
Producer:
```
http://localhost:8888/kafka-web/send/producer
```
Consumer: 
```
http://localhost:8888/kafka-web/receive/consumer
```
