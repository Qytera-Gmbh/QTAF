package de.qytera.qtaf.xray.builder;

/**
 * Interface for building HTTP request bodies described by DTOs (or other Java classes). For example, a request builder
 * for building authentication requests could look as follows:
 * <pre>
 * {@code
 * public class AuthRequestBodyBuilder implements RequestBodyBuilder<CredentialsDto> {
 *
 *   @Override
 *   CredentialsDto build() {
 *     return new BasicCredentialsDto("username", "password");
 *   }
 *
 * }
 * }
 * </pre>
 *
 * @param <T> the body's type
 */
public interface RequestBodyBuilder<T> {

    /**
     * Builds the request's body.
     *
     * @return the request body
     */
    T build();

}
