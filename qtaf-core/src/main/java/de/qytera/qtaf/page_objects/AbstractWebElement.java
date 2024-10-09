package de.qytera.qtaf.page_objects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.codeborne.selenide.Selenide.$;

public abstract class AbstractWebElement extends QtafTestNGContext {
    /**
     * Parent element
     */
    protected String name;

    /**
     * Parent element
     */
    protected By selector;

    /**
     * Element supplier
     */
    protected Supplier<SelenideElement> supplier;

    /**
     * Initialization of page object
     *
     * @param name     Field name
     * @param selector Selenium selector
     */
    public <T> T init(String name, By selector) {
        this.name = name;
        this.selector = selector;
        return (T) this;
    }

    /**
     * Initialization of page object
     *
     * @param name     Field name
     * @param supplier Selenium selector supplier
     */
    public <T> T init(String name, Supplier<SelenideElement> supplier) {
        this.name = name;
        this.supplier = supplier;
        return (T) this;
    }

    /**
     * Get selenide element
     *
     * @return selenide element
     */
    public SelenideElement element() {
        if (selector != null) {
            return $(selector);
        }

        if (supplier != null) {
            return supplier.get();
        }

        throw new IllegalArgumentException("Neither a selector nor a supplier were found.");
    }

    /**
     * Click element
     */
    @Step(name = "click element", description = "Click on the element")
    public void click() {
        element().click();
    }

    /**
     * Get inner text
     */
    @Step(name = "get inner text", description = "Get inner text of element")
    public String innerText() {
        return element()
                .scrollIntoView(true)
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .innerText();
    }

    /**
     * Assert inner text equals a given inner text
     */
    @Step(name = "assert inner text equals", description = "Assert inner text equals a given text")
    public void assertInnerTextEquals(String expectedInnerText) {
        String actualInnerText = innerText().trim();
        assertEquals(
                actualInnerText,
                expectedInnerText,
                "Expected inner text of %s to be '%s' and it was '%s'".formatted(name, expectedInnerText, actualInnerText)
        );
    }

    /**
     * Get inner html
     */
    @Step(name = "get inner html", description = "Get inner html of element")
    public String innerHtml() {
        return element()
                .scrollIntoView(true)
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .innerHtml();
    }

    /**
     * Assert inner html contains a given string
     */
    @Step(name = "assert inner html contains", description = "Assert inner html contains a given string")
    public void assertInnerHtmlContains(String expectedString) {
        String actualInnerHtml = innerHtml().trim();
        assertTrue(
                actualInnerHtml.contains(expectedString),
                "Expected inner html to contain '%s', but didn't. The inner html was '%s'".formatted(expectedString, actualInnerHtml)
        );
    }

    /**
     * Assert that element has a given css class
     *
     * @param className Name of the class
     */
    @Step(name = "assert element has class", description = "Assert that the element has a given class")
    public void assertHasClass(String className) {
        String cssClassAttr = element().attr("class");
        String[] cssClasses;

        if (cssClassAttr != null) {
            cssClasses = cssClassAttr.split(" ");
        } else {
            cssClasses = new String[]{};
        }

        boolean containsClass = Arrays.asList(cssClasses).contains(className);
        assertTrue(
                containsClass,
                "Expected %s to contain a css class named '%s' in: '%s'"
                        .formatted(name, className, cssClassAttr)
        );
    }

    /**
     * Assert that element has a given css class
     *
     * @param className Name of the class
     */
    @Step(name = "assert element hasn't class", description = "Assert that the element hasn't a given class")
    public void assertHasNotClass(String className) {
        String cssClassAttr = element().attr("class");
        String[] cssClasses = cssClassAttr.split(" ");
        boolean containsClass = Arrays.asList(cssClasses).contains(className);
        assertFalse(
                containsClass,
                "Expected %s to not contain a css class named '%s' but element has one. The element has the following classes: '%s'"
                        .formatted(name, className, cssClassAttr)
        );
    }

    /**
     * Assert that element has all of a list of given css classes
     *
     * @param classNames Names of the class
     */
    @Step(name = "assert has classes", description = "Assert that an element has all of the given classes")
    public void assertHasClasses(List<String> classNames) {
        String cssClassAttr = element().attr("class");
        String[] cssClasses;

        if (cssClassAttr != null) {
            cssClasses = cssClassAttr.split(" ");
        } else {
            cssClasses = new String[]{};
        }

        boolean containsClass = Arrays.asList(cssClasses).contains(classNames);
        assertTrue(
                containsClass,
                "Expected %s to contain the class names '%s' but element hasn't all of them. Instead it has those classes: '%s'"
                        .formatted(name, classNames, cssClassAttr)
        );
    }

