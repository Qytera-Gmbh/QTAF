package de.qytera.qtaf.testng.test_factory;

import com.google.inject.Injector;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.context.IQtafTestContext;
import de.qytera.qtaf.core.guice.QtafInjector;
import de.qytera.qtaf.core.reflection.ClassLoader;
import de.qytera.qtaf.cucumber.context.QtafTestNGCucumberContext;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.ITestObjectFactory;
import org.testng.annotations.Factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class is responsible for creating instances of all test classes
 */
public class TestNGFactory implements ITestObjectFactory {

    /**
     * Creates test class instances.
     *
     * @return the instances
     */
    @Factory()
    public Object[] createInstances() {
        // Get Guice instance
        Injector injector = QtafInjector.getInstance();

        // Specify the package names where to look for tests
        List<String> packageNames = List.of(QtafFactory.getConfiguration().getString("tests.package"));

        // Find all test classes
        Set<Class<?>> classes = ClassLoader.getSubTypesOfRecursively(
                IQtafTestContext.class,
                null,
                packageNames
        );

        // Do not create instances of the following classes
        classes.remove(QtafTestNGContext.class);
        classes.remove(QtafTestNGCucumberContext.class);

        // Test class instances are stored in this list
        List<Object> instances = new ArrayList<>();

        // Get instances of all test classes and let Guice create the instances,
        // so that dependency injection and method invocation is working
        for (Class<?> clazz : classes) {
            try {
                instances.add(injector.getInstance(clazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return instances.toArray();
    }

}
