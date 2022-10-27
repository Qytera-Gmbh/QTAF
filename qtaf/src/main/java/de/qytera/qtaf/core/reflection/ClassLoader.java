package de.qytera.qtaf.core.reflection;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

public class ClassLoader {
    /**
     * package names that are searched for classes
     */
    private static List<String> packageNames = new ArrayList<String>(
            Collections.singletonList("de.qytera.qtaf")
    );

    /**
     * Class instances are saved in this map
     */
    private static final Map<String, Object> instancesByClassName = new HashMap<>();

    /**
     * Class type index
     */
    private static final Map<Type, Object[]> instancesByType = new HashMap<>();

    /**
     * Add a package that can be search for classes
     * @param packageName   Package name
     */
    public static void addPackageName(String packageName) {
        if (!packageNames.contains(packageName)) {
            packageNames.add(packageName);
        }
    }

    /**
     * Check if list already contains package name
     * @param packageName   Package name
     * @return  true if package name is already in the list, false otherwise
     */
    public static boolean hasPackageName(String packageName) {
        return packageNames.contains(packageName);
    }

    /**
     * CRemove package name form the list
     * @param packageName   Package name
     * @return  true if package name was in the list, false otherwise
     */
    public static boolean removePackageName(String packageName) {
        if (hasPackageName(packageName)) {
            return packageNames.remove(packageName);
        }

        return false;
    }

    /**
     * Get a set of classes that are derived from the given class
     * @param clazz sub type
     * @return      set of classes
     */
    public static Set<Class<?>> getSubTypesOfRecursively(
            Class<?> clazz,
            Set<Class<?>> classes,
            List<String> packageNames
    ) {
        // Get all classes that are directly derived from the given class
        if (classes == null) {
            classes = new HashSet<>();
        }

        if (packageNames == null) {
            packageNames = List.of();
        }

        Set<Class<?>> newClasses = getSubTypesOf(clazz, packageNames);
        classes.addAll(newClasses);

        // Repeat this step recursively for the found sub classes
        for (Class<?> subClass : newClasses) {
            classes.addAll(getSubTypesOfRecursively(subClass, classes, packageNames));
        }

        return classes;
    }

    /**
     * Get a set of classes that are directly derived from the given class
     * @param clazz         sub type
     * @param packageNames  package names where to search for this class
     * @return      set of classes
     */
    public static Set<Class<?>> getSubTypesOf(Class<?> clazz, List<String> packageNames) {
        if (packageNames != null) {
            for (String packageName: packageNames) {
                addPackageName(packageName);
            }
        }

        Set<Class<?>> classes = new HashSet<>();

        // Find all classes that implement or extend the given interface / class
        for (String packageName : packageNames) {
            try {
                Reflections ref = new Reflections(packageName);
                classes.addAll(ref.getSubTypesOf((Class<Object>) clazz));
            } catch (Exception e) {
                // This exception occurs when no matching class is found in the package
                // It#s no critical exception, just continue with the next package
                continue;
            }
        }

        return classes;
    }

    /**
     * Get a set of classes that are directly derived from the given class
     * @param clazz sub type
     * @return      set of classes
     */
    public static Set<Class<?>> getSubTypesOf(Class<?> clazz) {
        ConfigMap configMap = QtafFactory.getConfiguration();

        List<String> configPackageNames = (List<String>) configMap.getArray("framework.packageNames");
        configPackageNames.add(configMap.getString("tests.package", "de.qytera.qtaf"));

        if (configPackageNames != null) {
            for (String packageName: configPackageNames) {
                addPackageName(packageName);
            }
        }

        return ClassLoader.getSubTypesOf(clazz, ClassLoader.packageNames);
    }

    /**
     * Get all sub types of a given class
     * @param clazz Instance class type
     * @return      Instances
     */
    public static Object[] getInstancesOfDirectSubtypesOf(Class<?> clazz) {
        // Check if classes of teh given type were already loaded
        if (instancesByType.get(clazz) != null) {
            return instancesByType.get(clazz);
        }

        // Find all classes that implement or extend the given interface / class
        Set<Class<?>> classes = getSubTypesOf(clazz);

        // Instances by type list
        List<Object> newInstancesByType = new ArrayList<>();

        // Loop over classes and create an instance of each class
        for (Class<?> c : classes) {
            try {
                if (instancesByClassName.get(c.getName()) == null) { // Try to load instance from internal cache
                    // Get instance
                    Object newInstance = ClassLoader.getInstance(c);

                    // Skip abstract classes
                    if (newInstance == null) {
                        continue;
                    }

                    // Add instance to class name map
                    instancesByClassName.put(c.getName(), newInstance);

                    // Add instance to type map
                    newInstancesByType.add(newInstance);
                } else {
                    newInstancesByType.add(instancesByClassName.get(c.getName()));
                }
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        // Save loaded instances
        instancesByType.put(clazz, newInstancesByType.toArray());

        return newInstancesByType.toArray();
    }

    /**
     * Get instance of class
     * @param c Class
     * @return  instance
     */
    public static Object getInstance(Class<?> c) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        // Get class constructor instance
        Constructor<?> constructor = (Constructor<?>) c.getConstructor();

        // Skip abstract classes
        if (Modifier.isAbstract(c.getModifiers()))
            return null;

        // Get instance
        return constructor.newInstance();
    }
}
