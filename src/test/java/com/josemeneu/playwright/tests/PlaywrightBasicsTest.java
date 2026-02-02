package com.josemeneu.playwright.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests que demuestran las funcionalidades básicas de Playwright.
 */
@DisplayName("Conceptos básicos de Playwright")
class PlaywrightBasicsTest extends BaseTest {

    @Test
    @DisplayName("Debería navegar a una URL y obtener el título")
    void shouldNavigateAndGetTitle() {
        // When
        page.navigate("https://playwright.dev");

        // Then
        String title = page.title();
        assertThat(title).contains("Playwright");

        takeScreenshot("playwright_homepage");
    }

    @Test
    @DisplayName("Debería encontrar elementos por selector CSS")
    void shouldFindElementsByCssSelector() {
        // Given
        page.navigate("https://playwright.dev");

        // When
        boolean headerExists = page.locator("header").count() > 0;

        // Then
        assertThat(headerExists).isTrue();
    }

    @Test
    @DisplayName("Debería obtener el texto de un elemento")
    void shouldGetElementText() {
        // Given
        page.navigate("https://playwright.dev");

        // When
        String headerText = page.locator("h1").first().textContent();

        // Then
        assertThat(headerText).isNotEmpty();
        logger.info("Texto del header: {}", headerText);
    }

    @Test
    @DisplayName("Debería verificar que un elemento es visible")
    void shouldCheckElementVisibility() {
        // Given
        page.navigate("https://playwright.dev");

        // When
        boolean isVisible = page.locator("header").isVisible();

        // Then
        assertThat(isVisible).isTrue();
    }

    @Test
    @DisplayName("Debería hacer clic en un enlace")
    void shouldClickLink() {
        // Given
        page.navigate("https://playwright.dev");

        // When
        page.locator("a:has-text('Get started')").first().click();

        // Then
        page.waitForLoadState();
        assertThat(page.url()).contains("docs");

        takeScreenshot("playwright_docs");
    }

    @Test
    @DisplayName("Debería esperar a que un elemento aparezca")
    void shouldWaitForElement() {
        // Given
        page.navigate("https://playwright.dev");

        // When/Then
        page.locator("header").waitFor();
        assertThat(page.locator("header").isVisible()).isTrue();
    }

    @Test
    @DisplayName("Debería obtener múltiples elementos")
    void shouldGetMultipleElements() {
        // Given
        page.navigate("https://playwright.dev");

        // When
        int linkCount = page.locator("a").count();

        // Then
        assertThat(linkCount).isGreaterThan(0);
        logger.info("Número de enlaces encontrados: {}", linkCount);
    }

    @Test
    @DisplayName("Debería tomar una captura de pantalla completa")
    void shouldTakeFullPageScreenshot() {
        // Given
        page.navigate("https://playwright.dev");

        // When/Then
        takeFullPageScreenshot("playwright_full_page");
    }

    @Test
    @DisplayName("Debería obtener atributos de elementos")
    void shouldGetElementAttributes() {
        // Given
        page.navigate("https://playwright.dev");

        // When
        String href = page.locator("a:has-text('Get started')").first().getAttribute("href");

        // Then
        assertThat(href).isNotNull();
        logger.info("Href del enlace: {}", href);
    }

    @Test
    @DisplayName("Debería verificar la URL actual")
    void shouldVerifyCurrentUrl() {
        // Given
        String expectedUrl = "https://playwright.dev/";

        // When
        page.navigate(expectedUrl);

        // Then
        assertThat(page.url()).startsWith("https://playwright.dev");
    }
}
