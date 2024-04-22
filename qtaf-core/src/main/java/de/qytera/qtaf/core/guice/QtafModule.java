package de.qytera.qtaf.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.guice.method_interceptor.*;
import de.qytera.qtaf.core.reflection.ClassLoader;
import de.qytera.qtaf.core.selenium.AbstractDriver;
import org.testng.annotations.*;


/**
 * Guice module used in TestContext classes.
 * This module is responsible for method interception.
 */
public class QtafModule extends AbstractModule {
    @Override
    protected void configure() {
        // Bind to all methods that are annotated with the @BeforeTest annotation
        bindInterceptor(
                Matchers.any(),                             // Any class
                Matchers.annotatedWith(BeforeSuite.class),  // Any method annotated with @BeforeSuite
                new QtafTestNGBeforeSuiteInterceptor()      // Logic for method invocation
        );

        // Bind to all methods that are annotated with the @AfterTest annotation
        bindInterceptor(
                Matchers.any(),                             // Any class
                Matchers.annotatedWith(AfterSuite.class),   // Any method annotated with @AfterSuite
                new QtafTestNGAfterSuiteInterceptor()       // Logic for method invocation
        );

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
                new QtafTestNGAfterTestInterceptor()        // Logic for method invocation
        );

        // Bind to all methods that are annotated with the @BeforeTest annotation
        bindInterceptor(
                Matchers.any(),                             // Any class
                Matchers.annotatedWith(BeforeMethod.class), // Any method annotated with @BeforeMethod
                new QtafTestNGBeforeMethodInterceptor()     // Logic for method invocation
        );

        // Bind to all methods that are annotated with the @AfterTest annotation
        bindInterceptor(
                Matchers.any(),                             // Any class
                Matchers.annotatedWith(AfterMethod.class),  // Any method annotated with @AfterMethod
                new QtafTestNGAfterMethodInterceptor()      // Logic for method invocation
        );

        // Bind to all methods that are annotated with the @Step annotation
        bindInterceptor(
                Matchers.any(),                             // Any class
                Matchers.annotatedWith(Step.class),         // Any method annotated with @Step
                new QtafStepMethodInterceptor()             // Logic for method invocation
        );

        // Get instances of all method interceptor classes
        Object[] lst = ClassLoader.getInstancesOfDirectSubtypesOf(QtafMethodInterceptor.class);

        // Initialize each method interceptor
        for (Object o : lst) {
            QtafMethodInterceptor interceptor = (QtafMethodInterceptor) o;
            bindInterceptor(
                    interceptor.getClassMatcher(),
                    interceptor.getMethodMatcher(),
                    interceptor.getMethodInterceptor()
            );
        }
    }
}