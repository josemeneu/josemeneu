package com.josemeneu.playwright.tests;

import com.josemeneu.playwright.pages.GoogleHomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de ejemplo usando la página de Google.
 * Demuestra el uso del patrón Page Object Model.
 */
@DisplayName("Tests de búsqueda en Google")
class GoogleSearchTest extends BaseTest {

    private GoogleHomePage googlePage;

    @BeforeEach
    void setupPage() {
        googlePage = new GoogleHomePage(page);
    }

    @Test
    @DisplayName("Debería cargar la página de Google correctamente")
    void shouldLoadGoogleHomePage() {
        // When
        googlePage.open();

        // Then
        assertThat(googlePage.isOnGooglePage()).isTrue();
        assertThat(googlePage.isSearchInputVisible()).isTrue();

        takeScreenshot("google_homepage");
    }

    @Test
    @DisplayName("Debería realizar una búsqueda y mostrar resultados")
    void shouldSearchAndShowResults() {
        // Given
        googlePage.open();

        // When
        googlePage.search("Playwright Java");

        // Then
        assertThat(googlePage.hasSearchResults()).isTrue();
        assertThat(googlePage.getResultCount()).isGreaterThan(0);

        takeScreenshot("google_search_results");
    }

    @Test
    @DisplayName("Debería poder escribir en el campo de búsqueda")
    void shouldTypeInSearchField() {
        // Given
        googlePage.open();
        String searchQuery = "automatización de pruebas";

        // When
        googlePage.typeSearchQuery(searchQuery);

        // Then
        assertThat(googlePage.isSearchInputVisible()).isTrue();

        takeScreenshot("google_search_typed");
    }

    @Test
    @DisplayName("El título de la página debería contener Google")
    void pageTitleShouldContainGoogle() {
        // When
        googlePage.open();

        // Then
        assertThat(googlePage.getTitle()).containsIgnoringCase("google");
    }
}
