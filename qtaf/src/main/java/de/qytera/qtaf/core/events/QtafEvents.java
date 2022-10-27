package de.qytera.qtaf.core.events;

import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.selenium.AbstractDriver;
import de.qytera.qtaf.core.events.payload.IQtafTestEventPayload;
import de.qytera.qtaf.core.events.payload.IQtafTestStepEventPayload;
import de.qytera.qtaf.core.events.payload.IQtafTestingContext;
import de.qytera.qtaf.core.guice.invokation.StepExecutionInfo;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import java.io.File;

/**
 * This class manages events emitted by the Qtaf framework
 */
public class QtafEvents {
    // Qtaf Framework events

    /**
     * Subject that emits an event when the event listeners are initialized
     */
    public static final BehaviorSubject<Void> eventListenersInitialized = BehaviorSubject.create();

    /**
     * Subject that emits an event when the configuration is loaded
     */
    public static final BehaviorSubject<ConfigMap> configurationLoaded = BehaviorSubject.create();

    /**
     * Subject that emits an event when the framework is initialized
     */
    public static final BehaviorSubject<Void> frameworkInitialized = BehaviorSubject.create();

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
     * Subject that emits an event before a step method is executed and transfers information about the executed step
     */
    public static final PublishSubject<StepExecutionInfo> beforeStepExecution = PublishSubject.create();

    /**
     * Subject that emits an event when a step method is executed successfully and transfers information about the executed step
     */
    public static final PublishSubject<StepExecutionInfo> stepExecutionSuccess = PublishSubject.create();

    /**
     * Subject that emits an event when a step method fails and transfers information about the executed step
     */
    public static final PublishSubject<StepExecutionInfo> stepExecutionFailure = PublishSubject.create();

    /**
     * Subject that emits an event when a new LogMessage object is created
     */
    public static final PublishSubject<IQtafTestStepEventPayload> stepLog = PublishSubject.create();

    /**
     * Subject that emits an event when a screenshot is taken
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
