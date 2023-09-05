package de.qytera.qtaf.core.events;

import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.events.payload.IQtafTestEventPayload;
import de.qytera.qtaf.core.events.payload.IQtafTestStepEventPayload;
import de.qytera.qtaf.core.events.payload.IQtafTestingContext;
import de.qytera.qtaf.core.guice.invokation.*;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.selenium.AbstractDriver;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * This class manages events emitted by the Qtaf framework.
 */
public class QtafEvents {
    private QtafEvents() {
    }
    // Qtaf Framework events

    /**
     * Subject that emits an event when the event listeners are initialized.
     */
    public static final BehaviorSubject<Void> eventListenersInitialized = BehaviorSubject.create();

    /**
     * Subject that emits an event when the configuration is loaded.
     */
    public static final BehaviorSubject<ConfigMap> configurationLoaded = BehaviorSubject.create();

    /**
     * Subject that emits an event when the framework is initialized.
     */
    public static final BehaviorSubject<Void> frameworkInitialized = BehaviorSubject.create();

    /**
     * This event is dispatched in the TestNG factory class when the test classes are loaded
     */
    public static final BehaviorSubject<Set<Class<?>>> testClassesLoaded = BehaviorSubject.create();

    /**
     * This event is dispatched in the TestNG factory class when the test classes are instantiated
     */
    public static final BehaviorSubject<List<Object>> testClassInstancesLoaded = BehaviorSubject.create();

    /**
     * Subject that emits an event when the driver is initialized.
     * It transports the driver information object.
     */
    public static final BehaviorSubject<AbstractDriver> afterDriverInitialization = BehaviorSubject.create();


    // Testing lifecycle events

    /**
     * Subject that emits events when all tests are finished.
     */
    public static final PublishSubject<IQtafTestingContext> startTesting = PublishSubject.create();

    /**
     * Subject that emits events when all tests are finished.
     */
    public static final PublishSubject<IQtafTestingContext> finishedTesting = PublishSubject.create();

    /**
     * Subject that emits events before a method with the annotation @BeforeSuite is executed.
     */
    public static final PublishSubject<BeforeSuiteExecutionInfo> beforeTestSuite = PublishSubject.create();

    /**
     * Subject that emits events whenever a method with the annotation @BeforeSuite is executed successfully.
     */
    public static final PublishSubject<BeforeSuiteExecutionInfo> beforeTestSuiteSuccess = PublishSubject.create();

    /**
     * Subject that emits events whenever a method with the annotation @BeforeSuite is executed and throws an error.
     */
    public static final PublishSubject<BeforeSuiteExecutionInfo> beforeTestSuiteFailure = PublishSubject.create();

    /**
     * Subject that emits events before a method with the annotation @BeforeTest is executed.
     */
    public static final PublishSubject<BeforeTestExecutionInfo> beforeTestFeature = PublishSubject.create();

    /**
     * Subject that emits events whenever a method with the annotation @BeforeTest is executed successfully.
     */
    public static final PublishSubject<BeforeTestExecutionInfo> beforeTestFeatureSuccess = PublishSubject.create();

    /**
     * Subject that emits events whenever a method with the annotation @BeforeTest is executed and throws an error.
     */
    public static final PublishSubject<BeforeTestExecutionInfo> beforeTestFeatureFailure = PublishSubject.create();

    /**
     * Subject that emits events before a method with the annotation @AfterTest is executed.
     */
    public static final PublishSubject<AfterTestExecutionInfo> afterTestFeature = PublishSubject.create();

    /**
     * Subject that emits events before a method with the annotation @AfterTest is executed successfully.
     */
    public static final PublishSubject<AfterTestExecutionInfo> afterTestFeatureSuccess = PublishSubject.create();

    /**
     * Subject that emits events after a method with the annotation @AfterTest is executed and throws an error.
     */
    public static final PublishSubject<AfterTestExecutionInfo> afterTestFeatureFailure = PublishSubject.create();

    /**
     * Subject that emits events before a method with the annotation @AfterSuite is executed.
     */
    public static final PublishSubject<AfterSuiteExecutionInfo> afterTestSuite = PublishSubject.create();

