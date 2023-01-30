package de.qytera.qtaf.cucumber.entity;

import de.qytera.qtaf.cucumber.helper.*;
import io.cucumber.core.gherkin.Feature;
import io.cucumber.messages.types.Tag;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.Pickle;
import io.cucumber.testng.PickleWrapper;

import java.util.List;
import java.util.Map;

/**
 * Factory class for QTAF Cucumber Scenario Entities
 */
public class QTAFCucumberScenarioEntityFactory {
    /**
     * Get instance from FeatureWrapper and PickleWrapper
     * @param pickleWrapper     PickleWrapper object
     * @param featureWrapper    FeatureWrapper object
     * @return  scenario entity
     */
    public static QTAFCucumberScenarioEntity getEntity(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        QTAFCucumberScenarioEntity scenarioEntity = new QTAFCucumberScenarioEntity();

        Feature iFeature = CucumberFeatureWrapperHelper.getFeature(featureWrapper);

        assert iFeature != null;
        io.cucumber.messages.types.Feature feature = CucumberFeatureHelper.getFeature(iFeature);

        assert feature != null;
        String featureName = String.valueOf(feature.getName());
        List<Tag> tags = feature.getTags();

        Map<String, String> featureTags = CucumberTagHelper.getKeyValuePairsFromTagList(tags);

        Pickle testngPickle = CucumberPickleWrapperHelper.getPickle(pickleWrapper);

        Map<String, List<String>> scenarioTags = CucumberTagHelper.getKeyValueListPairs(testngPickle.getTags());
        String scenarioName = testngPickle.getName();

        scenarioEntity
                .setFeatureName(featureName)
                .setFeatureTags(featureTags)
                .setScenarioName(scenarioName)
                .setScenarioTags(scenarioTags)
                .parseGroupTags()
                .parseTestSetTags();

        return scenarioEntity;
    }
}
