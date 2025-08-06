
# Library Management System

A learning-focused web application.  
Created to explore CRUD development with multiple related entities using Java and Spring MVC.  
Includes form handling with Thymeleaf, database integration with PostgreSQL, and basic unit testing using JUnit.

The application allows users to:
- manage books, movies, and journals (create, edit, delete),
- upload and display cover images,
- register library members,
- assign and release items (check-out and return),
- toggle between light and dark themes (stored in localStorage).


---

## Features

### Library Items Management

- Manage **Books**, **Movies**, and **Journals**
- Create, edit, delete, view details
- Upload images (covers/posters)
- Default placeholder image if no image is uploaded

### Member Management

- Register new members
- Assign items to members (Check-out)
- Release items back to the library (Check-in)

### Dynamic Theme Switching

- Toggle between light and dark mode
- Preference stored in browser `localStorage`

### Server-Side Rendering

- Thymeleaf for dynamic views, form handling, and error display

---

## Tech Stack

| Layer           | Technology                |
|-----------------|---------------------------|
| Language        | Java 17+                  |
| Backend         | Spring MVC                |
| Template Engine | Thymeleaf                 |
| Database        | PostgreSQL (manual setup) |
| Build Tool      | Maven                     |
| Frontend        | HTML, CSS, Vanilla JS     |
| Testing         | JUnit  (basic service layer tests)    |

---

## Getting Started

### Requirements

- Java 17+
- Maven
- PostgreSQL

### Database Setup

Manually create a PostgreSQL database and user:

```sql
CREATE DATABASE library_db;
CREATE USER your_username WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE library_db TO your_username;
```

Add/update the following properties in your `application.properties`:

```properties
db.driver=org.postgresql.Driver
db.url=jdbc:postgresql://localhost:5432/library_db
db.username=your_username
db.password=your_password
upload.dir=your/absolute/path/to/images
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.show_sql=true
```

### Running the Application

#### Step 1: Build the project

```bash
mvn clean package
```

This will generate a WAR file under the `target/` directory, for example:

```bash
target/my-app.war
```

#### Step 2: Deploy the WAR file to Tomcat

- Copy the generated WAR file into the `webapps/` directory of your local Tomcat installation.
- Start Tomcat (`catalina.sh run` or via IDE).
- Tomcat will automatically deploy and unpack the WAR.

#### Step 3: Access the application

```bash
http://localhost:8080/my-app/
```

Replace `my-app` with the actual name of your WAR file (without the `.war` extension).

---

## Testing

### Test Coverage

- Unit tests written for service layer (e.g. `BookServiceTest`)
- Currently covers CRUD operations
- Further validation and error handling tests planned

### Running Tests

```bash
mvn test
```

More tests are planned as the application evolves.
## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com.library/
│   │       ├── config/         # App configuration
│   │       ├── controllers/    # Spring MVC controllers
│   │       ├── dao/            # Manual DAO classes (JDBC-based)
│   │       ├── models/         # Domain entities
│   │       ├── repositories/   # Spring Data JPA repositories
│   │       ├── services/       # Business logic
│   │       └── utils/          # Utility classes (e.g. image upload)
│   │
│   └── webapp/
│       ├── css/                # Stylesheets
│       ├── images/             # Uploaded/static images
│       ├── js/                 # JavaScript files
│       └── WEB-INF/            # JSP/Thymeleaf templates
│
├── test/
│   └── java/
│       └── com.library
│            └── services/      # Unit tests for service layer classes         

```

## Application Endpoints

### Books

| Method | Endpoint              | Description                          |
|--------|-----------------------|--------------------------------------|
| GET    | `/books`              | Retrieve all books                   |
| GET    | `/books/new`          | Show the book creation form          |
| POST   | `/books/new`          | Create a new book                    |
| GET    | `/books/{id}`         | Retrieve a specific book             |
| GET    | `/books/{id}/edit`    | Show the edit form for a book        |
| PATCH  | `/books/{id}`         | Update an existing book              |
| DELETE | `/books/{id}`         | Delete a book                        |
| PATCH  | `/books/{id}/assign`  | Assign the book to a member          |
| PATCH  | `/books/{id}/release` | Release the book back to the library |

### Movies and Journals

- The endpoints for managing movies and journals follow the same structure as books, using `/films` and `/journals` as base paths.

### Members

| Method | Endpoint             | Description                           |
|--------|----------------------|---------------------------------------|
| GET    | `/members`           | Retrieve all members                  |
| GET    | `/members/new`       | Show the member creation form         |
| POST   | `/members`           | Create a new member                   |
| GET    | `/members/{id}`      | Retrieve details of a specific member |
| GET    | `/members/{id}/edit` | Show the member edit form             |
| PATCH  | `/members/{id}`      | Update an existing member             |
| DELETE | `/members/{id}`      | Delete a member                       |

---

## Current Status

- Full CRUD for Books, Movies, Journals
- Assign/Release functionality
- Dark/Light theme toggle
- Validated forms with server-side error display
- Responsive frontend with hover effects and styling
- Placeholder images for missing uploads
- JUnit tests implementation started

---

## Future Enhancements

- User authentication and role-based access (Spring Security)
- Database migration support (Flyway)
- Enhanced search & filtering capabilities
- Overdue status display
- Pagination for large datasets
- REST API endpoints for external integration (Spring REST)
- Unit and integration test coverage with JUnit and Mockito
- Layout improvements
- Docker support for containerized deployment

---


## UI Preview

### Theme Comparison

| Light Mode                           | Dark Mode                          |
|--------------------------------------|------------------------------------|
| ![Light](screenshots/light_mode.png) | ![Dark](screenshots/dark_mode.png) |

---

### Book List

![Book List](screenshots/books_list.png)

---

### Book Detail

![Book Detail](screenshots/book_detail.png)

---

### Edit Book Form

![Edit Book](screenshots/book_edit.png)

---

### Member Detail

![Member Detail](screenshots/member_detail.png)


## License

This project is for **educational use only**.  
Feel free to fork, clone, and modify as needed.

---

## Author

Lena Manicheva

- GitHub: [github.com/Lena435545](https://github.com/Lena435545)


