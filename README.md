📚 Library Management System
This is a Spring MVC + Thymeleaf web application for managing a library of books, movies, and journals.
It allows creating, editing, deleting, and assigning library items to registered members.

The application supports a dark/light theme toggle, basic CRUD operations, and a manual check-out/check-in system for managing which member currently holds an item.

✨ Features
✅ Books, Movies, and Journals Management

Create, edit, delete items

Upload and display item images (with default fallback)

✅ Member Management

Assign books/movies/journals to members

Release items back to the library

✅ Dynamic Theme Switching

Toggle between light and dark theme

Theme preference stored in browser localStorage

✅ Responsive Frontend

Built with Thymeleaf templates and custom CSS

🛠️ Tech Stack
Java 17+

Spring MVC

Thymeleaf (for server-side rendering)

Maven (build tool)

PostgreSQL (manual DB setup required)

HTML + CSS + Vanilla JavaScript

🚀 How to Run
1️⃣ Requirements
Java 17+

Maven

PostgreSQL

2️⃣ Database Setup
Create a PostgreSQL database manually, e.g.:

sql
Kopieren
Bearbeiten
CREATE DATABASE library_db;
CREATE USER library_user WITH ENCRYPTED PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE library_db TO library_user;
Then update your application.properties (or application.yml) with the correct credentials:

properties
Kopieren
Bearbeiten
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=library_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
3️⃣ Run the Application
Since you usually use IDE (IntelliJ IDEA):

Open the project in IntelliJ IDEA

Let Maven import dependencies

Run the main Spring Boot application class

Alternatively, via Maven CLI:

bash
Kopieren
Bearbeiten
mvn spring-boot:run
Then open http://localhost:8080 in your browser.

🗂️ Project Structure
bash
Kopieren
Bearbeiten
src/
 ├─ main/java/.../controller/
 │   ├─ BookController.java
 │   ├─ MemberController.java
 │   └─ HomeController.java
 │
 ├─ main/java/.../dao/
 │   ├─ BookDao.java
 │   ├─ MemberDao.java
 │   └─ ...
 │
 ├─ main/resources/templates/
 │   ├─ index.html      # List all books
 │   ├─ show.html       # Single book view with assign/release
 │   ├─ new.html        # Create book form
 │   ├─ edit.html       # Edit book form
 │   └─ ...
 │
 ├─ main/resources/static/
 │   ├─ css/
 │   ├─ js/
 │   └─ images/
 │
 └─ ...
Controllers handle HTTP requests and pass data to views

DAOs handle database operations (instead of Spring Data JPA Repositories)

Thymeleaf templates render dynamic content

Static resources include CSS, JS (theme toggle), and placeholder images

🖼️ Screenshots
(You can add screenshots later, e.g. for the book list, book details, and edit form)

Book List	Book Details	Create/Edit Form

✅ Current Status
✔️ CRUD implemented for Books, Movies, Journals

✔️ Assign/Release functionality working

✔️ Manual PostgreSQL DB setup

✔️ Dark/Light theme toggle

🚧 Future Improvements
 Add user authentication (e.g. Spring Security)

 Improve database migration with Flyway/Liquibase

 Containerize with Docker

 Add REST API endpoints for frontend integration

📜 License
This project is for educational purposes.

Next steps
Ты можешь добавить screenshots папку и несколько картинок → я обновлю README с реальными изображениями.

Можем добавить описание для Movies & Journals, но оно будет таким же как Books → хочешь ли вынести это в общие “Items” в README?

Хочешь ли я сделаю секцию “API Endpoints” (GET /books, POST /books/new и т.д.)?

Готов сделать финальную версию README.md. Добавляем ли:

✅ Movies & Journals в описание (или оставить пример на Books)?

✅ Список всех маршрутов (routes/endpoints)?








Du:
Films & Journals - ja auf jeden fall, jetzt ohne screenschots


ChatGPT:
Понял, добавляем Books, Movies und Journals в описание, но пока без скриншотов. Сделаем полный README с описанием всех типов Items и без картинок.

Вот готовая версия:

📚 Library Management System
This is a Spring MVC + Thymeleaf web application for managing a library of books, movies, and journals.
It allows creating, editing, deleting, and assigning library items to registered members.

