package de.qytera.qtaf.testrail.utils;

import com.google.common.net.HttpHeaders;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.testrail.entity.Attachments;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;

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
    public static void addResultForTestCase(APIClient client, String caseId, String runId, int status, String comment) throws APIException {
        JsonObject body = new JsonObject();
        body.add("status_id", new JsonPrimitive(status));
        body.add("comment", new JsonPrimitive(comment));
        RequestBuilder request = WebService.buildRequest(URI.create(client.getUrl() + "add_result_for_case/" + runId + "/" + caseId));
        request.getBuilder().header(HttpHeaders.AUTHORIZATION, client.getAuthorizationHeader());
        try (Response response = WebService.post(request, Entity.json(body))) {
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String content = response.readEntity(String.class);
                throw new APIException(response.getStatus(), content);
            }
        }
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
        try (MultiPart multiPartEntity = new FormDataMultiPart()) {
            FileDataBodyPart filePart = new FileDataBodyPart("attachment", new File(path));
            // Testrail only accepts content-dispositions of the following form:
            // Content-Disposition: form-data; name="attachment"; filename="..."
            // See: https://github.com/gurock/testrail-api/blob/0320205d86f223415db3e8a301b9a534202ff820/java/com/gurock/testrail/APIClient.java#L166
            filePart.setFormDataContentDisposition(new FormDataContentDisposition(
                    "form-data; name=\"attachment\"; filename=\"%s\"".formatted(filePart.getFileEntity().getName()))
            );
            multiPartEntity.bodyPart(filePart);
            RequestBuilder request = WebService.buildRequest(
                    URI.create("%s/add_attachment_to_case/%s".formatted(client.getUrl(), testCaseId))
            );
            request.getBuilder().header(HttpHeaders.AUTHORIZATION, client.getAuthorizationHeader());
            try (Response response = WebService.post(request, Entity.entity(multiPartEntity, MediaType.MULTIPART_FORM_DATA))) {
                if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                    String content = response.readEntity(String.class);
                    throw new APIException(response.getStatus(), content);
                }
            }
        } catch (ParseException e) {
            QtafFactory.getLogger().error("[QTAF TestRail Plugin] Failed to read attachment: %s", path);
            QtafFactory.getLogger().error(e);
        }
    }

    /**
     * Delete an attachment.
     *
     * @param client       the TestRail API client
     * @param attachmentId the attachment
     * @throws APIException if something goes wrong during deletion
     */
    public static void deleteAttachmentForTestCase(APIClient client, String attachmentId) throws APIException {
        RequestBuilder request = WebService.buildRequest(URI.create(client.getUrl() + "delete_attachment/" + attachmentId));
        request.getBuilder().header(HttpHeaders.AUTHORIZATION, client.getAuthorizationHeader());
        try (Response response = WebService.post(request)) {
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String content = response.readEntity(String.class);
                throw new APIException(response.getStatus(), content);
            }
        }
    }

    /**
     * Get all attachments for a test case.
     *
     * @param client     the TestRail API client.
     * @param testCaseId the test case ID
     * @return all attachments of the test case
     * @throws APIException if something goes wrong during attachment retrieval
     */
    public static Attachments getAttachmentsForTestCase(APIClient client, String testCaseId) throws APIException {
        RequestBuilder request = WebService.buildRequest(URI.create(client.getUrl() + "get_attachments_for_case/" + testCaseId));
        request.getBuilder().header(HttpHeaders.AUTHORIZATION, client.getAuthorizationHeader());
        try (Response response = WebService.get(request)) {
            String content = response.readEntity(String.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new APIException(response.getStatus(), content);
            } else {
                return GsonFactory.getInstance().fromJson(content, Attachments.class);
            }
        }
    }
}
