package de.qytera.qtaf.core.log.model.collection;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.payload.IQtafTestEventPayload;
import de.qytera.qtaf.core.log.model.LogLevel;
import de.qytera.qtaf.core.log.model.index.LogMessageIndex;
import de.qytera.qtaf.core.log.model.index.ScenarioLogCollectionIndex;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * This class works as a collection for log messages that are produced during a test execution.
 */
public class TestScenarioLogCollection {
    /**
     * Index for log collections
     */
    private static final ScenarioLogCollectionIndex index = ScenarioLogCollectionIndex.getInstance();

    /**
     * Index for log messages
     */
    private static final LogMessageIndex logMessageIndex = LogMessageIndex.getInstance();

    /**
     * Unique test feature ID
     */
    private final String featureId;

    /**
     * Test method ID
     */
    private final String scenarioId;

    /**
     * Test ID
     */
    private final String scenarioName;

    /**
     * ID of the abstract scenario
     */
    private String abstractScenarioId;

    /**
     * ID of the concrete test scenario
     */
    private String instanceId;

    /**
     * Test description. Contains content of the 'description' attribute of the 'Test' annotation.
     */
    private String description;

    /**
     * Time when test started
     */
    private Date start = null;

    /**
     * Time when test ended
     */
    private Date end = null;

    /**
     * Test duration
     */
    private long duration = 0L;

    /**
     * Thread ID
     */
    private long threadId = 0L;

    /**
     * Thread name
     */
    private String threadName = "";

    /**
     * Test groups. Contains the groups a test belongs to.
     */
    private String[] groups = null;

    /**
     * Test group dependencies. Contains content of the 'groupDependencies' attribute of the 'Test' annotation.
     */
    private String[] groupDependencies = null;

    /**
     * Test method dependencies. Contains content of the 'methodDependencies' attribute of the 'Test' annotation.
     */
    private String[] methodDependencies = null;

    /**
     * Test method parameters
     */
    private final List<TestParameter> testParameters = Collections.synchronizedList(new ArrayList<>());

    /**
     * Annotations of the corresponding test method
     */
    private transient Annotation[] annotations;

    /**
     * Test status
     */
    private Status status = Status.PENDING;

    /**
     * Test log messages. Contains log messages that are produced during test execution.
     */
    private final List<LogMessage> logMessages = Collections.synchronizedList(new ArrayList<>());

    /**
     * Test screenshots. Contains paths to screenshot files.
     */
    private final List<String> screenshotPaths = Collections.synchronizedList(new ArrayList<>());

    /**
     * Path to screenshot file that was saved before execution of the step
     */
    private String screenshotBefore = "";

    /**
     * Path to screenshot file that was saved after execution of the step
     */
    private String screenshotAfter = "";

    /**
     * Test tags. Contains additional information about the test scenario.
     */
    private final Map<String, String> tags = Collections.synchronizedMap(new HashMap<>());

    /**
     * Constructor
     *
     * @param scenarioId Test ID
     */
    private TestScenarioLogCollection(String featureId, String scenarioId, String scenarioName) {
        this.featureId = featureId;
        this.scenarioId = scenarioId;
        this.scenarioName = scenarioName;

        // Logging
        QtafFactory.getLogger().debug(
                String.format(
                        "Created scenario log: id=%s, hash=%s, featureId=%s, scenarioName=%s",
                        scenarioId,
                        this.hashCode(),
                        featureId,
                        scenarioName
                )
        );
        QtafFactory.getConfiguration().getString("page.url");
        QtafFactory.getLogger().debug(
                String.format(
                        "feature log index: size=%s, scenario log index: size=%s",
                        index.size(),
                        ScenarioLogCollectionIndex.getInstance().size()
                )
        );
    }

