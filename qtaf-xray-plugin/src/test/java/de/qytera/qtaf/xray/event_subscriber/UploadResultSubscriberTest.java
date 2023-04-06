package de.qytera.qtaf.xray.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.payload.QtafTestContextPayload;
import de.qytera.qtaf.xray.commands.AuthenticationCommand;
import de.qytera.qtaf.xray.commands.UploadImportCommand;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.dto.response.XrayCloudImportResponseDto;
import de.qytera.qtaf.xray.dto.response.XrayImportResponseDto;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;


public class UploadResultSubscriberTest {
    AuthenticationCommand authenticationCommand = new AuthenticationCommand();
    XrayImportRequestDto xrayImportRequestDto = new XrayImportRequestDto();
    XrayImportResponseDto xrayImportResponseDto = new XrayCloudImportResponseDto();
    UploadResultsSubscriber uploadResultsSubscriber = new UploadResultsSubscriber();

    //@Mock
    UploadImportCommand uploadImportCommand;


    //@BeforeTest
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Initialize Mock objects
     *
     * @param xrayEnabled Xray enabled / disabled configuration
     */
    private void initializeMocks(boolean xrayEnabled) {
        // Set command object of subscriber object
        Whitebox.setInternalState(uploadResultsSubscriber, "uploadImportCommand", uploadImportCommand);

        // Set attributes of upload command
        Whitebox.setInternalState(uploadImportCommand, "authenticationCommand", authenticationCommand);
        Whitebox.setInternalState(uploadImportCommand, "xrayImportRequestDto", xrayImportRequestDto);
        Whitebox.setInternalState(uploadImportCommand, "xrayImportResponseDto", xrayImportResponseDto);

        // Mock methods
        Mockito.when(uploadImportCommand.setXrayImportRequestDto(Mockito.anyObject())).thenReturn(uploadImportCommand);
        Mockito.when(uploadImportCommand.getXrayImportResponseDto()).thenReturn(xrayImportResponseDto);

        // Activate Xray Plugin
        ConfigMap configMap = QtafFactory.getConfiguration();
        configMap.setBoolean("xray.enabled", xrayEnabled);
    }

    //@Test
    public void testUploadSubscriberEnabled() {
        // Set configuration
        initializeMocks(true);

        // Initialize event subscriber
        uploadResultsSubscriber.initialize();

        // Dispatch Event
        QtafEvents.finishedTesting.onNext(new QtafTestContextPayload());

        // Upload Import Command should be called one time
        Mockito.verify(uploadImportCommand, Mockito.times(1)).execute();
    }

    //@Test
    public void testUploadSubscriberDisabled() {
        // Set configuration
        initializeMocks(false);

        // Initialize event subscriber
        uploadResultsSubscriber.initialize();

        // Dispatch Event
        QtafEvents.finishedTesting.onNext(new QtafTestContextPayload());

        // Upload Import Command should be called zero times
        Mockito.verify(uploadImportCommand, Mockito.times(0)).execute();
    }
}
