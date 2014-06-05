package org.fseek.simon.swingi18n;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper class to work with resource paths
 *
 * @author Simon Wimmesberger
 */
public class ResourceUtil {

    /**
     * Assume the caller class is the class path to be searched
     *
     * @param resourcePath
     * @return
     */
    public static String[] listFiles(String resourcePath) {
        try {
            String callerName = getCallerClassName();
            Class<?> caller = Class.forName(callerName);
            return listFiles(resourcePath, caller);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * List all files in a given resource path
     *
     * @param resourcePath
     * @param c The class which path should be searched
     * @return
     */
    public static String[] listFiles(String resourcePath, Class<?> c) {
        try {

            URL url = c.getResource(c.getSimpleName() + ".class");

            String scheme = url.getProtocol();
            if (null != scheme) //class is loaded via jar file
            //class is loaded via jar file
            {
                switch (scheme) {
                    case "jar": {
                        JarURLConnection con = (JarURLConnection) url.openConnection();
                        JarFile archive = con.getJarFile();
                        /* Search for the entries you care about. */
                        Enumeration<JarEntry> entries = archive.entries();
                        ArrayList<String> files = new ArrayList<>();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            if (entry.getName().startsWith(resourcePath)) {
                                files.add(entry.getName());
                            }
                        }
                        return files.toArray(new String[files.size()]);
                        //class is loaded via filesystem
                    }
                    case "file": {
                        File f = new File(url.toURI());
                        String path = f.getAbsolutePath();
                        String backPath = c.getName().replace('.', File.separatorChar);
                        String finalPath = path.substring(0, path.indexOf(backPath));

                        String filePath = finalPath + resourcePath.replace('.', File.separatorChar);
                        File resourceFile = new File(filePath);
                        ArrayList<String> files = new ArrayList<>();
                        //if the given file is no directory assume that the file part is meant to be as filter for all files starting with that name
                        if (resourceFile.isDirectory() == false) {
                            String resourceFileName = resourceFile.getName();
                            resourceFile = resourceFile.getParentFile();
                            for (File resF : resourceFile.listFiles()) {
                                String fileName = resF.getName();
                                if (fileName.startsWith(resourceFileName)) {
                                    files.add(resF.getName());
                                }
                            }
                        } else {
                            for (File resF : resourceFile.listFiles()) {
                                files.add(resF.getName());
                            }
                        }
                        return files.toArray(new String[files.size()]);
                    }
                    default:
                        throw new IllegalArgumentException("Unsupported scheme: " + scheme);
                }
            }
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static final Class<?>[] PERMITTED_CLASSES = new Class<?>[]{ResourceUtil.class, I18nBundleController.class, I18nController.class, I18nUtil.class, Thread.class};
    public static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            String classNm = ste.getClassName();
            boolean allowed = true;
            for (Class<?> c : PERMITTED_CLASSES) {
                String name = c.getName();
                if (name.equals(classNm)) {
                    allowed = false;
                    break;
                }
            }
            if(allowed == false || classNm.contains("$"))continue;
            return classNm;
        }
        return null;
    }
}
