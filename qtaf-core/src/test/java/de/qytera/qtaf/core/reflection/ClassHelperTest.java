package de.qytera.qtaf.core.reflection;

import de.qytera.qtaf.core.reflection.sample3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Tests for the ClassHelper class
 */
public class ClassHelperTest {

    @Test(testName = "Find all superclasses", description = "Find all superclasses of given object")
    public void testSuperClassLoader() {
        SKlasse car = new SKlasse();
        Set<Class<?>> superclasses = ClassHelper.getSuperclasses(car);
        Assert.assertEquals(superclasses.size(), 3);
        Assert.assertTrue(superclasses.contains(Mercedes.class));
        Assert.assertTrue(superclasses.contains(Automobile.class));
        Assert.assertTrue(superclasses.contains(Object.class));
    }

    @Test(
            testName = "Find all superclasses and stop at Object class",
            description = "Find all superclasses of given object but stop at a given class"
    )
    public void testSuperClassLoaderWithStop() {
        SKlasse car = new SKlasse();
        Set<Class<?>> superclasses = ClassHelper.getSuperclasses(car, Object.class);
        Assert.assertEquals(superclasses.size(), 2);
        Assert.assertTrue(superclasses.contains(Mercedes.class));
        Assert.assertTrue(superclasses.contains(Automobile.class));
        Assert.assertFalse(superclasses.contains(Object.class));
    }

    @Test(
            testName = "Find all interfaces of class",
            description = "Find all interfaces that a given object implements"
    )
    public void testFindInterfaces() {
        SKlasse car = new SKlasse();
        Set<Class<?>> interfaces = ClassHelper.getInterfaces(car);
        Assert.assertEquals(interfaces.size(), 3);
        Assert.assertTrue(interfaces.contains(SteeringWheel.class));
        Assert.assertTrue(interfaces.contains(FourWheels.class));
        Assert.assertTrue(interfaces.contains(GermanCar.class));
    }

    @Test(
            testName = "Find all superclasses and interfaces of a class",
            description = "Find all superclasses and interfaces that a given object implements"
    )
    public void testFindSuperclassesAndInterfaces() {
        SKlasse car = new SKlasse();
        Set<Class<?>> superclassesInterfaces = ClassHelper.getSuperclassesAndInterfaces(car);
        Assert.assertEquals(superclassesInterfaces.size(), 6);
        Assert.assertTrue(superclassesInterfaces.contains(Mercedes.class));
        Assert.assertTrue(superclassesInterfaces.contains(Automobile.class));
        Assert.assertTrue(superclassesInterfaces.contains(Object.class));
        Assert.assertTrue(superclassesInterfaces.contains(SteeringWheel.class));
        Assert.assertTrue(superclassesInterfaces.contains(FourWheels.class));
        Assert.assertTrue(superclassesInterfaces.contains(GermanCar.class));
    }

    @Test(
            testName = "Find suitable class methods",
            description = "Find all methods of a class that can handle a given list of parameters"
    )
    public void testParametersSuitableForMethod() {
        SKlasse car = new SKlasse();

        List<Method> methods = ClassHelper.findSuitableMethods(Driver.class, new Object[]{car}, null);
        Assert.assertEquals(methods.size(), 5);

        methods = ClassHelper.findSuitableMethods(Driver.class, new Object[]{car}, "drivesCar");
        Assert.assertEquals(methods.size(), 3);
    }
}
