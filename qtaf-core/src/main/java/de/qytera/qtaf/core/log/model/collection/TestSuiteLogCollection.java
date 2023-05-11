package de.qytera.qtaf.core.log.model.collection;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.io.DirectoryHelper;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Collection that holds all log messages from all test classes
 */
public class TestSuiteLogCollection {

    /**
     * Singleton instance
     */
    private static TestSuiteLogCollection instance = new TestSuiteLogCollection();

    /**
     * Random unique ID
     */
    private final UUID uuid = UUID.randomUUID();

    /**
     * Suite information
     */
    private SuiteInfo suiteInfo = new SuiteInfo();

    /**
     * System info
     */
    private final SystemInfo systemInfo = new SystemInfo();

    /**
     * Process Info
     */
    private final ProcessInfo processInfo = new ProcessInfo();

    /**
     * Thread Info
     */
    private final ThreadInfo threadInfo = new ThreadInfo();

    /**
     * Operating system
     */
    private String osName = System.getProperty("os.name");

    /**
     * Driver name
     */
    private String driverName = "";

    /**
     * Test Suite tags
     */
    private final Map<String, String> tags = new ConcurrentHashMap<>();

    /**
     * Time when testing started
     */
    private Date start = new Date();

    /**
     * Time when testing ended
     */
    private Date end = new Date();

    /**
     * Duration of testing
     */
    private long duration = 0L;

    /**
     * Directory where logs are stored
     */
    private String logDirectory = null;

    /**
     * Holds a collection of test feature log collections
     */
    private final List<TestFeatureLogCollection> testFeatureLogCollections = Collections.synchronizedList(new ArrayList<>());

    /**
     * Constructor
     */
    private TestSuiteLogCollection() {
        super();
        buildLogDirectoryPath();
    }

    /**
     * Get instance of class
     *
     * @return instance of class
     */
    public static synchronized TestSuiteLogCollection getInstance() {
        return instance;
    }

    /**
     * Get uuid
     *
     * @return uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Get logDirectory
     *
     * @return logDirectory
     */
    public String getLogDirectory() {
        return logDirectory;
    }

    /**
     * Build the log directory path
     *
     * @return log directory path
     */
    public TestSuiteLogCollection buildLogDirectoryPath() {
        if (this.logDirectory == null) {
            SimpleDateFormat dirDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dirHourFormatter = new SimpleDateFormat("HH-mm-ss");

            this.logDirectory = DirectoryHelper.preparePath(
                    "$USER_DIR/logs/"
                            + dirDateFormatter.format(this.start)
                            + "/" + dirHourFormatter.format(this.start)
                            + "-" + this.getDriverName() + "-" + uuid
            );
        }

        return this;
    }

    /**
     * Get test case log collections
     *
     * @return log collections
     */
    public synchronized List<TestFeatureLogCollection> getTestFeatureLogCollections() {
        return testFeatureLogCollections;
    }

    /**
     * Clear test case log collections
     */
    public synchronized void clearCollection() {
        testFeatureLogCollections.clear();
    }

    /**
     * Add new test case collection
     *
     * @param collection Collection
     * @return this
     */
    public synchronized TestSuiteLogCollection addTestClassLogCollection(TestFeatureLogCollection collection) {
        if (!this.testFeatureLogCollections.contains(collection)) {
            testFeatureLogCollections.add(collection);
        }

        return this;
    }

    /**
     * Create a new log collection if it was not created before
     *
     * @param featureId   Hash code of the test scenario
     * @param featureName Scenario name / Class ID of the test
     * @return new collection
     */
    public synchronized TestFeatureLogCollection createFeatureIfNotExists(String featureId, String featureName) {
        TestFeatureLogCollection collection = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists(
                featureId,
                featureName
        );

        if (!this.testFeatureLogCollections.contains(collection)) {
            this.testFeatureLogCollections.add(collection);
            QtafFactory.getLogger().debug(String.format("Added Feature log: feature_id=%s, name=%s, size=%s, suite_hash=%s, feature_hash=%s", featureId, featureName, testFeatureLogCollections.size(), this.hashCode(), collection.hashCode()));
        }

        return collection;
    }

    /**
     * Set instance
     *
     * @param instance Instance
     */
    public synchronized static void setInstance(TestSuiteLogCollection instance) {
        TestSuiteLogCollection.instance = instance;
    }

    /**
     * Get suiteInfo
     *
     * @return suiteInfo
     */
    public SuiteInfo getSuiteInfo() {
        return suiteInfo;
    }

    /**
     * Set suiteInfo
     *
     * @param suiteInfo SuiteInfo
     * @return this
     */
    public TestSuiteLogCollection setSuiteInfo(SuiteInfo suiteInfo) {
        this.suiteInfo = suiteInfo;
        return this;
    }

    /**
     * Get systemInfo
     *
     * @return systemInfo
     */
    public SystemInfo getSystemInfo() {
        return systemInfo;
    }

    /**
     * Get processInfo
     *
     * @return processInfo
     */
    public ProcessInfo getProcessInfo() {
        return processInfo;
    }

