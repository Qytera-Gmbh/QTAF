package de.qytera.qtaf.core.guice.method_interceptor.builder;

import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import de.qytera.qtaf.core.guice.method_interceptor.QtafMethodInterceptor;
import de.qytera.qtaf.core.guice.method_interceptor.QtafTestNGAfterSuiteInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.testng.annotations.AfterSuite;

import java.lang.reflect.Method;

/**
 * TestNG AfterSuite method interceptor.
 * Bind to all methods that are annotated with the @AfterTest annotation.
 */
public class QtafAfterSuiteMethodInterceptorBuilder implements QtafMethodInterceptor {
    @Override
    public Matcher<? super Class<?>> getClassMatcher() {
        return Matchers.any();
    }

    @Override
    public Matcher<? super Method> getMethodMatcher() {
        return Matchers.annotatedWith(AfterSuite.class);
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return new QtafTestNGAfterSuiteInterceptor();
    }
}
