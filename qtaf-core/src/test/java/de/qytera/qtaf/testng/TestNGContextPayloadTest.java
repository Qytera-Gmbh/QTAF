package de.qytera.qtaf.testng;

import de.qytera.qtaf.testng.events.payload.TestNGTestContextPayload;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * These tests check if the iTestResult object is correctly transformed to a TestNGTestContextPayload object
 */
public class TestNGContextPayloadTest {
    @Test
    public void testTestNG() throws ParseException {
        ITestContext iTestContext = new SampleTestContext();
        TestNGTestContextPayload payload = new TestNGTestContextPayload(iTestContext);

        // Test original event class
        Assert.assertEquals(
                payload.getiTestContext().getClass().getName(),
                iTestContext.getClass().getName()
        );
    }

    @Test
    public void testOriginalEventEquality() {
        ITestContext iTestContext = new SampleTestContext();
        TestNGTestContextPayload payload = new TestNGTestContextPayload(iTestContext);

        // Test equality
        Assert.assertEquals(
                payload.getiTestContext(),
                iTestContext
        );

    }

    @Test
    public void testSuiteName() {
        ITestContext iTestContext = new SampleTestContext();
        TestNGTestContextPayload payload = new TestNGTestContextPayload(iTestContext);

        // Test suite name
        Assert.assertEquals(payload.getSuiteName(), "sample-suite-name");

    }

    @Test
    public void testStartDate() throws ParseException {
        ITestContext iTestContext = new SampleTestContext();
        TestNGTestContextPayload payload = new TestNGTestContextPayload(iTestContext);
        // Test start date
        Assert.assertEquals(
                payload.getStartDate().getTime(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .parse("2020-01-01 12:00:00")
                        .getTime()
        );

    }

    @Test
    public void testEndDate() throws ParseException {
        ITestContext iTestContext = new SampleTestContext();
        TestNGTestContextPayload payload = new TestNGTestContextPayload(iTestContext);
        // Test end date
        Assert.assertEquals(
                payload.getEndDate().getTime(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .parse("2020-01-01 12:05:00")
                        .getTime()
        );

        // Test output directory
        Assert.assertEquals(payload.getLogDirectory(), "/var/log/qtaf");
    }
}
