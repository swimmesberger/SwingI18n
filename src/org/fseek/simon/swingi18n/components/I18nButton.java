package org.fseek.simon.swingi18n.components;

import java.beans.ConstructorProperties;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import org.fseek.simon.swingi18n.I18nable;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nButton extends JButton implements I18nable {
    /**
     * Creates a button with no set text or icon.
     */
    public I18nButton() {
        super(null, null);
    }

    /**
     * Creates a button with an icon.
     *
     * @param icon the Icon image to display on the button
     */
    public I18nButton(Icon icon) {
        super(null, icon);
    }

    /**
     * Creates a button with text.
     *
     * @param text the text of the button
     */
    @ConstructorProperties({"text"})
    public I18nButton(String text) {
        super(text, null);
    }

    /**
     * Creates a button where properties are taken from the <code>Action</code>
     * supplied.
     *
     * @param a the <code>Action</code> used to specify the new button
     *
     * @since 1.3
     */
    public I18nButton(Action a) {
        super(a);
    }

    /**
     * Creates a button with initial text and an icon.
     *
     * @param text the text of the button
     * @param icon the Icon image to display on the button
     */
    public I18nButton(String text, Icon icon) {
        super(text, icon);
    }

    @Override
    public void setLocalizedText(String text) {
        this.setText(text);
    }
}
