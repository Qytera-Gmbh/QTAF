package de.qytera.qtaf.core.log.service;

import com.google.gson.Gson;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class for writing log file.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogFileWriter {

    /**
     * TestSuiteLogCollection instance.
     */
    private static final TestSuiteLogCollection logCollection = TestSuiteLogCollection.getInstance();

    /**
     * Persist logs to local disk.
     *
     * @param collection log collection
     * @return path to log file if it was created successfully
     */
    public static String persistLogs(TestSuiteLogCollection collection) {
        // Get Gson instance
        Gson gson = GsonFactory.getInstance();

        try {
            // Transform log collection to JSON string
            String json = gson.toJson(collection);

            // Write log file
            String reportPath = DirectoryHelper.preparePath(logCollection.getLogDirectory() + "/" + "Report.json");

            DirectoryHelper.createDirectoryIfNotExists(logCollection.getLogDirectory());
            Files.write(Paths.get(reportPath), json.getBytes());
            return reportPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create a error log file.
     *
     * @param collection error logs
     */
    public static void persistErrorLogs(ErrorLogCollection collection) {
        // Get Gson instance
        Gson gson = GsonFactory.getInstance();

        // Transform log collection to JSON string
        String json = gson.toJson(collection);

        String reportPath = DirectoryHelper.preparePath(logCollection.getLogDirectory() + "/" + "Error.json");

        try {
            DirectoryHelper.createDirectoryIfNotExists(logCollection.getLogDirectory());
            Files.write(Paths.get(reportPath), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
