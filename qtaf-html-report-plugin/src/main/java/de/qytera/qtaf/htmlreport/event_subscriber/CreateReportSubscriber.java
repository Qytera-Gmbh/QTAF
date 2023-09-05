package de.qytera.qtaf.htmlreport.event_subscriber;

import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.htmlreport.creator.FeatureReportCreator;
import de.qytera.qtaf.htmlreport.creator.ReportCreator;
import de.qytera.qtaf.htmlreport.creator.ScenarioReportCreator;
import rx.Subscription;

/**
 * A subscriber that creates HTML reports on log persistence.
 */
public class CreateReportSubscriber implements IEventSubscriber {

    /**
     * Subscription reference.
     */
    Subscription onBeforeLogsPersistedSub;

    @Override
    public void initialize() {
        this.onBeforeLogsPersistedSub = QtafEvents.beforeLogsPersisted.subscribe(
                this::onBeforeLogsPersisted
        );
    }

    private void onBeforeLogsPersisted(TestSuiteLogCollection logCollection) {
        ReportCreator reportCreator = new ReportCreator();
        FeatureReportCreator featureReportCreator = new FeatureReportCreator();
        ScenarioReportCreator scenarioReportCreator = new ScenarioReportCreator();

        // Create report that includes all features and scenarios
        reportCreator.createReport(logCollection);

        for (TestFeatureLogCollection featureLog : logCollection.getTestFeatureLogCollections()) {
            // Create a report for each feature
            featureReportCreator.createReport(logCollection, featureLog);

            for (TestScenarioLogCollection scenarioLog : featureLog.getScenarioLogCollection()) {
                // Create a report for each scenario
                scenarioReportCreator.createReport(logCollection, scenarioLog);
            }
        }
    }
}
