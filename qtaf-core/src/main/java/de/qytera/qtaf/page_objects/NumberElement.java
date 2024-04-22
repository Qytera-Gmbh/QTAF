package de.qytera.qtaf.page_objects;

import de.qytera.qtaf.core.guice.annotations.Step;

import java.util.function.Function;

/**
 * This page object represents a text element like a paragraph or a headline
 */
public class NumberElement extends TextElement {
    /**
     * A function that accepts a string and returns a double
     */
    private Function<String, Double> doubleParser = Double::parseDouble;

    /**
     * Set parser
     * @param doubleParser   a function that accepts a string and returns a double
     * @return  this
     */
    public NumberElement setDoubleParser(Function<String, Double> doubleParser) {
        this.doubleParser = doubleParser;
        return this;
    }

    /**
     * Get inner Text as double
     * @return double value
     */
    public double parseInnerTextDouble() {
        return doubleParser.apply(innerText());
    }

    /**
     * Check value
     * @param value    value
     */
    @Step(name = "check value is equal to", description = "Check if value is equal to a given value")
    public void assertValueToBeEqualTo(double value) {
        double actualValue = parseInnerTextDouble();
        assertTrue(
                value == actualValue,
                String.format("Expected value '%s' to be '%s' and it was '%s'", actualValue, value, value)
        );
    }

    /**
     * Check value is in range
     * @param min    min value
     * @param max    max value
     */
    @Step(name = "check value is in range", description = "Check if value is less or equal to a minimum value and greater than or equal to a maximum value")
    public void assertValueInRange(double min, double max) {
        double actualValue = parseInnerTextDouble();
        assertTrue(
                actualValue >= min && actualValue <= max,
                String.format("Value should be in range [%s, %s], but current value is %s", min, max, actualValue)
        );
    }

    /**
     * Check value is not in range
     * @param min    min value
     * @param max    max value
     */
    @Step(name = "check value is not in range", description = "Check if value is not in a given range")
    public void assertValueNotInRange(double min, double max) {
        double actualValue = parseInnerTextDouble();
        assertTrue(
                actualValue < min || actualValue > max,
                String.format("Value should not be in range [%s, %s], but current value is %s", min, max, actualValue)
        );
    }

    /**
     * Check lower bound
     * @param upperbound    lower bound
     */
    @Step(name = "check value is less than", description = "Check if value is less than a given value")
    public void assertValueToBeLessThan(double upperbound) {
        double actualValue = parseInnerTextDouble();
        assertTrue(
                upperbound > actualValue,
                String.format("Value %s is not less than %s", actualValue, upperbound)
        );
    }

    /**
     * Check lower bound
     * @param upperbound    lower bound
     */
    @Step(name = "check value is less than or equal to", description = "Check if value is less than or equal to a given value")
    public void assertValueToBeLessThanOrEqualTo(double upperbound) {
        double actualValue = parseInnerTextDouble();
        assertTrue(
                upperbound >= actualValue,
                String.format("Value %s is not less that or equal to %s", actualValue, upperbound)
        );
    }

    /**
     * Check lower bound
     * @param lowerBound    lower bound
     */
    @Step(name = "check value is greater than", description = "Check if value is greater than a given value")
    public void assertValueToBeGreaterThan(double lowerBound) {
        double actualValue = parseInnerTextDouble();
        assertTrue(
                lowerBound < actualValue,
                String.format("Value %s is not greater than %s", actualValue, lowerBound)
        );
    }

    /**
     * Check lower bound
     * @param lowerBound    lower bound
     */
    @Step(name = "check value is greater than or equal to", description = "Check if value is greater than or equal to a given value")
    public void assertValueToBeGreaterThanOrEqualTo(double lowerBound) {
        double actualValue = parseInnerTextDouble();
        assertTrue(
                lowerBound <= actualValue,
                String.format("Value %s is not greater than or equal to %s", actualValue, lowerBound)
        );
    }
}
