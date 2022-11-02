package de.qytera.qtaf.cucumber;

import io.cucumber.plugin.event.TestStep;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Mock TestStep collection
 */
public class SampleTestSteps extends ArrayList<TestStep> {
    SampleTestSteps() {
        this.add(new SampleTestStep("line1", UUID.fromString("fb4042a9-804c-43e7-bf68-eb45c3238579")));
        this.add(new SamplePickleStepTestStep("line2", UUID.fromString("343e61f6-97fb-4ac5-a6a1-ee4350a40c6b")));
        this.add(new SampleTestStep("line3", UUID.fromString("2e6c048d-f4fa-43cc-a1cb-3fff8c2952d2")));
        this.add(new SamplePickleStepTestStep("line4", UUID.fromString("57146f21-9383-4a07-86fe-4407c4d544ba")));
        this.add(new SamplePickleStepTestStep("line5", UUID.fromString("d265fce5-4844-40ed-bb4c-17a32bb76ab1")));
    }
}
