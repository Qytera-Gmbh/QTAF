package de.qytera.qtaf.apitesting.restassured;

import com.codeborne.selenide.Condition;
import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;
import static org.hamcrest.Matchers.equalTo;

public class UIApiTests extends QtafTestNGContext implements ApiTest {
    private static final String url = "https://dummyjson.com";

    @Test(testName = "Get phone information", description = "Use dummyjson fake api to get the first phone")
    public void getTheFirstPhone() {
        apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/products/1"),
                List.of(statusCodeIs(200), body("id", equalTo(1)))
        );
    }

    @Test(testName = "Get an information from api response", description = "Check if the branch of the first phone is apple")
    public void getResponseText() {
        String productOneUrl = "https://dummyjson.com/products/1";
        String productOneImage = "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg";
        driver.get(productOneUrl);
        var showedResponse = $("pre").shouldBe(Condition.visible, Duration.ofSeconds(10)).getText();
        assertTrue(showedResponse.contains("Apple"), "Try to find the phone brand", false);

        restartDriver();
        driver.get(productOneImage);
        var imageElement = $("img").shouldBe(Condition.visible, Duration.ofSeconds(10));
        assertEquals(imageElement.attr("src"), productOneImage, "Check image url", false);
    }
}