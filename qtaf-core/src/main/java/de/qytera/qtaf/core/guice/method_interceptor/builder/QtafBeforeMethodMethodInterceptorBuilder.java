package de.qytera.qtaf.core.guice.method_interceptor.builder;

import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import de.qytera.qtaf.core.guice.method_interceptor.QtafMethodInterceptor;
import de.qytera.qtaf.core.guice.method_interceptor.QtafTestNGBeforeMethodInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

/**
 * TestNG BeforeMethod method interceptor.
 * Bind to all methods that are annotated with the @BeforeTest annotation.
 */
public class QtafBeforeMethodMethodInterceptorBuilder implements QtafMethodInterceptor {
    @Override
    public Matcher<? super Class<?>> getClassMatcher() {
        return Matchers.any();
    }

    @Override
    public Matcher<? super Method> getMethodMatcher() {
        return Matchers.annotatedWith(BeforeMethod.class);
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return new QtafTestNGBeforeMethodInterceptor();
    }
}
