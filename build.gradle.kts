plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.flywaydb.flyway") version "11.10.1"
	id("checkstyle")
	jacoco
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

// Configuración de JaCoCo para reportes de cobertura de código
jacoco {
	toolVersion = "0.8.11"
}

// Configurar la tarea de test para generar reportes de JaCoCo
tasks.withType<Test> {
	useJUnitPlatform()

	// Configurar JaCoCo agent
	jacoco {
		// Excluir clases que no queremos medir cobertura
		excludes.addAll(listOf(
			// DTOs y Records generalmente no necesitan tests complejos
			"**/dto/**",
			"**/response/**",
			"**/request/**",
			"**/document/**",
			"**/event/**",
			// Configuración
			"**/config/**",
			// Clases generated por MapStruct
			"**/mapper/**/*Impl.class",
			// Main application
			"**/SeedApplication.class",
			// Excepciones simples
			"**/exception/**",
			// Constantes y enums simples
			"**/constants/**",
			"**/ProblemType.class"
		))
	}
}

// Tarea para generar reportes HTML de JaCoCo
tasks.jacocoTestReport {
	dependsOn(tasks.test)

	reports {
		html.required.set(true) // Reporte HTML para visualización
		xml.required.set(true)  // XML para herramientas CI/CD
		csv.required.set(false) // CSV no necesitamos

		// Configurar ubicación de los reportes
		html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/html"))
		xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml"))
	}

	// Excluir los mismos paquetes que en la configuración del test
	classDirectories.setFrom(
		files(classDirectories.files.map { dir ->
			fileTree(dir).apply {
				exclude("**/dto/**")
				exclude("**/response/**")
				exclude("**/request/**")
				exclude("**/document/**")
				exclude("**/event/**")
				exclude("**/config/**")
				exclude("**/mapper/**/*Impl.class")
				exclude("**/SeedApplication.class")
				exclude("**/exception/**")
				exclude("**/constants/**")
				exclude("**/ProblemType.class")
			}
		})
	)
}

// Tarea para verificar umbrales de cobertura
tasks.jacocoTestCoverageVerification {
	dependsOn(tasks.jacocoTestReport)

	violationRules {
		rule {
			// Regla general para todo el proyecto
			limit {
				minimum = 0.60.toBigDecimal() // 60% de cobertura mínima
			}
		}

		rule {
			// Regla más estricta para el paquete domain (lógica de negocio)
			limit {
				minimum = 0.80.toBigDecimal() // 80% para domain layer
			}
			includes = listOf("gov.justucuman.seed.domain.**")
		}

		rule {
			// Regla moderada para application layer (use cases)
			limit {
				minimum = 0.70.toBigDecimal() // 70% para application layer
			}
			includes = listOf("gov.justucuman.seed.application.**")
		}

		rule {
			// Regla menos estricta para infrastructure (adapters)
			limit {
				minimum = 0.50.toBigDecimal() // 50% para infrastructure layer
			}
			includes = listOf("gov.justucuman.seed.infrastructure.**")
		}
	}
}

// Hacer que 'check' dependa de la verificación de cobertura
tasks.check {
	dependsOn(tasks.jacocoTestCoverageVerification)
}

// Configurar task para generar reportes siempre después de los tests
tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}
