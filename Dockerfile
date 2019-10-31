FROM openjdk:8u111-jdk-alpine

EXPOSE 8080
ADD /target/questions-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "-./target/app.jar" ]
