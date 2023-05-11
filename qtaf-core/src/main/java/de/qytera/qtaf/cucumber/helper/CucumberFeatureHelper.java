package de.qytera.qtaf.cucumber.helper;

import de.qytera.qtaf.core.reflection.FieldHelper;
import io.cucumber.core.gherkin.Feature;

/**
 * Helper class for extracting information from Cucumber features
 */
public class CucumberFeatureHelper {

    private CucumberFeatureHelper() {}
    /**
     * Get feature file path relative to project's source directory
     *
     * @param absolutePath The absolute path to the feature file
     * @return The relative path of the feature file
     */
    public static String getRelativeFeatureFilePath(String absolutePath) {
        String userDir = System.getProperty("user.dir").replace("\\", "/");
        return absolutePath
                .replace("file:///" + userDir, "")
                .replace("/src/java/", "")
                .replace("/src/test/", "");
    }

    /**
     * Get Feature object from Feature object
     *
     * @param iFeature Feature interface object
     * @return Feature object
     */
    public static io.cucumber.messages.types.Feature getFeature(Feature iFeature) {
        return (io.cucumber.messages.types.Feature) FieldHelper.getFieldValue(iFeature, "feature");
    }

}
