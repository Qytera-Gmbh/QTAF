package de.qytera.qtaf.apitesting.restassured.mixed;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.apitesting.restassured.mixed.Entities.User;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static de.qytera.qtaf.apitesting.restassured.util.TestHelper.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

@TestFeature(name = "Authentication unit tests", description = "Check authentication and login process using Rest-assured")
public class AuthenticationTests extends QtafTestNGContext implements ApiTest {

    List<User> users;
    private User user1;
    private User user2;
    private final String USER_PROVIDER = "USER_PROVIDER";

    @BeforeTest
    public void initialise() {
        user1 = User.builder().username("kminchelle").password("0lelplR").build();
        user2 = User.builder().username("someone").password("somePassword").build();
        users = List.of(user1, user2);
    }

    @DataProvider(name = USER_PROVIDER)
    public Object[][] usersProvider() {
        List<Object[]> data = new ArrayList<>();
        for (User usr : users) {
            data.add(new Object[]{
                    usr
            });
        }
        return data.toArray(Object[][]::new);
    }
    private int iterationCheckUserName = 0;
    @Test(testName = "Authentification test using User Object", dataProvider = USER_PROVIDER)
    public void iterationCheckUserName(User user) {
        var token = getToken(user);
        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromLogMessages(getCurrentLogCollectionFrom(this));

        List<AssertionLogMessage> assertionLogMessages = getAssertionMessagesFormApiLogMessage(latestApiLogMessage);
        if (iterationCheckUserName == 0){
            apiLogMessageFitsTo("iterationCheckUserName 0",
                    latestApiLogMessage,
                    LogMessage.Status.PASSED,
                    1,
                    ApiLogMessage.Action.RequestType.POST,
                    200
            );
            apiAssertionMessageFitsTo(
                    "",
                    assertionLogMessages.get(0),
                    AssertionLogMessageType.ASSERT_EQUALS,
                    200,
                    200,
                    LogMessage.Status.PASSED
                    );
            assertEquals(token.getString("username"), user.getUsername(), "Something went wrong during the login process. Please check your credentials.", false);
        }
        if (iterationCheckUserName == 1){
            apiLogMessageFitsTo("iterationCheckUserName 1",
                    latestApiLogMessage,
                    LogMessage.Status.FAILED,
                    1,
                    ApiLogMessage.Action.RequestType.POST,
                    400
            );
            apiAssertionMessageFitsTo(
                    "",
                    assertionLogMessages.get(0),
                    AssertionLogMessageType.ASSERT_EQUALS,
                    400,
                    200,
                    LogMessage.Status.FAILED
            );
            changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
            assertEquals(token.getString("username"), null, "Something went wrong during the login process. Please check your credentials.", false);
        }
        iterationCheckUserName++;
    }

    int iterationGetTokenAndLogin = 0;
    @Test(testName = "Login test using username and password", dataProvider = USER_PROVIDER)
    public void getTokenAndLoginUsingUsernameAndPassword(User user) {
        String loginUrl = "https://dummyjson.com/auth";
        var token = getToken(user);

        ApiLogMessage latestApiLogMessage = getLatestApiLogMessageFromLogMessages(getCurrentLogCollectionFrom(this));
        List<AssertionLogMessage> assertionLogMessages = getAssertionMessagesFormApiLogMessage(latestApiLogMessage);

        // check getToken Api Call
        if (iterationGetTokenAndLogin == 0){
            Assert.assertEquals(assertionLogMessages.size(), 1);
            apiAssertionMessageFitsTo(
                    "get Token 0",
                    assertionLogMessages.get(0),
                    AssertionLogMessageType.ASSERT_EQUALS,
                    200,
                    200,
                    LogMessage.Status.PASSED
            );
            Assert.assertEquals(token.getString("username"), user.getUsername(), "Something went wrong during the login process. Please check your credentials.");
        }
        if (iterationGetTokenAndLogin == 1){
            Assert.assertEquals(assertionLogMessages.size(), 1);
            apiAssertionMessageFitsTo(
                    "get Token 1",
                    assertionLogMessages.get(0),
                    AssertionLogMessageType.ASSERT_EQUALS,
                    400,
                    200,
                    LogMessage.Status.FAILED
            );
            changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
            Assert.assertNull(token.getString("username"), "Something went wrong during the login process. Please check your credentials.");
        }

        var response = apiTest(
                this,
                List.of(
                        bearer(token.getString("token")),
                        baseUri(loginUrl),
                        basePath("/me")
                ),
                getRequest(),
                List.of(statusCodeIs(200))
        );
        latestApiLogMessage = getLatestApiLogMessageFromLogMessages(getCurrentLogCollectionFrom(this));
        assertionLogMessages = getAssertionMessagesFormApiLogMessage(latestApiLogMessage);

        // check getRequest API calls
        if (iterationGetTokenAndLogin == 0){
            Assert.assertEquals(assertionLogMessages.size(), 1);
            apiAssertionMessageFitsTo(
                    "getRequest 0",
                    assertionLogMessages.get(0),
                    AssertionLogMessageType.ASSERT_EQUALS,
                    200,
                    200,
                    LogMessage.Status.PASSED
            );
        }
        if (iterationGetTokenAndLogin == 1){
            Assert.assertEquals(assertionLogMessages.size(), 1);
            apiAssertionMessageFitsTo(
                    "getRequest 1",
                    assertionLogMessages.get(0),
                    AssertionLogMessageType.ASSERT_EQUALS,
                    401,
                    200,
                    LogMessage.Status.FAILED
            );
            changeApiLogMessageStatusFromFailedToPassed(latestApiLogMessage);
        }
        iterationGetTokenAndLogin++;
    }


    public JsonPath getToken(User user) {
        String url = "https://dummyjson.com/auth";

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json; charset=utf-8");

        JSONObject body = new JSONObject();
        body.put("username", user.getUsername());
        body.put("password", user.getPassword());

        var response = apiTest(
                this,
                List.of(
                        headers(headers),
                        baseUri(url),
                        basePath("/login"),
                        json(body)),
                postRequest(),
                List.of(statusCodeIs(200))
        );
        return response.getRes().body().jsonPath();
    }
}
