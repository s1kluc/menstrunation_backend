
#version2
## Basis-Image mit JDK 17 (falls nötig, anpassen)
#FROM amazoncorretto:23-alpine-jdk
#
# Arbeitsverzeichnis im Container
#WORKDIR /app
#
### Kopiere das JAR-File ins Image
#COPY target/*.jar app.jar
#
## Startbefehl
#ENTRYPOINT ["java", "-jar", "app.jar"]
