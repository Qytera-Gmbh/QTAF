package de.qytera.qtaf.core.events.payload;

import java.util.Date;

/**
 * Entity class for payloads of TestContext events
 */
public class QtafTestContextPayload implements IQtafTestingContext {
    /**
     * Name of the test suite
     */
    protected String suiteName;

    /**
     * Start date of the test suite
     */
    protected Date startDate;

    /**
     * End date of the test suite
     */
    protected Date endDate;

    /**
     * Directory where log files are persisted
     */
    protected String logDirectory;

    /**
     * Original event fired by the test framework (TestNG, Cucumber)
     */
    protected Object originalEvent;

    /**
     * Get suiteName
     *
     * @return suiteName
     */
    @Override
    public String getSuiteName() {
        return suiteName;
    }

    /**
     * Set suiteName
     *
     * @param suiteName SuiteName
     * @return this
     */
    public QtafTestContextPayload setSuiteName(String suiteName) {
        this.suiteName = suiteName;
        return this;
    }

    /**
     * Get startDate
     *
     * @return startDate
     */
    @Override
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set startDate
     *
     * @param startDate StartDate
     * @return this
     */
    public QtafTestContextPayload setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Get endDate
     *
     * @return endDate
     */
    @Override
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set endDate
     *
     * @param endDate EndDate
     * @return this
     */
    public QtafTestContextPayload setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * Get logDirectory
     *
     * @return logDirectory
     */
    @Override
    public String getLogDirectory() {
        return logDirectory;
    }

    /**
     * Set logDirectory
     *
     * @param logDirectory LogDirectory
     * @return this
     */
    public QtafTestContextPayload setLogDirectory(String logDirectory) {
        this.logDirectory = logDirectory;
        return this;
    }

    /**
     * Get originalEvent
     *
     * @return originalEvent
     */
    @Override
    public Object getOriginalEvent() {
        return originalEvent;
    }

    /**
     * Set originalEvent
     *
     * @param originalEvent OriginalEvent
     * @return this
     */
    public QtafTestContextPayload setOriginalEvent(Object originalEvent) {
        this.originalEvent = originalEvent;
        return this;
    }
}
