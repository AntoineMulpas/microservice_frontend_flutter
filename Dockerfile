FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY .mvn/ .mvn

RUN mvn clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "/app/target/microservice-patient.jar"]