    /**
     * Subject that emits events whenever a method with the annotation @AfterSuite is executed successfully.
     */
    public static final PublishSubject<AfterSuiteExecutionInfo> afterTestSuiteSuccess = PublishSubject.create();

    /**
     * Subject that emits events whenever a method with the annotation @AfterSuite is executed and throws an error.
     */
    public static final PublishSubject<AfterSuiteExecutionInfo> afterTestSuiteFailure = PublishSubject.create();

    /**
     * Subject that emits events before a method with the annotation @BeforeMethod is executed.
     */
    public static final PublishSubject<BeforeMethodExecutionInfo> beforeTestScenario = PublishSubject.create();

    /**
     * Subject that emits events whenever a method with the annotation @BeforeMethod is executed successfully.
     */
    public static final PublishSubject<BeforeMethodExecutionInfo> beforeTestScenarioSuccess = PublishSubject.create();

    /**
     * Subject that emits events whenever a method with the annotation @BeforeMethod is executed and throws an error.
     */
    public static final PublishSubject<BeforeMethodExecutionInfo> beforeTestScenarioFailure = PublishSubject.create();

    /**
     * Subject that emits events before a method with the annotation @AfterMethod is executed.
     */
    public static final PublishSubject<AfterMethodExecutionInfo> afterTestScenario = PublishSubject.create();

    /**
     * Subject that emits events whenever a method with the annotation @AfterMethod is executed successfully.
     */
    public static final PublishSubject<AfterMethodExecutionInfo> afterTestScenarioSuccess = PublishSubject.create();

    /**
     * Subject that emits events whenever a method with the annotation @AfterMethod is executed and throws an error.
     */
    public static final PublishSubject<AfterMethodExecutionInfo> afterTestScenarioFailure = PublishSubject.create();

    /**
     * Subject that emits events when all tests are finished.
     */
    public static final PublishSubject<IQtafTestEventPayload> testStarted = PublishSubject.create();

    /**
     * Subject that emits an event when a test finishes successfully.
     */
    public static final PublishSubject<IQtafTestEventPayload> testSuccess = PublishSubject.create();

    /**
     * Subject that emits an event when a test finishes with error.
     */
    public static final PublishSubject<IQtafTestEventPayload> testFailure = PublishSubject.create();

    /**
     * Subject that emits an event when a test is skipped.
     */
    public static final PublishSubject<IQtafTestEventPayload> testSkipped = PublishSubject.create();

    /**
     * Subject that emits an event when a test is skipped.
     */
    public static final PublishSubject<IQtafTestEventPayload> testFailedButWithinSuccessPercentage = PublishSubject.create();


    // Step execution events

    /**
     * Subject that emits an event before a step method is executed and transfers information about the executed step.
     */
    public static final PublishSubject<StepExecutionInfo> beforeStepExecution = PublishSubject.create();

    /**
     * Subject that emits an event when a step method is executed successfully and transfers information about the executed step.
     */
    public static final PublishSubject<StepExecutionInfo> stepExecutionSuccess = PublishSubject.create();

    /**
     * Subject that emits an event when a step method fails and transfers information about the executed step.
     */
    public static final PublishSubject<StepExecutionInfo> stepExecutionFailure = PublishSubject.create();

    /**
     * Subject that emits an event when a new LogMessage object is created.
     */
    public static final PublishSubject<IQtafTestStepEventPayload> stepLog = PublishSubject.create();

    /**
     * Subject that emits an event when a screenshot is taken.
     */
    public static final PublishSubject<File> screenshotTaken = PublishSubject.create();


    // Logging events

    /**
     * Subject that emits an event before the logs are persisted to the disk.
     * It transports the log collection.
     */
    public static final PublishSubject<TestSuiteLogCollection> beforeLogsPersisted = PublishSubject.create();

    /**
     * Subject that emits an event when the logs were persisted successfully to the disk. It transports the file path
     * where the log file is stored.
     */
    public static final PublishSubject<String> logsPersisted = PublishSubject.create();
}
