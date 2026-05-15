# ☕ Blockchain Café

> Enterprise-inspired blockchain payment ecosystem prototype for VAT transparency, invoice traceability, blockchain auditability, and fintech-oriented transaction verification.

![Main Checkout](./screenshots/home.png)

---

# Overview

Blockchain Café is a full-stack fintech-inspired prototype designed to demonstrate how blockchain technology can improve transaction transparency, VAT handling, invoice traceability, and auditability for businesses and tax authorities.

The platform simulates a modern café payment ecosystem where every transaction is processed, validated, VAT-calculated, and permanently recorded on a blockchain-inspired ledger structure.

The project focuses on:

- Immutable transaction records
- VAT-aware invoice generation
- Blockchain integrity verification
- Real-time audit analytics
- B2B vs B2C payment handling
- Tax authority transparency dashboards

This repository is designed as an **educational and architectural prototype**, inspired by modern enterprise fintech systems and blockchain audit infrastructures.

---

# Elevator Pitch

Blockchain Café demonstrates how blockchain-inspired transaction storage and VAT-aware invoice systems can improve transparency, auditability, and financial traceability for digital payment ecosystems.

The system combines:

- Blockchain transaction chains
- VAT analytics
- Invoice verification
- Real-time audit dashboards
- Risk and anomaly detection
- Business/private customer separation

into one integrated educational fintech platform.

---

# Project Goals

The main objectives of this project are:

- Demonstrate blockchain-based transaction integrity
- Simulate immutable invoice storage
- Improve VAT traceability and auditability
- Showcase enterprise microservice architecture
- Simulate tax authority analytics workflows
- Explore blockchain use cases in payment systems
- Provide a realistic fintech-oriented backend project

---

# Core Features

## Customer Features

- Digital café menu
- Cart & checkout system
- Business and private customer flows
- NFC-ready payment simulation
- Instant invoice generation
- PDF invoice export
- VAT-aware payment calculations

---

## Blockchain Features

- Blockchain transaction storage
- Previous-hash verification
- SHA-based integrity validation
- Immutable transaction history
- Blockchain audit records
- Hash recalculation checks

---

## VAT & Compliance Features

- Automatic VAT separation
- Gross / Net / VAT calculation
- VAT declaration generation
- VAT analytics dashboards
- Business vs private invoice tracking
- Audit-ready reporting

---

## Audit & Analytics Features

- Real-time tax authority dashboard
- Blockchain chain verification
- Risk & anomaly detection
- VAT discrepancy checks
- Invoice review portal
- Audit logging

---

# Screenshots

---

## Main Checkout Interface

![Main Checkout](./screenshots/home.png)

---

## Cart & Payment Workflow

![Cart & Payment](./screenshots/cart.png)

---

## Customer Invoice

![Invoice](./screenshots/invoice.png)

---

## Tax Authority Overview Dashboard

![Overview Dashboard](./screenshots/Overview_Dashboard.png)

---

## Blockchain Audit Records

![Blockchain Records](./screenshots/Blockchain_Records.png)

---

## Invoice Management

![Invoice List](./screenshots/invoice_list.png)

---

## Anomaly Detection

![Anomaly Detection](./screenshots/Anomaly_Detection.png)

---

## VAT Declaration System

![VAT Declaration](./screenshots/VAT_Declaration.png)

---

# System Architecture

The platform follows a modular microservice-oriented architecture inspired by enterprise fintech systems.

```text
                    ┌────────────────────┐
                    │   Frontend Client  │
                    │ HTML / CSS / JS UI │
                    └─────────┬──────────┘
                              │ REST APIs
                              ▼
┌─────────────────────────────────────────────────────────┐
│                  API / Backend Layer                   │
├─────────────────────────────────────────────────────────┤
│ Menu Service                                           │
│ Order Service                                          │
│ Payment Service                                        │
│ VAT Service                                            │
│ Wallet Service                                         │
│ Blockchain Service                                     │
│ Invoice Service                                        │
└─────────────────────────────────────────────────────────┘
                              │
                              ▼
                    ┌────────────────────┐
                    │      MySQL DB      │
                    └────────────────────┘
```

---

# Microservices

| Service            | Responsibility                          |
| ------------------ | --------------------------------------- |
| Menu Service       | Product and menu management             |
| Order Service      | Order lifecycle and checkout            |
| Payment Service    | Payment processing workflows            |
| VAT Service        | VAT calculations and analytics          |
| Wallet Service     | Customer wallet simulation              |
| Blockchain Service | Blockchain ledger and hash verification |
| Invoice Service    | Invoice generation and PDF export       |

---

# Blockchain Logic

The blockchain layer simulates immutable transaction storage using chained block structures.

Each block contains:

- Transaction details
- VAT information
- Invoice references
- Timestamp
- Current block hash
- Previous block hash

---

## Example Block Structure

```json
{
  "index": 2,
  "timestamp": "2026-05-15T18:30:00",
  "orderId": "order-ui-1778801227403",
  "grossAmount": 7.6,
  "vatAmount": 1.21,
  "netAmount": 6.39,
  "previousHash": "6b8cd637...",
  "hash": "eb4cc3c2..."
}
```

---

## Integrity Verification

The system verifies blockchain integrity through:

- Previous-hash linkage checks
- Hash recalculation
- Chain consistency validation
- VAT-to-transaction alignment checks

---

# VAT Logic

The VAT engine automatically:

