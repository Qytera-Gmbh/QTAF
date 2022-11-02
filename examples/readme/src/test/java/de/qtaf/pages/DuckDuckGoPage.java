package de.qtaf.pages;

import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DuckDuckGoPage extends QtafTestNGContext {

    @Step(
            name = "open test page",
            description = "opens a browser window and navigates to the test page"
    )
    public void openTestPage() {
        driver.get("https://duckduckgo.com");
        driver.manage().window().maximize();
    }

    @Step(
            name = "enter search term",
            description = "enters the given search term into the search field"
    )
    public void enterSearchTerm(String term) {
        WebElement inputField = driver.findElement(By.id("search_form_input_homepage"));
        inputField.sendKeys(term);
    }

    @Step(
            name = "click search button",
            description = "clicks on the search button next to the search field"
    )
    public void clickSearchButton() {
        driver.findElement(By.id("search_button_homepage")).click();
    }

}