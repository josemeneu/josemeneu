package com.josemeneu.playwright.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests que demuestran la interacción con formularios.
 * Usa una página de ejemplo para practicar.
 */
@DisplayName("Interacción con formularios")
class FormInteractionTest extends BaseTest {

    private static final String FORM_URL = "https://demoqa.com/automation-practice-form";
    private static final String TEXT_BOX_URL = "https://demoqa.com/text-box";

    @Test
    @DisplayName("Debería rellenar campos de texto")
    void shouldFillTextFields() {
        // Given
        page.navigate(TEXT_BOX_URL);

        // When
        page.locator("#userName").fill("Juan Pérez");
        page.locator("#userEmail").fill("juan.perez@email.com");
        page.locator("#currentAddress").fill("Calle Principal 123");
        page.locator("#permanentAddress").fill("Avenida Secundaria 456");

        // Then
        assertThat(page.locator("#userName").inputValue()).isEqualTo("Juan Pérez");
        assertThat(page.locator("#userEmail").inputValue()).isEqualTo("juan.perez@email.com");

        takeScreenshot("form_filled");
    }

    @Test
    @DisplayName("Debería enviar el formulario")
    void shouldSubmitForm() {
        // Given
        page.navigate(TEXT_BOX_URL);
        page.locator("#userName").fill("Test User");
        page.locator("#userEmail").fill("test@test.com");
        page.locator("#currentAddress").fill("Test Address");

        // When
        page.locator("#submit").click();

        // Then
        assertThat(page.locator("#output")).isNotNull();
        takeScreenshot("form_submitted");
    }

    @Test
    @DisplayName("Debería interactuar con checkboxes")
    void shouldInteractWithCheckboxes() {
        // Given
        page.navigate("https://demoqa.com/checkbox");

        // When
        page.locator(".rct-checkbox").first().click();

        // Then
        takeScreenshot("checkbox_clicked");
    }

    @Test
    @DisplayName("Debería interactuar con radio buttons")
    void shouldInteractWithRadioButtons() {
        // Given
        page.navigate("https://demoqa.com/radio-button");

        // When
        page.locator("label[for='yesRadio']").click();

        // Then
        assertThat(page.locator(".text-success").textContent()).isEqualTo("Yes");
        takeScreenshot("radio_selected");
    }

    @Test
    @DisplayName("Debería interactuar con botones")
    void shouldInteractWithButtons() {
        // Given
        page.navigate("https://demoqa.com/buttons");

        // When - Doble clic
        page.locator("#doubleClickBtn").dblclick();

        // Then
        assertThat(page.locator("#doubleClickMessage").textContent())
                .contains("double click");

        takeScreenshot("button_double_clicked");
    }

    @Test
    @DisplayName("Debería hacer clic derecho en un botón")
    void shouldRightClickButton() {
        // Given
        page.navigate("https://demoqa.com/buttons");

        // When
        page.locator("#rightClickBtn").click(new com.microsoft.playwright.Locator.ClickOptions()
                .setButton(com.microsoft.playwright.options.MouseButton.RIGHT));

        // Then
        assertThat(page.locator("#rightClickMessage").textContent())
                .contains("right click");

        takeScreenshot("button_right_clicked");
    }
}
