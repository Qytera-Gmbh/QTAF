package de.qytera.qtaf.xray.repository.xray;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.http.RequestBuilder;
import de.qytera.qtaf.http.WebService;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Export Tests from Xray Server as Cucumber Feature File
 */
public class XrayCucumberRepositoryCloud implements XrayCucumberRepository, XrayEndpoint {
    /**
     * Logger
     */
    private static final Logger logger = QtafFactory.getLogger();


    @Override
    public String getFeatureFileDefinition(String[] testIDs) {
        String keys = StringUtils.join(testIDs, ";");

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
     * Send HTTP request to API, get ZIP file as a response and transform it to a ZipInputStream object
     *
     * @param keys Test keys
     * @return ZIP content as ZipInputStream
     */
    private ZipInputStream getZipInputStreamFromAPIResponse(String keys) throws URISyntaxException, MissingConfigurationValueException {
        RequestBuilder request = WebService.buildRequest(getURIExportFeatureFiles(keys));
        request.getBuilder()
                .header(HttpHeaders.AUTHORIZATION, getXrayAuthorizationHeaderValue())
                .accept("application/zip");
        try (Response response = WebService.get(request)) {
            InputStream inputStream = response.readEntity(InputStream.class);
            return new ZipInputStream(inputStream);
        }
    }

    private URI getURIExportFeatureFiles(String keys) throws URISyntaxException {
        return new URI(
                String.format(
                        "%s/export/cucumber?keys=%s",
                        getXrayURL(),
                        keys
                )
        );
    }

    /**
     * Get feature files from Xray Cloud API by Test(Set) IDs
     *
     * @param testIDs Array of Test IDs
     * @return list of feature file contents. Xray will return a ZIP file with multiple files in it.
     * This methods extracts all files and saves them in a string array which is returned
     * @throws IOException Error during ZIP file extraction
     */
    public List<String> getFeatureFileDefinitions(String[] testIDs) throws IOException, URISyntaxException, MissingConfigurationValueException {
        String keys = StringUtils.join(testIDs, ";");
        ArrayList<String> features = new ArrayList<>();

        // Download ZIP file
        ZipInputStream zis = getZipInputStreamFromAPIResponse(keys);

        while (zis.getNextEntry() != null) {
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
     *
     * @param testIDs Array of Test IDs
     * @param dir     Name of directory where to store downloaded feature files
     * @throws IOException Error during ZIP file extraction
     */
    public void getAndStoreFeatureFileDefinitions(String[] testIDs, String dir) throws IOException, URISyntaxException, MissingConfigurationValueException {
        String keys = StringUtils.join(testIDs, ";");

        // Download ZIP file
        ZipInputStream zis = getZipInputStreamFromAPIResponse(keys);

        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            try (FileOutputStream outputStream = new FileOutputStream(DirectoryHelper.preparePath(dir + "/" + ze.getName()))) {

                for (int content = zis.read(); content != -1; content = zis.read()) {
                    outputStream.write(content);
                }
                zis.closeEntry();
            }
        }

        zis.close();
    }
}
