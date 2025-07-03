FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/your-app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]