package de.qytera.qtaf.core.context;

import de.qytera.qtaf.core.reflection.FieldHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Helper methods for ITestContext implementations
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestContextHelper {

    /**
     * Add logger to all instance fields
     *
     * @param testContext the test context
     */
    public static void addLoggerToFieldsRecursively(IQtafTestContext testContext) {
        // Get all declared fields of the test context class and its super classes
        List<Field> fields = FieldHelper.getDeclaredFieldsRecursively(
                testContext.getClass()
        );

        // Iterate over fields
        for (Field field : fields) {
            // Check if the field is currently accessible
            boolean accessible = false;

            try {
                // Check if field can be accessed (is public)
                accessible = field.canAccess(testContext);
            } catch (IllegalArgumentException e) { // occurs if field is static
                // Static fields can be ignored
                continue;
            }

            // Make field accessible
            field.setAccessible(true);

            try {
                // Get field instance
                Object fieldObject = field.get(testContext);

                // Check if field is instance of TestContext
                if (fieldObject instanceof IQtafTestContext fieldContext) {
                    fieldContext.setLogCollection(testContext.getLogCollection());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // Reverse access modification
            field.setAccessible(accessible);
        }
    }
}
