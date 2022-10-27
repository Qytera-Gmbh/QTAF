package de.qytera.qtaf.core.io;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class DirectoryHelperTest {
    @Test
    public void testDirectoryHelper() throws IOException {
        String fs = System.getProperty("file.separator");
        String dir = System.getProperty("user.dir") + fs + "foo";

        File file = new File(dir);

        // Directory should not exist
        Assert.assertFalse(file.exists());

        // Create directory
        DirectoryHelper.createDirectoryIfNotExists(dir);
        Assert.assertTrue(file.exists());

        // Delete directory
        Assert.assertTrue(DirectoryHelper.deleteDirectory(dir));
        Assert.assertFalse(file.exists());
    }

    @Test
    public void testPreparePath() {
        Assert.assertEquals(
                DirectoryHelper.preparePath("$USER_DIR"),
                System.getProperty("user.dir")
        );

        Assert.assertEquals(
                DirectoryHelper.preparePath("$USER_HOME"),
                System.getProperty("user.home")
        );

        Assert.assertEquals(
                DirectoryHelper.preparePath("$USER_NAME"),
                System.getProperty("user.name")
        );

        String fs = System.getProperty("file.separator");

        Assert.assertEquals(
                DirectoryHelper.preparePath("a/b\\c"),
                "a" + fs + "b" + fs + "c"
        );
    }
}
