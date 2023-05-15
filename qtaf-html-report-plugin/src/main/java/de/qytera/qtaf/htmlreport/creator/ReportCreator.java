package de.qytera.qtaf.htmlreport.creator;

import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.htmlreport.engine.TemplateEngine;
import de.qytera.qtaf.htmlreport.events.QtafHtmlReportEvents;
import io.pebbletemplates.pebble.error.LoaderException;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for creating HTML reports
 */
public class ReportCreator implements IReportCreator {
    /**
     * Root template
     */
    protected String rootTemplate = "de/qytera/qtaf/htmlreport/templates/home.html";
    /**
     * Filename of the rendered template
     */
    protected String filename = "Report.html";
    /**
     * Create report and save it to the disk
     *
     * @param logCollection Log collection
     * @return true on success
     */
    public boolean createReport(TestSuiteLogCollection logCollection) {
        Writer writer = getRenderedTemplate(logCollection);
        if (writer == null) return false;

        // Persist rendered template
        String reportPath = persistRenderedTemplate(logCollection, writer);
        if (reportPath == null) return false;

        // Dispatch events
        QtafHtmlReportEvents.htmlReportCreated.onNext(reportPath);

        return true;
    }

    /**
     * Get rendered template
     *
     * @param logCollection Log collection
     * @return Writer object that contains rendered template
     */
    public Writer getRenderedTemplate(TestSuiteLogCollection logCollection) {
        // Load template
        PebbleTemplate compiledTemplate = getPebbleTemplate();

        // Check for errors during loading
        if (compiledTemplate == null) return null;

        // Build context
        Map<String, Object> context = getTemplateContext(logCollection);

        // Create writer object
        Writer writer = new StringWriter();

        // Render template and check for errors
        if (renderTemplate(compiledTemplate, context, writer)) return null;
        return writer;
    }

    /**
     * Persist rendered template
     *
     * @param logCollection Log collection
     * @param writer        Writer object
     * @return Path to rendered template
     */
    protected String persistRenderedTemplate(TestSuiteLogCollection logCollection, Writer writer) {
        // Write report to disk
        String reportPath = getReportPath(logCollection);

        try {
            Path filepath = Paths.get(reportPath);
            Files.createDirectories(filepath.getParent());
            Files.write(filepath, writer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            QtafHtmlReportEvents.htmlReportCreated.onError(e);
            return null;
        }
        return reportPath;
    }

    /**
     * Render the template
     *
     * @param compiledTemplate Pebble template object
     * @param context          Template context
     * @param writer           Writer
     * @return true on success, false otherwise
     */
    public boolean renderTemplate(PebbleTemplate compiledTemplate, Map<String, Object> context, Writer writer) {
        // Render template
        try {
            compiledTemplate.evaluate(writer, context);
        } catch (IOException e) {
            e.printStackTrace();
            QtafHtmlReportEvents.htmlReportCreated.onError(e);
            return true;
        }
        return false;
    }

    /**
     * Get template context
     *
     * @param logCollection Logs
     * @return Template context
     */
    protected Map<String, Object> getTemplateContext(TestSuiteLogCollection logCollection) {
        // Build template context
        Map<String, Object> context = new HashMap<>();
        context.put("testSuiteLog", logCollection);
        context.put("testFeatureLogCollections", logCollection.getTestFeatureLogCollections());
        return context;
    }

    /**
     * Load the Pebble Template
     *
     * @return Pebble template object
     */
    protected PebbleTemplate getPebbleTemplate() {
        PebbleTemplate compiledTemplate;

        // Load template
        try {
            compiledTemplate = TemplateEngine.getTemplate(getRootTemplate());
        } catch (LoaderException e) {
            e.printStackTrace();
            QtafHtmlReportEvents.htmlReportCreated.onError(e);
            return null;
        }
        return compiledTemplate;
    }

    /**
     * Get root template path
     *
     * @return root template path
     */
    protected String getRootTemplate() {
        return rootTemplate;
    }

    /**
     * Build report path
     *
     * @param logCollection the log collection
     * @return report path
     */
    public String getReportPath(TestSuiteLogCollection logCollection) {
        return DirectoryHelper.preparePath(
                logCollection.getLogDirectory() + "/" + this.filename
        );
    }
}
