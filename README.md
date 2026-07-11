# Sonata

A RESTful API built with Spring Boot for managing users and music collections. This application provides comprehensive endpoints for creating, retrieving, updating, and deleting users and music records.

## Table of Contents

- [Overview](#overview)
- [Technologies](#technologies)
- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Routes](#api-routes)
- [Request/Response Examples](#requestresponse-examples)
- [Database](#database)
- [Project Structure](#project-structure)

## Overview

Sonata is a music management API that allows you to:
- Manage user accounts with credentials
- Manage a music catalog with song details
- Perform CRUD operations on both entities
- Built with Spring Boot 4.1.0 and Java 21

## Technologies

- **Java 21** - Programming language
- **Spring Boot 4.1.0** - Web framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - ORM and database abstraction
- **PostgreSQL** - Primary database
- **H2 Database** - In-memory database for testing
- **Lombok** - Boilerplate code generation
- **Maven** - Build tool

## Requirements

- **Java 21** or higher
- **PostgreSQL 12** or higher
- **Maven 3.6** or higher
- **Git**

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/golfettozh/Sonata.git
cd Sonata
```

### 2. Install PostgreSQL

Make sure PostgreSQL is installed and running on your system.

#### On Windows:
```bash
# Using Chocolatey
choco install postgresql
```

#### On macOS:
```bash
# Using Homebrew
brew install postgresql@15
brew services start postgresql@15
```

#### On Linux:
```bash
# Ubuntu/Debian
sudo apt-get install postgresql postgresql-contrib
```

### 3. Create the Database

```bash
psql -U postgres
```

Then in the PostgreSQL terminal:

```sql
CREATE DATABASE sonata;
```

## Configuration

### Application Properties

The application uses different configuration profiles:

#### Main Configuration (`application.properties`)

```properties
spring.application.name=Sonata
server.port=8081

# Active profile (test or dev)
spring.profiles.active=dev

# JPA/Hibernate Configuration
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

# Error Handling
spring.web.error.include-stacktrace=never
spring.web.error.include-message=never
spring.web.error.include-binding-errors=never
```

#### Development Configuration (`application-dev.properties`)

```properties
spring.datasource.username=${USER_DB:postgres}
spring.datasource.password=${PASSWORD_DB:postgres}
spring.datasource.url=${URL_DB:jdbc:postgresql://localhost:5432/sonata}
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Environment Variables

You can override the default database configuration using environment variables:

- `USER_DB` - Database username (default: `postgres`)
- `PASSWORD_DB` - Database password (default: `postgres`)
- `URL_DB` - Database URL (default: `jdbc:postgresql://localhost:5432/sonata`)

#### Setting Environment Variables

**On Windows (PowerShell):**
```powershell
$env:USER_DB = "your_username"
$env:PASSWORD_DB = "your_password"
$env:URL_DB = "jdbc:postgresql://localhost:5432/sonata"
```

**On macOS/Linux (Bash):**
```bash
export USER_DB=your_username
export PASSWORD_DB=your_password
export URL_DB=jdbc:postgresql://localhost:5432/sonata
```

### Server Configuration

- **Port:** 8081 (configurable via `server.port`)
- **Context Path:** `/` (root)
- **Profiles:** `dev` (default), `test` (for testing)

## Running the Application

### Using Maven

```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run

# Run with a specific profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Using Java Direct

```bash
# After building with Maven
java -jar target/Sonata-0.0.1-SNAPSHOT.jar
```

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest
```

The application will start on `http://localhost:8081`

## API Routes

### User Endpoints

#### 1. Get All Users

**Endpoint:** `GET /users`

**Description:** Retrieves a list of all users in the system.

**Response:**
```json
[
  {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com"
  },
  {
    "id": 2,
    "username": "jane_smith",
    "email": "jane@example.com"
  }
]
```

**Status Code:** `200 OK`

---

#### 2. Get User by ID

**Endpoint:** `GET /users/{id}`

**Description:** Retrieves a specific user by their ID.

**Path Parameters:**
- `id` (Long, required) - The user ID

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com"
}
```

**Status Codes:**
- `200 OK` - User found
- `404 NOT FOUND` - User not found

---

#### 3. Create User

**Endpoint:** `POST /users`

**Description:** Creates a new user account.

**Request Body:**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Validation Rules:**
- `username` - Required, must not be blank
- `email` - Required, must be valid email format, must be unique
- `password` - Required, must not be blank

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com"
}
```

**Status Codes:**
- `201 CREATED` - User successfully created
- `400 BAD REQUEST` - Validation error or email already exists
- `409 CONFLICT` - Email already registered

---

#### 4. Delete User

**Endpoint:** `DELETE /users/{id}`

**Description:** Deletes a user by ID.

**Path Parameters:**
- `id` (Long, required) - The user ID to delete

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com"
}
```

**Status Codes:**
- `200 OK` - User successfully deleted
- `404 NOT FOUND` - User not found

---

### Music Endpoints

#### 1. Get All Musics

**Endpoint:** `GET /musics`

**Description:** Retrieves a list of all music records in the catalog.

**Response:**
```json
[
  {
    "id": 1,
    "title": "Bohemian Rhapsody",
    "artist": "Queen",
    "durationInMinutes": 5.55
  },
  {
    "id": 2,
    "title": "Imagine",
    "artist": "John Lennon",
    "durationInMinutes": 3.03
  }
]
```

**Status Code:** `200 OK`

---

#### 2. Get Music by ID

**Endpoint:** `GET /musics/{id}`

**Description:** Retrieves a specific music record by its ID.

**Path Parameters:**
- `id` (Long, required) - The music ID

**Response:**
```json
{
  "id": 1,
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Status Codes:**
- `200 OK` - Music found
- `404 NOT FOUND` - Music not found

---

#### 3. Create Music

**Endpoint:** `POST /musics`

**Description:** Adds a new music record to the catalog.

**Request Body:**
```json
{
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Validation Rules:**
- `title` - Required, must not be blank
- `artist` - Required, must not be blank
- `durationInMinutes` - Required, must be a positive number

**Response:**
```json
{
  "id": 1,
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Status Codes:**
- `201 CREATED` - Music successfully created
- `400 BAD REQUEST` - Validation error

---

#### 4. Delete Music

**Endpoint:** `DELETE /musics/{id}`

**Description:** Deletes a music record by ID.

**Path Parameters:**
- `id` (Long, required) - The music ID to delete

**Response:**
```json
{
  "id": 1,
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Status Codes:**
- `200 OK` - Music successfully deleted
- `404 NOT FOUND` - Music not found

---

## Request/Response Examples

### Using cURL

#### Create a User

```bash
curl -X POST http://localhost:8081/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

#### Get All Users

```bash
curl -X GET http://localhost:8081/users \
  -H "Accept: application/json"
```

#### Get User by ID

```bash
curl -X GET http://localhost:8081/users/1 \
  -H "Accept: application/json"
```

#### Delete User

```bash
curl -X DELETE http://localhost:8081/users/1
```

#### Create a Music Record

```bash
curl -X POST http://localhost:8081/musics \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Bohemian Rhapsody",
    "artist": "Queen",
    "durationInMinutes": 5.55
  }'
```

#### Get All Musics

```bash
curl -X GET http://localhost:8081/musics \
  -H "Accept: application/json"
```

#### Get Music by ID

```bash
curl -X GET http://localhost:8081/musics/1 \
  -H "Accept: application/json"
```

#### Delete Music

```bash
curl -X DELETE http://localhost:8081/musics/1
```

### Using Postman

1. **Create Collection:** `Sonata API`
2. **Create Requests:**
   - Name: `Create User`
     - Method: `POST`
     - URL: `http://localhost:8081/users`
     - Body (JSON):
       ```json
       {
         "username": "john_doe",
         "email": "john@example.com",
         "password": "securePassword123"
       }
       ```

   - Name: `Get All Users`
     - Method: `GET`
     - URL: `http://localhost:8081/users`

   - Name: `Create Music`
     - Method: `POST`
     - URL: `http://localhost:8081/musics`
     - Body (JSON):
       ```json
       {
         "title": "Bohemian Rhapsody",
         "artist": "Queen",
         "durationInMinutes": 5.55
       }
       ```

   - Name: `Get All Musics`
     - Method: `GET`
     - URL: `http://localhost:8081/musics`

## Database

### Database Models

#### Users Table

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | User unique identifier |
| username | VARCHAR | NOT NULL | User's display name |
| email | VARCHAR | NOT NULL, UNIQUE | User's email address |
| password | VARCHAR | NOT NULL | User's password (hashed) |

#### Musics Table

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Music unique identifier |
| title | VARCHAR | NOT NULL | Song title |
| artist | VARCHAR | NOT NULL | Artist name |
| durationInMinutes | DOUBLE | NOT NULL | Song duration in minutes |

### Database Features

- **Auto-DDL:** Hibernate is configured with `ddl-auto=update`, which automatically creates/updates tables
- **Connection Pooling:** Spring Boot configures connection pooling automatically
- **Transaction Management:** All database operations are transaction-managed by Spring

### Accessing H2 Console (Development)

When running in development mode, you can access the H2 console at:
```
http://localhost:8081/h2-console
```

**Default Credentials:**
- URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (empty)

## Project Structure

```
Sonata/
├── src/
│   ├── main/
│   │   ├── java/com/golfettozh/sonata/
│   │   │   ├── controller/
│   │   │   │   ├── UserController.java
│   │   │   │   └── MusicController.java
│   │   │   ├── service/
│   │   │   │   ├── UserService.java
│   │   │   │   └── MusicService.java
│   │   │   ├── model/
│   │   │   │   ├── User.java
│   │   │   │   └── Music.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── MusicRepository.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   │   ├── UserRequestDTO.java
│   │   │   │   │   └── MusicRequestDTO.java
│   │   │   │   └── response/
│   │   │   │       ├── UserResponseDTO.java
│   │   │   │       └── MusicResponseDTO.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   └── SonataApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-dev.properties
│   └── test/
│       ├── java/com/golfettozh/sonata/
│       │   ├── service/
│       │   │   ├── UserServiceTest.java
│       │   │   └── MusicServiceTest.java
│       │   └── SonataApplicationTests.java
│       └── resources/
│           └── application-test.properties
├── pom.xml
└── README.md
```

## Component Descriptions

### Controllers
- **UserController:** Handles all HTTP requests related to user management
- **MusicController:** Handles all HTTP requests related to music catalog management

### Services
- **UserService:** Business logic for user operations
- **MusicService:** Business logic for music operations

### Repositories
- **UserRepository:** Data access layer for User entity (extends JpaRepository)
- **MusicRepository:** Data access layer for Music entity (extends JpaRepository)

### DTOs (Data Transfer Objects)
- **Request DTOs:** Used for incoming API requests
- **Response DTOs:** Used for outgoing API responses

### Exception Handling
- **GlobalExceptionHandler:** Centralized exception handling for the entire application
- **ResourceNotFoundException:** Thrown when a requested resource is not found

## Error Handling

The application implements global exception handling. Common error responses:

### 400 Bad Request
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request parameters"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

## Development

### Building the Project

```bash
mvn clean install
```

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run tests with coverage
mvn test jacoco:report
```

### Running with Development Tools

```bash
# Run with Spring Boot DevTools for auto-reload
mvn spring-boot:run
```

Changes to Java files will automatically recompile and restart the application.

## Troubleshooting

### PostgreSQL Connection Issues

If you encounter connection errors:

1. Verify PostgreSQL is running:
   ```bash
   # Windows (PowerShell)
   Get-Service | grep postgres
   
   # macOS/Linux
   sudo systemctl status postgresql
   ```

2. Check database exists:
   ```bash
   psql -U postgres -l | grep sonata
   ```

3. Verify environment variables:
   ```bash
   echo $env:USER_DB
   echo $env:PASSWORD_DB
   ```

### Port Already in Use

If port 8081 is already in use:

1. Find the process using the port:
   ```bash
   # Windows
   netstat -ano | findstr :8081
   
   # macOS/Linux
   lsof -i :8081
   ```

2. Change the port in `application.properties`:
   ```properties
   server.port=8082
   ```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Support

For issues or questions:
- Open an issue on GitHub
- Contact the maintainers

## Version

**Current Version:** 0.0.1-SNAPSHOT

**Last Updated:** 2024

---

**Built with ❤️ by [golfettozh](https://github.com/golfettozh)**
