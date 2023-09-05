package de.qytera.qtaf.core.guice.invokation;

import org.testng.annotations.BeforeSuite;

/**
 * Information object that contains Step execution information.
 */
public class BeforeSuiteExecutionInfo extends AbstractStepExecutionInfo {
    private BeforeSuite annotation;

    /**
     * Get annotation.
     *
     * @return annotation
     */
    public BeforeSuite getAnnotation() {
        return annotation;
    }

    /**
     * Set BeforeSuite Annotation.
     *
     * @param annotation BeforeSuite Annotation
     * @return this
     */
    public BeforeSuiteExecutionInfo setAnnotation(BeforeSuite annotation) {
        this.annotation = annotation;
        return this;
    }
}
