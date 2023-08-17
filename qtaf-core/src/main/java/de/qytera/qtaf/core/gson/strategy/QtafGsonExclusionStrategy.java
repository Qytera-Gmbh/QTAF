package de.qytera.qtaf.core.gson.strategy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * This class defines which classes or fields should be ignored by JSON
 */
public class QtafGsonExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return aClass == InvocationHandler.class
                || aClass == Proxy.class
                || aClass == byte[].class
                || aClass == ByteArrayOutputStream.class;
    }
}
