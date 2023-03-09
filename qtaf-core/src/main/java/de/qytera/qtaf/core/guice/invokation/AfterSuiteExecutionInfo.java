package de.qytera.qtaf.core.guice.invokation;

import org.testng.annotations.AfterSuite;

/**
 * Information object that contains Step execution information
 */
public class AfterSuiteExecutionInfo extends AbstractStepExecutionInfo {
    private AfterSuite annotation;

    /**
     * Get annotation
     *
     * @return annotation
     */
    public AfterSuite getAnnotation() {
        return annotation;
    }

    /**
     * Set AfterSuite Annotation
     *
     * @param annotation AfterSuite Annotation
     * @return this
     */
    public AfterSuiteExecutionInfo setAnnotation(AfterSuite annotation) {
        this.annotation = annotation;
        return this;
    }
}
