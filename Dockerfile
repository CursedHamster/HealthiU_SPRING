FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/HealthiU-0.0.1-SNAPSHOT.jar HealthiU-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/HealthiU-0.0.1-SNAPSHOT.jar"]