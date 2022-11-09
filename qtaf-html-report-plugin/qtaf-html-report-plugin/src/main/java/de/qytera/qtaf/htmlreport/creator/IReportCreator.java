package de.qytera.qtaf.htmlreport.creator;

import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;

/**
 * Interface that classes that create reports should implement
 */
public interface IReportCreator {
    /**
     * Create report
     * @param logCollection Log collection
     * @return true on success, false otherwise
     */
    boolean createReport(TestSuiteLogCollection logCollection);
}
