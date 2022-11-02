package de.qytera.qtaf.cucumber.helper;

import de.qytera.qtaf.core.reflection.FieldHelper;
import io.cucumber.messages.types.Feature;
import io.cucumber.testng.FeatureWrapper;

public class CucumberFeatureWrapperHelper {
    /**
     * Get Feature object from FeatureWrapper object
     * @param featureWrapper     FeatureWrapper object
     * @return  Feature object
     */
    public static Feature getFeature(FeatureWrapper featureWrapper) {
        return (Feature) FieldHelper.getFieldValue(featureWrapper, "feature");
    }

}
