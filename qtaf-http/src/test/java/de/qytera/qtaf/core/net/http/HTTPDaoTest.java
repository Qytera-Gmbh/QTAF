package de.qytera.qtaf.core.net.http;

import com.sun.jersey.api.client.ClientResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;

/**
 * HTTP Dao Test
 */
public class HTTPDaoTest {
    /**
     * Host we are testing against
     */
    final String HOST = "http://jsonplaceholder.typicode.com";

    @Test
    public void testHttpGetRequestWithStringMapping() {
        HTTPDao httpDao = new HTTPDao(HOST);
        String result = httpDao.getAsString("/comments/1", MediaType.APPLICATION_JSON);
        Assert.assertEquals(result, "{\n" +
                "  \"postId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"id labore ex et quam laborum\",\n" +
                "  \"email\": \"Eliseo@gardner.biz\",\n" +
                "  \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"\n" +
                "}");
    }

    @Test
    public void testHttpGetRequestWithResponseObject() {
        HTTPDao httpDao = new HTTPDao(HOST);
        ClientResponse result = httpDao.get("/comments/1", MediaType.APPLICATION_JSON);
        Assert.assertEquals(result.getStatus(), 200);
        String json = result.getEntity(String.class);
        Assert.assertEquals(json, "{\n" +
                "  \"postId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"id labore ex et quam laborum\",\n" +
                "  \"email\": \"Eliseo@gardner.biz\",\n" +
                "  \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"\n" +
                "}");
    }

    @Test
    public void testHttpPostRequestWithStringMapping() {
        String newCommentEntity = "{\n" +
                "  \"postId\": 1,\n" +
                "  \"id\": 501,\n" +
                "  \"name\": \"id labore ex et quam laborum\",\n" +
                "  \"email\": \"Eliseo@gardner.biz\",\n" +
                "  \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"\n" +
                "}";
        HTTPDao httpDao = new HTTPDao(HOST);
        String result = httpDao.postAsString("/comments", MediaType.APPLICATION_JSON, newCommentEntity);
        Assert.assertEquals(result, newCommentEntity);
    }

    @Test
    public void testHttpPostRequestWithResponseObject() {
        HTTPDao httpDao = new HTTPDao(HOST);
        String newCommentEntity = "{\n" +
                "  \"postId\": 1,\n" +
                "  \"id\": 501,\n" +
                "  \"name\": \"id labore ex et quam laborum\",\n" +
                "  \"email\": \"Eliseo@gardner.biz\",\n" +
                "  \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"\n" +
                "}";
        ClientResponse result = httpDao.post("/comments", MediaType.APPLICATION_JSON, newCommentEntity);
        Assert.assertEquals(result.getStatus(), 201);
        String json = result.getEntity(String.class);
        Assert.assertEquals(json, newCommentEntity);
    }

    @Test
    public void testHttpPutRequestWithStringMapping() {
        String newCommentEntity = "{\n" +
                "  \"postId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"id labore ex et quam laborum\",\n" +
                "  \"email\": \"john.doe@inet.com\",\n" +
                "  \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"\n" +
                "}";
        HTTPDao httpDao = new HTTPDao(HOST);
        String result = httpDao.putAsString("/comments/1", MediaType.APPLICATION_JSON, newCommentEntity);
        Assert.assertEquals(result, newCommentEntity);
    }

    @Test
    public void testHttpPutRequestWithResponseObject() {
        HTTPDao httpDao = new HTTPDao(HOST);
        String updatedCommentEntity = "{\n" +
                "  \"postId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"id labore ex et quam laborum\",\n" +
                "  \"email\": \"Eliseo@gardner.biz\",\n" +
                "  \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"\n" +
                "}";
        ClientResponse result = httpDao.put("/comments/1", MediaType.APPLICATION_JSON, updatedCommentEntity);
        Assert.assertEquals(result.getStatus(), 200);
        String json = result.getEntity(String.class);
        Assert.assertEquals(json, updatedCommentEntity);
    }

    @Test
    public void testHttpDeleteRequestWithStringMapping() {
        HTTPDao httpDao = new HTTPDao(HOST);
        String result = httpDao.deleteAsString("/comments/1", MediaType.APPLICATION_JSON);
        Assert.assertEquals(result, "{}");
    }

    @Test
    public void testHttpDeleteRequestWithResponseObject() {
        HTTPDao httpDao = new HTTPDao(HOST);
        ClientResponse result = httpDao.delete("/comments/1", MediaType.APPLICATION_JSON);
        Assert.assertEquals(result.getStatus(), 200);
        String json = result.getEntity(String.class);
        Assert.assertEquals(json, "{}");
    }
}
