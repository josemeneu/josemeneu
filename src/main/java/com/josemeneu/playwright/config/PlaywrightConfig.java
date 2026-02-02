package com.josemeneu.playwright.config;

import com.microsoft.playwright.*;

/**
 * Configuración centralizada para Playwright.
 * Gestiona la creación y configuración de Browser, BrowserContext y Page.
 */
public class PlaywrightConfig {

    private static final String DEFAULT_BROWSER = "chromium";
    private static final boolean DEFAULT_HEADLESS = true;
    private static final int DEFAULT_TIMEOUT = 30000;
    private static final int DEFAULT_VIEWPORT_WIDTH = 1920;
    private static final int DEFAULT_VIEWPORT_HEIGHT = 1080;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    /**
     * Obtiene el tipo de navegador desde las propiedades del sistema.
     */
    public String getBrowserType() {
        return System.getProperty("browser", DEFAULT_BROWSER);
    }

    /**
     * Verifica si el modo headless está habilitado.
     */
    public boolean isHeadless() {
        String headless = System.getProperty("headless", String.valueOf(DEFAULT_HEADLESS));
        return Boolean.parseBoolean(headless);
    }

    /**
     * Crea y configura la instancia de Playwright.
     */
    public void setup() {
        playwright = Playwright.create();
        browser = createBrowser();
        context = createContext();
        page = context.newPage();
    }

    /**
     * Crea el navegador según la configuración.
     */
    private Browser createBrowser() {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(isHeadless())
                .setSlowMo(50);

        String browserType = getBrowserType();
        return switch (browserType.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> playwright.chromium().launch(options);
        };
    }

    /**
     * Crea el contexto del navegador con configuraciones específicas.
     */
    private Browser.NewContextOptions getContextOptions() {
        return new Browser.NewContextOptions()
                .setViewportSize(DEFAULT_VIEWPORT_WIDTH, DEFAULT_VIEWPORT_HEIGHT)
                .setRecordVideoDir(java.nio.file.Paths.get("videos/"))
                .setRecordVideoSize(1280, 720);
    }

    /**
     * Crea el contexto del navegador.
     */
    private BrowserContext createContext() {
        return browser.newContext(getContextOptions());
    }

    /**
     * Limpia todos los recursos de Playwright.
     */
    public void teardown() {
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

    public int getDefaultTimeout() {
        return DEFAULT_TIMEOUT;
    }
}
