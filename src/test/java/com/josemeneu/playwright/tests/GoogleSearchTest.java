package com.josemeneu.playwright.tests;

import com.josemeneu.playwright.pages.GoogleHomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de ejemplo para la búsqueda en Google.
 * Demuestra el uso del patrón Page Object Model con Playwright.
 */
@DisplayName("Tests de búsqueda en Google")
class GoogleSearchTest extends BaseTest {

    private GoogleHomePage googleHomePage;

    @BeforeEach
    void setupPage() {
        googleHomePage = new GoogleHomePage(page);
    }

    @Test
    @DisplayName("Debería cargar la página de inicio de Google")
    void shouldLoadGoogleHomePage() {
        // Given & When
        googleHomePage.open();

        // Then
        assertThat(googleHomePage.getTitle()).containsIgnoringCase("google");
        assertThat(googleHomePage.isSearchInputVisible()).isTrue();

        takeScreenshot("google_home_page");
    }

    @Test
    @DisplayName("Debería mostrar resultados al buscar")
    void shouldShowSearchResults() {
        // Given
        googleHomePage.open();

        // When
        googleHomePage.search("Playwright Java");

        // Then
        assertThat(googleHomePage.hasSearchResults()).isTrue();
        assertThat(googleHomePage.getResultCount()).isGreaterThan(0);

        takeScreenshot("google_search_results");
    }

    @Test
    @DisplayName("Debería contener 'Playwright' en los resultados")
    void shouldContainPlaywrightInResults() {
        // Given
        googleHomePage.open();

        // When
        googleHomePage.search("Microsoft Playwright");

        // Then
        assertThat(googleHomePage.hasSearchResults()).isTrue();
        String firstResult = googleHomePage.getFirstResultTitle();
        logger.info("Primer resultado: {}", firstResult);

        takeScreenshot("playwright_search");
    }
}
