package de.qytera.qtaf.core.events.payload;

import de.qytera.qtaf.testng.events.payload.TestNGTestEventPayload;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


/**
 * Entity class for test scenario methods
 */
public class MethodInfoEntity {
    /**
     * Method reflection object
     */
    private Method method;

    /**
     * List of scenario parameter types
     */
    private Class<?>[] methodParamTypes;

    /**
     * List of scenario parameter values
     */
    private Object[] methodParamValues;

    /**
     * Method annotations
     */
    private Annotation[] annotations;

    /**
     * Constructor
     * @param method            Method object of the test scenario
     * @param methodParamTypes  Types of the scenario parameters
     * @param methodParamValues Values of the scenario parameters
     * @param annotations       Annotations of the scenario method
     */
    public MethodInfoEntity(Method method, Class<?>[] methodParamTypes, Object[] methodParamValues, Annotation[] annotations) {
        this.method = method;
        this.methodParamTypes = methodParamTypes;
        this.methodParamValues = methodParamValues;
        this.annotations = annotations;
    }

    /**
     * Get method
     *
     * @return method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Set method
     *
     * @param method Method
     * @return this
     */
    public MethodInfoEntity setMethod(Method method) {
        this.method = method;
        return this;
    }

    /**
     * Get methodParamTypes
     *
     * @return methodParamTypes
     */
    public Class<?>[] getMethodParamTypes() {
        return methodParamTypes;
    }

    /**
     * Set methodParamTypes
     *
     * @param methodParamTypes MethodParamTypes
     * @return this
     */
    public MethodInfoEntity setMethodParamTypes(Class<?>[] methodParamTypes) {
        this.methodParamTypes = methodParamTypes;
        return this;
    }

    /**
     * Get methodParamValues
     *
     * @return methodParamValues
     */
    public Object[] getMethodParamValues() {
        return methodParamValues;
    }

    /**
     * Set methodParamValues
     *
     * @param methodParamValues MethodParamValues
     * @return this
     */
    public MethodInfoEntity setMethodParamValues(Object[] methodParamValues) {
        this.methodParamValues = methodParamValues;
        return this;
    }

    /**
     * Get annotations
     *
     * @return annotations
     */
    public Annotation[] getAnnotations() {
        return annotations;
    }

    /**
     * Set annotations
     *
     * @param annotations Annotations
     * @return this
     */
    public MethodInfoEntity setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
        return this;
    }
}
