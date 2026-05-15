# EventHub Academy 2026

Piattaforma per organizzare e prenotare eventi: conferenze, workshop, meetup, corsi di formazione.

## Prerequisiti

- Java 17+
- Maven
- Docker Desktop

## Avvio del progetto

1. Avvia il database: docker-compose up -d

## Avvio della applicazione:
mvn spring-boot:run

## Database

- **Host:** localhost
- **Porta:** 5432
- **Database:** eventhub
- **Utente:** eventhub_user
- **Password:** eventhub_pass

## Adminer (interfaccia DB)

Disponibile su: http://localhost:8081

## Comandi Docker principali

| Comando | Descrizione |
|---|---|
| `docker-compose up -d` | Avvia i contenitori |
| `docker-compose down` | Ferma i contenitori |
| `docker-compose ps` | Stato dei contenitori |
| `docker-compose logs postgres` | Log del database |