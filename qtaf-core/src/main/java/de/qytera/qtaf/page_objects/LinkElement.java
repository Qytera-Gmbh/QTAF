package de.qytera.qtaf.page_objects;

import com.codeborne.selenide.Condition;
import de.qytera.qtaf.core.guice.annotations.Step;

import java.time.Duration;

/**
 * This page object represents a button
 */
public class LinkElement extends AbstractWebElement {
    /**
     * Get the href attribute of the link
     */
    @Step(name = "get href", description = "Get href of link")
    public String href() {
        return element()
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .scrollIntoView(true)
                .attr("href");
    }
}
