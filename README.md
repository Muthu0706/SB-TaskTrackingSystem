# Task Tracking System :

Technologies Used :
Java: Programming language used to build the backend.
Spring Boot: Framework for building the RESTful application.
Spring Security: For authentication and authorization.
JPA/Hibernate: For ORM functionality to interact with the database.
Swagger: For API documentation.
Maven: For dependency management.

## Description
The **Task Tracking System** is a web application built using Spring Boot that allows users to manage their tasks efficiently. This application provides functionalities for user registration, authentication, task creation, updating, deletion, and generating task reports. The application also supports JWT-based authentication, making it secure and reliable for managing user data and tasks.

### Key Features
- **User Management**: Sign up, login, update profile details, and logout.
- **Task Management**: Create, update, delete, and retrieve tasks.
- **Progress Tracking**: Update the status of tasks from open to in-progress and completed.
- **Reporting**: Generate reports that summarize task completion and average completion time.
- **Scheduled Tasks**: Execute tasks on a scheduled basis.
- **API Documentation**: Generated using Swagger, providing clear documentation on available endpoints.

## Requirements
- Java 17
- Spring Boot 3.4.1
- SQL Server Database
- Maven for dependency management

## Setup and Installation

### 1. Clone the Repository
Clone this repository to your local machine using the following command:

API End Points:
http://localhost:8080/swagger-ui/index.html

6. Testing
You can use tools like Postman to test the API endpoints given in the documentation.

Folder Structure :

src/main/java/com/tasktrackingsystem: Contains all Java source files for controllers, services, repositories, configurations, and entity models.
src/main/resources: Contains properties files and configurations.
src/test/java/com/tasktrackingsystem: Contains unit and integration tests. Mockito and Junit

