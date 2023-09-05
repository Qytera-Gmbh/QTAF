package de.qytera.qtaf.core.gson.serializer;

/**
 * All classes that provided methods that help QTAF to create JSON obejcts form Java objects
 * need ot implement this interface
 */
public interface IQtafJsonSerializer {
    /**
     * Get the class of the objects that this serializer can serialize
     * @return class of the serialized object
     */
    Class<?> getSerializedObjectClass();
}
