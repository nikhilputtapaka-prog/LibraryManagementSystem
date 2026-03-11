# 📚 Library Management System (Java + JDBC)

## 📌 Project Description
This is a console-based Library Management System developed using Core Java and JDBC.  
The system allows the admin to manage books, register students, issue and return books, and calculate fines.

## 🚀 Features
- Add Book
- View Books
- Register Student
- Issue Book
- Return Book
- Fine Calculation

## 🛠️ Technologies Used
- Core Java
- JDBC
- MySQL

## 🗄️ Database Setup
Run the `database.sql` file in MySQL to create required tables.
Add your my sql username and password

## ▶️ How to Run
1. Download MySQL Connector/J and place inside `lib` folder.
2. Compile: javac -cp ".;lib/mysql-connector-j.jar" src/*.java
3. Run: java -cp ".;lib/mysql-connector-j.jar;src" LibraryManagementSystem
