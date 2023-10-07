package de.qytera.qtaf.testrail.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * A manager class for keeping track of and uploading executed tests.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestRailManager {
    /**
     * Add the result of an executed test case.
     *
     * @param client  the TestRail API client
     * @param caseId  the TestRail case ID
     * @param runId   the TestRail run ID
     * @param status  the test status
     * @param comment any additional comment
     * @throws APIException if something goes wrong during upload
     */
    public static void addResultForTestCase(APIClient client, String caseId, String runId, int status, String comment) throws APIException, IOException {
        var data = new HashMap<>();
        data.put("status_id", status);
        data.put("comment", comment);
        client.sendPost("add_result_for_case/" + runId + "/" + caseId, data);
    }

    /**
     * Add an attachment to a test case.
     *
     * @param client     the TestRail API client
     * @param testCaseId the test case ID
     * @param path       the attachment file path
     * @throws APIException if something goes wrong during upload
     * @throws IOException  if the file cannot be accessed
     */
    public static void addAttachmentForTestCase(APIClient client, String testCaseId, String path) throws APIException, IOException {
        client.sendPost("add_attachment_to_case/" + testCaseId, path);
    }

    /**
     * Delete an attachment.
     *
     * @param client       the TestRail API client
     * @param attachmentId the attachment
     * @throws APIException if something goes wrong during deletion
     */
    public static void deleteAttachmentForTestCase(APIClient client, String attachmentId) throws APIException, IOException {
        client.sendPost("delete_attachment/" + attachmentId, "");
    }

    /**
     * Get all attachments for a test case.
     *
     * @param client     the TestRail API client.
     * @param testCaseId the test case ID
     * @return all attachments of the test case
     * @throws APIException if something goes wrong during attachment retrieval
     */
    public static JSONObject getAttachmentsForTestCase(APIClient client, String testCaseId) throws APIException, IOException {
        return (JSONObject) client.sendGet("get_attachments_for_case/" + testCaseId);
    }
}
