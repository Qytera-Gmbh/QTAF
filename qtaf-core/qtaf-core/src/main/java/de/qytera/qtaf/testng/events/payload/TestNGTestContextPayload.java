package de.qytera.qtaf.testng.events.payload;

import de.qytera.qtaf.core.events.payload.QtafTestContextPayload;
import org.testng.ITestContext;

public class TestNGTestContextPayload extends QtafTestContextPayload {
    ITestContext originalEvent;

    public TestNGTestContextPayload(ITestContext iTestContext) {
        this.originalEvent = iTestContext;
        this.suiteName = iTestContext.getSuite().getName();
        this.startDate = iTestContext.getStartDate();
        this.endDate = iTestContext.getEndDate();
        this.logDirectory = iTestContext.getSuite().getOutputDirectory();
    }

    @Override
    public ITestContext getOriginalEvent() {
        return this.originalEvent;
    }

    public QtafTestContextPayload setOriginalEvent(ITestContext originalEvent) {
        this.originalEvent = originalEvent;
        return this;
    }
}
