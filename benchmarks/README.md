# Camel M2M gateway - benchmarks

This page summarizes some benchmarks of the Camel executed on the small computing platforms.

## Sending MQTT messages to the external ActiveMQ broker

In [this](https://github.com/hekonsek/camel-m2m-gateway/tree/master/benchmarks/mqtt-forward) benchmark we generate
test events using internal Camel timer and for each event we send a message to the external ActiveMQ MQTT broker. We
use in-memory [SEDA](http://camel.apache.org/seda.html) queue to decouple events collection from the MQTT sending
process.

TL;DR;

### Raspberry Pi 2 results

"Seda + MQTT
 java -Dsensors.number=1 -Dbroker.consumers=15 -jar camel-m2m-gateway-benchmarks-mqtt-forward-0.1-SNAPSHOT.jar"

| Test duration in seconds | Messages created (total)    | Messages consumed (total)    | Messages created per second | Messages consumed per second | Current queue size | Thread count |
|:------------------------:|:---------------------------:|:----------------------------:|:---------------------------:|:----------------------------:|:------------------:|:------------:|
| 30                       | 7652                        |	7637	                    | 255	                      | 254	                         | 15	              | 49           |
| 60	                   | 27924	27917| 	465| 	465| 	7| 	49|

90	48284	48273	536	536	11	49
120	69787	69775	581	581	12	49
151	87684	87666	580	580	18	49
181	90386	89846	499	496	540	49
212	92077	91655	434	432	422	49
244	94055	93167	385	381	888	49
274	95943	94579	350	345	1364	49
306	97472	96117	318	314	1355	49
337	99419	97652	295	289	1767	49
368	101495	99150	275	269	2345	49
398	103118	100512	259	252	2606	49
431	104925	101080	243	234	3845	49
461	107949	101097	234	219	6852	49
492	111120	101107	225	205	10013	49
524	114240	101117	218	192	13123	49
557	117316	101137	210	181	16179	49
590	119842	101154	203	171	18688	49
621	121021	101157	194	162	19864	49

### Raspberry Pi B+ results

TODO