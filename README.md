# InterviewOS

A Spring Boot REST API backend for structured interview preparation. InterviewOS helps you track coding questions, log practice attempts, write behavioral stories, schedule mock interviews, and measure your readiness — all secured per user with JWT authentication.

---

## What Makes This Project Stand Out

- **JWT-secured, user-isolated data** — every resource belongs to a specific user. Cross-user access is blocked at the repository level using `findByIdAndUserId`, not just at the service level.
- **Transactional status updates** — creating a practice attempt automatically updates the parent question's status, confidence score, and last attempted timestamp in one atomic transaction.
- **Dynamic filtering with Specifications** — `GET /api/questions` supports optional filtering by topic, difficulty, status, and company tag using Spring Data JPA Specifications, eliminating the exponential method explosion of derived query methods.
- **Intelligent practice recommendations** — `GET /api/practice-plan/today` analyzes your data and generates prioritized recommendations with reasons across three tiers: unattempted questions, low confidence questions, and overdue revision.
- **Analytics aggregation** — weak topic detection and topic summaries use custom JPQL aggregation queries with `COUNT`, `SUM(CASE WHEN...)`, and `AVG` grouped by topic.
- **Schema versioning with Flyway** — every database change is a versioned, trackable migration script. No Hibernate auto-DDL — schema evolution is explicit and reproducible.
- **Docker Compose ready** — the entire stack (app + database) starts with one command. No manual setup required beyond Docker Desktop.
- **Unit tests** — covering all service layers with Mockito, including security ownership tests proving users cannot access each other's data.

---

## Tech Stack

| Layer | Technology                          |
|---|-------------------------------------|
| Framework | Spring Boot 4.1.0                   |
| Language | Java 17                             |
| Database | PostgreSQL 16                       |
| ORM | Hibernate / Spring Data JPA         |
| Schema Management | Flyway                              |
| Security | Spring Security + JWT (JJWT 0.12.6) |
| Validation | Jakarta Validation                  |
| Build Tool | Maven                               |
| Containerization | Docker + Docker Compose             |
| Testing | JUnit 5 + Mockito                   |

---

## Application Architecture

```
com.example.interview_os
├── controller        HTTP routing, request/response handling
├── service           Business logic (interfaces + implementations)
├── repository        Data access layer (Spring Data JPA)
├── entity            JPA-mapped database entities
├── dto               Request and response data transfer objects
│   ├── analytics     Analytics-specific response DTOs
│   └── dashboard     Dashboard and practice plan DTOs
├── mapper            Entity ↔ DTO conversion
├── enums             Type-safe constants
├── specification     Dynamic filtering logic (Specifications)
├── security          JWT filter, SecurityConfig, SecurityUtils
└── exception         Custom exceptions and global error handler
```

### Request Flow

```
Client Request (with JWT token)
        ↓
JwtAuthenticationFilter — validates token, sets SecurityContext
        ↓
Spring Security — checks endpoint authorization rules
        ↓
Controller — deserializes JSON (@RequestBody), validates (@Valid)
        ↓
Service — business logic, ownership enforcement via SecurityUtils
        ↓
Repository — data access (Spring Data JPA + Hibernate)
        ↓
HikariCP connection pool → JDBC Driver → PostgreSQL
        ↓
Response mapped to DTO → serialized to JSON → returned to client
```

### Database Schema

```
users
├── id, name, email (unique), password (BCrypt), created_at

questions
├── id, title, description, topic, difficulty, company_tag
├── status, confidence_score, last_attempted_at, created_at
└── user_id (FK → users)

question_attempts
├── id, status, time_taken, approach, mistakes, notes
├── confidence_score, attempted_at
└── question_id (FK → questions)

stories
├── id, title, situation, task, action, result
├── category, tags, created_at
└── user_id (FK → users)

mock_interviews
├── id, company, type, status, scheduled_at, notes, created_at
└── user_id (FK → users)

interview_feedback
├── id, strengths, weaknesses, technical_score
├── communication_score, next_steps, created_at
└── interview_id (FK → mock_interviews, UNIQUE)
```

---

## Features

### Authentication
- User registration with BCrypt password hashing
- JWT login returning a signed token (24 hour expiry)
- Every protected endpoint requires `Authorization: Bearer <token>`

### Question Management
- Full CRUD for coding questions with topic, difficulty, company tag
- Dynamic filtering by topic, difficulty, status, and company tag
- User-isolated — each user sees only their own questions

### Attempt Tracking
- Log practice attempts with status (SOLVED/PARTIAL/FAILED), time taken, approach, mistakes, notes, and confidence score
- Each attempt automatically updates the parent question's status, confidence score, and last attempted timestamp in a single transaction

### Analytics
- Weak topic detection — topics ranked by failure rate and average confidence
- Topic summary — breakdown of solved, failed, and partial counts per topic

