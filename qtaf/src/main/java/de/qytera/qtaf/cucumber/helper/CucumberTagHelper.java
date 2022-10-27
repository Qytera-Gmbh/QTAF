package de.qytera.qtaf.cucumber.helper;

import io.cucumber.messages.types.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that provides helper methods for extracting information from cucumber tags
 */
public class CucumberTagHelper {
    private static final String keyValueRegex = "^@([A-Za-z0-9-_]+):(.*)$";
    private static final String tagRegex = "^@([A-Za-z0-9-_]+)";
    private static final String testIDRegex = "^@TestName:(.*)$";

    /**
     * Check if a given string is a key-value-pair (matches keyValueRegex)
     * @param s string
     * @return  true if string is a key-value-pair, false otherwise
     */
    public static boolean isKeyValuePair(String s) {
        return s.matches(keyValueRegex);
    }

    /**
     * Check if a given string is a tag
     * @param s string
     * @return  true if string is a tag
     */
    public static boolean isTag(String s) {
        return s.matches(tagRegex);
    }

    /**
     * Extract hey and value from a string
     * @param s string
     * @return  Matcher object
     */
    public static Matcher getKeyAndValueMatch(String s) {
        return Pattern.compile(keyValueRegex).matcher(s);
    }

    /**
     * Get a list of key-value pairs from a list of cucumber tag objects
     * @param tags  List of cucumber tag objects
     * @return  Map of key-value-pairs
     */
    public static Map<String, String> getKeyValuePairsFromTagList(List<Tag> tags) {
        Map<String, String> map = new HashMap<>();

        for (Tag tag : tags) {
            if (isKeyValuePair(tag.getName())) {
                Matcher m = getKeyAndValueMatch(tag.getName());

                if (m.find()) {
                    map.put(m.group(1), m.group(2));
                }
            } else if (isTag(tag.getName())) {
                map.put(tag.getName().replace("@", ""), "");
            }
        }

        return map;
    }

    /**
     * Get a list of key-value pairs from a list of cucumber tag strings
     * @param tags  List of cucumber tag strings
     * @return  Map of key-value-pairs
     */
    public static Map<String, String> getKeyValuePairs(List<String> tags) {
        Map<String, String> map = new HashMap<>();

        for (String tag : tags) {
            if (isKeyValuePair(tag)) {
                Matcher m = getKeyAndValueMatch(tag);

                if (m.find()) {
                    map.put(m.group(1), m.group(2));
                }
            } else if (isTag(tag)) {
                map.put(tag.replace("@", ""), "");
            }
        }

        return map;
    }

    /**
     * Get a key-value-list pairs from a list of cucumber tag strings
     * @param tags  List of cucumber tag strings
     * @return  Map of key-value-lists
     */
    public static Map<String, List<String>> getKeyValueListPairs(List<String> tags) {
        Map<String, List<String>> map = new HashMap<>();

        for (String tag : tags) {
            if (isKeyValuePair(tag)) {
                Matcher m = getKeyAndValueMatch(tag);

                if (m.find()) {
                    map.computeIfAbsent(m.group(1), k -> new ArrayList<>());
                    map.get(m.group(1)).add(m.group(2));
                }
            }
        }

        return map;
    }

    /**
     * Find the tag that represents the test ID and extracts the test ID
     * @param tags  List of cucumber tags
     * @return  test ID
     */
    public static String getTestId(List<String> tags) {
        for (String tag : tags) {
            if (tag.matches(testIDRegex)) {
                Matcher m = Pattern.compile(testIDRegex).matcher(tag);

                if (m.find()) {
                    return m.group(1);
                }
            }
        }

        return null;
    }
}
