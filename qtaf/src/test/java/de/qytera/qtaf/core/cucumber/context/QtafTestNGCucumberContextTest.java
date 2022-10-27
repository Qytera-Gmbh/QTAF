package de.qytera.qtaf.core.cucumber.context;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.cucumber.context.QtafTestNGCucumberContext;
import de.qytera.qtaf.cucumber.entity.QTAFCucumberScenarioEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Tests for the TestNG Cucumber context class
 */
public class QtafTestNGCucumberContextTest {
    @Test
    public void testShallRun() {
        ConfigMap config = QtafFactory.getConfiguration();

        QtafTestNGCucumberContext context = new QtafTestNGCucumberContext(false);
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        List<String> scenarioGroupNames = List.of("foo", "bar");
        entity.setGroupNames(scenarioGroupNames);

        config.setString("tests.groups", null);
        Assert.assertTrue(context.shallRun(entity));

        config.setString("tests.groups", "foo");
        Assert.assertTrue(context.shallRun(entity));

        config.setString("tests.groups", "bar");
        Assert.assertTrue(context.shallRun(entity));

        config.setString("tests.groups", "foo, bar");
        Assert.assertTrue(context.shallRun(entity));

        config.setString("tests.groups", "foo, foobar");
        Assert.assertTrue(context.shallRun(entity));

        config.setString("tests.groups", "foo, bar, foobar");
        Assert.assertTrue(context.shallRun(entity));

        config.setString("tests.groups", "foobar, barfoo");
        Assert.assertFalse(context.shallRun(entity));

        config.setString("tests.groups", "foobar");
        Assert.assertFalse(context.shallRun(entity));

        config.setString("tests.groups", "barfoo");
        Assert.assertFalse(context.shallRun(entity));

        // Clear changed configuration
        config.setString("tests.groups", null);
    }
}
