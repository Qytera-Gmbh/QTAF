package de.qytera.qtaf.cucumber;

import io.cucumber.plugin.event.Location;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestStep;

import java.net.URI;
import java.util.List;
import java.util.UUID;

public class SampleTestCase implements TestCase {
    @Override
    public Integer getLine() {
        return null;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public String getKeyword() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getScenarioDesignation() {
        return null;
    }

    @Override
    public List<String> getTags() {
        return null;
    }

    @Override
    public List<TestStep> getTestSteps() {
        return null;
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public UUID getId() {
        return null;
    }
}
