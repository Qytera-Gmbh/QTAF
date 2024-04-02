package de.qytera.qtaf.xray.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.console.ConsoleColors;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.events.EventListenerInitializer;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.payload.IQtafTestEventPayload;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.testng.event_subscriber.TestNGLoggingSubscriber;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.internal.ConstructorOrMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;


public class InitializationSubscriberTest {

    private static final IQtafTestContext CONTEXT = new IQtafTestContext() {
        @Override
        public TestScenarioLogCollection getLogCollection() {
            throw new UnsupportedOperationException();
        }

        @Override
        public IQtafTestContext setLogCollection(TestScenarioLogCollection collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addLoggerToFieldsRecursively() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void restartDriver() {
            throw new UnsupportedOperationException();
        }

        @Override
        public IQtafTestContext initialize() {
            throw new UnsupportedOperationException();
        }
    };

    @Test(description = "import execution results should be called exactly once")
    public void testUploadSubscriberEnabled() {
        QtafFactory.getConfiguration().setBoolean(XrayConfigHelper.XRAY_ENABLED, true);

        List<String> messages = new ArrayList<>();
        try (MockedStatic<QtafFactory> qtafFactory = Mockito.mockStatic(QtafFactory.class, Mockito.CALLS_REAL_METHODS)) {
            Logger logger = Mockito.mock(Logger.class);
            qtafFactory.when(QtafFactory::getLogger).thenReturn(logger);

            TestNGLoggingSubscriber testNGLoggingSubscriber = new TestNGLoggingSubscriber();
            testNGLoggingSubscriber.initialize();

            new InitializationSubscriber().initialize();

            Mockito.doAnswer(answer -> {
                messages.add(answer.getArgument(0, String.class));
                return null;
            }).when(logger).info(anyString());


            try (MockedStatic<EventListenerInitializer> eventInitializer = Mockito.mockStatic(EventListenerInitializer.class)) {
                eventInitializer.when(EventListenerInitializer::getEventSubscribers).thenReturn(List.of(testNGLoggingSubscriber));
                QtafEvents.eventListenersInitialized.onNext(null);
            }

            QtafEvents.testFailure.onNext(getTestEventPayload(getTestResult(getTestMethod("QTAF-123"))));

            Assert.assertEquals(messages,
                    List.of(
                            "[Test] [QTAF-123] [de.qytera.qtaf.xray.event_subscriber.InitializationSubscriberTest$1.mockMethod] %s"
                                    .formatted(
                                            ConsoleColors.redBright("failure")
                                    )
                    )
            );
        }

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
            ITestNGMethod method
    ) {
        ITestResult result = Mockito.mock(ITestResult.class, invocation -> {
            throw new UnsupportedOperationException();
        });
        Mockito.doReturn(method).when(result).getMethod();
        Mockito.doReturn(CONTEXT).when(result).getInstance();
        return result;
    }

    public ITestNGMethod getTestMethod(String key) {
        XrayTest annotation = Mockito.mock(XrayTest.class, invocation -> {
            throw new UnsupportedOperationException();
        });
        Mockito.doReturn(key).when(annotation).key();
        Method method = Mockito.mock(Method.class, invocation -> {
            throw new UnsupportedOperationException();
        });
        Mockito.doReturn(annotation).when(method).getAnnotation(XrayTest.class);
        ITestNGMethod testNGMethod = Mockito.mock(ITestNGMethod.class, invocation -> {
            throw new UnsupportedOperationException();
        });
        Mockito.doReturn(new ConstructorOrMethod(method)).when(testNGMethod).getConstructorOrMethod();
        Mockito.doReturn("mockMethod").when(testNGMethod).getMethodName();
        return testNGMethod;
    }
}
