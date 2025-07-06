# 🏨 Hotel Booking API (MVP)

A RESTful hotel booking application built with **Spring Boot 2.7.18** and **PostgreSQL**, supporting hotel search and booking operations. The app is instrumented with analytics logging and Swagger documentation.

---

## 🚀 Features

- ✅ Search hotels by location
- ✅ Create, update, view, and cancel bookings
- ✅ Analytics logging for all major actions
- ✅ Custom exception handling with global error responses
- ✅ Swagger UI integration for API documentation
- ✅ PostgreSQL persistence with JPA
- ✅ Compatible with Java 11 and Spring Boot 2.7.18

---

## 🧰 Tech Stack

| Layer         | Technology           |
|---------------|----------------------|
| Backend       | Spring Boot 2.7.18   |
| Language      | Java 11              |
| ORM           | Spring Data JPA      |
| Database      | PostgreSQL           |
| API Docs      | Springdoc OpenAPI 1.6.x |
| Logging       | SLF4J + AnalyticsLogger |
| Build Tool    | Maven                |

---

## 📁 Project Structure

src/
├── controller/ → REST Controllers
├── service/ → Business logic
├── repository/ → JPA Repositories
├── model/ → Entities (Hotel, Booking)
├── dto/ → DTOs (BookingRequest, BookingResponse)
├── exception/ → Custom exceptions & global handler
├── analytics/ → AnalyticsLogger
├── constants/ → Event constants
└── resources/
├── application.properties
└── data.sql → Sample data


---

## ⚙️ Setup Instructions

### 1. Configure PostgreSQL

Run the following SQL command to create a database:

```sql
CREATE DATABASE hotel_db;
```

### 2. Update application.properties
In src/main/resources/application-dev.yml, update the configuration:

spring:
    datasource:
        url: jdbc:postgresql://localhost:5432/hotel_booking
        username: your_db_user
        password: your_db_password
    jpa:
        hibernate:
            ddl-auto: none
        show-sql: true
    sql:
        init:
            mode: always

### 3. Run the Application

Use Maven to build and run the project:

mvn spring-boot:run


