package org.fseek.simon.swingi18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nBundleController {
    private final String bundlePath;
    private I18nUtil i18nSwingUtil;
    private ResourceBundle bundle;

    public I18nBundleController(String bundlePath) {
        this.bundlePath = bundlePath;
    }

    public I18nBundleController(String bundlePath, Locale locale) {
        this.bundlePath = bundlePath;
        setLocale(locale);
    }

    public I18nUtil getI18nSwingUtility() {
        return i18nSwingUtil;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    protected void setBundle(ResourceBundle bundle) {
        if (this.bundle == bundle) {
            return;
        }
        this.bundle = bundle;
        if (this.i18nSwingUtil == null) {
            this.i18nSwingUtil = new I18nUtil(bundle);
        } else {
            this.i18nSwingUtil.setBundle(bundle);
        }
    }

    public void setLocale(Locale locale) {
        if (this.bundle == null || this.bundle.getLocale().equals(locale) == false) {
            setBundle(ResourceBundle.getBundle(bundlePath, locale));
        }
    }
}
