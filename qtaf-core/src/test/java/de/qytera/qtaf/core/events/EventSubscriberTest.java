package de.qytera.qtaf.core.events;

import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import org.testng.Assert;
import org.testng.annotations.Test;
import rx.Subscription;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class EventSubscriberTest {
    /**
     * Test one subscriber
     */
    @Test
    public void testOneSubscriber() {
        AtomicInteger countOne = new AtomicInteger();

        Subscription sub = QtafEvents.testStarted.subscribe(iTestResult -> {
            countOne.addAndGet(1);
        });

        // Event has not been dispatched, so counter should be still zero
        Assert.assertEquals(countOne.get(), 0);

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should be increased
        Assert.assertEquals(countOne.get(), 1);

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should be increased
        Assert.assertEquals(countOne.get(), 2);

        sub.unsubscribe();

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should not be increased
        Assert.assertEquals(countOne.get(), 2);

    }

    /**
     * Test two subscribers
     */
    @Test
    public void testTwoSubscribers() {
        AtomicInteger countTwo = new AtomicInteger();

        // First subscriber
        Subscription sub1 = QtafEvents.testStarted.subscribe(iTestResult -> {
            countTwo.addAndGet(1);
        });

        // Second subscriber
        Subscription sub2 = QtafEvents.testStarted.subscribe(iTestResult -> {
            countTwo.addAndGet(2);
        });

        // Event has not been dispatched, so counter should be still zero
        Assert.assertEquals(countTwo.get(), 0);

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should be increased
        Assert.assertEquals(countTwo.get(), 3);

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should be increased
        Assert.assertEquals(countTwo.get(), 6);

        sub1.unsubscribe();

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should be increased only by two
        Assert.assertEquals(countTwo.get(), 8);

        sub2.unsubscribe();

        // Fire event
        QtafEvents.testStarted.onNext(new QtafTestEventPayload());

        // Now counter should not be increased, because there are no subscriptions anymore
        Assert.assertEquals(countTwo.get(), 8);
    }

    /**
     * Test one subscriber
     */
    @Test
    public void testClassesLoadedEvent() {
        AtomicInteger countEvents = new AtomicInteger();

        Subscription sub = QtafEvents.testClassesLoaded.subscribe(classes -> {
            countEvents.addAndGet(1);
        });

        // Event has not been dispatched, so counter should be still zero
        Assert.assertEquals(countEvents.get(), 0);

        // Fire event
        QtafEvents.testClassesLoaded.onNext(new HashSet<>());

        // Now counter should be increased
        Assert.assertEquals(countEvents.get(), 1);

        // Fire event
        QtafEvents.testClassesLoaded.onNext(new HashSet<>());

        // Now counter should be increased
        Assert.assertEquals(countEvents.get(), 2);

        sub.unsubscribe();

        // Fire event
        QtafEvents.testClassesLoaded.onNext(new HashSet<>());

        // Now counter should not be increased
        Assert.assertEquals(countEvents.get(), 2);
    }

    /**
     * Test one subscriber
     */
    @Test
    public void testClassInstancesLoadedEvent() {
        AtomicInteger countEvents = new AtomicInteger();

        Subscription sub = QtafEvents.testClassInstancesLoaded.subscribe(instances -> {
            countEvents.addAndGet(1);
        });

        // Event has not been dispatched, so counter should be still zero
        Assert.assertEquals(countEvents.get(), 0);

        // Fire event
        QtafEvents.testClassInstancesLoaded.onNext(new ArrayList<>());

        // Now counter should be increased
        Assert.assertEquals(countEvents.get(), 1);

        // Fire event
        QtafEvents.testClassInstancesLoaded.onNext(new ArrayList<>());

        // Now counter should be increased
        Assert.assertEquals(countEvents.get(), 2);

        sub.unsubscribe();

        // Fire event
        QtafEvents.testClassInstancesLoaded.onNext(new ArrayList<>());

        // Now counter should not be increased
        Assert.assertEquals(countEvents.get(), 2);
    }
}
