package de.qytera.qtaf.cucumber;

import de.qytera.qtaf.cucumber.helper.CucumberTagHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Tests for the CucumberTagHelper class
 */
public class CucumberTagHelperTest {
    @Test
    public void testGetKeyValuePairs() {
        List<String> tags = List.of("@key1:value1", "@key2:value2", "key3:value3");
        Map<String, String> result = CucumberTagHelper.getKeyValuePairs(tags);

        Assert.assertEquals(
                result.size(),
                2,
                "Expect that there are two entries, tags without leading '@' should be ignored"
        );

        Assert.assertEquals(
                result.get("key1"),
                "value1",
                "Expect value of key1 is value1"
        );

        Assert.assertEquals(
                result.get("key2"),
                "value2",
                "Expect value of key2 is value2"
        );
    }

    @Test
    public void testGetKeyValueListPairs() {
        List<String> tags = List.of("@TestSet:set1", "@TestSet:set2", "@TestSet:set3", "@Group:group1", "@Group:group2");
        Map<String, List<String>> result = CucumberTagHelper.getKeyValueListPairs(tags);

        Assert.assertEquals(
                result.size(),
                2,
                "Expect to find two groups of tags"
        );

        Assert.assertEquals(
                result.get("TestSet").size(),
                3,
                "Expect to find three 'TestSet' tags"
        );

        Assert.assertEquals(
                result.get("TestSet").get(0),
                "set1",
                "Expect value of first 'TestSet' tag to be 'set1'"
        );

        Assert.assertEquals(
                result.get("TestSet").get(1),
                "set2",
                "Expect value of second 'TestSet' tag to be 'set2'"
        );

        Assert.assertEquals(
                result.get("TestSet").get(2),
                "set3",
                "Expect value of third 'TestSet' tag to be 'set3'"
        );

        Assert.assertEquals(
                result.get("Group").size(),
                2,
                "Expect to find two 'Group' tags"
        );

        Assert.assertEquals(
                result.get("Group").get(0),
                "group1",
                "Expect value of first 'Group' tag to be 'group1'"
        );

        Assert.assertEquals(
                result.get("Group").get(1),
                "group2",
                "Expect value of first 'Group' tag to be 'group2'"
        );
    }

    @Test
    public void testGetTestID() {
        List<String> tags = List.of("@key1:value1", "@key2:value2", "@TestName:value3");

        Assert.assertEquals(
                CucumberTagHelper.getTestId(tags),
                "value3",
                "Assert that the TestName tag gets identified correctly and that its value is returned"
        );
    }
}
