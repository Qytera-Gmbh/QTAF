package de.qytera.qtaf.apitesting.interceptors;

import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import de.qytera.qtaf.apitesting.annotations.RestCall;
import de.qytera.qtaf.core.guice.method_interceptor.QtafMethodInterceptor;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;

public class RestCallMethodInterceptorBuilder implements QtafMethodInterceptor {
    @Override
    public Matcher<? super Class<?>> getClassMatcher() {
        return Matchers.any();
    }

    @Override
    public Matcher<? super Method> getMethodMatcher() {
        return Matchers.annotatedWith(RestCall.class);
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return new RestCallMethodInterceptor();
    }
}
