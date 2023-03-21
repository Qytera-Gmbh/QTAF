package de.qytera.qtaf.core.event_subscriber.test;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.events.payload.IQtafTestEventPayload;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.log.model.index.ScenarioLogCollectionIndex;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.log.model.error.SeleniumScreenshotError;
import de.qytera.qtaf.core.log.repository.ScenarioLogCollectionRepository;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Event subscriber that takes screenshots of tests
 */
public class ScreenshotSubscriber implements IEventSubscriber {
    @Override
    public void initialize() {
        if (SeleniumDriverConfigHelper.shouldTakeScreenshotsBeforeScenario()) {
            QtafEvents.testStarted.subscribe(
                    this::takeScreenshotBefore,
                    err -> QtafFactory.getLogger().error(err)
            );
        }

        if (SeleniumDriverConfigHelper.shouldTakeScreenshotsAfterScenario()) {
            QtafEvents.testSuccess.subscribe(
                    this::takeScreenshotAfter,
                    err -> QtafFactory.getLogger().error(err)
            );
            QtafEvents.testFailure.subscribe(
                    this::takeScreenshotAfter,
                    err -> QtafFactory.getLogger().error(err)
            );
        }
    }

    /**
     * Take a screenshot of the currently displayed web page and save it
     * @param payload   event payload
     * @param status    test status
     */
    private void takeTestScreenshot(IQtafTestEventPayload payload, String status) {
        WebDriver driver = QtafFactory.getWebDriver();
        TestSuiteLogCollection suiteLogCollection = TestSuiteLogCollection.getInstance();

        // Take screenshot
        File srcFile = this.takeTestScreenshot(driver);

        // Generate screenshot path
        String path = this.getScreenshotDestinationPath(
                payload.getScenarioId(),
                suiteLogCollection.getLogDirectory(),
                status
        );

        // Save screenshot file
        this.saveScreenshot(srcFile, path, payload.getScenarioId());

        // Save screenshot path in logs
        TestScenarioLogCollection logCollection = ScenarioLogCollectionIndex.getInstance().get(payload.getScenarioId());

        if (status.equals("before")) {
            logCollection.setScreenshotBefore(path);
        } else {
            logCollection.setScreenshotAfter(path);
        }
    }

    /**
     * Take a screenshot of the currently displayed web page and save it
     * @param payload   event payload
     */
    private void takeScreenshotBefore(IQtafTestEventPayload payload) {
        this.takeTestScreenshot(payload, "before");
    }

    /**
     * Take a screenshot of the currently displayed web page and save it
     * @param payload   event payload
     */
    private void takeScreenshotAfter(IQtafTestEventPayload payload) {
        this.takeTestScreenshot(payload, "after");
    }

    /**
     * Take screenshot
     * @param driver    WebDriver object
     * @return  Screenshot file object
     */
    private File takeTestScreenshot(WebDriver driver) {
        TakesScreenshot scrShot = ((TakesScreenshot)driver);
        return scrShot.getScreenshotAs(OutputType.FILE);
    }

    /**
     * Get screenshot destination path
     * @param scenarioId    Scenario ID
     * @param logDir        Log directory
     * @return              Path where screenshot should be saved
     */
    private String getScreenshotDestinationPath(String scenarioId, String logDir, String status) {
        return logDir + "/"
                + scenarioId + "_"
                + status + ".png";
    }
    /**
     * Save screenshot file
     * @param srcFile   Screenshot file object
     * @param path      Destination path
     */
    private void saveScreenshot(File srcFile, String path, String scenarioId) {
        File destFile = new File(
                DirectoryHelper.preparePath(path)
        );

        try {
            // Copy screenshot to destination path
            FileUtils.copyFile(srcFile, destFile);

            // Add screenshot path to log message
            TestScenarioLogCollection scenarioLog = ScenarioLogCollectionRepository.findById(scenarioId);
            scenarioLog.addScreenshotPath(destFile.getPath());

            // Dispatch event
            QtafEvents.screenshotTaken.onNext(destFile);
        } catch (IOException e) {
            handleError(e);
        }

    }

    /**
     * Handle exceptions that occurred during invocation of subscriber method
     * @param e Exception object
     */
    private void handleError(Throwable e) {
        SeleniumScreenshotError error = new SeleniumScreenshotError(e);
        ErrorLogCollection errors = ErrorLogCollection.getInstance();
        errors.addErrorLog(error);
    }

}
