package de.qytera.qtaf.aws_devicefarm.driver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.edge.EdgeOptions;

public class AWSEdge extends AbstractAWSDeviceFarmDriver {

    @Override
    public String getName() {
        return "aws-edge";
    }

    @Override
    protected Capabilities getCapabilities() {
        EdgeOptions options = new EdgeOptions();
        options.setCapability("ms:edgeChromium", true);
        return options;
    }
}
