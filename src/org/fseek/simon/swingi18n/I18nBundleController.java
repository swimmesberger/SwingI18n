package org.fseek.simon.swingi18n;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nBundleController {

    private ArrayList<String> bundlePath;
    private I18nUtil i18nSwingUtil;
    private Locale currentLocale;

    public I18nBundleController(String bundlePath) {
        this.bundlePath = new ArrayList<>();
        this.bundlePath.add(bundlePath);
    }

    public I18nBundleController(String bundlePath, Locale locale) {
        this.bundlePath = new ArrayList<>();
        this.bundlePath.add(bundlePath);
        setLocale(locale);
    }

    public I18nUtil getI18nSwingUtility() {
        return i18nSwingUtil;
    }

    public ResourceBundle[] getBundles() {
        return i18nSwingUtil.getBundles();
    }

    public Locale getLocale() {
        return this.currentLocale;
    }

    /**
     * EXPERIMENTAL
     * Unstable and unsecure method to get the available languages
     * @return 
     */
    public Locale[] getSupportedLocales() {
        return I18nUtil.getLocales(this.bundlePath);
    }

    public void addPath(String bundlePath) {
        if (this.bundlePath == null) {
            this.bundlePath = new ArrayList<>();
        }
        this.bundlePath.add(bundlePath);
        if (currentLocale != null) {
            this.addBundle(ResourceBundle.getBundle(bundlePath, currentLocale));
        }
    }

    public void clearBundles() {
        if (this.i18nSwingUtil == null) {
            return;
        }
        this.i18nSwingUtil.clearBundles();
    }

    protected void addBundle(ResourceBundle bundle) {
        if (this.i18nSwingUtil == null) {
            this.i18nSwingUtil = new I18nUtil(bundle);
        } else {
            this.i18nSwingUtil.addBundle(bundle);
        }
    }

    public void setLocale(Locale locale) {
        if (this.getLocale() == null || this.getLocale().equals(locale) == false) {
            this.currentLocale = locale;
            clearBundles();
            for (String path : bundlePath) {
                ResourceBundle bundle = ResourceBundle.getBundle(path, locale);
                addBundle(bundle);
            }
        }
    }
}
