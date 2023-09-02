package de.qytera.qtaf.testrail.utils;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * The client class for communicating with TestRail's API.
 */
@Data
public class APIClient {
    private String user;
    private String password;
    private String url;

    /**
     * Create a new TestRail client.
     *
     * @param baseUrl the TestRail URL
     */
    public APIClient(String baseUrl) {
        if (baseUrl == null) {
            throw new IllegalArgumentException("TestRail Base URL is null, please set the value in your configuration file");
        }
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        this.url = baseUrl + "index.php?/api/v2/";
    }

    /**
     * Get the authorization header value.
     *
     * @return the header value
     */
    public String getAuthorizationHeader() {
        return "Basic %s".formatted(new String(Base64.getEncoder().encode((user + ":" + password).getBytes(StandardCharsets.UTF_8))));
    }
}

