# docker build -t ghcr.io/josuefernandes7/ms-util-josue:latest .
# docker image ls

# taggear no remoto:
# docker tag josuefernandes7/ms-util-josue:latest ghcr.io/josuefernandes7/ms-util-josue:latest

# logar no ghcr com docker (use o PAT (token))
# docker login ghcr.io

# empurrar a imagem para o ghcr
# docker push ghcr.io/josuefernandes7/ms-util-josue:latest


# para checar, vá em org/packages

# deployar no render.com

FROM gradle:8.13-jdk17-alpine AS build

WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
COPY gradle ./gradle
RUN gradle build --no-daemon
# resulta num JAR

FROM eclipse-temurin:17-jdk-alpine AS runtime

WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
# resulta numa imagem executável

# se der CACHED use `docker system prune` para limpar