package de.qytera.qtaf.testng;

import de.qytera.qtaf.core.config.annotations.TestFeature;
import org.testng.annotations.Test;

@TestFeature(
        name = "sample-test-feature-annotation-name",
        description = "sample-test-feature-annotation-description"
)
public class SampleRealClassTest {
    /**
     * Sample test method
     */
    @Test(
            testName = "sample-test-method-annotation-name",
            description = "sample-test-method-annotation-description",
            enabled = false
    )
    public void sampleTestMethod(String param1, Integer param2) {

    }
}
