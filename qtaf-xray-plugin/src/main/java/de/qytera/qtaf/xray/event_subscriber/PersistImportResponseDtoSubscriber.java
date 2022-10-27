package de.qytera.qtaf.xray.event_subscriber;

import com.google.gson.Gson;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.io.FileHelper;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.xray.dto.response.XrayImportResponseDto;
import de.qytera.qtaf.xray.error.ImportResponseDtoPersistenceError;
import de.qytera.qtaf.xray.events.QtafXrayEvents;

import java.io.IOException;

/**
 * Persist the Import DTO to the disk
 */
public class PersistImportResponseDtoSubscriber implements IEventSubscriber {
    @Override
    public void initialize() {
        QtafXrayEvents.responseDtoAvailable.subscribe(this::persistImportResponseDto);
    }

    private void persistImportResponseDto(XrayImportResponseDto xrayImportResponseDto) {
        TestSuiteLogCollection suiteLogCollection = TestSuiteLogCollection.getInstance();

        Gson gson = GsonFactory.getInstance();
        String json = gson.toJson(xrayImportResponseDto);

        try {
            FileHelper.createFileIfNotExists(
                    suiteLogCollection.getLogDirectory() + "/xray/response.dto.json",
                    json
            );
        } catch (IOException e) {
            ImportResponseDtoPersistenceError error = new ImportResponseDtoPersistenceError(e)
                    .setXrayImportResponseDto(xrayImportResponseDto);
            ErrorLogCollection errors = ErrorLogCollection.getInstance();
            errors.addErrorLog(error);
        }
    }
}
