package de.qytera.qtaf.core.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class containing helper methods for handling files
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileHelper {
    /**
     * Check if a file exists
     *
     * @param filePath File location
     * @return true if exists, false otherwise
     */
    public static boolean exists(String filePath) {
        filePath = DirectoryHelper.preparePath(filePath);
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Create an empty file and all its parent directories
     *
     * @param filePath File location
     * @return true on success, false otherwise
     */
    public static boolean touch(String filePath) {
        filePath = DirectoryHelper.preparePath(filePath);
        File file = new File(filePath);

        if (!file.getParentFile().exists()) {
            return file.getParentFile().mkdirs();
        }

        return true;
    }

    /**
     * Create a file
     *
     * @param filePath    File path relative to project's root directory
     * @param fileContent File content
     * @return true on success, false otherwise
     * @throws IOException error during file creation
     */
    public static boolean createFileIfNotExists(String filePath, String fileContent) throws IOException {
        filePath = DirectoryHelper.preparePath(filePath);

        File file = new File(filePath);

        boolean mkdirsSuccess = touch(filePath);

        if (mkdirsSuccess && !file.exists()) {
            boolean fileCreated = file.createNewFile();

            // Write file contents
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fileContent);
            bw.close();

            return fileCreated;
        }

        return true;
    }

    /**
     * Get file content
     *
     * @param filepath path to file
     * @return file content
     * @throws IOException File not found
     */
    public static byte[] getFileContent(String filepath) throws IOException {
        return Files.readAllBytes(Paths.get(DirectoryHelper.preparePath(filepath)));
    }

    /**
     * Get file content as String
     *
     * @param filepath path to file
     * @return file content
     * @throws IOException File not found
     */
    public static String getFileContentAsUTF8String(String filepath) throws IOException {
        return new String(FileHelper.getFileContent(filepath), StandardCharsets.UTF_8);
    }

    /**
     * Write file
     *
     * @param filePath File location
     * @param content  File content
     * @throws IOException file not writable
     */
    public static void writeFile(String filePath, String content) throws IOException {
        filePath = DirectoryHelper.preparePath(filePath);
        Files.write(Path.of(filePath), content.getBytes());
    }

    /**
     * Delete a file
     *
     * @param filePath File location
     * @return true on success, false otherwise
     */
    public static boolean delete(String filePath) {
        filePath = DirectoryHelper.preparePath(filePath);
        File file = new File(filePath);
        return file.delete();
    }
}
