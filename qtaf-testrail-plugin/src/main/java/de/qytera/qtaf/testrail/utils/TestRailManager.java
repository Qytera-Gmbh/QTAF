package de.qytera.qtaf.testrail.utils;

import com.google.common.net.HttpHeaders;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import de.qytera.qtaf.testrail.entity.Attachments;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.EntityPart;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestRailManager {
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

    public static void addAttachmentForTestCase(APIClient client, String testCaseId, String path) throws APIException, IOException {
        File file = new File(path);
        List<EntityPart> parts = new ArrayList<>();
        parts.add(EntityPart.withName("attachment")
                .fileName(file.getName())
                .content(new FileInputStream(file))
                .build());
        RequestBuilder request = WebService.buildRequest(URI.create(client.getUrl() + "add_attachment_to_case/" + testCaseId));
        request.getBuilder().header(HttpHeaders.AUTHORIZATION, client.getAuthorizationHeader());
        try (Response response = WebService.post(request, Entity.entity(parts, MediaType.MULTIPART_FORM_DATA))) {
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                String content = response.readEntity(String.class);
                throw new APIException(response.getStatus(), content);
            }
        }
    }

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
