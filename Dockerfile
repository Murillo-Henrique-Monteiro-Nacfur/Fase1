FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /workspace/app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21.0.7_6-jdk-alpine-3.21

WORKDIR /app

COPY --from=build /workspace/app/target/*.jar app.jar

EXPOSE 8080

ENV DATABASE_URL=
ENV DATABASE_USER=
ENV DATABASE_PASSWORD=

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dlog4j2.formatMsgNoLookups=true", "-XX:MinRAMPercentage=60", "-XX:MaxRAMPercentage=90", "-server", "-XX:+OptimizeStringConcat", "-XX:+UseStringDeduplication", "-jar", "app.jar"]


