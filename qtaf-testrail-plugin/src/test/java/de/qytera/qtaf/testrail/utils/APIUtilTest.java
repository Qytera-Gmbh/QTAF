package de.qytera.qtaf.testrail.utils;

import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;

public class APIUtilTest {

    @Test
    public void testToMultipartAttachmentMediaType() throws ParseException {
        APIUtil.consumeAttachment("pom.xml", entity ->
                Assert.assertEquals(entity.getMediaType(), MediaType.MULTIPART_FORM_DATA_TYPE)
        );
    }

    @Test
    public void testToMultipartAttachmentBodyPartSize() throws ParseException {
        APIUtil.consumeAttachment("pom.xml", entity -> Assert.assertEquals(entity.getEntity().getBodyParts().size(), 1));
    }

    @Test
    public void testToMultipartAttachmentBodyPartContentDisposition() throws ParseException {
        APIUtil.consumeAttachment("pom.xml", entity -> {
            Assert.assertEquals(entity.getEntity().getBodyParts().get(0).getContentDisposition().getClass(), FormDataContentDisposition.class);
            if (entity.getEntity().getBodyParts().get(0).getContentDisposition() instanceof FormDataContentDisposition contentDisposition) {
                Assert.assertEquals(contentDisposition.getType(), "form-data");
                Assert.assertEquals(contentDisposition.getFileName(), "pom.xml");
                Assert.assertEquals(contentDisposition.getName(), "attachment");
                Assert.assertNull(contentDisposition.getCreationDate());
                Assert.assertNull(contentDisposition.getReadDate());
                Assert.assertNull(contentDisposition.getModificationDate());
                Assert.assertEquals(contentDisposition.getSize(), -1);
            } else {
                Assert.fail(
                        "Expected the attachment's content disposition to be an instance of %s, but it is: %s".formatted(
                                FormDataContentDisposition.class,
                                entity.getEntity().getBodyParts().get(0).getContentDisposition().getClass()
                        )
                );
            }
        });
    }
}