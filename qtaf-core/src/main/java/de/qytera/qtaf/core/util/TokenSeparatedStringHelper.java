package de.qytera.qtaf.core.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper for token separated strings
 */
public class TokenSeparatedStringHelper {
    private TokenSeparatedStringHelper() {}
    /**
     * Transform a token separated string of values to a list of values
     *
     * @param s         Input string
     * @param separator Separator token
     * @return List of strings
     */
    public static List<String> toList(String s, String separator) {
        return toList(s, separator, true);
    }

    /**
     * Transform a token separated string of values to a list of values
     *
     * @param s          Input string
     * @param separator  Separator token
     * @param trimValues true if list values should be trimmed
     * @return List of strings
     */
    public static List<String> toList(String s, String separator, boolean trimValues) {
        List<String> list = Arrays.asList(s.split(separator));

        if (trimValues) {
            list = list.stream().map(String::trim).collect(Collectors.toList());
        }

        return list;
    }
}
