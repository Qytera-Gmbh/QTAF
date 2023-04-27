package de.qytera.qtaf.http;

import jakarta.ws.rs.client.Invocation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RequestBuilder {

    private final URI path;
    private final Invocation.Builder builder;

}
