package de.qytera.qtaf.xray.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that can be used for tests to configure documentation of test results in Xray
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface XrayTest {
    /**
     * Internal Xray test ID that can be used for uploading test execution results
     * @return  ID
     */
    String key();

    /**
     * Flag that indicates whether an HTML report should be added as evidence to this test
     */
    boolean scenarioReport() default false;

    /**
     * Flag that indicates whether screenshots should be added as evidence to this test
     */
    boolean screenshots() default false;
}
