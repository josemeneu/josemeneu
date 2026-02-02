package com.josemeneu.playwright.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object para la página principal de Google.
 * Ejemplo de implementación del patrón Page Object Model.
 */
public class GoogleHomePage extends BasePage {

    // URL
    private static final String URL = "https://www.google.com";

    // Selectores
    private static final String SEARCH_INPUT = "textarea[name='q'], input[name='q']";
    private static final String SEARCH_BUTTON = "input[name='btnK']";
    private static final String LUCKY_BUTTON = "input[name='btnI']";
    private static final String ACCEPT_COOKIES_BUTTON = "button#L2AGLb, button:has-text('Aceptar todo')";
    private static final String SEARCH_RESULTS = "#search";
    private static final String RESULT_LINKS = "#search a h3";

    public GoogleHomePage(Page page) {
        super(page);
    }

    /**
     * Navega a la página principal de Google.
     */
    public GoogleHomePage open() {
        navigate(URL);
        acceptCookiesIfPresent();
        return this;
    }

    /**
     * Acepta las cookies si aparece el diálogo.
     */
    public GoogleHomePage acceptCookiesIfPresent() {
        try {
            if (isVisible(ACCEPT_COOKIES_BUTTON)) {
                click(ACCEPT_COOKIES_BUTTON);
                waitFor(500);
                logger.info("Cookies aceptadas");
            }
        } catch (Exception e) {
            logger.debug("No se encontró el diálogo de cookies");
        }
        return this;
    }

    /**
     * Escribe texto en el campo de búsqueda.
     */
    public GoogleHomePage typeSearchQuery(String query) {
        waitForElement(SEARCH_INPUT);
        fill(SEARCH_INPUT, query);
        return this;
    }

    /**
     * Hace clic en el botón de búsqueda.
     */
    public GoogleHomePage clickSearchButton() {
        // Google a veces muestra sugerencias que cubren el botón
        press(SEARCH_INPUT, "Enter");
        return this;
    }

    /**
     * Realiza una búsqueda completa.
     */
    public GoogleHomePage search(String query) {
        typeSearchQuery(query);
        clickSearchButton();
        waitForSearchResults();
        return this;
    }

    /**
     * Hace clic en "Voy a tener suerte".
     */
    public GoogleHomePage clickLuckyButton() {
        click(LUCKY_BUTTON);
        return this;
    }

    /**
     * Espera a que aparezcan los resultados de búsqueda.
     */
    public GoogleHomePage waitForSearchResults() {
        waitForElement(SEARCH_RESULTS);
        return this;
    }

    /**
     * Verifica si el campo de búsqueda es visible.
     */
    public boolean isSearchInputVisible() {
        return isVisible(SEARCH_INPUT);
    }

    /**
     * Verifica si hay resultados de búsqueda.
     */
    public boolean hasSearchResults() {
        return exists(RESULT_LINKS);
    }

    /**
     * Obtiene el número de resultados visibles.
     */
    public int getResultCount() {
        return page.locator(RESULT_LINKS).count();
    }

    /**
     * Hace clic en el primer resultado.
     */
    public void clickFirstResult() {
        page.locator(RESULT_LINKS).first().click();
    }

    /**
     * Obtiene el texto del primer resultado.
     */
    public String getFirstResultText() {
        return page.locator(RESULT_LINKS).first().textContent();
    }

    /**
     * Limpia el campo de búsqueda.
     */
    public GoogleHomePage clearSearch() {
        clear(SEARCH_INPUT);
        return this;
    }

    /**
     * Verifica si estamos en la página de Google.
     */
    public boolean isOnGooglePage() {
        return getCurrentUrl().contains("google.com");
    }
}
