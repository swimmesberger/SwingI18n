package org.fseek.simon.swingi18n;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nBundleController {

    private HashSet<I18nBundle> bundlePath;
    private I18nUtil i18nSwingUtil;
    private Locale currentLocale;

    public I18nBundleController(String bundlePath) {
        this.bundlePath = new HashSet<>();
        this.bundlePath.add(new I18nBundle(bundlePath));
    }

    public I18nBundleController(String bundlePath, Locale locale) {
        this.bundlePath = new HashSet<>();
        this.bundlePath.add(new I18nBundle(bundlePath));
        setLocale(locale);
    }
    
        public I18nBundleController(String bundlePath, ClassLoader cl) {
        this.bundlePath = new HashSet<>();
        this.bundlePath.add(new I18nBundle(bundlePath, cl));
    }

    public I18nBundleController(String bundlePath, Locale locale, ClassLoader cl) {
        this.bundlePath = new HashSet<>();
        this.bundlePath.add(new I18nBundle(bundlePath, cl));
        setLocale(locale);
    }

    public I18nUtil getI18nSwingUtility() {
        return i18nSwingUtil;
    }

    public Locale getLocale() {
        return this.currentLocale;
    }

    /**
     * EXPERIMENTAL Unstable and unsecure method to get the available languages
     *
     * @return
     */
    public Locale[] getSupportedLocales() {
        return I18nUtil.getLocalesBundle(this.bundlePath);
    }

    public void addPath(String bundlePath) {
        if (this.bundlePath == null) {
            this.bundlePath = new HashSet<>();
        }
        I18nBundle bundle = new I18nBundle(bundlePath);
        if (currentLocale != null) {
            this.addBundle(bundle);
        }
    }

    public void addPath(I18nBundle bundle) {
        if (this.bundlePath == null) {
            this.bundlePath = new HashSet<>();
        }
        if (currentLocale != null) {
            this.addBundle(bundle);
        }
    }

    public ResourceBundle removePath(I18nBundle bundle) {
        if(bundle == null)return null;
        if (this.bundlePath == null) {
            return null;
        }
        boolean remove = this.bundlePath.remove(bundle);
        if (this.i18nSwingUtil != null && remove == true) {
            ResourceBundle resBundle = loadBundle(bundle);
            this.i18nSwingUtil.removeBundle(resBundle);
            return resBundle;
        }
        return null;
    }

    public ResourceBundle removePath(String bundlePath) {
        return removePath(findBundle(bundlePath));
    }
    
    public String getText(String key){
        if(this.i18nSwingUtil == null)return null;
        return this.i18nSwingUtil.getText(key);
    }

    protected I18nBundle findBundle(String bundlePath) {
        for (I18nBundle bundle : this.bundlePath) {
            if (bundle.getPath().equals(bundlePath)) {
                return bundle;
            }
        }
        return null;
    }

    public void clearBundles() {
        if (this.i18nSwingUtil == null) {
            return;
        }
        this.i18nSwingUtil.clearBundles();
    }

    protected void addBundle(I18nBundle bundlePath) {
        addBundle(bundlePath, loadBundle(bundlePath));
    }

    protected void addBundle(I18nBundle bundlePath, ResourceBundle bundle) {
        if (this.i18nSwingUtil == null) {
            this.i18nSwingUtil = new I18nUtil(bundle);
        } else {
            this.i18nSwingUtil.addBundle(bundle);
        }
        this.bundlePath.add(bundlePath);
    }

    protected void removeBundle(ResourceBundle bundle) {
        if (this.i18nSwingUtil == null) {
            return;
        }
        this.i18nSwingUtil.removeBundle(bundle);
    }
    
    protected ResourceBundle loadBundle(I18nBundle i18nBundle) {
        return loadBundle(i18nBundle, currentLocale);
    }

    protected ResourceBundle loadBundle(I18nBundle i18nBundle, Locale locale) {
        ResourceBundle bundle;
        if (i18nBundle.getClassLoader() == null) {
            bundle = ResourceBundle.getBundle(i18nBundle.getPath(), locale);
        } else {
            bundle = ResourceBundle.getBundle(i18nBundle.getPath(), locale, i18nBundle.getClassLoader());
        }
        return bundle;
    }

    public void setLocale(Locale locale) {
        if (this.getLocale() == null || this.getLocale().equals(locale) == false) {
            this.currentLocale = locale;
            clearBundles();
            for (I18nBundle path : bundlePath) {
                addBundle(path, loadBundle(path, locale));
            }
        }
    }
}
