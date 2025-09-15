# COE528 – Bookstore Management Application

**Toronto Metropolitan University**  
**Course:** COE528 – Object-Oriented Analysis and Design 
**Project Duration:** 4 Weeks  
**Group Project**

---

## 📌 Project Overview

This project involves the analysis, design, and implementation of a **GUI-based Bookstore Application** using **JavaFX** and object-oriented principles. The application supports multiple user roles and demonstrates the use of design patterns and software architecture practices learned in class.

---

## 🎯 Objectives

- Design and implement using the **State Design Pattern**
- Build a GUI-based application with **JavaFX**
- Persist user and book data using file I/O
- Demonstrate group collaboration and individual accountability

---

## 🛠 Technologies

- Java (JavaFX)
- NetBeans IDE (No Maven)
- Visual Paradigm for UML diagrams
- JavaFX `TableView` for GUI tables
- `books.txt` and `customers.txt` for data persistence

---

## 👤 Application Roles

### 🧑‍💼 **Owner**
- Can add/delete **books** and **customers**
- Views and manages all records
- Accesses an admin panel after logging in with credentials:  
  `username: admin`, `password: admin`

### 🛍️ **Customer**
- Logs in with their own credentials
- Can browse books, select and purchase using either:
  - Regular buy
  - Redeem points and buy
- Accrues points (10 points per CAD spent)
- Redeems points (100 points = 1 CAD off)
- Status changes dynamically between **Silver** and **Gold** based on total points

### 🛍️ **Use Case Diagram**
<img width="1494" height="874" alt="image" src="https://github.com/user-attachments/assets/4ca249ac-2351-4998-bdf1-2fd855e2c116" />


---

## 💡 Key Features

- **Single-window GUI**: Interface updates without spawning multiple windows
- **Persistent Storage**: 
  - On exit, the app saves data to `books.txt` and `customers.txt`
  - On launch, it loads data from these files
- **Customer Rewards System**:
  - Gold status for customers with ≥1000 points
  - Silver status for <1000 points
- **State Design Pattern** used to manage customer states (Gold/Silver) and behaviors

---

## 📁 Project Deliverables

Each phase includes structured deliverables:

- `useCaseDiagram.pdf`: Use Case diagram showing system interactions
- `classDiagram.pdf`: Class diagram showing system structure
- `report.pdf`: Use-case narrative and rationale for using the State Design Pattern
- `NetBeans project folder`: Full implementation of the bookstore app
- `books.txt` and `customers.txt`: Runtime-generated data files

---

## 🧪 Example Scenarios

1. **Customer earns points and achieves Gold status:**
   - Buys books worth $700 → earns 7000 points → Status: Gold
2. **Customer redeems points:**
   - Buys a $50 book using 5000 points → Pays $0 → Points drop accordingly
3. **Customer status downgrade:**
   - Redeems below 1000 points → Status changes to Silver

---

## 📷 Bookstore GUI Application Screenshots

**Login Screen**
![image](https://github.com/user-attachments/assets/cc650b33-3053-4fdc-aa71-7b36d64738ea)

**Owner Screen**
![image](https://github.com/user-attachments/assets/83640108-3e48-4fa5-9b4c-07903f120c7e)

**Owner Manage Books Screen**
![image](https://github.com/user-attachments/assets/d2ad2e81-e69d-4cfe-8f81-3163f28e1f9d)

**Owner Add Books Screen**
![image](https://github.com/user-attachments/assets/72bb8e43-5a50-4cdd-bb77-ce241fa06b99)

**Owner Remove Books Screen**
![image](https://github.com/user-attachments/assets/ab55d6e0-df0c-4e68-ab33-7194eb020636)

**Owner Remove Customer Screen**
![image](https://github.com/user-attachments/assets/ed825fdd-85e3-40af-aad2-4136ab574625)

**Customer Main Screen**
![image](https://github.com/user-attachments/assets/7ec14c27-c553-4b38-a5cd-a8c7aebc4867)

**Customer Purchase Book Screen**
![image](https://github.com/user-attachments/assets/ecc81dc0-e5e6-4c93-8006-8ecaa81ab5bb)













