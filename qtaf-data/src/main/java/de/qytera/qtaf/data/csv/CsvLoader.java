package de.qytera.qtaf.data.csv;

import de.qytera.qtaf.core.io.DirectoryHelper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * CSV loader class.
 */
public class CsvLoader {
    /**
     * File path.
     */
    protected String filePath = "";

    /**
     * Has headers flag.
     */
    protected boolean hasHeaders = true;

    /**
     * Header names array.
     */
    protected String[] headers = {};

    /**
     * Get CSV file path.
     *
     * @return CSV file path
     */
    protected String getFilePath() {
        return DirectoryHelper.preparePath(filePath);
    }

    /**
     * Set filePath.
     *
     * @param filePath FilePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Has headers flag.
     *
     * @return true if has headers, false otherwise
     */
    protected boolean hasHeaders() {
        return hasHeaders;
    }

    /**
     * Get CSV delimiter.
     *
     * @return CSV delimiter
     */
    protected char getDelimiter() {
        return ',';
    }

    /**
     * Get CSV headers.
     *
     * @return CSV headers
     */
    protected String[] getHeaders() {
        return headers;
    }

    /**
     * Get reader that allows reading a local file.
     *
     * @return reader
     * @throws FileNotFoundException File reading error
     */
    protected Reader getReader() throws FileNotFoundException {
        return new FileReader(getFilePath());
    }

}
