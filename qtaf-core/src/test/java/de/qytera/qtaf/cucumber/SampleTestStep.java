package de.qytera.qtaf.cucumber;

import io.cucumber.plugin.event.TestStep;

import java.util.UUID;

/**
 * TestStep mock class
 */
public class SampleTestStep implements TestStep {
    private String codeLocation;
    private UUID id;

    public SampleTestStep(String codeLocation, UUID id) {
        this.codeLocation = codeLocation;
        this.id = id;
    }

    @Override
    public String getCodeLocation() {
        return this.codeLocation;
    }

    @Override
    public UUID getId() {
        return this.id;
    }
}
