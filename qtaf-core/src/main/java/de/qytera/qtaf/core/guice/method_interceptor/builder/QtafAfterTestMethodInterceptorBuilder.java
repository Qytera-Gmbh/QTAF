package de.qytera.qtaf.core.guice.method_interceptor.builder;

import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import de.qytera.qtaf.core.guice.method_interceptor.QtafMethodInterceptor;
import de.qytera.qtaf.core.guice.method_interceptor.QtafTestNGAfterTestInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.testng.annotations.AfterTest;

import java.lang.reflect.Method;

/**
 * TestNG AfterTest method interceptor.
 * Bind to all methods that are annotated with the @AfterTest annotation.
 */
public class QtafAfterTestMethodInterceptorBuilder implements QtafMethodInterceptor {
    @Override
    public Matcher<? super Class<?>> getClassMatcher() {
        return Matchers.any();
    }

    @Override
    public Matcher<? super Method> getMethodMatcher() {
        return Matchers.annotatedWith(AfterTest.class);
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return new QtafTestNGAfterTestInterceptor();
    }
}
