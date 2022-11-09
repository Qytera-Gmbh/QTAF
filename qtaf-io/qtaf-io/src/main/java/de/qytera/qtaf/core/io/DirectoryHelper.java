package de.qytera.qtaf.core.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Class containing helper methods for interacting with the file system
 */
public class DirectoryHelper {
    public static final String fs = System.getProperty("file.separator");

    /**
     * Replace variables and insert default file separator
     * @param path  Path
     * @return      Prepared path
     */
    public static String preparePath(String path) {
        return path
                .replace("$USER_DIR", System.getProperty("user.dir"))
                .replace("$USER_HOME", System.getProperty("user.home"))
                .replace("$USER_NAME", System.getProperty("user.name"))
                .replace("/", fs)
                .replace("\\", fs);
    }

    /**
     * Create a directory
     * @param dir      File path relative to project's root directory
     * @return  true on success, false otherwise
     * @throws IOException  error during file creation
     */
    public static boolean createDirectoryIfNotExists(String dir) throws IOException {
        dir = preparePath(dir);

        File file = new File(dir);

        return file.mkdirs();
    }

    /**
     * Delete a directory and all its sub directories
     * @param dir  directory
     * @return  true on success, false otherwise
     */
    public static boolean deleteDirectory(String dir) {
        return deleteDirectory(new File(preparePath(dir)));
    }

    /**
     * Delete a directory and all its sub directories
     * @param dir  directory
     * @return  true on success, false otherwise
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
