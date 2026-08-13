# Blog App — Spring Boot REST API

A JWT-secured blogging REST API built with **Spring Boot, Spring Security, Spring Data JPA, and MySQL**.

The application provides user authentication, role-based authorization, user management, category management, post management, pagination, sorting, searching, and image upload/download functionality.

---

## 🚀 Features

- 🔐 JWT Authentication
- 👤 User Registration & Login
- 🛡️ Role-Based Authorization
  - `ROLE_USER`
  - `ROLE_ADMIN`
- 👥 User Management
  - Create User
  - Get User by ID
  - Get All Users
  - Search Users
  - Update User
  - Delete User
- 📂 Category Management
  - Create Category
  - Update Category
  - Delete Category
  - Get Category
  - Search Categories
- 📝 Post Management
  - Create Post
  - Update Post
  - Delete Post
  - Get Post by ID
  - Get All Posts
  - Get Posts by User
  - Get Posts by Category
  - Search Posts
- 📄 Pagination
- 🔢 Sorting
- 🔎 Search & Filtering
- 🖼️ Image Upload
- 📥 Image Download
- 🔒 Stateless JWT Security
- 📚 Swagger/OpenAPI Documentation
- ⚠️ Centralized Exception Handling
- 🔑 BCrypt Password Encryption

---

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Security**
- **JWT**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **ModelMapper**
- **Lombok**
- **Maven**
- **Swagger / OpenAPI**

---

## 📁 Project Structure

```text
src/main/java/blog
│
├── Security
│   ├── CustomUserDetailsService.java
│   ├── JwtAuthenticationEntryPoint.java
│   ├── JwtAuthenticationFilter.java
│   └── JwtTokenHelper.java
│
├── configuration
│   ├── SecurityConfig.java
│   └── OpenAPIConfig.java
│
├── controller
│   ├── AuthController.java
│   ├── UserController.java
│   ├── PostController.java
│   └── CategoryController.java
│
├── entity
│   ├── User.java
│   ├── Post.java
│   └── Category.java
│
├── payloads
│   ├── UserDto.java
│   ├── PostDto.java
│   ├── CategoryDto.java
│   ├── ApiResponse.java
│   └── PostResponse.java
│
├── repo
│   ├── Userrepo.java
│   ├── PostRepo.java
│   └── CategoryRepo.java
│
├── services
│   ├── Userservice.java
│   ├── PostService.java
│   ├── CategoryService.java
│   └── FileService.java
│
└── services/impl
    ├── Userserviceimpl.java
    ├── PostServiceImpl.java
    ├── CategoryServiceImpl.java
    └── FileServiceImpl.java
