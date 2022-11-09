package de.qytera.qtaf.cucumber;

import io.cucumber.core.backend.Status;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.TestCase;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SampleTestCaseState implements TestCaseState {
    TestCase testCase = new SampleTestCase();
    List<Result> stepResults = new ArrayList<>();

    public SampleTestCaseState() {
        List<Result> results = new ArrayList<>();
        Result result = new Result(
                io.cucumber.plugin.event.Status.PENDING,
                Duration.ofSeconds(100),
                null
        );
        results.add(result);
        this.stepResults = results;
    }

    @Override
    public Collection<String> getSourceTagNames() {
        List<String> tags = new ArrayList<>();
        tags.add("@TestName:SampleTestName-1");
        return tags;
    }

    @Override
    public Status getStatus() {
        return Status.PENDING;
    }

    @Override
    public boolean isFailed() {
        return false;
    }

    @Override
    public void attach(byte[] bytes, String s, String s1) {

    }

    @Override
    public void attach(String s, String s1, String s2) {

    }

    @Override
    public void log(String s) {

    }

    @Override
    public String getName() {
        return "test-case-state-name";
    }

    @Override
    public String getId() {
        return "test-case-state-id";
    }

    @Override
    public URI getUri() {
        try {
            return new URI("file:///features/sample-feature");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer getLine() {
        return 123;
    }
}
