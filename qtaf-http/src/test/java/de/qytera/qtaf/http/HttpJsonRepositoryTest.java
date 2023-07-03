package de.qytera.qtaf.http;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HttpJsonRepositoryTest {
    @Test
    public void testRepositoryFindOne() {
        HTTPJsonDao dao = new HTTPJsonDao("https://jsonplaceholder.typicode.com");
        CommentHttpJsonRepository repository = new CommentHttpJsonRepository(dao, "/comments");

        CommentEntity commentEntity = repository.findOne(1);
        Assert.assertEquals(commentEntity.getPostId(), 1);
        Assert.assertEquals(commentEntity.getId(), 1);
        Assert.assertEquals(commentEntity.getName(), "id labore ex et quam laborum");
        Assert.assertEquals(commentEntity.getEmail(), "Eliseo@gardner.biz");
        Assert.assertEquals(commentEntity.getBody(), "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium");
    }

    @Test
    public void testRepositoryCreateOne() {
        HTTPJsonDao dao = new HTTPJsonDao("https://jsonplaceholder.typicode.com");
        CommentHttpJsonRepository repository = new CommentHttpJsonRepository(dao, "/comments");

        CommentEntity newCommentEntity = new CommentEntity(
                1,
                501,
                "My name",
                "john.doe@inet.com",
                "Lorem ipsum"
        );

        CommentEntity commentEntity = repository.createOne(newCommentEntity);
        Assert.assertEquals(commentEntity.getPostId(), 1);
        Assert.assertEquals(commentEntity.getId(), 501);
        Assert.assertEquals(commentEntity.getName(), "My name");
        Assert.assertEquals(commentEntity.getEmail(), "john.doe@inet.com");
        Assert.assertEquals(commentEntity.getBody(), "Lorem ipsum");
    }

    @Test
    public void testRepositoryUpdateOne() {
        HTTPJsonDao dao = new HTTPJsonDao("https://jsonplaceholder.typicode.com");
        CommentHttpJsonRepository repository = new CommentHttpJsonRepository(dao, "/comments");

        CommentEntity updatedCommentEntity = new CommentEntity(
                3,
                2,
                "Jane's name",
                "jane.doe@inet.com",
                "Lorem ipsum dolor"
        );

        CommentEntity commentEntity = repository.updateOne(updatedCommentEntity);
        Assert.assertEquals(commentEntity.getPostId(), 3);
        Assert.assertEquals(commentEntity.getId(), 2);
        Assert.assertEquals(commentEntity.getName(), "Jane's name");
        Assert.assertEquals(commentEntity.getEmail(), "jane.doe@inet.com");
        Assert.assertEquals(commentEntity.getBody(), "Lorem ipsum dolor");
    }

    @Test
    public void testRepositoryDeleteOne() {
        HTTPJsonDao dao = new HTTPJsonDao("https://jsonplaceholder.typicode.com");
        CommentHttpJsonRepository repository = new CommentHttpJsonRepository(dao, "/comments");

        CommentEntity commentEntity = repository.findOne(1);
        CommentEntity deletedEntity = repository.deleteOne(commentEntity);

        Assert.assertEquals(deletedEntity.getPostId(), 0);
        Assert.assertEquals(deletedEntity.getId(), 0);
        Assert.assertNull(deletedEntity.getName());
        Assert.assertNull(deletedEntity.getEmail());
        Assert.assertNull(deletedEntity.getBody());
    }
}
