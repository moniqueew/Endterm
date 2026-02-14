Pet Adoption Center
Project Overview
This project is a RESTful Pet Adoption Center API developed using Java Spring Boot and PostgreSQL. The purpose of the project is to demonstrate advanced OOP principles, JDBC-based database access, layered architecture, and proper exception handling.The API allows managing animals of different types (Dog and Cat) that belong to shelters. All data is stored in a relational database and accessed using JDBC.

Architecture Overview
The application follows a layered architecture:Controller → Service → Repository → Database

Controller layer handles HTTP requests and responses (REST endpoints)
Service layer contains business logic and validation
Repository layer interacts with the database using JDBC
Database stores persistent data (PostgreSQL)

OOP Design
AnimalBase (abstract)
Common fields: id, name, breed, age, shelter
Abstract methods: getType(), getDetails()
Concrete validation logic

Subclasses
Dog -> additional field: weight (double)
Cat -> Additional field: indoor (boolean)

Polymorphism is demonstrated by working with AnimalBase references while creating and processing different animal types.
Interfaces and Design Patterns
Factory Pattern

AnimalFactory
Creates Dog or Cat objects based on request data
Hides object creation logic from the service layer

Composition
Animal → Shelter
Each animal contains a Shelter object
Shelter is stored as a separate table and referenced using a foreign key

Exception Handling

- InvalidInputException → 400 Bad Request
- ResourceNotFoundException → 404 Not Found
- DuplicateResourceException → 409 Conflict
- DatabaseOperationException → 500 Internal Server Error

How to Run the Project
Requirements:Java 17+, Maven, PostgreSQL

Steps:

Create PostgreSQL database
Execute schema.sql to initialize tables
Update application.properties with DB credentials
Run the application

API will be available at: http://localhost:8081
Example Requests
POST
<img width="1385" height="600" alt="image" src="https://github.com/user-attachments/assets/d4ea5343-0dc0-4b5d-b616-7c99caf88bf1" />
GET ALL
<img width="1397" height="745" alt="image" src="https://github.com/user-attachments/assets/5f04f89b-8408-4b3a-8095-fd5a44486c25" />
GET ID
<img width="1389" height="539" alt="image" src="https://github.com/user-attachments/assets/99a85975-5ee9-4c6c-9658-807242bf001a" />
PUT
<img width="1767" height="581" alt="image" src="https://github.com/user-attachments/assets/7ece73ea-b10e-4c95-a858-0a20fbc4012f" />
DELETE
<img width="1780" height="299" alt="image" src="https://github.com/user-attachments/assets/7d68d209-71f1-4c65-bbec-38af06c90d79" />

SQL DATABASE

<img width="776" height="770" alt="image" src="https://github.com/user-attachments/assets/b581c9cc-a686-40f3-83ef-ccdd9a04e771" />


Reflection 

This project was a very important step for me. I learned how to use a layered architecture, which helps to organize code so that every part has its own job. 
I also worked with JDBC and transactions to make sure the data stays safe and correct. Additionally, I learned how to create REST APIs that work well and handle errors correctly. 
Finally, I improved my skills in finding and fixing SQL problems. This project showed me that good organization makes software much easier to grow and fix in the future.


Bonus Task
DELETE cache
<img width="1481" height="306" alt="image" src="https://github.com/user-attachments/assets/9d560868-98dd-433e-9c5f-38b59973daae" />

1. Performance Improvement
The system now makes fewer database requests when the same data is needed many times. This helps the method getAllAnimals() work faster and improves response time.

2. Better Architecture

- The project uses the Singleton pattern, so there is only one cache object in the system.
- All cache logic is placed in a separate class.
- This keeps the project structure clean and organized.

3. Data Consistency
- The cache is automatically updated after data changes (for example, after adding or editing information).
- If needed, it can also be controlled manually.
- Because of this, users always receive correct and up-to-date data.

4. SOLID Principles
- The project follows SOLID principles:
- Single Responsibility Principle – The AnimalCache class is responsible only for caching.
- Open/Closed Principle – It is easy to add new caching strategies without changing existing code.
- Dependency Inversion Principle – The service works with a cache interface, not with a specific implementation.

