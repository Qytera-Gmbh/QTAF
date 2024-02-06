package de.qytera.qtaf.apitesting.restassured;

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

    public static ApiLogMessage getLatestApiLogMessageFrom(List<LogMessage> logMessages){
        for (LogMessage logMessage : logMessages) {
            ApiLogMessage convertedApiLogmessage = (logMessage instanceof ApiLogMessage ? (ApiLogMessage) logMessage : null);
            if (convertedApiLogmessage != null) {
                return convertedApiLogmessage;
            }
        }
        throw new IllegalArgumentException("The list of LogMessages provided does not include a ApiLogMessage");
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

    public static void apiAssertionMessageFitsTo(AssertionLogMessage assertionLogMessage,
                                                 AssertionLogMessageType type,
                                                 boolean condition,
                                                 Object actual,
                                                 Object expected,
                                                 LogMessage.Status status) {
        Assert.assertEquals(assertionLogMessage.type(), type);
        Assert.assertEquals(assertionLogMessage.condition(), condition);
        Assert.assertEquals(assertionLogMessage.actual(), actual);
        Assert.assertEquals(assertionLogMessage.expected(), expected);
        Assert.assertEquals(assertionLogMessage.getStatus(), status);
        Assert.assertTrue(assertionLogMessage.getAssertions().isEmpty());

    }
}
