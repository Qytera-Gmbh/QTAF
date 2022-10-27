package de.qytera.qtaf.core.reflection;

import de.qytera.qtaf.core.reflection.sample1.SampleChildOne;
import de.qytera.qtaf.core.reflection.sample1.SampleGrandChildOne;
import de.qytera.qtaf.core.reflection.sample1.SampleParent;
import de.qytera.qtaf.core.reflection.sample2.SampleChildTwo;
import de.qytera.qtaf.core.reflection.sample2.SampleGrandChildTwo;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

/**
 * These tests test the Qtaf class loader class
 */
public class ClassLoaderTest {
    @Test
    public void testSubTypeLoaderParentOne() {
        // This should only find children of SampleParent in package 'sample1'
        Set<Class<?>> classes = ClassLoader.getSubTypesOf(
                SampleParent.class,
                List.of("de.qytera.qtaf.core.reflection.sample1")
        );

        Assert.assertEquals(classes.size(), 2);

        Assert.assertTrue(classes.contains(SampleChildOne.class));
        Assert.assertTrue(classes.contains(SampleGrandChildOne.class));

        Assert.assertFalse(classes.contains(SampleChildTwo.class));
        Assert.assertFalse(classes.contains(SampleGrandChildTwo.class));
    }

    @Test
    public void testSubTypeLoaderParentTwo() {
        // This should only find children of SampleParent in package 'sample2'
        Set<Class<?>> classes = ClassLoader.getSubTypesOf(
                SampleParent.class,
                List.of("de.qytera.qtaf.core.reflection.sample2")
        );

        Assert.assertEquals(classes.size(), 2);

        Assert.assertFalse(classes.contains(SampleChildOne.class));
        Assert.assertFalse(classes.contains(SampleGrandChildOne.class));

        Assert.assertTrue(classes.contains(SampleChildTwo.class));
        Assert.assertTrue(classes.contains(SampleGrandChildTwo.class));
    }

    @Test
    public void testSubTypeLoaderParentOneAndTwo() {
        // This should find children of SampleParent in both packages 'sample1' and 'sample2
        Set<Class<?>> classes = ClassLoader.getSubTypesOf(
                SampleParent.class,
                List.of("de.qytera.qtaf.core.reflection")
        );

        Assert.assertEquals(classes.size(), 4);

        Assert.assertTrue(classes.contains(SampleChildOne.class));
        Assert.assertTrue(classes.contains(SampleGrandChildOne.class));

        Assert.assertTrue(classes.contains(SampleChildTwo.class));
        Assert.assertTrue(classes.contains(SampleGrandChildTwo.class));
    }
}
