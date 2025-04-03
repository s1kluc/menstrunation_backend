FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . /app

RUN ./gradlew build --exclude-task test

CMD ["./gradlew", "bootRun"]