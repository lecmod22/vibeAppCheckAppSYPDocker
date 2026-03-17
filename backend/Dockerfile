FROM eclipse-temurin:21-jre

RUN mkdir /opt/application
COPY ./target/*.jar /opt/application/backend.jar
WORKDIR /opt/application
CMD ["java", "-jar", "backend.jar"]