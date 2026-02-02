package com.josemeneu.playwright.tests;

import com.josemeneu.playwright.config.PlaywrightConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

/**
 * Clase base para todos los tests.
 * Gestiona el ciclo de vida de Playwright y proporciona utilidades comunes.
 */
public abstract class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected static PlaywrightConfig config;
    protected static Playwright playwright;
    protected static Browser browser;

    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void setupAll() {
        logger.info("=== Iniciando suite de tests ===");
        config = new PlaywrightConfig();
        config.setup();
        playwright = config.getPlaywright();
        browser = config.getBrowser();
        logger.info("Navegador configurado: {}", config.getBrowserType());
        logger.info("Modo headless: {}", config.isHeadless());
    }

    @AfterAll
    static void teardownAll() {
        logger.info("=== Finalizando suite de tests ===");
        if (config != null) {
            config.teardown();
        }
    }

    @BeforeEach
    void setup(TestInfo testInfo) {
        logger.info("--- Iniciando test: {} ---", testInfo.getDisplayName());

        // Crear un nuevo contexto para cada test (aislamiento)
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setRecordVideoDir(Paths.get("videos/")));

        page = context.newPage();

        // Configurar timeout por defecto
        page.setDefaultTimeout(config.getDefaultTimeout());
    }

    @AfterEach
    void teardown(TestInfo testInfo) {
        logger.info("--- Finalizando test: {} ---", testInfo.getDisplayName());

        // Tomar screenshot si el test falló (útil para depuración)
        // Nota: JUnit 5 no proporciona el estado del test directamente en @AfterEach

        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
    }

    /**
     * Toma una captura de pantalla con un nombre descriptivo.
     */
    protected void takeScreenshot(String testName) {
        String fileName = "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileName)));
        logger.info("Screenshot guardado: {}", fileName);
    }

    /**
     * Toma una captura de pantalla de la página completa.
     */
    protected void takeFullPageScreenshot(String testName) {
        String fileName = "screenshots/" + testName + "_full_" + System.currentTimeMillis() + ".png";
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(fileName))
                .setFullPage(true));
        logger.info("Screenshot de página completa guardado: {}", fileName);
    }
}
