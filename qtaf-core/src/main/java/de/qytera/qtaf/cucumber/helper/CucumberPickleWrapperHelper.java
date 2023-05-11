package de.qytera.qtaf.cucumber.helper;

import de.qytera.qtaf.core.reflection.FieldHelper;
import io.cucumber.testng.Pickle;
import io.cucumber.testng.PickleWrapper;

/**
 * Helper class that provides methods for extracting information from cucumber pickle wrapper objects
 */
public class CucumberPickleWrapperHelper {
    private CucumberPickleWrapperHelper() {}
    /**
     * Get Pickle object from PickleWrapper object
     *
     * @param pickleWrapper PickleWrapper object
     * @return Pickle object
     */
    public static Pickle getPickle(PickleWrapper pickleWrapper) {
        return (Pickle) FieldHelper.getFieldValue(pickleWrapper, "pickle");
    }
}
