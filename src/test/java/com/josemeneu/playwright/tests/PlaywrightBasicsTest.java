package com.josemeneu.playwright.tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests que demuestran las funcionalidades básicas de Playwright.
 * Ideal para aprender los conceptos fundamentales.
 */
@DisplayName("Conceptos básicos de Playwright")
class PlaywrightBasicsTest extends BaseTest {

    private static final String TEST_URL = "https://playwright.dev/";

    @Nested
    @DisplayName("Navegación")
    class NavigationTests {

        @Test
        @DisplayName("Debería navegar a una URL")
        void shouldNavigateToUrl() {
            // When
            page.navigate(TEST_URL);

            // Then
            assertThat(page.url()).contains("playwright.dev");
        }

        @Test
        @DisplayName("Debería obtener el título de la página")
        void shouldGetPageTitle() {
            // When
            page.navigate(TEST_URL);

            // Then
            String title = page.title();
            assertThat(title).containsIgnoringCase("playwright");
            logger.info("Título de la página: {}", title);
        }
    }

    @Nested
    @DisplayName("Selectores")
    class SelectorTests {

        @Test
        @DisplayName("Debería encontrar elementos por CSS selector")
        void shouldFindElementByCssSelector() {
            // Given
            page.navigate(TEST_URL);

            // When
            Locator logo = page.locator(".navbar__logo");

            // Then
            assertThat(logo.isVisible()).isTrue();
        }

        @Test
        @DisplayName("Debería encontrar elementos por rol")
        void shouldFindElementByRole() {
            // Given
            page.navigate(TEST_URL);

            // When
            Locator links = page.getByRole(AriaRole.LINK);

            // Then
            assertThat(links.count()).isGreaterThan(0);
            logger.info("Número de enlaces encontrados: {}", links.count());
        }

        @Test
        @DisplayName("Debería encontrar elementos por texto")
        void shouldFindElementByText() {
            // Given
            page.navigate(TEST_URL);

            // When
            Locator getStarted = page.getByRole(AriaRole.LINK,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName("Get started"));

            // Then
            assertThat(getStarted.isVisible()).isTrue();
        }
    }

    @Nested
    @DisplayName("Interacciones")
    class InteractionTests {

        @Test
        @DisplayName("Debería hacer clic en un enlace")
        void shouldClickLink() {
            // Given
            page.navigate(TEST_URL);
            String initialUrl = page.url();

            // When
            page.getByRole(AriaRole.LINK,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName("Get started")).click();

            // Then
            assertThat(page.url()).isNotEqualTo(initialUrl);
            logger.info("URL después del clic: {}", page.url());
        }

        @Test
        @DisplayName("Debería hacer hover sobre un elemento")
        void shouldHoverElement() {
            // Given
            page.navigate(TEST_URL);

            // When & Then (no debería lanzar excepciones)
            Locator logo = page.locator(".navbar__logo");
            logo.hover();

            assertThat(logo.isVisible()).isTrue();
        }
    }

    @Nested
    @DisplayName("Esperas")
    class WaitTests {

        @Test
        @DisplayName("Debería esperar a que un elemento sea visible")
        void shouldWaitForElementToBeVisible() {
            // Given
            page.navigate(TEST_URL);

            // When
            Locator content = page.locator(".hero");
            content.waitFor();

            // Then
            assertThat(content.isVisible()).isTrue();
        }

        @Test
        @DisplayName("Debería esperar la carga de la página")
        void shouldWaitForPageLoad() {
            // When
            page.navigate(TEST_URL);
            page.waitForLoadState();

            // Then
            assertThat(page.title()).isNotEmpty();
        }
    }

    @Nested
    @DisplayName("Capturas de pantalla")
    class ScreenshotTests {

        @Test
        @DisplayName("Debería tomar una captura de pantalla")
        void shouldTakeScreenshot() {
            // Given
            page.navigate(TEST_URL);

            // When
            takeScreenshot("playwright_homepage");

            // Then - El screenshot se guardó correctamente (verificar manualmente)
            logger.info("Captura de pantalla tomada exitosamente");
        }

        @Test
        @DisplayName("Debería tomar una captura de pantalla de la página completa")
        void shouldTakeFullPageScreenshot() {
            // Given
            page.navigate(TEST_URL);

            // When
            takeFullPageScreenshot("playwright_homepage_full");

            // Then
            logger.info("Captura de pantalla completa tomada exitosamente");
        }
    }
}
