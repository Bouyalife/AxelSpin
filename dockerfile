FROM maven:3.8.2-jdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk

COPY "target/AxelSpin-0.0.1-SNAPSHOT.jar" "AxelSpin.jar"

ENTRYPOINT ["java", "-jar", "AxelSpin.jar"]