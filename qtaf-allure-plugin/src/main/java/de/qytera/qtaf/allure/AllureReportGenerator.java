package de.qytera.qtaf.allure;

import com.google.gson.Gson;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import io.qameta.allure.model.TestResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class for generating Allure JSON files
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AllureReportGenerator {
    /**
     * Generates an Allure report based on the provided test suite log collection.
     *
     * @param suite the log collection
     * @throws IOException if the report cannot be created
     */
    public static void generateReport(TestSuiteLogCollection suite) throws IOException {
        // Map QTAF data structure to Allure data structure
        List<TestResult> testResults = AllureTestResultGenerator.fromQtafTestSuiteCollection(suite);

        // Build destination directory for JSON files
        String path = DirectoryHelper.preparePath("$USER_DIR/allure-results");
        DirectoryHelper.createDirectoryIfNotExists(path);

        // Generate a JSON file for each test feature
        for (TestResult testResult : testResults) {
            generateReportFile(suite, testResult, path);
        }
    }

    /**
     * Generate an Allure JSON file
     *
     * @param suite      Test Suite entity
     * @param testResult Allure test result entity
     * @param path       Destination Path
     */
    public static void generateReportFile(TestSuiteLogCollection suite, TestResult testResult, String path) {
        // Get Gson instance
        Gson gson = GsonFactory.getInstance();

        // Transform log collection to JSON string
        String json = gson.toJson(testResult);

        // Build destination path for JSON file
        String reportPath = DirectoryHelper.preparePath(
                Paths.get(path,
                                testResult
                                        .getTestCaseId()
                                        .replace(' ', '-')
                                        .replace('.', '-')
                                        .replace('_', '-')
                                        .replace("'", "")
                                        .toLowerCase() + suite.getStart().getTime() + "-result.json"
                        )
                        .toString()
        );

        // Save file
        try {
            DirectoryHelper.createDirectoryIfNotExists(suite.getLogDirectory());
            Files.write(Paths.get(reportPath), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
