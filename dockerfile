FROM maven:3.8.3-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src/ /app/src/

RUN mvn package -DskipTests

FROM openjdk:17-jdk

COPY --from=build target/AxelSpin-0.0.1-SNAPSHOT.jar AxelSpin.jar

ENTRYPOINT ["java", "-jar", "AxelSpin.jar"]