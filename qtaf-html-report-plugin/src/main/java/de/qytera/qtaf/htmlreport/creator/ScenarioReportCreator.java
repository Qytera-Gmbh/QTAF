package de.qytera.qtaf.htmlreport.creator;

import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;

import java.io.Writer;
import java.util.Map;

/**
 * HTML report creator for scenarios
 */
public class ScenarioReportCreator extends ReportCreator {
    /**
     * Root template
     */
    protected String rootTemplate = "de/qytera/qtaf/htmlreport/templates/scenario.html";

    /**
     * Scenario Log Collection
     */
    protected TestScenarioLogCollection scenarioLogCollection;

    /**
     * Create scenario report
     *
     * @param logCollection         Log collection
     * @param scenarioLogCollection Scenario Log Collection
     */
    public boolean createReport(
            TestSuiteLogCollection logCollection,
            TestScenarioLogCollection scenarioLogCollection
    ) {
        this.scenarioLogCollection = scenarioLogCollection;
        return createReport(logCollection);
    }

    public Writer getRenderedTemplate(
            TestSuiteLogCollection logCollection,
            TestScenarioLogCollection scenarioLogCollection
    ) {
        this.scenarioLogCollection = scenarioLogCollection;
        return getRenderedTemplate(logCollection);
    }

    @Override
    protected Map<String, Object> getTemplateContext(TestSuiteLogCollection logCollection) {
        Map<String, Object> context = super.getTemplateContext(logCollection);
        context.put("scenarioLog", scenarioLogCollection);
        return context;
    }

    @Override
    public String getReportPath(TestSuiteLogCollection logCollection) {
        return DirectoryHelper.preparePath(
                logCollection.getLogDirectory() + "/scenario_" + this.scenarioLogCollection.getScenarioName() + ".html"
        );
    }

    @Override
    protected String getRootTemplate() {
        return rootTemplate;
    }
}