    /**
     * Assert that element has all of a list of given css classes
     *
     * @param classNames Names of the class
     */
    public void assertHasNotClasses(List<String> classNames) {
        String cssClassAttr = element().attr("class");
        String[] cssClasses = cssClassAttr.split(" ");
        boolean containsClass = Arrays.asList(cssClasses).contains(classNames);
        assertTrue(
                containsClass,
                "Expected %s to not contain the classes '%s' but element has at least one of them. The element has the following classes: '%s'"
                        .formatted(name, classNames, cssClassAttr)
        );
    }

    /**
     * Check that the element has a given value for a given CSS attribute
     *
     * @param key           CSS attribute name
     * @param expectedValue CSS attribute value
     */
    @Step(name = "assert element has CSS attribute value", description = "Assert that the element has a given CSS attribute value")
    public void assertCssAttributeHasValue(String key, String expectedValue) {
        String actualValue = element().getCssValue(key);
        assertEquals(
                actualValue,
                expectedValue,
                "Expected CSS attribute %s of %s to have the value '%s' and it's value is '%s'".formatted(key, name, expectedValue, actualValue)
        );
    }

    /**
     * Check that the element hasn't a given value for a given CSS attribute
     *
     * @param key           CSS attribute name
     * @param expectedValue CSS attribute value
     */
    @Step(name = "assert element hasn't CSS attribute value", description = "Assert that the element hasn't a given CSS attribute value")
    public void assertCssAttributeHasNotValue(String key, String expectedValue) {
        String actualValue = element().getCssValue(key);
        assertNotEquals(
                actualValue,
                expectedValue,
                "Expected CSS attribute %s of %s to not have the value '%s' and it's value is '%s'".formatted(key, name, expectedValue, actualValue)
        );
    }

    /**
     * Check if the input or hidden field is disabled
     *
     * @return true if the field is disabled, false otherwise
     */
    @Step(name = "check if field is disabled", description = "Check if field is disabled")
    public boolean isDisabled() {
        return element().attr("disabled") != null;
    }

    /**
     * Check if element is displayed
     */
    @Step(name = "check if element is displayed", description = "Check if the element is displayed")
    public void assertIsDisplayed() {
        boolean isDisplayed = element().isDisplayed();
        assertTrue(
                isDisplayed,
                "Expected element %s to be displayed and this is %s".formatted(name, isDisplayed)
        );
    }

    /**
     * Check if element is not displayed
     */
    @Step(name = "check if element is not displayed", description = "Check if the element is not displayed")
    public void assertIsNotDisplayed() {
        boolean isNotDisplayed = !element().isDisplayed();
        assertTrue(
                isNotDisplayed,
                "Expected element %s to not be displayed and this is %s".formatted(name, isNotDisplayed)
        );
    }

    /**
     * Assert that the element is disabled
     */
    @Step(name = "assert that field is disabled", description = "Assert that field is disabled")
    public void assertIsDisabled() {
        boolean condition = element().attr("disabled") != null;
        assertTrue(
                condition,
                String.format("Expected %s element to be disabled and this was %s", name, condition)
        );
    }

    /**
     * Assert that the element is enabled
     */
    @Step(name = "assert that field is enabled", description = "Assert that field is enabled")
    public void assertIsEnabled() {
        boolean condition = element().attr("disabled") == null;
        assertTrue(
                condition,
                String.format("Expected %s element to be enabled and this was %s", name, condition)
        );
    }

    @Step(name = "check if an element is visible", description = "Check if an element is visible")
    public void isVisible(int seconds) {
        element().shouldBe(Condition.visible, Duration.ofSeconds(seconds));
    }

    @Step(name = "check if an element is visible", description = "Check if an element is visible")
    public void isNotVisible(int seconds) {
        element().shouldNotBe(Condition.visible, Duration.ofSeconds(seconds));
    }

    @Step(name = "check if some elements are visible", description = "Check if some elements are in the page visible")
    public void areVisible(List<SelenideElement> elements, int seconds) {
        for (SelenideElement element : elements) {
            element.shouldBe(Condition.visible, Duration.ofSeconds(seconds));
        }
    }
}