package de.qytera.qtaf.core.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper methods for creating date objects
 */
public class DateHelper {
    /**
     * Create date from date string
     *
     * @param s date string
     * @return Date object
     * @throws ParseException Occurs if date string is wrong
     */
    public static Date fromTimeString(String s) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .parse(s);
    }
}
