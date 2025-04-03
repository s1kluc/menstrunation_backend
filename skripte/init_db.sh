#!/bin/bash

# Warte darauf, dass MariaDB hochfährt und verfügbar ist
echo "Waiting for MariaDB to be ready..."

# Warten, bis MariaDB auf dem Dienstnamen 'mariadb' verfügbar ist
until mysql -h "mariadb" -u "root" -p"$MYSQL_ROOT_PASSWORD" -e "select 1" > /dev/null 2>&1; do
  sleep 2
done

echo "MariaDB is up and running. Running SQL commands..."

# Führe SQL-Befehle aus (z.B. Datenbank erstellen und Tabellen anlegen)
mysql -h "mariadb" -u "root" -p"$MYSQL_ROOT_PASSWORD" -e "CREATE DATABASE IF NOT EXISTS menstrunation_db;"

echo "Database created successfully"

# Startet den MariaDB-Dienst (dies wird der Standard-Docker-EntryPoint sein)
exec mysqld
