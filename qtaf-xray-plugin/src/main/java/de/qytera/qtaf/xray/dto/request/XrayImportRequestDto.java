package de.qytera.qtaf.xray.dto.request;

import de.qytera.qtaf.xray.entity.XrayTestEntity;
import de.qytera.qtaf.xray.entity.XrayTestExecutionInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Xray import request DTO. This class represents the JSON structure that Xray API expects when importing tests.
 */
public class XrayImportRequestDto {
    /**
     * Tests
     */
    private final List<XrayTestEntity> tests = new ArrayList<>();

    /**
     * Test EXecution Info Entity
     */
    private XrayTestExecutionInfoEntity info;

    /**
     * Get tests
     *
     * @return tests
     */
    public List<XrayTestEntity> getTests() {
        return tests;
    }

    /**
     * Add test to list
     * @param xrayTestEntity  xray test entity
     * @return          this
     */
    public XrayImportRequestDto addTest(XrayTestEntity xrayTestEntity) {
        this.tests.add(xrayTestEntity);
        return this;
    }

    /**
     * Get info
     *
     * @return info
     */
    public XrayTestExecutionInfoEntity getInfo() {
        return info;
    }

    /**
     * Set info
     *
     * @param info Info
     * @return this
     */
    public XrayImportRequestDto setInfo(XrayTestExecutionInfoEntity info) {
        this.info = info;
        return this;
    }
}
