package com.josemeneu.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

/**
 * Clase base para implementar el patrón Page Object Model.
 * Proporciona métodos comunes para interactuar con elementos de la página.
 */
public abstract class BasePage {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Page page;

    // Timeouts por defecto (en milisegundos)
    protected static final double DEFAULT_TIMEOUT = 10000;
    protected static final double SHORT_TIMEOUT = 5000;
    protected static final double LONG_TIMEOUT = 30000;

    public BasePage(Page page) {
        this.page = page;
    }

    // ==================== Navegación ====================

    /**
     * Navega a una URL.
     */
    public void navigate(String url) {
        logger.info("Navegando a: {}", url);
        page.navigate(url);
    }

    /**
     * Recarga la página actual.
     */
    public void reload() {
        logger.info("Recargando página");
        page.reload();
    }

    /**
     * Navega hacia atrás en el historial.
     */
    public void goBack() {
        logger.info("Navegando hacia atrás");
        page.goBack();
    }

    /**
     * Navega hacia adelante en el historial.
     */
    public void goForward() {
        logger.info("Navegando hacia adelante");
        page.goForward();
    }

    // ==================== Obtención de información ====================

    /**
     * Obtiene el título de la página.
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
     * Obtiene el texto de un elemento.
     */
    public String getText(String selector) {
        return page.locator(selector).textContent();
    }

    /**
     * Obtiene el valor de un atributo de un elemento.
     */
    public String getAttribute(String selector, String attribute) {
        return page.locator(selector).getAttribute(attribute);
    }

    /**
     * Obtiene el valor de un campo de entrada.
     */
    public String getInputValue(String selector) {
        return page.locator(selector).inputValue();
    }

    // ==================== Interacción con elementos ====================

    /**
     * Hace clic en un elemento.
     */
    public void click(String selector) {
        logger.debug("Click en: {}", selector);
        page.locator(selector).click();
    }

    /**
     * Hace doble clic en un elemento.
     */
    public void doubleClick(String selector) {
        logger.debug("Doble click en: {}", selector);
        page.locator(selector).dblclick();
    }

    /**
     * Hace clic derecho en un elemento.
     */
    public void rightClick(String selector) {
        logger.debug("Click derecho en: {}", selector);
        page.locator(selector).click(new Locator.ClickOptions()
                .setButton(com.microsoft.playwright.options.MouseButton.RIGHT));
    }

    /**
     * Pasa el ratón sobre un elemento (hover).
     */
    public void hover(String selector) {
        logger.debug("Hover sobre: {}", selector);
        page.locator(selector).hover();
    }

    /**
     * Escribe texto en un campo de entrada.
     */
    public void fill(String selector, String text) {
        logger.debug("Escribiendo '{}' en: {}", text, selector);
        page.locator(selector).fill(text);
    }

    /**
     * Limpia un campo de entrada.
     */
    public void clear(String selector) {
        logger.debug("Limpiando campo: {}", selector);
        page.locator(selector).clear();
    }

    /**
     * Escribe texto carácter por carácter (simulando escritura real).
     */
    public void type(String selector, String text) {
        logger.debug("Escribiendo (type) '{}' en: {}", text, selector);
        page.locator(selector).type(text);
    }

    /**
     * Presiona una tecla.
     */
    public void press(String selector, String key) {
        logger.debug("Presionando tecla '{}' en: {}", key, selector);
        page.locator(selector).press(key);
    }

    /**
     * Selecciona una opción de un dropdown por su valor.
     */
    public void selectByValue(String selector, String value) {
        logger.debug("Seleccionando valor '{}' en: {}", value, selector);
        page.locator(selector).selectOption(value);
    }

    /**
     * Selecciona una opción de un dropdown por su texto visible.
     */
    public void selectByText(String selector, String text) {
        logger.debug("Seleccionando texto '{}' en: {}", text, selector);
        page.locator(selector).selectOption(new com.microsoft.playwright.options.SelectOption().setLabel(text));
    }

    /**
     * Marca o desmarca un checkbox.
     */
    public void setChecked(String selector, boolean checked) {
        logger.debug("Estableciendo checkbox {} a: {}", selector, checked);
        page.locator(selector).setChecked(checked);
    }

    // ==================== Verificaciones ====================

    /**
     * Verifica si un elemento es visible.
     */
    public boolean isVisible(String selector) {
        return page.locator(selector).isVisible();
    }

    /**
     * Verifica si un elemento está habilitado.
     */
    public boolean isEnabled(String selector) {
        return page.locator(selector).isEnabled();
    }

    /**
     * Verifica si un checkbox está marcado.
     */
    public boolean isChecked(String selector) {
        return page.locator(selector).isChecked();
    }

    /**
     * Verifica si un elemento existe en el DOM.
     */
    public boolean exists(String selector) {
        return page.locator(selector).count() > 0;
    }

    // ==================== Esperas ====================

    /**
     * Espera a que un elemento sea visible.
     */
    public void waitForElement(String selector) {
        waitForElement(selector, DEFAULT_TIMEOUT);
    }

    /**
     * Espera a que un elemento sea visible con timeout personalizado.
     */
    public void waitForElement(String selector, double timeout) {
        logger.debug("Esperando elemento: {}", selector);
        page.locator(selector).waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeout));
    }

    /**
     * Espera a que un elemento desaparezca.
     */
    public void waitForElementToDisappear(String selector) {
        logger.debug("Esperando que desaparezca: {}", selector);
        page.locator(selector).waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(DEFAULT_TIMEOUT));
    }

    /**
     * Espera a que la página cargue completamente.
     */
    public void waitForPageLoad() {
        logger.debug("Esperando carga de página");
        page.waitForLoadState();
    }

    /**
     * Espera un tiempo específico (usar con precaución).
     */
    public void waitFor(long milliseconds) {
        logger.debug("Esperando {} ms", milliseconds);
        page.waitForTimeout(milliseconds);
    }

    // ==================== Capturas y debugging ====================

    /**
     * Toma una captura de pantalla.
     */
    public void takeScreenshot(String name) {
        String path = "screenshots/" + name + ".png";
        logger.info("Tomando captura de pantalla: {}", path);
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)));
    }

    /**
     * Toma una captura de pantalla de toda la página.
     */
    public void takeFullPageScreenshot(String name) {
        String path = "screenshots/" + name + "_full.png";
        logger.info("Tomando captura de pantalla completa: {}", path);
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(path))
                .setFullPage(true));
    }

    // ==================== Frames e iframes ====================

    /**
     * Cambia el contexto a un iframe.
     */
    public Page getFrame(String selector) {
        return page.frameLocator(selector).owner().page();
    }

    // ==================== JavaScript ====================

    /**
     * Ejecuta código JavaScript en la página.
     */
    public Object executeScript(String script) {
        logger.debug("Ejecutando script: {}", script);
        return page.evaluate(script);
    }

    /**
     * Hace scroll hasta un elemento.
     */
    public void scrollToElement(String selector) {
        logger.debug("Scroll hacia: {}", selector);
        page.locator(selector).scrollIntoViewIfNeeded();
    }
}