### Behavioral Stories (STAR Format)
- Create and store interview stories with Situation, Task, Action, Result
- Category tagging (LEADERSHIP, TEAMWORK, PROBLEM_SOLVING, etc.)
- Filter stories by category

### Mock Interview Scheduling
- Schedule mock interviews with company, type, and date
- Status lifecycle: SCHEDULED → COMPLETED (auto on feedback) / CANCELLED
- Filter by status

### Feedback
- Add structured feedback to completed interviews
- Strengths, weaknesses, technical score, communication score, next steps
- One feedback per interview enforced at database level

### Dashboard
- Overall readiness score (0-100) based on solved questions, attempts, and confidence
- Per-topic mastery breakdown (STRONG / DEVELOPING / WEAK / NOT_STARTED)
- Upcoming scheduled interviews with days until interview

### Practice Plan
- Daily practice recommendations with priority (HIGH/MEDIUM) and reasons
- Three recommendation tiers: unattempted questions, low confidence (< 5/10), overdue revision (> 7 days)

---

## API Reference

All endpoints except `/api/auth/**` require:
```
Authorization: Bearer <your-jwt-token>
```

---

### Authentication

#### Register
```
POST /api/auth/register

Request:
{
  "name": "string",
  "email": "string (valid email)",
  "password": "string (required)"
}

Response 201:
{
  "token": "eyJhbGci...",
  "email": "darsh@example.com",
  "name": "Darsh"
}
```

#### Login
```
POST /api/auth/login

Request:
{
  "email": "string",
  "password": "string"
}

Response 200:
{
  "token": "eyJhbGci...",
  "email": "darsh@example.com",
  "name": "Darsh"
}
```

---

### Questions

#### Create Question
```
POST /api/questions

Request:
{
  "title": "Two Sum",
  "description": "Find indices of two numbers that add up to target",
  "topic": "ARRAYS",
  "difficulty": "EASY",
  "companyTag": "Google"
}

Topics: ARRAYS, STRINGS, LINKED_LIST, TREES, GRAPHS, DYNAMIC_PROGRAMMING,
        SORTING, SEARCHING, RECURSION, GREEDY, BACKTRACKING, SYSTEM_DESIGN

Difficulties: EASY, MEDIUM, HARD

Response 201:
{
  "id": 1,
  "title": "Two Sum",
  "topic": "ARRAYS",
  "difficulty": "EASY",
  "status": "NOT_ATTEMPTED",
  "confidenceScore": 0,
  "lastAttemptedAt": null,
  "createdAt": "2026-07-17T10:00:00",
  "userId": 1
}
```

#### Get All Questions (with optional filters)
```
GET /api/questions
GET /api/questions?topic=ARRAYS
GET /api/questions?difficulty=EASY
GET /api/questions?topic=ARRAYS&difficulty=EASY
GET /api/questions?status=NOT_ATTEMPTED
GET /api/questions?companyTag=Google

Response 200: Array of question objects
```

#### Get Question by ID
```
GET /api/questions/{id}

Response 200: Single question object
Response 404: Question not found (or belongs to another user)
```

#### Update Question
```
PUT /api/questions/{id}

Request: Same as create
Response 200: Updated question object
```

#### Delete Question
```
DELETE /api/questions/{id}

Response 204: No content
```

---

### Attempts

#### Create Attempt
```
POST /api/questions/{questionId}/attempts

Request:
{
  "status": "SOLVED",
  "timeTaken": 25,
  "approach": "Two pointer technique",
  "mistakes": "Initially tried brute force",
  "notes": "Remember to sort first",
  "confidenceScore": 8
}

Status values: SOLVED, PARTIAL, FAILED
Confidence score: 1-10 (required)

Response 201:
{
  "id": 1,
  "questionId": 1,
  "status": "SOLVED",
  "timeTaken": 25,
  "approach": "Two pointer technique",
  "confidenceScore": 8,
  "attemptedAt": "2026-07-17T10:30:00"
}
```

#### Get Attempts for Question
```
GET /api/questions/{questionId}/attempts

Response 200: Array of attempt objects
```

---

### Analytics

#### Weak Topics
```
GET /api/analytics/weak-topics

Response 200:
[
  {
    "topic": "DYNAMIC_PROGRAMMING",
    "totalAttempts": 10,
    "failedAttempts": 7,
    "averageConfidence": 3.2,
    "failureRate": 70.0
  }
]
```

#### Topic Summary
```
GET /api/analytics/topic-summary

Response 200:
[
  {
    "topic": "ARRAYS",
    "totalAttempts": 15,
    "solvedCount": 10,
    "failedCount": 3,
    "partialCount": 2,
    "averageConfidence": 7.4
  }
]
```

---

### Stories (STAR Format)

