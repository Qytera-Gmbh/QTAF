package de.qytera.qtaf.core.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.guice.annotations.Step;
import de.qytera.qtaf.core.gson.serializer.StepAnnotationSerializer;
import de.qytera.qtaf.core.gson.serializer.TestCaseAnnotationSerializer;
import de.qytera.qtaf.core.gson.serializer.ThrowableSerializer;

/**
 * Generate GSON object with custom serializers
 */
public class GsonFactory {
    /**
     * GSON singleton instance
     */
    private static Gson instance = null;

    /**
     * Get singleton GSOn instance
     * @return  GSON singleton instance
     */
    public static Gson getInstance() {
        if (instance == null) {
            // Create instance of Gson builder
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setPrettyPrinting();

            // Register custom serializers
            gsonBuilder.registerTypeAdapter(TestFeature.class, new TestCaseAnnotationSerializer());
            gsonBuilder.registerTypeAdapter(Step.class, new StepAnnotationSerializer());
            gsonBuilder.registerTypeAdapter(Throwable.class, new ThrowableSerializer());

            // Get Gson instance
            instance = gsonBuilder.create();
        }

        return instance;
    }
}
