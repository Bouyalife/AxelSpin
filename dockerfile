FROM maven:3.8.3-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src/ /app/src/

RUN mvn package -DskipTests

FROM openjdk:17-jdk

WORKDIR /app

COPY --from=build /app/target/AxelSpin-0.0.1-SNAPSHOT.jar /app/AxelSpin.jar

ENTRYPOINT ["java", "-jar", "AxelSpin.jar"]