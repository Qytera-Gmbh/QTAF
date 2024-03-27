package de.qytera.qtaf.testng.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.console.ConsoleColors;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.payload.IQtafTestEventPayload;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

public class TestNGLoggingSubscriberTest {

    @Test(description = "Test logging when tests succeed")
    public void testLoggingTestSuccess() {
        List<String> loggedMessages = new ArrayList<>();
        try (MockedStatic<QtafFactory> qtafFactory = Mockito.mockStatic(QtafFactory.class, Mockito.CALLS_REAL_METHODS)) {
            Logger logger = Mockito.mock(Logger.class);
            qtafFactory.when(QtafFactory::getLogger).thenReturn(logger);

            new TestNGLoggingSubscriber().initialize();

            Mockito.doAnswer(answer -> {
                loggedMessages.add(answer.getArgument(0, String.class));
                return null;
            }).when(logger).info(anyString());

            MiniContext testClass = new MiniContext();
            ITestNGMethod testMethod = getTestMethod("testMethod");
            ITestResult testResult = getTestResult(
                    testClass,
                    testMethod
            );

            QtafEvents.testSuccess.onNext(getTestEventPayload(testResult));
        }
        Assert.assertEquals(
                loggedMessages,
                List.of(
                        "[Test] [de.qytera.qtaf.testng.event_subscriber.TestNGLoggingSubscriberTest$MiniContext.testMethod] %s"
                                .formatted(
                                        ConsoleColors.greenBright("success")
                                )
                )
        );
    }

    @Test(description = "Test logging when tests fail")
    public void testLoggingTestFailure() {
        List<String> loggedMessages = new ArrayList<>();
        try (MockedStatic<QtafFactory> qtafFactory = Mockito.mockStatic(QtafFactory.class, Mockito.CALLS_REAL_METHODS)) {
            Logger logger = Mockito.mock(Logger.class);
            qtafFactory.when(QtafFactory::getLogger).thenReturn(logger);

            new TestNGLoggingSubscriber().initialize();

            Mockito.doAnswer(answer -> {
                loggedMessages.add(answer.getArgument(0, String.class));
                return null;
            }).when(logger).info(anyString());

            MiniContext testClass = new MiniContext();
            ITestNGMethod testMethod = getTestMethod("testMethod");
            ITestResult testResult = getTestResult(
                    testClass,
                    testMethod
            );

            QtafEvents.testFailure.onNext(getTestEventPayload(testResult));
        }
        Assert.assertEquals(
                loggedMessages,
                List.of(
                        "[Test] [de.qytera.qtaf.testng.event_subscriber.TestNGLoggingSubscriberTest$MiniContext.testMethod] %s"
                                .formatted(
                                        ConsoleColors.redBright("failure")
                                )
                )
        );
    }

    private IQtafTestEventPayload getTestEventPayload(
            ITestResult originalEvent
    ) {
        IQtafTestEventPayload payload = Mockito.mock(IQtafTestEventPayload.class, invocation -> {
            throw new UnsupportedOperationException();
        });
        Mockito.doReturn(originalEvent).when(payload).getOriginalEvent();
        return payload;
    }

    private ITestResult getTestResult(
            IQtafTestContext context,
            ITestNGMethod method
    ) {
        ITestResult result = Mockito.mock(ITestResult.class, invocation -> {
            throw new UnsupportedOperationException();
        });
        Mockito.doReturn(method).when(result).getMethod();
        Mockito.doReturn(context).when(result).getInstance();
        return result;
    }

    public ITestNGMethod getTestMethod(String methodName) {
        ITestNGMethod method = Mockito.mock(ITestNGMethod.class, invocation -> {
            throw new UnsupportedOperationException();
        });
        Mockito.doReturn(methodName).when(method).getMethodName();
        return method;
    }

    @TestFeature(name = "Mini context")
    private static final class MiniContext extends QtafTestNGContext {


    }

}
