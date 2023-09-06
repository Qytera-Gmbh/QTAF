package de.qytera.qtaf.xray.dto.response.steps;

import de.qytera.qtaf.xray.dto.response.graphql.GraphQLResponseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a response when querying the {@code getTests} method of Xray Cloud's GraphQL endpoint.
 *
 * @see <a href="https://xray.cloud.getxray.app/doc/graphql/gettests.doc.html">Xray GraphQL documentation</a>
 */
@Getter
@Setter
public class XrayCloudTestStepResponseDto extends GraphQLResponseDto<XrayCloudTestStepResponseDto.ResponseData> {

    /**
     * The step response data.
     */
    @Getter
    @Setter
    public static class ResponseData {

        private TestsData getTests;

        /**
         * The test data.
         */
        @Getter
        @Setter
        public static class TestsData {
            private int total;
            private int start;
            private int limit;
            private Result[] results;

            /**
             * The result data.
             */
            @Getter
            @Setter
            public static class Result {
                private String issueId;
                private TestType testType;
                private Step[] steps;

                /**
                 * The test type.
                 */
                @Getter
                @Setter
                public static class TestType {
                    private String name;
                    private String kind;
                }

                /**
                 * The step data.
                 */
                @Getter
                @Setter
                public static class Step implements XrayTestStepResponseDto {
                    private String id;
                    private String data;
                    private String action;
                    private String result;
                }

            }

        }

    }

}
