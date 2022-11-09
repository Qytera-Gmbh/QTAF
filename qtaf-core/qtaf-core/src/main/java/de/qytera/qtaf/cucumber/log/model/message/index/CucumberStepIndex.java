package de.qytera.qtaf.cucumber.log.model.message.index;

import de.qytera.qtaf.cucumber.log.model.message.CucumberStepLogMessage;

import java.util.HashMap;
import java.util.UUID;

/**
 * Index that holds all Cucumber log messages
 */
public class CucumberStepIndex extends HashMap<UUID, CucumberStepLogMessage> {
    /**
     * Singleton instance
     */
    private static final CucumberStepIndex instance = new CucumberStepIndex();

    /**
     * Private constructor, part of the singleton design pattern
     */
    private CucumberStepIndex() {
    }

    /**
     * Get instance
     *
     * @return instance
     */
    public static CucumberStepIndex getInstance() {
        return instance;
    }
}
