package de.qytera.qtaf.core.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.event_subscriber.test.QtafScenarioLoggingSubscriber;
import de.qytera.qtaf.core.events.payload.IQtafTestEventPayload;
import de.qytera.qtaf.core.events.payload.IQtafTestingContext;
import de.qytera.qtaf.core.events.payload.QtafTestContextPayload;
import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import de.qytera.qtaf.core.helper.DateHelper;
import de.qytera.qtaf.core.log.model.collection.FeatureLogCollectionIndex;
import de.qytera.qtaf.core.log.model.collection.ScenarioLogCollectionIndex;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;

/**
 * These tests are for the Qtaf Logging subscriber which is responsible for transforming events into log messages
 */
public class QtafTestLoggingSubscriberScenario {
    /**
     * Test the onStartTesting event handler
     */
    @Test
    public void testOnStartTesting() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParseException {
        // Get clear instance of suite log collection
        TestSuiteLogCollection suiteLogCollection = QtafFactory.getTestSuiteLogCollection();
        suiteLogCollection.clear();

        // Get instance of subscriber
        QtafScenarioLoggingSubscriber subscriber = new QtafScenarioLoggingSubscriber();

        // Make private method accessible
        Method method = QtafScenarioLoggingSubscriber.class.getDeclaredMethod(
                "onStartTesting",
                IQtafTestingContext.class
        );
        method.setAccessible(true);

        // Create mock event payload
        IQtafTestingContext payload = new QtafTestContextPayload()
                .setSuiteName("my-suite")
                .setLogDirectory("/var/log/qtaf")
                .setStartDate(DateHelper.fromTimeString("2020-01-01 12:00:00"))
                .setEndDate(DateHelper.fromTimeString("2020-01-01 12:10:00"));

        method.invoke(subscriber, payload);

        // Assert that data from the event payload was added to the log collection
        Assert.assertEquals(
                suiteLogCollection.getSuiteInfo().getName(),
                "my-suite",
                "Assert suite name to be equal"
        );
        Assert.assertEquals(
                suiteLogCollection.getSuiteInfo().getOutputDir(),
                "/var/log/qtaf",
                "Assert log directory to be equal"
        );
        Assert.assertEquals(
                suiteLogCollection.getStart().getTime(),
                DateHelper.fromTimeString("2020-01-01 12:00:00").getTime(),
                "Assert start dates to be equal"
        );
        Assert.assertEquals(
                suiteLogCollection.getEnd().getTime(),
                DateHelper.fromTimeString("2020-01-01 12:10:00").getTime(),
                "Assert end dates to be equal"
        );
    }

    /**
     * Test the onStartTesting event handler
     */
    @Test
    public void testOnFinishedTesting() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParseException {
        // Get clear instance of suite log collection
        TestSuiteLogCollection suiteLogCollection = QtafFactory.getTestSuiteLogCollection();
        suiteLogCollection.clear();
        suiteLogCollection
                .setStart(DateHelper.fromTimeString("2020-01-01 12:00:00"))
                .setEnd(DateHelper.fromTimeString("2020-01-01 13:00:00"));

        // Get instance of subscriber
        QtafScenarioLoggingSubscriber subscriber = new QtafScenarioLoggingSubscriber();

        // Make private method accessible
        Method method = QtafScenarioLoggingSubscriber.class.getDeclaredMethod(
                "onFinishTesting",
                IQtafTestingContext.class
        );
        method.setAccessible(true);

        // Create mock event payload
        IQtafTestingContext payload = new QtafTestContextPayload()
                .setSuiteName("my-suite")
                .setLogDirectory("/var/log/qtaf")
                .setStartDate(DateHelper.fromTimeString("2020-01-01 12:00:00"))
                .setEndDate(DateHelper.fromTimeString("2020-01-01 13:00:00"));

        method.invoke(subscriber, payload);

        // Assert that data from the event payload was added to the log collection
        Assert.assertEquals(
                suiteLogCollection.getSuiteInfo().getName(),
                null,
                "Assert suite name should not be changed"
        );
        Assert.assertEquals(
                suiteLogCollection.getSuiteInfo().getOutputDir(),
                null,
                "Assert log directory should not be changed"
        );
        Assert.assertEquals(
                suiteLogCollection.getStart().getTime(),
                DateHelper.fromTimeString("2020-01-01 12:00:00").getTime(),
                "Assert start dates to be equal"
        );
        Assert.assertEquals(
                suiteLogCollection.getEnd().getTime(),
                DateHelper.fromTimeString("2020-01-01 13:00:00").getTime(),
                "Assert end dates to be equal"
        );
        Assert.assertEquals(
                suiteLogCollection.getDuration(),
                3600000,
                "Assert duration to be one hour (= 3600000 ms)"
        );

        // Clean indices
        ScenarioLogCollectionIndex.getInstance().clear();
        FeatureLogCollectionIndex.getInstance().clear();
    }

