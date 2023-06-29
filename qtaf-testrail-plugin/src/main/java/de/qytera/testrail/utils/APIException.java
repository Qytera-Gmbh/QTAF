package de.qytera.testrail.utils;

public class APIException extends Exception {
    public APIException(int status, String message) {
        super("TestRail API returned HTTP %d (%s)".formatted(status, message));
    }
}