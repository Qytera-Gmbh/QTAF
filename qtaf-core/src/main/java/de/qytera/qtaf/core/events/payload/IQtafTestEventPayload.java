package de.qytera.qtaf.core.events.payload;

import java.util.Date;

/**
 * Interface that all test event entity classes have to implement
 */
public interface IQtafTestEventPayload {
    /**
     * Get the ID of a feature
     * @return  feature ID
     */
    int getFeatureId();

    /**
     * Get the name of a feature
     * @return  feature name
     */
    String getFeatureName();

    /**
     * Get the description of a feature
     * @return  feature description
     */
    String getFeatureDescription();

    /**
     * Get the ID of a scenario
     * @return  scenario ID
     */
    String getScenarioId();

    /**
     * Get the name of a scenario
     * @return  scenario name
     */
    String getScenarioName();

    /**
     * Get the description of a scenario
     * @return scenario description
     */
    String getScenarioDescription();

    /**
     * Get the status of a scenario
     * @return  scenario status
     */
    ScenarioStatus getScenarioStatus();

    /**
     * Get the start date of a scenario
     * @return scenario start date
     */
    Date getScenarioStart();

    /**
     * Get the end date of a scenario
     * @return scenario end date
     */
    Date getScenarioEnd();

    /**
     * Get the thread ID of a scenario
     * @return  scenario thread ID
     */
    long getThreadId();

    /**
     * Get the name of the thread the scenario was executed in
     * @return  scenario thread name
     */
    String getThreadName();

    /**
     * Get groups that the test belongs to
     * @return groups
     */
    String[] getGroups();

    /**
     * Get all groups that the current test depends on
     * @return  group dependencies
     */
    String[] getGroupDependencies();

    /**
     * Get all other test methods this tests depends on
     * @return method dependencies
     */
    String[] getMethodDependencies();

    /**
     * Get the original event fired by the test framework (like TestNG, Cucumber, ...)
     * @return  original test event
     */
    Object getOriginalEvent();
}
