package de.qytera.qtaf.xray.entity;

import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.util.Base64Helper;
import de.qytera.qtaf.xray.error.EvidenceUploadError;
import jakarta.activation.FileTypeMap;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Xray evidence item entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray Server JSON format</a>
 * @see <a href="https://docs.getxray.app/display/XRAYCLOUD/Using+Xray+JSON+format+to+import+execution+results">Xray Cloud JSON format</a>
 */
@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
public class XrayEvidenceItemEntity {
    /**
     * The attachment data encoded in <b>base64</b>.
     */
    private final String data;
    /**
     * The file name for the attachment.
     */
    private final String filename;
    /**
     * The Content-Type representation header is used to indicate the original media type of the resource.
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
            return XrayEvidenceItemEntity.builder()
                    .filename(evidenceName == null ? path.getFileName().toString() : evidenceName)
                    .data(Base64Helper.encodeFileContent(path.toAbsolutePath().toString()))
                    .contentType(mimeType)
                    .build();
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
            return XrayEvidenceItemEntity.builder()
                    .filename(evidenceName)
                    .data(Base64Helper.encode(data))
                    .contentType(mimeType)
                    .build();
        } catch (NullPointerException e) {
            ErrorLogCollection.getInstance().addErrorLog(new EvidenceUploadError(e));
        }
        return null;
    }

}
