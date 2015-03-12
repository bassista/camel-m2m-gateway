# Camelm2m - Apache Camel-based M2M gateway

This project summarizes the R&D activities around the process of adopting the Apache Camel as the *Internet Of Things* M2M
gateway. By the *gateway* we understand a field device with the moderate processing power (such as
[Raspberry Pi](http://www.raspberrypi.org) or [BeagleBone Black](http://beagleboard.org/BLACK)) responsible for the
routing of the messages between the IoT edge devices (sensors, drones, cars, etc) and the data center.

## Who's behind the project

Currently two IoT enthusiasts - [Henryk Konsek](https://twitter.com/hekonsek) (from [Red Hat](http://redhat.com)) and
[Arek Jurasz](https://twitter.com/arekjurasz) (from [Coderion](http://coderion.pl)). Wanna join us? We love
contributors!

## Benchmarks

We perform and maintain the results of Camel benchmarks executed against the various computing platforms. More details
can be found [here](https://github.com/hekonsek/camel-m2m-gateway/tree/master/benchmarks/README.md).

## Eclipse Kura and Camel

If you would like to run Camel together with [Eclipse Kura](https://eclipse.org/kura), check out the
[Apache Camel Kura component](http://camel.apache.org/kura). It is a really easy option to deploy Camel routes into the
Kura container.
