package de.qytera.qtaf.core.reflection;

/**
 * Sample data class for testing the FiledHelper class
 */
public class SampleData {
    /**
     * Private attribute
     */
    private String msg = "Hello";

    /**
     * Private attribute
     */
    protected String msgProtected = "Hello";

    /**
     * Private static field
     */
    private static String msgStatic = "Hello";

    /**
     * Get msg
     *
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Set msg
     *
     * @param msg Msg
     * @return this
     */
    public SampleData setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * Get staticMsg
     *
     * @return staticMsg
     */
    public static String getMsgStatic() {
        return msgStatic;
    }

    /**
     * Set staticMsg
     *
     * @param msgStatic StaticMsg
     * @return this
     */
    public static void setMsgStatic(String msgStatic) {
        SampleData.msgStatic = msgStatic;
    }
}
