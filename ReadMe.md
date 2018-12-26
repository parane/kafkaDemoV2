# KAFKA DEMO - COMMIT AND OFFSET
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)
** No Code Sample**
#### Consumers config
one of Kafka’s unique characteristics is that it does not track acknowledgments from consumers the way
many JMS queues do. Instead, it allows consumers to use Kafka to track their position (offset) in each partition.
We call the action of updating the current position in the partition a **commit**.
How does a consumer commit an offset? It produces a message to Kafka, to a special
__consumer_offsets topic, with the committed offset for each partition.
However,
if a consumer crashes or a new consumer joins the consumer group, this will
trigger a rebalance. After a rebalance, each consumer may be assigned a new set of
partitions than the one it processed before. In order to know where to pick up the
work, the consumer will read the latest committed offset of each partition and continue
from there.

If the committed offset is smaller than the offset of the last message the client processed,
the messages between the last processed offset and the committed offset will
be processed twice.

*
If the committed offset is larger than the offset of the last message the client actually
processed, all messages between the last processed offset and the committed offset
will be missed by the consumer group.

####  Automatic Commit
The easiest way to commit offsets is to allow the consumer to do it for you. If you
configure enable.auto.commit=true, then every five seconds the consumer will
commit the largest offset your client received from poll(). The five-second interval
is the default and is controlled by setting auto.commit.interval.ms. Just like everything
else in the consumer, the automatic commits are driven by the poll loop. Whenever
you poll, the consumer checks if it
Consider that, by default, automatic commits occur every five seconds. Suppose that
we are three seconds after the most recent commit and a rebalance is triggered. After
the rebalancing, all consumers will start consuming from the last offset committed. In
this case, the offset is three seconds old, so all the events that arrived in those three
seconds will be processed twice. It is possible to configure the commit interval to
commit more frequently and reduce the window in which records will be duplicated,
but it is impossible to completely eliminate them.


####  Commit Current Offset
Most developers exercise more control over the time at which offsets are committed
—both to eliminate the possibility of missing messages and to reduce the number of
messages duplicated during rebalancing. The consumer API has the option of committing
the current offset .but it may cause re reading in msg when rebalance occured. 

By setting auto.commit.offset=false

there are two way to commit offset 
1. Sync (ref code)
2. Async (ref code)

