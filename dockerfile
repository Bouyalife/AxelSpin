FROM maven:3.8.3-jdk-17 as build

COPY . .

RUN mvn clean package

FROM openjdk:17-jdk


COPY target/AxelSpin-0.0.1-SNAPSHOT.jar AxelSpin.jar

ENTRYPOINT ["java", "-jar", "AxelSpin.jar"]