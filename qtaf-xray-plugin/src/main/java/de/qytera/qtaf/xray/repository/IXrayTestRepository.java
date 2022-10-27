package de.qytera.qtaf.xray.repository;

import de.qytera.qtaf.xray.dto.XrayTestDto;
import de.qytera.qtaf.xray.dto.XrayTestDtoCollection;

public interface IXrayTestRepository {
    XrayTestDto findByTestId(String testId);
    XrayTestDtoCollection findByTestSetId(String testSetId);
}
