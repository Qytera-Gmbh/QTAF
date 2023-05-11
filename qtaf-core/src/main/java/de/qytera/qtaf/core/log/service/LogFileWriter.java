package de.qytera.qtaf.core.log.service;

import com.google.gson.Gson;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for writing log file
 */
public class LogFileWriter {
    private LogFileWriter() {}
    /**
     * File separator
     */
    private static final String fs = System.getProperty("file.separator");

    /**
     * Log file date format
     */
    private static final String dateFormat = "yyyy-MM-dd_HH-mm-ss";

    /**
     * TestSuiteLogCollection instance
     */
    private static TestSuiteLogCollection logCollection = TestSuiteLogCollection.getInstance();

    /**
     * Persist logs to local disk
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
     * Create a error log file
     *
     * @param collection error logs
     * @return Path where error logs are stored
     */
    public static String persistErrorLogs(ErrorLogCollection collection) {
        // Get Gson instance
        Gson gson = GsonFactory.getInstance();

        // Transform log collection to JSON string
        String json = gson.toJson(collection);

        // Write log file
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date date = new Date();

        String reportPath = DirectoryHelper.preparePath(logCollection.getLogDirectory() + "/" + "Error.json");

        try {
            DirectoryHelper.createDirectoryIfNotExists(logCollection.getLogDirectory());
            Files.write(Paths.get(reportPath), json.getBytes());
            return reportPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
