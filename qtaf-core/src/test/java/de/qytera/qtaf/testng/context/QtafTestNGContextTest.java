package de.qytera.qtaf.testng.context;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.events.payload.QtafTestEventPayload;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.core.log.model.index.IndexHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Test;

public class QtafTestNGContextTest {
    @Test
    public void testLoad() {
        QtafTestNGContext context = new MyContext();
        TestScenarioLogCollection scenarioLogCollection = TestScenarioLogCollection.fromQtafTestEventPayload(new QtafTestEventPayload());
        context.setLogCollection(scenarioLogCollection);
        MyPageObject pageObject = context.load(MyPageObject.class);
        Assert.assertNotNull(pageObject.getLogCollection(), "Expected that scenario log collection of loaded page object is not null");
        Assert.assertEquals(pageObject.getLogCollection().hashCode(), scenarioLogCollection.hashCode(), "Expected page object instance to have the same scenario log instance as the class it was loaded from");
        QtafFactory.getWebDriver().quit();
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
        QtafFactory.getWebDriver().quit();
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testInitElements() {
        QtafTestNGContext context = new MyContext();
        context.initElements();
        MyPageObject2 pageObject = context.load(MyPageObject2.class);
        Assert.assertNotNull(pageObject.element);
        QtafFactory.getWebDriver().quit();
        IndexHelper.clearAllIndices();
    }

    @Test
    public void testJsExec1() {
        QtafTestNGContext context = new MyContext();
        context.jsExec("window.open('https://www.selenium.dev')");
        QtafFactory.getWebDriver().quit();
    }

    @Test
    public void testJsExec2() {
        QtafTestNGContext context = new MyContext();
        context.jsExec("window.open(arguments[0])", "https://www.selenium.dev");
        QtafFactory.getWebDriver().quit();
    }

    @Test
    public void testRestartDriver() {
        QtafTestNGContext context = new MyContext();
        context.restartDriver();
        QtafFactory.getWebDriver().quit();
    }
}

class MyContext extends QtafTestNGContext {

}

class MyPageObject extends QtafTestNGContext {

}

class MyPageObject2 extends QtafTestNGContext {
    @FindBy(css = "div")
    public WebElement element;
}