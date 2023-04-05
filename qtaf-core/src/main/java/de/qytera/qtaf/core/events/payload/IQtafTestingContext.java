package de.qytera.qtaf.core.events.payload;

import java.util.Date;

/**
 * Interface that all test context classes have to implement
 */
public interface IQtafTestingContext {
    /**
     * Get name of the test suite
     *
     * @return test suite name
     */
    String getSuiteName();

    /**
     * Get start date of the test suite
     *
     * @return start date of test suite
     */
    Date getStartDate();

    /**
     * Get end date of the test suite
     *
     * @return end date of test suite
     */
    Date getEndDate();

    /**
     * Get the directory where log files are stored in
     *
     * @return log directory
     */
    String getLogDirectory();

    /**
     * Get the original event fired by the test framework (TestNG, Cucumber, ...)
     *
     * @return original event
     */
    Object getOriginalEvent();
}
