package de.qytera.qtaf.aws_devicefarm.driver;

import com.amazonaws.SdkClientException;
import de.qytera.qtaf.aws_devicefarm.config.AWSDeviceFarmConfigHelper;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import org.junit.Assert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class DriverTest {
    @Test(expectedExceptions = {SdkClientException.class})
    public void testAWSChromeDriver() {
        AWSChrome driver = new AWSChrome();
        Assert.assertEquals(driver.getName(), "aws-chrome");
        Assert.assertTrue(driver.isRemoteDriver());
        Assert.assertNotNull(driver.getCapabilities());
        WebDriver webDriver = driver.getDriver();
        Assert.assertNotNull(webDriver);
    }

    @Test(expectedExceptions = {SdkClientException.class})
    public void testAWSFirefoxDriver() {
        AWSFirefox driver = new AWSFirefox();
        Assert.assertEquals(driver.getName(), "aws-firefox");
        Assert.assertTrue(driver.isRemoteDriver());
        Assert.assertNotNull(driver.getCapabilities());
        WebDriver webDriver = driver.getDriver();
        Assert.assertNotNull(webDriver);
    }

    @Test(expectedExceptions = {SdkClientException.class})
    public void testAWSEdgeDriver() {
        AWSEdge driver = new AWSEdge();
        Assert.assertEquals(driver.getName(), "aws-edge");
        Assert.assertTrue(driver.isRemoteDriver());
        Assert.assertNotNull(driver.getCapabilities());
        WebDriver webDriver = driver.getDriver();
        Assert.assertNotNull(webDriver);
    }

    @Test(expectedExceptions = {SdkClientException.class})
    public void testAWSDriver() {
        ConfigMap config = QtafFactory.getConfiguration();
        String oldRegion = config.getString(AWSDeviceFarmConfigHelper.PROJECT_REGION);
        config.setString(AWSDeviceFarmConfigHelper.PROJECT_REGION, "eu-central");
        AbstractAWSDeviceFarmDriver driver = new AbstractAWSDeviceFarmDriver() {
            @Override
            public String getName() {
                return "aws";
            }

            @Override
            protected Capabilities getCapabilities() {
                return null;
            }

            @Override
            protected boolean isRemoteDriver() {
                return true;
            }
        };

        WebDriver d = driver.getDriver();
        Assert.assertTrue(d instanceof WebDriver);
    }
}
