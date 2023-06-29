package de.qytera.qtaf.aws_devicefarm.driver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;

public class AWSChrome extends AbstractAWSDeviceFarmDriver {

    @Override
    public String getName() {
        return "aws-chrome";
    }

    @Override
    protected Capabilities getCapabilities() {
        return new ChromeOptions();
    }
}
