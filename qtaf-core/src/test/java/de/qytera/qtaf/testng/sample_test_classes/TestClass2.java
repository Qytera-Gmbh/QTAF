package de.qytera.qtaf.testng.sample_test_classes;

import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.testng.context.QtafTestNGContext;

public class TestClass2 implements IQtafTestContext {
    @Override
    public TestScenarioLogCollection getLogCollection() {
        return null;
    }

    @Override
    public IQtafTestContext setLogCollection(TestScenarioLogCollection collection) {
        return null;
    }

    @Override
    public void addLoggerToFieldsRecursively() {

    }

    @Override
    public void restartDriver() {

    }

    @Override
    public IQtafTestContext initialize() {
        return null;
    }
}
