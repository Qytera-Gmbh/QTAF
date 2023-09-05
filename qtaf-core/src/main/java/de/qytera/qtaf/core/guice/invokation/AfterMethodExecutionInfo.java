package de.qytera.qtaf.core.guice.invokation;

import org.testng.annotations.AfterMethod;

/**
 * Information object that contains Step execution information.
 */
public class AfterMethodExecutionInfo extends AbstractStepExecutionInfo {
    private AfterMethod annotation;

    /**
     * Get annotation.
     *
     * @return annotation
     */
    public AfterMethod getAnnotation() {
        return annotation;
    }

    /**
     * Set AfterMethod annotation.
     *
     * @param annotation AfterMethod annotation
     * @return this
     */
    public AfterMethodExecutionInfo setAnnotation(AfterMethod annotation) {
        this.annotation = annotation;
        return this;
    }
}
