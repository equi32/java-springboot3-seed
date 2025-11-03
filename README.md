# Hexagonal Architecture Seed - Spring Boot 3

Seed project for Spring Boot 3 with complete Hexagonal Architecture, including examples of all main components.

## 🏗️ Architecture

This project implements **Hexagonal Architecture** (also known as Ports & Adapters), which allows:

- ✅ Domain independence from frameworks
- ✅ Easy testing
- ✅ Flexibility to change implementations
- ✅ Clear separation of concerns

### Project Structure

```
src/main/java/gov/justucuman/seed/
├── domain/                          # Domain Layer (business core)
│   ├── model/                       # Domain entities
│   │   ├── Product.java             # Core entity (Java Record)
│   │   └── ProductStatus.java       # Enum with custom parsing
│   └── port/                        # Port interfaces
│       ├── input/                   # Input ports (use cases)
│       │   ├── CreateProduct.java
│       │   ├── GetAllProduct.java
│       │   ├── GetProductById.java
│       │   ├── SearchProduct.java
│       │   ├── IndexProduct.java
│       │   ├── UpdateProduct.java
│       │   ├── DeleteProductById.java
│       │   ├── GetExternalProductById.java
│       │   └── GetAllExternalProduct.java
│       └── output/                  # Output ports (repositories, events)
│           ├── ProductSavePort.java
│           ├── ProductFindByIdPort.java
│           ├── ProductFindAllPort.java
│           ├── ProductDeleteByIdPort.java
│           ├── ProductSearchPort.java
│           ├── ProductEventPublisherPort.java
│           └── ExternalProductPort.java
│
├── application/                     # Application Layer (orchestration)
│   ├── usecase/                     # Use case implementations
│   │   ├── CreateProductUseCase.java
│   │   ├── GetAllProductUseCase.java
│   │   ├── GetProductByIdUseCase.java
│   │   ├── SearchProductUseCase.java
│   │   ├── IndexProductUseCase.java
│   │   ├── UpdateProductUseCase.java
│   │   ├── DeleteProductByIdUseCase.java
│   │   ├── GetExternalProductByIdUseCase.java
│   │   └── GetAllExternalProductUseCase.java
│   └── exception/                   # Application exceptions
│       └── ProductNotFoundException.java
│
├── infrastructure/                  # Infrastructure Layer (adapters)
│   ├── adapter/
│   │   ├── input/                   # Input adapters
│   │   │   ├── rest/                # REST API Controllers
│   │   │   │   ├── CreateProductController.java
│   │   │   │   ├── GetAllProductController.java
│   │   │   │   ├── GetProductByIdController.java
│   │   │   │   ├── SearchProductController.java
│   │   │   │   ├── UpdateProductController.java
│   │   │   │   ├── DeleteProductByIdController.java
│   │   │   │   ├── GetExternalProductByIdController.java
│   │   │   │   └── GetAllExternalProductController.java
│   │   │   ├── dto/                 # Request/Response DTOs
│   │   │   │   ├── CreateProductRequest.java
│   │   │   │   ├── ProductResponse.java
│   │   │   │   └── UpdateProductRequest.java
│   │   │   ├── mapper/              # REST mappers
│   │   │   │   ├── CreateProductMapper.java
│   │   │   │   └── GetProductMapper.java
│   │   │   └── event/               # Kafka Consumer
│   │   │       └── ProductKafkaConsumer.java
│   │   │
│   │   └── output/                  # Output adapters
│   │       ├── persistence/         # Persistence Adapters
│   │       │   ├── jpa/             # JPA Implementation
│   │       │   │   ├── ProductSaveJpaAdapter.java
│   │       │   │   ├── ProductFindByIdJpaAdapter.java
│   │       │   │   ├── ProductFindAllJpaAdapter.java
│   │       │   │   ├── entity/ProductEntity.java
│   │       │   │   └── repository/ProductJpaRepository.java
│   │       │   └── jdbc/            # JDBC Template Implementation
│   │       │       ├── ProductDeleteJdbcAdapter.java
│   │       │       └── ProductRowMapper.java
│   │       ├── search/              # OpenSearch Integration
│   │       │   ├── ProductOpenSearchAdapter.java
│   │       │   ├── ProductDocumentMapper.java
│   │       │   └── ProductDocument.java
│   │       ├── event/               # Kafka Event Publisher
│   │       │   ├── ProductKafkaPublisherAdapter.java
│   │       │   ├── ProductEventMapper.java
│   │       │   └── ProductEvent.java
│   │       └── external/            # External API Integration
│   │           ├── ProductWebClientAdapter.java
│   │           ├── ExternalProductMapper.java
│   │           └── ExternalProductResponse.java
│   │
│   └── config/                      # Infrastructure Configurations
│       ├── OpenApiConfig.java
│       ├── KafkaConfig.java
│       ├── OpenSearchConfig.java
│       └── WebClientConfig.java
│
└── common/                          # Common Components
    ├── error/                       # Error Handling
    │   ├── Error.java
    │   ├── ErrorDetail.java
    │   └── ErrorResponse.java
    ├── constants/                   # Application Constants
    │   └── ProblemType.java
    └── util/                        # Utility Classes
        └── DateUtils.java
```

