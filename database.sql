CREATE DATABASE library_db;

USE library_db;

CREATE TABLE books(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    author VARCHAR(100),
    quantity INT
);

CREATE TABLE students(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100)
);

CREATE TABLE issue_book(
    issue_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    book_id INT,
    issue_date DATE,
    return_date DATE,
    fine INT
);