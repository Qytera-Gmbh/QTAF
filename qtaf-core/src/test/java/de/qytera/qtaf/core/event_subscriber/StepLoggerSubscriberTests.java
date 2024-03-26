package de.qytera.qtaf.core.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.event_subscriber.step.StepLoggerSubscriber;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.*;

public class StepLoggerSubscriberTests {

    @Test(description = "Test initialization")
    public void testCheckIfStepLoggingIsConfigured() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method checkIfStepLoggingIsConfigured = StepLoggerSubscriber.class.getDeclaredMethod("checkIfStepLoggingIsConfigured");
        checkIfStepLoggingIsConfigured.setAccessible(true);
        ConfigMap config = QtafFactory.getConfiguration();

        StepLoggerSubscriber stepLoggerSubscriber = new StepLoggerSubscriber();

        config.setBoolean("logging.logSteps", true);
        Assert.assertTrue((Boolean) checkIfStepLoggingIsConfigured.invoke(stepLoggerSubscriber));

        config.setBoolean("logging.logSteps", false);
        Assert.assertFalse((Boolean) checkIfStepLoggingIsConfigured.invoke(stepLoggerSubscriber));

        checkIfStepLoggingIsConfigured.setAccessible(false);
    }
}
