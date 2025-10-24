plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.flywaydb.flyway") version "11.10.1"
	id("checkstyle")
}

group = "gov.justucuman"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

checkstyle {
	toolVersion = "10.26.1"
	configFile = file("${project.rootDir}/config/codestyle/checks.xml")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// Data Access (JPA, PostgreSQL, Redis)
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.postgresql:postgresql")

	// OpenAPI (Swagger UI)
//    implementation("org.springdoc:springdoc-openapi-ui:1.8.0") //WebMVC
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13") //WebMVC
//	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.6.0") //WebFlux

	// MapStruct
	implementation("org.mapstruct:mapstruct:1.6.3")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

	//Flyway
	implementation("org.flywaydb:flyway-core:11.10.1")
	implementation("org.flywaydb:flyway-database-postgresql:11.10.1")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test") //Webflux test

    //Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    //Kafka
    implementation("org.springframework.kafka:spring-kafka:3.3.1")

    //OpenSearch
    implementation("org.opensearch.client:opensearch-java:3.2.0")
//    implementation("org.opensearch.client:opensearch-rest-client:3.3.1")
//    implementation("org.apache.httpcomponents.client5:httpclient5:5.4.1")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
