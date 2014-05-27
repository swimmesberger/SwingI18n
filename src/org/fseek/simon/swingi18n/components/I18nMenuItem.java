
package org.fseek.simon.swingi18n.components;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import org.fseek.simon.swingi18n.I18nable;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nMenuItem extends JMenuItem implements I18nable{
    /**
     * Creates a <code>JMenuItem</code> with no set text or icon.
     */
    public I18nMenuItem() {
        super();
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified icon.
     *
     * @param icon the icon of the <code>JMenuItem</code>
     */
    public I18nMenuItem(Icon icon) {
        super(icon);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text.
     *
     * @param text the text of the <code>JMenuItem</code>
     */
    public I18nMenuItem(String text) {
        super(text);
    }

    /**
     * Creates a menu item whose properties are taken from the
     * specified <code>Action</code>.
     *
     * @param a the action of the <code>JMenuItem</code>
     * @since 1.3
     */
    public I18nMenuItem(Action a) {
        super(a);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text and icon.
     *
     * @param text the text of the <code>JMenuItem</code>
     * @param icon the icon of the <code>JMenuItem</code>
     */
    public I18nMenuItem(String text, Icon icon) {
        super(text, icon);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text and
     * keyboard mnemonic.
     *
     * @param text the text of the <code>JMenuItem</code>
     * @param mnemonic the keyboard mnemonic for the <code>JMenuItem</code>
     */
    public I18nMenuItem(String text, int mnemonic) {
        super(text, mnemonic);
    }

    @Override
    public void setLocalizedText(String text) {
        this.setText(text);
    }
}
