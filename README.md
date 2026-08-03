<div align="center">

# 📝 Blog Management API

### A RESTful Blog Management System built using Spring Boot

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.3-green?style=for-the-badge&logo=springboot)
![Hibernate](https://img.shields.io/badge/Hibernate-6-yellow?style=for-the-badge&logo=hibernate)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-green?style=for-the-badge&logo=swagger)

A RESTful Blog Management API developed using Spring Boot that enables users to create, manage, search, and organize blog posts and categories with a clean layered architecture.

</div>

---

# 📖 About Project

**Blog Management API** is a backend application developed using **Spring Boot**, **Spring Data JPA**, and **MySQL**.

The application provides RESTful APIs for managing:

- 👤 Users
- 📂 Categories
- 📝 Blog Posts

The project follows a layered architecture and implements backend development practices including:

- DTO Pattern
- Entity Relationship Mapping
- ModelMapper Integration
- Bean Validation
- Global Exception Handling
- Pagination
- Sorting
- Searching
- Swagger/OpenAPI Documentation

---

# ✨ Features

## 👤 User Management

- Create new users
- Update user information
- Delete users
- Get user by ID
- Get all users
- Manage user and post relationships


---

## 📂 Category Management

- Create categories
- Update categories
- Delete categories
- Get category by ID
- Get all categories
- Manage category and post relationships


---

## 📝 Blog Post Management

- Create blog posts
- Update posts
- Delete posts
- Get post by ID
- Get all posts
- Get posts by user
- Get posts by category
- Search posts using keywords
- Pagination support
- Sorting support


---

# 🔍 Search, Pagination & Sorting

The application supports efficient data retrieval using Spring Data JPA features.

## 🔎 Searching

Users can search blog posts using keywords.

Features:

- Search posts by title
- Search posts by content
- Filter data efficiently


Example:

```
GET /api/posts/search?keyword=spring
```

---

## 📄 Pagination

Pagination is implemented to efficiently handle large amounts of data.

Features:

- Page number based fetching
- Custom page size support
- Optimized database queries using Spring Data JPA


Example:

```
GET /api/posts?pageNumber=0&pageSize=5
```

---

## ↕️ Sorting

The application supports sorting of data for better organization and retrieval.

Features:

- Sort blog posts based on different fields
- Dynamic sorting support using Spring Data JPA
- Improve data organization and readability


Example:

```
GET /api/posts?sortBy=addedDate
```

---

# ✅ Validation

The application uses **Jakarta Bean Validation**.

Implemented validations:

- Required field validation
- Email format validation
- Password validation
- Post title validation
- Content validation
- Custom validation error messages

---

# ⚠️ Exception Handling

Centralized exception handling is implemented using:

- `@RestControllerAdvice`
- Custom Resource Not Found Exception
- Validation Exception Handling
- Standard API Response Structure

---

# 📄 API Documentation

Interactive API documentation is available using:

- Swagger UI
- OpenAPI Specification


After starting the application, open:

```
http://localhost:9518/swagger-ui/index.html
```

---

# 🛠 Technology Stack

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.5.3 |
| Spring MVC | ✓ |
| Spring Data JPA | ✓ |
| Hibernate | 6 |
| MySQL | 8 |
| Maven | ✓ |
| Lombok | ✓ |
| ModelMapper | 3.2.6 |
| Swagger/OpenAPI | ✓ |
| Spring Data Pageable | ✓ |

---

# 🏗 Application Architecture

```
                 Client
                   |
                   |
             HTTP Request
                   |
                   ▼
            Controller Layer
                   |
                   ▼
             Service Layer
                   |
                   ▼
            Repository Layer
                   |
                   ▼
              MySQL Database
```

---

# 📁 Project Structure

```
src/main/java/blog

│
├── controller
│
├── entity
│
├── payloads
│
├── repository
│
├── service
│      |
│      └── impl
│
├── exception
│
├── config
│
└── Blogapp.java
```

---

# 🗄 Database Entities

## 👤 User Entity

Stores user information.

Fields:

- id
- name
- email
- password
- about


Relationship:

```
User 1 -------- * Post
```

---

## 📂 Category Entity

Stores blog categories.

Fields:

- categoryId
- categoryTitle
- categoryDescription


Relationship:

```
Category 1 -------- * Post
```

---

## 📝 Post Entity

Stores blog post details.

Fields:

- postId
- title
- content
- imageName
- addedDate
- user
- category


---

# 🌐 REST API Endpoints

## 👤 User APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users/` | Create User |
| GET | `/api/users/` | Get All Users |
| GET | `/api/users/{userId}` | Get User By ID |
| PUT | `/api/users/{userId}` | Update User |
| DELETE | `/api/users/{userId}` | Delete User |


---

## 📂 Category APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/categories/` | Create Category |
| GET | `/api/categories/` | Get All Categories |
| GET | `/api/categories/{categoryId}` | Get Category By ID |
| PUT | `/api/categories/{categoryId}` | Update Category |
| DELETE | `/api/categories/{categoryId}` | Delete Category |


---

## 📝 Post APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/user/{userId}/category/{categoryId}/posts` | Create Post |
| GET | `/api/posts/` | Get All Posts |
| GET | `/api/posts/{postId}` | Get Post By ID |
| PUT | `/api/posts/{postId}` | Update Post |
| DELETE | `/api/posts/{postId}` | Delete Post |
| GET | `/api/user/{userId}/posts` | Get Posts By User |
| GET | `/api/category/{categoryId}/posts` | Get Posts By Category |
| GET | `/api/posts/search?keyword=value` | Search Posts |


---

# ⚙️ Installation & Setup

## 1. Clone Repository

```bash
git clone https://github.com/amitjangra9518/Blog-Management-API.git
```

---

## 2. Navigate to Project

```bash
cd Blog-Management-API
```

---

## 3. Create Database

Open MySQL:

```sql
CREATE DATABASE Blog;
```

---

## 4. Configure Database

Update:

```
src/main/resources/application.properties
```

Add:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Blog
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=9518
```

---

## 5. Run Application

Using Maven:

```bash
mvn spring-boot:run
```

or run:

```
Blogapp.java
```

from IntelliJ IDEA.

---

# 🚀 Future Enhancements

- 🔐 JWT Authentication
- Spring Security Integration
- Role Based Authorization
- Image Upload Feature
- Comments System
- Like & Bookmark Feature
- Advanced Search Filters
- Elasticsearch Integration
- Docker Deployment
- Cloud Database Integration


---

# 👨‍💻 Author

## Amit Jangra

Computer Science Engineering Student


GitHub:

https://github.com/amitjangra9518


---

# ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub.
