package de.qytera.qtaf.testng.events.payload;

import de.qytera.qtaf.core.events.payload.QtafTestContextPayload;
import org.testng.ITestContext;

/**
 * A class describing TestNG test context payloads.
 */
public class TestNGTestContextPayload extends QtafTestContextPayload {
    ITestContext originalEvent;

    /**
     * Creates a new payload instance.
     *
     * @param iTestContext the TestNG test context
     */
    public TestNGTestContextPayload(ITestContext iTestContext) {
        this.originalEvent = iTestContext;
        this.suiteName = iTestContext.getSuite().getName();
        this.startDate = iTestContext.getStartDate();
        this.endDate = iTestContext.getEndDate();
        this.logDirectory = iTestContext.getSuite().getOutputDirectory();
        this.thread = Thread.currentThread();
    }

    @Override
    public ITestContext getOriginalEvent() {
        return this.originalEvent;
    }

    /**
     * Set the original context event.
     *
     * @param originalEvent the context event
     * @return this
     */
    public QtafTestContextPayload setOriginalEvent(ITestContext originalEvent) {
        this.originalEvent = originalEvent;
        return this;
    }
}