    @Test
    public void testOnTestStarted() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParseException {
        // Get clear instance of suite log collection
        TestSuiteLogCollection suiteLogCollection = QtafFactory.getTestSuiteLogCollection();
        suiteLogCollection.clear();

        // Get instance of subscriber
        QtafScenarioLoggingSubscriber subscriber = new QtafScenarioLoggingSubscriber();

        // Make private method accessible
        Method method = QtafScenarioLoggingSubscriber.class.getDeclaredMethod(
                "onTestStarted", IQtafTestEventPayload.class);
        method.setAccessible(true);

        // Create mock event
        IQtafTestEventPayload payload = new QtafTestEventPayload()
                .setFeatureId("f1")
                .setAbstractScenarioId("s1")
                .setScenarioName("scenario 1");

        // Assert indices and log collections are empty before the event occurs
        Assert.assertEquals(
                suiteLogCollection.countFeatureLogs(),
                0,
                "Assert there are zero feature logs before the event in the suite collection"
        );
        Assert.assertEquals(
                FeatureLogCollectionIndex.getInstance().size(),
                0,
                "Assert there are no feature logs in the index before the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().size(),
                0,
                "Assert there are no scenario logs in the index before the event"
        );

        // Call subscriber method
        method.invoke(subscriber, payload);

        // Assert indices and log collections hold te new log collections after the event
        Assert.assertEquals(
                suiteLogCollection.countFeatureLogs(),
                1,
                "Assert there is one feature log after the event"
        );
        Assert.assertEquals(
                FeatureLogCollectionIndex.getInstance().size(),
                1,
                "Assert there are is one feature log in the index after the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().size(),
                1,
                "Assert there are is one scenario log in the index after the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().get("s1").getStatus(),
                TestScenarioLogCollection.Status.PENDING,
                "Assert there the status of the new scenario log collection is pending"
        );

        // Clear data
        suiteLogCollection.clear();

        // Clean indices
        ScenarioLogCollectionIndex.getInstance().clear();
        FeatureLogCollectionIndex.getInstance().clear();
    }

    @Test
    public void testOnTestSuccess() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParseException {
        // Get clear instance of suite log collection
        TestSuiteLogCollection suiteLogCollection = QtafFactory.getTestSuiteLogCollection();
        suiteLogCollection.clear();

        // Get instance of subscriber
        QtafScenarioLoggingSubscriber subscriber = new QtafScenarioLoggingSubscriber();

        // Make private method accessible
        Method method = QtafScenarioLoggingSubscriber.class.getDeclaredMethod(
                "onTestSuccess", IQtafTestEventPayload.class);
        method.setAccessible(true);

        // Create mock event
        IQtafTestEventPayload payload = new QtafTestEventPayload()
                .setFeatureId("f1")
                .setAbstractScenarioId("s1")
                .setScenarioName("scenario 1");

        // Assert indices and log collections are empty before the event occurs
        Assert.assertEquals(
                suiteLogCollection.countFeatureLogs(),
                0,
                "Assert there are zero feature logs before the event in the suite collection"
        );
        Assert.assertEquals(
                FeatureLogCollectionIndex.getInstance().size(),
                0,
                "Assert there are no feature logs in the index before the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().size(),
                0,
                "Assert there are no scenario logs in the index before the event"
        );

        // Call subscriber method
        method.invoke(subscriber, payload);

        // Assert indices and log collections hold te new log collections after the event
        Assert.assertEquals(
                suiteLogCollection.countFeatureLogs(),
                1,
                "Assert there is one feature log after the event"
        );
        Assert.assertEquals(
                FeatureLogCollectionIndex.getInstance().size(),
                1,
                "Assert there are is one feature log in the index after the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().size(),
                1,
                "Assert there are is one scenario log in the index after the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().get("s1").getStatus(),
                TestScenarioLogCollection.Status.SUCCESS,
                "Assert there the status of the new scenario log collection is success"
        );

        // Clear data
        suiteLogCollection.clear();

        // Clean indices
        ScenarioLogCollectionIndex.getInstance().clear();
        FeatureLogCollectionIndex.getInstance().clear();
    }

