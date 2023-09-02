package de.qytera.qtaf.testrail.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestRail {
    /**
     * ID of the test case.
     *
     * @return test case ID
     */
    String[] caseId() default "";

    /**
     * ID of a test run. It's like a test plan ID.
     *
     * @return test run ID
     */
    String runId();
}
