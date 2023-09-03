package de.qytera.qtaf.core.guice.invokation;

import org.testng.annotations.BeforeMethod;

/**
 * Information object that contains Step execution information.
 */
public class BeforeMethodExecutionInfo extends AbstractStepExecutionInfo {
    private BeforeMethod annotation;

    /**
     * Get annotation.
     *
     * @return annotation
     */
    public BeforeMethod getAnnotation() {
        return annotation;
    }

    /**
     * Set BeforeMethod annotation.
     *
     * @param annotation BeforeMethod annotation
     * @return this
     */
    public BeforeMethodExecutionInfo setAnnotation(BeforeMethod annotation) {
        this.annotation = annotation;
        return this;
    }
}
