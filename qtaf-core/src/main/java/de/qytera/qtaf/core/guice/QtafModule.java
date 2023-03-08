package de.qytera.qtaf.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.guice.method_interceptor.QtafStepTrackerInterceptor;
import de.qytera.qtaf.core.guice.method_interceptor.QtafTestNGBeforeTestInterceptor;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


/**
 * Guice module used in TestContext classes
 */
public class QtafModule extends AbstractModule {
    @Override
    protected void configure() {
        // Bind to all methods that are annotated with the @BeforeTest annotation
        bindInterceptor(
                Matchers.any(),                             // Any class
                Matchers.annotatedWith(BeforeTest.class),   // Any method annotated with @BeforeTest
                new QtafTestNGBeforeTestInterceptor()       // Logic for method invocation
        );

        // Bind to all methods that are annotated with the @AfterTest annotation
        bindInterceptor(
                Matchers.any(),                             // Any class
                Matchers.annotatedWith(AfterTest.class),    // Any method annotated with @AfterTest
                new QtafTestNGBeforeTestInterceptor()       // Logic for method invocation
        );

        // Bind to all methods that are annotated with the @Step annotation
        bindInterceptor(
                Matchers.any(),                     // Any class
                Matchers.annotatedWith(Step.class), // Any method annotated with @Step
                new QtafStepTrackerInterceptor()    // Logic for method invocation
        );
    }
}