# Decsoft recruitment task service

## Instalacja

Bazę polecam postawić na dockerze:

```docker run --name postgres-db-decsoft -p 5333:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=decsoft postgres```

## Wskazówki

* Na starcie są tworzeni użytkownicy o loginach: admin, user (do obydwu jest hasło "password")
* Mamy w systemie dwie role: "ADMIN" i "USER" - ale na froncie nie ma ukrywania przycisków (nie wszystkie RESTy może wykonywać użytkownik w roli "USER").
