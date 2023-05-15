package de.qytera.qtaf.cucumber.events.payload;

import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import de.qytera.qtaf.core.events.payload.ScenarioStatus;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.cucumber.helper.CucumberFeatureHelper;
import de.qytera.qtaf.cucumber.helper.CucumberScenarioHelper;
import de.qytera.qtaf.cucumber.helper.CucumberTestCaseStateHelper;
import de.qytera.qtaf.cucumber.helper.CucumberTestResultHelper;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.TestCase;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Cucumber event payload information class
 */
public class CucumberScenarioEventPayload extends QtafTestEventPayload {
    /**
     * Constructor
     *
     * @param scenario Original scenario event payload
     */
    public CucumberScenarioEventPayload(Scenario scenario) {
        this.originalEvent = scenario;
        this.featureName = this.getFeatureNameFromScenario(scenario);
        this.featureId = this.featureName;

        // Extract information from Scenario object
        Map<String, String> tagData = CucumberScenarioHelper.getTagMetaData(scenario);
        TestCaseState testCaseState = CucumberScenarioHelper.getTestCaseState(scenario);
        TestCase testCase = CucumberTestCaseStateHelper.getTestCase(testCaseState);
        assert testCase != null;
        List<Result> testStepResults = CucumberTestCaseStateHelper.getStepResults(testCaseState);

        this.abstractScenarioId = scenario.getId().replace(DirectoryHelper.preparePath("file:///$USER_DIR/"), "");
        this.scenarioName = tagData.get("TestName");
        this.scenarioDescription = scenario.getName();
        this.scenarioStart = new Date();
        this.scenarioEnd = new Date();

        // Set scenario status
        assert testStepResults != null;

        if (CucumberTestResultHelper.didAllStepsPass(testStepResults)) {
            this.setScenarioStatus(ScenarioStatus.SUCCESS);
        } else {
            this.setScenarioStatus(ScenarioStatus.FAILURE);
        }

    }

    /**
     * Get name of feature file from Scenario object
     *
     * @param scenario Scenario information object
     * @return Feature name
     */
    private String getFeatureNameFromScenario(Scenario scenario) {
        URI scenarioURI = scenario.getUri();
        return CucumberFeatureHelper.getRelativeFeatureFilePath(
                scenarioURI.toString()
        );
    }
}
