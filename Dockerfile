FROM ibm-semeru-runtimes:open-23.0.2_7-jre
WORKDIR /app

COPY /build/libs/menstrunation_backend-0.0.1-SNAPSHOT.jar /app/menstrunation_backend-0.0.1-SNAPSHOT.jar
EXPOSE 8080

# Kopiere das SQL-Skript in das Initialisierungs-Verzeichnis
#COPY src/main/resources/db/migration/V1__init_database.sql /docker-entrypoint-initdb.d/

# Führe den SQL-Befehl während des Builds aus

CMD ["java", "-jar", "/app/menstrunation_backend-0.0.1-SNAPSHOT.jar"]