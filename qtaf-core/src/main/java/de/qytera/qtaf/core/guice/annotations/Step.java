package de.qytera.qtaf.core.guice.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method annotation for test steps.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Step {

    /**
     * Test step name.
     *
     * @return the step's name
     */
    String name() default "";

    /**
     * Test step description.
     *
     * @return the step's description
     */
    String description() default "";
}
