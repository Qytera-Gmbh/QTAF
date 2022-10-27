package de.qytera.qtaf.xray.event_subscriber;

import com.google.gson.Gson;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.gson.GsonFactory;
import de.qytera.qtaf.core.io.FileHelper;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.xray.dto.request.XrayImportRequestDto;
import de.qytera.qtaf.xray.error.ImportRequestDtoPersistenceError;
import de.qytera.qtaf.xray.events.QtafXrayEvents;

import java.io.IOException;

/**
 * Persist the Import DTO to the disk
 */
public class PersistImportRequestDtoSubscriber implements IEventSubscriber {
    @Override
    public void initialize() {
        QtafXrayEvents.importDtoCreated.subscribe(this::persistImportRequestDto);
    }

    private void persistImportRequestDto(XrayImportRequestDto xrayImportRequestDto) {
        TestSuiteLogCollection suiteLogCollection = TestSuiteLogCollection.getInstance();

        Gson gson = GsonFactory.getInstance();
        String json = gson.toJson(xrayImportRequestDto);

        try {
            FileHelper.createFileIfNotExists(
                    suiteLogCollection.getLogDirectory() + "/xray/request.dto.json",
                    json
            );
        } catch (IOException e) {
            ImportRequestDtoPersistenceError error = new ImportRequestDtoPersistenceError(e)
                    .setXrayImportRequestDto(xrayImportRequestDto);
            ErrorLogCollection errors = ErrorLogCollection.getInstance();
            errors.addErrorLog(error);
        }
    }
}
