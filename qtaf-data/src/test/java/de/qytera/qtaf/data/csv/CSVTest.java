package de.qytera.qtaf.data.csv;

import org.testng.annotations.Test;

import java.io.IOException;

public class CSVTest {

    /**
     * Test if CSV file gets loaded correctly
     */
    @Test
    public void testLoadCSVFile() throws IOException {
        CsvLoader loader = new CsvLoader();
        loader.setFilePath("$USER_DIR/src/test/resources/credentials.csv");
    }
}