#### Create Story
```
POST /api/stories

Request:
{
  "title": "Led team through critical deadline",
  "situation": "Our team had a product launch with a tight deadline",
  "task": "I was responsible for coordinating between teams",
  "action": "I set up daily standups and a shared tracker",
  "result": "We launched on time with zero critical bugs",
  "category": "LEADERSHIP",
  "tags": "leadership,teamwork,deadline"
}

Categories: LEADERSHIP, TEAMWORK, CONFLICT_RESOLUTION, PROBLEM_SOLVING,
            COMMUNICATION, ACHIEVEMENT, FAILURE, INITIATIVE,
            ADAPTABILITY, TIME_MANAGEMENT

Response 201: Story object with id, userId, all STAR fields, createdAt
```

#### Get Stories
```
GET /api/stories
GET /api/stories?category=LEADERSHIP

Response 200: Array of story objects
```

---

### Mock Interviews

#### Schedule Interview
```
POST /api/mock-interviews

Request:
{
  "company": "Google",
  "type": "TECHNICAL",
  "scheduledAt": "2026-08-15T10:00:00",
  "notes": "Focus on system design"
}

Types: TECHNICAL, BEHAVIORAL, SYSTEM_DESIGN, HR, MIXED

Response 201:
{
  "id": 1,
  "company": "Google",
  "type": "TECHNICAL",
  "status": "SCHEDULED",
  "scheduledAt": "2026-08-15T10:00:00",
  "userId": 1,
  "createdAt": "2026-07-17T10:00:00"
}
```

#### Get Interviews
```
GET /api/mock-interviews
GET /api/mock-interviews?status=SCHEDULED

Status values: SCHEDULED, COMPLETED, CANCELLED, RESCHEDULED

Response 200: Array of interview objects
```

#### Add Feedback
```
POST /api/mock-interviews/{id}/feedback

Request:
{
  "strengths": "Strong problem solving, clear communication",
  "weaknesses": "Need to improve system design knowledge",
  "technicalScore": 7,
  "communicationScore": 8,
  "nextSteps": "Study distributed systems, practice LeetCode hard"
}

Scores: 1-10

Response 201:
{
  "id": 1,
  "interviewId": 1,
  "strengths": "Strong problem solving",
  "technicalScore": 7,
  "communicationScore": 8,
  "nextSteps": "Study distributed systems",
  "createdAt": "2026-07-17T11:00:00"
}
```

---

### Dashboard

#### Readiness Score
```
GET /api/dashboard/readiness

Response 200:
{
  "overallScore": 72,
  "readinessLevel": "ALMOST_READY",
  "totalQuestions": 20,
  "solvedQuestions": 14,
  "totalAttempts": 35,
  "averageConfidence": 7.1,
  "summary": "Almost there! Focus on solving more questions."
}

Readiness levels: INTERVIEW_READY (80+), ALMOST_READY (60+),
                  IN_PROGRESS (40+), NEEDS_WORK (below 40)
```

#### Topic Breakdown
```
GET /api/dashboard/topic-breakdown

Response 200:
[
  {
    "topic": "ARRAYS",
    "totalQuestions": 5,
    "solvedQuestions": 4,
    "attemptedQuestions": 5,
    "averageConfidence": 8.2,
    "mastery": "STRONG"
  }
]

Mastery levels: STRONG (80%+ solved), DEVELOPING (50%+),
                WEAK (any solved), NOT_STARTED (none solved)
```

#### Upcoming Interviews
```
GET /api/dashboard/upcoming-interviews

Response 200:
[
  {
    "id": 2,
    "company": "Amazon",
    "type": "SYSTEM_DESIGN",
    "scheduledAt": "2026-08-15T10:00:00",
    "daysUntilInterview": 27
  }
]
```

---

### Practice Plan

#### Today's Plan
```
GET /api/practice-plan/today

Response 200:
{
  "summary": "Today's focus: 3 new questions to attempt. 2 questions need confidence building.",
  "recommendations": [
    {
      "topic": "DYNAMIC_PROGRAMMING",
      "reason": "Not attempted yet — start here",
      "questionId": 5,
      "questionTitle": "Longest Common Subsequence",
      "priority": "HIGH"
    },
    {
      "topic": "GRAPHS",
      "reason": "Low confidence score (3/10) — needs more practice",
      "questionId": 8,
      "questionTitle": "Number of Islands",
      "priority": "HIGH"
    },
    {
      "topic": "ARRAYS",
      "reason": "Not practiced in over 7 days — revision needed",
      "questionId": 2,
      "questionTitle": "Valid Parentheses",
      "priority": "MEDIUM"
    }
  ]
}
```

---

### Error Responses

