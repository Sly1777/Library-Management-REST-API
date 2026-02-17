````md
Book Library Management System – REST API

A REST API for managing a book library, built using Spring Boot, H2 database, and JUnit + Mockito for unit testing.

---

 1. Features

- Full CRUD operations  
- Search by title, author, genre, ISBN  
- Track availability status  
- Input validation with informative errors  
- Global exception handling  
- In-memory H2 database for easy setup  
- Swagger UI integration  
- Sample data initialization  
- JUnit 5 + Mockito tests  

---

 2. Requirements

- Java 17+  
- Maven 3.6+  

---

 3. Tech Stack

| Technology         | Purpose              |
|-------------------|----------------------|
| Spring Boot 3.2.0 | Core framework       |
| Spring Data JPA   | Data persistence     |
| H2 Database       | In-memory DB         |
| Lombok            | Reduce boilerplate   |
| SpringDoc OpenAPI | Swagger UI           |
| JUnit 5           | Testing              |
| Mockito           | Mocking              |
| Maven             | Build tool           |

---

 4. Installation & Running

```bash
mvn clean install
mvn spring-boot:run
````

### Access

* Swagger UI: `http://localhost:8080/swagger-ui.html`
* API Base URL: `http://localhost:8080/api/books`
* H2 Console: `http://localhost:8080/h2-console`

  * JDBC URL: `jdbc:h2:mem:booklibrarydb`
  * Username: `sa`
  * Password: *(leave empty)*

---

## 5. Testing

```bash
mvn test
mvn clean test jacoco:report
```

---

## 6. API Endpoints

| Method | Endpoint                                 | Description         |
| ------ | ---------------------------------------- | ------------------- |
| GET    | /api/books                               | List all books      |
| GET    | /api/books/{id}                          | Get by ID           |
| GET    | /api/books/isbn/{isbn}                   | Get by ISBN         |
| GET    | /api/books/search/author?author={author} | Search by author    |
| GET    | /api/books/search/title?title={title}    | Search by title     |
| GET    | /api/books/search/genre?genre={genre}    | Search by genre     |
| GET    | /api/books/available                     | Available books     |
| POST   | /api/books                               | Create book         |
| PUT    | /api/books/{id}                          | Update book         |
| PATCH  | /api/books/{id}/availability             | Update availability |
| DELETE | /api/books/{id}                          | Delete book         |

---

## 7. Data Model (Example)

```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "9780132350884",
  "publicationDate": "2008-08-01",
  "genre": "Programming",
  "available": true,
  "description": "A Handbook of Agile Software Craftsmanship"
}
```

---

## 8. Error Examples

### 404 Not Found

```json
{
  "status": 404,
  "message": "Book not found with id: 1"
}
```

### 409 Conflict

```json
{
  "status": 409,
  "message": "Book with ISBN already exists"
}
```

---

## 9. Sample Preloaded Books

* Clean Code – Robert C. Martin
* Effective Java – Joshua Bloch
* Design Patterns – Erich Gamma
* Spring in Action – Craig Walls
* The Pragmatic Programmer – Andrew Hunt

---

