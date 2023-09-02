package de.qytera.qtaf.testng.events.payload;

import de.qytera.qtaf.core.events.payload.QtafTestContextPayload;
import org.testng.ITestContext;

/**
 * A class describing TestNG test context payloads.
 */
public class TestNGTestContextPayload extends QtafTestContextPayload {
    ITestContext iTestContext;

    /**
     * Creates a new payload instance.
     *
     * @param iTestContext the TestNG test context
     */
    public TestNGTestContextPayload(ITestContext iTestContext) {
        this.iTestContext = iTestContext;
        this.suiteName = iTestContext.getSuite().getName();
        this.startDate = iTestContext.getStartDate();
        this.endDate = iTestContext.getEndDate();
        this.logDirectory = iTestContext.getSuite().getOutputDirectory();
        this.thread = Thread.currentThread();
    }

    @Override
    public ITestContext getiTestContext() {
        return this.iTestContext;
    }

    /**
     * Set the original context event.
     *
     * @param originalEvent the context event
     * @return this
     */
    public QtafTestContextPayload setOriginalEvent(ITestContext originalEvent) {
        this.iTestContext = originalEvent;
        return this;
    }
}
