# 🏨 Hotel Management System – Distributed Application (Java + Maven)

This is a Java-based hotel management system developed as a course project for Distributed Systems. The system supports managing bookings, customers, employees, services, and invoices. It follows a layered architecture with potential for distributed deployment using Java RMI, sockets, or web services.

---

## 📌 Project Overview

The application is designed to handle core hotel operations such as room reservations, customer management, employee records, service tracking, and billing. It uses a **DAO + Entity** architecture and is structured for future enhancement into a distributed system.

---

## 🧰 Tech Stack

- **Java SE 8+**
- **Maven** (project management via `pom.xml`)
- **Swing / JavaFX** (UI, if implemented)
- **JDBC** or other persistence libraries
- **SQL Server / MySQL** (based on `SQL/` scripts)
- Optional: **RMI**, **Sockets**, or **Web Services** for distributed communication

---

## ✨ Key Features

- 🛏 Room and booking management
- 👤 Customer and employee database
- 🧾 Bill creation and invoice details
- 🧹 Service room tracking
- 📑 Modular DAO and Entity design
- 🖥️ Potential client-server distribution via RMI or sockets

---

## 🗂 Project Structure

```

LapTrinhPhanTan\_DoAn\_2023-2024/
├── src/
│   ├── dao/           # Database access logic
│   ├── entity/        # Data models (Room, Customer, Booking, etc.)
│   ├── server/        # Business logic and controllers
│   └── ui/            # UI components (if available)
├── lib/               # External JAR libraries
├── SQL/               # SQL scripts for database schema and sample data
├── document/          # Project documentation (PDF/Word)
├── pom.xml            # Maven configuration

````

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 8 or higher
- Maven
- SQL Server or MySQL
- (Optional) IDE like IntelliJ IDEA or Eclipse

### Build and Run

```bash
mvn clean install
mvn exec:java
````

> Make sure to set up the database using scripts in the `SQL/` folder before running the application.

---

## 📄 Documentation

You can find user manuals, technical documentation, and usage guides inside the `document/` folder.

---

## 👥 Authors

Developed by university students for the Distributed Systems course (2023–2024 academic year).

---

## 📝 License

This project is for academic and educational purposes only. Not licensed for commercial use.

