package de.qytera.qtaf.core.guice.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation describing test functions.
 */
@BindingAnnotation
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestFunctions {
}
