package de.qytera.qtaf.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.guice.method_interceptor.QtafStepTrackerInterceptor;


/**
 * Guice module used in TestContext classes
 */
public class QtafModule extends AbstractModule {
    @Override
    protected void configure() {
        // Bind to all methods that are annotated with the Step annotation
        bindInterceptor(
                Matchers.any(),                     // Any class
                Matchers.annotatedWith(Step.class), // Any method annotated with Step
                new QtafStepTrackerInterceptor()    // Logic for method invocation
        );
    }
}