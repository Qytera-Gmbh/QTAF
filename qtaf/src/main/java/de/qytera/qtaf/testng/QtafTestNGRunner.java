package de.qytera.qtaf.testng;

import de.qytera.qtaf.core.guice.QtafModule;
import de.qytera.qtaf.testng.event_listener.TestNGEventListener;
import org.testng.*;
import org.testng.annotations.Guice;
import de.qytera.qtaf.testng.test_factory.TestNGFactory;

import java.util.Collections;

@Guice(modules = {QtafModule.class})
public class QtafTestNGRunner {
    public static void main(String[] args) {
        // Create TestNG instance
        TestNG testNG = new TestNG();

        // Add listener
        testNG.setListenerClasses(Collections.singletonList(TestNGEventListener.class));

        // Add test classes
        testNG.setTestClasses(new Class[] {
                TestNGFactory.class
        });

        // Run tests
        testNG.run();

        // Exit after tests
        System.exit(0);
    }
}