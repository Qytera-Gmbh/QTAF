package de.qytera.qtaf.core.guice.invokation;

import org.testng.annotations.AfterTest;

/**
 * Information object that contains Step execution information.
 */
public class AfterTestExecutionInfo extends AbstractStepExecutionInfo {
    private AfterTest annotation;

    /**
     * Get annotation.
     *
     * @return annotation
     */
    public AfterTest getAnnotation() {
        return annotation;
    }

    /**
     * Set beforeTestAnnotation.
     *
     * @param annotation AfterTest Annotation
     * @return this
     */
    public AfterTestExecutionInfo setAnnotation(AfterTest annotation) {
        this.annotation = annotation;
        return this;
    }
}
