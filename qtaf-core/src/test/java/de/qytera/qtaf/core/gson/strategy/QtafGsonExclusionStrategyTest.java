package de.qytera.qtaf.core.gson.strategy;

import com.google.gson.FieldAttributes;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class QtafGsonExclusionStrategyTest {
    public String demoField;

    @Test
    public void testExcludedFields() throws NoSuchFieldException {
        QtafGsonExclusionStrategy strategy = new QtafGsonExclusionStrategy();
        Assert.assertFalse(strategy.shouldSkipField(new FieldAttributes(this.getClass().getField("demoField"))));
    }

    @Test
    public void testSkipClass() {
        QtafGsonExclusionStrategy strategy = new QtafGsonExclusionStrategy();
        Assert.assertFalse(strategy.shouldSkipClass(this.getClass()));
        Assert.assertTrue(strategy.shouldSkipClass(Proxy.class));
        Assert.assertTrue(strategy.shouldSkipClass(InvocationHandler.class));
    }
}
