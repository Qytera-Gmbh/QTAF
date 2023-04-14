package de.qytera.qtaf.xray.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.events.payload.IQtafTestingContext;
import de.qytera.qtaf.core.log.Logger;
import de.qytera.qtaf.xray.builder.XrayJsonImportBuilder;
import de.qytera.qtaf.xray.commands.UploadImportCommand;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.dto.response.XrayCloudImportResponseDto;
import de.qytera.qtaf.xray.dto.response.XrayImportResponseDto;
import de.qytera.qtaf.xray.dto.response.XrayServerImportResponseDto;
import de.qytera.qtaf.xray.events.QtafXrayEvents;
import rx.Subscription;

/**
 * Event subscriber that uploads test results to Xray API
 */
public class UploadResultsSubscriber implements IEventSubscriber {
    /**
     * Whether tests have been uploaded already.
     */
    private static boolean uploaded = false;

    /**
     * Event subscription
     */
    private Subscription testFinishedSubscription;

    /**
     * Command to upload test results to Xray API
     */
    private static final UploadImportCommand UPLOAD_IMPORT_COMMAND = new UploadImportCommand();

    /**
     * QTAF logger.
     */
    private static final Logger logger = QtafFactory.getLogger();

    @Override
    public void initialize() {
        // Check if there already is a subscription
        if (testFinishedSubscription != null) {
            return;
        }

        // Subscribe to tests finished subject
        this.testFinishedSubscription = QtafEvents.finishedTesting.subscribe(UploadResultsSubscriber::onTestFinished);
    }

    /**
     * Method that is executed when testing has finished.
     *
     * @param testContext the test context payload
     */
    public static void onTestFinished(IQtafTestingContext testContext) {
        // Check if Xray Plugin is enabled
        if (Boolean.FALSE.equals(QtafFactory.getConfiguration().getBoolean("xray.enabled"))) {
            return;
        }

        // Check if result was already uploaded
        if (uploaded) {
            return;
        }
        uploaded = true;

        logger.info("[QTAF Xray Plugin] Uploading Xray results ...");

        try {
            // Build Request DTO for Xray API
            XrayImportRequestDto xrayImportRequestDto = new XrayJsonImportBuilder(QtafFactory.getTestSuiteLogCollection()).buildRequest();

            // Dispatch Event for Import DTO
            QtafXrayEvents.importDtoCreated.onNext(xrayImportRequestDto);

            // Upload test execution data
            UPLOAD_IMPORT_COMMAND.setXrayImportRequestDto(xrayImportRequestDto).execute();

            // Log result key to console
            XrayImportResponseDto responseDto = UPLOAD_IMPORT_COMMAND.getXrayImportResponseDto();

            // Log test execution key to console
            if (responseDto instanceof XrayCloudImportResponseDto xrayCloudImportResponseDto) {
                logger.info("[QTAF Xray Plugin] Uploaded test execution. Key is " + xrayCloudImportResponseDto.getKey());
            } else if (responseDto instanceof XrayServerImportResponseDto xrayServerImportResponseDto) {
                logger.info("[QTAF Xray Plugin] Uploaded test execution. Key is " + xrayServerImportResponseDto.getTestExecIssue().getKey());
            }

            // Dispatch events
            QtafXrayEvents.responseDtoAvailable.onNext(responseDto);
            QtafXrayEvents.responseDtoAvailable.onCompleted();
        } catch (XrayJsonImportBuilder.NoXrayTestException e) {
            logger.info("[QTAF Xray Plugin] No tests linked to Xray issues were executed. Skipping upload.");
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
