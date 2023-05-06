FROM openjdk:17-jdk

WORKDIR /app

COPY target/AxelSpin-0.0.1-SNAPSHOT.jar /app/AxelSpin.jar

ENTRYPOINT ["java", "-jar", "/app/AxelSpin.jar"]