## 🚀 Technologies and Versions

### Core Framework
- **Java**: 21 (with Records for immutable DTOs)
- **Spring Boot**: 3.5.3
- **Spring Framework**: 6.x (included in Spring Boot)

### Persistence
- **Spring Data JPA**: 3.4.x
- **JDBC Template**: Included in Spring Boot
- **PostgreSQL Driver**: Runtime dependency
- **Flyway**: 11.10.1

### Messaging & Events
- **Spring Kafka**: 3.3.1
- **Auto-created Kafka topics**

### Search & Analytics
- **OpenSearch Java Client**: 3.2.0

### External Integration
- **Spring WebFlux**: (for WebClient)
- **Reactive programming**: Support

### Mapping and Utilities
- **MapStruct**: 1.6.3 (compatible with Records)
- **Lombok**: (Reduced boilerplate)
- **Spring Validation**

### Testing
- **JUnit 5**: Jupiter (included in Spring Boot)
- **Spring Boot Test**: 3.5.3
- **Reactor Test**: (for WebFlux)

### Documentation
- **SpringDoc OpenAPI**: 2.8.13 (Swagger UI)

## 🎯 Key Features

### 🏗️ Complete Hexagonal Architecture
- ✅ **Domain Layer**: Pure business logic with ports and models
- ✅ **Application Layer**: Use case implementations
- ✅ **Infrastructure Layer**: Adapters for external integrations
- ✅ **Common Layer**: Shared components and utilities

### 💾 Multiple Persistence Strategies
- ✅ **JPA**: Standard CRUD operations with Spring Data
- ✅ **JDBC Template**: Complex queries and custom mapping
- ✅ **Flyway**: Database migrations and versioning

### 🔍 Advanced Search Capabilities
- ✅ **OpenSearch Integration**: Full-text search with field weighting
- ✅ **Multi-match queries**: Search across multiple fields
- ✅ **Parameter-based filtering**: Dynamic search criteria

### 📡 Event-Driven Architecture
- ✅ **Kafka Integration**: Event publishing and consumption
- ✅ **Auto-created topics**: Automatic topic management
- ✅ **Event DTOs**: Structured event data

### 🌐 External API Integration
- ✅ **WebClient**: Reactive HTTP client for external APIs
- ✅ **FakeStore API**: Example external product integration
- ✅ **Error Handling**: Comprehensive external API error management

### 🧮 Modern Java Features
- ✅ **Java Records**: Immutable DTOs and entities
- ✅ **Java 21**: Latest language features
- ✅ **MapStruct**: Type-safe mapping with record support
- ✅ **Spring Validation**: Jakarta Bean Validation

### 📊 Comprehensive Error Handling
- ✅ **Global Exception Handler**: Centralized error management
- ✅ **Custom Error Responses**: Structured error information
- ✅ **Problem Types**: Standardized error categorization

### 🔄 Reactive Programming
- ✅ **WebFlux Integration**: Non-blocking I/O for external APIs
- ✅ **Reactor Patterns**: Reactive stream processing
- ✅ **Backpressure Handling**: Resilient reactive streams

### 📚 API Documentation
- ✅ **OpenAPI 3.0**: Comprehensive API documentation
- ✅ **Swagger UI**: Interactive API exploration
- ✅ **Auto-generated Docs**: Always up-to-date documentation

### ⚙️ Multi-Environment Configuration
- ✅ **Profile-based configs**: Separate configurations per environment
- ✅ **Environment-specific properties**: Local, dev, test, production
- ✅ **Flexible deployment**: Configurable for different environments

