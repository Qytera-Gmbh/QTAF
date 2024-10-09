package de.qytera.qtaf.page_objects;

import com.codeborne.selenide.Condition;
import de.qytera.qtaf.core.guice.annotations.Step;

import java.time.Duration;

/**
 * This page object represents an input element
 */
public class FormInputElement extends FormElement {
    /**
     * Clear the value of the input or hidden field
     */
    @Step(name = "clear value of field", description = "Clear value of input field")
    public void clear() {
        element()
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldBe(Condition.interactable, Duration.ofSeconds(10))
                .scrollIntoView(true)
                .clear();
    }
}