package de.qytera.qtaf.core;

import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.events.EventListenerInitializer;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.log.model.error.FrameworkInitializationErrorLog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is responsible for initializing objects and workspaces (directories and files).
 */
public class QtafInitializer {
    /**
     * Initialization status.
     */
    private static boolean isInitialized = false;

    /**
     * Error logger.
     */
    private static final ErrorLogCollection errorLogs = ErrorLogCollection.getInstance();

    private QtafInitializer() {
    }

    /**
     * Initialize the framework.
     */
    public static void initialize() {
        // Check initialization status
        if (isInitialized) {
            return;
        }

        // Initialize the event system
        try {
            // Initialize the event system
            EventListenerInitializer.initialize();

            // Inform event listeners about event system initialization
            QtafEvents.eventListenersInitialized.onNext(null);
            QtafEvents.eventListenersInitialized.onCompleted();
        } catch (Exception e) {
            // Inform event listeners about error during event system initialization
            QtafEvents.eventListenersInitialized.onError(e);
            handleError(e);
        }

        // Initialize file system
        try {
            createDirectories();
        } catch (IOException e) {
            handleError(e);
        }

        // Inform event listeners that the framework is initialized
        QtafEvents.frameworkInitialized.onNext(null);
        QtafEvents.frameworkInitialized.onCompleted();

        // Initialize the configuration
        ConfigMap config = QtafFactory.getConfiguration();
        QtafEvents.configurationLoaded.onNext(config);
        QtafEvents.configurationLoaded.onCompleted();

        isInitialized = true;
    }

    /**
     * Get status of Initializer.
     *
     * @return initialization status
     */
    public static boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Created needed directories.
     *
     * @throws IOException error during file system initialization
     */
    private static void createDirectories() throws IOException {
        String userDir = System.getProperty("user.dir");
        String fs = System.getProperty("file.separator");

        Files.createDirectories(Paths.get(userDir + fs + "logs"));
    }

    /**
     * Handle errors that occurred during the framework initialization process.
     *
     * @param e Exception
     */
    private static void handleError(Exception e) {
        errorLogs.addErrorLog(new FrameworkInitializationErrorLog(e));
        QtafEvents.frameworkInitialized.onError(e);

        System.exit(1);
    }
}
