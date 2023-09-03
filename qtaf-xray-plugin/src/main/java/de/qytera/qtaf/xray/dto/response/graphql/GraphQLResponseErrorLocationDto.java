package de.qytera.qtaf.xray.dto.response.graphql;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a GraphQL error location.
 *
 * @see <a href="https://spec.graphql.org/October2021/#sec-Errors.Error-result-format">GraphQL specification</a>
 */
@Getter
@Setter
public class GraphQLResponseErrorLocationDto {
    /**
     * Line where the error occurred.
     */
    private int line;

    /**
     * Column where the error occurred.
     */
    private int column;

}

