package org.fseek.simon.swingi18n;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nController {

    private static Locale currentLocale;
    private static ArrayList<I18nChangeListener> localeChangeListener;
    private static I18nBundleController globalController;

    public static void init() {
        try {
            setLocale(Locale.getDefault());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("No localizations found !");
        }
    }

    public static void init(String bundlePath) {
        try {
            globalController = new I18nBundleController(bundlePath);
            setLocale(Locale.getDefault());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("No localizations found !");
        }
    }

    /**
     * Returns the currently set Locale
     * If no locale was set manually the default Locale is returned
     */
    public static Locale getLocale() {
        if(globalController == null){
            return currentLocale;
        }else{
            return globalController.getLocale();
        }
    }

    public static ResourceBundle[] getResources() {
        if (globalController == null) {
            throw new UnsupportedOperationException("You have to call init(bundlePath) first !");
        }
        return globalController.getBundles();
    }

    public static I18nUtil getI18nSwingUtility() {
        if (globalController == null) {
            throw new UnsupportedOperationException("You have to call init(bundlePath) first !");
        }
        return globalController.getI18nSwingUtility();
    }
    
    public static Locale[] getSupportedLocales(){
        if (globalController == null) {
            throw new UnsupportedOperationException("You have to call init(bundlePath) first !");
        }
        return globalController.getSupportedLocales();
    }

    public static void setLocale(Locale locale) {
        if (locale.equals(currentLocale)) {
            return;
        }
        if (globalController != null) {
            globalController.setLocale(locale);
        }
        currentLocale = locale;
        //long curTime = System.currentTimeMillis();
        onLocaleChange(currentLocale);
        //long curTimeLater = System.currentTimeMillis();
        //float seconds = ((curTimeLater - curTime)/1000f)%60f;
        //System.out.println("Changing language took: " +  + " seconds.");
    }

    protected static void onLocaleChange(Locale locale) {
        if (localeChangeListener == null) {
            return;
        }
        for (I18nChangeListener lis : localeChangeListener) {
            lis.onLocaleChange(locale);
        }
    }

    public static void addLocaleChangeListener(I18nChangeListener listener) {
        if (localeChangeListener == null) {
            localeChangeListener = new ArrayList<>();
        }
        localeChangeListener.add(listener);
    }

    public static boolean removeLocaleChangeListener(I18nChangeListener listener) {
        if (localeChangeListener == null) {
            return false;
        }
        return localeChangeListener.remove(listener);
    }

    public static interface I18nChangeListener {

        public void onLocaleChange(Locale locale);
    }
}
