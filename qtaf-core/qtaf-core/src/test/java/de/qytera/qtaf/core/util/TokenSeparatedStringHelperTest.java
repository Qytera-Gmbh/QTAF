package de.qytera.qtaf.core.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Tests for TokenSeparatedStringHelper methods
 */
public class TokenSeparatedStringHelperTest {
    @Test
    public void testCommaSeparatedStringWithOneValue() {
        String in = "foo";
        String separator = ",";
        List<String> result = TokenSeparatedStringHelper.toList(in, separator);

        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0), "foo");
    }

    @Test
    public void testCommaSeparatedStringWithMultipleValues() {
        String in = "foo,bar,foobar";
        String separator = ",";
        List<String> result = TokenSeparatedStringHelper.toList(in, separator);

        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "foo");
        Assert.assertEquals(result.get(1), "bar");
        Assert.assertEquals(result.get(2), "foobar");
    }

    @Test
    public void testCommaSeparatedStringWithDifferentSeparators() {
        String in = "foo,bar;foobar";

        String separator = ",";
        List<String> result = TokenSeparatedStringHelper.toList(in, separator);

        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(0), "foo");
        Assert.assertEquals(result.get(1), "bar;foobar");

        // Change separator from "," to ";"
        separator = ";";
        result = TokenSeparatedStringHelper.toList(in, separator);

        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(0), "foo,bar");
        Assert.assertEquals(result.get(1), "foobar");
    }

    @Test
    public void testTrimmingDisabled() {
        String in = "foo , bar , foobar";
        String separator = ",";

        // Trimming enabled
        List<String> result = TokenSeparatedStringHelper.toList(in, separator, false);

        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "foo ");
        Assert.assertEquals(result.get(1), " bar ");
        Assert.assertEquals(result.get(2), " foobar");
    }

    @Test()
    public void testTrimmingEnabled() {
        String in = "foo , bar , foobar";
        String separator = ",";

        // Trimming enabled
        List<String> result = TokenSeparatedStringHelper.toList(in, separator, true);

        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "foo");
        Assert.assertEquals(result.get(1), "bar");
        Assert.assertEquals(result.get(2), "foobar");
    }

    @Test()
    public void testTrimmingDefaultBehaviour() {
        String in = "foo , bar , foobar";
        String separator = ",";

        // Trimming should be enabled by default
        List<String> result = TokenSeparatedStringHelper.toList(in, separator);

        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0), "foo");
        Assert.assertEquals(result.get(1), "bar");
        Assert.assertEquals(result.get(2), "foobar");
    }
}
