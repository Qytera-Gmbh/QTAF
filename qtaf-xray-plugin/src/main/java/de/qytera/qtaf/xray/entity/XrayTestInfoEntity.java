package de.qytera.qtaf.xray.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray test info entity.
 *
 * @see <a href="https://docs.getxray.app/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONSchema">Xray JSON format</a>
 */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class XrayTestInfoEntity {
    /**
     * The summary for the test issue.
     */
    @NonNull
    private String summary;
    /**
     * The project key where the test issue will be created.
     */
    @NonNull
    private String projectKey;
    /**
     * An array of requirement issue keys to associate with the test.
     */
    private List<String> requirementKeys = new ArrayList<>();
    /**
     * The test issue labels.
     */
    private List<String> labels = new ArrayList<>();
    /**
     * An array of test steps (for Manual tests).
     */
    private List<XrayTestStepEntity> steps = new ArrayList<>();
    /**
     * The BDD scenario.
     */
    private String scenario;
    /**
     * The generic test definition.
     */
    private String definition;
}
