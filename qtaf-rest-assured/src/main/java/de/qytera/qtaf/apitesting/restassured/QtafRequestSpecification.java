package de.qytera.qtaf.apitesting.restassured;

import groovy.lang.MetaClass;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.NoAuthScheme;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.internal.log.LogRepository;
import io.restassured.response.Response;
import io.restassured.specification.ProxySpecification;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class QtafRequestSpecification extends RequestSpecificationImpl implements RequestSpecification {

    public QtafRequestSpecification(){
        super("", 0, "", new NoAuthScheme(), null, null, false, null, null, null, false, false);

    }
    public QtafRequestSpecification(RequestSpecificationImpl reqSpe){
        super(
                reqSpe.getBaseUri(),
                reqSpe.getPort(),
                reqSpe.getBasePath(),
                reqSpe.getAuthenticationScheme(),
                reqSpe.getDefinedFilters(),
                reqSpe.given(),
                true,
                reqSpe.restAssuredConfig(),
                new LogRepository(),
                null,
                true,
                true);

    }
    public QtafRequestSpecification(String baseURI, int requestPort, String basePath, AuthenticationScheme defaultAuthScheme, List<Filter> filters, RequestSpecification defaultSpec, boolean urlEncode, RestAssuredConfig restAssuredConfig, LogRepository logRepository, ProxySpecification proxySpecification, boolean allowContentType, boolean addCsrfFilter) {
        super(baseURI, requestPort, basePath, defaultAuthScheme, filters, defaultSpec, urlEncode, restAssuredConfig, logRepository, proxySpecification, allowContentType, addCsrfFilter);
    }

    @Override
    public QtafRequestSpecification when() {
        System.out.println("QTAF when()");
        return (QtafRequestSpecification) super.when();
    }

    @Override
    public QtafRequestSpecification baseUri(String baseUri) {
        System.out.println("QTAF baseUri()");
        return (QtafRequestSpecification) super.baseUri(baseUri);
    }

    @Override
    public QtafResponse get(String path, Object... pathParams) {
        return (QtafResponse) super.get(path, pathParams);
    }

    @Override
    public MetaClass getMetaClass() {
        return null;
    }

    @Override
    public void setMetaClass(MetaClass metaClass) {

    }
}