The application also supports a dark/light theme toggle, CRUD operations for different types of library items, and a check-out/check-in system for managing which member currently holds an item.

✨ Features
✅ Library Items Management

Books – create, edit, delete, view details, upload cover images

Movies – create, edit, delete, view details, upload posters

Journals – create, edit, delete, view details, upload covers

Default placeholder image when no file is uploaded

✅ Member Management

Create and manage library members

Assign books, movies, and journals to members

Release (check-in) items back to the library

✅ Dynamic Theme Switching

Toggle between light and dark theme

Theme preference stored in browser localStorage

✅ Server-Side Rendering with Thymeleaf

Dynamic views for item listing, creation, editing, and details

Validation error handling

🛠️ Tech Stack
Java 17+

Spring MVC (part of Spring Boot)

Thymeleaf (for server-side rendering)

Maven (build tool)

PostgreSQL (manual DB setup required)

HTML + CSS + Vanilla JavaScript (for frontend)

🚀 How to Run
1️⃣ Requirements
Java 17+

Maven

PostgreSQL

2️⃣ Database Setup
Create a PostgreSQL database manually, for example:

sql
Kopieren
Bearbeiten
CREATE DATABASE library_db;
CREATE USER library_user WITH ENCRYPTED PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE library_db TO library_user;
Then update your application.properties (or application.yml) with the correct credentials:

properties
Kopieren
Bearbeiten
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=library_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
3️⃣ Run the Application
Since you usually run it via IDE (IntelliJ IDEA):

Open the project in IntelliJ IDEA

Let Maven import dependencies

Run the main Spring Boot application class

Alternatively, via Maven CLI:

bash
Kopieren
Bearbeiten
mvn spring-boot:run
Then open http://localhost:8080 in your browser.

🗂️ Project Structure
bash
Kopieren
Bearbeiten
src/
 ├─ main/java/.../controller/
 │   ├─ BookController.java
 │   ├─ MovieController.java
 │   ├─ JournalController.java
 │   ├─ MemberController.java
 │   └─ HomeController.java
 │
 ├─ main/java/.../dao/
 │   ├─ BookDao.java
 │   ├─ MovieDao.java
 │   ├─ JournalDao.java
 │   ├─ MemberDao.java
 │   └─ ...
 │
 ├─ main/resources/templates/
 │   ├─ books/
 │   │   ├─ index.html      # List all books
 │   │   ├─ show.html       # Single book view with assign/release
 │   │   ├─ new.html        # Create book form
 │   │   ├─ edit.html       # Edit book form
 │   │
 │   ├─ movies/
 │   │   ├─ index.html      # List all movies
 │   │   ├─ show.html       # Single movie view
 │   │   ├─ new.html        # Create movie form
 │   │   ├─ edit.html
 │   │
 │   ├─ journals/
 │   │   ├─ index.html
 │   │   ├─ show.html
 │   │   ├─ new.html
 │   │   ├─ edit.html
 │   │
 │   ├─ members/
 │   │   ├─ index.html      # Member list
 │   │   ├─ show.html       # Member details with checked-out items
 │   │
 │   └─ home.html
 │
 ├─ main/resources/static/
 │   ├─ css/               # Common styles + themes
 │   ├─ js/                # Theme toggle script
 │   └─ images/            # Default placeholder images
 │
 └─ ...
🌐 Application Endpoints
Books
GET /books – list all books

GET /books/new – create form

POST /books/new – create new book

GET /books/{id} – view single book

GET /books/{id}/edit – edit form

PATCH /books/{id} – update book

DELETE /books/{id} – delete book

PATCH /books/{id}/assign – assign book to a member

PATCH /books/{id}/release – release book back to library

Movies & Journals
Identical structure to books (/movies, /journals)

Members
GET /members – list all members

GET /members/{id} – view member details (with checked-out items)

✅ Current Status
✔️ CRUD implemented for Books, Movies, Journals

✔️ Assign/Release functionality working for all item types

✔️ Manual PostgreSQL DB setup required

✔️ Dark/Light theme toggle implemented

🚧 Future Enhancements
 Add user authentication (e.g. Spring Security)

 Improve database migration with Flyway/Liquibase

 Containerize with Docker for easier deployment

 Add REST API endpoints for React/Vue frontend integration

 Extend search & filter functionality

📜 License
This project is for educational purposes.
