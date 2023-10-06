package de.qytera.qtaf.htmlreport;

import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.htmlreport.creator.FeatureReportCreator;
import de.qytera.qtaf.htmlreport.creator.ReportCreator;
import de.qytera.qtaf.htmlreport.creator.ScenarioReportCreator;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.error.LoaderException;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TemplateRendererTest {
    /**
     * Check if template files exist and can be compiled
     */
    @Test
    public void testRendering() {
        String[] templateFiles = new String[]{
                "de/qytera/qtaf/htmlreport/templates/home.html",
                "de/qytera/qtaf/htmlreport/templates/feature.html",
                "de/qytera/qtaf/htmlreport/templates/scenario.html",
                "de/qytera/qtaf/htmlreport/templates/step.inc.html",
        };

        PebbleEngine engine = new PebbleEngine.Builder().build();

        for (String filename : templateFiles) {
            PebbleTemplate compiledTemplate = engine.getTemplate(filename);

            Assert.assertNotNull(compiledTemplate, "Template could not be compiled");
            Assert.assertEquals(compiledTemplate.getName(), filename);
        }
    }

    @Test
    public void testSuiteTemplateEvaluation() {
        // Generate mock log collections
        MockLogCollectionFactory.generate();

        // Get instance of test suite log collection
        TestSuiteLogCollection logCollection = TestSuiteLogCollection.getInstance();

        // Generate the report
        ReportCreator creator = new ReportCreator();
        boolean result = creator.createReport(logCollection);

        // Clear mock logs
        MockLogCollectionFactory.clear();

        Assert.assertTrue(result);
    }

    @Test
    public void testFeatureTemplateEvaluation() {
        // Generate mock log collections
        MockLogCollectionFactory.generate();

        // Get instance of test suite log collection
        TestSuiteLogCollection logCollection = TestSuiteLogCollection.getInstance();

        // Generate the report
        FeatureReportCreator creator = new FeatureReportCreator();

        boolean result = creator.createReport(
                logCollection,
                logCollection.getTestFeatureLogCollections().get(0)
        );

        // Clear mock logs
        MockLogCollectionFactory.clear();

        Assert.assertTrue(result);
    }

    @Test
    public void testScenarioTemplateEvaluation() {
        // Generate mock log collections
        MockLogCollectionFactory.generate();

        // Get instance of test suite log collection
        TestSuiteLogCollection logCollection = TestSuiteLogCollection.getInstance();

        // Generate the report
        ScenarioReportCreator creator = new ScenarioReportCreator();

        boolean result = creator.createReport(
                logCollection,
                logCollection.getTestFeatureLogCollections().get(0).getScenarioLogCollection().get(0)
        );

        // Clear mock logs
        MockLogCollectionFactory.clear();

        Assert.assertTrue(result);
    }

    @Test
    public void testNonExistentTemplate() {
        PebbleEngine engine = new PebbleEngine.Builder().build();

        Assert.assertThrows(
                LoaderException.class,
                () -> engine.getTemplate("de/qytera/qtaf/htmlreport/templates/xyz.html")
        );
    }
}
