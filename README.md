# Library Management System

A **Spring MVC + Thymeleaf** web application for managing a library of books, movies, and journals. This project is built for educational purposes and demonstrates modern Java web development, CRUD operations, theming, and integration with PostgreSQL.

---

## Features

- **Library Items Management**
  - Books, Movies, Journals: Create, edit, delete, view details, upload images (covers/posters)
  - Default placeholder image if no upload provided

- **Member Management**
  - Register new members
  - Assign books, movies, and journals to members (check-out)
  - Release (check-in) items back to the library

- **Dynamic Theme Switching**
  - Toggle between light and dark themes
  - Preference stored in browser localStorage

- **Server-Side Rendering with Thymeleaf**
  - Dynamic item/member views, forms, and error handling

---

## 🛠Tech Stack

- **Java 17+**
- **Spring MVC** (Spring Boot)
- **Thymeleaf** (server-side rendering)
- **Maven** (build tool)
- **PostgreSQL** (manual setup)
- **HTML, CSS, Vanilla JavaScript** (frontend)

---

## Getting Started

### Requirements

- Java 17 or higher
- Maven
- PostgreSQL

### 2️⃣ Database Setup

Manually create a PostgreSQL database and user, for example:
```sql
CREATE DATABASE library_db;
CREATE USER library_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE library_db TO library_user;
```

Add/update the following properties in your `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=library_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Running the Application

```bash
mvn spring-boot:run
```

---

## Application Endpoints

### Books

- `GET /books` — List all books
- `GET /books/new` — Create form
- `POST /books/new` — Create new book
- `GET /books/{id}` — View single book
- `GET /books/{id}/edit` — Edit form
- `PATCH /books/{id}` — Update book
- `DELETE /books/{id}` — Delete book
- `PATCH /books/{id}/assign` — Assign to member
- `PATCH /books/{id}/release` — Release to library

### Movies & Journals

- Identical structure to books: `/movies`, `/journals`

### Members

- `GET /members` — List all members
- `GET /members/{id}` — Member details (with checked-out items)

---

## Current Status

- Full CRUD for Books, Movies, Journals
- Assign/Release functionality for all item types
- Manual PostgreSQL setup required
- Dark/Light theme toggle implemented

---

## Future Enhancements

- User authentication (Spring Security)
- Database migration support (Flyway/Liquibase)
- Docker containerization
- REST API endpoints for frontend integration (React/Vue)
- Enhanced search & filter features

---

## License

This project is for educational purposes.  
Feel free to use, modify, and learn from this repository!

---
