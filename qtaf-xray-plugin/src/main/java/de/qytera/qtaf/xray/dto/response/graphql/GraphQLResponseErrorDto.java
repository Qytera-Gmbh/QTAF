package de.qytera.qtaf.xray.dto.response.graphql;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a generic GraphQL error, consisting of a message, one or more locations and the query paths which caused
 * them.
 *
 * @see <a href="https://spec.graphql.org/October2021/#sec-Errors.Error-result-format">GraphQL specification</a>
 */
@Getter
@Setter
public class GraphQLResponseErrorDto {

    private String message;
    private GraphQLResponseErrorLocationDto[] locations;
    private String[] path;

}
