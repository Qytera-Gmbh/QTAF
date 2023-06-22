package de.qytera.qtaf.core.event_subscriber.step;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.guice.invokation.StepExecutionInfo;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.core.selenium.DriverFactory;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This subscriber reacts to lifecycle events of step methods
 */
public class StepLoggerSubscriber implements IEventSubscriber {
    /**
     * Search index for step log objects
     */
    private final Map<Integer, StepInformationLogMessage> stepIdLogMap = new HashMap<>();

    /**
     * Logger
     */
    private final Logger logger = QtafFactory.getLogger();

    @Override
    public void initialize() {
        QtafEvents.beforeStepExecution.subscribe(this::onBeforeStepExecution);
        QtafEvents.stepExecutionSuccess.subscribe(this::onStepExecutionSuccess);
        QtafEvents.stepExecutionFailure.subscribe(this::onStepExecutionFailure);
    }

    /**
     * Method is executed before step is executed
     *
     * @param stepExecutionInfo step execution info object
     */
    private void onBeforeStepExecution(StepExecutionInfo stepExecutionInfo) {
        this.log(stepExecutionInfo, "started");

        // Get method invocation object
        MethodInvocation methodInvocation = stepExecutionInfo.getMethodInvocation();

        // Get object context
        IQtafTestContext context = (IQtafTestContext) methodInvocation.getThis();
        assert context != null;

        // Class name of page object class where step method is defined
        String className = context.getClass().getName()
                .replace("$$EnhancerByGuice$$", "")
                .replaceAll("[A-Fa-f0-9]+$", "");

        // Get step annotation of invoked method
        Step step = methodInvocation.getMethod().getAnnotation(Step.class);

        // Create log message object
        StepInformationLogMessage logMessage = new StepInformationLogMessage(
                className + "." + methodInvocation.getMethod().getName(),
                "Step " + step.name() + " executed"
        )
                .setStep(step)
                .setStart(new Date());

        // Check if Selenium should take a screenshot
        if (SeleniumDriverConfigHelper.shouldTakeScreenshotsBeforeStep()) {
            // Take a screenshot
            String screenshotFilePath = this.stepExecutionScreenshot(
                    stepExecutionInfo,
                    "before",
                    logMessage.getUuid()
            );

            // Add screenshot path to log message
            logMessage.setScreenshotBefore(screenshotFilePath);
        }


        // Store log in map
        stepIdLogMap.put(stepExecutionInfo.getId(), logMessage);

        // Initialize context is it is not already initialized
        if (context.getLogCollection() == null) {
            throw new AssertionError("""
                    The LogCollection of the context class was not initialized properly.
                    You may check the following points:
                    \t- All your methods that are annotated with @Test, @BeforeXXX, @AfterXXX must be public""");
        }

        // Add log message to collection
        context.getLogCollection().addLogMessage(logMessage);

        // Get method parameters
        Parameter[] params = methodInvocation.getMethod().getParameters();

        // Get method parameter values
        Object[] args = methodInvocation.getArguments();

        // Add arguments to log message
        for (int i = 0; i < params.length; i++) {
            // Add information about the parameters to the log message
            logMessage.addStepParameter(params[i].getName(), args[i]);
        }
    }

    /**
     * Method is executed when step is processed without throwing an exception
     *
     * @param stepExecutionInfo step execution info object
     */
    private void onStepExecutionSuccess(StepExecutionInfo stepExecutionInfo) {
        this.log(stepExecutionInfo, "success");

        // Add information to log message
        StepInformationLogMessage logMessage = stepIdLogMap.get(stepExecutionInfo.getId());

        // Add information to log message
        logMessage
                .setEnd(new Date())
                .setResult(stepExecutionInfo.getResult());

        if (SeleniumDriverConfigHelper.shouldTakeScreenshotsAfterStep()) {
            // Take a screenshot
            String screenshotFilePath = this.stepExecutionScreenshot(stepExecutionInfo, "after", logMessage.getUuid());

            // Add screenshot path to log message
            logMessage.setScreenshotAfter(screenshotFilePath);
        }
    }

