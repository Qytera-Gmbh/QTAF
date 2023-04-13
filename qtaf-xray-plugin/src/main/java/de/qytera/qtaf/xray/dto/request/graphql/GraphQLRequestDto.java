package de.qytera.qtaf.xray.dto.request.graphql;

import com.google.gson.JsonObject;
import de.qytera.qtaf.core.gson.GsonFactory;
import lombok.Getter;
import lombok.Setter;

/**
 * A generic GraphQL request. These are split into a {@code query} property and a {@code variables} property. The
 * {@code variables} property is used in parameterized queries and can contain arbitrary data for each variable.
 *
 * @see <a href="https://graphql.org/learn/queries/">GraphQL documentation</a>
 */
@Getter
@Setter
public class GraphQLRequestDto {

    private String query;
    private JsonObject variables;

    /**
     * Adds a variable and its content to the GraphQL request.
     *
     * @param name  the name of the variable
     * @param value the variable's data
     */
    public void addVariable(String name, Object value) {
        if (variables == null) {
            variables = new JsonObject();
        }
        variables.add(name, GsonFactory.getInstance().toJsonTree(value));
    }

}
