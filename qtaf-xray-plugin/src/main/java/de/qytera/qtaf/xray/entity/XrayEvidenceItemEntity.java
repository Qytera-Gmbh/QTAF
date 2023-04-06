package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.util.Base64Helper;
import de.qytera.qtaf.xray.error.EvidenceUploadError;

import javax.activation.FileTypeMap;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Xray Evidence Entity
 */
public class XrayEvidenceItemEntity {
    /**
     * Base64 encoded content of file
     */
    private String data;

    /**
     * Filename
     */
    private String filename;

    /**
     * MIME type of file
     */
    private String contentType;


    /**
     * Transforms the provided file into an evidence entity.
     *
     * @param filepath the path to the file
     * @return a corresponding evidence entity
     */
    public static XrayEvidenceItemEntity fromFile(String filepath) {
        return fromFile(filepath, null);
    }

    /**
     * Transforms the provided file into an evidence entity. If {@code evidenceName} is {@code null}, the name of the
     * provided file will be used as name. Otherwise, the provided name will be used.
     *
     * @param filepath     the path to the file
     * @param evidenceName an optional name to assign to the evidence
     * @return a corresponding evidence entity
     */
    public static XrayEvidenceItemEntity fromFile(String filepath, String evidenceName) {
        try {
            Path path = Paths.get(filepath);
            String mimeType = FileTypeMap.getDefaultFileTypeMap().getContentType(path.toFile());
            return new XrayEvidenceItemEntity()
                    .setFilename(evidenceName == null ? path.getFileName().toString() : evidenceName)
                    .setContentType(mimeType)
                    .setData(Base64Helper.encodeFileContent(path.toAbsolutePath().toString()));
        } catch (IOException | NullPointerException e) {
            EvidenceUploadError error = new EvidenceUploadError(e)
                    .setFilepath(filepath);
            ErrorLogCollection.getInstance().addErrorLog(error);
        }
        return null;
    }

    /**
     * Transforms the provided {@code String} data into an evidence entity.
     *
     * @param data         the data to convert into evidence
     * @param evidenceName the name to assign to the evidence
     * @return a corresponding evidence entity
     */
    public static XrayEvidenceItemEntity fromString(String data, String evidenceName) {
        try {
            String mimeType = FileTypeMap.getDefaultFileTypeMap().getContentType(data);
            return new XrayEvidenceItemEntity()
                    .setFilename(evidenceName)
                    .setContentType(mimeType)
                    .setData(Base64Helper.encode(data));
        } catch (NullPointerException e) {
            ErrorLogCollection.getInstance().addErrorLog(new EvidenceUploadError(e));
        }
        return null;
    }

    /**
     * Get data
     *
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * Set data
     *
     * @param data Data
     * @return this
     */
    public XrayEvidenceItemEntity setData(String data) {
        this.data = data;
        return this;
    }

    /**
     * Get filename
     *
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Set filename
     *
     * @param filename Filename
     * @return this
     */
    public XrayEvidenceItemEntity setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * Get contentType
     *
     * @return contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Set contentType
     *
     * @param contentType ContentType
     * @return this
     */
    public XrayEvidenceItemEntity setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
}
