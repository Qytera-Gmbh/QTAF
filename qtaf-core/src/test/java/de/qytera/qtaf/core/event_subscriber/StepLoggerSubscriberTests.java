package de.qytera.qtaf.core.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.config.helper.QtafTestExecutionConfigHelper;
import de.qytera.qtaf.core.console.ConsoleColors;
import de.qytera.qtaf.core.event_subscriber.step.StepLoggerSubscriber;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.guice.invokation.StepExecutionInfo;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.message.StepInformationLogMessage;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.aopalliance.intercept.MethodInvocation;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyString;

public class StepLoggerSubscriberTests {

    @DataProvider
    public Object[][] provideExpectedLogs() {
        return Stream.of(
                new Object[]{
                        true,
                        List.of(
                                "[Step] [0] [sleep] started",
                                "[Step] [0] [sleep] %s".formatted(ConsoleColors.green("success")),
                                "[Step] [0] [wake up] started",
                                "[Step] [0] [wake up] %s".formatted(ConsoleColors.red("failure"))
                        )
                },
                new Object[]{
                        false,
                        Collections.emptyList()
                }
        ).toArray(Object[][]::new);
    }

    @Test(description = "Test logging when step logging is enabled", dataProvider = "provideExpectedLogs")
    public void testStepsLogging(boolean enableStepLogging, List<String> expectedMessages) throws NoSuchMethodException {

        QtafFactory.getConfiguration().setBoolean(QtafTestExecutionConfigHelper.LOGGING_LOG_STEPS, enableStepLogging);
        List<String> messages = new ArrayList<>();

        // Our mock object containing step-annotated methods.
        QtafTestNGContext context = new NightTime();
        context.setLogCollection(TestScenarioLogCollection.createTestScenarioLogCollection(
                "0",
                NightTime.class.getCanonicalName(),
                "0",
                "testMorningRoutine"
        ));

        try (MockedStatic<QtafFactory> qtafFactory = Mockito.mockStatic(QtafFactory.class, Mockito.CALLS_REAL_METHODS)) {
            Logger logger = Mockito.mock(Logger.class);
            qtafFactory.when(QtafFactory::getLogger).thenReturn(logger);

            new StepLoggerSubscriber().initialize();

            Mockito.doAnswer(answer -> {
                messages.add(answer.getArgument(0, String.class));
                return null;
            }).when(logger).info(anyString());

            Step step = NightTime.class.getMethod("doSleep").getAnnotation(Step.class);
            StepExecutionInfo stepExecutionInfo = new StepExecutionInfo();
            stepExecutionInfo.setLogMessage(new StepInformationLogMessage("doSleep", "still dreaming"));
            stepExecutionInfo.setAnnotation(step);
            stepExecutionInfo.setMethodInvocation(getInvocation(context, "doSleep"));
            QtafEvents.beforeStepExecution.onNext(stepExecutionInfo);
            QtafEvents.stepExecutionSuccess.onNext(stepExecutionInfo);

            step = NightTime.class.getMethod("doWakeUp").getAnnotation(Step.class);
            stepExecutionInfo = new StepExecutionInfo();
            stepExecutionInfo.setLogMessage(new StepInformationLogMessage("doWakeUp", "too early man"));
            stepExecutionInfo.setAnnotation(step);
            stepExecutionInfo.setMethodInvocation(getInvocation(context, "doWakeUp"));
            QtafEvents.beforeStepExecution.onNext(stepExecutionInfo);
            QtafEvents.stepExecutionFailure.onNext(stepExecutionInfo);
        }

        Assert.assertEquals(messages, expectedMessages);
    }

    @TestFeature(name = "Night time")
    private static final class NightTime extends QtafTestNGContext {

        @Step(name = "sleep")
        public String doSleep() {
            return "still dreaming";
        }

        @Step(name = "wake up")
        public String doWakeUp() {
            return "too early man";
        }

    }

    private MethodInvocation getInvocation(Object context, String methodName) {
        try {
            Method method = NightTime.class.getMethod(methodName);
            MethodInvocation methodInvocation = Mockito.mock(MethodInvocation.class, invocation -> {
                throw new UnsupportedOperationException();
            });
            Mockito.doReturn(method).when(methodInvocation).getMethod();
            Mockito.doReturn(context).when(methodInvocation).getThis();
            Mockito.doReturn(new Object[0]).when(methodInvocation).getArguments();
            return methodInvocation;
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
