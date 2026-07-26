FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY maven-settings.xml .
RUN mvn -q -s maven-settings.xml -DskipTests dependency:go-offline
COPY src src
RUN mvn -q -s maven-settings.xml package -DskipTests
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
