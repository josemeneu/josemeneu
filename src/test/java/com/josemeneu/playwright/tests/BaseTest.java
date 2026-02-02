package com.josemeneu.playwright.tests;

import com.josemeneu.playwright.config.PlaywrightConfig;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

/**
 * Clase base para todos los tests de Playwright.
 * Proporciona la configuración común y métodos de utilidad.
 */
public abstract class BaseTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected PlaywrightConfig config;
    protected Page page;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        logger.info("========================================");
        logger.info("Iniciando test: {}", testInfo.getDisplayName());
        logger.info("========================================");

        config = new PlaywrightConfig();
        config.setup();
        page = config.getPage();
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        logger.info("Finalizando test: {}", testInfo.getDisplayName());

        if (config != null) {
            config.teardown();
        }

        logger.info("========================================\n");
    }

    /**
     * Toma una captura de pantalla con nombre personalizado.
     */
    protected void takeScreenshot(String name) {
        String path = "screenshots/" + name + ".png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)));
        logger.info("Captura guardada: {}", path);
    }

    /**
     * Toma una captura de pantalla de la página completa.
     */
    protected void takeFullPageScreenshot(String name) {
        String path = "screenshots/" + name + "_full.png";
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(path))
                .setFullPage(true));
        logger.info("Captura completa guardada: {}", path);
    }

    /**
     * Espera un tiempo específico (usar con precaución).
     */
    protected void wait(int milliseconds) {
        page.waitForTimeout(milliseconds);
    }
}
