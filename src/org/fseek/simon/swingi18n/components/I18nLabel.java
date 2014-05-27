package org.fseek.simon.swingi18n.components;

import javax.swing.Icon;
import javax.swing.JLabel;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.LEADING;
import org.fseek.simon.swingi18n.I18nable;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nLabel extends JLabel implements I18nable {

    /**
     * Creates a <code>JLabel</code> instance with the specified text, image,
     * and horizontal alignment. The label is centered vertically in its display
     * area. The text is on the trailing edge of the image.
     *
     * @param text The text to be displayed by the label.
     * @param icon The image to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     * <code>SwingConstants</code>: <code>LEFT</code>, <code>CENTER</code>,
     * <code>RIGHT</code>, <code>LEADING</code> or <code>TRAILING</code>.
     */
    public I18nLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text and
     * horizontal alignment. The label is centered vertically in its display
     * area.
     *
     * @param text The text to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     * <code>SwingConstants</code>: <code>LEFT</code>, <code>CENTER</code>,
     * <code>RIGHT</code>, <code>LEADING</code> or <code>TRAILING</code>.
     */
    public I18nLabel(String text, int horizontalAlignment) {
        super(text, null, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text. The label
     * is aligned against the leading edge of its display area, and centered
     * vertically.
     *
     * @param text The text to be displayed by the label.
     */
    public I18nLabel(String text) {
        super(text, null, LEADING);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image and
     * horizontal alignment. The label is centered vertically in its display
     * area.
     *
     * @param image The image to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     * <code>SwingConstants</code>: <code>LEFT</code>, <code>CENTER</code>,
     * <code>RIGHT</code>, <code>LEADING</code> or <code>TRAILING</code>.
     */
    public I18nLabel(Icon image, int horizontalAlignment) {
        super(null, image, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image. The
     * label is centered vertically and horizontally in its display area.
     *
     * @param image The image to be displayed by the label.
     */
    public I18nLabel(Icon image) {
        super(null, image, CENTER);
    }

    /**
     * Creates a <code>JLabel</code> instance with no image and with an empty
     * string for the title. The label is centered vertically in its display
     * area. The label's contents, once set, will be displayed on the leading
     * edge of the label's display area.
     */
    public I18nLabel() {
        super("", null, LEADING);
    }

    @Override
    public void setLocalizedText(String text) {
        this.setText(text);
    }
}
