# CourseMedia - Digital Course Marketplace

CourseMedia is a backend-only Digital Course Marketplace developed using Kotlin and Spring Boot. This application allows creators to publish courses, customers to purchase them, and admins to manage the platform. The system includes role-based access control, JWT authentication, and robust purchase tracking.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)
- [License](#license)

## Introduction

CourseMedia is designed to facilitate online learning by enabling course creators to publish content and customers to enroll in courses. The platform includes purchase tracking, revenue calculation, and user management, ensuring a seamless experience for all stakeholders.

## Features

### User Management
- User signup/login with role-based access control (Admin, Creator, Customer)
- JWT authentication for secure API access

### Course Management
- Creators can create, update, and delete courses
- Customers can view and purchase courses


### Purchase
- Customers can purchase courses
- Purchase history tracking


### Statistics & Reporting
- Admins can view platform-wide purchase statistics
- Creators can track their own course sales and revenue

## Technologies Used

- **Kotlin**: Main programming language
- **Spring Boot**: Backend framework
- **Spring Security & JWT**: Secure authentication and authorization
- **MYSQL**: database for development
- **JPA/Hibernate**: ORM for database interactions
- **Docker**: Containerization for deployment

## Getting Started

### Prerequisites
- **Java 21 or higher**
- **Maven**
- **Docker (optional)**

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/suhanmhd/CourseMedia.git
   cd CourseMedia
   ```

2. **Configure the database**
   Modify `src/main/resources/application.yml` to configure the database:

   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Run with Docker** (optional)
   ```bash
   docker build -t course-media .
   docker run -p 9090:9090 course-media
   ```
## Database Schema

### **Users Table**
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('CREATOR', 'CUSTOMER', 'ADMIN') NOT NULL
);
```

### **Courses Table**
```sql
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    creator_id BIGINT NOT NULL,
    FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE
);
```

### **Purchases Table**
```sql
CREATE TABLE purchases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status ENUM('COMPLETED', 'REFUNDED', 'CANCELED') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);
```

## API Endpoints
For detailed API documentation, please refer to the Swagger documentation linked below.

## Swagger Collection
The API endpoints are documented using Swagger ui, After Running the Project You can access the Swagger UI at:

[http://localhost:9090/swagger-ui/index.html](https://taskstream.mediconnects.online/swagger-ui/index.html#/)


This project is licensed under the MIT License. See the `LICENSE` file for details.