- Separates VAT from revenue
- Calculates gross/net totals
- Supports multiple VAT rates
- Tracks B2B vs B2C invoices
- Generates declaration-ready analytics

---

## VAT Calculation Flow

```text
Gross Amount
      │
      ▼
VAT Extraction
      │
      ├──► Net Revenue
      │
      └──► VAT Liability
```

---

# Tax Authority Portal

The Tax Authority Portal acts as a simulated government audit interface.

Features include:

- VAT analytics
- Blockchain verification
- Invoice review
- Audit logs
- Anomaly detection
- VAT declaration export
- Risk monitoring

---

# Technology Stack

## Backend

| Technology        | Usage                          |
| ----------------- | ------------------------------ |
| Java 17           | Core backend language          |
| Spring Boot       | Backend framework              |
| Maven             | Dependency management          |
| Spring Security   | Authentication & authorization |
| JWT               | Token-based authentication     |
| REST APIs         | Service communication          |
| OpenAPI / Swagger | API documentation              |

---

## Frontend

| Technology | Usage          |
| ---------- | -------------- |
| HTML       | Structure      |
| CSS        | Styling        |
| JavaScript | Frontend logic |

---

## Database

| Technology | Usage              |
| ---------- | ------------------ |
| MySQL      | Persistent storage |

---

## Infrastructure

| Technology     | Usage                       |
| -------------- | --------------------------- |
| Docker         | Containerization            |
| Docker Compose | Multi-service orchestration |

---

# Suggested Project Structure

```text
blockchain-cafe/
│
├── frontend/
│   ├── index.html
│   ├── styles/
│   ├── scripts/
│   └── screenshots/
│
├── menu-service/
├── order-service/
├── payment-service/
├── vat-service/
├── wallet-service/
├── blockchain-service/
├── invoice-service/
│
├── docker/
├── docs/
├── database/
│
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

# Example REST API Endpoints

## Menu Service

```http
GET /api/menu
```

```http
GET /api/menu/{id}
```

---

## Order Service

```http
POST /api/orders
```

```http
GET /api/orders/{id}
```

---

## Payment Service

```http
POST /api/payments/private
```

```http
POST /api/payments/business
```

---

## Blockchain Service

```http
GET /api/blockchain/blocks
```

```http
GET /api/blockchain/verify
```

---

## VAT Service

```http
GET /api/vat/analytics
```

```http
GET /api/vat/declaration
```

---

## Invoice Service

```http
GET /api/invoices
```

```http
GET /api/invoices/{id}/pdf
```

---

# Run Locally

## Prerequisites

- Java 17
- Maven
- Docker
- Docker Compose
- MySQL

---

## Clone Repository

```bash
git clone https://github.com/your-username/blockchain-cafe.git
cd blockchain-cafe
```

---

## Build Backend Services

```bash
mvn clean install
```

---

## Start Infrastructure

```bash
docker-compose up --build
```

---

## Start Spring Boot Services

Example:

```bash
cd order-service
mvn spring-boot:run
```

Repeat for all services.

---

## Open Frontend

```text
http://localhost:8080
```

---

# Docker Setup

## Example docker-compose.yml

```yaml
version: "3.9"

services:
  mysql:
    image: mysql:8
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: blockchain_cafe
    ports:
      - "3306:3306"

  order-service:
    build: ./order-service
    ports:
      - "8081:8081"

  payment-service:
    build: ./payment-service
    ports:
      - "8082:8082"

  blockchain-service:
    build: ./blockchain-service
    ports:
      - "8083:8083"
```

---

# Swagger / OpenAPI

The backend services support OpenAPI documentation.

Example Swagger endpoint:

```text
http://localhost:8081/swagger-ui.html
```

or

```text
http://localhost:8081/swagger-ui/index.html
```

---

# Security Concepts

The platform includes simplified enterprise-inspired security concepts:

- JWT-based authentication
- Protected backend routes
- Service separation
- Transaction validation
- Blockchain integrity checks

---

# Example Workflow

```text
Customer places order
        │
        ▼
VAT calculated automatically
        │
        ▼
Payment processed
        │
        ▼
Invoice generated
        │
        ▼
Transaction stored in blockchain ledger
        │
        ▼
Audit dashboard updated
```

---

# Future Improvements

## Planned Enhancements

- Real NFC hardware integration
- Kafka event streaming
- Redis caching
- Kubernetes deployment
- Role-based access control
- OAuth2/OpenID Connect
- Real-time WebSocket analytics
- Multi-currency support
- Advanced fraud detection
- AI-powered transaction anomaly analysis
- Cloud deployment pipelines
- CI/CD automation

---

# Educational Disclaimer

> This project is an educational and architectural prototype created for learning, demonstration, and research purposes.

Blockchain Café is **NOT**:

- A licensed financial platform
- A banking infrastructure
- A cryptocurrency exchange
- A tax authority system
- A production-ready payment processor

The system simulates fintech and blockchain concepts for academic and technical demonstration purposes only.

---

# License

MIT License

```text
MIT License

Copyright (c) 2026

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files...
```

---

# Author

**Yazan Al Hussein**

International Information Systems (B.Sc.)  
TH Augsburg — Hochschule Augsburg

---

# Final Notes

Blockchain Café was built to explore how blockchain-inspired architectures can improve transparency, traceability, and auditability in modern transaction systems.

The project combines:

- Fintech-inspired architecture
- Enterprise backend engineering
- Blockchain verification concepts
- VAT analytics
- Audit transparency systems

into one integrated educational platform.

---
