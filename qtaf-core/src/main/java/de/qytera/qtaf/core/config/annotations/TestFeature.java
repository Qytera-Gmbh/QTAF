package de.qytera.qtaf.core.config.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Test case annotation should be used for all classes that contain tests.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestFeature {
    /**
     * Feature name.
     *
     * @return name
     */
    String name();

    /**
     * Feature description.
     *
     * @return description
     */
    String description() default "";
}
