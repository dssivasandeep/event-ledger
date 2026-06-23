# Event Ledger - Account Service

## Overview

Account Service manages account balances and transaction history.

Responsibilities:

* Persist transactions
* Compute balances
* Return account details
* Maintain account transaction history

---

## Technology Stack

* Java 8
* Spring Boot 2.7.18
* Spring Data JPA
* H2 Database
* Maven

---

## Running the Application

### Prerequisites

* Java 8
* Maven 3.8+

### Start Application

```bash
mvn spring-boot:run
```

Application starts on:

```text
http://localhost:8082
```

---

## H2 Console

```text
http://localhost:8082/h2-console
```

JDBC URL:

```text
jdbc:h2:mem:accountdb
```

Username:

```text
sa
```

Password:

```text
(empty)
```

---

## API Endpoints

### Apply Transaction

```http
POST /accounts/{accountId}/transactions
```
Endpoint:
http://localhost:8082/accounts/acct-123/transactions

Sample payload:

{
"eventId":"evt-002",
"type":"DEBIT",
"amount":50,
"eventTimestamp":"2026-05-15T14:05:11Z"
}
### Get Balance

```http
GET /accounts/{accountId}/balance
```
Endpoint:
http://localhost:8082/accounts/acct-123/balance

### Get Account Details

```http
GET /accounts/{accountId}
```
Endpoint:
http://localhost:8082/accounts/acct-123

### Health Check

```http
GET /health
```

---

## Balance Computation

Balance is calculated dynamically:

```text
Total Credits - Total Debits
```

No balance is stored directly.

---

## Features

### Transaction History

All transactions are persisted and can be retrieved through account APIs.

### Idempotency Support

Duplicate transaction events are ignored using eventId validation.

### Trace Propagation

Consumes Trace IDs propagated from Gateway Service.

---

## Testing

```bash
mvn test
```

Includes:

* Balance Calculation Tests
* Transaction Processing Tests
* Account Details Tests

---

## Dependency

Typically invoked by Gateway Service running on:

```text
http://localhost:8081
```
