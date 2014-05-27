package org.fseek.simon.swingi18n.components;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import org.fseek.simon.swingi18n.I18nable;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nCheckbox extends JCheckBox implements I18nable {
    /**
     * Creates an initially unselected check box button with no text, no icon.
     */
    public I18nCheckbox() {
        super();
    }

    /**
     * Creates an initially unselected check box with an icon.
     *
     * @param icon the Icon image to display
     */
    public I18nCheckbox(Icon icon) {
        super(icon);
    }

    /**
     * Creates a check box with an icon and specifies whether or not it is
     * initially selected.
     *
     * @param icon the Icon image to display
     * @param selected a boolean value indicating the initial selection state.
     * If <code>true</code> the check box is selected
     */
    public I18nCheckbox(Icon icon, boolean selected) {
        super(icon, selected);
    }

    /**
     * Creates an initially unselected check box with text.
     *
     * @param text the text of the check box.
     */
    public I18nCheckbox(String text) {
        super(text);
    }

    /**
     * Creates a check box where properties are taken from the Action supplied.
     *
     * @since 1.3
     */
    public I18nCheckbox(Action a) {
        super(a);
    }

    /**
     * Creates a check box with text and specifies whether or not it is
     * initially selected.
     *
     * @param text the text of the check box.
     * @param selected a boolean value indicating the initial selection state.
     * If <code>true</code> the check box is selected
     */
    public I18nCheckbox(String text, boolean selected) {
        super(text, selected);
    }

    /**
     * Creates an initially unselected check box with the specified text and
     * icon.
     *
     * @param text the text of the check box.
     * @param icon the Icon image to display
     */
    public I18nCheckbox(String text, Icon icon) {
        super(text, icon);
    }

    /**
     * Creates a check box with text and icon, and specifies whether or not it
     * is initially selected.
     *
     * @param text the text of the check box.
     * @param icon the Icon image to display
     * @param selected a boolean value indicating the initial selection state.
     * If <code>true</code> the check box is selected
     */
    public I18nCheckbox(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

    @Override
    public void setLocalizedText(String text) {
        this.setText(text);
    }
}
