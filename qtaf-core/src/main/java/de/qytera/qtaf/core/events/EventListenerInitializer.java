package de.qytera.qtaf.core.events;

import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.core.reflection.ClassLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that initializes the Qtaf event system
 */
public class EventListenerInitializer {
    /**
     * Parameter that saves the initialization status
     */
    private static boolean isInitialized = false;

    /**
     * Event listener instances are saved in this map
     */
    private static final Map<String, IEventSubscriber> instances = new HashMap<>();

    /**
     * Private constructor, there should be no instances if this class at runtime
     */
    private EventListenerInitializer() {
        super();
    }

    /**
     * Initialize the event system
     */
    public static void initialize() {
        // If already initialized there is nothing to od
        if (isInitialized) {
            return;
        }

        // Get instances of all classes that implement the event subscriber interface
        Object[] lst = ClassLoader.getInstancesOfDirectSubtypesOf(IEventSubscriber.class);

        // Initialize event subscriber objects and save them internally
        for (Object o : lst) {
            IEventSubscriber es = (IEventSubscriber) o;
            es.initialize();
            instances.put(es.getClass().getName(), es);
        }

        // Save initialization status
        isInitialized = true;
    }
}
