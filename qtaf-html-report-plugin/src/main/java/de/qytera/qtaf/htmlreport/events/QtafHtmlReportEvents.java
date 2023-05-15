package de.qytera.qtaf.htmlreport.events;

import rx.subjects.BehaviorSubject;

/**
 * This class manages events emitted by the Qtaf Html Report plugin
 */
public class QtafHtmlReportEvents {
    private QtafHtmlReportEvents() {}
    /**
     * This event is emitted when the HTML report is created. It#s payload is the path to the report.
     */
    public static final BehaviorSubject<String> htmlReportCreated = BehaviorSubject.create();
}
