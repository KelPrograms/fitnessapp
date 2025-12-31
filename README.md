# FitnessApp

A Spring Boot backend for a fitness tracking application. This repository contains the server-side implementation (Java + Spring Boot) and a companion frontend (JavaScript) used for the web client. The backend exposes REST APIs for user management, workouts, plans, metrics, and analytics.

Key language composition:
- Java: 75.6% (Spring Boot backend)
- JavaScript: 20.9% (frontend / admin UI)
- CSS / HTML: small amounts for static assets

---

## Table of contents

- [Project overview](#project-overview)
- [Tech stack](#tech-stack)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting started (quickstart)](#getting-started-quickstart)
  - [Run with Maven (recommended)](#run-with-maven-recommended)
  - [Run with Gradle](#run-with-gradle)
  - [Run with Docker](#run-with-docker)
  - [Run frontend locally](#run-frontend-locally)
- [Configuration](#configuration)
- [Database & migrations](#database--migrations)
- [API documentation](#api-documentation)
- [Testing](#testing)
- [Building & packaging](#building--packaging)
- [Development workflows](#development-workflows)
- [Contributing](#contributing)
- [Troubleshooting](#troubleshooting)
- [License & contact](#license--contact)

---

## Project overview

This project provides a scalable REST API to support fitness tracking features:
- User registration, authentication, and profile management
- Workout plans, sessions, and exercise templates
- Tracking metrics (weight, body measurements, reps/sets, performance)
- Scheduled jobs for data aggregation and reminders
- Basic analytics endpoints for progress visualization

The backend is structured as a typical Spring Boot application (controllers, services, repositories, DTOs). The frontend (if present in `frontend/` or `web/`) communicates with the backend REST API.

---

## Tech stack

- Java (Spring Boot)
- Spring Web, Spring Data JPA, Spring Security (optional), Spring Actuator
- Database: Postgres (recommended) / H2 for local dev
- Build tool: Maven (assumed). If your repo uses Gradle, replace commands accordingly.
- Optional: Flyway or Liquibase for migrations
- Frontend: JavaScript (likely Node/npm or yarn project)
- Docker & Docker Compose for containerized environments

Useful links:
- Spring Boot: https://spring.io/projects/spring-boot
- Maven: https://maven.apache.org
- Docker: https://www.docker.com

---

## Features

- RESTful APIs for core fitness domain (users, workouts, metrics)
- JWT or session-based authentication (check `application.properties` for chosen strategy)
- Health & metrics via Spring Actuator
- Configurable persistence (H2 for dev, Postgres for production)
- API docs via Swagger/OpenAPI (if enabled)
- Optional Docker support for easy local deployment

---

## Prerequisites

- Java 17+ (or Java 11 if the project targets that; check `pom.xml` for `maven.compiler.target`)
- Maven 3.6+ (or Gradle if used)
- PostgreSQL (for non-H2 setups)
- Node.js + npm/yarn (to run the frontend)
- Docker & Docker Compose (optional)

---

## Getting started (quickstart)

The instructions below assume Maven is used. If the repo uses Gradle, run the equivalent Gradle commands.

### Run with Maven (recommended)

1. Copy application configuration for local development:
   - Create `src/main/resources/application-dev.yml` or `application-dev.properties`
   - Or set environment variables (see [Configuration](#configuration)).

2. Start an embedded/test database (or configure a local Postgres). To run with an in-memory H2 profile:
   - Run:
     ```bash
     mvn spring-boot:run -Dspring-boot.run.profiles=dev
     ```
   - Or build and run the jar:
     ```bash
     mvn clean package
     java -jar target/fitnessapp-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
     ```

3. The API will be available at:
   - http://localhost:8080/ (default)
   - Health: http://localhost:8080/actuator/health (if actuator is enabled)

### Run with Gradle

If the project uses Gradle, run:
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Run with Docker

A `Dockerfile` and/or `docker-compose.yml` may be present. Example quick steps:

1. Build the image:
   ```bash
   mvn clean package -DskipTests
   docker build -t kelprograms/fitnessapp:local .
   ```

2. Run with environment variables or a compose file:
   ```bash
   docker run -e SPRING_PROFILES_ACTIVE=prod -p 8080:8080 kelprograms/fitnessapp:local
   ```
Or with docker-compose:
```bash
docker-compose up
```

(If there is an existing `docker-compose.yml`, prefer that; it will commonly include a database service.)

### Run frontend locally

If there is a frontend folder (e.g. `frontend/` or `web/`):

1. cd into the frontend folder:
   ```bash
   cd frontend
   npm install
   npm start
   ```
2. Configure the frontend to point to the backend API (via environment variables such as REACT_APP_API_URL or similar).

---

## Configuration

Configuration is loaded from `application.yml` / `application.properties` and environment variables.

Common properties to configure:
- Server:
  - `server.port` (default 8080)
- Datasource:
  - `spring.datasource.url`
  - `spring.datasource.username`
  - `spring.datasource.password`
- JPA:
  - `spring.jpa.hibernate.ddl-auto` (use `validate` or `none` in production)
- Security:
  - JWT secrets, expiration, OAuth client IDs (if applicable)
- Profiles:
  - `spring.profiles.active` (dev, test, prod)

Examples (environment variables):
- SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/fitness
- SPRING_DATASOURCE_USERNAME=fitness_user
- SPRING_DATASOURCE_PASSWORD=change-me
- SPRING_PROFILES_ACTIVE=prod

If you prefer a `.env` for local docker-compose, include these environment variables there.

---

## Database & migrations

- Development: H2 in-memory can be used for quick local setup (profile `dev`).
- Production: PostgreSQL is recommended.

If migrations are used:
- Flyway: place SQL migrations in `src/main/resources/db/migration`.
- Liquibase: changelogs typically live in `src/main/resources/db/changelog`.

Run migrations automatically on startup (commonly enabled in production). Make sure credentials and migration locations are configured in your application properties.

---

## API documentation

If Swagger/OpenAPI is enabled:
- Visit: http://localhost:8080/swagger-ui.html or http://localhost:8080/swagger-ui/index.html
- The OpenAPI JSON is typically at: `/v3/api-docs`

If the project uses Spring REST Docs, generated snippets may be in `target/generated-snippets`.

---

## Testing

Run unit & integration tests:

- With Maven:
  ```bash
  mvn test
  ```

- For integration tests that require an external DB, either:
  - Run tests with an embedded DB/testcontainers, or
  - Configure a test profile pointing to a test database.

Check the test folder `src/test/java` for examples and base test classes.

---

## Building & packaging

- Build the JAR:
  ```bash
  mvn clean package
  ```
- The runnable jar is typically in:
  `target/fitnessapp-<version>.jar`

- Build a Docker image (example):
  ```bash
  docker build -t kelprograms/fitnessapp:1.0.0 .
  ```

---

## Development workflows

- Branching: follow GitHub Flow (feature branches -> PR -> main)
- Commit messages: use conventional commits where practical (eg. feat:, fix:, chore:)
- Code style: use the project formatter / checkstyle config if present (check `pom.xml` for plugins)
- Tests: include unit tests for new logic and update integration tests where needed

---

## Contributing

See [CONTRIBUTING.md](./CONTRIBUTING.md) for guidelines on submitting issues, pull requests, and code style.

Short summary:
- Fork -> branch from main -> implement feature/fix -> run tests -> open PR
- Include tests and documentation for new features
- Use meaningful commit messages
- Keep PR scope small and focused

---

## Troubleshooting

- Port already in use: check `server.port` and confirm no other process uses 8080
- Database connection errors: verify `spring.datasource.*` settings and that the database is reachable
- Migrations failing: inspect migration scripts and the migration history table
- Authentication issues: verify environment secret keys and token configuration

If a specific error occurs, include logs and configuration when opening an issue.

---

## License & contact

Specify your license here (e.g., MIT, Apache-2.0). If not yet chosen, add a LICENSE file.

Maintainer / Contact:
- GitHub: [KelPrograms](https://github.com/KelPrograms)

---

Thank you for using FitnessApp. If you'd like, I can also:
- Generate a starter `application.yml` and `.env.example`
- Create a Dockerfile and docker-compose.yml template
- Generate an OpenAPI (Swagger) starter configuration
Let me know which one you'd like next.
