# Blog App — Spring Boot REST API

A JWT-secured blogging REST API built with Spring Boot, Spring Security, and Spring Data JPA. Supports user registration/login, role-based access control (`ROLE_USER` / `ROLE_ADMIN`), categories, posts, and image uploads for post thumbnails.

## Features

- JWT authentication (`/api/v1/auth/login`) with 5-hour token expiry
- User registration, update, delete, search, and paginated listing
- Role-based authorization — regular users vs. admins
- Category CRUD with search
- Post CRUD scoped to a user + category, with pagination, search, and per-category/per-user listing
- Image upload and download for posts, with content-type detection based on the actual file extension
- Centralized exception handling with consistent JSON error responses

## Tech Stack

- Java 17+
- Spring Boot
- Spring Security (JWT, stateless sessions)
- Spring Data JPA / Hibernate
- ModelMapper
- Lombok
- Swagger / OpenAPI (springdoc)
- MySQL (or any relational DB — configure in `application.properties`)

## Getting Started

### Prerequisites

- JDK 17+
- Maven
- MySQL (or your preferred relational database)

### Configuration

Set the following in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blog_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# Folder where uploaded post images are stored
project.image=./uploads/images
```

### Run

```bash
mvn spring-boot:run
```

The API will start on `http://localhost:9009` (or whatever port you've configured).

### API Docs

Once running, Swagger UI is available at:

```
http://localhost:9009/swagger-ui/index.html
```

Click **Authorize** and paste your JWT token (no `Bearer ` prefix needed — Swagger adds it) to test protected endpoints directly from the browser.

## Authentication Flow

1. **Register** — `POST /api/users/`
   ```json
   {
     "name": "Jane Doe",
     "email": "jane@example.com",
     "password": "secret123",
     "about": "Just here to blog."
   }
   ```
   New users are always created with `ROLE_USER`; there is no public endpoint to self-assign `ROLE_ADMIN`.

2. **Login** — `POST /api/v1/auth/login`
   ```json
   {
     "username": "jane@example.com",
     "password": "secret123"
   }
   ```
   Returns a JWT token.

3. **Use the token** — attach it to every subsequent request:
   ```
   Authorization: Bearer <token>
   ```

## Key Endpoints

| Method | Path | Access |
|---|---|---|
| `POST` | `/api/v1/auth/login` | Public |
| `POST` | `/api/users/` | Public (registration) |
| `GET` | `/api/users/**` | Authenticated (USER or ADMIN) |
| `PUT` / `DELETE` | `/api/users/**` | ADMIN only |
| `GET` | `/api/post/image/**` | Public |
| `POST` / `PUT` / `DELETE` | `/api/category`, `/api/post` | Authenticated |

## Known Limitations / Notes for Contributors

- `GET /api/category` and `GET /api/post` currently require authentication. If you want public blog browsing (readers without accounts), add explicit `permitAll()` matchers for those `GET` routes in `SecurityConfig`.
- Category creation is currently open to any authenticated user, not restricted to admins. Consider locking `POST /api/category` down to `hasRole("ADMIN")` if that fits your intended workflow.
- `SecurityConfig` permits the exact path `/api/v1/auth/login` rather than a wildcard. If you add more public auth endpoints later (e.g. `/refresh`, `/register-admin`), remember to add matchers for them explicitly.

## License

Add your license of choice here (MIT, Apache 2.0, etc.).
