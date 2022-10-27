package de.qytera.qtaf.core.log.model.error;

public class TestError extends ErrorLog {
    /**
     * Constructor
     *
     * @param e Error
     */
    public TestError(Throwable e) {
        super(e);
        this.type = "Test Error";
    }
}
