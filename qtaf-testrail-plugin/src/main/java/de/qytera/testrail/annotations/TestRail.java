package de.qytera.testrail.annotations;

import de.qytera.testrail.utils.RunIds;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestRail {
    String[] caseId() default "";
    String runId();
}
