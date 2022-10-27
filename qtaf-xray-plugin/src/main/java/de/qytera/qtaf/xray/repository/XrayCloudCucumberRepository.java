package de.qytera.qtaf.xray.repository;

import com.sun.jersey.api.client.ClientResponse;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.core.net.http.HTTPDao;
import de.qytera.qtaf.xray.config.XrayRestPaths;
import de.qytera.qtaf.xray.service.AbstractXrayService;
import de.qytera.qtaf.xray.service.XrayCloudService;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Export Tests from Xray Server as Cucumber Feature File
 */
public class XrayCloudCucumberRepository implements IXrayCucumberRepository {
    /**
     * Logger
     */
    private static final Logger logger = QtafFactory.getLogger();

    /**
     * API Path for exporting tests as cucumber feature file (%s is replaced with semicolon-separated test IDs)
     */
    private static final String EXPORT_TEST_AS_CUCUMBER = "/export/cucumber?keys=%s";

    /**
     * Data Access Object for Xray API
     */
    private final HTTPDao httpDao = new HTTPDao(XrayRestPaths.XRAY_CLOUD_API_V1);


    @Override
    public String getFeatureFileDefinition(String[] testIDs) {
        String keys = StringUtils.join(testIDs, ";");
        authenticateAgainstXrayAPI();

        try {
            ZipInputStream zis = getZipInputStreamFromAPIResponse(keys);
            zis.getNextEntry();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            for (int content = zis.read(); content != -1; content = zis.read()) {
                outputStream.write(content);
            }

            zis.closeEntry();
            zis.close();

            String content = outputStream.toString(StandardCharsets.UTF_8);
            outputStream.close();
            return content;
        } catch (Exception e) {
            logger.error(e);
            return "";
        }
    }

    /**
     * Authenticate against Xray API
     */
    private void authenticateAgainstXrayAPI() {
        // Authenticate against Xray API
        AbstractXrayService service = XrayCloudService.getInstance();
        service.authenticate();
        httpDao.setAuthorizationHeaderValue("Bearer " + service.getJwtToken());
    }

    /**
     * Send HTTP request to API, get ZIP file as a response and transform it to a ZipInputStream object
     * @param keys  Test keys
     * @return      ZIP content as ZipInputStream
     */
    private ZipInputStream getZipInputStreamFromAPIResponse(String keys) {
        ClientResponse res = httpDao.get(String.format(EXPORT_TEST_AS_CUCUMBER, keys), "application/zip");
        InputStream inputStream = res.getEntity(InputStream.class);
        return new ZipInputStream(inputStream);
    }

    /**
     * Get feature files from Xray Cloud API by Test(Set) IDs
     * @param testIDs   Array of Test IDs
     * @return          Array of feature file contents. Xray will return a ZIP file with multiple files in it.
     *                  This methods extracts all files and saves them in a string array which is returned
     * @throws IOException  Error during ZIP file extraction
     */
    public ArrayList<String> getFeatureFileDefinitions(String[] testIDs) throws IOException {
        String keys = StringUtils.join(testIDs, ";");
        ArrayList<String> features = new ArrayList<>();

        // Authenticate against Xray API
        authenticateAgainstXrayAPI();

        // Download ZIP file
        ZipInputStream zis = getZipInputStreamFromAPIResponse(keys);

        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            for (int content = zis.read(); content != -1; content = zis.read()) {
                outputStream.write(content);
            }

            zis.closeEntry();

            features.add(outputStream.toString(StandardCharsets.UTF_8));
            outputStream.close();
        }

        zis.close();

        return features;
    }

    /**
     * Get feature files from Xray Cloud API by Test(Set) IDs and store them in files
     * @param testIDs   Array of Test IDs
     * @param dir       Name of directory where to store downloaded feature files
     * @throws IOException  Error during ZIP file extraction
     */
    public void getAndStoreFeatureFileDefinitions(String[] testIDs, String dir) throws IOException {
        String keys = StringUtils.join(testIDs, ";");

        // Authenticate against Xray API
        authenticateAgainstXrayAPI();

        // Download ZIP file
        ZipInputStream zis = getZipInputStreamFromAPIResponse(keys);

        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            FileOutputStream outputStream = new FileOutputStream(DirectoryHelper.preparePath(dir + "/" + ze.getName()));

            for (int content = zis.read(); content != -1; content = zis.read()) {
                outputStream.write(content);
            }

            zis.closeEntry();

            outputStream.close();
        }

        zis.close();
    }
}
