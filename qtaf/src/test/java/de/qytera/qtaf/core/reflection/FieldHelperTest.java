package de.qytera.qtaf.core.reflection;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Test the FieldHelper class
 */
public class FieldHelperTest {
    /**
     * Read the value of a private field
     */
    @Test
    public void testReadPrivateFieldValue() {
        SampleData foo = new SampleData();

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            // Try to set private static field value by using reflection
            FieldHelper.getFieldValue(
                    foo,
                    "msgStatic"
            );
        });
    }

    /**
     * Read the value of a private static field
     */
    @Test
    public void testReadPrivateStaticFieldValue() {
        SampleData foo = new SampleData();

        // Get value by calling getter
        Assert.assertEquals(foo.getMsg(), "Hello");

        // Get value by reading private attribute via reflection
        Assert.assertEquals(FieldHelper.getFieldValue(foo, "msg"), "Hello");
    }

    /**
     * Write the value of a private field
     */
    @Test
    public void testWritePrivateFieldValue() {
        SampleData foo = new SampleData();

        // Get value by calling getter
        Assert.assertEquals(foo.getMsg(), "Hello");

        // Set private field value by using reflection
        FieldHelper.setFieldValue(
                foo,
                "msg",
                "World"
        );

        Assert.assertEquals(foo.getMsg(), "World");
    }

    /**
     * Write the value of a private field
     */
    @Test
    public void testWritePrivateStaticFieldValue() {
        SampleData foo = new SampleData();

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            // Try to set private static field value by using reflection
            FieldHelper.setFieldValue(
                    foo,
                    "msgStatic",
                    "World"
            );
        });

        // Value of static field should not have changed
        Assert.assertEquals(SampleData.getMsgStatic(), "Hello");
    }

    @Test
    public void testGetDeclaredFieldsRecursively() {
        List<Field> fields = FieldHelper.getDeclaredFieldsRecursively(SampleDataChild.class);

        // There should be the fields: msg, msgProtected and msgStatic from SampleData and msg2 from SampleDataChild
        Assert.assertEquals(fields.size(), 4);

        // Assert that the fields are ordered by their declaration and the fields of the child class
        // are at the beginning
        Assert.assertEquals(fields.get(0).getName(), "msg2");
        Assert.assertEquals(fields.get(1).getName(), "msg");
        Assert.assertEquals(fields.get(2).getName(), "msgProtected");
        Assert.assertEquals(fields.get(3).getName(), "msgStatic");
    }
}
