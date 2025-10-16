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
src/main/java/com/example/hexagonal/
├── domain/                          # Domain Layer (business core)
│   ├── model/                       # Domain entities
│   │   ├── Product.java
│   │   └── ProductStatus.java
│   └── port/                        # Port interfaces
│       ├── input/                   # Input ports (use cases)
│       │   └── ProductService.java
│       └── output/                  # Output ports (repositories, events)
│           ├── ProductRepositoryPort.java
│           ├── ProductSearchPort.java
│           └── ProductEventPublisherPort.java
│
├── application/                     # Application Layer (orchestration)
│   └── service/
│       └── ProductServiceImpl.java  # Use case implementation
│
└── infrastructure/                  # Infrastructure Layer (adapters)
    ├── adapter/
    │   ├── input/                   # Input adapters
    │   │   ├── rest/                # REST API
    │   │   │   ├── ProductController.java
    │   │   │   ├── dto/
    │   │   │   └── mapper/
    │   │   └── event/               # Kafka Consumer
    │   │       └── kafka/
    │   │           └── ProductKafkaConsumer.java
    │   │
    │   └── output/                  # Output adapters
    │       ├── persistence/         # Persistence
    │       │   ├── jpa/             # JPA Adapter
    │       │   │   ├── ProductJpaAdapter.java
    │       │   │   ├── entity/
    │       │   │   ├── repository/
    │       │   │   └── mapper/
    │       │   └── jdbc/            # JDBC Template Adapter
    │       │       └── ProductJdbcTemplateAdapter.java
    │       ├── event/               # Events
    │       │   └── kafka/           # Kafka Producer
    │       │       ├── ProductKafkaAdapter.java
    │       │       └── dto/
    │       └── search/              # Search
    │           └── ProductOpenSearchAdapter.java
    │
    └── config/                      # Configurations
        ├── OpenSearchConfig.java
        └── KafkaConfig.java
```

## 🚀 Technologies and Versions

### Core
- **Java**: 21 (with Records for immutable DTOs)
- **Spring Boot**: 3.4.10
- **Spring Framework**: 6.2.x (included in Spring Boot)

### Persistence
- **Spring Data JPA**: 3.4.x
- **JDBC Template**: Included in Spring Boot
- **PostgreSQL Driver**: Latest
- **Flyway**: 10.21.0

### Messaging
- **Spring Kafka**: 3.9.0

### Search
- **OpenSearch Java Client**: 2.17.1

### Mapping and Utilities
- **MapStruct**: 1.6.3 (compatible with Records)
- **Lombok**: 1.18.36 (only for mutable entities)

### Testing
- **JUnit 5**: Jupiter (included in Spring Boot)
- **Mockito**: (included in Spring Boot Test)
- **Testcontainers**: 1.20.4
- **Spring Boot Test**: 3.4.10

### Documentation
- **SpringDoc OpenAPI**: 2.7.0 (Swagger UI)

## 🎯 Key Features

### Java Records for Immutability
This project uses **Java Records** (Java 21) for all immutable objects:
- ✅ Request/Response DTOs
- ✅ Event DTOs (Kafka)
- ✅ Search Documents (OpenSearch)
- ✅ Value Objects

**Advantages**:
- Cleaner and more concise code
- Guaranteed immutability
- Thread-safe by design
- Automatic equals, hashCode and toString

**Example**:
```java
// Immutable record for DTOs
public record CreateProductRequest(
    @NotBlank String name,
    @NotNull BigDecimal price,
    Integer stock
) {}

// Usage in controller
@PostMapping
public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
    // ...
}
```

For more details on using records, see [RECORDS_GUIDE.md](RECORDS_GUIDE.md)

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
cd hexagonal-seed
```

### 2. Configure infrastructure with Docker

Create a `docker-compose.yml` file in the project root:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:16-alpine
    container_name: hexagonal-postgres
    environment:
      POSTGRES_DB: hexagonal_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  kafka:
    image: confluentinc/cp-kafka:7.6.0
    container_name: hexagonal-kafka
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
    container_name: hexagonal-zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - "2181:2181"

  opensearch:
    image: opensearchproject/opensearch:2.12.0
    container_name: hexagonal-opensearch
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
mvn clean install
```

### 5. Run the application

```bash
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080`

## 📚 Available Endpoints

### Swagger UI
- **URL**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

### REST API Endpoints

#### Create Product
```bash
POST /api/v1/products
Content-Type: application/json

