package de.qytera.qtaf.htmlreport.engine;


import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

/**
 * Template file rendering engine
 */
public class TemplateEngine {
    /**
     * Pebble template rendering engine
     */
    private static PebbleEngine engine = new PebbleEngine.Builder().build();

    /**
     * Load compiled template
     *
     * @param name template name
     * @return compiled template
     */
    public static PebbleTemplate getTemplate(String name) {
        return engine.getTemplate(name);
    }
}
