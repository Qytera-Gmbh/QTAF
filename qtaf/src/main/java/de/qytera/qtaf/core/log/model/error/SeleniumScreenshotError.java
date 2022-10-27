package de.qytera.qtaf.core.log.model.error;

public class SeleniumScreenshotError extends ErrorLog {
    /**
     * Constructor
     *
     * @param e Error
     */
    public SeleniumScreenshotError(Throwable e) {
        super(e);
        this.type = "Selenium Screenshot Error";
    }
}
