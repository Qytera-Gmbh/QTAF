package de.qytera.qtaf.core.log;

import de.qytera.qtaf.core.events.payload.MethodInfoEntity;
import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Parameter;

public class TestScenarioLogCollectionTest {
    /**
     * Assert that two ScenarioLogCollection objects with the same ID are equal
     */
    @Test
    public void testObjectEquality() {
        TestSuiteLogCollection slc = TestSuiteLogCollection.getInstance();
        TestFeatureLogCollection fc1 = slc.createFeatureIfNotExists("feature1", "feature1");
        TestScenarioLogCollection c1 = fc1.createScenarioIfNotExists(fc1.getFeatureId(), "scenario1", "instance1", "Scenario 1");
        TestScenarioLogCollection c2 = fc1.createScenarioIfNotExists(fc1.getFeatureId(), "scenario1", "instance1", "Scenario 2");

        Assert.assertEquals(c1, c2);

        // Clear up
        slc.clearCollection();
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testFactoryMethodUniqueness() {
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0, "Assert empty index");

        TestScenarioLogCollection scenarioLogCollection1 = TestScenarioLogCollection
                .createTestScenarioLogCollection(
                        "feature1",
                        "scenario1",
                        "instance1",
                        "test1"
                );

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);

        TestScenarioLogCollection scenarioLogCollection2 = TestScenarioLogCollection
                .createTestScenarioLogCollection(
                        "feature2",
                        "scenario1",
                        "instance1",
                        "test2"
                );

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);

        TestScenarioLogCollection scenarioLogCollection3 = TestScenarioLogCollection
                .createTestScenarioLogCollection(
                        "feature3",
                        "scenario2",
                        "instance1",
                        "test3"
                );

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 2);

        // Clear indices
        IndexHelper.clearAllIndices();
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0);
    }

    @Test
    public void testQtafEventFactoryMethodUniqueness() throws NoSuchMethodException {
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0);

        MethodInfoEntity methodInfoEntity = new MethodInfoEntity(
                DemoStepClass.class.getDeclaredMethod("foo", String.class, int.class),
                DemoStepClass.class.getDeclaredMethod("foo", String.class, int.class).getParameterTypes(),
                new Object[] {"BAR", 10},
                DemoStepClass.class.getDeclaredMethod("foo", String.class, int.class).getAnnotations()
        );

        QtafTestEventPayload eventPayload1 = new QtafTestEventPayload()
                .setFeatureId("feature1")
                .setAbstractScenarioId("scenario1")
                .setMethodInfo(methodInfoEntity);

        QtafTestEventPayload eventPayload2 = new QtafTestEventPayload()
                .setFeatureId("feature2")
                .setAbstractScenarioId("scenario1");

        QtafTestEventPayload eventPayload3 = new QtafTestEventPayload()
                .setFeatureId("feature3")
                .setAbstractScenarioId("scenario2");

        TestScenarioLogCollection scenarioLogCollection1 = TestScenarioLogCollection
                .fromQtafTestEventPayload(eventPayload1);

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);
        Assert.assertEquals(scenarioLogCollection1.getTestParameters().size(), 2);
        Assert.assertEquals(scenarioLogCollection1.getAnnotations().length, 1);
        Assert.assertEquals(((Step) scenarioLogCollection1.getAnnotations()[0]).name(), "step one");
        Assert.assertEquals(((Step) scenarioLogCollection1.getAnnotations()[0]).description(), "this is step one");
        Assert.assertEquals(scenarioLogCollection1.getTestParameters().get(0).getType(), "java.lang.String");
        Assert.assertEquals(scenarioLogCollection1.getTestParameters().get(0).getName(), "arg0");
        Assert.assertEquals(scenarioLogCollection1.getTestParameters().get(0).getValue(), "BAR");
        Assert.assertEquals(scenarioLogCollection1.getTestParameters().get(1).getType(), "java.lang.Integer");
        Assert.assertEquals(scenarioLogCollection1.getTestParameters().get(1).getName(), "arg1");
        Assert.assertEquals(scenarioLogCollection1.getTestParameters().get(1).getValue(), 10);

        TestScenarioLogCollection scenarioLogCollection2 = TestScenarioLogCollection
                .fromQtafTestEventPayload(eventPayload2);

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 1);
        Assert.assertEquals(scenarioLogCollection2.getTestParameters().size(), 2);
        Assert.assertEquals(scenarioLogCollection2.getAnnotations().length, 1);
        Assert.assertEquals(((Step) scenarioLogCollection2.getAnnotations()[0]).name(), "step one");
        Assert.assertEquals(((Step) scenarioLogCollection2.getAnnotations()[0]).description(), "this is step one");
        Assert.assertEquals(scenarioLogCollection2.getTestParameters().get(0).getType(), "java.lang.String");
        Assert.assertEquals(scenarioLogCollection2.getTestParameters().get(0).getName(), "arg0");
        Assert.assertEquals(scenarioLogCollection2.getTestParameters().get(0).getValue(), "BAR");
        Assert.assertEquals(scenarioLogCollection2.getTestParameters().get(1).getType(), "java.lang.Integer");
        Assert.assertEquals(scenarioLogCollection2.getTestParameters().get(1).getName(), "arg1");
        Assert.assertEquals(scenarioLogCollection2.getTestParameters().get(1).getValue(), 10);

        TestScenarioLogCollection scenarioLogCollection3 = TestScenarioLogCollection
                .fromQtafTestEventPayload(eventPayload3);

        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 2);
        Assert.assertEquals(scenarioLogCollection3.getTestParameters().size(), 0);
        Assert.assertNull(scenarioLogCollection3.getAnnotations());

        // Clear indices
        IndexHelper.clearAllIndices();
        Assert.assertEquals(TestScenarioLogCollection.getIndexSize(), 0);
    }

    @Test
    public void testEquals() {
        TestSuiteLogCollection slc = TestSuiteLogCollection.getInstance();
        TestFeatureLogCollection fc1 = slc.createFeatureIfNotExists("feature1", "feature1");
        TestScenarioLogCollection c1 = fc1.createScenarioIfNotExists(fc1.getFeatureId(), "scenario1", "instance1", "Scenario 1");
        TestScenarioLogCollection c2 = fc1.createScenarioIfNotExists(fc1.getFeatureId(), "scenario1", "instance1", "Scenario 1");
        TestScenarioLogCollection c3 = fc1.createScenarioIfNotExists(fc1.getFeatureId(), "scenario1", "instance2", "Scenario 1");
        Assert.assertEquals(c1, c2);
        Assert.assertNotEquals(c1, fc1);

        Assert.assertNotEquals(c1, c3);
        c3.setInstanceId("instance1");
        Assert.assertEquals(c1, c3);

        // Clear up
        slc.clearCollection();
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testScenarioName() {
        TestSuiteLogCollection slc = TestSuiteLogCollection.getInstance();
        TestFeatureLogCollection fc1 = slc.createFeatureIfNotExists("feature1", "feature1");
        TestScenarioLogCollection c1 = fc1.createScenarioIfNotExists(fc1.getFeatureId(), "scenario1", "instance1", "Scenario 1");

        Assert.assertEquals(c1.getAbstractScenarioId(), "scenario1");
        Assert.assertEquals(c1.getScenarioName(), "Scenario 1");
        Assert.assertEquals(c1.getInstanceId(), "instance1");

        // Clear up
        slc.clearCollection();
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testAddParameters() throws NoSuchMethodException {
        TestSuiteLogCollection slc = TestSuiteLogCollection.getInstance();
        TestFeatureLogCollection fc1 = slc.createFeatureIfNotExists("feature1", "feature1");
        TestScenarioLogCollection c1 = fc1.createScenarioIfNotExists(fc1.getFeatureId(), "scenario1", "instance1", "Scenario 1");
        Assert.assertEquals(c1.getTestParameters().size(), 0);
        Parameter[] params = DemoStepClass.class.getDeclaredMethod("foo", String.class, int.class).getParameters();
        Object[] values = new Object[]{"Hello", 2};
        c1.addParameters(params, values);
        Assert.assertEquals(c1.getTestParameters().size(), 2);
        Assert.assertEquals(c1.getTestParameters().get(0).getName(), "arg0");
        Assert.assertEquals(c1.getTestParameters().get(0).getType(), "java.lang.String");
        Assert.assertEquals(c1.getTestParameters().get(0).getValue(), "Hello");
        Assert.assertEquals(c1.getTestParameters().get(1).getName(), "arg1");
        Assert.assertEquals(c1.getTestParameters().get(1).getType(), "java.lang.Integer");
        Assert.assertEquals(c1.getTestParameters().get(1).getValue(), 2);
        // Clear up
        slc.clearCollection();
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testStatus() {
        TestSuiteLogCollection slc = TestSuiteLogCollection.getInstance();
        TestFeatureLogCollection fc1 = slc.createFeatureIfNotExists("feature1", "feature1");
        TestScenarioLogCollection c1 = fc1.createScenarioIfNotExists(fc1.getFeatureId(), "scenario1", "instance1", "Scenario 1");

        Assert.assertEquals(c1.getStatus(), TestScenarioLogCollection.Status.PENDING);
        c1.setStatus(TestScenarioLogCollection.Status.SUCCESS);
        Assert.assertEquals(c1.getStatus(), TestScenarioLogCollection.Status.SUCCESS);
        c1.setStatus(TestScenarioLogCollection.Status.FAILURE);
        Assert.assertEquals(c1.getStatus(), TestScenarioLogCollection.Status.FAILURE);

        // Clear up
        slc.clearCollection();
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testAddParameterValues() {
        TestSuiteLogCollection slc = TestSuiteLogCollection.getInstance();
        TestFeatureLogCollection fc1 = slc.createFeatureIfNotExists("feature1", "feature1");
        TestScenarioLogCollection c1 = fc1.createScenarioIfNotExists(fc1.getFeatureId(), "scenario1", "instance1", "Scenario 1");
        Assert.assertEquals(c1.getTestParameters().size(), 0);
        c1.addParameters(new String[]{"Hello", "World"});
        Assert.assertEquals(c1.getTestParameters().size(), 2);
        // Clear up
        slc.clearCollection();
        IndexHelper.clearAllIndices();
    }
}