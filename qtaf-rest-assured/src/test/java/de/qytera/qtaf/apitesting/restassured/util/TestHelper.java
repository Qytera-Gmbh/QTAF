package de.qytera.qtaf.apitesting.restassured.util;

import com.google.common.collect.Lists;
import de.qytera.qtaf.apitesting.log.model.message.ApiLogMessage;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessage;
import de.qytera.qtaf.core.log.model.message.AssertionLogMessageType;
import de.qytera.qtaf.core.log.model.message.LogMessage;
import lombok.Getter;
import lombok.Setter;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public static ApiLogMessage getApiLogMessageAtIndexFromContext(IQtafTestContext testContext, int index){
        List<LogMessage> allLogMessages = getCurrentLogCollectionFrom(testContext);
        List<ApiLogMessage> apiLogMessages = new ArrayList<>();
        for (LogMessage logMessage : allLogMessages) {
            ApiLogMessage convertedApiLogmessage = (logMessage instanceof ApiLogMessage ? (ApiLogMessage) logMessage : null);
            if (convertedApiLogmessage != null) {
                apiLogMessages.add(convertedApiLogmessage);
            }
        }
        if (apiLogMessages.isEmpty()){
            throw new IllegalArgumentException("The list of LogMessages provided does not include a ApiLogMessage");
        }
        return apiLogMessages.get(index);
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
                                                 Object actual,
                                                 Object expected,
                                                 LogMessage.Status status) {
        Assert.assertEquals(assertionLogMessage.type(), type, message + " <- from type");
        Assert.assertEquals(assertionLogMessage.actual(), actual, message + " <- from actual");
        Assert.assertEquals(assertionLogMessage.expected(), expected, message + " <- from expected");
        Assert.assertEquals(assertionLogMessage.getStatus(), status, message + " <- from status");
        Assert.assertTrue(assertionLogMessage.getAssertions().isEmpty(), message + " <- from isEmpty");

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
    public static void apiLogMessageUrlPathFitsTo(String message,
                                           ApiLogMessage apiLogMessage,
                                           String baseUri,
                                           String basePath,
                                           Map<String, ?> pathParams,
                                           Map<String, ?> queryParams){
        Assert.assertEquals(apiLogMessage.getRequest().getBaseUri(), baseUri, message + " <- from baseUri");
        Assert.assertEquals(apiLogMessage.getRequest().getBasePath(), basePath, message + " <- from basePath");
        Assert.assertEquals(apiLogMessage.getRequest().getPathParams(), pathParams, message + " <- from pathParams");
        Assert.assertEquals(apiLogMessage.getRequest().getQueryParams(), queryParams, message + " <- from queryParams");
    }
}
