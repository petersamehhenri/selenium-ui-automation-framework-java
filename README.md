# selenium-ui-automation-framework-java
Selenium UI Automation Framework using Java, TestNG, and Page Object Model (POM) to automate end-to-end testing of an e-commerce web application (Automation Exercise). Covers login, registration, product search, cart, checkout, payment, and invoice flow with Allure reporting and reusable components.

---

# 📌 Selenium UI Automation Framework

## 🧪 Overview

This project is a **Selenium WebDriver + TestNG automation framework** built in **Java**, following the **Page Object Model (POM)** design pattern.

The framework is used to automate the demo e-commerce application:

👉 [https://automationexercise.com](https://automationexercise.com)

It is designed to be:

* Scalable
* Maintainable
* Reusable
* Easy to extend
* Built using industry best practices

---

## 🛠️ Tech Stack

* Java
* Selenium WebDriver
* TestNG
* Maven
* Page Object Model (POM)
* Allure Reports
* Git

---

## 📁 Project Structure

```
src
 ├── main
 │   ├── java
 │   │   ├── Pages          → Page Object classes (POM)
 │   │   ├── Drivers        → WebDriver management
 │   │   ├── Utils          → Utilities (Readers, Logs, Helpers)
 │   │   └── Components     → Reusable UI components
 │
 └── test
     ├── java
     │   └── Tests          → Test classes (Login, Cart, Payment, etc.)
     └── resources
         └── testng.xml     → Test suite configuration
```

---

## ⚙️ Key Framework Features

### 🏗️ Page Object Model (POM)

Each page is represented as a separate class containing:

* Locators
* Actions
* Validations

---

### 🔄 End-to-End Test Coverage

The framework covers complete user flows:

* User registration
* Login
* Product search & product details validation
* Add to cart (with & without login)
* Checkout process
* Payment processing
* Invoice download
* Account deletion

---

### ⚙️ Reusable & Scalable Design

* Centralized WebDriver handling
* Reusable components (Navigation Bar, etc.)
* Clean separation of concerns

---

### 📊 Allure Reporting

* Detailed execution reports
* Step-by-step test logs using `@Step`
* Clear failure tracking

---

### 🔍 Smart Validations

* Custom validation layer
* Clear assertion messages
* Improved debugging support

---

### 🚫 No Hardcoded Data

* Configurations managed externally
* Property-based data handling

---

## 🧪 Test Coverage

### 1️⃣ Authentication Tests

* User registration
* Login with valid credentials
* Login with invalid credentials
* Duplicate account prevention

---

### 2️⃣ Product Tests

* Search for products
* Validate product details
* View product details page
* Add product to cart

---

### 3️⃣ Cart Tests

* Add product to cart (logged / guest user)
* Verify product details in cart
* Remove product from cart

---

### 4️⃣ Checkout & Payment Tests

* Complete checkout flow
* Place order
* Enter payment details
* Download invoice

---

### 5️⃣ Account Management

* Delete account
* Validate account deletion flow

---

## ▶️ How to Run the Tests

### 📌 Prerequisites

* Java 11+
* Maven
* Chrome / Edge / FireFox / Safari Browser

---

### 🚀 Run via Maven

```bash
mvn clean test
```

---

### 🚀 Run via TestNG

Execute:

```
src/test/resources/testng.xml
```

---

## 📸 Reporting

After execution:

* Allure report is generated
* Step-by-step execution details available
* Failures include screenshots, clear logs and traceability

---

## 🚀 Best Practices Followed

* Page Object Model (POM)
* No test dependencies
* Reusable components
* Clean code structure
* Maintainable architecture
* Strong assertions with messages
* Centralized driver management

---

## 📤 Project Purpose

This framework demonstrates:

* Real-world automation architecture
* Scalable test design
* Industry best practices
* End-to-end UI testing capability
* Maintainable automation solution
