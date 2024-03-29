package de.qytera.qtaf.core.gson;

import com.google.gson.Gson;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.io.FileHelper;
import de.qytera.qtaf.core.log.model.error.ErrorLog;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * Helper class for creating GSON objects.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GsonHelper {
    /**
     * Gson object.
     */
    private static final Gson gson = GsonFactory.getInstance();

    /**
     * From JSON to entity.
     *
     * @param json   JSON string
     * @param tClass Entity class
     * @param <T>    Entity Type
     * @return Entity object
     */
    public static <T> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    /**
     * Save an entity to a JSON file.
     *
     * @param entity   the entity
     * @param filePath the path to save the JSON file to
     * @param <T>      the entity type
     */
    public static <T> void saveJsonFile(T entity, String filePath) {
        Gson gson = GsonFactory.getInstance();
        String json = gson.toJson(entity);

        try {
            FileHelper.createFileIfNotExists(
                    DirectoryHelper.preparePath(filePath),
                    json
            );
        } catch (IOException e) {
            ErrorLogCollection errors = ErrorLogCollection.getInstance();
            ErrorLog error = new ErrorLog(e);
            errors.addErrorLog(error);
        }
    }
}
