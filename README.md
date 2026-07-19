# Sonata

A RESTful music management API built with Spring Boot. Sonata provides a complete backend solution for managing users and music catalogs with JWT-based authentication and role-based access control.

## Table of Contents

- [Overview](#overview)
- [Technologies](#technologies)
- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Authentication](#authentication)
- [API Endpoints](#api-endpoints)
- [Request/Response Examples](#requestresponse-examples)
- [Database](#database)
- [Project Structure](#project-structure)
- [Development](#development)

## Overview

Sonata is a production-ready music management platform that enables:
- User registration and JWT-based authentication
- Role-based access control (ROLE_USER, ROLE_ARTIST, ROLE_ADMIN)
- Complete user profile management
- Music catalog management with full CRUD operations
- Secure endpoints with Spring Security integration

## Technologies

- **Java 21** - Modern JVM language with latest features
- **Spring Boot 4.1.0** - Enterprise-grade web framework
- **Spring Security** - Comprehensive authentication and authorization framework
- **Spring Data JPA** - Object-relational mapping and data access
- **JWT (JJWT 0.12.6)** - Stateless authentication tokens
- **PostgreSQL** - Production database
- **H2 Database** - In-memory database for testing
- **Lombok** - Reduces boilerplate code
- **Maven** - Build and dependency management

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

### 2. Set Up PostgreSQL

Make sure PostgreSQL is installed and running on your system.

#### Windows (Chocolatey)
```powershell
choco install postgresql
```

#### macOS (Homebrew)
```bash
brew install postgresql@15
brew services start postgresql@15
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt-get install postgresql postgresql-contrib
sudo systemctl start postgresql
```

### 3. Create the Database

```bash
psql -U postgres
```

Then in the PostgreSQL prompt:

```sql
CREATE DATABASE sonata;
```

## Configuration

### Environment Variables

Create a `.env` file in the project root with the following variables:

```env
USER_DB=postgres
PASSWORD_DB=your_password
URL_DB=jdbc:postgresql://localhost:5432/sonata
API_SECURITY_TOKEN_SECRET=your_secret_jwt_key_here_make_it_long_and_random
```

#### Setting Environment Variables

**Windows (PowerShell):**
```powershell
$env:USER_DB = "postgres"
$env:PASSWORD_DB = "your_password"
$env:URL_DB = "jdbc:postgresql://localhost:5432/sonata"
$env:API_SECURITY_TOKEN_SECRET = "your_secret_jwt_key"
```

**macOS/Linux (Bash):**
```bash
export USER_DB=postgres
export PASSWORD_DB=your_password
export URL_DB=jdbc:postgresql://localhost:5432/sonata
export API_SECURITY_TOKEN_SECRET=your_secret_jwt_key
```

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

# Security Configuration
application.jwt.secret=change_this_secret_to_a_long_random_value
application.jwt.token.secret=${API_SECURITY_TOKEN_SECRET}

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

## Running the Application

### Using Maven

```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Using Java Directly

```bash
# After building
java -jar target/Sonata-0.0.1-SNAPSHOT.jar
```

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest
```

The application starts on `http://localhost:8081`

## Authentication

Sonata uses JWT (JSON Web Tokens) for stateless authentication. All protected endpoints require a valid JWT token in the `Authorization` header.

### User Roles

- **ROLE_USER** - Standard user with basic permissions
- **ROLE_ARTIST** - Artist account with extended permissions
- **ROLE_ADMIN** - Administrative account with full permissions

Users can have multiple roles simultaneously (e.g., ROLE_ADMIN includes ROLE_USER permissions).

## API Endpoints

The API is versioned at `/api/v1`. All endpoints expect and return JSON.

### Authentication Endpoints

#### Register User

**POST** `/api/v1/auth/register`

Create a new user account.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "securePassword123",
  "role": "ROLE_USER"
}
```

**Response:**
```
200 OK
```

**Status Codes:**
- `200 OK` - User registered successfully
- `400 BAD REQUEST` - Invalid request or email already in use

---

#### Login

**POST** `/api/v1/auth/login`

Authenticate and receive a JWT token.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "securePassword123"
}
```

**Response:**
```
200 OK
Header: Authorization: Bearer <jwt_token>
```

**Status Codes:**
- `200 OK` - Authentication successful
- `401 UNAUTHORIZED` - Invalid credentials

---

### User Endpoints

#### Get All Users

**GET** `/api/v1/users`

Retrieve all users. Requires authentication.

**Response:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "email": "user1@example.com"
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440002",
    "email": "user2@example.com"
  }
]
```

**Status Code:** `200 OK`

---

#### Get User by ID

**GET** `/api/v1/users/{id}`

Retrieve a specific user. Requires authentication.

**Path Parameters:**
- `id` (UUID, required) - User ID

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "email": "user@example.com"
}
```

**Status Codes:**
- `200 OK` - User found
- `404 NOT FOUND` - User not found
- `401 UNAUTHORIZED` - Invalid or missing token

---

#### Create User

**POST** `/api/v1/users`

Create a new user. Requires ROLE_ADMIN.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "securePassword123",
  "role": "ROLE_ARTIST"
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "email": "user@example.com"
}
```

**Status Codes:**
- `201 CREATED` - User created successfully
- `400 BAD REQUEST` - Validation error
- `409 CONFLICT` - Email already exists
- `403 FORBIDDEN` - Insufficient permissions

---

#### Delete User

**DELETE** `/api/v1/users/{id}`

Delete a user. Requires ROLE_ADMIN.

**Path Parameters:**
- `id` (UUID, required) - User ID to delete

**Response:**
```
204 NO CONTENT
```

**Status Codes:**
- `204 NO CONTENT` - User deleted successfully
- `404 NOT FOUND` - User not found
- `403 FORBIDDEN` - Insufficient permissions

---

### Music Endpoints

#### Get All Music

**GET** `/api/v1/musics`

Retrieve all music records. Requires authentication.

**Response:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "title": "Bohemian Rhapsody",
    "artist": "Queen",
    "durationInMinutes": 5.55
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440002",
    "title": "Imagine",
    "artist": "John Lennon",
    "durationInMinutes": 3.03
  }
]
```

**Status Code:** `200 OK`

---

#### Get Music by ID

**GET** `/api/v1/musics/{id}`

Retrieve a specific music record. Requires authentication.

**Path Parameters:**
- `id` (UUID, required) - Music ID

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Status Codes:**
- `200 OK` - Music found
- `404 NOT FOUND` - Music not found

---

#### Create Music

**POST** `/api/v1/musics`

Add a new music record. Requires ROLE_ARTIST or ROLE_ADMIN.

**Request Body:**
```json
{
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Status Codes:**
- `201 CREATED` - Music created successfully
- `400 BAD REQUEST` - Validation error
- `403 FORBIDDEN` - Insufficient permissions

---

#### Delete Music

**DELETE** `/api/v1/musics/{id}`

Delete a music record. Requires ROLE_ARTIST or ROLE_ADMIN.

**Path Parameters:**
- `id` (UUID, required) - Music ID to delete

**Response:**
```
204 NO CONTENT
```

**Status Codes:**
- `204 NO CONTENT` - Music deleted successfully
- `404 NOT FOUND` - Music not found
- `403 FORBIDDEN` - Insufficient permissions

---

## Request/Response Examples

### Using cURL

#### Register User

```bash
curl -X POST http://localhost:8081/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "securePassword123",
    "role": "ROLE_USER"
  }'
```

#### Login

```bash
curl -X POST http://localhost:8081/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

#### Get All Users (with JWT token)

```bash
curl -X GET http://localhost:8081/api/v1/users \
  -H "Authorization: Bearer your_jwt_token_here" \
  -H "Accept: application/json"
```

#### Create Music

```bash
curl -X POST http://localhost:8081/api/v1/musics \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer your_jwt_token_here" \
  -d '{
    "title": "Bohemian Rhapsody",
    "artist": "Queen",
    "durationInMinutes": 5.55
  }'
```

#### Get All Music

```bash
curl -X GET http://localhost:8081/api/v1/musics \
  -H "Authorization: Bearer your_jwt_token_here" \
  -H "Accept: application/json"
```

#### Delete Music

```bash
curl -X DELETE http://localhost:8081/api/v1/musics/550e8400-e29b-41d4-a716-446655440001 \
  -H "Authorization: Bearer your_jwt_token_here"
```

### Using Postman

1. Create a collection named `Sonata API`
2. Set up environment variables:
   - `base_url`: http://localhost:8081/api/v1
   - `token`: (will be populated after login)

3. Create requests:

**Register**
- Method: `POST`
- URL: `{{base_url}}/auth/register`
- Body:
  ```json
  {
    "email": "user@example.com",
    "password": "password123",
    "role": "ROLE_USER"
  }
  ```

**Login**
- Method: `POST`
- URL: `{{base_url}}/auth/login`
- Body:
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }
  ```
- In the test section, add:
  ```javascript
  var jsonData = pm.response.json();
  pm.environment.set("token", jsonData.token);
  ```

**Get All Users**
- Method: `GET`
- URL: `{{base_url}}/users`
- Headers: `Authorization: Bearer {{token}}`

**Get All Music**
- Method: `GET`
- URL: `{{base_url}}/musics`
- Headers: `Authorization: Bearer {{token}}`

## Database

### Database Models

#### Users Table

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | UUID | PRIMARY KEY | Unique user identifier |
| email | VARCHAR | NOT NULL, UNIQUE | User's email address |
| password | VARCHAR | NOT NULL | Encrypted password |
| role | VARCHAR | NOT NULL | User role (ROLE_USER, ROLE_ARTIST, ROLE_ADMIN) |

#### Musics Table

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | UUID | PRIMARY KEY | Unique music identifier |
| title | VARCHAR | NOT NULL | Song title |
| artist | VARCHAR | NOT NULL | Artist name |
| durationInMinutes | DOUBLE | NOT NULL | Duration in minutes |

### Database Features

- **Auto-DDL:** Hibernate configured with `ddl-auto=update`, automatically manages schema
- **Connection Pooling:** HikariCP configured by Spring Boot
- **Transaction Management:** All operations transaction-managed by Spring
- **UUID Generation:** Automatic UUID generation for all IDs

## Project Structure

```
Sonata/
├── src/
│   ├── main/
│   │   ├── java/com/golfettozh/sonata/
│   │   │   ├── controller/
│   │   │   │   ├── AuthenticationController.java
│   │   │   │   ├── UserController.java
│   │   │   │   └── MusicController.java
│   │   │   ├── service/
│   │   │   │   ├── UserService.java
│   │   │   │   ├── MusicService.java
│   │   │   │   └── AuthorizationService.java
│   │   │   ├── model/
│   │   │   │   ├── user/
│   │   │   │   │   ├── User.java
│   │   │   │   │   └── UserRole.java
│   │   │   │   └── music/
│   │   │   │       └── Music.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── MusicRepository.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   │   ├── AuthenticationRequestDTO.java
│   │   │   │   │   ├── RegisterRequestDTO.java
│   │   │   │   │   ├── UserRequestDTO.java
│   │   │   │   │   └── MusicRequestDTO.java
│   │   │   │   └── response/
│   │   │   │       ├── UserResponseDTO.java
│   │   │   │       └── MusicResponseDTO.java
│   │   │   ├── infra/
│   │   │   │   └── security/
│   │   │   │       └── SecurityConfiguration.java
│   │   │   ├── exception/
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   └── SonataApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-test.properties
│   └── test/
│       ├── java/com/golfettozh/sonata/
│       │   └── ...
│       └── resources/
│           └── application-test.properties
├── .env
├── pom.xml
└── README.md
```

### Component Overview

- **Controllers** - REST endpoints and request routing
- **Services** - Business logic and domain operations
- **Repositories** - Data access layer (JPA)
- **DTOs** - Data transfer objects for API contracts
- **Models** - Entity classes representing domain objects
- **Security** - JWT and authentication configuration
- **Exception Handling** - Centralized error handling

## Development

### Building the Project

```bash
mvn clean install
```

### Running Tests

```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=UserServiceTest

# With coverage report
mvn test jacoco:report
```

### Development Tools

The project includes Spring Boot DevTools for automatic reload during development.

```bash
mvn spring-boot:run
```

Any changes to Java files will automatically recompile and restart the application.

## Troubleshooting

### PostgreSQL Connection Issues

**Check if PostgreSQL is running:**
```bash
# Windows
Get-Service | grep postgres

# macOS/Linux
sudo systemctl status postgresql
```

**Verify database exists:**
```bash
psql -U postgres -l | grep sonata
```

**Test connection:**
```bash
psql -U postgres -d sonata -c "SELECT 1"
```

### Port Already in Use

If port 8081 is already in use:

```bash
# Find process using the port
# Windows
netstat -ano | findstr :8081

# macOS/Linux
lsof -i :8081
```

Change the port in `application.properties`:
```properties
server.port=8082
```

### JWT Token Issues

- Ensure `API_SECURITY_TOKEN_SECRET` environment variable is set
- Token format in headers: `Authorization: Bearer <token>`
- Check token expiration time in logs

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/my-feature`)
3. Commit your changes (`git commit -m 'Add my feature'`)
4. Push to the branch (`git push origin feature/my-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues or questions:
- Open an issue on GitHub
- Check existing documentation and examples

## Version

**Current Version:** 0.0.1-SNAPSHOT

**Last Updated:** 2025

---

**Made with ❤️ by [golfettozh](https://github.com/golfettozh)**
