package de.qytera.qtaf.cucumber.helper;

import de.qytera.qtaf.core.reflection.FieldHelper;
import io.cucumber.core.gherkin.Feature;
import io.cucumber.testng.FeatureWrapper;

/**
 * Helper class that provides methods to extract information from Cucumber feature wrapper objects
 */
public class CucumberFeatureWrapperHelper {
    private CucumberFeatureWrapperHelper() {}
    /**
     * Get Feature object from FeatureWrapper object
     *
     * @param featureWrapper FeatureWrapper object
     * @return Feature object
     */
    public static Feature getFeature(FeatureWrapper featureWrapper) {
        return (Feature) FieldHelper.getFieldValue(featureWrapper, "feature");
    }

}
