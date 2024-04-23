package de.qytera.qtaf.apitesting.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for methods performing a REST call.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RestCall {
    /**
     * Test step name.
     *
     * @return  test step name
     */
    String name();
}
