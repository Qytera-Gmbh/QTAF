package de.qytera.qtaf.testrail.utils;

/**
 * Exceptions which occur during interaction with TestRail's API.
 */
public class APIException extends Exception {
    /**
     * Create a new {@link APIException} with the given status and message.
     *
     * @param status  the status
     * @param message the message
     */
    public APIException(int status, String message) {
        super("TestRail API returned HTTP %d (%s)".formatted(status, message));
    }

    /**
     * Create a new {@link APIException} with the given status and message.
     *
     * @param message the message
     */
    public APIException(String message) {
        super(message);
    }
}