FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY gradlew .
COPY settings.gradle .
COPY build.gradle .
COPY gradle ./gradle

COPY src ./src

RUN chmod +x ./gradlew

RUN ./gradlew bootJar -x test

FROM eclipse-temurin:17-jre-alpine

ARG JAR_FILE=build/libs/*.jar

COPY --from=build /app/${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=$PORT"]