### 🎯 SOLID Principles Implementation
- ✅ **Single Responsibility**: Each class has one purpose
- ✅ **Open/Closed**: Extensible without modification
- ✅ **Liskov Substitution**: Subtypes replaceable by base types
- ✅ **Interface Segregation**: Fine-grained interfaces
- ✅ **Dependency Inversion**: Depend on abstractions, not concretions

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.9+
- Docker and Docker Compose (for local infrastructure)
- PostgreSQL (or use Docker)
- Kafka (or use Docker)
- OpenSearch (or use Docker)

## 🛠️ Installation and Setup

### 1. Clone the repository

```bash
git clone <repository-url>
cd seed-gradle-kotlin
```

### 2. Configure infrastructure with Docker

Create a `docker-compose.yml` file in the project root:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:16-alpine
    container_name: seed-postgres
    environment:
      POSTGRES_DB: seed_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  kafka:
    image: confluentinc/cp-kafka:7.6.0
    container_name: seed-kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    depends_on:
      - zookeeper

  zookeeper:
    image: confluentinc/cp-zookeeper:7.6.0
    container_name: seed-zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - "2181:2181"

  opensearch:
    image: opensearchproject/opensearch:2.12.0
    container_name: seed-opensearch
    environment:
      - discovery.type=single-node
      - "OPENSEARCH_JAVA_OPTS=-Xms512m -Xmx512m"
      - "DISABLE_SECURITY_PLUGIN=true"
    ports:
      - "9200:9200"
      - "9600:9600"
    volumes:
      - opensearch_data:/usr/share/opensearch/data

volumes:
  postgres_data:
  opensearch_data:
```

### 3. Start infrastructure

```bash
docker-compose up -d
```

### 4. Build the project

```bash
./gradlew clean build
```

### 5. Run the application

```bash
./gradlew bootRun
```

The application will be available at: `http://localhost:8080`

## 📚 Configuration Profiles

### Development Profiles

This project supports multiple configuration profiles:

#### Local Development (default)
```bash
./gradlew bootRun
```
- Uses local configuration from `application-local.yml`
- Default profile for development

#### Development Environment
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```
- Uses development configuration from `application-dev.yml`

#### Test Environment
```bash
./gradlew bootRun --args='--spring.profiles.active=test'
```
- Uses test configuration from `application-test.yml`

## 📚 Available Endpoints

### Swagger UI
- **URL**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

### REST API Endpoints

#### Product Management
```bash
# Create Product
POST /api/v1/products
Content-Type: application/json

{
  "name": "Laptop Dell XPS 15",
  "description": "High performance laptop",
  "price": 1299.99,
  "stock": 10,
  "category": "Electronics"
}

# Get Product by ID
GET /api/v1/products/{id}

# Get All Products
GET /api/v1/products

# Update Product
PUT /api/v1/products/{id}
Content-Type: application/json

{
  "name": "Updated Product",
  "description": "Updated description",
  "price": 1499.99,
  "stock": 15,
  "category": "Electronics"
}

# Delete Product
DELETE /api/v1/products/{id}
```

#### Search Operations
```bash
# Search Products (OpenSearch with multi-field search)
GET /api/v1/products/search?query=laptop

# Search with parameter-based filtering
GET /api/v1/products/search?query=electronics&minPrice=100&maxPrice=1000
```

#### External API Integration
```bash
# Get External Product by ID
GET /api/v1/external/products/{id}

# Get All External Products
GET /api/v1/external/products
```

### Event-Driven Features

#### Kafka Events
- **Product Creation**: Events published to `product-events` topic
- **Event Structure**: Contains product data with timestamps
- **Auto-created Topics**: Kafka topics created automatically

## 🧪 Testing

### Current Test Status

**Note**: This project currently has minimal test coverage. The testing framework is configured but comprehensive tests need to be implemented.

### Available Test Files
- **`SeedApplicationTests.java`**: Basic Spring context loading test

### Run tests

```bash
# Run all tests
./gradlew test

