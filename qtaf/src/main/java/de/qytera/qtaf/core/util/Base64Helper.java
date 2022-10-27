package de.qytera.qtaf.core.util;

import de.qytera.qtaf.core.io.FileHelper;

import java.io.IOException;
import java.util.Base64;

/**
 * Class that encodes and decodes base64 Strings
 */
public class Base64Helper {
    /**
     * Base64 Encoder
     */
    private static final Base64.Encoder encoder = Base64.getEncoder();

    /**
     * Base64 Decoder
     */
    private static final Base64.Decoder decoder = Base64.getDecoder();

    /**
     * Convert a string to its base64 representation
     * @param input String input
     * @return  base64 string
     */
    public static String encode(String input) {
        return encoder.encodeToString(input.getBytes());
    }

    /**
     * Convert a file content to its base64 representation
     * @param filepath Path to a file
     * @return  base64 string
     */
    public static String encodeFileContent(String filepath) throws IOException {
        byte[] input = FileHelper.getFileContent(filepath);
        return encoder.encodeToString(input);
    }

    /**
     * Convert a base64 to its original representation
     * @param input Base64 String input
     * @return  original string
     */
    public static String decode(String input) {
        return new String(decoder.decode(input));
    }

    /**
     * Convert a base64 file content to its original byte array representation
     * @param filepath Path to a file
     * @return  original string
     */
    public static byte[] decodeFileContent(String filepath) throws IOException {
        byte[] input = FileHelper.getFileContent(filepath);
        return decoder.decode(input);
    }

    /**
     * Convert a base64 file content to its original string representation
     * @param filepath Path to a file
     * @return  original string
     */
    public static String decodeFileContentAsString(String filepath) throws IOException {
        return new String(Base64Helper.decodeFileContent(filepath));
    }
}
