package de.qytera.testrail.utils;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class   TestRailManager {
    public static void addResultForTestCase(APIClient client, String caseId, String runId, int status, String comment) throws APIException, IOException {
        Map<String, Comparable> data = new HashMap<>();
        data.put("status_id", status);
        data.put("comment", comment);
        client.sendPost("add_result_for_case/" + runId + "/" + caseId + "", data);
    }

    public static void addAttachementForTestCase(APIClient client, String testCaseId, String path) throws APIException, IOException {
        client.sendPost("add_attachment_to_case/" + testCaseId, path);
    }

    public static void deleteAttachementForTestCase(APIClient client, String attachementId) throws APIException, IOException {
        client.sendPost("delete_attachment/" + attachementId, "");
    }

    public static JSONObject getAttachementForTestCase(APIClient client, String testCaseId) throws APIException, IOException {
        return (JSONObject) client.sendGet("get_attachments_for_case/" + testCaseId);
    }
}