All errors follow a consistent structure:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Question not found with id: 99",
  "path": "/api/questions/99",
  "timestamp": "2026-07-17T10:00:00"
}
```

| Status | Meaning |
|---|---|
| 400 | Validation failed or malformed request body |
| 401 | Missing or invalid JWT token |
| 403 | Authenticated but not authorized for this resource |
| 404 | Resource not found or belongs to another user |
| 409 | Conflict — e.g. email already registered |
| 500 | Unexpected server error |

---

## Running with Docker Compose

### Prerequisites
- Docker Desktop installed and running
- Git

### Steps

**1. Clone the repository:**
```bash
git clone https://github.com/shahdarshil123/interviewOS.git
cd interview-os
```

**2. Build the jar:**
```bash
mvn clean package -DskipTests
```

**3. Start the full stack:**
```bash
docker compose up --build
```

This starts two containers:
- `interviewos-db` — PostgreSQL 16 database on port 5432
- `interviewos-app` — Spring Boot application on port 8080

Flyway migrations run automatically on startup, creating all database tables in the correct order.

**4. Verify it's running:**

Watch the logs for:
```
interviewos-db   | database system is ready to accept connections
interviewos-app  | Started InterviewOsApplication in X seconds
```

**5. Register your first user:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Your Name","email":"you@example.com","password":"yourpassword"}'
```

**6. Use the returned token for all subsequent requests:**
```
Authorization: Bearer <token>
```

### Stopping
```bash
docker compose down
```

Data is persisted in a named Docker volume — restarting with `docker compose up` resumes from where you left off.

### Environment Variables

| Variable | Description | Default |
|---|---|---|
| `SPRING_DATASOURCE_URL` | JDBC connection string | `jdbc:postgresql://postgres:5432/interviewos` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `mysecretpassword` |
| `JWT_SECRET` | Token signing secret (min 32 chars) | see docker-compose.yml |
| `JWT_EXPIRATION` | Token expiry in milliseconds | `86400000` (24 hours) |

---

## Running Locally (without Docker)

### Prerequisites
- Java 17+
- Maven
- PostgreSQL running on port 5432 with database `interviewos`

### Steps

**1. Configure database in `src/main/resources/application.properties`:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/interviewos
spring.datasource.username=postgres
spring.datasource.password=yourpassword
```

**2. Run the application:**
```bash
mvn spring-boot:run
```

---

## Running Tests

```bash
mvn test
```

22 unit tests covering:
- `QuestionServiceTest` — CRUD with ownership enforcement (6 tests)
- `AttemptServiceTest` — attempt creation and retrieval (4 tests)
- `AuthServiceTest` — registration, login, duplicate email, password encoding (4 tests)
- `StoryServiceTest` — story creation, retrieval, category filtering, isolation (4 tests)
- `MockInterviewServiceTest` — interview creation, retrieval, cross-user blocking (4 tests)

---

## Key Spring Boot Concepts Demonstrated

**Dependency Injection** — constructor injection throughout, depending on interfaces not implementations following Dependency Inversion (SOLID).

**Spring Data JPA** — derived queries (`findByUserIdAndStatus`), custom JPQL with `@Query`, dynamic Specifications for multi-filter queries, `JpaSpecificationExecutor`.

**Spring Security + JWT** — stateless JWT authentication, `OncePerRequestFilter`, `SecurityContextHolder`, `BCryptPasswordEncoder`, `DaoAuthenticationProvider`.

**Transactional consistency** — `@Transactional` on multi-table operations ensuring atomic updates.

**Global exception handling** — `@RestControllerAdvice` with `@ExceptionHandler` for consistent error responses across all endpoints.

**Flyway migrations** — versioned SQL scripts (V1 through V9) managing schema evolution reproducibly.

**Docker Compose** — multi-service orchestration with health checks, named volumes, service networking, and environment variable overrides.

**DTO pattern** — separate Request and Response DTOs decoupled from entities, with Jakarta validation annotations on request DTOs.

**REST conventions** — nested resource URLs (`/questions/{id}/attempts`), correct HTTP verbs and status codes, resource-based naming.

---

## Project Structure Summary

```
interview-os/
├── src/
│   ├── main/
│   │   ├── java/com/example/interview_os/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── entity/
│   │   │   ├── dto/
│   │   │   ├── mapper/
│   │   │   ├── enums/
│   │   │   ├── specification/
│   │   │   ├── security/
│   │   │   └── exception/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/
│   │           ├── V1__create_questions_table.sql
│   │           ├── V2__create_users_table.sql
│   │           ├── V3__add_columns_to_questions.sql
│   │           ├── V4__create_question_attempts_table.sql
│   │           ├── V5__set_default_confidence_score.sql
│   │           ├── V6__add_user_id_to_questions.sql
│   │           ├── V7__create_stories_table.sql
│   │           ├── V8__create_mock_interviews_table.sql
│   │           └── V9__create_interview_feedback_table.sql
│   └── test/
│       └── java/com/example/interview_os/service/
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

---

