# Camel M2M gateway - benchmarks

This page summarizes some benchmarks of the Camel executed on the small computing platforms.

## Sending MQTT messages to the external ActiveMQ broker

In [this](https://github.com/hekonsek/camel-m2m-gateway/tree/master/benchmarks/mqtt-forward) benchmark we generate
test events using internal Camel timer and for each event we send a message to the external ActiveMQ MQTT broker. We
use in-memory [SEDA](http://camel.apache.org/seda.html) queue to decouple events collection from the MQTT sending
process. We also use [Camel Paho component](http://camel.apache.org/paho) as the MQTT broker.

The broker itself doesn't perform any action against the messages it receives. There is no subscriber to the MQTT topic. We use dockerized ActiveMQ 5.11.

### Raspberry Pi 2 results

TL;DR; You can send almost 600 small messages per second from Raspbberry Pi 2 gateway to the MQTT server.

The following benchmarks were executed on the [Raspberry Pi 2](http://www.raspberrypi.org/products/raspberry-pi-2-model-b).
Raspberry Pi 2 is really fast! As for such small and cheap device, the performance of the unit is really
impressive.

If you plan to run the [Paho](https://eclipse.org/paho) MQTT client on the RPi 2 remember to:

- enqueue messages in the internal in-memory queue and use at least 15 concurrent threads to process these messages (as Paho IO
operations become a bottleneck otherwise)
- do not let sensors to put too many messages into the queue, otherwise the overall performance of the gateway is decreased
 significantly. Consider using [Camel throttler](http://camel.apache.org/throttler.html) to limit the number of the
 messages sent to the queue.

#### Benchmark 1: 3 consumers sending messages to the MQTT broker

In this particular benchmarks we used 3 concurrent consumers, reading messages from the in-memory
[SEDA](http://camel.apache.org/seda.html) queue. Route performs pretty well (up to ~315 messages per second) until
[Paho](https://eclipse.org/paho/) client got overwhelmed with the messages produced by the timer. When the number of
the messages to be processed is too large, the performance of the gateway started to decrease. The interesting point here
is that many messages are produced, while not so many are consumed - we should consider increasing the number of the
concurrent consumers to address this (see next benchmark).

| Test duration in seconds | Messages created (total)    | Messages consumed (total)    | Messages created per second | Messages consumed per second | Current queue size | JVM threads count |
|:------------------------:|:---------------------------:|:----------------------------:|:---------------------------:|:----------------------------:|:------------------:|:-----------------:|
| 30	| 7919	    | 3471	| 263	| 115	| 4448	| 52|
| 60	| 29163	    | 13574	| 486	| 226	| 15589	| 52|
| 90	| 51039	    | 24262	| 567	| 269	| 26777	| 52|
| 120	| 72750	    | 34785	| 606	| 289	| 37965	| 52|
| 150	| 96066	    | 46550	| 640	| 310	| 49516	| 52|
| 180	| 116954	| 57078	| 649	| 317	| 59876	| 52|
| 211	| 136445	| 66721	| 646	| 316	| 69724	| 52|
| 241	| 144859	| 70852	| 601	| 293	| 74007	| 52|
| 275	| 150036	| 73085	| 545	| 265	| 76951	| 52|
| 307	| 153202	| 74503	| 499	| 242	| 78699	| 52|
| 339	| 156333	| 75879	| 461	| 223	| 80454	| 52|
| 372	| 159560	| 77230	| 428	| 207	| 82330	| 52|
| 404	| 162058	| 78157	| 401	| 193	| 83901	| 52|
| 438	| 164926	| 79512	| 376	| 181	| 85414	| 52|
| 468	| 167943	| 80890	| 358	| 172	| 87053	| 52|
| 498	| 170478	| 81996	| 342	| 164	| 88482	| 52|
| 529	| 173213	| 83210	| 327	| 157	| 90003	| 52|
| 560	| 175940	| 84420	| 314	| 150	| 91520	| 52|
| 591	| 178629	| 85666	| 302	| 144	| 92963	| 52|
| 623	| 181342	| 86839	| 291	| 139	| 94503	| 52|

#### Benchmark 2: 15 consumers sending messages to the MQTT broker

In this particular benchmarks we used 15 concurrent consumers, reading messages from the in-memory
[SEDA](http://camel.apache.org/seda.html) queue. Route performs really well (up to ~580 messages per second) until
[Paho](https://eclipse.org/paho/) client got overwhelmed with the messages produced by the timer. When the number of
the messages to be processed is too big, the performance of the gateway started to decrease.

| Test duration in seconds | Messages created (total) | Messages consumed (total) | Messages created per second | Messages consumed per second | Current queue size | JVM threads count |
|:------------------------:|:------------------------:|:-------------------------:|:---------------------------:|:----------------------------:|:------------------:|:-----------------:|
| 30    | 7652      | 7637      | 255	                      | 254	                         | 15	              | 49           |
| 60    | 27924     | 27917     | 465| 	465| 	7| 	49|
| 90	| 48284	    | 48273	    | 536	| 536	| 11	| 49|
| 120	| 69787	    | 69775	    | 581	| 581	| 12	| 49|
| 151	| 87684	    | 87666	    | 580	| 580	| 18	| 49|
| 181	| 90386	    | 89846	    | 499	| 496	| 540	| 49|
| 212	| 92077	    | 91655	    | 434	| 432	| 422	| 49|
| 244	| 94055	    | 93167	    | 385	| 381	| 888	| 49|
| 274	| 95943	    | 94579	    | 350	| 345	| 1364	| 49|
| 306	| 97472	    | 96117	    | 318	| 314	| 1355	| 49|
| 337	| 99419	    | 97652	    | 295	| 289	| 1767	| 49|
| 368	| 101495	| 99150     | 275	| 269	| 2345	| 49|
| 398	| 103118	| 100512	| 259	| 252	| 2606	| 49|
| 431	| 104925	| 101080	| 243	| 234	| 3845	| 49|
| 461	| 107949	| 101097	| 234	| 219	| 6852	| 49|
| 492	| 111120	| 101107	| 225	| 205	| 10013	| 49|
| 524	| 114240	| 101117	| 218	| 192	| 13123	| 49|
| 557	| 117316	| 101137	| 210	| 181	| 16179	| 49|
| 590	| 119842	| 101154	| 203	| 171	| 18688	| 49|
| 621	| 121021	| 101157	| 194	| 162	| 19864	| 49|
