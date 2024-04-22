package de.qytera.qtaf.page_objects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementShould;
import de.qytera.qtaf.core.guice.annotations.Step;

import java.time.Duration;

/**
 * This page object represents generic form field like inputs, selects, checkboxes
 */
public class FormElement extends AbstractWebElement {
    /**
     * Get the current value of the input or hidden field
     *
     * @return the current value of the field
     */
    @Step(name = "get value", description = "Get value of input field")
    public String getValue() {
        return element()
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .scrollIntoView(true)
                .attr("value");
    }

    /**
     * Get the current inner text of the input field
     *
     * @return the current inner text of the field
     */
    @Step(name = "get inner text", description = "Get inner text of input field")
    public String getText() {
        return element()
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .scrollIntoView(true)
                .getText();
    }

    @Step(name = "assert value is empty", description = "Assert that value of field is empty")
    public void assertValueIsEmpty() {
        String actualValue = getValue();
        assertEquals(
                actualValue,
                "",
                "Expected value of %s to be empty and value was '%s'".formatted(name, actualValue)
        );
    }

    @Step(name = "assert value equals given value", description = "Assert that value of field equals a given value")
    public void assertValueEquals(String expectedValue) {
        String actualValue = getValue();
        assertEquals(
                actualValue,
                expectedValue,
                "Expected value of %s to be '%s' and value was '%s'".formatted(name, expectedValue, actualValue)
        );
    }

    @Step(name = "wait until value equals given value", description = "Wait until the value of field equals a given value")
    public void waitUntilValueEquals(String expectedValue, int seconds) {
        try {
            element().shouldHave(Condition.value(expectedValue), Duration.ofSeconds(seconds));
            assertTrue(true, "Expected value of %s to be '%s' within %s seconds and it did".formatted(name, expectedValue, seconds));
        } catch (ElementShould ignored) {
            String actualValue = getValue();
            assertTrue(false, "Expected value of %s to be '%s' within %s seconds, but was '%s'".formatted(name, expectedValue, seconds, actualValue));
        }
    }

    /**
     * Type a value into the input or hidden field
     *
     * @param value the value to type
     */
    @Step(name = "type a value into the field", description = "Type a value into the field")
    public void type(String value) {
        element()
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldBe(Condition.interactable, Duration.ofSeconds(10))
                .scrollIntoView(true)
                .type(value);
    }

    /**
     * Send a value into the input field
     *
     * @param value the value to type
     */
    @Step(name = "send keys into a field", description = "Send keys into a field")
    public void sendKeys(String value) {
        element()
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .scrollIntoView(true)
                .sendKeys(value);
    }
}