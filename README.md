# 🧠 QuizMind — Backend API

Spring Boot REST API powering the QuizMind Android app/WebApp. Handles user authentication, AI-powered quiz generation via Google Gemini, score history, and test review.

> 📱 Android client repo → [QuizMind Android](https://github.com/RingkhangBTY/QuizMindAndroidApp)

---

## ✨ Features

- 🔐 JWT-based stateless authentication
- 🤖 AI quiz generation using **Google Gemini API**
- 📊 Score history tracking per user
- 📝 Per-question storage with user answers & explanations
- 🔍 Test review — returns questions with correct + user answers
- 📦 Clean layered architecture (Controller → Service → Repo)

---

## 🏗️ Project Structure

```
src/main/java/com/ringkhang/quizmindweb/
│
├── config/
│   ├── CorsConfig.java          # Allows all origins (Android support)
│   ├── GeminiConfig.java        # Gemini API client setup
│   ├── JWTFilter.java           # Validates JWT on every request
│   └── MyConfig.java            # Spring Security filter chain
│
├── controller/
│   ├── HomeController.java      # Auth + dashboard endpoints
│   ├── QuestionsController.java # Quiz generation endpoints
│   └── ScoreHistoryController.java # History endpoints
│
├── DTO/
│   ├── InitialAppPayloadDTO.java # Dashboard payload
│   ├── Questions.java
│   ├── QuizDetails.java
│   ├── Result.java
│   ├── ScoreHistoryDisplay.java
│   ├── SubmitQuizRequest.java
│   ├── TestReviewDTO.java
│   └── UserDetailsDTO.java
│
├── model/
│   ├── QuestionsTable.java      # questions table entity
│   ├── ScoreHistoryTable.java   # score_history table entity
│   ├── UserDetailsTable.java    # user_details table entity
│   ├── UserInput.java
│   └── UsersPrincipal.java
│
├── repo/
│   ├── QuizRepo.java
│   ├── ScoreHistoryRepo.java
│   └── UserDetailsRepo.java
│
├── service/
│   ├── GeminiService.java       # Calls Gemini AI to generate questions
│   ├── JWTTokenService.java     # JWT generation & validation
│   ├── MixedUtilService.java
│   ├── MyUserDetailsService.java
│   ├── QuizService.java
│   ├── ScoreHistoryService.java
│   └── UsersDetailsService.java
│
└── utils/
```

---

## 🔌 API Endpoints

### 🔓 Public (no JWT required)

| Method | Endpoint | Body / Params | Description |
|--------|----------|---------------|-------------|
| `POST` | `/register` | `{ username, pass, email }` | Register new user |
| `POST` | `/login/auth` | `?username=x&pass=y` | Login → returns JWT string |

### 🔐 Protected (JWT required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/users` | Get current user details |
| `GET` | `/initial_data` | Dashboard payload (stats + recent history) |
| `GET` | `/score_history` | Full test history for current user |
| `POST` | `/generate_quiz` | Generate AI quiz (Gemini) |
| `POST` | `/submit_quiz` | Save quiz result + questions to DB |
| `GET` | `/review/{id}` | Get questions for a past test |

> All protected endpoints read the current user from the JWT — no need to pass user ID manually.

---

## 🗄️ Database Schema (PostgreSql)

```sql
-- Users
CREATE TABLE user_details (
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL
);

-- Score History (linked to user)
CREATE TABLE score_history (
    score_id       SERIAL PRIMARY KEY,
    s_user_id      INTEGER NOT NULL,
    total_question INTEGER,
    correct_ans    INTEGER,
    test_score     INTEGER,
    time_stamp     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    feedback       VARCHAR(255),
    topic_sub      VARCHAR(255),
    level          VARCHAR(255),
    short_des      TEXT,
    CONSTRAINT fk_score_user FOREIGN KEY (s_user_id)
        REFERENCES user_details(id) ON DELETE CASCADE
);

-- Questions (linked to user + score history)
CREATE TABLE questions (
    q_id         SERIAL PRIMARY KEY,
    q_user_id    INTEGER NOT NULL,
    question     TEXT,
    option_a     VARCHAR(255),
    option_b     VARCHAR(255),
    option_c     VARCHAR(255),
    option_d     VARCHAR(255),
    correct_ans  VARCHAR(255),
    explanation  TEXT,
    user_ans     VARCHAR(255),
    time_stamp   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    q_history_id INTEGER NOT NULL,
    CONSTRAINT fk_question_user    FOREIGN KEY (q_user_id)
        REFERENCES user_details(id) ON DELETE CASCADE,
    CONSTRAINT fk_question_history FOREIGN KEY (q_history_id)
        REFERENCES score_history(score_id) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX idx_score_history_user ON score_history(s_user_id);
CREATE INDEX idx_questions_user     ON questions(q_user_id);
CREATE INDEX idx_questions_history  ON questions(q_history_id);
```

### Entity Relationships
```
user_details (1)
    ├──── (many) score_history
    └──── (many) questions
                    │
score_history (1) ──┴── (many) questions
```

---

## 🔐 Security & Auth Flow

```
POST /login/auth?username=x&pass=y
    │
    ▼
MyUserDetailsService loads user from DB
    │
    ▼
BCrypt (strength 12) verifies password
    │
    ▼
JWTTokenService generates token
    │
    ▼
Returns raw JWT string → stored on Android device

─────────────────────────────────────────────────

Every protected request:
    │
    ▼
JWTFilter intercepts
    └── reads Authorization: Bearer <token>
    └── validates signature + expiry
    └── sets user in SecurityContext
    │
    ▼
Controller receives request ✅
```

---

## ⚙️ Setup & Run

### Prerequisites
- Java 17+
- PostgreSQL running locally
- Google Gemini API key

### 1. Clone
```bash
git clone https://github.com/ringkhang/quizmind-backend
cd QuizMindWeb
```

### 2. Configure `.env` or `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/quizmind
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

jwt.secret=your_jwt_secret_key
gemini.api.key=your_gemini_api_key
```

### 3. Run
```bash
./mvnw spring-boot:run
```

### 4. API Docs (Swagger)
```
http://localhost:8080/swagger-ui.html
```

---

## 📦 Key Dependencies

```xml
<!-- pom.xml -->
spring-boot-starter-web
spring-boot-starter-security
spring-boot-starter-data-jpa
spring-boot-starter-validation
postgresql
jjwt (JWT)
lombok
springdoc-openapi (Swagger)
```

---

## 🚧 Roadmap

- [ ] Refresh token support
- [ ] Rate limiting on quiz generation
- [ ] Topic suggestions endpoint
- [ ] Leaderboard / global rankings
- [ ] Email verification on register

---

## 👤 Author

**Ringkhang**  
Built with ☕ Spring Boot + 🤖 Google Gemini +  🫙 PostgreSql