    /**
     * Factory method.
     * <p>
     * Creates new test log collection.
     * If a collection with the given ID exists then return the existing collection.
     * This method has to be synchronized so that it works correctly when using multiple threads.
     *
     * @param featureId          Unique collection ID
     * @param abstractScenarioId Method ID (packageName + className + methodName)
     * @param instanceId         Test object ID
     * @param scenarioName       Test ID
     * @return test log collection
     */
    public static synchronized TestScenarioLogCollection createTestScenarioLogCollection(
            String featureId,
            String abstractScenarioId,
            String instanceId,
            String scenarioName
    ) {
        // Check if index already contains a scenario log collection with the given ID
        String scenarioId = buildId(abstractScenarioId, instanceId);

        if (index.get(scenarioId) != null) {
            return index.get(scenarioId);
        }

        // Create new scenario log collection and register it in the index
        TestScenarioLogCollection collection = new TestScenarioLogCollection(featureId, scenarioId, scenarioName);
        collection.setAbstractScenarioId(abstractScenarioId);
        collection.setInstanceId(instanceId);
        return index.put(scenarioId, collection);
    }

    /**
     * Factory method
     * <p>
     * Factory method that creates new log collection from test event payload
     * This method has to be synchronized so that it works correctly when using multiple threads.
     *
     * @param iQtafTestEventPayload test event payload
     * @return test log collection
     */
    public static synchronized TestScenarioLogCollection fromQtafTestEventPayload(IQtafTestEventPayload iQtafTestEventPayload) {
        // Build scenario ID
        String scenarioId = buildId(iQtafTestEventPayload.getAbstractScenarioId(), iQtafTestEventPayload.getInstanceId());

        // Check if index already contains a scenario log collection with the given ID
        if (index.get(scenarioId) != null) {
            return index.get(scenarioId);
        }

        // Create new scenario log collection
        TestScenarioLogCollection collection = new TestScenarioLogCollection(
                iQtafTestEventPayload.getFeatureId(),
                scenarioId,
                iQtafTestEventPayload.getScenarioName()
        );

        collection
                .setAbstractScenarioId(iQtafTestEventPayload.getAbstractScenarioId())
                .setInstanceId(iQtafTestEventPayload.getInstanceId())
                .setDescription(iQtafTestEventPayload.getScenarioDescription())
                .setStart(iQtafTestEventPayload.getScenarioStart())
                .setEnd(iQtafTestEventPayload.getScenarioEnd())
                .setThreadId(iQtafTestEventPayload.getThreadId())
                .setThreadName(iQtafTestEventPayload.getThreadName())
                .setGroups(iQtafTestEventPayload.getGroups())
                .setGroupDependencies(iQtafTestEventPayload.getGroupDependencies())
                .setMethodDependencies(iQtafTestEventPayload.getMethodDependencies());

        if (iQtafTestEventPayload.getMethodInfoEntity() != null) {
            collection
                    .setAnnotations(iQtafTestEventPayload.getMethodInfoEntity().getAnnotations())
                    .addParameters(iQtafTestEventPayload.getMethodInfoEntity().getMethodParamValues());
        }

        // Register new scenario log collection in the index
        return index.put(scenarioId, collection);
    }

    /**
     * Override equals to compare two TestScenarioLogCollection objects
     *
     * @param o Object to compare with this instance
     * @return true if both are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof TestScenarioLogCollection c)) {
            return false;
        }

        return getFeatureId().equals(c.getFeatureId()) &&
                getAbstractScenarioId().equals(c.getAbstractScenarioId()) &&
                getInstanceId().equals(c.getInstanceId());
    }

    /**
     * Calculate hash code for this instance.
     * The contains() methods of the collections use the hash code to check if object is already stored.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return (getFeatureId() + getAbstractScenarioId() + getInstanceId()).hashCode();
    }

    /**
     * Get uniqueId
     *
     * @return uniqueId
     */
    public String getFeatureId() {
        return featureId;
    }

    /**
     * Build ID
     *
     * @param abstractScenarioId Method ID
     * @param instanceId         Test ID
     * @return ID
     */
    public static String buildId(String abstractScenarioId, String instanceId) {
        return abstractScenarioId + "-" + instanceId;
    }

    /**
     * Get test ID
     *
     * @return test ID
     */
    public String getScenarioId() {
        return scenarioId;
    }

    /**
     * Get testId
     *
     * @return testId TestId
     */
    public String getScenarioName() {
        return scenarioName;
    }

    /**
     * Get abstractScenarioId
     *
     * @return abstractScenarioId
     */
    public String getAbstractScenarioId() {
        return Objects.requireNonNullElse(abstractScenarioId, "");
    }

