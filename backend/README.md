# Lockermat Backend

## Prerequisites
- Java 21
- Maven 3.6+ 
- Docker & Docker Compose (v2.4+)

## Running with Docker Compose
The Compose file builds two services:
1. **db**: Postgres 17 with PostGIS, persists data under `./.pg_data`, and includes a healthcheck.
2. **app**: Your Spring Boot service, built via a Maven builder stage, waits for `db` to be healthy.

```bash
docker-compose -f docker-compose-local.yaml up --build --force-recreate
```
The app will be available on `http://localhost:8080`.

## Building locally
Ensure the test database is available before running tests:
```bash
docker-compose -f docker-compose-test.yaml up
```
Run tests with Maven:
```bash
mvn clean install
```

## Database Migrations
Flyway is enabled by default and runs on startup, applying SQL files under `classpath:db/migration`.