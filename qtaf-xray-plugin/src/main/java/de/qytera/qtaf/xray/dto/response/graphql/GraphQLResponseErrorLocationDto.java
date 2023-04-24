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

    private int line;
    private int column;

}

