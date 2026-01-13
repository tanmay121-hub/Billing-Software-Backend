# 🧾 Billing Software Backend (Spring Boot + mySQL)

A RESTful Billing Software Backend built using Spring Boot that manages products, customers, and invoices with proper layered architecture and real-world billing logic.

> - Product & Customer Management
> - Invoice Generation with Tax & Discount
> - Stock Validation & Reduction
> - Clean Controller → Service → Repository flow

---

## ✨ Features
- 📦 Product management (CRUD)
- 👥 Customer management
- 🧾 Invoice generation with:
- Dynamic GST calculation
- Stock validation
- Discount handling
- 📉 Automatic stock reduction after billing
- 🧠 Business logic handled in Service layer
- 🧪 Easily testable using Postman
- ⚡ Fast development with Spring Boot DevTools
- 🧱 In-memory data storage (List-based, DB-ready design)
- Stored in DATABSE
  
---

## 🧑‍💻 Tech Stack
| Technology     | Usage                         |
| -------------- | ----------------------------- |
| ☕ Java 17+     | Programming language          |
| 🌱 Spring Boot | Backend framework             |
| 🌐 Spring Web  | REST API development          |
| 🧪 Maven       | Dependency management         |
| 🧰 Swagger     | API testing                   |
| ⚡ DevTools     | Hot reload during development |

---

## 📂 Project Structure


```
billing-software
│
├── controller
│   ├── ProductController.java
│   ├── CustomerController.java
│   ├── InvoiceController.java
│
├── service
│   ├── ProductService.java
│   ├── CustomerService.java
│   ├── InvoiceService.java
│
├── repository
│   ├── ProductRepository.java
│   ├── CustomerRepository.java
│   ├── InvoiceRepository.java
│
├── entity
│   ├── Product.java
│   ├── Customer.java
│   ├── Invoice.java
│   ├── InvoiceItem.java
│
├── dto
│   ├── InvoiceRequestDTO.java
│   ├── InvoiceItemRequestDTO.java
│
├── exception
│   ├── ResourceNotFoundException.java
│   ├── InsufficientStockException.java
│
└── BillingApplication.java
```

---

## ⚙️ Getting Started

### Step 1: Clone the Repository

```bash
git clone https://github.com/tanmay121-hub/billing-software
cd billing-software
```

### Step 2: Build the Project

```bash
mvn clean install
```

### Step 3: Run the Application

```bash
mvn spring-boot:run
```

Server will start at:

```
http://localhost:8080
```

---

## 📡 API Endpoints

### 📦 Product APIs

| Method | Endpoint       | Description          |
| ------ | -------------- | -------------------- |
| POST   | /products      | Add new product      |
| GET    | /products      | Get all products     |
| GET    | /products/{id} | Get product by ID    |
| PUT    | /products/{id} | Update price & stock |
| DELETE | /products/{id} | Delete product       |

---

### 👥 Customer APIs

| Method | Endpoint        | Description        |
| ------ | --------------- | ------------------ |
| POST   | /customers      | Add new customer   |
| GET    | /customers      | Get all customers  |
| GET    | /customers/{id} | Get customer by ID |
| PUT    | /customers/{id} | Update customer    |
| DELETE | /customers/{id} | Delete customer    |

---

### 🧾 Invoice APIs

| Method | Endpoint                        | Description              |
| ------ | ------------------------------- | ------------------------ |
| POST   | /invoices                       | Generate invoice         |
| GET    | /invoices                       | Get all invoices         |
| GET    | /invoices/{id}                  | Get invoice by ID        |
| GET    | /invoices/customer/{customerId} | Get invoices by customer |

---

## 🧠 Business Rules

* ❌ Billing not allowed if stock is insufficient
* ❌ Invoice creation fails if customer does not exist
* ✔ Product stock reduced after invoice generation
* ✔ GST calculated dynamically per product
* ✔ Invoice number auto-generated
* ✔ All calculations handled in Service layer

---

## 🧪 Sample Invoice Request (Postman)

```json
{
  "customerId": 1,
  "discount": 50,
  "items": [
    {
      "productId": 2,
      "quantity": 3
    },
    {
      "productId": 1,
      "quantity": 1
    }
  ]
}
```

---

## 🔮 Future Enhancements

* 🗄️ Replace in-memory storage with JPA + MySQL
* 🔐 JWT authentication
* 📊 Invoice PDF generation
* 🧪 Unit & integration testing
* 📈 Sales reports & analytics

---

## 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push and create a Pull Request

---

## 🧑‍💻 Author
Tanmay Patil — Full Stack Developer  
[GitHub](https://github.com/tanmay121-hub) - [LinkedIn](https://www.linkedin.com/in/tanmay-patil-10997a259/)
