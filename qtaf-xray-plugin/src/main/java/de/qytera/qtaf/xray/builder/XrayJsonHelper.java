package de.qytera.qtaf.xray.builder;

import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.entity.XrayIterationParameterEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Utility class for transforming data into objects compatible with Xray's REST APIs.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XrayJsonHelper {

    /**
     * Transforms a {@link Date} into a string compatible with Xray's REST API.
     *
     * @param date the date to transform
     * @return the corresponding string
     */
    public static String isoDateString(Date date) {
        // Date in ISO 8601 format: "2022-12-01T02:30:44Z"
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_INSTANT);
    }

    /**
     * Truncates an {@link XrayIterationParameterEntity}'s parameter name to the maximum allowed length. Returns the
     * unmodified string in case its length is less than the maximum allowed length.
     *
     * @param parameterName the name to truncate
     * @return the truncated or unmodified parameter name
     */
    public static String truncateParameterName(String parameterName) {
        Integer maxLength = XrayConfigHelper.getTestsIterationsParametersNameMaxLength();
        if (maxLength == null || parameterName.length() <= maxLength) {
            return parameterName;
        }
        return parameterName.substring(0, maxLength);
    }


    /**
     * Truncates an {@link XrayIterationParameterEntity}'s parameter value to the maximum allowed length. Returns the
     * unmodified string in case its length is less than the maximum allowed length.
     *
     * @param parameterValue the value to truncate
     * @return the truncated or unmodified parameter value
     */
    public static String truncateParameterValue(String parameterValue) {
        Integer maxLength = XrayConfigHelper.getTestsIterationsParametersValueMaxLength();
        if (maxLength == null || parameterValue.length() <= maxLength) {
            return parameterValue;
        }
        return parameterValue.substring(0, maxLength);
    }
}
