package de.qytera.qtaf.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.guice.invokation.StepExecutionInfo;
import de.qytera.qtaf.core.guice.method_interceptor.QtafStepTrackerInterceptor;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import io.cucumber.java.en.When;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


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