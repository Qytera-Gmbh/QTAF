package de.qytera.qtaf.apitesting.restassured.preconditions.advanced;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.ExecutedApiTest;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

public class RequestCookiesTest extends QtafTestNGContext implements ApiTest {
    private static String url = "https://jsonplaceholder.typicode.com";

    @Test(testName = "Test cookies using strings precondition")
    public void testCookiesUsingStringsPrecondition() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/albums/1"),
                        cookie("bar-foo", "foo_bar"),
                        cookie("foo-bar", "bar_foo")
                ),
                getRequest(),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of cookies 'bar-foo' and 'foo-bar'
        Assert.assertEquals(result.getReq().getCookies().get("bar-foo").getValue(), "foo_bar");
        Assert.assertEquals(result.getReq().getCookies().get("foo-bar").getValue(), "bar_foo");

        // The cookie 'xxx' should not exist
        Assert.assertNull(result.getReq().getCookies().get("xxx"));
    }

    @Test(testName = "Test cookies using map precondition")
    public void testCookiesUsingMapPrecondition() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/albums/1"),
                        cookie(Map.of("bar-foo", "foo_bar", "foo-bar", "bar_foo"))
                ),
                getRequest(),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of cookies 'bar-foo' and 'foo-bar'
        Assert.assertEquals(result.getReq().getCookies().get("bar-foo").getValue(), "foo_bar");
        Assert.assertEquals(result.getReq().getCookies().get("foo-bar").getValue(), "bar_foo");

        // The cookie 'xxx' should not exist
        Assert.assertNull(result.getReq().getCookies().get("xxx"));
    }

    @Test(testName = "Test cookies using cookie objects precondition")
    public void testCookiesUsingCookieObjectsPrecondition() {
        Cookie cookie1 = (new Cookie.Builder("bar-foo", "foo_bar")).build();
        Cookie cookie2 = (new Cookie.Builder("foo-bar", "bar_foo")).build();

        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/albums/1"),
                        cookie(cookie1),
                        cookie(cookie2)
                ),
                getRequest(),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of cookies 'bar-foo' and 'foo-bar'
        Assert.assertEquals(result.getReq().getCookies().get("bar-foo").getValue(), "foo_bar");
        Assert.assertEquals(result.getReq().getCookies().get("foo-bar").getValue(), "bar_foo");

        // The cookie 'xxx' should not exist
        Assert.assertNull(result.getReq().getCookies().get("xxx"));
    }

    @Test(testName = "Test cookies using cookies object precondition")
    public void testCookiesUsingCookiesObjectPrecondition() {
        Cookie cookie1 = (new Cookie.Builder("bar-foo", "foo_bar")).build();
        Cookie cookie2 = (new Cookie.Builder("foo-bar", "bar_foo")).build();
        Cookies cookies = Cookies.cookies(cookie1, cookie2);

        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        basePath("/albums/1"),
                        cookies(cookies)
                ),
                getRequest(),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of cookies 'bar-foo' and 'foo-bar'
        Assert.assertEquals(result.getReq().getCookies().get("bar-foo").getValue(), "foo_bar");
        Assert.assertEquals(result.getReq().getCookies().get("foo-bar").getValue(), "bar_foo");

        // The cookie 'xxx' should not exist
        Assert.assertNull(result.getReq().getCookies().get("xxx"));
    }
}
