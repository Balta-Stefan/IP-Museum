FROM openjdk:11-jre-slim
COPY target/*.jar virtualBank.jar
ENTRYPOINT [ "java", "-jar", "/virtualBank.jar" ]