package de.qytera.qtaf.core.net.http;

public class CommentHttpJsonRepository extends HTTPJsonEntityRepository<CommentEntity> {
    /**
     * Constructor
     *
     * @param dao  DAO
     * @param path API resource path
     */
    public CommentHttpJsonRepository(HTTPJsonDao dao, String path) {
        super(dao, path, CommentEntity.class);
    }

    @Override
    public String buildItemPath(CommentEntity entity) {
        return path + "/" + entity.getId();
    }
}
