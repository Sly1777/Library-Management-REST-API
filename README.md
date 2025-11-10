# Book Library Management System - REST API

A comprehensive REST API for managing a book library, built with Spring Boot, H2 database, and comprehensive unit tests using JUnit and Mockito.

## 🚀 Features

- **Complete CRUD Operations** for book management
- **Advanced Search** by title, author, genre, and ISBN
- **Availability Tracking** for books
- **Input Validation** with detailed error messages
- **Global Exception Handling** for consistent error responses
- **H2 In-Memory Database** for easy setup and testing
- **Comprehensive Unit Tests** with JUnit 5 and Mockito
- **RESTful API Design** following best practices
- **Sample Data Initialization** for quick testing

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## 🛠️ Tech Stack

- **Spring Boot 3.2.0** - Application framework
- **Spring Data JPA** - Data persistence
- **H2 Database** - In-memory database
- **Lombok** - Reduce boilerplate code
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework
- **Maven** - Dependency management

## 📦 Installation & Setup

1. **Clone the repository**
   ```bash
   cd "c:\Users\HomePC\Desktop\JAVA PROJECT FOR CV"
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080/api/books`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:booklibrarydb`
     - Username: `sa`
     - Password: (leave empty)

## 🧪 Running Tests

Run all unit tests:
```bash
mvn test
```

Run tests with coverage:
```bash
mvn clean test jacoco:report
```

## 📚 API Endpoints

### Book Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/books` | Get all books |
| GET | `/api/books/{id}` | Get book by ID |
| GET | `/api/books/isbn/{isbn}` | Get book by ISBN |
| GET | `/api/books/search/author?author={author}` | Search books by author |
| GET | `/api/books/search/title?title={title}` | Search books by title |
| GET | `/api/books/search/genre?genre={genre}` | Search books by genre |
| GET | `/api/books/available` | Get all available books |
| POST | `/api/books` | Create a new book |
| PUT | `/api/books/{id}` | Update a book |
| PATCH | `/api/books/{id}/availability` | Update book availability |
| DELETE | `/api/books/{id}` | Delete a book |

## 📝 API Examples

### 1. Get All Books
```bash
curl -X GET http://localhost:8080/api/books
```

### 2. Get Book by ID
```bash
curl -X GET http://localhost:8080/api/books/1
```

### 3. Create a New Book
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Java Programming",
    "author": "John Doe",
    "isbn": "9781234567890",
    "publicationDate": "2024-01-15",
    "genre": "Programming",
    "available": true,
    "description": "A comprehensive guide to Java programming"
  }'
```

### 4. Update a Book
```bash
curl -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Advanced Java Programming",
    "author": "John Doe",
    "isbn": "9781234567890",
    "publicationDate": "2024-01-15",
    "genre": "Programming",
    "available": true,
    "description": "An advanced guide to Java programming"
  }'
```

### 5. Update Book Availability
```bash
curl -X PATCH http://localhost:8080/api/books/1/availability \
  -H "Content-Type: application/json" \
  -d '{"available": false}'
```

### 6. Search Books by Author
```bash
curl -X GET "http://localhost:8080/api/books/search/author?author=Martin"
```

### 7. Get Available Books
```bash
curl -X GET http://localhost:8080/api/books/available
```

### 8. Delete a Book
```bash
curl -X DELETE http://localhost:8080/api/books/1
```

## 📊 Data Model

### Book Entity

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

### Field Validations

- **title**: Required, 1-200 characters
- **author**: Required, 1-100 characters
- **isbn**: Required, 10-13 characters, must be unique
- **publicationDate**: Required, date format
- **genre**: Optional, max 50 characters
- **available**: Optional, defaults to true
- **description**: Optional, max 1000 characters

## 🔍 Error Handling

The API provides consistent error responses:

### 404 Not Found
```json
{
  "status": 404,
  "message": "Book not found with id: 1",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 409 Conflict
```json
{
  "status": 409,
  "message": "Book with ISBN 9780132350884 already exists",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 400 Bad Request (Validation Errors)
```json
{
  "status": 400,
  "errors": {
    "title": "Title is required",
    "isbn": "ISBN must be between 10 and 13 characters"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

## 🧪 Test Coverage

The project includes comprehensive unit tests:

- **BookServiceTest**: Tests for service layer business logic
- **BookControllerTest**: Tests for REST API endpoints
- **BookRepositoryTest**: Tests for database operations

Test categories:
- ✅ CRUD operations
- ✅ Search functionality
- ✅ Validation
- ✅ Exception handling
- ✅ Edge cases

## 📁 Project Structure

```
book-library-api/
├── src/
│   ├── main/
│   │   ├── java/com/library/
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java
│   │   │   ├── controller/
│   │   │   │   └── BookController.java
│   │   │   ├── dto/
│   │   │   │   └── BookDTO.java
│   │   │   ├── exception/
│   │   │   │   ├── DuplicateResourceException.java
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── model/
│   │   │   │   └── Book.java
│   │   │   ├── repository/
│   │   │   │   └── BookRepository.java
│   │   │   ├── service/
│   │   │   │   └── BookService.java
│   │   │   └── BookLibraryApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-test.properties
│   └── test/
│       └── java/com/library/
│           ├── controller/
│           │   └── BookControllerTest.java
│           ├── repository/
│           │   └── BookRepositoryTest.java
│           └── service/
│               └── BookServiceTest.java
├── pom.xml
└── README.md
```

## 🎯 Sample Data

The application comes with 5 pre-loaded books for testing:

1. **Clean Code** by Robert C. Martin
2. **Effective Java** by Joshua Bloch
3. **Design Patterns** by Erich Gamma
4. **Spring in Action** by Craig Walls
5. **The Pragmatic Programmer** by Andrew Hunt

## 🔧 Configuration

### Application Properties

Key configurations in `application.properties`:

```properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:booklibrarydb
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

## 🚀 Future Enhancements

- [ ] Add pagination and sorting
- [ ] Implement user authentication and authorization
- [ ] Add book borrowing/lending functionality
- [ ] Integrate with external book APIs
- [ ] Add book cover image upload
- [ ] Implement full-text search
- [ ] Add API documentation with Swagger/OpenAPI
- [ ] Deploy to cloud platform

## 📄 License

This project is created for educational and portfolio purposes.

## 👤 Author

Created as a demonstration project for CV/Portfolio purposes.

## 🤝 Contributing

This is a portfolio project, but suggestions and feedback are welcome!

---

**Happy Coding! 📚**
