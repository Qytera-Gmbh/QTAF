package de.qytera.qtaf.xray.dto.jira;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

public class ProjectDetailsDtoTest {

    @DataProvider
    private Object[][] projectTypeProvider() {
        return Arrays.stream(ProjectDetailsDto.ProjectType.values())
                .map(type -> new Object[]{type})
                .toArray(Object[][]::new);

    }

    @Test(dataProvider = "projectTypeProvider")
    public void testGetProjectTypeKey(ProjectDetailsDto.ProjectType type) {
        ProjectDetailsDto dto = new ProjectDetailsDto();
        Assert.assertNull(dto.getProjectTypeKey());
        dto.setProjectTypeKey(type);
        Assert.assertEquals(dto.getProjectTypeKey(), type);
    }
}