package de.qytera.qtaf.aws_devicefarm.config;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AWSDeviceFarmConfigHelperTest {
    @Test(testName = "Test project arn configuration")
    public void testAWSConfigProjectArn() {
        ConfigMap config = QtafFactory.getConfiguration();
        String oldValue = config.getString(AWSDeviceFarmConfigHelper.PROJECT_ARN);
        config.setString(AWSDeviceFarmConfigHelper.PROJECT_ARN, "abc123");
        Assert.assertEquals(AWSDeviceFarmConfigHelper.getProjectArn(), "abc123");
        config.setString(AWSDeviceFarmConfigHelper.PROJECT_ARN, oldValue);
    }

    @Test(testName = "Test project region configuration")
    public void testAWSConfigProjectRegion() {
        ConfigMap config = QtafFactory.getConfiguration();
        String oldValue = config.getString(AWSDeviceFarmConfigHelper.PROJECT_REGION);
        config.setString(AWSDeviceFarmConfigHelper.PROJECT_REGION, "us-west-1");
        Assert.assertEquals(AWSDeviceFarmConfigHelper.getProjectRegion(), "us-west-1");
        config.setString(AWSDeviceFarmConfigHelper.PROJECT_REGION, oldValue);
    }
}
