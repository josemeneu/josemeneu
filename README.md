# Playwright Tests con Java - Proyecto Base para IntelliJ

Proyecto de automatización de pruebas web utilizando **Playwright para Java** con **JUnit 5**, optimizado para **IntelliJ IDEA**.

## Requisitos Previos

- **Java JDK 17** o superior
- **Maven 3.8+**
- **IntelliJ IDEA** (Community o Ultimate)

## Estructura del Proyecto

```
playwright-tests/
├── pom.xml                                    # Configuración Maven
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/josemeneu/playwright/
│   │   │       ├── config/
│   │   │       │   └── PlaywrightConfig.java  # Configuración de Playwright
│   │   │       └── pages/
│   │   │           ├── BasePage.java          # Clase base para Page Objects
│   │   │           └── GoogleHomePage.java    # Ejemplo de Page Object
│   │   └── resources/
│   │       └── logback.xml                    # Configuración de logging
│   └── test/
│       └── java/
│           └── com/josemeneu/playwright/tests/
│               ├── BaseTest.java              # Clase base para tests
│               ├── GoogleSearchTest.java      # Tests de ejemplo (Google)
│               ├── PlaywrightBasicsTest.java  # Tests de conceptos básicos
│               └── FormInteractionTest.java   # Tests de interacción con formularios
├── screenshots/                               # Capturas de pantalla
├── videos/                                    # Grabaciones de tests
└── test-results/                              # Resultados y logs
```

## Configuración Inicial

### 1. Clonar el Repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd josemeneu
```

### 2. Abrir en IntelliJ IDEA

1. Abre IntelliJ IDEA
2. Selecciona `File > Open`
3. Navega hasta la carpeta del proyecto
4. IntelliJ detectará automáticamente el archivo `pom.xml`

### 3. Instalar los Navegadores de Playwright

**Opción A - Desde IntelliJ:**
- Usa la configuración de ejecución `Install Playwright Browsers` (disponible en Run Configurations)

**Opción B - Desde terminal:**
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

**Opción C - Solo instalar Chromium:**
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"
```

## Ejecución de Tests

### Desde IntelliJ IDEA

El proyecto incluye configuraciones de ejecución predefinidas:

| Configuración | Descripción |
|--------------|-------------|
| `Run All Tests` | Ejecuta todos los tests en modo headless con Chromium |
| `Run Tests (Headed)` | Ejecuta los tests con el navegador visible |
| `Run Tests (Firefox)` | Ejecuta los tests con Firefox |
| `Install Playwright Browsers` | Instala los navegadores de Playwright |

### Desde Terminal (Maven)

```bash
# Ejecutar todos los tests (modo headless por defecto)
mvn test

# Ejecutar con navegador visible
mvn test -Dheadless=false

# Ejecutar con un navegador específico
mvn test -Dbrowser=firefox
mvn test -Dbrowser=webkit
mvn test -Dbrowser=chromium

# Usar perfiles predefinidos
mvn test -P headless        # Modo headless
mvn test -P chrome          # Usar Chromium
mvn test -P firefox         # Usar Firefox
mvn test -P webkit          # Usar WebKit (Safari)

# Combinar opciones
mvn test -Dbrowser=firefox -Dheadless=false

# Ejecutar una clase de test específica
mvn test -Dtest=GoogleSearchTest

# Ejecutar un test específico
mvn test -Dtest=GoogleSearchTest#shouldLoadGoogleHomePage
```

## Crear Nuevos Tests

### 1. Crear un Page Object

```java
package com.josemeneu.playwright.pages;

import com.microsoft.playwright.Page;

public class MiPagina extends BasePage {

    // Selectores
    private static final String MI_BOTON = "#miBoton";
    private static final String MI_INPUT = "input[name='miInput']";

    public MiPagina(Page page) {
        super(page);
    }

    public MiPagina abrir() {
        navigate("https://mi-sitio.com");
        return this;
    }

    public void hacerClic() {
        click(MI_BOTON);
    }

    public void escribirTexto(String texto) {
        fill(MI_INPUT, texto);
    }
}
```

### 2. Crear una Clase de Test

```java
package com.josemeneu.playwright.tests;

import com.josemeneu.playwright.pages.MiPagina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests de Mi Página")
class MiPaginaTest extends BaseTest {

    private MiPagina miPagina;

    @BeforeEach
    void setupPage() {
        miPagina = new MiPagina(page);
    }

    @Test
    @DisplayName("Debería cargar la página correctamente")
    void shouldLoadPage() {
        miPagina.abrir();
        assertThat(miPagina.getTitle()).contains("Mi Sitio");
    }
}
```

## Funcionalidades Incluidas

### Page Object Model (POM)
- `BasePage.java`: Clase base con métodos comunes
- `GoogleHomePage.java`: Ejemplo de implementación

### Características de Playwright
- Soporte para Chromium, Firefox y WebKit
- Modo headless configurable
- Capturas de pantalla automáticas
- Grabación de video
- Logging con Logback

### Métodos Disponibles en BasePage

| Método | Descripción |
|--------|-------------|
| `navigate(url)` | Navega a una URL |
| `click(selector)` | Hace clic en un elemento |
| `fill(selector, text)` | Escribe texto en un campo |
| `getText(selector)` | Obtiene el texto de un elemento |
| `isVisible(selector)` | Verifica si un elemento es visible |
| `waitForElement(selector)` | Espera a que un elemento sea visible |
| `takeScreenshot(name)` | Toma una captura de pantalla |
| `hover(selector)` | Hace hover sobre un elemento |
| `selectByValue(selector, value)` | Selecciona una opción por valor |

## Recursos de Aprendizaje

- [Documentación oficial de Playwright Java](https://playwright.dev/java/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [AssertJ Documentation](https://assertj.github.io/doc/)

## Autor

**José Pascual Meneu**
- Email: jpmeneu@gmail.com
- LinkedIn: [linkedin.com/in/josemeneu](https://www.linkedin.com/in/josemeneu)
- Web: [josemeneu.com](https://josemeneu.com)
