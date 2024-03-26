package de.qytera.qtaf.testng.event_subscriber;

import org.testng.annotations.Test;

public class TestNGLoggingSubscriberTest {
    @Test
    public void initialiseTest() {
        TestNGLoggingSubscriber testNGLoggingSubscriber = new TestNGLoggingSubscriber();
        testNGLoggingSubscriber.initialize();
    }
}
