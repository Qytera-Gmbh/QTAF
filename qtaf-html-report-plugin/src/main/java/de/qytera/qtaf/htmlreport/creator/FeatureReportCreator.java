package de.qytera.qtaf.htmlreport.creator;

import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;

import java.util.Map;

/**
 * HTML report creator for scenarios
 */
public class FeatureReportCreator extends ReportCreator {
    /**
     * Root template
     */
    protected String rootTemplate = "de/qytera/qtaf/htmlreport/templates/feature.html";

    /**
     * Scenario Log Collection
     */
    protected TestFeatureLogCollection featureLog;

    /**
     * Create feature report
     *
     * @param logCollection        Log collection
     * @param featureLogCollection Feature Log Collection
     * @return true on success
     */
    public boolean createReport(
            TestSuiteLogCollection logCollection,
            TestFeatureLogCollection featureLogCollection
    ) {
        this.featureLog = featureLogCollection;
        return createReport(logCollection);
    }

    @Override
    protected Map<String, Object> getTemplateContext(TestSuiteLogCollection logCollection) {
        Map<String, Object> context = super.getTemplateContext(logCollection);
        context.put("featureLog", featureLog);
        return context;
    }

    @Override
    public String getReportPath(TestSuiteLogCollection logCollection) {
        return DirectoryHelper.preparePath(
                logCollection.getLogDirectory() + "/feature_" + this.featureLog.getFeatureId() + ".html"
        );
    }

    @Override
    protected String getRootTemplate() {
        return rootTemplate;
    }
}
