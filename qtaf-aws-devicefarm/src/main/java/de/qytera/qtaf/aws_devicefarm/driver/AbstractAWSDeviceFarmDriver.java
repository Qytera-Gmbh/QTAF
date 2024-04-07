package de.qytera.qtaf.aws_devicefarm.driver;

import com.amazonaws.services.devicefarm.AWSDeviceFarm;
import com.amazonaws.services.devicefarm.AWSDeviceFarmClient;
import com.amazonaws.services.devicefarm.AWSDeviceFarmClientBuilder;
import com.amazonaws.services.devicefarm.model.CreateTestGridUrlRequest;
import com.amazonaws.services.devicefarm.model.CreateTestGridUrlResult;
import de.qytera.qtaf.aws_devicefarm.config.AWSDeviceFarmConfigHelper;
import de.qytera.qtaf.core.selenium.AbstractDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Base class that all AWS DeviceFarm drivers should extend.
 */
public abstract class AbstractAWSDeviceFarmDriver extends AbstractDriver {
    @Override
    public WebDriver getDriver() {
        String myProjectARN = AWSDeviceFarmConfigHelper.getProjectArn();
        AWSDeviceFarmClientBuilder builder = AWSDeviceFarmClient.builder();
        builder.setRegion(AWSDeviceFarmConfigHelper.getProjectRegion());

        AWSDeviceFarm client = builder.build();
        CreateTestGridUrlRequest request = new CreateTestGridUrlRequest();
        request.setExpiresInSeconds(300);
        request.setProjectArn(myProjectARN);
        CreateTestGridUrlResult response = getTestGridUrl(client, request);
        URL testGridUrl = null;
        try {
            testGridUrl = new URL(response.getUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        // You can now pass this URL into RemoteWebDriver.
        return new RemoteWebDriver(testGridUrl, getCapabilities());
    }

    /**
     * Gets the capabilities for the remote driver instance.
     *
     * @return the capabilities
     */
    protected abstract Capabilities getCapabilities();

    /**
     * Get the AWS device farm grid URL.
     *
     * @param client  the device farm client
     * @param request the request to send
     * @return the result
     */
    public CreateTestGridUrlResult getTestGridUrl(AWSDeviceFarm client, CreateTestGridUrlRequest request) {
        return client.createTestGridUrl(request);
    }
}
