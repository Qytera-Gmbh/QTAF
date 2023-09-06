package de.qytera.qtaf.aws_devicefarm.driver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * An AWS Firefox driver.
 */
public class AWSFirefox extends AbstractAWSDeviceFarmDriver {

    @Override
    public String getName() {
        return "aws-firefox";
    }

    @Override
    protected Capabilities getCapabilities() {
        return new FirefoxOptions();
    }

    @Override
    protected boolean isRemoteDriver() {
        return true;
    }
}
