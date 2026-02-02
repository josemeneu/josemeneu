package com.josemeneu.playwright.config;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuración centralizada para Playwright.
 * Gestiona la creación de instancias de Playwright, Browser, BrowserContext y Page.
 */
public class PlaywrightConfig {

    private static final Logger logger = LoggerFactory.getLogger(PlaywrightConfig.class);

    // Propiedades del sistema para configuración
    private static final String BROWSER_PROPERTY = "browser";
    private static final String HEADLESS_PROPERTY = "headless";

    // Valores por defecto
    private static final String DEFAULT_BROWSER = "chromium";
    private static final boolean DEFAULT_HEADLESS = true;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    /**
     * Obtiene el tipo de navegador desde las propiedades del sistema.
     */
    public static String getBrowserType() {
        return System.getProperty(BROWSER_PROPERTY, DEFAULT_BROWSER);
    }

    /**
     * Verifica si los tests deben ejecutarse en modo headless.
     */
    public static boolean isHeadless() {
        String headless = System.getProperty(HEADLESS_PROPERTY);
        if (headless == null) {
            return DEFAULT_HEADLESS;
        }
        return Boolean.parseBoolean(headless);
    }

    /**
     * Inicializa Playwright y el navegador.
     */
    public void setup() {
        logger.info("Iniciando Playwright con navegador: {} (headless: {})", getBrowserType(), isHeadless());

        playwright = Playwright.create();
        browser = createBrowser();
        context = createBrowserContext();
        page = context.newPage();

        logger.info("Playwright inicializado correctamente");
    }

    /**
     * Crea una instancia del navegador según la configuración.
     */
    private Browser createBrowser() {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(isHeadless())
                .setSlowMo(50); // Pequeño delay para mejor visualización

        String browserType = getBrowserType().toLowerCase();

        return switch (browserType) {
            case "firefox" -> {
                logger.info("Lanzando Firefox");
                yield playwright.firefox().launch(options);
            }
            case "webkit" -> {
                logger.info("Lanzando WebKit");
                yield playwright.webkit().launch(options);
            }
            default -> {
                logger.info("Lanzando Chromium");
                yield playwright.chromium().launch(options);
            }
        };
    }

    /**
     * Crea un contexto de navegador con configuración personalizada.
     */
    private BrowserContext createBrowserContext() {
        return browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setLocale("es-ES")
                .setTimezoneId("Europe/Madrid")
                .setRecordVideoDir(java.nio.file.Paths.get("videos/"))
        );
    }

    /**
     * Cierra todos los recursos de Playwright.
     */
    public void teardown() {
        logger.info("Cerrando recursos de Playwright");

        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }

        logger.info("Recursos de Playwright cerrados");
    }

    // Getters

    public Playwright getPlaywright() {
        return playwright;
    }

    public Browser getBrowser() {
        return browser;
    }

    public BrowserContext getContext() {
        return context;
    }

    public Page getPage() {
        return page;
    }
}
