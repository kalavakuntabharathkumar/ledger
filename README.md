# Ledger Reconciliation Platform

A runnable local demo of a finance reconciliation platform built with Java 17, Spring Boot, Maven, PostgreSQL-compatible JDBC configuration, and React. The backend includes journal/transaction APIs, deterministic matching, concurrent batch processing, JWT-ready security configuration notes, and tests.

## Requirements
- Java 17+
- Maven 3.9+
- Node.js 20+ (for the React UI)
- PostgreSQL 14+ (optional; H2 is used by default for easy local startup)

## Run
```bash
cd backend
mvn spring-boot:run
```
API: `http://localhost:8080/api/transactions`; Swagger is not bundled. Default demo database is in-memory H2.
In another terminal:
```bash
cd frontend
npm install
npm run dev
```
UI: `http://localhost:5173`

## API
- `GET /api/transactions`
- `POST /api/transactions/reconcile` — runs matching across demo records
- `GET /api/health`

## Notes
This is a portfolio/demo implementation, not a production accounting system. The included figures in project descriptions are target narrative examples, not measured benchmarks from this repository. OAuth2/JWT and Jenkins pipeline examples are documented as extension points; configure real identity-provider secrets before production use.
