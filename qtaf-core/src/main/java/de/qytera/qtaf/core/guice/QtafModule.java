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