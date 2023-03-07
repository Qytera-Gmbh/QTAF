package de.qytera.qtaf.core.reflection;

import javax.lang.model.type.NullType;
import java.lang.reflect.Method;
import java.util.*;

public class ClassHelper {
    /**
     * Return all superclasses of the given object until stopAt is reached
     * @param object    The object that should be inspected
     * @param stopAt    The final superclass at which the algorithm should stop
     * @return  List of superclasses of the given object
     */
    public static Set<Class<?>> getSuperclasses(Object object, Class<?> stopAt) {
        Set<Class<?>> set = new HashSet<>();
        Class<?> clazz = object.getClass().getSuperclass();

        while (clazz != null && !stopAt.getName().equals(clazz.getName())) {
            set.add(clazz);
            clazz = clazz.getSuperclass();
        }

        return set;
    }

    /**
     * Return all superclasses of a given object
     * @param object    The object that should be inspected
     * @return  All superclasses of the object
     */
    public static Set<Class<?>> getSuperclasses(Object object) {
        return ClassHelper.getSuperclasses(object, NullType.class);
    }

    public static Set<Class<?>> getInterfaces(Object object) {
        Set<Class<?>> superclasses = ClassHelper.getSuperclasses(object);
        Set<Class<?>> allInterfaces = new HashSet<>(List.of(object.getClass().getInterfaces()));

        for (Class<?> sc: superclasses) {
            Class<?>[] interfaces = sc.getInterfaces();
            allInterfaces.addAll(Arrays.asList(interfaces));
        }

        return allInterfaces;
    }

    /**
     * Get all superclasses and all interfaces that an object implements
     * @param object    The inspected object
     * @return  Set of all superclasses and implemented interfaces
     */
    public static Set<Class<?>> getSuperclassesAndInterfaces(Object object) {
        Set<Class<?>> superclasses = ClassHelper.getSuperclasses(object);
        Set<Class<?>> interfaces = ClassHelper.getInterfaces(object);

        superclasses.addAll(interfaces);
        return superclasses;
    }

    /**
     * Check if a provided list of parameters can be used for a given method
     * @param m         The method that should be inspected
     * @param params    The parameters that should be inspected
     * @return  True if the provided parameters can be used for the provided method
     */
    public static boolean parametersSuitableForMethod(Method m, Object[] params) {
        Class<?>[] paramTypes = m.getParameterTypes();

        // If the number of provided parameters doesn't match the number of accepted parameters of the method there can be no match
        if (params.length != paramTypes.length) {
            return false;
        }

        for (int i = 0; i < paramTypes.length; i++) {
            if (!paramTypes[i].isInstance(params[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Find all methods in a class that match a given list of parameters. The parameters have to be provided in the order
     * as they are passes to the method.
     * @param clazz         The class that should be inspected
     * @param params        The parameters that should be provided to a method
     * @param methodName    Optionally you can provide a method name here that the desired method should have, otherwise set this attribute to null
     * @return  All method that can handle the provided list of parameters
     */
    public static List<Method> findSuitableMethods(Class<?> clazz, Object[] params, String methodName) {
        List<Method> methods = new ArrayList<>();

        for (Method m: clazz.getMethods()) {
            // If a desired name for a method is given check if the currently expected method's name matches the given name
            if (methodName != null && !methodName.equals(m.getName())) {
                continue;
            }

            // Check if the provides parameters can be used for the currently inspected method
            if (ClassHelper.parametersSuitableForMethod(m, params)) {
                methods.add(m);
            }
        }

        return methods;
    }

}
