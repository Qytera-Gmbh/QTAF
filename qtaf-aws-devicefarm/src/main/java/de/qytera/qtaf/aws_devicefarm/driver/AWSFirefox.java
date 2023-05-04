package de.qytera.qtaf.aws_devicefarm.driver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxOptions;

public class AWSFirefox extends AbstractAWSDeviceFarmDriver {

    @Override
    public String getName() {
        return "aws-firefox";
    }

    @Override
    protected Capabilities getCapabilities() {
        return new FirefoxOptions();
    }
}
