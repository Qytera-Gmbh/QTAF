package de.qytera.qtaf.apitesting.restassured;

import io.restassured.authentication.AuthenticationScheme;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.internal.log.LogRepository;
import io.restassured.specification.ProxySpecification;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class QtafRequestSpecification extends RequestSpecificationImpl {
    public QtafRequestSpecification(String baseURI, int requestPort, String basePath, AuthenticationScheme defaultAuthScheme, List<Filter> filters, RequestSpecification defaultSpec, boolean urlEncode, RestAssuredConfig restAssuredConfig, LogRepository logRepository, ProxySpecification proxySpecification, boolean allowContentType, boolean addCsrfFilter) {
        super(baseURI, requestPort, basePath, defaultAuthScheme, filters, defaultSpec, urlEncode, restAssuredConfig, logRepository, proxySpecification, allowContentType, addCsrfFilter);
    }
}
