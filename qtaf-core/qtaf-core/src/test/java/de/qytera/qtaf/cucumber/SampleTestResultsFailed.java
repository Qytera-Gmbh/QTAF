package de.qytera.qtaf.cucumber;

import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;

import java.time.Duration;
import java.util.ArrayList;

public class SampleTestResultsFailed extends ArrayList<Result> {
    SampleTestResultsFailed() {
        this.add(new Result(
                Status.PASSED,
                Duration.ofSeconds(200),
                null
        ));
        this.add(new Result(
                Status.FAILED,
                Duration.ofSeconds(100),
                null
        ));
    }
}
