package de.qytera.qtaf.core.cucumber.entity;

import de.qytera.qtaf.cucumber.entity.QTAFCucumberScenarioEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Tests for the cucumber scenario entity
 */
public class QtafCucumberScenarioEntityTest {
    @Test
    public void testAddFeatureTag() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        Assert.assertEquals(entity.getFeatureTags().size(), 0);
        entity.addFeatureTag("foo", "bar");
        Assert.assertEquals(entity.getFeatureTags().size(), 1);
    }

    @Test
    public void testHasFeatureTag() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        Assert.assertFalse(entity.hasFeatureTag("foo"));
        entity.addFeatureTag("foo", "bar");
        Assert.assertTrue(entity.hasFeatureTag("foo"));
    }

    @Test
    public void testGetFeatureTag() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        Assert.assertNull(entity.getFeatureTag("foo"));
        entity.addFeatureTag("foo", "bar");
        Assert.assertEquals(entity.getFeatureTag("foo"), "bar");
    }

    @Test
    public void testRemoveFeatureTag() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        Assert.assertNull(entity.getFeatureTag("foo"));
        Assert.assertEquals(entity.getFeatureTags().size(), 0);
        entity.addFeatureTag("foo", "bar");
        Assert.assertEquals(entity.getFeatureTag("foo"), "bar");
        Assert.assertEquals(entity.getFeatureTags().size(), 1);
        entity.removeFeatureTag("foo");
        Assert.assertNull(entity.getFeatureTag("foo"));
        Assert.assertEquals(entity.getFeatureTags().size(), 0);
    }

    @Test
    public void testAddScenarioTag() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        Assert.assertEquals(entity.getScenarioTags().size(), 0);
        entity.addScenarioTag("foo", "bar");
        Assert.assertEquals(entity.getScenarioTags().size(), 1);
    }

    @Test
    public void testHasScenarioTag() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        Assert.assertFalse(entity.hasScenarioTag("foo"));
        entity.addScenarioTag("foo", "bar");
        Assert.assertTrue(entity.hasScenarioTag("foo"));
    }

    @Test
    public void testGetScenarioTag() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        Assert.assertNull(entity.getScenarioTag("foo"));
        entity.addScenarioTag("foo", "bar");
        Assert.assertEquals(entity.getScenarioTag("foo"), List.of("bar"));
    }

    @Test
    public void testRemoveScenarioTag() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        Assert.assertNull(entity.getScenarioTag("foo"));
        Assert.assertEquals(entity.getScenarioTags().size(), 0);
        entity.addScenarioTag("foo", "bar");
        Assert.assertEquals(entity.getScenarioTag("foo"), List.of("bar"));
        Assert.assertEquals(entity.getScenarioTags().size(), 1);
        entity.removeScenarioTag("foo");
        Assert.assertNull(entity.getScenarioTag("foo"));
        Assert.assertEquals(entity.getScenarioTags().size(), 0);
    }

    @Test
    public void testParseTestSetTag() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();
        Assert.assertEquals(entity.getGroupNames().size(), 0);

        entity.addScenarioTag("TestSet", "QTAF-1, QTAF-2");
        entity.parseTestSetTags();
        Assert.assertEquals(entity.getTestSets().size(), 2);
        Assert.assertEquals(entity.getTestSets().get(0), "QTAF-1");
        Assert.assertEquals(entity.getTestSets().get(1), "QTAF-2");
    }

    @Test
    public void testParseMultipleTestSetTags() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();
        Assert.assertEquals(entity.getTestSets().size(), 0);

        entity.addScenarioTag("TestSet", "QTAF-1 , QTAF-2");
        entity.addScenarioTag("TestSet", "QTAF-3");
        entity.addScenarioTag("TestSet", "QTAF-4,QTAF-5");

        entity.parseTestSetTags();
        Assert.assertEquals(entity.getTestSets().size(), 5);
        Assert.assertEquals(entity.getTestSets().get(0), "QTAF-1");
        Assert.assertEquals(entity.getTestSets().get(1), "QTAF-2");
        Assert.assertEquals(entity.getTestSets().get(2), "QTAF-3");
        Assert.assertEquals(entity.getTestSets().get(3), "QTAF-4");
        Assert.assertEquals(entity.getTestSets().get(4), "QTAF-5");
    }

    @Test
    public void testAddTestSet() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();
        Assert.assertEquals(entity.getTestSets().size(), 0);
        entity.addTestSet("foo");
        Assert.assertEquals(entity.getTestSets().size(), 1);
        Assert.assertEquals(entity.getTestSets().get(0), "foo");
    }

    @Test
    public void testHasTestSet() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();
        Assert.assertFalse(entity.belongsToTestSet("foo"));
        entity.addTestSet("foo");
        Assert.assertTrue(entity.belongsToTestSet("foo"));
    }

    @Test
    public void testBelongsToAllTestSets() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        entity.addTestSet("foo");
        entity.addTestSet("bar");

        List<String> l1 = List.of("foo");
        Assert.assertTrue(entity.belongsToAllTestSets(l1));

        List<String> l2 = List.of("bar");
        Assert.assertTrue(entity.belongsToAllTestSets(l2));

        List<String> l3 = List.of("foo", "bar");
        Assert.assertTrue(entity.belongsToAllTestSets(l3));

        List<String> l4 = List.of("foo", "bar", "foobar");
        Assert.assertFalse(entity.belongsToAllTestSets(l4));
    }

    @Test
    public void testBelongsToAnyTestSet() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        entity.addTestSet("foo");

        List<String> l1 = List.of("foo");
        Assert.assertTrue(entity.belongsToAnyTestSet(l1));

        List<String> l2 = List.of("bar");
        Assert.assertFalse(entity.belongsToAnyTestSet(l2));

        List<String> l3 = List.of("foo", "bar");
        Assert.assertTrue(entity.belongsToAnyTestSet(l3));

        List<String> l4 = List.of("bar", "foobar");
        Assert.assertFalse(entity.belongsToAnyTestSet(l4));

        List<String> l5 = List.of("foo", "bar", "foobar");
        Assert.assertTrue(entity.belongsToAnyTestSet(l5));
    }

    @Test
    public void testRemoveTestSet() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();
        Assert.assertFalse(entity.belongsToTestSet("foo"));
        entity.addTestSet("foo");
        Assert.assertTrue(entity.belongsToTestSet("foo"));
        entity.removeTestSet("foo");
        Assert.assertFalse(entity.belongsToTestSet("foo"));
    }

    @Test
    public void testParseGroupTag() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();
        Assert.assertEquals(entity.getGroupNames().size(), 0);

        entity.addScenarioTag("Groups", "foo , bar");
        entity.parseGroupTags();
        Assert.assertEquals(entity.getGroupNames().size(), 2);
        Assert.assertEquals(entity.getGroupNames().get(0), "foo");
        Assert.assertEquals(entity.getGroupNames().get(1), "bar");
    }

    @Test
    public void testParseMultipleGroupTags() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();
        Assert.assertEquals(entity.getGroupNames().size(), 0);

        entity.addScenarioTag("Groups", "foo , bar");
        entity.addScenarioTag("Groups", "x,y,  z");
        entity.parseGroupTags();
        Assert.assertEquals(entity.getGroupNames().size(), 5);
        Assert.assertEquals(entity.getGroupNames().get(0), "foo");
        Assert.assertEquals(entity.getGroupNames().get(1), "bar");
        Assert.assertEquals(entity.getGroupNames().get(2), "x");
        Assert.assertEquals(entity.getGroupNames().get(3), "y");
        Assert.assertEquals(entity.getGroupNames().get(4), "z");
    }

    @Test
    public void testAddGroupName() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();
        Assert.assertEquals(entity.getGroupNames().size(), 0);
        entity.addGroupName("foo");
        Assert.assertEquals(entity.getGroupNames().size(), 1);
        Assert.assertEquals(entity.getGroupNames().get(0), "foo");
    }

    @Test
    public void testHasGroupName() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();
        Assert.assertFalse(entity.hasGroupName("foo"));
        entity.addGroupName("foo");
        Assert.assertTrue(entity.hasGroupName("foo"));
    }

    @Test
    public void testHasAllGroupNames() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        entity.addGroupName("foo");
        entity.addGroupName("bar");

        List<String> l1 = List.of("foo");
        Assert.assertTrue(entity.hasAllGroupNames(l1));

        List<String> l2 = List.of("bar");
        Assert.assertTrue(entity.hasAllGroupNames(l2));

        List<String> l3 = List.of("foo", "bar");
        Assert.assertTrue(entity.hasAllGroupNames(l3));

        List<String> l4 = List.of("foo", "bar", "foobar");
        Assert.assertFalse(entity.hasAllGroupNames(l4));
    }

    @Test
    public void testHasAnyGroupName() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();

        entity.addGroupName("foo");

        List<String> l1 = List.of("foo");
        Assert.assertTrue(entity.hasAnyGroupName(l1));

        List<String> l2 = List.of("bar");
        Assert.assertFalse(entity.hasAnyGroupName(l2));

        List<String> l3 = List.of("foo", "bar");
        Assert.assertTrue(entity.hasAnyGroupName(l3));

        List<String> l4 = List.of("bar", "foobar");
        Assert.assertFalse(entity.hasAnyGroupName(l4));

        List<String> l5 = List.of("foo", "bar", "foobar");
        Assert.assertTrue(entity.hasAnyGroupName(l5));
    }

    @Test
    public void testRemoveGroupName() {
        QTAFCucumberScenarioEntity entity = new QTAFCucumberScenarioEntity();
        Assert.assertFalse(entity.hasGroupName("foo"));
        entity.addGroupName("foo");
        Assert.assertTrue(entity.hasGroupName("foo"));
        entity.removeGroupName("foo");
        Assert.assertFalse(entity.hasGroupName("foo"));
    }
}
