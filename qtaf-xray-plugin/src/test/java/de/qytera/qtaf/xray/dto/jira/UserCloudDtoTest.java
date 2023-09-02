package de.qytera.qtaf.xray.dto.jira;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

public class UserCloudDtoTest {

    @DataProvider
    private Object[][] userAccountTypeProvider() {
        return Arrays.stream(UserCloudDto.AccountType.values())
                .map(type -> new Object[]{type})
                .toArray(Object[][]::new);

    }

    @Test(dataProvider = "userAccountTypeProvider")
    public void testGetProjectTypeKey(UserCloudDto.AccountType type) {
        UserCloudDto dto = new UserCloudDto();
        Assert.assertNull(dto.getAccountType());
        dto.setAccountType(type);
        Assert.assertEquals(dto.getAccountType(), type);
    }
}