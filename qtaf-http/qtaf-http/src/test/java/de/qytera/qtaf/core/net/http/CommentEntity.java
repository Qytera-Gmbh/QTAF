package de.qytera.qtaf.core.net.http;

public class CommentEntity {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    public CommentEntity(int postId, int id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    /**
     * Get postId
     *
     * @return postId
     */
    public int getPostId() {
        return postId;
    }

    /**
     * Set postId
     *
     * @param postId PostId
     * @return this
     */
    public CommentEntity setPostId(int postId) {
        this.postId = postId;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id Id
     * @return this
     */
    public CommentEntity setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name Name
     * @return this
     */
    public CommentEntity setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     *
     * @param email Email
     * @return this
     */
    public CommentEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get body
     *
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * Set body
     *
     * @param body Body
     * @return this
     */
    public CommentEntity setBody(String body) {
        this.body = body;
        return this;
    }
}
