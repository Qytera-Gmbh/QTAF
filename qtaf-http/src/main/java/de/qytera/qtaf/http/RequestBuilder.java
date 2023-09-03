package de.qytera.qtaf.http;

import jakarta.ws.rs.client.Invocation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.net.URI;

/**
 * A wrapper around {@link Invocation.Builder} used for building HTTP requests. The wrap is needed so that the request
 * path (e.g. <a href="https://example.org/a/b/c?d=f">https://example.org/a/b/c?d=f</a>) remains exposed, which is very
 * useful for error messages.
 *
 * <p>Instances of {@link RequestBuilder} can be obtained using {@link WebService#buildRequest(URI)}.</p>
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RequestBuilder {

    /**
     * The HTTP request's path (e.g. <a href="https://example.org/a/b">https://example.org/a/b</a>.
     */
    private final URI path;
    /**
     * The HTTP builder object, which can be used to specify request headers or the request's content type.
     */
    private final Invocation.Builder builder;

}
