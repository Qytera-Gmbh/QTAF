package de.qytera.qtaf.core.guice.invokation;

import org.testng.annotations.BeforeTest;

/**
 * Information object that contains Step execution information
 */
public class BeforeTestExecutionInfo extends AbstractStepExecutionInfo {
    private BeforeTest annotation;

    /**
     * Get annotation
     *
     * @return annotation
     */
    public BeforeTest getAnnotation() {
        return annotation;
    }

    /**
     * Set BeforeTest annotation
     *
     * @param annotation BeforeTest annotation
     * @return this
     */
    public BeforeTestExecutionInfo setAnnotation(BeforeTest annotation) {
        this.annotation = annotation;
        return this;
    }
}
