package de.qytera.testrail.utils;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Data
public class APIClient {
    private String user;
    private String password;
    private String url;

    public APIClient(String baseUrl) {
        if (baseUrl == null) {
            throw new IllegalArgumentException("Testrail Base URL is null, please set the value in your configuration file");
        }
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        this.url = baseUrl + "index.php?/api/v2/";
    }

    public String getAuthorizationHeader() {
        return "Basic %s".formatted(new String(Base64.getEncoder().encode((user + ":" + password).getBytes(StandardCharsets.UTF_8))));
    }
}

