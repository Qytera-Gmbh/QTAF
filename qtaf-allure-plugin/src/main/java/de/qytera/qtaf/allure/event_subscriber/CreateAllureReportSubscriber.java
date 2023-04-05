package de.qytera.qtaf.allure.event_subscriber;

import de.qytera.qtaf.allure.AllureReportGenerator;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;

import java.io.IOException;

/**
 * Event listener for Allure Report Plugin
 */
public class CreateAllureReportSubscriber implements IEventSubscriber {
    @Override
    public void initialize() {
        // Subscribe to event
        QtafEvents.beforeLogsPersisted.subscribe(
                this::generateReports,
                Throwable::printStackTrace
        );
    }

    /**
     * Generate Allure JSON files
     *
     * @param suite TestSuite
     */
    private void generateReports(TestSuiteLogCollection suite) {
        try {
            AllureReportGenerator.generateReport(suite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
