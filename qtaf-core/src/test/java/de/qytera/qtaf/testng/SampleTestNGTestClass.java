package de.qytera.qtaf.testng;

import org.testng.IClass;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlTest;

public class SampleTestNGTestClass implements IClass {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public XmlTest getXmlTest() {
        return null;
    }

    @Override
    public XmlClass getXmlClass() {
        return null;
    }

    @Override
    public String getTestName() {
        return null;
    }

    @Override
    public Class<?> getRealClass() {
        return SampleRealClassTest.class;
    }

    @Override
    public Object[] getInstances(boolean b) {
        return new Object[0];
    }

    @Override
    public long[] getInstanceHashCodes() {
        return new long[0];
    }

    @Override
    public void addInstance(Object o) {

    }
}
