package de.qytera.qtaf.core.events.payload;

import java.util.Date;

public interface IQtafTestEventPayload {
    int getFeatureId();
    String getFeatureName();
    String getFeatureDescription();
    String getScenarioId();
    String getScenarioName();
    String getScenarioDescription();
    ScenarioStatus getScenarioStatus();
    Date getScenarioStart();
    Date getScenarioEnd();
    long getThreadId();
    String getThreadName();
    String[] getGroups();
    String[] getGroupDependencies();
    String[] getMethodDependencies();
    Object getOriginalEvent();
}
