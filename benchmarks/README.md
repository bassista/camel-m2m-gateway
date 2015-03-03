# Camel M2M gateway - benchmarks

This page summarizes some benchmarks of the Camel executed on the small computing platforms.

## Sending MQTT messages to the external ActiveMQ broker

In [this](https://github.com/hekonsek/camel-m2m-gateway/tree/master/benchmarks/mqtt-forward) test we generate
test events using internal Camel timer and for each event we send a message to the external ActiveMQ MQTT broker. We
use in-memory [SEDA](http://camel.apache.org/seda.html) queue to decouple events collection from the MQTT sending
process.

TL;DR; 

### Raspberry Pi 2 results

TODO

### Raspberry Pi B+ results

TODO