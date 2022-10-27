package de.qytera.qtaf.cucumber.helper;

import de.qytera.qtaf.core.reflection.FieldHelper;
import io.cucumber.testng.Pickle;

public class CucumberTestNGPickleHelper {
    /**
     * Get Gherkin Pickle object from TestNG Pickle object
     * @param testngPickle     PickleWrapper object
     * @return  Pickle object
     */
    public static io.cucumber.core.gherkin.Pickle getPickle(Pickle testngPickle) {
        return (io.cucumber.core.gherkin.Pickle) FieldHelper.getFieldValue(testngPickle, "pickle");
    }
}
