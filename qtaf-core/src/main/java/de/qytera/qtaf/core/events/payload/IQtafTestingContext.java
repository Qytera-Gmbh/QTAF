package de.qytera.qtaf.core.events.payload;

import java.util.Date;

public interface IQtafTestingContext {
    String getSuiteName();

    Date getStartDate();

    Date getEndDate();

    String getLogDirectory();

    Object getOriginalEvent();
}
