# Camelm2m reference documentation (version 0.0.0)

Camelm2m stands for the *Apache Camel-based M2M gateway*. This project summarizes the R&D activities around the process
of adopting the Apache Camel as the *Internet Of Things* M2M gateway. By the *gateway* we understand a field device with
the moderate processing power (such as [Raspberry Pi](http://www.raspberrypi.org) or
[BeagleBone Black](http://beagleboard.org/BLACK)) responsible for the routing of the messages between the IoT edge
devices (sensors, drones, cars, etc) and the data center.

## Field gateway

This section summarizes the features available for the field gateways based on the Camelm2m.

### MQTT connector

Camelm2m provides reference quickstart project that can be used to easily create a field gateway applications communicating
with the MQTT brokers. In order to create
[gateway application sending messages to the MQTT broker](https://github.com/hekonsek/camel-m2m-gateway/tree/master/quickstarts/gateway-mqtt-producer)
, clone the following Git repository:

    git clone git@github.com:hekonsek/camel-m2m-gateway.git

After you clone the `camel-m2m-gateway` project, just copy the `quickstarts/gateway-mqtt-producer` directory:

    cp -r camel-m2m-gateway/quickstarts/gateway-mqtt-producer ~/gateway-mqtt-producer

Now navigate to the copied directory and build the project using Maven...

    cd ~/gateway-mqtt-producer
    mvn install

Now copy the fat jar you just created to the Raspberry Pi device of your choice using secure SFTP protocol...

    sftp target/camelm2m-quickstarts-gateway-mqtt-producer-0.0.0.jar pi@my-rpi-device

Then log into the device using SSH and execute the gateway application as a fat jar. Don't forget to set the MQTT
broker connection URL using the `broker.url` property...

    ssh pi@my-rpi-device
    ...
    java -Dbroker.url=tcp://mqtt-broker.example.com:1883 -jar target/camelm2m-quickstarts-gateway-mqtt-producer-0.0.0.jar
       _____                     _   __  __ ___  __  _
      / ____|                   | ||  \/  |__ \|  \/  |
     | |     __ _ _ __ ___   ___| || \  / |  ) | \  / |
     | |    / _` | '_ ` _ \ / _ \ || |\/| | / /| |\/| |
     | |___| (_| | | | | | |  __/ || |  | |/ /_| |  | |
      \_____\__,_|_| |_| |_|\___|_||_|  |_|____|_|  |_|
    2015-03-17 22:32:57.544  INFO 10662 --- [           main] o.apache.camel.spring.boot.FatJarRouter  : Starting FatJarRouter v2.15.0 on henryberg with PID 10662
    ...
    2015-03-17 22:33:02.350  INFO 10662 --- [           main] o.a.camel.spring.SpringCamelContext      : Total 2 routes, of which 2 is started.
    2015-03-17 22:33:02.351  INFO 10662 --- [           main] o.a.camel.spring.SpringCamelContext      : Apache Camel 2.15.0 (CamelContext: camel-1) started in 0.423 seconds
    2015-03-17 22:33:02.396  INFO 10662 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
    2015-03-17 22:33:02.397  INFO 10662 --- [           main] o.apache.camel.spring.boot.FatJarRouter  : Started FatJarRouter in 5.224 seconds (JVM running for 5.727)

That's it! Starting from now on, your gateway application will be trigger timer every second and send the message to
the MQTT broker. If you would like to change the Camel routing rules in your project, open the
`camelm2m.quickstarts.mqttproducer.GatewayMqttProducer.groovy` file and edit the `GatewayMqttProducer#configure()` method.