package de.qytera.qtaf.xray.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.payload.IQtafTestingContext;
import de.qytera.qtaf.xray.builder.XrayJsonImportBuilder;
import de.qytera.qtaf.xray.commands.UploadImportCommand;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.dto.response.XrayCloudImportResponseDto;
import de.qytera.qtaf.xray.dto.response.XrayImportResponseDto;
import de.qytera.qtaf.xray.dto.response.XrayServerImportResponseDto;
import de.qytera.qtaf.xray.events.QtafXrayEvents;
import de.qytera.qtaf.core.log.Logger;
import rx.Subscription;

/**
 * Event subscriber that uploads test results to Xray API
 */
public class UploadResultsSubscriber implements IEventSubscriber {
    /**
     * Indicates if tests were uploaded
     */
    private static boolean uploaded = false;

    /**
     * Qtaf configuration
     */
    private static final ConfigMap config = QtafFactory.getConfiguration();

    /**
     * Event subscription
     */
    private Subscription testFinishedSubscription;

    /**
     * Command to upload test results to Xray API
     */
    private UploadImportCommand uploadImportCommand;

    /**
     * Logger
     */
    private static final Logger logger = QtafFactory.getLogger();

    /**
     * Builder object that translates a QTAF Log Object to an Xray Import DTO object
     */
    private final XrayJsonImportBuilder xrayJsonImportBuilder = new XrayJsonImportBuilder();

    @Override
    public void initialize() {
        // Check if there already is a subscription
        if (testFinishedSubscription != null) {
            return;
        }

        if (uploadImportCommand == null) {
            uploadImportCommand = new UploadImportCommand();
        }

        // Subscribe to tests finished subject
        this.testFinishedSubscription = QtafEvents.finishedTesting.subscribe(this::onTestFinished);
    }

    /**
     * Method that gets executed when testing has finished
     * @param testContext   Test context payload
     */
    public void onTestFinished(IQtafTestingContext testContext) {
        // Check if Xray Plugin is enabled
        if (!config.getBoolean("xray.enabled")) {
            return;
        }

        // Check if result was already uploaded
        if (uploaded) {
            return;
        }

        logger.info("[QTAF Xray Plugin] Uploading Xray results ...");

        // Build Request DTO for Xray API
        XrayImportRequestDto xrayImportRequestDto = xrayJsonImportBuilder.buildFromTestSuiteLogs(
                QtafFactory.getTestSuiteLogCollection()
        );

        // Dispatch Event for Import DTO
        QtafXrayEvents.importDtoCreated.onNext(xrayImportRequestDto);

        // Upload test execution data
        try {
            uploadImportCommand
                    .setXrayImportRequestDto(xrayImportRequestDto)
                    .execute();
        } catch (Exception e) {
            logger.error(e);
        }

        // Save uploading status
        uploaded = true;

        // Log result key to console
        XrayImportResponseDto responseDto = uploadImportCommand.getXrayImportResponseDto();

        // Log test execution key to console
        if (responseDto instanceof XrayCloudImportResponseDto) {
            logger.info("[QTAF Xray Plugin] Uploaded test execution. Key is "
                    + ((XrayCloudImportResponseDto) responseDto).getKey());
        } else if (responseDto instanceof XrayServerImportResponseDto) {
            logger.info("[QTAF Xray Plugin] Uploaded test execution. Key is "
                    + ((XrayServerImportResponseDto) responseDto).getTestExecIssue().getKey());
        }

        // Dispatch events
        QtafXrayEvents.responseDtoAvailable.onNext(responseDto);
        QtafXrayEvents.responseDtoAvailable.onCompleted();
    }

    /**
     * Unsubscribe from subscriptions
     */
    public void unsubscribe() {
        this.testFinishedSubscription.unsubscribe();
    }
}
