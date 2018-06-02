# SpringBoot Apache Camel (RSS component) 
Example project of Spring Boot app with Apache Camel RSS component (Available as of Camel version 2.0).

## Apache Camel RSS component

The rss component is used for polling RSS feeds. Camel will default poll the feed every 60th seconds. 

The component currently only supports polling (consuming) feeds.

Camel-rss internally uses a patched version of ROME hosted on ServiceMix to solve some OSGi class loading issues.

### Prerequisites

You will need following to run this application:

- [Java 8+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Maven 2+](https://maven.apache.org/)

### Starting the application

You can start the application using maven:

mvn camel:run

Note: This is a console app


# See Also

- [Spring Boot website](https://projects.spring.io/spring-boot/)
- [Apache Camel GitHub](https://github.com/apache/camel)


# Author

- David Jiménez Anca : https://twitter.com/davidjimenezanc
- mailto: dvdancca@gmail.com
- Upwork profile: https://www.upwork.com/fl/davidjimenez3


