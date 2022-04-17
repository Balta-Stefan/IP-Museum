FROM openjdk:11-jre-slim
RUN mkdir "Static content"
RUN mkdir "Static content/Tours static content"
RUN mkdir "Static content/Angular frontend"
COPY target/*.jar museum.jar
COPY [ "Angular frontend", "/Static content/Angular frontend" ]
ENTRYPOINT [ "java", "-jar", "/museum.jar" ]