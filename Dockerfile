#FROM eclipse-temurin:17-jdk-alpine
#WORKDIR /app
#COPY . /app
#
#RUN ./gradlew build --exclude-task test
#
#CMD ["./gradlew", "bootRun"]

#version2
## Basis-Image mit JDK 17 (falls nötig, anpassen)
#FROM eclipse-temurin:17-jdk-alpine
#
## Arbeitsverzeichnis im Container
#WORKDIR /app
#
## Kopiere das JAR-File ins Image
#COPY target/*.jar app.jar
#
## Port, auf dem die App läuft (falls nötig, anpassen)
#EXPOSE 8080
#
## Startbefehl
#ENTRYPOINT ["java", "-jar", "app.jar"]

# Basis-Image mit JDK 17 (falls nötig, anpassen)
FROM openjdk:17-jdk-slim

# Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere den gesamten Quellcode ins Image
COPY . .

# Gib Berechtigungen für das Gradle Wrapper-Skript
RUN chmod +x gradlew

# Führe den Gradle-Build aus
RUN ./gradlew build --no-daemon --exclude-task test

# Setze das gebaute JAR-File als Startpunkt
#CMD ["sh", "-c", "echo 'Generated JAR:' $(ls build/libs/*.jar) && java -jar $(ls build/libs/*.jar)"]
ENTRYPOINT ["java", "-jar", "build/libs/menstrunation_backend.jar"]
