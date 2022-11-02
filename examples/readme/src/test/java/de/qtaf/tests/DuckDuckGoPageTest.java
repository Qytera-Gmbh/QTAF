package de.qtaf.tests;

import de.qtaf.pages.DuckDuckGoPage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import javax.inject.Singleton;

import static org.testng.Assert.assertTrue;

@TestFeature(
        name = "duckduckgo search",
        description = "tests the search feature from https://duckduckgo.com"
)
@Singleton
public class DuckDuckGoPageTest extends QtafTestNGContext {

    private static final DuckDuckGoPage examplePage = new DuckDuckGoPage();

    @Test(testName = "QTAF-001", description = "test a simple search")
    public void testSearch() {
        examplePage.openTestPage();
        examplePage.enterSearchTerm("test automation");
        examplePage.clickSearchButton();
        assertTrue(driver.getTitle().contains("test automation"));
    }

}
