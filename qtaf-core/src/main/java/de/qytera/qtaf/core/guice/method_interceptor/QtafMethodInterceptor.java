package de.qytera.qtaf.core.guice.method_interceptor;

import com.google.inject.matcher.Matcher;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * This is an interface for classes providing Guice method interceptors.
 * A method interceptor can execute code before and after methods.
 */
public interface QtafMethodInterceptor {
    /**
     * Get a matcher that describes which classes should be intercepted.
     *
     * @return class matcher
     */
    Matcher<? super Class<?>> getClassMatcher();

    /**
     * Get a matcher that describes which methods should be intercepted.
     *
     * @return method matcher
     */
    Matcher<? super Method> getMethodMatcher();

    /**
     * Get an instance of MethodInterceptor that contains the logic for intercepting methods.
     * @return  MethodInterceptor object
     */
    MethodInterceptor getMethodInterceptor();
}
