package de.qytera.qtaf.http;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        Assert.assertEquals(result, """
                {
                  "postId": 1,
                  "id": 1,
                  "name": "id labore ex et quam laborum",
                  "email": "Eliseo@gardner.biz",
                  "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"
                }""");
    }

    @Test
    public void testHttpGetRequestWithResponseObject() {
        HTTPDao httpDao = new HTTPDao(HOST);
        Response result = httpDao.get("/comments/1", MediaType.APPLICATION_JSON);
        Assert.assertEquals(result.getStatus(), 200);
        String json = result.readEntity(String.class);
        Assert.assertEquals(json, """
                {
                  "postId": 1,
                  "id": 1,
                  "name": "id labore ex et quam laborum",
                  "email": "Eliseo@gardner.biz",
                  "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"
                }""");
    }

    @Test
    public void testHttpPostRequestWithStringMapping() {
        String newCommentEntity = """
                {
                  "postId": 1,
                  "id": 501,
                  "name": "id labore ex et quam laborum",
                  "email": "Eliseo@gardner.biz",
                  "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"
                }""";
        HTTPDao httpDao = new HTTPDao(HOST);
        String result = httpDao.postAsString("/comments", MediaType.APPLICATION_JSON, newCommentEntity);
        Assert.assertEquals(result, newCommentEntity);
    }

    @Test
    public void testHttpPostRequestWithResponseObject() {
        HTTPDao httpDao = new HTTPDao(HOST);
        String newCommentEntity = """
                {
                  "postId": 1,
                  "id": 501,
                  "name": "id labore ex et quam laborum",
                  "email": "Eliseo@gardner.biz",
                  "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"
                }""";
        try (Response result = httpDao.post("/comments", MediaType.APPLICATION_JSON, newCommentEntity)) {
            Assert.assertEquals(result.getStatus(), 201);
            String json = result.readEntity(String.class);
            Assert.assertEquals(json, newCommentEntity);
        }
    }

    @Test
    public void testHttpPutRequestWithStringMapping() {
        String newCommentEntity = """
                {
                  "postId": 1,
                  "id": 1,
                  "name": "id labore ex et quam laborum",
                  "email": "john.doe@inet.com",
                  "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"
                }""";
        HTTPDao httpDao = new HTTPDao(HOST);
        String result = httpDao.putAsString("/comments/1", MediaType.APPLICATION_JSON, newCommentEntity);
        Assert.assertEquals(result, newCommentEntity);
    }

    @Test
    public void testHttpPutRequestWithResponseObject() {
        HTTPDao httpDao = new HTTPDao(HOST);
        String updatedCommentEntity = """
                {
                  "postId": 1,
                  "id": 1,
                  "name": "id labore ex et quam laborum",
                  "email": "Eliseo@gardner.biz",
                  "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"
                }""";
        String json;
        try (Response result = httpDao.put("/comments/1", MediaType.APPLICATION_JSON, updatedCommentEntity)) {
            Assert.assertEquals(result.getStatus(), 200);
            json = result.readEntity(String.class);
            Assert.assertEquals(json, updatedCommentEntity);
        }
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
        String json;
        try (Response result = httpDao.delete("/comments/1", MediaType.APPLICATION_JSON)) {
            Assert.assertEquals(result.getStatus(), 200);
            json = result.readEntity(String.class);
            Assert.assertEquals(json, "{}");
        }
    }
}