    /**
     * Set abstractScenarioId
     *
     * @param abstractScenarioId AbstractScenarioId
     * @return this
     */
    public TestScenarioLogCollection setAbstractScenarioId(String abstractScenarioId) {
        this.abstractScenarioId = abstractScenarioId;
        return this;
    }

    /**
     * Get instanceId
     *
     * @return instanceId
     */
    public String getInstanceId() {
        return Objects.requireNonNullElse(instanceId, "");
    }

    /**
     * Set instanceId
     *
     * @param instanceId InstanceId
     * @return this
     */
    public TestScenarioLogCollection setInstanceId(String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    /**
     * Get status
     *
     * @return status Status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set test status
     *
     * @param status test status
     * @return this
     */
    public TestScenarioLogCollection setStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Set test description
     *
     * @param description test description
     * @return this
     */
    public TestScenarioLogCollection setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set test group dependencies
     *
     * @param groupDependencies test group dependencies
     * @return this
     */
    public TestScenarioLogCollection setGroupDependencies(String[] groupDependencies) {
        this.groupDependencies = groupDependencies;
        return this;
    }

    /**
     * Set test method dependencies
     *
     * @param methodDependencies test method dependencies
     * @return this
     */
    public TestScenarioLogCollection setMethodDependencies(String[] methodDependencies) {
        this.methodDependencies = methodDependencies;
        return this;
    }

    /**
     * Get testParameters
     *
     * @return testParameters
     */
    public List<TestParameter> getTestParameters() {
        return testParameters;
    }

    /**
     * Add test parameters to log
     *
     * @param parameters method parameters
     * @param values     method values
     * @return this
     */
    public TestScenarioLogCollection addParameters(Parameter[] parameters, Object[] values) {
        for (int i = 0; i < parameters.length; i++) {
            TestParameter testParameter = new TestParameter(
                    parameters[i].getName(),
                    values[i].getClass().getName(),
                    values[i]
            );

            this.testParameters.add(testParameter);
        }

        return this;
    }

    /**
     * Add test parameters to log
     *
     * @param parameterValues method values
     * @return this
     */
    public TestScenarioLogCollection addParameters(Object[] parameterValues) {
        for (int i = 0; i < parameterValues.length; i++) {
            TestParameter testParameter = new TestParameter(
                    "arg" + i,
                    parameterValues[i].getClass().getName(),
                    parameterValues[i]
            );

            this.testParameters.add(testParameter);
        }

        return this;
    }


    /**
     * Get annotations
     *
     * @return annotations
     */
    public Annotation[] getAnnotations() {
        return annotations;
    }

    /**
     * Get a specific annotation of this scenario. Always returns the first annotation found in case there are more
     * than one.
     *
     * @param annotationClass the class of the annotation to retrieve
     * @param <T>             the annotation's type
     * @return the annotation if it exists or null
     */
    public <T> T getAnnotation(Class<T> annotationClass) {
        for (Annotation annotation : annotations) {
            if (annotationClass.isInstance(annotation)) {
                return annotationClass.cast(annotation);
            }
        }
        return null;
    }

    /**
     * Set annotations
     *
     * @param annotations Annotations
     * @return this
     */
    public TestScenarioLogCollection setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
        return this;
    }

    /**
     * Get logMessages
     *
     * @return logMessages LogMessages
     */
    public synchronized List<LogMessage> getLogMessages() {
        if (logMessages.isEmpty()) {
            return LogMessageIndex.getInstance().getByScenarioId(getScenarioId());
        }

        return logMessages;
    }

    /**
     * Returns all log message of the provided class, filtering out log messages that do not match the class.
     *
     * @param logMessageClass the desired log messages' class
     * @param <T>             any class extending the {@link LogMessage} class
     * @return a list of log messages with type {@code T}
     */
    public synchronized <T extends LogMessage> List<T> getLogMessages(Class<T> logMessageClass) {
        return getLogMessages().stream()
                .filter(logMessageClass::isInstance)
                .map(logMessageClass::cast)
                .toList();
    }

