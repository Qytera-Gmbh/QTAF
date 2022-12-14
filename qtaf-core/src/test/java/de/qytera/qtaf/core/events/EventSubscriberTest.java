package de.qytera.qtaf.core.events;

import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import org.testng.Assert;
import org.testng.annotations.Test;
import rx.Subscription;

public class EventSubscriberTest {
    static int count = 0;

    /**
     * Test one subscriber
     */
    @Test
    public void testOneSubscriber() {
        count = 0;

        Subscription sub = QtafEvents.testStarted.subscribe(iTestResult -> {
            count += 1;
        });

        // Event has not been dispatched, so counter should be still zero
        Assert.assertEquals(count, 0);

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should be increased
        Assert.assertEquals(count, 1);

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should be increased
        Assert.assertEquals(count, 2);

        sub.unsubscribe();

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should not be increased
        Assert.assertEquals(count, 2);

    }

    /**
     * Test two subscribers
     */
    @Test
    public void testTwoSubscribers() {
        count = 0;

        // First subscriber
        Subscription sub1 = QtafEvents.testStarted.subscribe(iTestResult -> {
            count += 1;
        });

        // Second subscriber
        Subscription sub2 = QtafEvents.testStarted.subscribe(iTestResult -> {
            count += 2;
        });

        // Event has not been dispatched, so counter should be still zero
        Assert.assertEquals(count, 0);

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should be increased
        Assert.assertEquals(count, 3);

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should be increased
        Assert.assertEquals(count, 6);

        sub1.unsubscribe();

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should be increased only by two
        Assert.assertEquals(count, 8);

        sub2.unsubscribe();

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should not be increased, because there are no subscriptions anymore
        Assert.assertEquals(count, 8);
    }
}
