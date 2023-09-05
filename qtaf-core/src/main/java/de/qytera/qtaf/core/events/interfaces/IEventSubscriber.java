package de.qytera.qtaf.core.events.interfaces;

/**
 * Interface that all event subscribers have to implement.
 */
public interface IEventSubscriber {
    /**
     * Code to run on event initialization.
     */
    void initialize();
}
