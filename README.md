Banking Management System

A Java-based web application built using Servlets, JDBC, and MySQL, following a clean Layered Architecture (Controller → Service → DAO).

This project demonstrates secure authentication, loan management, transaction handling, pagination, exception handling, and clean backend design principles.

🚀 Features
👤 User Module

User Registration

Secure Login (BCrypt password hashing)

Role-based access (USER / ADMIN)

Input validation

🏦 Account Module

Create Account

View Account Details

Account Dashboard

💳 Transaction Module

Deposit

Withdraw

Transaction History

Pagination (page & size based)

📝 Loan Module

Apply for Loan

Loan Status Tracking

Admin Approval / Rejection

Loan Pagination

🛡 Security & Validation

BCrypt Password Encryption

PreparedStatement (SQL Injection prevention)

Custom Exception Handling

Input Validation Utility

Centralized JSON Response Handling

🏗️ Architecture
Client (Browser / Postman)
        ↓
Controller Layer (Servlets)
        ↓
Service Layer (Business Logic)
        ↓
DAO Layer (Database Operations)
        ↓
MySQL Database
📂 Project Structure
src/main/java
│
├── com.banking.controller
│   ├── AccountServlet.java
│   ├── ApproveLoanServlet.java
│   ├── DashboardServlet.java
│   ├── LoanServlet.java
│   ├── LoanStatusServlet.java
│   ├── LoginServlet.java
│   ├── RegisterServlet.java
│   ├── RejectLoanServlet.java
│   └── TransactionServlet.java
│
├── com.banking.service
│   ├── AccountService.java
│   ├── LoanService.java
│   ├── LoanApprovalService.java
│   ├── TransactionService.java
│   └── UserService.java
│
├── com.banking.dao
│   ├── AccountDAO.java
│   ├── LoanDAO.java
│   ├── TransactionDAO.java
│   └── UserDAO.java
│
├── com.banking.model
│   ├── Account.java
│   ├── Loan.java
│   ├── Transaction.java
│   └── User.java
│
├── com.banking.filter
│   └── (Authentication / Authorization Filters)
│
├── com.banking.exception
│   └── BankingException.java
│
├── com.banking.util
│   ├── DBConnection.java
│   ├── PasswordUtil.java
│   ├── ValidationUtil.java
│   ├── JSONResponse.java
│   ├── EmailUtil.java
│   └── TestConnection.java
🧠 Key Concepts Implemented

Layered Architecture

Separation of Concerns

Connection Pooling (HikariCP)

Pagination using LIMIT & OFFSET

Exception Handling using Custom Exception

Role-Based Authorization

Secure Password Storage (BCrypt)

Clean API JSON Response Structure

📌 Pagination Implementation

The application supports pagination using:

page = 1
size = 5

Formula used:

offset = (page - 1) * size

SQL Example:

SELECT * FROM transactions
LIMIT 5 OFFSET 0;

This improves performance when handling large transaction or loan records.

🛠️ Technologies Used
Technology	Version
Java	1.8
Apache Tomcat	9.0.x
Servlet API	4.0.1
MySQL	8.0.x
Maven	3.8+
HikariCP	5.0.1
Gson	2.10.1
jBCrypt	0.4
Git	2.x
⚙️ Setup Instructions
1️⃣ Clone Repository
git clone https://github.com/nisalya14/banking-app.git
2️⃣ Database Setup

Create database:

CREATE DATABASE banking_app;

Update DB credentials inside:

com.banking.util.DBConnection.java
3️⃣ Run Application

Import project as Maven Project in Eclipse

Configure Apache Tomcat 9

Deploy project

Run on:

http://localhost:8080/banking-app
🔐 Sample API Endpoints
Register
POST /register
Login
POST /login
Apply Loan
POST /loan
Approve Loan (Admin)
POST /approve-loan
Transaction History (With Pagination)
GET /transactions?page=1&size=5
🎯 Learning Outcomes

Through this project, I gained hands-on experience in:

Designing layered backend architecture

Implementing secure authentication

Building REST-style APIs using Servlets

Handling pagination for large datasets

Managing database connections efficiently

Applying clean coding practices

Using Git for version control

