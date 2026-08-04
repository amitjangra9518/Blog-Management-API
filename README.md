<div align="center">

# 📝 Blog Management API

### A RESTful Blog Management System built using Spring Boot

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.3-green?style=for-the-badge&logo=springboot)
![Hibernate](https://img.shields.io/badge/Hibernate-6-yellow?style=for-the-badge&logo=hibernate)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-green?style=for-the-badge&logo=swagger)

A RESTful Blog Management API developed using **Spring Boot** that enables users to create, manage, organize, search, and share blog posts through well-structured REST APIs. The project follows a clean layered architecture and provides secure, scalable, and maintainable backend services.

</div>

---

# 📖 About Project

**Blog Management API** is a backend application developed using **Spring Boot**, **Spring Data JPA**, **Hibernate**, and **MySQL**.

The application provides REST APIs for managing:

- 👤 Users
- 📂 Categories
- 📝 Blog Posts
- 🖼 Image Upload & Download

The project follows a layered architecture and implements modern backend development practices including:

- DTO Pattern
- Entity Relationship Mapping
- ModelMapper
- Bean Validation
- Global Exception Handling
- Pagination
- Sorting
- Searching
- File Upload
- File Download
- Swagger/OpenAPI Documentation

---

# ✨ Features

## 👤 User Management

- Create User
- Update User
- Delete User
- Get User by ID
- Get All Users
- Manage User-Post Relationship

---

## 📂 Category Management

- Create Category
- Update Category
- Delete Category
- Get Category by ID
- Get All Categories
- Manage Category-Post Relationship

---

## 📝 Blog Post Management

- Create Blog Posts
- Update Posts
- Delete Posts
- Get Post by ID
- Get All Posts
- Get Posts by User
- Get Posts by Category
- Search Posts by Title
- Pagination Support
- Sorting Support

---

## 🖼 Image Management

- Upload Image for Blog Posts
- Download/View Uploaded Images
- Store Images on Local Server
- Generate Unique File Names using UUID
- Associate Images with Blog Posts
- Multipart File Upload Support

---

# 🔍 Search, Pagination & Sorting

The application supports efficient data retrieval using Spring Data JPA.

## 🔎 Searching

Search blog posts by title.

Example

```http
GET /api/posts/search/{keywords}
```

---

## 📄 Pagination

Retrieve blog posts page-wise.

Example

```http
GET /api/post?pageNumber=0&pageSize=5
```

---

## ↕️ Sorting

Retrieve blog posts in sorted order.

Example

```http
GET /api/post?sortBy=addedDate
```

---

# ✅ Validation

The application uses **Jakarta Bean Validation**.

Implemented validations include:

- Required Field Validation
- Email Validation
- Password Validation
- Post Title Validation
- Content Validation
- Custom Validation Messages

---

# ⚠ Exception Handling

Centralized exception handling is implemented using:

- `@RestControllerAdvice`
- ResourceNotFoundException
- Validation Exception Handling
- Standard API Response Structure

---

# 📄 API Documentation

Interactive API documentation is available using Swagger.

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
| Multipart File Upload | ✓ |

---

# 🏗 Application Architecture

```
                Client
                  │
                  ▼
          HTTP Request
                  │
                  ▼
        Controller Layer
                  │
                  ▼
         Service Layer
                  │
                  ▼
       Repository Layer
                  │
                  ▼
           MySQL Database
                  │
                  ▼
          Local File Storage
```

---

# 📁 Project Structure

```
src/main/java/blog

├── config
├── controller
├── entity
├── exception
├── payloads
├── repository
├── services
│   └── impl
├── config
└── Blogapp.java

src/main/resources
├── application.properties

BlogImages/
```

---

# 🗄 Database Entities

## 👤 User

Stores user information.

Fields

- id
- name
- email
- password
- about

Relationship

```
User (1) -------- (*) Post
```

---

## 📂 Category

Stores category information.

Fields

- categoryId
- categoryTitle
- categoryDescription

Relationship

```
Category (1) -------- (*) Post
```

---

## 📝 Post

Stores blog post details.

Fields

- postId
- title
- content
- imageName
- addedDate
- user
- category

Relationship

```
Post
 ├── User
 ├── Category
 └── Image
```

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
| POST | `/api/user/{userId}/category/{categoryId}/post` | Create Post |
| GET | `/api/post` | Get All Posts |
| GET | `/api/post/{postId}` | Get Post By ID |
| PUT | `/api/post/{postId}` | Update Post |
| DELETE | `/api/post/{postId}` | Delete Post |
| GET | `/api/user/{userId}/post` | Get Posts By User |
| GET | `/api/category/{categoryId}/post` | Get Posts By Category |
| GET | `/api/posts/search/{keywords}` | Search Posts |

---

## 🖼 Image APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/post/image/upload/{postId}` | Upload Image for a Post |
| GET | `/api/post/image/{imageName}` | Download/View Image |

---

# 📸 Image Upload

Upload an image for any blog post.

**Endpoint**

```http
POST /api/post/image/upload/{postId}
```

**Content-Type**

```
multipart/form-data
```

**Form Data**

| Key | Type |
|------|------|
| image | File |

**Response**

- Upload successful
- Image stored on server
- Unique image name generated using UUID
- Post updated with uploaded image

---

# 📥 Image Download

Download or view an uploaded image.

**Endpoint**

```http
GET /api/post/image/{imageName}
```

The server returns the requested image so it can be viewed in the browser or downloaded by the client.

---

# ⚙ Installation & Setup

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

```sql
CREATE DATABASE Blog;
```

---

## 4. Configure Database

Update **application.properties**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Blog
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=9518

project.image=C:/Users/YourName/Desktop/BlogImages/

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

---

## 5. Run the Application

Using Maven

```bash
mvn spring-boot:run
```

or run

```
Blogapp.java
```

from IntelliJ IDEA.

---

# 📌 API Testing

The APIs can be tested using:

- Swagger UI
- Postman
- cURL

---

# 🚀 Future Enhancements

- JWT Authentication
- Spring Security Integration
- Role-Based Authorization
- Comments System
- Like & Bookmark Feature
- Rich Text Editor
- Advanced Search Filters
- Elasticsearch Integration
- Docker Support
- AWS S3 / Cloudinary Image Storage
- Email Notifications
- User Profile Images
- API Rate Limiting

---

# 👨‍💻 Author

## Amit Jangra

**Computer Science Engineering Student**

### GitHub

https://github.com/amitjangra9518

---

# ⭐ Support

If you found this project helpful, please consider giving it a **⭐ Star** on GitHub. Your support motivates further improvements and helps others discover the project.

Happy Coding! 🚀
