package de.qytera.qtaf.xray.entity;

/**
 * Representation of xray auth credentials JSON request.
 * The attributes need to have the same names as the corresponding JSON attributes.
 */
public class XrayAuthCredentials {
    /**
     * Client ID
     */
    private String client_id;

    /**
     * Client secret
     */
    private String client_secret;

    public XrayAuthCredentials(String client_id, String client_secret) {
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public String getClient_id() {
        return client_id;
    }

    public XrayAuthCredentials setClient_id(String client_id) {
        this.client_id = client_id;
        return this;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public XrayAuthCredentials setClient_secret(String client_secret) {
        this.client_secret = client_secret;
        return this;
    }
}
