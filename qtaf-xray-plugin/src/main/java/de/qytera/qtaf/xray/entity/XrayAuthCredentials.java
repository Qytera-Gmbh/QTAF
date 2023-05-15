package de.qytera.qtaf.xray.entity;

/**
 * Representation of xray auth credentials JSON request.
 * The attributes need to have the same names as the corresponding JSON attributes.
 */
public class XrayAuthCredentials {
    /**
     * Client ID
     */
    private String clientId;

    /**
     * Client secret
     */
    private String clientSecret;

    public XrayAuthCredentials(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public XrayAuthCredentials setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public XrayAuthCredentials setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }
}
