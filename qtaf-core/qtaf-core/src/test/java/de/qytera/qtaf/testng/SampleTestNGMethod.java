package de.qytera.qtaf.testng;

import org.testng.*;
import org.testng.internal.ConstructorOrMethod;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class SampleTestNGMethod implements ITestNGMethod {
    @Override
    public Class getRealClass() {
        return null;
    }

    @Override
    public ITestClass getTestClass() {
        return null;
    }

    @Override
    public void setTestClass(ITestClass iTestClass) {

    }

    @Override
    public String getMethodName() {
        return "sampleTestNGMethodName";
    }

    @Override
    public Object getInstance() {
        return null;
    }

    @Override
    public long[] getInstanceHashCodes() {
        return new long[0];
    }

    @Override
    public String[] getGroups() {
        return new String[] {"group-1", "group-2"};
    }

    @Override
    public String[] getGroupsDependedUpon() {
        return new String[] {"dependent-group-1", "dependent-group-2"};
    }

    @Override
    public String getMissingGroup() {
        return null;
    }

    @Override
    public void setMissingGroup(String s) {

    }

    @Override
    public String[] getBeforeGroups() {
        return new String[]{"before-group-1", "before-group-2"};
    }

    @Override
    public String[] getAfterGroups() {
        return new String[]{"after-group-1", "after-group-2"};
    }

    @Override
    public String[] getMethodsDependedUpon() {
        return new String[]{"dependent-method-1", "dependent-method-2"};
    }

    @Override
    public void addMethodDependedUpon(String s) {

    }

    @Override
    public boolean isTest() {
        return false;
    }

    @Override
    public boolean isBeforeMethodConfiguration() {
        return false;
    }

    @Override
    public boolean isAfterMethodConfiguration() {
        return false;
    }

    @Override
    public boolean isBeforeClassConfiguration() {
        return false;
    }

    @Override
    public boolean isAfterClassConfiguration() {
        return false;
    }

    @Override
    public boolean isBeforeSuiteConfiguration() {
        return false;
    }

    @Override
    public boolean isAfterSuiteConfiguration() {
        return false;
    }

    @Override
    public boolean isBeforeTestConfiguration() {
        return false;
    }

    @Override
    public boolean isAfterTestConfiguration() {
        return false;
    }

    @Override
    public boolean isBeforeGroupsConfiguration() {
        return false;
    }

    @Override
    public boolean isAfterGroupsConfiguration() {
        return false;
    }

    @Override
    public long getTimeOut() {
        return 0;
    }

    @Override
    public void setTimeOut(long l) {

    }

    @Override
    public int getInvocationCount() {
        return 0;
    }

    @Override
    public void setInvocationCount(int i) {

    }

    @Override
    public int getSuccessPercentage() {
        return 0;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String s) {

    }

    @Override
    public long getDate() {
        return 0;
    }

    @Override
    public void setDate(long l) {

    }

    @Override
    public boolean canRunFromClass(IClass iClass) {
        return false;
    }

    @Override
    public boolean isAlwaysRun() {
        return false;
    }

    @Override
    public int getThreadPoolSize() {
        return 0;
    }

    @Override
    public void setThreadPoolSize(int i) {

    }

    @Override
    public boolean getEnabled() {
        return false;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String s) {

    }

    @Override
    public void incrementCurrentInvocationCount() {

    }

    @Override
    public int getCurrentInvocationCount() {
        return 0;
    }

    @Override
    public void setParameterInvocationCount(int i) {

    }

    @Override
    public int getParameterInvocationCount() {
        return 0;
    }

    @Override
    public void setMoreInvocationChecker(Callable<Boolean> callable) {

    }

    @Override
    public boolean hasMoreInvocation() {
        return false;
    }

    @Override
    public ITestNGMethod clone() {
        return null;
    }

    @Override
    public IRetryAnalyzer getRetryAnalyzer(ITestResult iTestResult) {
        return null;
    }

    @Override
    public void setRetryAnalyzerClass(Class<? extends IRetryAnalyzer> aClass) {

    }

    @Override
    public Class<? extends IRetryAnalyzer> getRetryAnalyzerClass() {
        return null;
    }

    @Override
    public boolean skipFailedInvocations() {
        return false;
    }

    @Override
    public void setSkipFailedInvocations(boolean b) {

    }

    @Override
    public long getInvocationTimeOut() {
        return 0;
    }

    @Override
    public boolean ignoreMissingDependencies() {
        return false;
    }

    @Override
    public void setIgnoreMissingDependencies(boolean b) {

    }

    @Override
    public List<Integer> getInvocationNumbers() {
        return null;
    }

    @Override
    public void setInvocationNumbers(List<Integer> list) {

    }

    @Override
    public void addFailedInvocationNumber(int i) {

    }

    @Override
    public List<Integer> getFailedInvocationNumbers() {
        return null;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void setPriority(int i) {

    }

    @Override
    public int getInterceptedPriority() {
        return 0;
    }

    @Override
    public void setInterceptedPriority(int i) {

    }

    @Override
    public XmlTest getXmlTest() {
        return null;
    }

    @Override
    public ConstructorOrMethod getConstructorOrMethod() {
        return null;
    }

    @Override
    public Map<String, String> findMethodParameters(XmlTest xmlTest) {
        return null;
    }

    @Override
    public String getQualifiedName() {
        return null;
    }

    public int compareTo(Object o) {
        return 0;
    }
}
