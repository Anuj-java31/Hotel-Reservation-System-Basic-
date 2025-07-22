# 🏨 Hotel Reservation System (JDBC + MySQL)

This is a simple **Java console application** that interacts with a **MySQL database** using JDBC to perform CRUD operations on hotel reservations.

## 💡 Features

- Make a new reservation
- View all reservations
- Fetch room number by reservation ID
- Update an existing reservation
- Delete a reservation
- Exit the application

## 🛠️ Technologies Used

- Java 21
- JDBC (MySQL Connector/J 9.3.0)
- MySQL Database
- IntelliJ IDEA (for development)

## 📦 Database Setup

1. **Create the database and table:**

```sql
CREATE DATABASE hotel;

USE hotel;

CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    room_no INT,
    contact_no VARCHAR(15)
);
