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

/**
 * Utility class for dispatching HTTP requests.
 * <p>
 * The typical workflow for dispatching requests looks as follows:
 * <pre>
 *     {@code
 *  RequestBuilder request = WebService.buildRequest(new URI("https://some-url.you/need"));
 *  request.getBuilder()
 *      .accept(MediaType.APPLICATION_JSON_TYPE)
 *      .header(HttpHeaders.AUTHORIZATION, "my-secret");
 *  Response response = WebService.get(request);
 *  }
 *  </pre>
 */
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
            if (i == 0) {
                QtafFactory.getLogger().info(
                        String.format(
                                "[QTAF HTTP] Sending request to %s...",
                                path
                        )
                );
            }
            try {
                return request.invoke();
            } catch (ProcessingException exception) {
                if (i < MAX_RETRIES - 1) {
                    QtafFactory.getLogger().warn(
                            String.format(
                                    "[QTAF HTTP] Request %s failed, sending attempt %d/%d... (reason: %s)",
                                    path,
                                    i + 2,
                                    MAX_RETRIES,
                                    exception
                            )
                    );
                } else {
                    QtafFactory.getLogger().error(String.format("[QTAF HTTP] Request %s failed.", request));
                }
            }
        }
        return null;
    }

    /**
     * Starting point for building HTTP requests.
     *
     * @param uri the {@link URI} to which the request will be sent
     * @return a {@link RequestBuilder} to further modify the HTTP request
     * @see WebService#get(RequestBuilder)
     * @see WebService#post(RequestBuilder, JsonElement)
     * @see WebService#put(RequestBuilder, JsonElement)
     * @see WebService#delete(RequestBuilder)
     */
    public static RequestBuilder buildRequest(URI uri) {
        WebTarget target = CLIENT.target(uri);
        HTTPEvents.webResourceAvailable.onNext(target);
        return new RequestBuilder(uri, target.request());
    }

    /**
     * Method for dispatching HTTP GET requests.
     *
     * @param request the prepared HTTP request
     * @return the HTTP response
     */
    public static Response get(RequestBuilder request) {
        return wrapInRetry(
                request.getBuilder().buildGet(),
                request.getPath()
        );
    }

    /**
     * Method for dispatching HTTP POST requests with JSON bodies. The body can be obtained using Gson's
     * {@link com.google.gson.Gson#toJsonTree(Object)}:
     * <pre>
     * {@code Response response = WebService.post(request, toJsonTree(data));}
     * </pre>
     *
     * @param request the prepared HTTP request
     * @param body    the request's JSON body
     * @return the HTTP response
     */
    public static Response post(RequestBuilder request, JsonElement body) {
        return wrapInRetry(
                request.getBuilder().buildPost(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE)),
                request.getPath()
        );
    }

    /**
     * Method for dispatching HTTP PUT requests with JSON bodies. The body can be obtained using Gson's
     * {@link com.google.gson.Gson#toJsonTree(Object)}:
     * <pre>
     * {@code Response response = WebService.put(request, toJsonTree(data));}
     * </pre>
     *
     * @param request the prepared HTTP request
     * @param body    the request's JSON body
     * @return the HTTP response
     */
    public static Response put(RequestBuilder request, JsonElement body) {
        return wrapInRetry(
                request.getBuilder().buildPut(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE)),
                request.getPath()
        );
    }

    /**
     * Method for dispatching HTTP DELETE requests.
     *
     * @param request the prepared HTTP request
     * @return the HTTP response
     */
    public static Response delete(RequestBuilder request) {
        return wrapInRetry(
                request.getBuilder().buildDelete(),
                request.getPath()
        );
    }

}
