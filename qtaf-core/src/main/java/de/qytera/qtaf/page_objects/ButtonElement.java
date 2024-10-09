package de.qytera.qtaf.page_objects;

import com.codeborne.selenide.Condition;
import de.qytera.qtaf.core.guice.annotations.Step;

import java.time.Duration;

/**
 * This page object represents a button
 */
public class ButtonElement extends AbstractWebElement {
    /**
     * Click a button
     */
    @Override
    @Step(name = "click button", description = "Click on the button")
    public void click() {
        element()
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldBe(Condition.interactable, Duration.ofSeconds(10))
                .scrollIntoView(true)
                .click();
    }

    /**
     * Verify if the button is enabled
     */
    @Step(name = "check whether the button is enabled", description = "Check whether the button is enabled")
    public boolean isEnabled() {
        return element()
                .scrollIntoView(true)
                .is(Condition.enabled);
    }

    /**
     * Verify if the button exists
     */
    @Step(name = "check if the element exists", description = "Check if the element exists")
    public boolean exist() {
        return element().exists();
    }
}
