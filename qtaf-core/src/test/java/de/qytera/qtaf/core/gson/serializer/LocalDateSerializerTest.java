package de.qytera.qtaf.core.gson.serializer;

import com.google.gson.JsonElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class LocalDateSerializerTest {
    @Test(testName = "Test TestScenarioAnnotationSerializer")
    public void testSerializer() {
        LocalDateSerializer serializer = new LocalDateSerializer();
        LocalDate localDate = LocalDate.of(2020, 1, 3);
        JsonElement jsonElement = serializer.serialize(localDate, null, null);
        Assert.assertEquals(jsonElement.toString(), "\"2020-01-03\"");
    }


    @Test
    public void testSerializerObjectClass() {
        LocalDateSerializer serializer = new LocalDateSerializer();
        Assert.assertEquals(serializer.getSerializedObjectClass(), LocalDate.class);
    }
}
