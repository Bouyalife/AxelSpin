
FROM openjdk:17-jdk

COPY --from=build target/AxelSpin-0.0.1-SNAPSHOT.jar AxelSpin.jar

ENTRYPOINT ["java", "-jar", "AxelSpin.jar"]