    @Test
    public void testOnTestFailure() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParseException {
        // Get clear instance of suite log collection
        TestSuiteLogCollection suiteLogCollection = QtafFactory.getTestSuiteLogCollection();
        suiteLogCollection.clear();

        // Get instance of subscriber
        QtafScenarioLoggingSubscriber subscriber = new QtafScenarioLoggingSubscriber();

        // Make private method accessible
        Method method = QtafScenarioLoggingSubscriber.class.getDeclaredMethod(
                "onTestFailure", IQtafTestEventPayload.class);
        method.setAccessible(true);

        // Create mock event
        IQtafTestEventPayload payload = new QtafTestEventPayload()
                .setFeatureId("f1")
                .setAbstractScenarioId("s1")
                .setScenarioName("scenario 1");

        // Assert indices and log collections are empty before the event occurs
        Assert.assertEquals(
                suiteLogCollection.countFeatureLogs(),
                0,
                "Assert there are zero feature logs before the event in the suite collection"
        );
        Assert.assertEquals(
                FeatureLogCollectionIndex.getInstance().size(),
                0,
                "Assert there are no feature logs in the index before the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().size(),
                0,
                "Assert there are no scenario logs in the index before the event"
        );

        // Call subscriber method
        method.invoke(subscriber, payload);

        // Assert indices and log collections hold te new log collections after the event
        Assert.assertEquals(
                suiteLogCollection.countFeatureLogs(),
                1,
                "Assert there is one feature log after the event"
        );
        Assert.assertEquals(
                FeatureLogCollectionIndex.getInstance().size(),
                1,
                "Assert there are is one feature log in the index after the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().size(),
                1,
                "Assert there are is one scenario log in the index after the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().get("s1").getStatus(),
                TestScenarioLogCollection.Status.FAILURE,
                "Assert there the status of the new scenario log collection is failed"
        );

        // Clear data
        suiteLogCollection.clear();

        // Clean indices
        ScenarioLogCollectionIndex.getInstance().clear();
        FeatureLogCollectionIndex.getInstance().clear();
    }

    @Test
    public void testOnTestSkipped() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParseException {
        // Get clear instance of suite log collection
        TestSuiteLogCollection suiteLogCollection = QtafFactory.getTestSuiteLogCollection();
        suiteLogCollection.clear();

        // Get instance of subscriber
        QtafScenarioLoggingSubscriber subscriber = new QtafScenarioLoggingSubscriber();

        // Make private method accessible
        Method method = QtafScenarioLoggingSubscriber.class.getDeclaredMethod(
                "onTestSkipped", IQtafTestEventPayload.class);
        method.setAccessible(true);

        // Create mock event
        IQtafTestEventPayload payload = new QtafTestEventPayload()
                .setFeatureId("f1")
                .setAbstractScenarioId("s1")
                .setScenarioName("scenario 1");

        // Assert indices and log collections are empty before the event occurs
        Assert.assertEquals(
                suiteLogCollection.countFeatureLogs(),
                0,
                "Assert there are zero feature logs before the event in the suite collection"
        );
        Assert.assertEquals(
                FeatureLogCollectionIndex.getInstance().size(),
                0,
                "Assert there are no feature logs in the index before the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().size(),
                0,
                "Assert there are no scenario logs in the index before the event"
        );

        // Call subscriber method
        method.invoke(subscriber, payload);

        // Assert indices and log collections hold te new log collections after the event
        Assert.assertEquals(
                suiteLogCollection.countFeatureLogs(),
                1,
                "Assert there is one feature log after the event"
        );
        Assert.assertEquals(
                FeatureLogCollectionIndex.getInstance().size(),
                1,
                "Assert there are is one feature log in the index after the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().size(),
                1,
                "Assert there are is one scenario log in the index after the event"
        );
        Assert.assertEquals(
                ScenarioLogCollectionIndex.getInstance().get("s1").getStatus(),
                TestScenarioLogCollection.Status.SKIPPED,
                "Assert there the status of the new scenario log collection is skipped"
        );

        // Clear data
        suiteLogCollection.clear();

        // Clean indices
        ScenarioLogCollectionIndex.getInstance().clear();
        FeatureLogCollectionIndex.getInstance().clear();
    }
}
