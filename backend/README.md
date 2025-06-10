# Lockermat Backend

## Prerequisites
- Java 21
- Maven 3.6+ 
- Docker & Docker Compose (v2.4+)

## Project Structure
```
backend/
├─ src/             # Java source & resources
├─ pom.xml          # Maven configuration
├─ Dockerfile.backend   # Multi-stage build for the app
├─ Dockerfile.postgres  # PostGIS-enabled PostgreSQL image
└─ docker-compose.yaml  # Orchestrates db + app services
```

## Running with Docker Compose
The Compose file builds two services:
1. **db**: Postgres 17 with PostGIS, persists data under `./.pg_data`, and includes a healthcheck.
2. **app**: Your Spring Boot service, built via a Maven builder stage, waits for `db` to be healthy.

```bash
cd backend
docker-compose up --build --force-recreate
```
The app will be available on `http://localhost:8080`.

## Database Migrations
Flyway is enabled by default and runs on startup, applying SQL files under `classpath:db/migration`.