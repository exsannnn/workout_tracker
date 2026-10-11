# Workout Tracker

REST API для учёта тренировок.

Пользователь может зарегистрироваться, создать план тренировок, начать тренировку, записать свои подходы и посмотреть историю и статистику прогресса.

## Технологии

- Java 21
- Spring Boot 3.5.3
- Spring Web
- Spring Data JPA
- Spring Security и JWT
- PostgreSQL 18
- Flyway
- Maven
- JUnit и Mockito
- Docker Compose

## Запуск

Нужны Java 21, Maven Wrapper и Docker Desktop.

Запусти приложение:

```bash
./mvnw spring-boot:run
```

Spring Boot запустит PostgreSQL из `compose.yaml`. Миграции Flyway создадут таблицы и добавят базовые упражнения.

Проверка приложения:

```bash
curl http://localhost:8080/api/health
```

Ответ:

```json
{"status":"UP"}
```

Проверка тестов:

```bash
./mvnw test
```

## Основные endpoints

### Авторизация

```text
POST /api/auth/register
POST /api/auth/login
```

Оба endpoint возвращают JWT-токен. Для защищённых запросов нужен заголовок:

```text
Authorization: Bearer <token>
```

### Упражнения

```text
GET /api/exercises
GET /api/exercises?category=STRENGTH
```

### Планы тренировок

```text
POST   /api/workout-plans
GET    /api/workout-plans
GET    /api/workout-plans/{id}
PUT    /api/workout-plans/{id}
DELETE /api/workout-plans/{id}
```

### Тренировки

```text
POST  /api/workouts/from-plan/{planId}
GET   /api/workouts/{id}
PATCH /api/workouts/{workoutId}/sets/{setId}
POST  /api/workouts/{id}/complete
GET   /api/workouts/history?page=0&size=10&name=chest
```

### Статистика

```text
GET /api/statistics
```

## Что есть в проекте

- регистрация и вход пользователей;
- роли `USER` и `ADMIN`;
- JWT-аутентификация;
- упражнения и фильтр по категории;
- планы тренировок с упражнениями, подходами, повторениями и весом;
- выполненные тренировки и история;
- поиск и пагинация истории;
- статистика прогресса;
- валидация и обработка ошибок;
- миграции базы данных через Flyway;
- модульные тесты;
- Docker Compose для PostgreSQL;
- GitHub Actions CI для запуска тестов.