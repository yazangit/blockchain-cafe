# ☕ Blockchain Café — VAT Transparency System

## 📌 Overview

**Blockchain Café** is a full-stack prototype demonstrating how blockchain can improve **VAT transparency** in a café environment.

The system:

- Processes orders
- Calculates VAT automatically (German model)
- Splits funds (merchant vs tax)
- Records each transaction on a blockchain
- Runs as **microservices inside Docker**

---

## 🚨 Problem

Current VAT systems:

- Reported **monthly (not real-time)**
- Prone to **fraud and manipulation**
- Lack **transparency and auditability**

---

## 💡 Solution

A real-time system that:

- Calculates VAT instantly
- Separates funds automatically
- Stores transactions immutably on a blockchain

---
## 🏗️ Architecture

Frontend (HTML / JS)
        │
        ▼
Order Service (8083)
   ├── Menu Service (8082)
   ├── Payment Service (8084)
   ├── VAT Service (8086)
   ├── Wallet Service (8085)
   └── Blockchain Service (8087)

---

## ⚙️ Tech Stack

- Java 17 + Spring Boot
- Microservices architecture
- MySQL
- REST APIs
- Docker + Docker Compose
- HTML + CSS + JavaScript (Frontend)

---

## 🚀 Run the Project

### 1) Build

mvn clean package -DskipTests

### 2) Start Docker

docker compose up --build

---

## 🌐 Access

Frontend:

http://127.0.0.1:5501/frontend/index.html

---

## 🧪 Demo Flow

1. Add items
2. Click PAY
3. System processes:
   - Order
   - Payment
   - VAT
   - Wallet split
   - Blockchain record

---

## 📊 Example Output

- Total: €8.50
- Net: €7.71
- VAT: €0.79

Wallets:

- Merchant → €7.71
- VAT → €0.79

Blockchain:

- Transaction recorded ✔
- Chain valid ✔

---

## 🔗 API Endpoints

| Service    | Endpoint                    |
| ---------- | --------------------------- |
| Menu       | /api/products               |
| Order      | /api/orders/checkout        |
| Payment    | /api/payments/by-order/{id} |
| VAT        | /api/vat/records/{id}       |
| Wallet     | /api/wallets                |
| Blockchain | /api/blockchain/validate    |

---

## ⚠️ Disclaimer

This is a prototype:

- No real crypto payments
- No real Finanzamt integration
- Demonstration purposes only

---

## 📈 Future Improvements

- Real blockchain (Ethereum / Hyperledger)
- Authentication (JWT)
- Kafka event-driven architecture
- Cloud deployment
- UI dashboard

---

## 👨‍💻 Author

Yazan Al Hussein
