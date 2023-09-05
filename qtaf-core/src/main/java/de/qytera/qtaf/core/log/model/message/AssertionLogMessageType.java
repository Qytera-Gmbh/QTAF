package de.qytera.qtaf.core.log.model.message;

/**
 * All TestNG Assertion Types
 *
 * @see <a href="https://www.javadoc.io/doc/org.testng/testng/latest/org/testng/Assert.html">TestNG Assetions</a>
 */
public enum AssertionLogMessageType {
    /**
     * Assertion that checks if two values are equal
     */
    ASSERT_EQUALS,
    /**
     * Assertion that checks if collections contain the same objects
     */
    ASSERT_EQUALS_DEEP,
    /**
     * Check if two collections contain the same objects no mater in which order
     */
    ASSERT_EQUALS_NO_ORDER,
    /**
     * Assertion that checks if a value is false
     */
    ASSERT_FALSE,
    /**
     * Assertion that checks if two values are not equal
     */
    ASSERT_NOT_EQUALS,
    /**
     * Assertion that checks if two collections differ from each other
     */
    ASSERT_NOT_EQUALS_DEEP,
    /**
     * Assertion that checks if a value is not null
     */
    ASSERT_NOT_NULL,
    /**
     * Assertion that checks that two references do not point to the same object
     */
    ASSERT_NOT_SAME,
    /**
     * Assertion that checks if a value is null
     */
    ASSERT_NULL,
    /**
     * Assertion that checks if two variables have the same hash code
     */
    ASSERT_SAME,
    /**
     * Assertion that checks if a value is true
     */
    ASSERT_TRUE,
}
