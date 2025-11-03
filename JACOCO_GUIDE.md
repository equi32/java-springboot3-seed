# 🎯 Guía Completa para JaCoCo - Reportes de Cobertura de Tests

## 📋 Prerrequisitos

### 1. Instalar Java 21
```bash
# Para Ubuntu/Debian:
sudo apt update
sudo apt install openjdk-21-jdk

# Para macOS con Homebrew:
brew install openjdk@21

# Para Windows:
# Descargar desde https://adoptium.net/
```

### 2. Configurar JAVA_HOME
```bash
# Para Linux/macOS (agregar a ~/.bashrc o ~/.zshrc):
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Para Windows (en Variables de Entorno):
# JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-21.x.x.x-hotspot\
```

## 🚀 Comandos para Ejecutar JaCoCo

### Generar Reportes de Cobertura
```bash
# Ejecutar tests y generar reportes
./gradlew test jacocoTestReport

# Solo generar reportes (si los tests ya se ejecutaron)
./gradlew jacocoTestReport

# Verificar cobertura contra umbrales configurados
./gradlew jacocoTestCoverageVerification

# Ejecutar check completo (incluye verificación de cobertura)
./gradlew check
```

### Limpiar y Regenerar
```bash
# Limpiar y ejecutar todo desde cero
./gradlew clean test jacocoTestReport

# Forzar regeneración de reportes
./gradlew clean jacocoTestReport
```

## 📊 Visualización de Reportes

### 1. Reporte HTML (Recomendado)
```bash
# Los reportes se generan en:
build/reports/jacoco/html/index.html
```

**Para visualizar:**
```bash
# Abrir en navegador (Linux/macOS)
xdg-open build/reports/jacoco/html/index.html
# o
open build/reports/jacoco/html/index.html

# Para Windows:
start build/reports/jacoco/html/index.html
```

### 2. Reporte XML (Para CI/CD)
```bash
# Ubicación del XML:
build/reports/jacoco/test/jacocoTestReport.xml
```

## 🎯 Configuración Implementada

### Umbrales de Cobertura por Capa

- **🏗️ Domain Layer**: 80% (lógica de negocio crítica)
- **⚙️ Application Layer**: 70% (use cases y orquestación)
- **🔌 Infrastructure Layer**: 50% (adapters y configuración)
- **📊 General**: 60% (promedio del proyecto)

### Clases Excluidas
- DTOs, Requests, Responses (data transfer objects)
- Records y Documents (estructuras inmutables)
- Clases de configuración (Spring configs)
- Implementaciones generadas por MapStruct
- Clases principales de Spring Boot
- Excepciones simples y constantes

## 📈 Métricas Importantes que JaCoCo Muestra

### 1. **Instruction Coverage** (Cobertura de Instrucciones)
- Mide el porcentaje de bytecode ejecutado
- Más preciso que cobertura de líneas

### 2. **Branch Coverage** (Cobertura de Ramas)
- Mide si todas las condiciones (if/else, switch) fueron probadas
- Crítico para validar lógica condicional

### 3. **Line Coverage** (Cobertura de Líneas)
- Mide líneas de código ejecutadas
- Métrica más básica pero fácil de entender

### 4. **Complexity Coverage** (Cobertura de Complejidad)
- Mide cobertura de caminos complejos
- Útil para identificar código complejo sin probar

## 🔍 Interpretación de Reportes

### Colores en el Reporte HTML
- **🟢 Verde**: Código cubierto por tests
- **🔴 Rojo**: Código no cubierto por tests
- **🟡 Amarillo**: Cobertura parcial (algunas ramas no probadas)

### Métricas por Clase
- **Coverage %**: Porcentaje total de cobertura
- **Missed Instructions**: Instrucciones no ejecutadas
- **Missed Branches**: Ramas condicionales no probadas
- **Cxty**: Complejidad ciclomática

## 🛠️ Integración con CI/CD

### GitHub Actions
```yaml
- name: Run tests with coverage
  run: ./gradlew test jacocoTestReport

- name: Upload coverage reports
  uses: codecov/codecov-action@v3
  with:
    file: ./build/reports/jacoco/test/jacocoTestReport.xml
```

### SonarQube
```bash
# Los reportes XML de JaCoCo son compatibles con SonarQube
# Se configuran automáticamente en Sonar para análisis de calidad
```

## 📝 Buenas Prácticas

### 1. **Estrategia de Exclusiones**
Excluir elementos que no aportan valor:
- DTOs y Records
- Configuración de Spring
- Clases generadas automáticamente
- Excepciones simples

### 2. **Umbrales Realistas**
- Domain: 80-90% (lógica de negocio)
- Application: 70-80% (use cases)
- Infrastructure: 50-70% (adapters)
- Tests: 90-95% (los tests themselves)

### 3. **Integración con Workflow**
```bash
# En desarrollo local:
./gradlew test jacocoTestReport

# Antes de commit:
./gradlew check

# Para build completo:
./gradlew clean build check
```

## 🚨 Solución de Problemas Comunes

### Problema: "No class files found"
```bash
# Solución: Asegurarse de compilar primero
./gradlew compileJava testClasses
./gradlew jacocoTestReport
```

### Problema: Reporte vacío
```bash
# Verificar que los tests se ejecuten correctamente
./gradlew test --info
```

### Problema: Alto uso de memoria
```bash
# Aumentar memoria para Gradle
./gradlew -Xmx2g -XX:MaxMetaspaceSize=512m test jacocoTestReport
```

## 📚 Recursos Adicionales

- [Documentación Oficial de JaCoCo](https://www.jacoco.org/jacoco/trunk/doc/)
- [JaCoCo Gradle Plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
- [Best Practices for Code Coverage](https://martinfowler.com/articles/origin-of-test-coverage.html)