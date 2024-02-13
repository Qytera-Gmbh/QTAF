package de.qytera.qtaf.apitesting.restassured;

import com.google.common.collect.Lists;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import org.testng.Assert;

import java.util.List;

public class TestHelper {
    public static List<LogMessage> getCurrentLogCollectionFrom(IQtafTestContext testContext) {
        List<LogMessage> logMessages = testContext.getLogCollection().getLogMessages();
        return logMessages;
    }

    public static ApiLogMessage getLatestApiLogMessageFromLogMessages(List<LogMessage> logMessages){
        List<LogMessage> reversedLogMessages = Lists.reverse(logMessages);
        for (LogMessage logMessage : reversedLogMessages) {
            ApiLogMessage convertedApiLogmessage = (logMessage instanceof ApiLogMessage ? (ApiLogMessage) logMessage : null);
            if (convertedApiLogmessage != null) {
                return convertedApiLogmessage;
            }
        }
        throw new IllegalArgumentException("The list of LogMessages provided does not include a ApiLogMessage");
    }

    public static ApiLogMessage getLatestApiLogMessageFromContext(IQtafTestContext testContext){
        return getLatestApiLogMessageFromLogMessages(getCurrentLogCollectionFrom(testContext));
    }

    public static void changeApiLogMessageStatusFromFailedToPassed(ApiLogMessage apiLogMessage){
        if (apiLogMessage.getStatus() != LogMessage.Status.FAILED) {
            throw new IllegalArgumentException("It was expected that a apiLogMessage with a failed status is provided. Please double check if you are sure you want to change the status of this logMessage from: \"" + apiLogMessage.getStatus() + "\" to: \"" + LogMessage.Status.PASSED + "\"");
        }
        apiLogMessage.setStatus(LogMessage.Status.PASSED);
    }

    public static List<AssertionLogMessage> getAssertionMessagesFormApiLogMessage(ApiLogMessage apiLogMessage){
        return apiLogMessage.getAssertions();
    }

    public static void apiAssertionMessageFitsTo(String message, AssertionLogMessage assertionLogMessage,
                                                 AssertionLogMessageType type,
                                                 boolean condition,
                                                 Object actual,
                                                 Object expected,
                                                 LogMessage.Status status) {
        Assert.assertEquals(assertionLogMessage.type(), type, message);
        Assert.assertEquals(assertionLogMessage.condition(), condition, message);
        Assert.assertEquals(assertionLogMessage.actual(), actual, message);
        Assert.assertEquals(assertionLogMessage.expected(), expected, message);
        Assert.assertEquals(assertionLogMessage.getStatus(), status, message);
        Assert.assertTrue(assertionLogMessage.getAssertions().isEmpty(), message);

    }

    public static void apiLogMessageFitsTo(String message,
                                           ApiLogMessage apiLogMessage,
                                           LogMessage.Status apiLogMessageStatus,
                                           int numberOfAssertions,
                                           ApiLogMessage.Action.RequestType requestType,
                                           int statusCode){
        Assert.assertEquals(apiLogMessage.getStatus(), apiLogMessageStatus, message);
        Assert.assertEquals(apiLogMessage.getAssertions().size(), numberOfAssertions, message);
        Assert.assertEquals(apiLogMessage.getAction().getRequestType(), requestType, message);
        Assert.assertEquals(apiLogMessage.getResponse().getStatusCode(), statusCode, message);
    }
}
