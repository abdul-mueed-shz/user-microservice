# 👤 User Admin Service – Spring Boot Microservice

A secure, OAuth-enabled **User Administration Service** built with Spring Boot.  
Designed to integrate seamlessly with [`springboot-microservices-toolkit`](https://github.com/abdul-mueed-shz/springboot-microservices-toolkit) for shared JWT security, DTOs, and utilities.

---

## 🚀 What Does This Service Do?

This microservice acts as the **authentication and user management layer** in your microservice ecosystem.

It provides:

- ✅ **User registration & login**
- 🔐 **JWT-based authentication**
- 🌐 **OAuth login** with Google, GitHub, LinkedIn, X (Twitter)
- 📤 Token issuance + user profile APIs
- 🔄 Pluggable with external identity services
- 🧠 Planned: full **Role-Based Access Control (RBAC)**

---

## 🔧 How it Works

1. User logs in via email/password or third-party OAuth (Google, GitHub, etc.)
2. A JWT is issued via `springboot-microservices-toolkit`'s security module
3. Other services (like payment, orders, etc.) validate JWT and extract user context
4. User profile or token refresh can be managed centrally here

---

## 📦 Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **JWT**
- **OAuth2 (Google, GitHub, LinkedIn, X)**
- **Docker & Docker Compose**
- **MongoDB / PostgreSQL (for user persistence)**

---

## 🧰 Built With

This service reuses components from:

👉 [`springboot-microservices-toolkit`](https://github.com/abdul-mueed-shz/springboot-microservices-toolkit)

- ✅ `JwtAuthenticationFilter`, `SecurityConfig`
- ✅ DTOs: `UserDTO`, OAuth-specific login objects
- ✅ Common exception handling & constants

---

## 🔐 Auth APIs (Example => Check controller for actual endpoints)

| Method | Endpoint            | Description                     |
|--------|---------------------|---------------------------------|
| POST   | `/api/auth/register`| Register new user               |
| POST   | `/api/auth/login`   | Email + password authentication |
| POST   | `/api/auth/oauth`   | OAuth login (Google, GitHub...) |
| GET    | `/api/user/profile` | Get authenticated user details  |

> 🛠️ `Authorization: Bearer <JWT>` header required for protected routes

---

## 🌐 OAuth Providers Supported

- ✅ Google
- ✅ GitHub
- ✅ LinkedIn
- ✅ X (Twitter)

These are handled via DTOs provided by the toolkit and mapped to a unified user model in the DB.

---

## 📁 `.env` Configuration

```env
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION_MS=86400000

GOOGLE_CLIENT_ID=...
GOOGLE_CLIENT_SECRET=...

GITHUB_CLIENT_ID=...
GITHUB_CLIENT_SECRET=...

LINKEDIN_CLIENT_ID=...
LINKEDIN_CLIENT_SECRET=...

X_CLIENT_ID=...
X_CLIENT_SECRET=...

MONGO_URI=mongodb://localhost:27017/userservice
```

## 🧪 Sample Login Request

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@example.com",
  "password": "secure123"
}
```

## 📜 Future Plans

-	🔐 Role-based access control (RBAC)
-	🛂 Admin-only APIs
-	📊 Activity logging for audit trails
-	🧩 Federated identity support
-	🧩 Integrate proper database (Possibly MongoDb)

## 📦 Dockerized for Deployment

```bash
docker-compose up --build
```

The service will boot up with:
-	OAuth + JWT enabled
-	.env configuration loaded into Spring Boot

## 🧩 Recommended Toolkit

Use this alongside:

👉 springboot-microservices-toolkit
For shared UserDTO, JwtTokenProvider, OAuth DTOs, and more.

## 📄 License

“Your central hub for authentication, identity, and secure access in a modern Java microservice ecosystem.”
