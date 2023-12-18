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
    protected QueryableRequestSpecification req;

    /**
     * API Response information
     */
    protected ExtractableResponse<Response> res;

    /**
     * Constructor
     * @param req   Request specification information
     * @param res   Response information
     */
    public ExecutedApiTest(QueryableRequestSpecification req, ExtractableResponse<Response> res) {
        this.req = req;
        this.res = res;
    }

    /**
     * Getter for request specification
     * @return  request specification
     */
    public QueryableRequestSpecification getReq() {
        return req;
    }

    /**
     * Getter for request specification. Kotlin uses this function for Pair destructuring.
     * @see <a href="https://kotlinlang.org/docs/destructuring-declarations.html">Kotlin Destructuring</a>
     * @return request specification
     */
    public QueryableRequestSpecification component1() {
        return req;
    }

    /**
     * Getter for response
     * @return  response
     */
    public ExtractableResponse<Response> getRes() {
        return res;
    }

    /**
     * Getter for Kotlin code. Kotlin uses this function for Pair destructuring.
     * @see <a href="https://kotlinlang.org/docs/destructuring-declarations.html">Kotlin Destructuring</a>
     * @return response
     */
    public ExtractableResponse<Response> component2() {
        return res;
    }
}
