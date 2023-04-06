package de.qytera.qtaf.core.reflection;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Class that provides helper methods for reflective field access and manipulation
 */
public class FieldHelper {

    /**
     * Get declared fields of a class and its superclasses
     *
     * @param clazz class
     * @return fields
     */
    public static ArrayList<Field> getDeclaredFieldsRecursively(Class<?> clazz) {
        ArrayList<Field> fields = new ArrayList<>();
        getDeclaredFieldsRecursively(fields, clazz);

        return fields;
    }

    /**
     * Get declared fields of a class and its superclasses
     *
     * @param fields fields
     * @param clazz  class
     */
    private static void getDeclaredFieldsRecursively(ArrayList<Field> fields, Class<?> clazz) {
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        if (clazz.getSuperclass() != null) {
            getDeclaredFieldsRecursively(fields, clazz.getSuperclass());
        }
    }

    /**
     * Get field value of given object.
     * This method can be used to access private fields of objects.
     * If the field is static this method will return null.
     *
     * @param object    Object
     * @param fieldName Field name
     * @return Field value on success, null otherwise
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getFieldByName(fieldName, object.getClass());

        if (field == null) {
            return null;
        }

        return getFieldValue(object, field);
    }

    /**
     * Get a field reflection object of a given object by a class object
     *
     * @param name  field name
     * @param clazz Object class
     * @return Field reflection object on success, false otherwise
     */
    public static Field getFieldByName(String name, Class<?> clazz) {
        List<Field> fields = getDeclaredFieldsRecursively(clazz);
        Optional<Field> fieldOptional = fields.stream().filter(field -> field.getName().equals(name)).findAny();

        try {
            return fieldOptional.get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Check if field can be accessed
     *
     * @param field  Field reflection object
     * @param object Object that has the field
     * @return true if accessible, false otherwise
     * @throws IllegalArgumentException Thrown when trying to access static fields
     */
    public static boolean canAccess(Field field, Object object) throws IllegalArgumentException {
        return field.canAccess(object);
    }

    /**
     * Get value of an object field using the Reflection API
     *
     * @param field  Field reflection object
     * @param object Object that has the field
     * @return Field value on success, null otherwise
     */
    public static Object getFieldValue(Object object, Field field) {
        try {
            boolean accessible = field.canAccess(object);

            // Make field accessible
            field.setAccessible(true);

            // Get field instance
            Object value = field.get(object);
            field.setAccessible(accessible);
            return value;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Set value of an object field using the Reflection API
     *
     * @param object Object that has the field
     * @param field  Field reflection object
     * @param value  New value of the field
     * @return Field value on success, null otherwise
     */
    public static boolean setFieldValue(Object object, Field field, Object value) {
        try {
            boolean accessible = field.canAccess(object);

            // Make field accessible
            field.setAccessible(true);

            // Get field instance
            field.set(object, value);
            field.setAccessible(accessible);
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    /**
     * Set value of an object field using the Reflection API
     *
     * @param object    Object that has the field
     * @param fieldName Field's name
     * @param value     New value of the field
     * @return Field value on success, null otherwise
     */
    public static boolean setFieldValue(Object object, String fieldName, Object value) {
        Field field = FieldHelper.getFieldByName(fieldName, object.getClass());
        assert field != null;

        try {
            boolean accessible = field.canAccess(object);

            // Make field accessible
            field.setAccessible(true);

            // Get field instance
            field.set(object, value);
            field.setAccessible(accessible);
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}
