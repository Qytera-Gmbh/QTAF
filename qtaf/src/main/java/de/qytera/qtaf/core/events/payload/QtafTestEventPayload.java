package de.qytera.qtaf.core.events.payload;

import java.lang.reflect.Parameter;
import java.util.Date;

public class QtafTestEventPayload implements IQtafTestEventPayload {
    protected Object originalEvent;
    protected int featureId;
    protected String featureName;
    protected String featureDescription;
    protected String scenarioId;
    protected String scenarioName;
    protected String scenarioDescription;
    protected ScenarioStatus scenarioStatus;
    protected Date scenarioStart;
    protected Date scenarioEnd;
    protected long threadId;
    protected String threadName;
    protected Parameter[] scenarioParameters;
    protected Object[] parameterValues;
    protected String[] groups = new String[]{};
    protected String[] groupDependencies = new String[]{};
    protected String[] methodDependencies = new String[]{};

    /**
     * Get uniqueId
     *
     * @return uniqueId
     */
    public int getFeatureId() {
        return featureId;
    }

    /**
     * Set uniqueId
     *
     * @param featureId UniqueId
     * @return this
     */
    public QtafTestEventPayload setFeatureId(int featureId) {
        this.featureId = featureId;
        return this;
    }

    /**
     * Get featureName
     *
     * @return featureName
     */
    @Override
    public String getFeatureName() {
        return featureName;
    }

    /**
     * Set featureName
     *
     * @param featureName FeatureName
     * @return this
     */
    public QtafTestEventPayload setFeatureName(String featureName) {
        this.featureName = featureName;
        return this;
    }

    /**
     * Get featureDescription
     *
     * @return featureDescription
     */
    @Override
    public String getFeatureDescription() {
        return featureDescription;
    }

    /**
     * Set featureDescription
     *
     * @param featureDescription FeatureDescription
     * @return this
     */
    public QtafTestEventPayload setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
        return this;
    }

    /**
     * Get methodId
     *
     * @return methodId
     */
    public String getScenarioId() {
        return scenarioId;
    }

    /**
     * Set methodId
     *
     * @param scenarioId MethodId
     * @return this
     */
    public QtafTestEventPayload setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
        return this;
    }

    /**
     * Get testId
     *
     * @return testId
     */
    public String getScenarioName() {
        return scenarioName;
    }

    /**
     * Set testId
     *
     * @param scenarioName TestId
     * @return this
     */
    public QtafTestEventPayload setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
        return this;
    }

    /**
     * Get testDescription
     *
     * @return testDescription
     */
    @Override
    public String getScenarioDescription() {
        return scenarioDescription;
    }

    /**
     * Get scenarioStatus
     *
     * @return scenarioStatus
     */
    @Override
    public ScenarioStatus getScenarioStatus() {
        return scenarioStatus;
    }

    /**
     * Set scenarioStatus
     *
     * @param scenarioStatus ScenarioStatus
     * @return this
     */
    public QtafTestEventPayload setScenarioStatus(ScenarioStatus scenarioStatus) {
        this.scenarioStatus = scenarioStatus;
        return this;
    }

    /**
     * Get testStart
     *
     * @return testStart
     */
    @Override
    public Date getScenarioStart() {
        return scenarioStart;
    }

    /**
     * Set testStart
     *
     * @param scenarioStart TestStart
     * @return this
     */
    public QtafTestEventPayload setScenarioStart(Date scenarioStart) {
        this.scenarioStart = scenarioStart;
        return this;
    }

    /**
     * Get testEnd
     *
     * @return testEnd
     */
    @Override
    public Date getScenarioEnd() {
        return scenarioEnd;
    }

    /**
     * Set testEnd
     *
     * @param scenarioEnd TestEnd
     * @return this
     */
    public QtafTestEventPayload setScenarioEnd(Date scenarioEnd) {
        this.scenarioEnd = scenarioEnd;
        return this;
    }

    /**
     * Get threadId
     *
     * @return threadId
     */
    @Override
    public long getThreadId() {
        return threadId;
    }

    /**
     * Set threadId
     *
     * @param threadId ThreadId
     * @return this
     */
    public QtafTestEventPayload setThreadId(long threadId) {
        this.threadId = threadId;
        return this;
    }

    /**
     * Get threadName
     *
     * @return threadName
     */
    @Override
    public String getThreadName() {
        return threadName;
    }

    /**
     * Set threadName
     *
     * @param threadName ThreadName
     * @return this
     */
    public QtafTestEventPayload setThreadName(String threadName) {
        this.threadName = threadName;
        return this;
    }

    /**
     * Get parameters
     *
     * @return parameters
     */
    public Parameter[] getScenarioParameters() {
        return scenarioParameters;
    }

    /**
     * Set parameters
     *
     * @param scenarioParameters Parameters
     * @return this
     */
    public QtafTestEventPayload setScenarioParameters(Parameter[] scenarioParameters) {
        this.scenarioParameters = scenarioParameters;
        return this;
    }

    /**
     * Get parameterValues
     *
     * @return parameterValues
     */
    public Object[] getParameterValues() {
        return parameterValues;
    }

    /**
     * Set parameterValues
     *
     * @param parameterValues ParameterValues
     * @return this
     */
    public QtafTestEventPayload setParameterValues(Object[] parameterValues) {
        this.parameterValues = parameterValues;
        return this;
    }

    /**
     * Set testDescription
     *
     * @param scenarioDescription TestDescription
     * @return this
     */
    public QtafTestEventPayload setScenarioDescription(String scenarioDescription) {
        this.scenarioDescription = scenarioDescription;
        return this;
    }

    /**
     * Get groups
     *
     * @return groups
     */
    @Override
    public String[] getGroups() {
        return groups;
    }

    /**
     * Set groups
     *
     * @param groups Groups
     * @return this
     */
    public QtafTestEventPayload setGroups(String[] groups) {
        this.groups = groups;
        return this;
    }

    /**
     * Get groupDependencies
     *
     * @return groupDependencies
     */
    @Override
    public String[] getGroupDependencies() {
        return groupDependencies;
    }

    /**
     * Set groupDependencies
     *
     * @param groupDependencies GroupDependencies
     * @return this
     */
    public QtafTestEventPayload setGroupDependencies(String[] groupDependencies) {
        this.groupDependencies = groupDependencies;
        return this;
    }

    /**
     * Get methodDependencies
     *
     * @return methodDependencies
     */
    @Override
    public String[] getMethodDependencies() {
        return methodDependencies;
    }

    /**
     * Set methodDependencies
     *
     * @param methodDependencies MethodDependencies
     * @return this
     */
    public QtafTestEventPayload setMethodDependencies(String[] methodDependencies) {
        this.methodDependencies = methodDependencies;
        return this;
    }

    /**
     * Get originalEvent
     *
     * @return originalEvent
     */
    @Override
    public Object getOriginalEvent() {
        return originalEvent;
    }

    /**
     * Set originalEvent
     *
     * @param originalEvent OriginalEvent
     * @return this
     */
    public Object setOriginalEvent(Object originalEvent) {
        this.originalEvent = originalEvent;
        return this;
    }
}
