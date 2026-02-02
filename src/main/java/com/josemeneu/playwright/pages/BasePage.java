package com.josemeneu.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

/**
 * Clase base para todas las páginas (Page Object Model).
 * Proporciona métodos comunes para interactuar con elementos web.
 */
public abstract class BasePage {

    protected final Page page;
    protected final Logger logger;

    public BasePage(Page page) {
        this.page = page;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * Navega a una URL específica.
     */
    public void navigate(String url) {
        logger.info("Navegando a: {}", url);
        page.navigate(url);
    }

    /**
     * Obtiene el título de la página actual.
     */
    public String getTitle() {
        return page.title();
    }

    /**
     * Obtiene la URL actual.
     */
    public String getCurrentUrl() {
        return page.url();
    }

    /**
     * Espera a que un elemento sea visible.
     */
    protected void waitForElement(String selector) {
        page.locator(selector).waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
    }

    /**
     * Hace clic en un elemento.
     */
    protected void click(String selector) {
        logger.debug("Clic en elemento: {}", selector);
        page.locator(selector).click();
    }

    /**
     * Escribe texto en un campo.
     */
    protected void fill(String selector, String text) {
        logger.debug("Escribiendo en {}: {}", selector, text);
        page.locator(selector).fill(text);
    }

    /**
     * Obtiene el texto de un elemento.
     */
    protected String getText(String selector) {
        return page.locator(selector).textContent();
    }

    /**
     * Verifica si un elemento es visible.
     */
    protected boolean isVisible(String selector) {
        return page.locator(selector).isVisible();
    }

    /**
     * Verifica si un elemento está habilitado.
     */
    protected boolean isEnabled(String selector) {
        return page.locator(selector).isEnabled();
    }

    /**
     * Toma una captura de pantalla.
     */
    public void takeScreenshot(String name) {
        String path = "screenshots/" + name + ".png";
        logger.info("Captura de pantalla guardada: {}", path);
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)));
    }

    /**
     * Toma una captura de pantalla de la página completa.
     */
    public void takeFullPageScreenshot(String name) {
        String path = "screenshots/" + name + "_full.png";
        logger.info("Captura de pantalla completa guardada: {}", path);
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(path))
                .setFullPage(true));
    }

    /**
     * Selecciona una opción de un dropdown por valor.
     */
    protected void selectByValue(String selector, String value) {
        logger.debug("Seleccionando valor {} en {}", value, selector);
        page.locator(selector).selectOption(value);
    }

    /**
     * Selecciona una opción de un dropdown por texto visible.
     */
    protected void selectByText(String selector, String text) {
        logger.debug("Seleccionando texto {} en {}", text, selector);
        page.locator(selector).selectOption(new com.microsoft.playwright.options.SelectOption().setLabel(text));
    }

    /**
     * Hace hover sobre un elemento.
     */
    protected void hover(String selector) {
        logger.debug("Hover sobre: {}", selector);
        page.locator(selector).hover();
    }

    /**
     * Hace scroll hasta un elemento.
     */
    protected void scrollToElement(String selector) {
        logger.debug("Scroll hacia: {}", selector);
        page.locator(selector).scrollIntoViewIfNeeded();
    }

    /**
     * Obtiene un atributo de un elemento.
     */
    protected String getAttribute(String selector, String attributeName) {
        return page.locator(selector).getAttribute(attributeName);
    }

    /**
     * Espera un tiempo específico (usar con precaución).
     */
    protected void waitFor(int milliseconds) {
        page.waitForTimeout(milliseconds);
    }
}
