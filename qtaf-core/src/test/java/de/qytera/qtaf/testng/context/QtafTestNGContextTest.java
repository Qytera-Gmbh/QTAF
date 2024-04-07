package de.qytera.qtaf.testng.context;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import de.qytera.qtaf.core.selenium.helper.SeleniumDriverConfigHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class QtafTestNGContextTest {

    @BeforeMethod
    public void clear() {
        System.clearProperty(SeleniumDriverConfigHelper.DRIVER_CAPABILITIES);
        System.clearProperty(SeleniumDriverConfigHelper.DRIVER_OPTIONS);
    }

    @Test
    public void testSetLoggerRecursively() {
        MyContext context = new MyContext();
        TestScenarioLogCollection scenarioLogCollection = TestScenarioLogCollection.fromQtafTestEventPayload(new QtafTestEventPayload());
        context.setLogCollection(scenarioLogCollection);
        Assert.assertNull(context.getNestedTestContext().getLogCollection());
        context.addLoggerToFieldsRecursively();
        Assert.assertNotNull(context.getNestedTestContext().getLogCollection());
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testLoad() {
        QtafTestNGContext context = new MyContext();
        TestScenarioLogCollection scenarioLogCollection = TestScenarioLogCollection.fromQtafTestEventPayload(new QtafTestEventPayload());
        context.setLogCollection(scenarioLogCollection);
        MyPageObject pageObject = context.load(MyPageObject.class);
        Assert.assertNotNull(pageObject.getLogCollection(), "Expected that scenario log collection of loaded page object is not null");
        Assert.assertEquals(pageObject.getLogCollection().hashCode(), scenarioLogCollection.hashCode(), "Expected page object instance to have the same scenario log instance as the class it was loaded from");
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testStaticLoad() {
        QtafTestNGContext context = new MyContext();
        TestScenarioLogCollection scenarioLogCollection = TestScenarioLogCollection.fromQtafTestEventPayload(new QtafTestEventPayload());
        context.setLogCollection(scenarioLogCollection);
        MyPageObject pageObject = QtafTestNGContext.load(context, MyPageObject.class);
        Assert.assertNotNull(pageObject.getLogCollection(), "Expected that scenario log collection of loaded page object is not null");
        Assert.assertEquals(pageObject.getLogCollection().hashCode(), scenarioLogCollection.hashCode(), "Expected page object instance to have the same scenario log instance as the class it was loaded from");
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testInitElements() {
        QtafTestNGContext context = new MyContext();
        TestScenarioLogCollection scenarioLogCollection = TestScenarioLogCollection.fromQtafTestEventPayload(new QtafTestEventPayload());
        context.setLogCollection(scenarioLogCollection);
        context.initElements();
        MyPageObject2 pageObject = context.load(MyPageObject2.class);
        Assert.assertNotNull(pageObject.getLogCollection(), "Log collection of page object class should not be empty");
        Assert.assertNull(pageObject.element);
        pageObject.initElements();
        Assert.assertNotNull(pageObject.element);
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testJsExec1() {
        QtafFactory.getWebDriver().get("https://www.selenium.dev");
        QtafTestNGContext context = new MyContext();
        context.initialize();
        context.jsExec("window.open('https://www.selenium.dev')");
    }

    @Test
    public void testJsExec2() {
        QtafFactory.getWebDriver().get("https://www.selenium.dev");
        QtafTestNGContext context = new MyContext();
        context.initialize();
        context.jsExec("window.open(arguments[0])", "https://www.selenium.dev");
    }

    @Test
    public void testRestartDriver() {
        QtafTestNGContext context = new MyContext();
        context.restartDriver();
    }

    @AfterTest
    public void quitDriver() {
        QtafFactory.getWebDriver().quit();
    }
}

class MyContext extends QtafTestNGContext {
    private NestedTestContext nestedTestContext = new NestedTestContext();

    public NestedTestContext getNestedTestContext() {
        return nestedTestContext;
    }
}

class NestedTestContext extends QtafTestNGContext {

}

class MyPageObject extends QtafTestNGContext {

}

class MyPageObject2 extends QtafTestNGContext {
    @FindBy(css = "div")
    public WebElement element;
}