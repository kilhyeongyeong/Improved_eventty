FROM openjdk:17-jdk-slim-buster
WORKDIR /app/event
COPY build/libs/*.jar event.jar
ENTRYPOINT ["java", "-jar", "event.jar"]