    /**
     * Method is executed when step is throwing an exception
     *
     * @param stepExecutionInfo step execution info object
     */
    private void onStepExecutionFailure(StepExecutionInfo stepExecutionInfo) {
        this.log(stepExecutionInfo, "failure");

        // Add information to log message
        StepInformationLogMessage logMessage = stepIdLogMap.get(stepExecutionInfo.getId());

        // Add information to log message
        logMessage
                .setEnd(new Date())
                .setError(stepExecutionInfo.getError());

        if (SeleniumDriverConfigHelper.shouldTakeScreenshotsAfterStep() ||
                SeleniumDriverConfigHelper.shouldTakeScreenshotsAfterStepFailure()
        ) {
            // Take a screenshot
            String screenshotFilePath = this.stepExecutionScreenshot(stepExecutionInfo, "after", logMessage.getUuid());

            // Add screenshot path to log message
            logMessage.setScreenshotAfter(screenshotFilePath);
        }
    }

    /**
     * Take a screenshot
     *
     * @param stepExecutionInfo Step execution info object
     * @param status            Step status
     * @param uuid              Unique id
     * @return Screenshot path
     */
    private String stepExecutionScreenshot(StepExecutionInfo stepExecutionInfo, String status, UUID uuid) {
        WebDriver driver = QtafFactory.getWebDriver();

        // Check if driver was quit
        if (DriverFactory.driverHasQuit()) {
            return null;
        }

        // Get an instance of the suite log collection
        TestSuiteLogCollection suiteLogCollection = TestSuiteLogCollection.getInstance();

        // Take screenshot
        File srcFile = this.takeScreenshot(driver);

        // Get path where screenshot should be stored
        String path = this.getStepScreenshotDestinationPath(
                stepExecutionInfo,
                status,
                suiteLogCollection.getLogDirectory(),
                uuid
        );

        // save the screenshot and return the path of it
        File destFile = this.saveStepScreenshot(srcFile, path);
        return destFile.getAbsolutePath();
    }

    /**
     * Take screenshot
     *
     * @param driver WebDriver object
     * @return Screenshot file object
     */
    private File takeScreenshot(WebDriver driver) {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        return scrShot.getScreenshotAs(OutputType.FILE);
    }

    /**
     * Get destination path of screenshot file
     *
     * @param stepExecutionInfo Step execution info object
     * @param status            Step status
     * @param logDir            Log directory
     * @return Screenshot path
     */
    private String getStepScreenshotDestinationPath(
            StepExecutionInfo stepExecutionInfo,
            String status,
            String logDir,
            UUID uuid
    ) {
        String name = stepExecutionInfo.getMethodInvocation().getMethod().getName();

        return logDir
                + "/" + name + "_"
                + status + "_"
                + uuid
                + ".png";
    }

    /**
     * Save step screenshot file
     *
     * @param srcFile Screenshot file object
     * @param path    Destination path
     * @return Screenshot file object
     */
    private File saveStepScreenshot(File srcFile, String path) {
        File destFile = new File(
                DirectoryHelper.preparePath(path)
        );

        try {
            // Copy screenshot to destination path
            FileUtils.copyFile(srcFile, destFile);

            // Dispatch event
            QtafEvents.screenshotTaken.onNext(destFile);
        } catch (IOException e) {
            QtafFactory.getLogger().error(e);
        }

        return destFile;
    }

    /**
     * Logging to console
     *
     * @param stepExecutionInfo step execution info object
     * @param message           log message
     */
    private void log(StepExecutionInfo stepExecutionInfo, String message) {
        logger.info(
                "[Step] " +
                        "[" + stepExecutionInfo.getId() + "] " +
                        "[" + stepExecutionInfo.getAnnotation().name() + "] " +
                        message
        );
    }
}