package de.qytera.qtaf.xray.dto.response.xray;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Response object returned from Xray Server when a test execution results import has been successful.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class ImportExecutionResultsResponseServerDto implements ImportExecutionResultsResponseDto {

    private TestExecutionIssueDto testExecIssue;

    @Override
    public String getId() {
        return testExecIssue.getId();
    }

    @Override
    public String getKey() {
        return testExecIssue.getKey();
    }

    @Override
    public String getSelf() {
        return testExecIssue.getSelf();
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class TestExecutionIssueDto {
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

}
