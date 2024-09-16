package de.qytera.qtaf.testng;

import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

public class SampleTestResult implements ITestResult {
    int status = ITestResult.FAILURE;
    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(int i) {
        this.status = i;
    }

    @Override
    public ITestNGMethod getMethod() {
        return new SampleTestNGMethod();
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{"my-parameter-1", 2000};
    }

    @Override
    public void setParameters(Object[] objects) {

    }

    @Override
    public IClass getTestClass() {
        return new SampleTestNGTestClass();
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }

    @Override
    public void setThrowable(Throwable throwable) {

    }

    @Override
    public long getStartMillis() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse("2020-01-01 12:00:00")
                    .getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public long getEndMillis() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse("2020-01-01 12:05:00")
                    .getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public void setEndMillis(long l) {

    }

    @Override
    public String getName() {
        // This is the method name in the real test class
        return "sampleTestMethod";
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public Object getInstance() {
        return null;
    }

    @Override
    public Object[] getFactoryParameters() {
        return new Object[0];
    }

    @Override
    public String getTestName() {
        return null;
    }

    @Override
    public String getInstanceName() {
        return null;
    }

    @Override
    public ITestContext getTestContext() {
        return null;
    }

    @Override
    public void setTestName(String s) {

    }

    @Override
    public boolean wasRetried() {
        return false;
    }

    @Override
    public void setWasRetried(boolean b) {

    }

    @Override
    public String id() {
        return null;
    }

    @Override
    public int compareTo(ITestResult o) {
        return 0;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public Set<String> getAttributeNames() {
        return null;
    }

    @Override
    public Object removeAttribute(String s) {
        return null;
    }
}
