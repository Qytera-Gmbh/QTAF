package de.qytera.qtaf.core.log;

import de.qytera.qtaf.core.guice.annotations.Step;

public class DemoStepClass {
    @Step(name = "step one", description = "this is step one")
    public boolean foo(String arg1, int arg2) {
        return true;
    }
}
