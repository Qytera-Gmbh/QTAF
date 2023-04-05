package de.qytera.qtaf.core.net.http;


/**
 * Class that is responsible for loading entities from an HTTP server that returns JSON Responses (i.e. a REST API)
 *
 * @param <T> Entity Type
 */
abstract public class HTTPJsonEntityRepository<T> {
    /**
     * HTTP JSON DAO Object
     */
    protected HTTPJsonDao dao;

    /**
     * URL Path
     */
    protected String path;

    /**
     * Entity class
     */
    protected Class<T> entityClass;

    /**
     * Constructor
     *
     * @param dao DAO
     */
    public HTTPJsonEntityRepository(HTTPJsonDao dao, String path, Class<T> entityClass) {
        this.dao = dao;
        this.path = path;
        this.entityClass = entityClass;
    }

    /**
     * Build path where to look for single entities
     *
     * @param id Entity id
     */
    public String buildItemPath(int id) {
        return path + "/" + id;
    }

    /**
     * Build path where to look for single entities
     *
     * @param id Entity id
     */
    public String buildItemPath(String id) {
        return path + "/" + id;
    }

    /**
     * Build path where to look for single entities
     *
     * @param entity Entity object
     */
    public abstract String buildItemPath(T entity);

    /**
     * Find single entity by ID
     *
     * @param id Entity ID
     * @return Entity object
     */
    public T findOne(int id) {
        return dao.get(buildItemPath(id), entityClass);
    }

    /**
     * Create single entity
     *
     * @param entity Entity object
     * @return Entity object
     */
    public T createOne(T entity) {
        return dao.post(path, entityClass, entity);
    }

    /**
     * Update single entity
     *
     * @param entity Entity object
     * @return Entity object
     */
    public T updateOne(T entity) {
        return dao.put(buildItemPath(entity), entityClass, entity);
    }

    /**
     * Find single entity by ID
     *
     * @param entity Entity object
     * @return Entity object
     */
    public T deleteOne(T entity) {
        return dao.delete(buildItemPath(entity), entityClass);
    }

}
