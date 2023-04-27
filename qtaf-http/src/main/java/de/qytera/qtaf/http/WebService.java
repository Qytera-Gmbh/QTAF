package de.qytera.qtaf.http;

import com.google.gson.JsonElement;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.http.events.HTTPEvents;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.gson.internal.JsonGsonProvider;

import java.net.URI;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebService {

    /**
     * Jersey HTTP Client.
     */
    private static final Client CLIENT = ClientBuilder.newClient().register(JsonGsonProvider.class);

    /**
     * Maximum number of retries in case an HTTP request fails because of networking issues.
     */
    private static final int MAX_RETRIES = 3;

    private static Response wrapInRetry(Invocation request, URI path) {
        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                return request.invoke();
            } catch (ProcessingException exception) {
                if (i < MAX_RETRIES - 1) {
                    QtafFactory.getLogger().warn(
                            String.format(
                                    "[QTAF HTTP] Request %s failed, retrying (%d/%d)... (reason: %s)",
                                    path,
                                    i,
                                    MAX_RETRIES,
                                    exception
                            )
                    );
                }
            }
        }
        QtafFactory.getLogger().error(String.format("[QTAF HTTP] Request %s failed.", request));
        return null;
    }

    public static RequestBuilder buildRequest(URI uri) {
        WebTarget target = CLIENT.target(uri);
        HTTPEvents.webResourceAvailable.onNext(target);
        return new RequestBuilder(uri, target.request());
    }

    public static Response post(RequestBuilder requestBuilder, JsonElement body) {
        return wrapInRetry(
                requestBuilder.getBuilder().buildPost(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE)),
                requestBuilder.getPath()
        );
    }

    public static Response get(RequestBuilder requestBuilder) {
        return wrapInRetry(requestBuilder.getBuilder().buildGet(), requestBuilder.getPath());
    }

}