# Run tests with coverage report
./gradlew test jacocoTestReport
```

### Planned Test Coverage

The following test types should be implemented:

#### Unit Tests (Planned)
- ✅ **Use Case Tests**: Test all business logic in use cases
- ✅ **Service Tests**: Test application layer components
- ✅ **Repository Tests**: Test persistence layer with in-memory database
- ✅ **Adapter Tests**: Test individual adapters in isolation
- ✅ **Mapper Tests**: Test MapStruct mappers

#### Integration Tests (Planned)
- ✅ **Controller Tests**: Test REST endpoints with MockMvc
- ✅ **Database Integration**: Test JPA and JDBC adapters
- ✅ **Kafka Integration**: Test event publishing and consumption
- ✅ **OpenSearch Integration**: Test search functionality
- ✅ **External API Integration**: Test WebClient adapters

#### End-to-End Tests (Planned)
- ✅ **Full Workflow Tests**: Test complete request flows
- ✅ **Container Tests**: Test with Testcontainers
- ✅ **API Contract Tests**: Test OpenAPI contracts

### Testing Tools Available
- **JUnit 5**: Testing framework
- **Spring Boot Test**: Spring testing utilities
- **Mockito**: Mocking framework
- **Testcontainers**: Integration testing with real containers
- **Reactor Test**: Testing reactive components

## 🔄 Data Flow

### Product Creation Flow

```
1. REST Request → CreateProductController (Input Adapter)
2. CreateProductController → CreateProductMapper (DTO → Domain)
3. CreateProductMapper → CreateProductUseCase (Application Layer)
4. CreateProductUseCase → ProductSavePort (Output Port)
5. ProductSaveJpaAdapter → JPA Repository (Persistence)
6. CreateProductUseCase → ProductSearchPort (Output Port)
7. ProductOpenSearchAdapter → OpenSearch (Search Index)
8. CreateProductUseCase → ProductEventPublisherPort (Output Port)
9. ProductKafkaPublisherAdapter → Kafka Topic (Event Publish)
10. Response ← Product created successfully
```

### Product Search Flow

```
1. Search Request → SearchProductController (Input Adapter)
2. SearchProductController → SearchProductUseCase (Application Layer)
3. SearchProductUseCase → ProductSearchPort (Output Port)
4. ProductOpenSearchAdapter → OpenSearch (Multi-field search with weighting)
5. ProductDocumentMapper → Domain Objects
6. Response ← Search results
```

### External API Integration Flow

```
1. External Request → GetExternalProductController (Input Adapter)
2. GetExternalProductController → GetExternalProductByIdUseCase (Application Layer)
3. GetExternalProductByIdUseCase → ExternalProductPort (Output Port)
4. ProductWebClientAdapter → FakeStore API (Reactive WebClient)
5. ExternalProductMapper → Domain Objects
6. Response ← External product data
```

### Event Consumption Flow

```
1. Kafka Topic → ProductKafkaConsumer (Input Adapter)
2. ProductKafkaConsumer → Process Event
3. Business Logic → Actions based on event type
```

## 🗄️ Database

### Flyway Migrations

Migrations are located in: `src/main/resources/db/migration/`

- **V1_1_0__create_product_table.sql**: Creates products table with UUID primary key

### Database Schema

```sql
CREATE TABLE product (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(19,2) NOT NULL,
    stock INTEGER DEFAULT 0,
    category VARCHAR(100),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Run migrations manually

```bash
./gradlew flywayMigrate
```

### Clean database

```bash
./gradlew flywayClean
```

## 📊 Monitoring and Observability

### Actuator Endpoints

Spring Boot Actuator is available at:
- Health: http://localhost:8080/actuator/health
- Info: http://localhost:8080/actuator/info
- Metrics: http://localhost:8080/actuator/metrics

## 🏗️ Architecture Patterns and Principles

### Hexagonal Architecture Implementation

- **Domain Layer**: Pure business logic, no external dependencies
- **Ports**: Interfaces that define contracts between layers
- **Adapters**: Concrete implementations of ports for external systems

### Use Case-Driven Design

- **Granular Use Cases**: Each use case handles one specific business operation
- **Clear Separation**: Input/Output ports separate concerns
- **Testable Architecture**: Easy to mock and test individual components

### SOLID Principles Applied

- ✅ **Single Responsibility**: Each class has one reason to change
- ✅ **Open/Closed**: Open for extension, closed for modification
- ✅ **Liskov Substitution**: Subtypes are replaceable by base types
- ✅ **Interface Segregation**: Client-specific interfaces
- ✅ **Dependency Inversion**: Depend on abstractions, not concretions

### Design Patterns Used

- ✅ **Adapter Pattern**: Integration with external systems
- ✅ **Repository Pattern**: Data access abstraction
- ✅ **Publisher-Subscriber Pattern**: Event-driven communication
- ✅ **Strategy Pattern**: Multiple persistence strategies
- ✅ **Facade Pattern**: Simplified external API access

## ⚙️ Configuration Management

### Multi-Environment Support

The application supports multiple configuration profiles:

#### Local Development (default)
```yaml
# application-local.yml
spring.profiles.active: local
```

#### Development Environment
```yaml
# application-dev.yml
spring.profiles.active: dev
```

#### Test Environment
```yaml
# application-test.yml
spring.profiles.active: test
```

### Running with Different Profiles

```bash
# Default (local)
./gradlew bootRun

# Development
./gradlew bootRun --args='--spring.profiles.active=dev'

# Test
./gradlew bootRun --args='--spring.profiles.active=test'
```

## 🐳 Docker Support

### Build Docker Image

```bash
./gradlew bootBuildImage
```

### Run with Docker

```bash
docker run -p 8080:8080 seed-gradle-kotlin:1.0.0-SNAPSHOT
```

### Docker Compose for Development

Use the provided `docker-compose.yml` to run all required services:

```bash
# Start all infrastructure services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

## 🔧 Build and Development Tools

### Gradle Commands

```bash
# Clean and build
./gradlew clean build

# Run application
./gradlew bootRun

# Run tests
./gradlew test

# Generate test coverage report
./gradlew test jacocoTestReport

# Check dependencies
./gradlew dependencies

# Build Docker image
./gradlew bootBuildImage

# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### IDE Configuration

#### IntelliJ IDEA
1. Import project as Gradle project
2. Set Java 21 as project SDK
3. Enable annotation processing for MapStruct
4. Configure code style for Spring Boot

#### VS Code
1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
3. Configure settings for Java 21
4. Set up recommended extensions

## 📖 Additional Documentation

- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [MapStruct Documentation](https://mapstruct.org/)
- [Flyway Documentation](https://flywaydb.org/documentation/)
- [OpenSearch Documentation](https://opensearch.org/docs/latest/)
- [Spring WebFlux Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
- [Kafka Documentation](https://kafka.apache.org/documentation/)

## 🤝 Contributing

### Development Guidelines

1. **Follow Hexagonal Architecture principles**
2. **Write comprehensive tests** for new features
3. **Update documentation** when adding new features
4. **Use meaningful commit messages**
5. **Follow existing code style and patterns**

### Contribution Process

1. Fork the project
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Review Checklist

- [ ] Hexagonal architecture principles are followed
- [ ] Tests are written and passing
- [ ] Documentation is updated
- [ ] Code follows project conventions
- [ ] No breaking changes without version bump

## 📄 License

This project is an open source seed/template, available under the MIT License.

## 👥 Authors

- **Ezequiel Aparicio** - *Initial work* - [Ezequiel Aparicio](https://github.com/ezeap)

## 🎯 Roadmap

### Immediate Goals
- [ ] **Comprehensive Test Suite**: Implement unit, integration, and E2E tests
- [ ] **CI/CD Pipeline**: GitHub Actions for automated testing and deployment
- [ ] **Code Quality**: SonarQube integration and coverage reporting

### Feature Enhancements
- [ ] **Authentication & Authorization**: JWT-based security with Spring Security
- [ ] **Caching Layer**: Redis integration for performance optimization
- [ ] **Circuit Breaker**: Resilience4j for fault tolerance
- [ ] **Monitoring**: Micrometer metrics and Prometheus integration
- [ ] **API Versioning**: Support for multiple API versions

### Advanced Features
- [ ] **GraphQL Support**: GraphQL endpoint alongside REST APIs
- [ ] **Distributed Tracing**: Spring Cloud Sleuth or Micrometer Tracing
- [ ] **Event Sourcing**: Advanced event-driven patterns
- [ ] **Multi-tenancy**: Support for multiple tenants
- [ ] **Background Jobs**: Spring Batch for processing large datasets

### Documentation and Examples
- [ ] **Tutorial Series**: Step-by-step guides for common use cases
- [ ] **Video Walkthroughs**: Architecture explanation and demos
- [ ] **Blog Posts**: Best practices and patterns
- [ ] **Sample Applications**: Real-world use case examples

## 📞 Support

### Getting Help

- **Issues**: Open an issue in the repository for bugs or feature requests
- **Discussions**: Use GitHub Discussions for questions and ideas
- **Documentation**: Check the comprehensive documentation first

### Community

- **Contributors**: All contributions are welcome!
- **Feedback**: Share your experience using this seed project
- **Improvements**: Suggest improvements or report issues

---

**Happy Coding! 🚀**

This seed project provides a solid foundation for building enterprise-grade Spring Boot applications with Hexagonal Architecture. Feel free to use it as a starting point for your projects!