    /**
     * Get the log message object of the step that is currently pending
     * @return  Step log object of the currently pending step
     */
    public synchronized StepInformationLogMessage getStepLogOfPendingStep() {
        List<LogMessage> logMessages = LogMessageIndex.getInstance().getByScenarioIdAndPending(getScenarioId());

        if (logMessages.size() > 0) {
            return (StepInformationLogMessage) logMessages.get(0);
        }

        return null;
    }

    /**
     * Add log message object
     * This methods needs to run synchronized because of the check for existence.
     *
     * @param logMessage log message object
     * @return this
     */
    public synchronized TestScenarioLogCollection addLogMessage(LogMessage logMessage) {
        if (!logMessages.contains(logMessage)) {
            // Add information about the scenario to the log message
            logMessage
                    .setFeatureId(getFeatureId())
                    .setAbstractScenarioId(getAbstractScenarioId())
                    .setScenarioId(getScenarioId());

            // Update the index
            logMessageIndex.put(logMessage.hashCode(), logMessage);

            // Add log message to this scenario
            logMessages.add(logMessage);

            QtafFactory.getLogger().debug(String.format("Added log message: message=%s, scenario_hash=%s", logMessage.getMessage(), this.hashCode()));
            QtafFactory.getLogger().debug(String.format("Scenario %s: log_messages_size=%s, scenario_hash=%s, log_messages_list_hash=%s", this.getScenarioId(), this.logMessages.size(), this.hashCode(), this.logMessages.hashCode()));
        }

        return this;
    }

    /**
     * Add log message
     *
     * @param level   log level
     * @param message log message
     * @return this
     */
    public synchronized TestScenarioLogCollection addLogMessage(LogLevel level, String message) {
        LogMessage logMessage = new LogMessage(level, message);
        logMessages.add(logMessage);
        return this;
    }

    /**
     * Get screenshotPaths
     *
     * @return screenshotPaths
     */
    public List<String> getScreenshotPaths() {
        return screenshotPaths;
    }

    /**
     * Add screenshot path to test scenario log
     *
     * @param filepath Path to screenshot file
     * @return this
     */
    public TestScenarioLogCollection addScreenshotPath(String filepath) {
        screenshotPaths.add(filepath);
        return this;
    }

    /**
     * Get screenshotBefore
     *
     * @return screenshotBefore
     */
    public String getScreenshotBefore() {
        return screenshotBefore;
    }

    /**
     * Set screenshotBefore
     *
     * @param screenshotBefore ScreenshotBefore
     * @return this
     */
    public TestScenarioLogCollection setScreenshotBefore(String screenshotBefore) {
        this.screenshotBefore = screenshotBefore;
        return this;
    }

    /**
     * Get screenshotAfter
     *
     * @return screenshotAfter
     */
    public String getScreenshotAfter() {
        return screenshotAfter;
    }

    /**
     * Set screenshotAfter
     *
     * @param screenshotAfter ScreenshotAfter
     * @return this
     */
    public TestScenarioLogCollection setScreenshotAfter(String screenshotAfter) {
        this.screenshotAfter = screenshotAfter;
        return this;
    }

    /**
     * Get tags
     *
     * @return tags
     */
    public Map<String, String> getTags() {
        return tags;
    }

    /**
     * Add tag to test scenario log
     *
     * @param key   Tag key
     * @param value Tag value
     * @return this
     */
    public TestScenarioLogCollection addTag(String key, String value) {
        tags.put(key, value);
        return this;
    }


    /**
     * Add debug message
     *
     * @param message message
     * @return this
     */
    public TestScenarioLogCollection debug(String message) {
        addLogMessage(LogLevel.DEBUG, message);
        return this;
    }

    /**
     * Add info message
     *
     * @param message message
     * @return this
     */
    public TestScenarioLogCollection info(String message) {
        addLogMessage(LogLevel.INFO, message);
        return this;
    }

    /**
     * Add warn message
     *
     * @param message message
     * @return this
     */
    public TestScenarioLogCollection warn(String message) {
        addLogMessage(LogLevel.WARN, message);
        return this;
    }

    /**
     * Add error message
     *
     * @param message message
     * @return this
     */
    public TestScenarioLogCollection error(String message) {
        addLogMessage(LogLevel.ERROR, message);
        return this;
    }

