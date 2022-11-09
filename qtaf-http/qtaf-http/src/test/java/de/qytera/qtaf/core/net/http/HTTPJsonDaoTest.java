package de.qytera.qtaf.core.net.http;

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.stream.events.Comment;

public class HTTPJsonDaoTest {
    /**
     * Host we are testing against
     */
    final String HOST = "https://jsonplaceholder.typicode.com";

    @Test
    public void testHttpJsonGetRequestWithEntityMapping() {
        HTTPJsonDao httpDao = new HTTPJsonDao(HOST);
        CommentEntity commentEntity = httpDao.get("/comments/1", CommentEntity.class);

        Assert.assertEquals(commentEntity.getPostId(), 1);
        Assert.assertEquals(commentEntity.getId(), 1);
        Assert.assertEquals(commentEntity.getName(), "id labore ex et quam laborum");
        Assert.assertEquals(commentEntity.getEmail(), "Eliseo@gardner.biz");
        Assert.assertEquals(commentEntity.getBody(), "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium");
    }

    @Test
    public void testHttpJsonPostRequestWithEntityMapping() {
        HTTPJsonDao httpDao = new HTTPJsonDao(HOST);
        CommentEntity newCommentEntity = new CommentEntity(
                1,
                501,
                "My name",
                "john.doe@inet.com",
                "Lorem ipsum"
        );

        CommentEntity result = httpDao.post("/comments", CommentEntity.class, newCommentEntity);

        Assert.assertEquals(result.getPostId(), 1);
        Assert.assertEquals(result.getId(), 501);
        Assert.assertEquals(result.getName(), "My name");
        Assert.assertEquals(result.getEmail(), "john.doe@inet.com");
        Assert.assertEquals(result.getBody(), "Lorem ipsum");
    }

    @Test
    public void testHttpJsonPutRequestWithEntityMapping() {
        HTTPJsonDao httpDao = new HTTPJsonDao(HOST);
        CommentEntity updatedCommentEntity = new CommentEntity(
                3,
                2,
                "Jane's name",
                "jane.doe@inet.com",
                "Lorem ipsum dolor"
        );

        CommentEntity result = httpDao.put("/comments/2", CommentEntity.class, updatedCommentEntity);

        Assert.assertEquals(result.getPostId(), 3);
        Assert.assertEquals(result.getId(), 2);
        Assert.assertEquals(result.getName(), "Jane's name");
        Assert.assertEquals(result.getEmail(), "jane.doe@inet.com");
        Assert.assertEquals(result.getBody(), "Lorem ipsum dolor");
    }

    @Test
    public void testHttpJsonDeleteRequestWithEntityMapping() {
        HTTPJsonDao httpDao = new HTTPJsonDao(HOST);
        CommentEntity commentEntity = httpDao.delete("/comments/5", CommentEntity.class);

        Assert.assertEquals(commentEntity.getPostId(), 0);
        Assert.assertEquals(commentEntity.getId(), 0);
        Assert.assertNull(commentEntity.getName());
        Assert.assertNull(commentEntity.getEmail());
        Assert.assertNull(commentEntity.getBody());
    }

}
