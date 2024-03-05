package de.qytera.qtaf.apitesting.log.model.message;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ApiLogMessageUpdater {
    public static void updateHeaderLogsByAddingAHeader(ApiLogMessage logMessage, Header newHeader){
        Assert.assertNotNull(logMessage.getRequest().getHeaders());
        List<Header> headerList = logMessage.getRequest().getHeaders().asList();
        List<Header> modifiableHeaderList = new ArrayList<>(headerList);
        modifiableHeaderList.add(newHeader);
        Headers headers = new Headers(modifiableHeaderList);
        logMessage.getRequest().setHeaders(headers);
    }
}
