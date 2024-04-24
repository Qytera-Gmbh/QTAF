package de.qytera.qtaf.core.guice.method_interceptor.builder;

import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.guice.method_interceptor.QtafMethodInterceptor;
import de.qytera.qtaf.core.guice.method_interceptor.QtafStepMethodInterceptor;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * Step method interceptor.
 */
public class QtafStepMethodInterceptorBuilder implements QtafMethodInterceptor {
    @Override
    public Matcher<? super Class<?>> getClassMatcher() {
        return Matchers.any();
    }

    @Override
    public Matcher<? super Method> getMethodMatcher() {
        return Matchers.annotatedWith(Step.class);
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return new QtafStepMethodInterceptor();
    }
}