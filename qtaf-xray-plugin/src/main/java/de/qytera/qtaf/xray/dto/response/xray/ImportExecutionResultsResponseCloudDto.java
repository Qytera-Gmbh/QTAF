package de.qytera.qtaf.xray.dto.response.xray;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Response object returned from Xray Cloud when a test execution results import has been successful.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class ImportExecutionResultsResponseCloudDto implements ImportExecutionResultsResponseDto {
    /**
     * Xray test execution issue ID.
     */
    private String id;

    /**
     * Xray test execution issue key.
     */
    private String key;

    /**
     * A REST API endpoint for the test execution issue.
     */
    private String self;
}
