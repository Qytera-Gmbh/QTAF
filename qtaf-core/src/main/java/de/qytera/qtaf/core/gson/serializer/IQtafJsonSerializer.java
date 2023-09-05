package de.qytera.qtaf.core.gson.serializer;

/**
 * An interface describing objects which implement JSON serialization.
 */
public interface IQtafJsonSerializer {
    /**
     * Returns the class of the serialized object.
     *
     * @return the serialized class
     */
    Class<?> getSerializedObjectClass();
}
