package de.qytera.qtaf.page_objects;

import com.codeborne.selenide.BaseElementsCollection;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import de.qytera.qtaf.core.guice.annotations.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This page object represents select elements
 */
public class FormSelectElement extends FormElement {
    /**
     * Get the available options of a select as a list of strings
     * @param dynamic   Whether the element of the collection should be reloaded through the iteration or not
     *                  see: https://selenide.org/2022/01/10/selenide-6.2.0/
     *                  If you have StaleElement exceptions during this method set this parameter to true
     * @return the list of available options
     */
    @Step(name = "get all available options", description = "Get all available options of the select field")
    public List<String> getAvailableOptions(boolean dynamic) {
        BaseElementsCollection.SelenideElementIterable<SelenideElement> it;
        if (dynamic) {
            it = element().$$("option").asDynamicIterable();
        } else {
            it = element().$$("option").asFixedIterable();
        }
        List<String> options = new ArrayList<>();
        for (SelenideElement el : it) {
            options.add(el.text());
        }
        return options;
    }

    @Step(name = "assert select element has option", description = "Check that the select element has a given option")
    public void assertHasOption(String expectedOption, boolean dynamic) {
        List<String> availableOptions = getAvailableOptions(dynamic);
        boolean containsOption = availableOptions.contains(expectedOption);
        String message;
        if (containsOption) {
            message = "Expected to find option '%s' in %s and found it".formatted(expectedOption, name);
        } else {
            message = "Did not find option '%s' in %s. Instead found these options: '%s'".formatted(expectedOption, name, availableOptions);
        }
        assertTrue(
                containsOption,
                message
        );
    }

    /**
     * Wrapper method for assertHasOption with dynamic parameter set to true
     * @param expectedOption    The expected option
     */
    public void assertHasOptionDynamic(String expectedOption) {
        assertHasOption(expectedOption, true);
    }

    /**
     * Wrapper method for assertHasOption with dynamic parameter set to false
     * @param expectedOption    The expected option
     */
    public void assertHasOptionFixed(String expectedOption) {
        assertHasOption(expectedOption, false);
    }

    @Step(name = "assert select element has options", description = "Check that the select element has a given list of options")
    public void assertHasOptions(List<String> expectedOptions) {
        List<String> availableOptions = getAvailableOptions(true);

        boolean containsOptions = new HashSet<>(availableOptions).containsAll(expectedOptions);
        String message;

        if (containsOptions) {
            message = "Expected to find options '%s' in %s and found them".formatted(expectedOptions, name);
        } else {
            message = "Did not find all of the options '%s' in %s. Instead found these options: '%s'".formatted(expectedOptions, name, availableOptions);
        }

        assertTrue(
                containsOptions,
                message
        );
    }

    @Step(name = "assert select element hasn't option", description = "Check that the select element hasn't a given option")
    public void assertHasNotOption(String notExpectedOption) {
        List<String> availableOptions = getAvailableOptions(true);

        boolean containsOption = availableOptions.contains(notExpectedOption);
        String message;

        if (containsOption) {
            message = "Didn't expect to find option '%s' in %s but found it".formatted(notExpectedOption, name);
        } else {
            message = "%s did not contain option '%s'".formatted(name, notExpectedOption);
        }

        assertFalse(
                containsOption,
                message
        );
    }

    @Step(name = "assert select element hasn't any of provided options", description = "Check that the select element hasn't any options of a given list of options")
    public void assertHasNotOptions(List<String> notExpectedOptions) {
        List<String> availableOptions = getAvailableOptions(true);

        boolean containsOptions = new HashSet<>(availableOptions).containsAll(notExpectedOptions);
        String message;

        if (containsOptions) {
            message = "Didn't expect to find any of the options '%s' in %s but found at least one of them. The available options are '%s'".formatted(notExpectedOptions, name, availableOptions);
        } else {
            message = "Expected %s not to have any of the options '%s' and this was true".formatted(name, notExpectedOptions);
        }

        assertFalse(
                containsOptions,
                message
        );
    }

    /**
     * Select the i-th option of a select
     *
     * @param index    the index of the option to select
     */
    @Step(name = "select option by index", description = "Select an option by its index")
    public void selectOptionByIndex(int index) {
        Select select = new Select(element());
        select.selectByIndex(index);
    }

    /**
     * Select an option of a select by its text
     *
     * @param text     the text of the option to select
     */
    @Step(name = "select option by visible text", description = "Select an option by its visible text")
    public void selectOptionByVisibleText(String text) {
        if (text == null) throw new NullPointerException("Text for select element must not be null");
        element()
                .scrollIntoView(true)
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldBe(Condition.interactable, Duration.ofSeconds(10))
                .selectOption(text);
    }

    /**
     * Select an option of a select by its value
     *
     * @param value    the value of the option to select
     */
    @Step(name = "select option by value", description = "Select an option by its value")
    public void selectOptionByValue(String value) {
        if (value == null) throw new NullPointerException("Value for select element must not be null");
        element()
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldBe(Condition.interactable, Duration.ofSeconds(10))
                .scrollIntoView(true)
                .selectOptionByValue(value);
    }

    /**
     * Get the currently selected value of a select
     *
     * @return the currently selected value
     */
    @Step(name = "get selected value", description = "Get the selected value")
    public String getSelectedValue() {
        Select select = new Select(element());
        try {
            WebElement selectedOption = select.getFirstSelectedOption();
            return selectedOption.getText();
        } catch (NoSuchElementException e) { // no element is selected
            return null;
        }
    }

    /**
     * Get the currently selected value of a select
     */
    @Step(name = "assert selected value is empty", description = "Assert that the selected value is empty")
    public void assertNoElementIsSelected() {
        String actualValue = getSelectedValue();
        assertEquals(
                actualValue,
                null,
                "Expected value of %s to be empty and the actual value was '%s'".formatted(name, actualValue)
        );
    }
}