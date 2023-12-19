package de.qytera.qtaf.apitesting;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;

/**
 * This class models the results of an API test.
 * It contains the request specification and the response information.
 */
public class ExecutedApiTest {
    /**
     * Request specification information
     */
    protected QueryableRequestSpecification request;

    /**
     * API Response information
     */
    protected ExtractableResponse<Response> response;

    /**
     * Constructor
     * @param request   Request specification information
     * @param response   Response information
     */
    public ExecutedApiTest(QueryableRequestSpecification request, ExtractableResponse<Response> response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Getter for request specification
     * @return  request specification
     */
    public QueryableRequestSpecification getReq() {
        return request;
    }

    /**
     * Getter for request specification. Kotlin uses this function for Pair destructuring.
     * @see <a href="https://kotlinlang.org/docs/destructuring-declarations.html">Kotlin Destructuring</a>
     * @return request specification
     */
    public QueryableRequestSpecification component1() {
        return request;
    }

    /**
     * Getter for response
     * @return  response
     */
    public ExtractableResponse<Response> getRes() {
        return response;
    }

    /**
     * Getter for Kotlin code. Kotlin uses this function for Pair destructuring.
     * @see <a href="https://kotlinlang.org/docs/destructuring-declarations.html">Kotlin Destructuring</a>
     * @return response
     */
    public ExtractableResponse<Response> component2() {
        return response;
    }
}
