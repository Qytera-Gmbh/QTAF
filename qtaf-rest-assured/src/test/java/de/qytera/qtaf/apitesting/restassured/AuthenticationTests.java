package de.qytera.qtaf.apitesting.restassured;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.restassured.Entities.User;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

    @Test(testName = "Authentification test using User Object", dataProvider = USER_PROVIDER)
    public void checkUserName(User user) {
        var token = getToken(user);
        assertEquals(user.getUsername(), token.getString("username"), "Something went wrong during the login process. Please check your credentials.", false);
    }

    @Test(testName = "Login test using username and password", dataProvider = USER_PROVIDER)
    public void getTokenAndLoginUsingUsernameAndPassword(User user) {
        String loginUrl = "https://dummyjson.com/auth";
        var token = getToken(user);

        var response = apiTest(
                this,
                List.of(bearer(token.getString("token")), baseUri(loginUrl)),
                getRequest("/me"),
                List.of(statusCodeIs(200))
        );
        System.out.println(response.getRes().body().jsonPath().getString("firstName"));
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
                List.of(headers(headers), baseUri(url), json(body)),
                postRequest("/login"),
                List.of(statusCodeIs(200))
        );
        return response.getRes().body().jsonPath();
    }
}
