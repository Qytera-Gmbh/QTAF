package de.qytera.qtaf.apitesting.restassured.preconditions;

import de.qytera.qtaf.apitesting.ApiTest;
import de.qytera.qtaf.apitesting.ExecutedApiTest;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static de.qytera.qtaf.apitesting.ApiTestExecutor.apiTest;

public class RequestHeaderTests extends QtafTestNGContext implements ApiTest {

    private static String url = "https://jsonplaceholder.typicode.com";

    @Test(testName = "Test headers using strings precondition")
    public void testHeadersPreconditionUsingStrings() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        header("foo-bar", "bar_foo"),
                        header("bar-foo", "foo_bar")
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIsNot1xx(),
                        statusCodeIs2xx(),
                        statusCodeIs(200),
                        statusCodeIsNot(201),
                        statusCodeIsNot3xx(),
                        statusCodeIsNot4xx(),
                        statusCodeIsNot5xx()
                )
        );

        // Check the values of the headers 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getHeaders().get("foo-bar").getValue(), "bar_foo");
        Assert.assertEquals(result.getReq().getHeaders().get("bar-foo").getValue(), "foo_bar");

        // The header 'xxx' should not exist
        Assert.assertNull(result.getReq().getHeaders().get("xxx"));
    }

    @Test(testName = "Test headers using map precondition")
    public void testHeadersPreconditionUsingMap() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        headers(Map.of("foo-bar", "bar_foo", "bar-foo", "foo_bar"))
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIsNot1xx(),
                        statusCodeIs2xx(),
                        statusCodeIs(200),
                        statusCodeIsNot(201),
                        statusCodeIsNot3xx(),
                        statusCodeIsNot4xx(),
                        statusCodeIsNot5xx()
                )
        );

        // Check the values of the headers 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getHeaders().get("foo-bar").getValue(), "bar_foo");
        Assert.assertEquals(result.getReq().getHeaders().get("bar-foo").getValue(), "foo_bar");

        // The header 'xxx' should not exist
        Assert.assertNull(result.getReq().getHeaders().get("xxx"));
    }

    @Test(testName = "Test headers using header object precondition")
    public void testHeadersPreconditionUsingHeaderObjects() {
        Header header1 = new Header("foo-bar", "bar_foo");
        Header header2 = new Header("bar-foo", "foo_bar");

        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        header(header1),
                        header(header2)
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIsNot1xx(),
                        statusCodeIs2xx(),
                        statusCodeIs(200),
                        statusCodeIsNot(201),
                        statusCodeIsNot3xx(),
                        statusCodeIsNot4xx(),
                        statusCodeIsNot5xx()
                )
        );

        // Check the values of the headers 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getHeaders().get("foo-bar").getValue(), "bar_foo");
        Assert.assertEquals(result.getReq().getHeaders().get("bar-foo").getValue(), "foo_bar");

        // The header 'xxx' should not exist
        Assert.assertNull(result.getReq().getHeaders().get("xxx"));
    }

    @Test(testName = "Test bearer")
    public void testBearer() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        bearer("abc123")
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of the headers 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getHeaders().get("Authorization").getValue(), "Bearer abc123");
    }

    @Test(testName = "Test JSON Content Type header")
    public void testContentTypeJSON() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        json()
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of the headers 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getHeaders().get("Content-Type").getValue(), "application/json");
    }

    @Test(testName = "Test Multipart Content Type header")
    public void testContentTypeMultipart() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        multipart()
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of the headers 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getHeaders().get("Content-Type").getValue(), "multipart/form-data");
    }

    @Test(testName = "Test Multipart Content Type header with String body")
    public void testContentTypeMultipartAndStringBody() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        multipart("foo-bar", "bar_foo"),
                        multipart("bar-foo", "foo_bar")
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of the multipart params 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getMultiPartParams().size(), 2);
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getControlName(), "foo-bar");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getContent(), "bar_foo");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getMimeType(), "text/plain");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getControlName(), "bar-foo");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getContent(), "foo_bar");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getMimeType(), "text/plain");
    }

    @Test(testName = "Test Multipart Content Type header with non-string body")
    public void testContentTypeMultipartAndNonStringBody() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        multipart("foo-bar", 1),
                        multipart("bar-foo", true)
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of the multipart params 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getMultiPartParams().size(), 2);
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getControlName(), "foo-bar");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getContent(), "1");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getMimeType(), "multipart/form-data");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getControlName(), "bar-foo");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getContent(), "true");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getMimeType(), "multipart/form-data");
    }

    @Test(testName = "Test Multipart Content Type header with file body")
    public void testContentTypeMultipartAndFilePrecondition() {
        File file1 = new File("src/test/resources/data1.json");
        File file2 = new File("src/test/resources/data2.json");

        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        file(file1),
                        file(file2)
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of the multipart params 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getMultiPartParams().size(), 2);
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getControlName(), "file");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getContent().getClass().getName(), "java.io.File");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getContent(), file1);
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getMimeType(), "application/octet-stream");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getFileName(), "data1.json");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getControlName(), "file");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getContent().getClass().getName(), "java.io.File");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getContent(), file2);
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getMimeType(), "application/octet-stream");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getFileName(), "data2.json");
    }

    @Test(testName = "Test Multipart Content Type header with named files")
    public void testContentTypeMultipartAndNamedFilePrecondition() {
        File file1 = new File("src/test/resources/data1.json");
        File file2 = new File("src/test/resources/data2.json");

        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        file("file1", file1),
                        file("file2", file2)
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of the multipart params 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getMultiPartParams().size(), 2);
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getControlName(), "file1");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getContent().getClass().getName(), "java.io.File");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getContent(), file1);
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getMimeType(), "application/octet-stream");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(0).getFileName(), "data1.json");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getControlName(), "file2");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getContent().getClass().getName(), "java.io.File");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getContent(), file2);
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getMimeType(), "application/octet-stream");
        Assert.assertEquals(result.getReq().getMultiPartParams().get(1).getFileName(), "data2.json");
    }

    @Test(testName = "Test Text Content Type header")
    public void testContentTypeText() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        text()
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of the headers 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getHeaders().get("Content-Type").getValue(), "text/plain; charset=ISO-8859-1");
    }

    @Test(testName = "Test XML Content Type header")
    public void testContentTypeXML() {
        ExecutedApiTest result = apiTest(
                this,
                List.of(
                        baseUri(url),
                        xml()
                ),
                getRequest("/albums/1"),
                List.of(
                        statusCodeIs(200)
                )
        );

        // Check the values of the headers 'foo-bar' and 'bar-foo'
        Assert.assertEquals(result.getReq().getHeaders().get("Content-Type").getValue(), "application/xml; charset=ISO-8859-1");
    }
}
