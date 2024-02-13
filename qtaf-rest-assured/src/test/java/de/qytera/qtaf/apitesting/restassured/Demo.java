package de.qytera.qtaf.apitesting.restassured;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.ExecutedApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import static org.hamcrest.Matchers.*;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

@TestFeature(
        name = "Api Feature Demo",
        description = "Some tests to demonstrate the Api testing feature"
)
public class Demo extends QtafTestNGContext implements ApiTest {

    // "https://jsonplaceholder.typicode.com" has a free to use dummy api
    private static final String url = "https://jsonplaceholder.typicode.com";

    @Test(testName = "simple status code test")
    public void simpleStatusCodeTest() {
        apiTest(
            this,
            List.of(baseUri(url)),
            getRequest("/posts/1"),
            List.of(
                    statusCodeIs(200)
            )
        );
    }

    @Test(testName = "simple is not status code test")
    public void simpleIsNotStatusCodeTest() {
        apiTest(
            this,
            List.of(baseUri(url)),
            getRequest("/posts/1"),
            List.of(
                    statusCodeIsNot3xx(),
                    statusCodeIsNot4xx(),
                    statusCodeIsNot(505)
            )
        );
    }

    @Test(testName = "simple is not status code test")
    public void statusCodeAndTimeTest() {
        apiTest(
            this,
            List.of(baseUri(url)),
            getRequest("/posts/1"),
            List.of(
                    statusCodeIs2xx(),
                    responseTimeShouldBeLessThanXMilliseconds(1200)
            )
        );
    }

    @Test(testName = "status code test with path params")
    public void statusCodeTestWithPathParams() {
        String[] idParams = {"1","2","3"};
        for (String idParam : idParams){
            apiTest(
                    this,
                    List.of(
                            baseUri(url),
                            pathParam("id", idParam)
                    ),
                    getRequest("/posts/{id}"),
                    List.of(
                            statusCodeIs2xx()
                    )
            );
        }
    }

    @Test(testName = "combined api and ui test")
    public void combinedApiAndUITest() throws ParseException {
        String url = "https://jsonplaceholder.typicode.com/";
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement usersLink = driver.findElement(By.partialLinkText("/posts/1"));
        usersLink.click();

        String textDisplayedInUi = driver.findElement(By.cssSelector("pre")).getText();

        // Convert String to JSON and get value by key "userId"
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(textDisplayedInUi);
        String uiUserId = json.get("userId").toString();

        ExecutedApiTest result = apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/posts/1"),
                List.of(
                        statusCodeIs(200)
                )
        );
        String apiUserId = result.getRes().body().jsonPath().getString("userId");
        Assert.assertEquals(apiUserId, uiUserId);

        // System.out.println(apiUserId);
        // System.out.println(result.getRes().body().asString());
    }
















    // ========== Not part of the demo ===========





















    @Test(testName = "simple is not status code test")
    public void statusCodeAndTimeTestBug() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/posts/1"),
                List.of(
                        statusCodeIs2xx(),
                        responseTimeShouldBeLessThanXMilliseconds(100)
                        // TODO: Potential Bug:
                        // in the log message actual value of response time assertion was set to 200
                        // although this is the accual value of the status code not the response time
                        // The assertion type was set to assert equals -> it should be less than a value not equals
                )
        );
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromContext(this);
        apiLogMessageFitsTo(
                "",
                latestApiLogMessage,
                LogMessage.Status.FAILED,
                2,
                ApiLogMessage.Action.RequestType.GET,
                200
        );
        List<AssertionLogMessage> assertionLogMessages  = getAssertionMessagesFormApiLogMessage(latestApiLogMessage);

        apiAssertionMessageFitsTo(
                "assertion 0",
                assertionLogMessages.get(0),
                AssertionLogMessageType.ASSERT_EQUALS,
                true,
                200,
                "2xx",
                LogMessage.Status.PASSED
        );
        apiAssertionMessageFitsTo(
                "assertion 1",
                assertionLogMessages.get(1),
                AssertionLogMessageType.ASSERT_EQUALS,
                false,
                latestApiLogMessage.getResponse().getTime(),
                100L,
                LogMessage.Status.FAILED
        );
        changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
    }

    @Test(testName = "Test Response Body")
    public void combinedApiAndUITestBugasdf() {
        assertTrue(0==1,"test", true); // TODO: potential Bug -> assertion is not displayed in log-message
    }

    @Test(testName = "Test Response Body")
    public void combinedApiAndUITestBug() {

        /*
        String url = "https://jsonplaceholder.typicode.com/";
        driver.get(url);
        // By userLinkSelector = By.linkText("/posts");
        // System.out.println(userLinkSelector);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // WebElement usersLink = driver.findElement(By.linkText("/posts"));
        WebElement usersLink = driver.findElement(By.partialLinkText("/posts/1"));

        System.out.println(usersLink.getText());
        usersLink.click();
        System.out.println(driver.findElement(By.cssSelector("pre")).getText());

        String textDisplayedInUi = driver.findElement(By.cssSelector("pre")).getText();

         */

        /*
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(textDisplayedInUi);
        //String uiUserId = json.get("userId").toString();
        Object uiUserId = json.get("userId");

        System.out.println("========");
        System.out.println(uiUserId);
         */

        //String userIdDisplayedOnUi = textDisplayedInUi.jsonPath().getString("id");

        //driver.get("https://jsonplaceholder.typicode.com/posts");

        ExecutedApiTest result = apiTest(
                this,
                List.of(baseUri(url)),
                getRequest("/posts/1"),
                List.of(
                        statusCodeIs(200),
                        body("userId", equalTo(1)) // TODO: potential bug -> Behavior: no report gets created, the test is successful
                )
        );
        System.out.println(result.getRes().body().asString());
    }
}