    /**
     * Get description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get groupDependencies
     *
     * @return groupDependencies
     */
    public String[] getGroupDependencies() {
        return groupDependencies;
    }

    /**
     * Get methodDependencies
     *
     * @return methodDependencies
     */
    public String[] getMethodDependencies() {
        return methodDependencies;
    }

    /**
     * Get start
     *
     * @return start
     */
    public Date getStart() {
        return start;
    }

    /**
     * Set start
     *
     * @param start Start
     * @return this
     */
    public TestScenarioLogCollection setStart(Date start) {
        this.start = start;
        return this;
    }

    /**
     * Get end
     *
     * @return end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Set end
     *
     * @param end End
     * @return this
     */
    public TestScenarioLogCollection setEnd(Date end) {
        this.end = end;
        return this;
    }

    /**
     * Get threadId
     *
     * @return threadId
     */
    public long getThreadId() {
        return threadId;
    }

    /**
     * Set threadId
     *
     * @param threadId ThreadId
     * @return this
     */
    public TestScenarioLogCollection setThreadId(long threadId) {
        this.threadId = threadId;
        return this;
    }

    /**
     * Get threadName
     *
     * @return threadName
     */
    public String getThreadName() {
        return threadName;
    }

    /**
     * Set threadName
     *
     * @param threadName ThreadName
     * @return this
     */
    public TestScenarioLogCollection setThreadName(String threadName) {
        this.threadName = threadName;
        return this;
    }

    /**
     * Get groups
     *
     * @return groups
     */
    public String[] getGroups() {
        return groups;
    }

    /**
     * Set groups
     *
     * @param groups Groups
     * @return this
     */
    public TestScenarioLogCollection setGroups(String[] groups) {
        this.groups = groups;
        return this;
    }

    /**
     * Get duration
     *
     * @return duration
     */
    public long getDuration() {
        if (this.end != null && this.start != null) {
            return this.end.getTime() - this.getStart().getTime();
        } else {
            return 0;
        }
    }

    /**
     * Set duration
     *
     * @param duration Duration
     * @return this
     */
    public TestScenarioLogCollection setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Test execution status
     */
    public enum Status {
        /**
         * The scenario is still pending execution.
         */
        PENDING,
        /**
         * The scenario was executed successfully.
         */
        SUCCESS,
        /**
         * The scenario execution resulted in errors.
         */
        FAILURE,
        /**
         * The scenario execution was skipped.
         */
        SKIPPED,
    }

    /**
     * Data class for step parameter information
     */
    public static class TestParameter {
        /**
         * Parameter name
         */
        private String name;

        /**
         * Parameter type
         */
        private String type;

        /**
         * Parameter value
         */
        private Object value;

        /**
         * Constructor
         *
         * @param name  parameter name
         * @param type  parameter type
         * @param value parameter value
         */
        public TestParameter(String name, String type, Object value) {
            this.name = name;
            this.type = type;
            this.value = value;
        }

        /**
         * Get name
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * Set name
         *
         * @param name Name
         * @return this
         */
        public TestParameter setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Get type
         *
         * @return type
         */
        public String getType() {
            return type;
        }

        /**
         * Set type
         *
         * @param type Type
         * @return this
         */
        public TestParameter setType(String type) {
            this.type = type;
            return this;
        }

        /**
         * Get value
         *
         * @return value
         */
        public Object getValue() {
            return value;
        }

        /**
         * Set value
         *
         * @param value Value
         * @return this
         */
        public TestParameter setValue(Object value) {
            this.value = value;
            return this;
        }
    }

    /**
     * Check if index already has this ScenarioLogCollection
     *
     * @param scenarioLogCollection Scenario log collection
     * @return true if exists, false otherwise
     */
    public static boolean exists(TestScenarioLogCollection scenarioLogCollection) {
        return index.get(scenarioLogCollection.getFeatureId()) != null;
    }

    /**
     * Count the number of scenario log collections saved in the index
     *
     * @return number of scenario log collections saved in the index
     */
    public static int getIndexSize() {
        return index.size();
    }

    /**
     * Remove all scenario log collections
     */
    public static void clearIndex() {
        index.clear();
    }
}
