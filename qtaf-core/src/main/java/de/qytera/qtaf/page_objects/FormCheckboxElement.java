package de.qytera.qtaf.page_objects;

import de.qytera.qtaf.core.guice.annotations.Step;

/**
 * This page object represents checkboxes
 */
public class FormCheckboxElement extends FormElement {
    /**
     * Check if the checkbox is checked
     *
     * @return true if the checkbox is checked, false otherwise
     */
    @Step(name = "check if box is checked", description = "Check if the box is checked")
    public boolean isChecked() {
        return element().isSelected();
    }

    /**
     * Check the checkbox
     */
    @Step(name = "check the box", description = "Check the box")
    public void check() {
        if (!isChecked()) {
            element().click();
        }
    }

    /**
     * Uncheck the checkbox
     */
    @Step(name = "uncheck the box", description = "Uncheck the box")
    public void uncheck() {
        if (isChecked()) {
            element().click();
        }
    }

}
