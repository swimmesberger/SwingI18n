
package org.fseek.simon.swingi18n.components;

import javax.swing.Action;
import javax.swing.JMenu;
import org.fseek.simon.swingi18n.I18nable;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nMenu extends JMenu implements I18nable{
    /**
     * Constructs a new <code>JMenu</code> with no text.
     */
    public I18nMenu() {
        super();
    }

    /**
     * Constructs a new <code>JMenu</code> with the supplied string
     * as its text.
     *
     * @param s  the text for the menu label
     */
    public I18nMenu(String s) {
        super(s);
    }

    /**
     * Constructs a menu whose properties are taken from the
     * <code>Action</code> supplied.
     * @param a an <code>Action</code>
     *
     * @since 1.3
     */
    public I18nMenu(Action a) {
        super(a);
    }

    /**
     * Constructs a new <code>JMenu</code> with the supplied string as
     * its text and specified as a tear-off menu or not.
     *
     * @param s the text for the menu label
     * @param b can the menu be torn off (not yet implemented)
     */
    public I18nMenu(String s, boolean b) {
        super(s, b);
    }
    
    @Override
    public void setLocalizedText(String text) {
        this.setText(text);
    }
}
