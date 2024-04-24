package de.qytera.qtaf.core.guice.method_interceptor.builder;

import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import de.qytera.qtaf.core.guice.method_interceptor.QtafMethodInterceptor;
import de.qytera.qtaf.core.guice.method_interceptor.QtafTestNGBeforeTestInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.testng.annotations.BeforeTest;

import java.lang.reflect.Method;

/**
 * TestNG BeforeTest method interceptor.
 * Bind to all methods that are annotated with the @BeforeTest annotation.
 */
public class QtafBeforeTestMethodInterceptorBuilder implements QtafMethodInterceptor {
    @Override
    public Matcher<? super Class<?>> getClassMatcher() {
        return Matchers.any();
    }

    @Override
    public Matcher<? super Method> getMethodMatcher() {
        return Matchers.annotatedWith(BeforeTest.class);
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return new QtafTestNGBeforeTestInterceptor();
    }
}