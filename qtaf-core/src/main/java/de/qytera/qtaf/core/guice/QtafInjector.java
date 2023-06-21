package de.qytera.qtaf.core.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * This class is responsible for creating a Guice injector for the QTAF framework and guarantees that there
 * is only one instance of the injector at runtime (singleton pattern)
 */
public class QtafInjector {
    private QtafInjector() {
    }

    /**
     * Qtaf Guice Injector
     */
    private static final Injector injector = Guice.createInjector(new QtafModule());

    /**
     * Get the singleton instance of the QTAF Guice injector
     *
     * @return Guice injector
     */
    public static Injector getInstance() {
        return injector;
    }
}
