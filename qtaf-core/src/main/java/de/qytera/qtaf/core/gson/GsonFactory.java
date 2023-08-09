package de.qytera.qtaf.core.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.gson.serializer.IQtafJsonSerializer;
import de.qytera.qtaf.core.log.model.error.ErrorLogCollection;
import de.qytera.qtaf.core.reflection.ClassLoader;
import org.openqa.selenium.InvalidArgumentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generate GSON object with custom serializers
 */
public class GsonFactory {
    private GsonFactory() {
    }

    /**
     * Private Constructor
     */
    private GsonFactory() {
    }

    /**
     * GSON singleton instance
     */
    private static Gson instance = null;

    private static Gson instanceWithoutCustomSerializers = null;

    /**
     * JsonSerializer instances are saved in this map
     */
    private static final Map<String, IQtafJsonSerializer> serializers = new HashMap<>();

    /**
     * ExclusionStrategy instances are saved in this map
     */
    private static final List<ExclusionStrategy> exclusionStrategies = new ArrayList<>();

    /**
     * Get a GSON instance without custom serializers. Sometimes this is needed, especially in the configuration class,
     * because otherwise you would have recursive and endless initialization loops.
     *
     * @return GSON instance without serializers
     */
    public static Gson getInstanceWithoutCustomSerializers() {
        if (instanceWithoutCustomSerializers == null) {
            // Create instance of Gson builder
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setPrettyPrinting();

            // Get Gson instance
            instanceWithoutCustomSerializers = gsonBuilder.create();
        }

        return instanceWithoutCustomSerializers;
    }

    /**
     * Get singleton GSOn instance
     *
     * @return GSON singleton instance
     */
    public static Gson getInstance() {
        if (instance == null) {
            // Initialize serializers
            loadSerializers();

            // Load exclusion strategies
            loadExclusionStrategies();

            // Create instance of Gson builder
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setPrettyPrinting();

            // Iterate over JSON serializers and add them to GSON
            for (Map.Entry<String, IQtafJsonSerializer> entry : serializers.entrySet()) {
                gsonBuilder.registerTypeAdapter(entry.getValue().getSerializedObjectClass(), entry.getValue());
            }

            // Set exclusion strategies
            gsonBuilder.setExclusionStrategies(exclusionStrategies.toArray(new ExclusionStrategy[0]));

            // Get Gson instance
            instance = gsonBuilder.create();
        }

        return instance;
    }

    /**
     * Initialize the serializers
     */
    public static void loadSerializers() {
        // Get instances of all classes that implement the IQtafJsonSerializer interface
        try {
            Object[] lst = ClassLoader.getInstancesOfDirectSubtypesOf(IQtafJsonSerializer.class);

            // Initialize IQtafJsonSerializer objects and save them internally
            for (Object o : lst) {
                IQtafJsonSerializer s = (IQtafJsonSerializer) o;

                // Check if return value of getSerializedObjectClass() is not null
                if (s.getSerializedObjectClass() == null) {
                    throw new InvalidArgumentException(s.getClass().getName() + " should declare a type for which it is responsible");
                }

                serializers.put(s.getClass().getName(), s);
            }
        } catch (Exception e) {
            QtafFactory.getLogger().error("GSON Initialization Error");
            QtafFactory.getLogger().error(e.getMessage());
            ErrorLogCollection.getInstance().addErrorLog(e);
        }
    }

    /**
     * Initialize the exclusion strategies
     */
    public static void loadExclusionStrategies() {
        // Get instances of all classes that implement the ExclusionStrategy interface
        try {
            Object[] lst = ClassLoader.getInstancesOfDirectSubtypesOf(ExclusionStrategy.class);

            // Initialize exclusion strategy objects and save them internally
            for (Object o : lst) {
                ExclusionStrategy s = (ExclusionStrategy) o;
                exclusionStrategies.add(s);
            }
        } catch (Exception e) {
            QtafFactory.getLogger().error("GSON Initialization Error");
            QtafFactory.getLogger().error(e.getMessage());
            ErrorLogCollection.getInstance().addErrorLog(e);
        }
    }
}
