package de.qytera.qtaf.testng;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.testng.sample_test_classes.TestClass1;
import de.qytera.qtaf.testng.sample_test_classes.TestClass2;
import de.qytera.qtaf.testng.test_factory.TestNGFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import rx.Subscription;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TestNGFactoryTest {
    Set<Class<?>> testClassesLoadedPayload;
    List<Object> testClassInstancesLoadedPayload;

    @Test(testName = "TestNG factory test", description = "Test if TestNGFactory loads test classes correctly")
    public void testFactory() {
        // Make sure that the attributes where the event payloads are stored are set to null
        testClassesLoadedPayload = null;
        testClassInstancesLoadedPayload = null;

        // Subscribe to events, store their payloads in class attributes
        Subscription testClassesSub = QtafEvents.testClassesLoaded.subscribe(_classes -> testClassesLoadedPayload = _classes);
        Subscription testClassInstancesSub = QtafEvents.testClassInstancesLoaded.subscribe(_instances -> testClassInstancesLoadedPayload = _instances);

        // Get the configuration
        ConfigMap configMap = QtafFactory.getConfiguration();
        // Get the current value where QTAF should look for test classes
        String oldValue = configMap.getString("tests.package");
        // Point to a new package for looking for test classes
        configMap.setString("tests.package", "de.qytera.qtaf.testng.sample_test_classes");
        // Use TestNGFactory to load test class instances
        TestNGFactory factory = new TestNGFactory();
        Object[] instances = factory.createInstances();
        // Test if test classes have been loaded correctly
        Assert.assertEquals(instances.length, 2, "TestNG factory should have found 2 test classes");
        Assert.assertTrue(Arrays.stream(instances).anyMatch(i -> i instanceof TestClass1), "There should be an instance of TestClass1");
        Assert.assertTrue(Arrays.stream(instances).anyMatch(i -> i instanceof TestClass2), "There should be an instance of TestClass2");
        // Test if events were dispatched correctly
        Assert.assertEquals(testClassesLoadedPayload.size(), 2, "Qtaf event should have dispatched an event with a payload that contains two classes");
        Assert.assertTrue(testClassesLoadedPayload.stream().anyMatch(i -> i.getName().equals("de.qytera.qtaf.testng.sample_test_classes.TestClass1")));
        Assert.assertTrue(testClassesLoadedPayload.stream().anyMatch(i -> i.getName().equals("de.qytera.qtaf.testng.sample_test_classes.TestClass2")));
        Assert.assertEquals(testClassInstancesLoadedPayload.size(), 2, "Qtaf event should have dispatched an event with a payload that contains two class instances");
        Assert.assertTrue(testClassInstancesLoadedPayload.stream().anyMatch(i -> i instanceof TestClass1), "There should be an instance of TestClass1");
        Assert.assertTrue(testClassInstancesLoadedPayload.stream().anyMatch(i -> i instanceof TestClass2), "There should be an instance of TestClass2");
        // Set the configuration back to its old value, so this has no side effects on other test cases
        configMap.setString("tests.package", oldValue);
        // Unsubscribe to events, so that this test has no side effect on other test cases
        testClassesSub.unsubscribe();
        testClassInstancesSub.unsubscribe();
    }

    @Test(testName = "TestNG factory manipulation test", description = "Test if we can manipulate the tests loaded by TestNGFactory")
    public void testFactoryManipulation() {
        // Make sure that the attributes where the event payloads are stored are set to null
        testClassesLoadedPayload = null;
        testClassInstancesLoadedPayload = null;

        // Subscribe to events, store their payloads in class attributes
        Subscription testClassesSub = QtafEvents.testClassesLoaded.subscribe(_classes -> {
            _classes.remove(TestClass1.class);
            testClassesLoadedPayload = _classes;
        });
        Subscription testClassInstancesSub = QtafEvents.testClassInstancesLoaded.subscribe(_instances -> testClassInstancesLoadedPayload = _instances);

        // Get the configuration
        ConfigMap configMap = QtafFactory.getConfiguration();
        // Get the current value where QTAF should look for test classes
        String oldValue = configMap.getString("tests.package");
        // Point to a new package for looking for test classes
        configMap.setString("tests.package", "de.qytera.qtaf.testng.sample_test_classes");
        // Use TestNGFactory to load test class instances
        TestNGFactory factory = new TestNGFactory();
        Object[] instances = factory.createInstances();
        // Test if test classes have been loaded correctly
        Assert.assertEquals(instances.length, 1, "TestNG factory should have found 1 test class");
        Assert.assertTrue(Arrays.stream(instances).anyMatch(i -> i instanceof TestClass2), "There should be an instance of TestClass2");
        // Test if events were dispatched correctly
        Assert.assertEquals(testClassesLoadedPayload.size(), 1, "Qtaf event should have dispatched an event with a payload that contains two classes");
        Assert.assertTrue(testClassesLoadedPayload.stream().anyMatch(i -> i.getName().equals("de.qytera.qtaf.testng.sample_test_classes.TestClass2")));
        Assert.assertEquals(testClassInstancesLoadedPayload.size(), 1, "Qtaf event should have dispatched an event with a payload that contains two class instances");
        Assert.assertTrue(testClassInstancesLoadedPayload.stream().anyMatch(i -> i instanceof TestClass2), "There should be an instance of TestClass2");
        // Set the configuration back to its old value, so this has no side effects on other test cases
        configMap.setString("tests.package", oldValue);
        // Unsubscribe to events, so that this test has no side effect on other test cases
        testClassesSub.unsubscribe();
        testClassInstancesSub.unsubscribe();
    }
}