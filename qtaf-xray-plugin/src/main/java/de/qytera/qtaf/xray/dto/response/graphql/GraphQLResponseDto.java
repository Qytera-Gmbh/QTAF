package de.qytera.qtaf.xray.dto.response.graphql;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a generic GraphQL response. These always consist of a data property and an optional array of errors.
 * <br/><br/>
 * A successful response could look like this:
 * <pre>
 * {@code
 *   {
 *     "data": {
 *       "getTests": {
 *         "total": 10,
 *         "start": 0,
 *         "results": [
 *           {
 *             "issueId": "12345"
 *           },
 *           {
 *             "issueId": "45678"
 *           }
 *         ]
 *       }
 *     }
 *   }
 * }
 * </pre>
 * <br/><br/>
 * An erroneous response could look like this:
 * <pre>
 * {@code
 *   {
 *     "errors": [
 *       {
 *         "message": "Invalid JQL query",
 *         "locations": [
 *           {
 *             "line": 2,
 *             "column": 3
 *           }
 *         ],
 *         "path": [
 *           "getTests"
 *         ]
 *       }
 *     ],
 *     "data": {
 *       "getTests": null
 *     }
 *   }
 * }
 * </pre>
 *
 * @param <T> the type of the returned data
 */
@Getter
@Setter
public abstract class GraphQLResponseDto<T> {

    /**
     * Response payload.
     */
    private T data;

    /**
     * Error DTO.
     */
    private GraphQLResponseErrorDto[] errors;

    /**
     * Whether the response contains any errors.
     *
     * @return true if it contains an error, false otherwise
     */
    public boolean hasErrors() {
        return errors != null;
    }

    /**
     * Returns a summary of all errors that occurred during request handling.
     *
     * @return the error summary
     * @throws IllegalStateException if the response does not contain any errors
     */
    public String errorReason() {
        if (!hasErrors()) {
            throw new IllegalStateException("the response does not contain any errors");
        }
        return Arrays.stream(errors)
                .map(error -> String.format("%s (%s)", error.getMessage(), String.join(",", error.getPath())))
                .collect(Collectors.joining(""));
    }

}
