# Flight Management System - Worldline Assignment

This is a Spring Boot application designed for managing and searching flight information. It integrates with an external supplier (CrazySupplier) via a REST API and provides secured access using JWT authentication. Data is pulled both from a local database and real-time external sources.

---

## Features

- *JWT Authentication*
- *Feign Client Integration* with CrazySupplier API
- *H2/In-Memory Database* for local flight records
- *Flight Search API* (Combines DB + Supplier)
- *Swagger* for API documentation
- Validation for request and response DTOs

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring Security + JWT
- Spring Data JPA 
- OpenFeign (for External API intergration)
- Lombok (Boilerplate Code)
- Swagger

---

## Steps

### 1. Clone the repo

```bash
git clone https://github.com/your-repo/flight-management.git
cd flight-management
```

**2. Build the project**
```
./mvnw clean install
```

**3. Run the application**
```
./mvnw spring-boot:run
```

## Postman Collection 
FlightsManagement

## Assumption

CrazySupplier API is up and runnning.
