package org.fseek.simon.swingi18n.components;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.Locale;
import javax.swing.JFrame;
import org.fseek.simon.swingi18n.I18nController;
import org.fseek.simon.swingi18n.I18nable;

/**
 * The default behaviour inits the Frame when it gets visible
 * @author Simon Wimmesberger
 */
public class I18nFrame extends JFrame implements I18nController.I18nChangeListener, I18nable {
    private boolean i18nListening = false;
    private Locale currentLocale;
    
    /**
     * Constructs a new frame that is initially invisible.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public I18nFrame() throws HeadlessException {
        super();
    }

    /**
     * Creates a <code>Frame</code> in the specified
     * <code>GraphicsConfiguration</code> of a screen device and a blank title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param gc the <code>GraphicsConfiguration</code> that is used to
     * construct the new <code>Frame</code>; if <code>gc</code> is
     * <code>null</code>, the system default <code>GraphicsConfiguration</code>
     * is assumed
     * @exception IllegalArgumentException if <code>gc</code> is not from a
     * screen device. This exception is always thrown when
     * GraphicsEnvironment.isHeadless() returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     * @since 1.3
     */
    public I18nFrame(GraphicsConfiguration gc) {
        super(gc);
    }

    /**
     * Creates a new, initially invisible <code>Frame</code> with the specified
     * title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param title the title for the frame
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public I18nFrame(String title) throws HeadlessException {
        super(title);
    }

    /**
     * Creates a <code>JFrame</code> with the specified title and the specified
     * <code>GraphicsConfiguration</code> of a screen device.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param title the title to be displayed in the frame's border. A
     * <code>null</code> value is treated as an empty string, "".
     * @param gc the <code>GraphicsConfiguration</code> that is used to
     * construct the new <code>JFrame</code> with; if <code>gc</code> is
     * <code>null</code>, the system default <code>GraphicsConfiguration</code>
     * is assumed
     * @exception IllegalArgumentException if <code>gc</code> is not from a
     * screen device. This exception is always thrown when
     * GraphicsEnvironment.isHeadless() returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     * @since 1.3
     */
    public I18nFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }

    @Override
    public void setVisible(boolean b) {
        if(isVisible() == false && b == true){
            initI18n();
            this.pack();
        }
        super.setVisible(b);
    }

    @Override
    public void dispose() {
        super.dispose();
        I18nController.removeLocaleChangeListener(this);
        i18nListening = false;
    }

    /**
     * This method needs to be called when all components are added to this Frame
     */
    protected void initI18n() {
        if(i18nListening)return;
        I18nController.addLocaleChangeListener(this);
        this.onLocaleChange(I18nController.getLocale());
        i18nListening = true;
    }

    @Override
    public void onLocaleChange(Locale locale) {
        if(currentLocale == null || currentLocale.equals(locale) == false){
            i18nApplyBundle(locale);
            //resize if there is not enough space after applying i18n
            if(this.getSize().height < this.getPreferredSize().height || this.getSize().width < this.getPreferredSize().width){
                this.pack();
            }
        }
    }

    protected void i18nApplyBundle(Locale locale){
        //Use global BundleController
        I18nController.getI18nSwingUtility().applyI18nBundle(this);
    }

    @Override
    public void setLocalizedText(String text) {
        this.setTitle(text);
    }
}
