package com.josemeneu.playwright.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object para la página de inicio de Google.
 * Ejemplo de implementación del patrón Page Object Model (POM).
 */
public class GoogleHomePage extends BasePage {

    // URL
    private static final String URL = "https://www.google.com";

    // Selectores
    private static final String SEARCH_INPUT = "textarea[name='q'], input[name='q']";
    private static final String SEARCH_BUTTON = "input[name='btnK']";
    private static final String FEELING_LUCKY_BUTTON = "input[name='btnI']";
    private static final String SEARCH_RESULTS = "#search";
    private static final String RESULT_LINKS = "#search a h3";

    public GoogleHomePage(Page page) {
        super(page);
    }

    /**
     * Navega a la página de inicio de Google.
     */
    public GoogleHomePage open() {
        navigate(URL);
        return this;
    }

    /**
     * Escribe texto en el campo de búsqueda.
     */
    public GoogleHomePage enterSearchTerm(String searchTerm) {
        logger.info("Buscando: {}", searchTerm);
        fill(SEARCH_INPUT, searchTerm);
        return this;
    }

    /**
     * Hace clic en el botón de búsqueda.
     */
    public GoogleHomePage clickSearchButton() {
        // Presionar Enter es más confiable que hacer clic en el botón
        page.locator(SEARCH_INPUT).press("Enter");
        return this;
    }

    /**
     * Realiza una búsqueda completa.
     */
    public GoogleHomePage search(String searchTerm) {
        enterSearchTerm(searchTerm);
        clickSearchButton();
        waitForResults();
        return this;
    }

    /**
     * Espera a que aparezcan los resultados de búsqueda.
     */
    public GoogleHomePage waitForResults() {
        waitForElement(SEARCH_RESULTS);
        return this;
    }

    /**
     * Verifica si hay resultados de búsqueda.
     */
    public boolean hasSearchResults() {
        return isVisible(SEARCH_RESULTS);
    }

    /**
     * Obtiene el número de resultados visibles.
     */
    public int getResultCount() {
        return page.locator(RESULT_LINKS).count();
    }

    /**
     * Obtiene el texto del primer resultado.
     */
    public String getFirstResultTitle() {
        return page.locator(RESULT_LINKS).first().textContent();
    }

    /**
     * Hace clic en el primer resultado.
     */
    public void clickFirstResult() {
        page.locator(RESULT_LINKS).first().click();
    }

    /**
     * Verifica si el campo de búsqueda es visible.
     */
    public boolean isSearchInputVisible() {
        return isVisible(SEARCH_INPUT);
    }
}
