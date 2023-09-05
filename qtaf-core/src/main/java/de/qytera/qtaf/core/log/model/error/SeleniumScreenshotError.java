package de.qytera.qtaf.core.log.model.error;

/**
 * An error that happened when an attempt to take a screenshot was made.
 */
public class SeleniumScreenshotError extends ErrorLog {
    /**
     * Constructor.
     *
     * @param e Error
     */
    public SeleniumScreenshotError(Throwable e) {
        super(e);
        this.type = "Selenium Screenshot Error";
    }
}
