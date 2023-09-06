package de.qytera.qtaf.core.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * Class containing helper methods for interacting with the file system.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DirectoryHelper {
    /**
     * File separator shorthand.
     */
    public static final String SEP = System.getProperty("file.separator");

    /**
     * Replace variables and insert default file separator.
     *
     * @param path Path
     * @return Prepared path
     */
    public static String preparePath(String path) {
        return path
                .replace("$USER_DIR", System.getProperty("user.dir"))
                .replace("$USER_HOME", System.getProperty("user.home"))
                .replace("$USER_NAME", System.getProperty("user.name"))
                .replace("/", SEP)
                .replace("\\", SEP);
    }

    /**
     * Create a directory.
     *
     * @param dir File path relative to project's root directory
     * @return true on success, false otherwise
     */
    public static boolean createDirectoryIfNotExists(String dir) {
        dir = preparePath(dir);

        File file = new File(dir);

        return file.mkdirs();
    }

    /**
     * Delete a directory and all its subdirectories.
     *
     * @param dir directory
     * @return true on success, false otherwise
     */
    public static boolean deleteDirectory(String dir) {
        return deleteDirectory(new File(preparePath(dir)));
    }

    /**
     * Delete a directory and all its subdirectories.
     *
     * @param dir directory
     * @return true on success, false otherwise
     */
    public static boolean deleteDirectory(File dir) {
        File[] allContents = dir.listFiles();

        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }

        return dir.delete();
    }
}
