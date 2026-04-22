# ☕ Blockchain Café

A full-stack prototype demonstrating how blockchain can enhance **tax transparency**, **VAT tracking**, and **financial auditability** in a real-world café environment.

---

## 🚀 Overview

Blockchain Café is a microservices-based system where:

- Customers place orders
- Payments are processed
- VAT is automatically calculated
- Each transaction is recorded on a **private blockchain**
- Data becomes **immutable, auditable, and transparent**

---

## 🎯 Problem

In systems like Germany’s tax ecosystem:

- VAT fraud is common
- Reporting is delayed (monthly)
- Transactions are opaque
- Audits are manual and expensive

---

## 💡 Solution

We introduce a **blockchain-backed transaction layer** where:

- Every order = blockchain block
- VAT is calculated in real-time
- Payments are traceable
- Data cannot be altered

---

## 🧱 Architecture

### Microservices

- **Menu Service** (Port 8082)
- **Order Service** (Port 8083)
- **Payment Service** (Port 8084)
- **Wallet Service** (Port 8085)
- **VAT Service** (Port 8086)
- **Blockchain Service** (Port 8087)
- **MySQL Database**

---

## 🔗 Core Features

### 🧾 Invoice System
- Invoice generation per order
- Business vs Private checkout
- VAT ID support
- Downloadable PDF invoices
- Invoice history with:
  - pagination
  - filtering
  - search
  - sorting by date

---

### 💰 VAT Engine
- Automatic VAT calculation
- Supports:
  - 19% VAT
  - 7% VAT
- Splits:
  - Gross
  - Net
  - VAT

---

### 📊 Tax Analytics Dashboard
- Total revenue (gross / net / VAT)
- VAT breakdown (19% vs 7%)
- Private vs Business VAT
- Time-based chart visualization

---

### 🔗 Blockchain Layer

Each order is stored as a **block**:

- blockIndex
- orderId
- paymentId
- totals (gross, net, VAT)
- previousHash
- hash
- timestamp

---

## 🔍 Blockchain Validation

Advanced validation ensures:

- Hash integrity (stored vs recalculated)
- Chain linkage (previousHash correctness)
- Full chain verification

Endpoint:
```bash
GET /api/blockchain/validate/details
Example response:
{
  "valid": true,
  "chainSize": 5
}

🔎 Blockchain Explorer

Endpoints:GET /api/blockchain/overview
GET /api/blockchain/blocks
GET /api/blockchain/blocks/latest
GET /api/blockchain/blocks/order/{orderId}
GET /api/blockchain/validate/details

Frontend includes:

* Latest blocks view
* Linked block per order
* Hash + previousHash visualization

🎨 Frontend

* Pure HTML/CSS/JS (no framework)
* Apple-inspired clean UI
* Features:
    * Menu
    * Cart
    * Checkout (Private / Business)
    * Invoice history dashboard
    * Tax analytics charts
    * Blockchain explorer integration

⸻

🐳 Running the Project

1. Build services
mvn clean package
2. Start with Docker
docker compose up --build

🧪 Testing Flow

1. Open frontend:
http://localhost

2. Add products to cart
3. Checkout (private or business)
4. Verify:

* Invoice generated
* VAT calculated
* Payment processed
* Block created
* Blockchain validation = true

⸻

🛠 Tech Stack

* Java 17
* Spring Boot
* Maven (multi-module)
* MySQL
* Docker
* REST APIs
* Vanilla JS frontend

⸻

🔐 Important Note

This is a prototype system:

* No real integration with German Finanzamt
* Designed for demonstration purposes
* Focused on transparency and auditability concepts

⸻

📈 Future Improvements

* Multi-tenant cafés (SaaS model)
* Authentication (JWT)
* NFC payment integration
* Smart contract layer
* Blockchain node distribution
* Real tax authority integration

⸻

## 👨‍💻 Author

**Yazan Al Hussein**

Fintech & Blockchain Engineer  
Focused on building transparent financial systems using distributed technologies.

This project was developed as a practical demonstration of:

- Blockchain-based transaction integrity
- VAT automation & auditability (German tax context)
- Scalable microservices architecture

🔗 GitHub: https://github.com/yazangit

⸻

⭐ Key Takeaway

Blockchain Café shows how:

“Every transaction can become verifiable, immutable, and transparent — reducing fraud and increasing trust in financial systems.”
