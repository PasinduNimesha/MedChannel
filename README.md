# MedChannel Server

A Spring Boot-based backend server for managing medical appointments, patients, and physicians. MedChannel provides a comprehensive REST API for healthcare management with cloud-based storage and DynamoDB integration.

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Running the Application](#running-the-application)
- [Docker Deployment](#docker-deployment)
- [Development](#development)
- [Contributing](#contributing)

## ✨ Features

- **Patient Management**: Create, read, update, and delete patient records
- **Physician Management**: Manage physician profiles and information
- **Appointment Scheduling**: Schedule and manage medical appointments
- **User Management**: Handle user authentication and profiles
- **Cloud Storage Integration**: AWS S3 integration for file uploads (profile pictures, documents)
- **NoSQL Database**: DynamoDB for scalable data storage
- **RESTful API**: Clean and intuitive API design
- **Exception Handling**: Custom exception handling for better error management
- **DTOs**: Data Transfer Objects for clean API contracts

## 🛠️ Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.3.1
- **Build Tool**: Maven
- **Database**: AWS DynamoDB
- **File Storage**: AWS S3
- **Security**: Spring Security 6.3.1
- **Mapping**: ModelMapper 3.2.2
- **Lombok**: For reducing boilerplate code
- **Containerization**: Docker

## 📦 Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven 3.6.0 or higher
- AWS Account with DynamoDB and S3 configured
- Docker (for containerized deployment)

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/PasinduNimesha/MedChannel.git
cd MedChannel-server
```

### 2. Configure AWS Credentials

Create an `.env` file or configure environment variables with your AWS credentials:

```bash
export AWS_ACCESS_KEY_ID=your_access_key
export AWS_SECRET_ACCESS_KEY=your_secret_key
export AWS_REGION=your_region
```

Or update `application.properties`:

```properties
aws.accessKeyId=your_access_key
aws.secretAccessKey=your_secret_key
aws.region=us-east-1
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The server will start on `http://localhost:8080` by default.

## 📁 Project Structure

```
src/
├── main/
│   └── java/com/example/medchannel/
│       ├── MedChannelApplication.java          # Main Spring Boot application
│       ├── config/
│       │   └── S3Config.java                   # AWS S3 configuration
│       ├── controller/
│       │   ├── AppointmentController.java      # Appointment endpoints
│       │   ├── PatientController.java          # Patient endpoints
│       │   ├── PhysicianController.java        # Physician endpoints
│       │   ├── UserController.java             # User endpoints
│       │   ├── ImageController.java            # Image/file upload endpoints
│       │   └── HealthController.java           # Health check endpoint
│       ├── service/
│       │   ├── AppointmentService.java         # Appointment business logic
│       │   ├── PatientService.java             # Patient business logic
│       │   ├── PhysicianService.java           # Physician business logic
│       │   └── UserService.java                # User business logic
│       ├── entity/
│       │   ├── Appointment.java                # Appointment entity
│       │   ├── Patient.java                    # Patient entity
│       │   ├── Physician.java                  # Physician entity
│       │   └── User.java                       # User entity
│       ├── dto/
│       │   ├── AppointmentDTO.java             # Appointment DTO
│       │   ├── PatientDTO.java                 # Patient DTO
│       │   ├── PhysicianDTO.java               # Physician DTO
│       │   ├── UserDTO.java                    # User DTO
│       │   └── ResponseDTO.java                # Generic response wrapper
│       └── exception/
│           ├── AppointmentException.java       # Appointment exceptions
│           ├── PatientException.java           # Patient exceptions
│           ├── PhysicianException.java         # Physician exceptions
│           └── UserException.java              # User exceptions
└── test/
    └── java/com/example/medchannel/
        └── MedChannelApplicationTests.java     # Application tests
```

## ⚙️ Configuration

### DynamoDB Tables

The application expects the following DynamoDB tables:

- `patients` - Stores patient information
- `physicians` - Stores physician information
- `appointments` - Stores appointment records
- `users` - Stores user accounts

### S3 Bucket

Configure an S3 bucket for file uploads. Update the `S3Config.java` with your bucket details.

### Application Properties

Create or update `application.properties`:

```properties
spring.application.name=MedChannel
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

## 📡 API Endpoints

### Health Check
- `GET /api/v1/health` - Check server health status

### Patient Endpoints
- `GET /api/v1/patient/all` - Get all patients
- `GET /api/v1/patient/{id}` - Get patient by ID
- `POST /api/v1/patient` - Create new patient
- `PATCH /api/v1/patient` - Update patient
- `DELETE /api/v1/patient/{id}` - Delete patient
- `POST /api/v1/patient/create` - Create patient with profile picture

### Physician Endpoints
- `GET /api/v1/physician/all` - Get all physicians
- `GET /api/v1/physician/{id}` - Get physician by ID
- `POST /api/v1/physician` - Create new physician
- `PATCH /api/v1/physician` - Update physician
- `DELETE /api/v1/physician/{id}` - Delete physician

### Appointment Endpoints
- `GET /api/v1/appointment/all` - Get all appointments
- `GET /api/v1/appointment/{id}` - Get appointment by ID
- `POST /api/v1/appointment` - Create new appointment
- `PATCH /api/v1/appointment` - Update appointment
- `DELETE /api/v1/appointment/{id}` - Delete appointment

### User Endpoints
- `GET /api/v1/user/all` - Get all users
- `GET /api/v1/user/{id}` - Get user by ID
- `POST /api/v1/user` - Create new user
- `PATCH /api/v1/user` - Update user
- `DELETE /api/v1/user/{id}` - Delete user

### Image Upload
- `POST /api/v1/image/upload` - Upload image file

## 🏃 Running the Application

### Using Maven
```bash
mvn spring-boot:run
```

### Using Java
```bash
java -jar target/MedChannel-0.0.1-SNAPSHOT.jar
```

### Using Maven Wrapper (Windows)
```bash
./mvnw.cmd spring-boot:run
```

### Using Maven Wrapper (Linux/Mac)
```bash
./mvnw spring-boot:run
```

## 🐳 Docker Deployment

### Build Docker Image

```bash
docker build -t medchannel-server:latest .
```

### Run Docker Container

```bash
docker run -d \
  -p 8080:8080 \
  -e AWS_ACCESS_KEY_ID=your_key \
  -e AWS_SECRET_ACCESS_KEY=your_secret \
  -e AWS_REGION=us-east-1 \
  --name medchannel \
  medchannel-server:latest
```

### Docker Compose (if available)

Create a `docker-compose.yml` and run:

```bash
docker-compose up -d
```

## 💻 Development

### IDE Setup

1. **IntelliJ IDEA**
   - Open the project folder
   - Maven dependencies will auto-resolve
   - Install Lombok plugin if needed

2. **Eclipse**
   - Import as Maven project
   - Install Lombok plugin for annotation processing

3. **VS Code**
   - Install Extension Pack for Java
   - Install Lombok Annotations Support for VS Code

### Running Tests

```bash
mvn test
```

### Code Coverage

```bash
mvn clean test jacoco:report
```

## 📝 Dependencies

Key dependencies used in this project:

| Dependency | Version | Purpose |
|-----------|---------|---------|
| Spring Boot Web | 3.3.1 | REST API framework |
| AWS Java SDK DynamoDB | 1.12.780 | NoSQL database |
| AWS Java SDK S3 | 1.12.570 | File storage |
| ModelMapper | 3.2.2 | Object mapping |
| Lombok | 1.18.34 | Boilerplate reduction |
| Spring Security | 6.3.1 | Security |
| AWS SDK v2 Auth | 2.29.31 | AWS authentication |

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💼 Author

**Pasindu Nimesha**
- GitHub: [@PasinduNimesha](https://github.com/PasinduNimesha)

## 📞 Support

For support, email support@medchannel.com or open an issue in the repository.

## 🔗 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [AWS DynamoDB](https://aws.amazon.com/dynamodb/)
- [AWS S3](https://aws.amazon.com/s3/)
- [Maven Documentation](https://maven.apache.org/)

---

**Note**: Make sure to configure AWS credentials and create necessary DynamoDB tables and S3 buckets before running the application.
