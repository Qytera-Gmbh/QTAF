package de.qytera.qtaf.page_objects;

import de.qytera.qtaf.core.guice.annotations.Step;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * This page object represents a text element that contains dates
 */
public class DateTextElement extends AbstractWebElement {
    /**
     * The date formatter that represents the value of this text field
     */
    private DateTimeFormatter dateTimeFormatter;

    /**
     * Set date time formatter
     * @param dateTimeFormatter date time formatter
     * @return  dthis
     */
    public DateTextElement setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
        return this;
    }

    /**
     * Get value of text field as local date
     * @return local date
     */
    @Nullable
    public LocalDate getLocalDate() {
        String value = element().innerText().trim();
        return value != null ? LocalDate.parse(value, dateTimeFormatter) : null;
    }

    /**
     * Get value of text field as local date time
     * @return local date time
     */
    @Nullable
    public LocalDateTime getLocalDateTime() {
        String value = element().text();
        return LocalDateTime.parse(value, dateTimeFormatter);
    }

    @Step(name = "assert date equals today", description = "Assert that the date in the text field equals the current date")
    public void assertDateIsToday() {
        LocalDate date = getLocalDate();
        assertNotNull(
                date,
                "Expected to find a date in %s and found '%s'".formatted(name, date)
        );

        // If the date is null we cannot continue
        if (date == null)
            return;

        assertTrue(
                date.getYear() == LocalDate.now().getYear() &&
                        date.getDayOfYear() == LocalDate.now().getDayOfYear(),
                String.format("Expected date in %s to be today (%s), but found '%s'", name, LocalDate.now(), date)
        );
    }

    @Step(name = "assert difference to other date equals a given value", description = "Expect that the date of this field has a given difference to another given date")
    public void assertDifferenceToOtherDateTimeEquals(LocalDateTime otherDate, long expectedDifference, ChronoUnit chronoUnit) {
        LocalDateTime actualDateTime = getLocalDateTime();
        assert actualDateTime != null;
        long actualDifference = chronoUnit.between(actualDateTime, otherDate);
        assertEquals(actualDifference, expectedDifference, "Expected difference between '%s' and '%s' to be %s %s and was %s %s".formatted(actualDateTime, otherDate, expectedDifference, chronoUnit, actualDifference, chronoUnit));
    }
}
