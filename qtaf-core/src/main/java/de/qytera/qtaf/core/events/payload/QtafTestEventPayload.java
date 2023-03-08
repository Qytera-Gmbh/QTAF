package de.qytera.qtaf.core.events.payload;

import java.lang.reflect.Parameter;
import java.util.Date;

/**
 * Entity class for test events
 */
public class QtafTestEventPayload implements IQtafTestEventPayload {
    /**
     * Original event fired by the test framework (TestNG, Cucumber, ...)
     */
    protected Object originalEvent;

    /**
     * Original test instance object
     */
    protected Object originalTestInstance;

    /**
     * ID of the test feature
     */
    protected String featureId;

    /**
     * Class name where the feature is implemented
     */
    protected String featureClassName;

    /**
     * Name of the package where the feature is implemented
     */
    protected String featurePackageName;

    /**
     * Name of the feature
     */
    protected String featureName;

    /**
     * Description of the feature
     */
    protected String featureDescription;

    /**
     * ID of the scenario
     */
    protected String scenarioId;

    /**
     * Name of the scenario
     */
    protected String scenarioName;

    /**
     * Description of the scenario
     */
    protected String scenarioDescription;

    /**
     * Status of the scenario
     */
    protected ScenarioStatus scenarioStatus;

    /**
     * Start date of the scenario
     */
    protected Date scenarioStart;

    /**
     * End date of the scenario
     */
    protected Date scenarioEnd;

    /**
     * Thread the test was executed in
     */
    protected Thread thread;

    /**
     * Thread ID of the scenario
     */
    protected long threadId;

    /**
     * Thread name of the scenario
     */
    protected String threadName;

    /**
     * Parameters that were passed to the scenario
     */
    protected Parameter[] scenarioParameters;

    /**
     * Values of the parameters that were passed to the scenario
     */
    protected Object[] parameterValues;

    /**
     * scenario groups
     */
    protected String[] groups = new String[]{};

    /**
     * All groups that the scenario depends on (see TestNG group dependencies)
     */
    protected String[] groupDependencies = new String[]{};

    /**
     * All test methods the scenario depends on (see testNG method dependencies)
     */
    protected String[] methodDependencies = new String[]{};

    /**
     * Scenario Method Name
     */
    protected String scenarioMethodName;

    /**
     * Get uniqueId
     *
     * @return uniqueId
     */
    public String getFeatureId() {
        return featureId;
    }

    /**
     * Set uniqueId
     *
     * @param featureId UniqueId
     * @return this
     */
    public QtafTestEventPayload setFeatureId(String featureId) {
        this.featureId = featureId;
        return this;
    }

    /**
     * Get featureClassName
     *
     * @return featureClassName
     */
    public String getFeatureClassName() {
        return featureClassName;
    }

    /**
     * Set featureClassName
     *
     * @param featureClassName FeatureClassName
     * @return this
     */
    public QtafTestEventPayload setFeatureClassName(String featureClassName) {
        this.featureClassName = featureClassName;
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
     * Get featurePackageName
     *
     * @return featurePackageName
     */
    public String getFeaturePackageName() {
        return featurePackageName;
    }

    /**
     * Set featurePackageName
     *
     * @param featurePackageName FeaturePackageName
     * @return this
     */
    public QtafTestEventPayload setFeaturePackageName(String featurePackageName) {
        this.featurePackageName = featurePackageName;
        return this;
    }

    /**
     * Get scenarioMethodName
     *
     * @return scenarioMethodName
     */
    @Override
    public String getScenarioMethodName() {
        return scenarioMethodName;
    }

    /**
     * Set scenarioMethodName
     *
     * @param scenarioMethodName ScenarioMethodName
     * @return this
     */
    public QtafTestEventPayload setScenarioMethodName(String scenarioMethodName) {
        this.scenarioMethodName = scenarioMethodName;
        return this;
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

    /**
     * Get originalTestInstance
     *
     * @return originalTestInstance
     */
    public Object getOriginalTestInstance() {
        return originalTestInstance;
    }

    /**
     * Set originalTestInstance
     *
     * @param originalTestInstance OriginalTestInstance
     * @return this
     */
    public QtafTestEventPayload setOriginalTestInstance(Object originalTestInstance) {
        this.originalTestInstance = originalTestInstance;
        return this;
    }

    /**
     * Get thread
     *
     * @return thread
     */
    public Thread getThread() {
        return thread;
    }

    /**
     * Set thread
     *
     * @param thread Thread
     * @return this
     */
    public QtafTestEventPayload setThread(Thread thread) {
        this.thread = thread;
        return this;
    }
}
