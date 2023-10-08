package de.qytera.qtaf.testrail.utils;

import de.qytera.qtaf.core.QtafFactory;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.function.Consumer;

/**
 * Utility class for handling TestRail multipart attachments.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class APIUtil {

    /**
     * Converts a file into a multipart attachment entity and passes the entity to the provided consumer.
     *
     * @param filePath           the file to attach
     * @param attachmentConsumer the consumer using the attachment entity
     * @throws ParseException if the file cannot be processed
     * @see <a href="https://support.testrail.com/hc/en-us/articles/7077039051284#example-attachment-upload-0-3">TestRail documentation</a>
     */
    public static void consumeAttachment(String filePath, Consumer<Entity<MultiPart>> attachmentConsumer) throws ParseException {
        try (MultiPart multiPartEntity = new FormDataMultiPart()) {
            FileDataBodyPart filePart = new FileDataBodyPart("attachment", new File(filePath));
            // Testrail only accepts content-dispositions of the following form:
            // Content-Disposition: form-data; name="attachment"; filename="..."
            // See: https://github.com/gurock/testrail-api/blob/0320205d86f223415db3e8a301b9a534202ff820/java/com/gurock/testrail/APIClient.java#L166
            filePart.setFormDataContentDisposition(new FormDataContentDisposition(
                    "form-data; name=\"attachment\"; filename=\"%s\"".formatted(filePart.getFileEntity().getName()))
            );
            multiPartEntity.bodyPart(filePart);
            Entity<MultiPart> entity = Entity.entity(multiPartEntity, MediaType.MULTIPART_FORM_DATA);
            attachmentConsumer.accept(entity);
        } catch (IOException e) {
            QtafFactory.getLogger().error(e);
        }
    }

}
