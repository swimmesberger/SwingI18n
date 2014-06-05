package org.fseek.simon.swingi18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nBundleController {

    private HashMap<String, ResourceBundle> bundlePath;
    private I18nUtil i18nSwingUtil;
    private Locale currentLocale;

    public I18nBundleController(String bundlePath) {
        this.bundlePath = new HashMap<>();
        this.bundlePath.put(bundlePath, null);
    }

    public I18nBundleController(String bundlePath, Locale locale) {
        this.bundlePath = new HashMap<>();
        this.bundlePath.put(bundlePath, null);
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
        return I18nUtil.getLocales(this.bundlePath.keySet());
    }

    public void addPath(String bundlePath) {
        if (this.bundlePath == null) {
            this.bundlePath = new HashMap<>();
        }
        if (currentLocale != null) {
            this.addBundle(bundlePath, ResourceBundle.getBundle(bundlePath, currentLocale));
        }
    }
    
    public ResourceBundle removePath(String bundlePath){
        if(this.bundlePath == null)return null;
        ResourceBundle remove = this.bundlePath.remove(bundlePath);
        if(this.i18nSwingUtil != null && remove != null){
            this.i18nSwingUtil.removeBundle(remove);
        }
        return remove;
    }

    public void clearBundles() {
        if (this.i18nSwingUtil == null) {
            return;
        }
        this.i18nSwingUtil.clearBundles();
    }

    protected void addBundle(String bundlePath, ResourceBundle bundle) {
        if (this.i18nSwingUtil == null) {
            this.i18nSwingUtil = new I18nUtil(bundle);
        } else {
            this.i18nSwingUtil.addBundle(bundle);
        }
        this.bundlePath.put(bundlePath, bundle);
    }
    
    protected void removeBundle(ResourceBundle bundle){
        if(this.i18nSwingUtil == null)return;
        this.i18nSwingUtil.removeBundle(bundle);
    }

    public void setLocale(Locale locale) {
        if (this.getLocale() == null || this.getLocale().equals(locale) == false) {
            this.currentLocale = locale;
            clearBundles();
            for (String path : bundlePath.keySet()) {
                ResourceBundle bundle = ResourceBundle.getBundle(path, locale);
                addBundle(path, bundle);
            }
        }
    }
}
