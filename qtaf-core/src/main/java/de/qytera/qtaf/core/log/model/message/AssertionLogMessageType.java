package de.qytera.qtaf.core.log.model.message;

/**
 * All TestNG Assertion Types
 *
 * @see <a href="https://www.javadoc.io/doc/org.testng/testng/latest/org/testng/Assert.html">TestNG Assetions</a>
 */
public enum AssertionLogMessageType {
    /**
     * Used for asserting object equality.
     */
    ASSERT_EQUALS,
    /**
     * Used for asserting deep object equality.
     */
    ASSERT_EQUALS_DEEP,
    /**
     * Used for asserting equality regardless of objects' internal ordering.
     */
    ASSERT_EQUALS_NO_ORDER,
    /**
     * Used for asserting {@code false}.
     */
    ASSERT_FALSE,
    /**
     * Used for asserting object inequality.
     */
    ASSERT_NOT_EQUALS,
    /**
     * Used for asserting deep object inequality.
     */
    ASSERT_NOT_EQUALS_DEEP,
    /**
     * Used for asserting objects are not {@code null}.
     */
    ASSERT_NOT_NULL,
    /**
     * Used for asserting that objects do not share the same reference.
     */
    ASSERT_NOT_SAME,
    /**
     * Used for asserting objects are {@code null}.
     */
    ASSERT_NULL,
    /**
     * Used for asserting that objects share the same reference.
     */
    ASSERT_SAME,
    /**
     * Used for asserting {@code true}.
     */
    ASSERT_TRUE,
}
