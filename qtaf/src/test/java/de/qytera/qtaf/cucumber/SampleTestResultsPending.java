package de.qytera.qtaf.cucumber;

import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;

import java.time.Duration;
import java.util.ArrayList;

public class SampleTestResultsPending extends ArrayList<Result> {
    SampleTestResultsPending() {
        this.add(new Result(
                Status.PASSED,
                Duration.ofSeconds(200),
                null
        ));
        this.add(new Result(
                Status.PENDING,
                Duration.ofSeconds(100),
                null
        ));
    }
}
