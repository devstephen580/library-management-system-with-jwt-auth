# 🎬 Movie Booking Web Application

A RESTful backend API built with **Spring Boot** for managing movie bookings, show scheduling, theatre management, and seat reservations — with JWT-based authentication and role-based access control.

---

## 🚀 Features

- 🔐 **JWT Authentication** – Secure login and token-based session management for users and admins
- 👥 **Role-Based Authorization** – Separate access control for `ADMIN` and `USER` roles
- 🎥 **Movie Management** – Add, update, delete, and search movies by title, genre, or language
- 🎭 **Theatre Management** – Manage theatres and search by location
- 📅 **Show Scheduling** – Create and manage shows linked to movies and theatres
- 🎟️ **Booking System** – Create, confirm, cancel, and filter bookings by status

---

## 🛠️ Tech Stack

| Technology       | Purpose                         |
|------------------|---------------------------------|
| Spring Boot      | Core framework                  |
| Spring Security  | Authentication & Authorization  |
| JWT              | Token-based auth                |
| MySQL            | Relational database             |
| Spring Data JPA  | ORM & database interaction      |
| Maven            | Dependency management           |

---

## ⚙️ Setup & Installation

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8.0+

### 1. Clone the Repository

```bash
git clone https://github.com/devstephen580/library-booking-system.git
cd Movie-Booking-System
```

### 2. Configure the Database

Create a MySQL database:

```sql
CREATE DATABASE moviedb;
```

Update `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/moviedb
    username: your_mysql_username
    password: your_mysql_password

jwt:
  secret: your_jwt_secret_key
  expiration: 600000
```

### 3. Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

The server will start at `http://localhost:8080`

---

## 📡 API Endpoints

### 🔐 User Auth

| Method | Endpoint                  | Description            | Access |
|--------|---------------------------|------------------------|--------|
| POST   | `/api/user/registeruser`  | Register a new user    | Public |
| POST   | `/api/user/loginuser`     | Login and receive JWT  | Public |

### 🔐 Admin Auth

| Method | Endpoint                    | Description             | Access     |
|--------|-----------------------------|-------------------------|------------|
| POST   | `/api/admin/registeradmin`  | Register a new admin    | Admin only |
| POST   | `/api/admin/loginadmin`     | Admin login and JWT     | Admin only |

### 🎥 Movies

| Method | Endpoint                                      | Description               | Access     |
|--------|-----------------------------------------------|---------------------------|------------|
| GET    | `/api/movies/getallmovies`                    | Get all movies            | Public     |
| GET    | `/api/movies/getmoviesbytitle?title=`         | Search movie by title     | Public     |
| GET    | `/api/movies/getmoviebygenre?genre=`          | Filter movies by genre    | Public     |
| GET    | `/api/movies/getmoviesbylanguage?language=`   | Filter movies by language | Public     |
| POST   | `/api/movies/add-movie`                       | Add a new movie           | Admin only |
| PUT    | `/api/movies/update/{id}`                     | Update a movie            | Admin only |
| DELETE | `/api/movies/delete/{id}`                     | Delete a movie            | Admin only |

### 🎭 Theatres

| Method | Endpoint                                       | Description                 | Access     |
|--------|------------------------------------------------|-----------------------------|------------|
| POST   | `/api/theatre/addtheatre`                      | Add a new theatre           | Admin only |
| GET    | `/api/theatre/addtheatrebylocation?location=`  | Search theatres by location | Public     |
| PUT    | `/api/theatre/updatetheatre/{id}`              | Update theatre details      | Admin only |
| DELETE | `/api/theatre/deletetheatre/{id}`              | Delete a theatre            | Admin only |

### 📅 Shows

| Method | Endpoint                                    | Description             | Access     |
|--------|---------------------------------------------|-------------------------|------------|
| POST   | `/api/shows/createshow`                     | Create a new show       | Admin only |
| PUT    | `/api/shows/updateshow?id=`                 | Update a show           | Admin only |
| GET    | `/api/shows/getallshows`                    | Get all shows           | Public     |
| GET    | `/api/shows/getshowsbymovie/{movieId}`      | Get shows by movie      | Public     |
| GET    | `/api/shows/getshowsbytheatre/{theatreId}`  | Get shows by theatre    | Public     |
| DELETE | `/api/shows/deleteshow/{id}`                | Delete a show           | Admin only |

### 🎟️ Bookings

| Method | Endpoint                                          | Description                   | Access |
|--------|---------------------------------------------------|-------------------------------|--------|
| POST   | `/api/booking/createbooking`                      | Create a new booking          | User   |
| GET    | `/api/booking/getshowbookings/{showId}`           | Get all bookings for a show   | User   |
| PUT    | `/api/booking/{showId}`                           | Confirm a booking             | User   |
| PUT    | `/api/booking/{showId}/confirmpayment`            | Cancel a booking              | User   |
| GET    | `/api/booking/getbookingbystatus/{bookingStatus}` | Get booking by status         | User   |

---

## 🔑 Authentication

All protected endpoints require a JWT token in the request header:

```
Authorization: Bearer <token>
```

To get a token, call `POST /api/user/loginuser` or `POST /api/admin/loginadmin` with your credentials.

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/devstephen/Movie/Booking/Web/Application/
│   │   ├── controller/         # REST controllers
│   │   ├── servies/            # Business logic
│   │   ├── repos/              # JPA repositories
│   │   ├── entities/           # Entity classes
│   │   ├── dto/                # Data Transfer Objects
│   │   ├── jwt/                # JWT filter
│   │   ├── security/           # Spring Security config
│   │   └── bookingstatus/      # Booking status enum
│   └── resources/
│       └── application.yml
```

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
