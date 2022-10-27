package de.qytera.qtaf.core.io;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class FileHelperTest {
    /**
     * Test the getFileContent method of the FileHelper class. This method should return the contents of a file
     */
    @Test
    public void testReadFileContent() throws IOException {
        byte[] byteContent = FileHelper.getFileContent("$USER_DIR/src/test/resources/hello.txt");

        String actualStringContent = new String(byteContent);
        String expectedStringContent = "hello";

        Assert.assertEquals(actualStringContent, expectedStringContent);
    }

    /**
     * Test the getFileContent method of the FileHelper class. This method should return the contents of a file
     */
    @Test
    public void testReadFileContentAsUTF8String() throws IOException {
        String content = FileHelper.getFileContentAsUTF8String("$USER_DIR/src/test/resources/loremipsum.txt");
        String expectedContent = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

        Assert.assertEquals(content, expectedContent);
    }
}
