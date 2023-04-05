package de.qytera.qtaf.testng;

import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlTest;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class SampleTestContext implements ITestContext {
    @Override
    public String getName() {
        return "sample-test-context";
    }

    @Override
    public Date getStartDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-01-01 12:00:00");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Date getEndDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-01-01 12:05:00");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public IResultMap getPassedTests() {
        return null;
    }

    @Override
    public IResultMap getSkippedTests() {
        return null;
    }

    @Override
    public IResultMap getFailedButWithinSuccessPercentageTests() {
        return null;
    }

    @Override
    public IResultMap getFailedTests() {
        return null;
    }

    @Override
    public String[] getIncludedGroups() {
        return new String[]{"inc-group-1", "inc-group-2"};
    }

    @Override
    public String[] getExcludedGroups() {
        return new String[]{"excl-group-1", "excl-group-2"};
    }

    @Override
    public String getOutputDirectory() {
        return "/var/lib/qtaf";
    }

    @Override
    public ISuite getSuite() {
        return new SampleSuite();
    }

    @Override
    public ITestNGMethod[] getAllTestMethods() {
        return new ITestNGMethod[0];
    }

    @Override
    public String getHost() {
        return "sample-host";
    }

    @Override
    public Collection<ITestNGMethod> getExcludedMethods() {
        return null;
    }

    @Override
    public IResultMap getPassedConfigurations() {
        return null;
    }

    @Override
    public IResultMap getSkippedConfigurations() {
        return null;
    }

    @Override
    public IResultMap getFailedConfigurations() {
        return null;
    }

    @Override
    public XmlTest getCurrentXmlTest() {
        return null;
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
