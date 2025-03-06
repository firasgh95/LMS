# Spring Boot Application

## Introduction
This is a Spring Boot application that serves as a template for building RESTful services, web applications, or microservices using Java and the Spring framework.

## Features
- Spring Boot auto-configuration
- REST API support
- Embedded Tomcat server
- Database integration with Spring Data JPA
- Logging with SLF4J and Logback
- Spring Security integration (if applicable)
- Actuator endpoints for monitoring

## Prerequisites
- Java 17 or later (check with `java -version`)
- Maven or Gradle installed
- (Optional) Docker for containerization

## Setup and Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/firasgh95/LMS
   cd spring-boot-app
   ```

2. Build the project using Maven:
   ```sh
   mvn clean install
   ```
   or using Gradle:
   ```sh
   ./gradlew build
   ```

3. Run the application:
   ```sh
   mvn spring-boot:run
   ```
   or
   ```sh
   java -jar target/spring-boot-app.jar
   ```

## Configuration
Application properties are defined in `src/main/resources/application.properties` or `application.yml`.

Example (for an H2 database):
```properties
spring.application.name=lib
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3300/library
spring.datasource.username=root
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
```

## API Endpoints
By default, the application runs on `http://localhost:8080`.
You can test API endpoints using tools like Postman or curl.

## Docker Support
To build and run the application in a Docker container:
```sh
docker build -t spring-boot-app .
docker run -p 8080:8080 spring-boot-app
```

## Testing
Run unit and integration tests using:
```sh
mvn test
```

## Actuator Endpoints (if enabled)
- Health Check: `http://localhost:8080/actuator/health`
- Metrics: `http://localhost:8080/actuator/metrics`