{
  "name": "Laptop Dell XPS 15",
  "description": "High performance laptop",
  "price": 1299.99,
  "stock": 10,
  "category": "Electronics"
}
```

#### Get Product by ID
```bash
GET /api/v1/products/{id}
```

#### Get All Products
```bash
GET /api/v1/products
```

#### Get Products by Category
```bash
GET /api/v1/products/category/{category}
```

#### Get Available Products
```bash
GET /api/v1/products/available
```

#### Search Products (OpenSearch)
```bash
GET /api/v1/products/search?query=laptop
```

#### Update Product
```bash
PUT /api/v1/products/{id}
Content-Type: application/json

{
  "name": "Updated Product",
  "description": "Updated description",
  "price": 1499.99,
  "stock": 15,
  "category": "Electronics"
}
```

#### Delete Product
```bash
DELETE /api/v1/products/{id}
```

#### Reduce Stock
```bash
PATCH /api/v1/products/{id}/reduce-stock?quantity=5
```

## 🧪 Testing

### Run all tests

```bash
mvn test
```

### Run specific tests

```bash
# Service unit tests
mvn test -Dtest=ProductServiceImplTest

# Controller tests
mvn test -Dtest=ProductControllerTest

# Integration tests (requires Docker)
mvn test -Dtest=ProductIntegrationTest
```

### Test Coverage

```bash
mvn verify
```

Tests include:
- ✅ Unit tests with Mockito
- ✅ Integration tests with MockMvc
- ✅ End-to-end tests with Testcontainers
- ✅ JPA repository tests
- ✅ Adapter tests

## 🔄 Data Flow

### Product Creation

```
1. REST Request → ProductController (Input Adapter)
2. ProductController → ProductRestMapper (DTO → Domain)
3. ProductRestMapper → ProductService (Use Case)
4. ProductService → ProductRepositoryPort (Save)
5. ProductJpaAdapter → JPA Repository (Persistence)
6. ProductService → ProductSearchPort (Index)
7. ProductOpenSearchAdapter → OpenSearch (Search Index)
8. ProductService → ProductEventPublisherPort (Event)
9. ProductKafkaAdapter → Kafka Topic (Event Publish)
10. Response ← Product created successfully
```

### Event Consumption

```
1. Kafka Topic → ProductKafkaConsumer (Input Adapter)
2. ProductKafkaConsumer → Process Event
3. Business Logic → Actions based on event type
```

## 🗄️ Database

### Flyway Migrations

Migrations are located in: `src/main/resources/db/migration/`

- **V1__create_products_table.sql**: Creates products table
- **V2__insert_sample_data.sql**: Inserts sample data

### Run migrations manually

```bash
mvn flyway:migrate
```

### Clean database

```bash
mvn flyway:clean
```

## 📊 Monitoring and Observability

### Actuator Endpoints

Spring Boot Actuator is available at:
- Health: http://localhost:8080/actuator/health
- Info: http://localhost:8080/actuator/info
- Metrics: http://localhost:8080/actuator/metrics

## 🏗️ Patterns and Principles

### Hexagonal Architecture

- **Domain**: Pure business logic, no external dependencies
- **Ports**: Interfaces that define contracts
- **Adapters**: Concrete implementations of ports

### SOLID Principles

- ✅ Single Responsibility
- ✅ Open/Closed
- ✅ Liskov Substitution
- ✅ Interface Segregation
- ✅ Dependency Inversion

### Features

- ✅ Separation of concerns
- ✅ Testability
- ✅ Maintainability
- ✅ Scalability
- ✅ Framework independence

## 📝 Profile Configuration

### Development (default)
```bash
mvn spring-boot:run
```

### Production
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 🐳 Docker

### Build Docker image

```bash
mvn spring-boot:build-image
```

### Run with Docker

```bash
docker run -p 8080:8080 hexagonal-seed:1.0.0-SNAPSHOT
```

## 📖 Additional Documentation

- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [MapStruct Documentation](https://mapstruct.org/)
- [Flyway Documentation](https://flywaydb.org/documentation/)
- [OpenSearch Documentation](https://opensearch.org/docs/latest/)

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is an open source seed/template.

## 👥 Authors

- Ezequiel Aparicio

## 🎯 Roadmap

- [ ] Add Redis caching
- [ ] Implement JWT authentication
- [ ] Add more testing examples
- [ ] Implement Circuit Breaker with Resilience4j
- [ ] Add metrics with Micrometer
- [ ] Implement API versioning
- [ ] Add GraphQL support

## 📞 Support

For support, open an issue in the repository or contact the development team.