    /**
     * Get threadInfo
     *
     * @return threadInfo
     */
    public ThreadInfo getThreadInfo() {
        return threadInfo;
    }

    /**
     * Get osName
     *
     * @return osName
     */
    public String getOsName() {
        return osName;
    }

    /**
     * Set osName
     *
     * @param osName OsName
     * @return this
     */
    public TestSuiteLogCollection setOsName(String osName) {
        this.osName = osName;
        return this;
    }

    /**
     * Get driverName
     *
     * @return driverName
     */
    public String getDriverName() {
        if (driverName == null || driverName.equals("")) {
            return "none";
        }

        return driverName;
    }

    /**
     * Set driverName
     *
     * @param driverName DriverName
     * @return this
     */
    public TestSuiteLogCollection setDriverName(String driverName) {
        this.driverName = driverName;
        this.buildLogDirectoryPath();
        return this;
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
    public TestSuiteLogCollection setStart(Date start) {
        this.start = start;
        return this;
    }

    /**
     * Get end
     *
     * @return end
     */
    public Date getEnd() {
        if (end == null) {
            end = new Date();
        }

        return end;
    }

    /**
     * Set end
     *
     * @param end End
     * @return this
     */
    public TestSuiteLogCollection setEnd(Date end) {
        this.end = end;
        return this;
    }

    /**
     * Get duration
     *
     * @return duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Set duration
     *
     * @param duration Duration
     * @return this
     */
    public TestSuiteLogCollection setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Count feature logs
     *
     * @return number of feature logs
     */
    public int countFeatureLogs() {
        return this.testFeatureLogCollections.size();
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
     * Get tag
     *
     * @param key Tag key
     * @return Tag value
     */
    public String getTag(String key) {
        return this.tags.get(key);
    }

    /**
     * Add tag
     *
     * @param key   Tag key
     * @param value Tag value
     */
    public void addTag(String key, String value) {
        this.tags.put(key, value);
    }

    /**
     * Remove tag
     *
     * @param key tag key
     */
    public void removeTag(String key) {
        this.tags.remove(key);
    }

    /**
     * Clear data
     */
    public void clear() {
        this.start = null;
        this.end = null;
        this.duration = 0;
        this.driverName = null;
        this.clearCollection();
        this.suiteInfo.name = null;
        this.suiteInfo.outputDir = null;
        this.tags.clear();
    }

    /**
     * Suite information
     */
    public class SuiteInfo {
        /**
         * Suite name
         */
        private String name = "";

        /**
         * Reports output directory
         */
        private String outputDir;

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
        public SuiteInfo setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Get outputDir
         *
         * @return outputDir
         */
        public String getOutputDir() {
            return outputDir;
        }

        /**
         * Set outputDir
         *
         * @param outputDir OutputDir
         * @return this
         */
        public SuiteInfo setOutputDir(String outputDir) {
            this.outputDir = outputDir;
            return this;
        }
    }

    /**
     * Information about the system the test suite was executed on
     */
    public class SystemInfo {
        /**
         * Operating system name
         */
        private String osName = System.getProperty("os.name");

        /**
         * Java version
         */
        private String javaVersion = System.getProperty("java.version");

        /**
         * User directory
         */
        private String userDir = System.getProperty("user.dir");

        /**
         * Get osName
         *
         * @return osName
         */
        public String getOsName() {
            return osName;
        }

        /**
         * Get javaVersion
         *
         * @return javaVersion
         */
        public String getJavaVersion() {
            return javaVersion;
        }

        /**
         * Set javaVersion
         *
         * @param javaVersion JavaVersion
         * @return this
         */
        public SystemInfo setJavaVersion(String javaVersion) {
            this.javaVersion = javaVersion;
            return this;
        }

        /**
         * Get userDir
         *
         * @return userDir
         */
        public String getUserDir() {
            return userDir;
        }

        /**
         * Set userDir
         *
         * @param userDir UserDir
         * @return this
         */
        public SystemInfo setUserDir(String userDir) {
            this.userDir = userDir;
            return this;
        }
    }

    /**
     * A class describing {@link Process} information.
     */
    public static class ProcessInfo {
        /**
         * Current Process
         */
        private ProcessHandle currentProcess = ProcessHandle.current();

        /**
         * Current processes PID
         */
        private long pid;

        /**
         * Constructor
         */
        ProcessInfo() {
            pid = currentProcess.pid();
        }

        /**
         * Get PID of current process
         *
         * @return PID of current process
         */
        public long getPid() {
            return pid;
        }
    }

    /**
     * Thread information
     */
    public class ThreadInfo {
        /**
         * Thread ID
         */
        private long threadId = Thread.currentThread().getId();

        /**
         * Thread name
         */
        private String threadName = Thread.currentThread().getName();

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
        public ThreadInfo setThreadId(long threadId) {
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
        public ThreadInfo setThreadName(String threadName) {
            this.threadName = threadName;
            return this;
        }
    }
}
