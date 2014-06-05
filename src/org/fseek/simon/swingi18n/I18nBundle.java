
package org.fseek.simon.swingi18n;

import java.util.Objects;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nBundle {
    private final String path;
    private final ClassLoader classLoader;

    public I18nBundle(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader;
    }

    public I18nBundle(String path) {
        this.path = path;
        this.classLoader = null;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int hashCode() {
        if(classLoader == null){
            return path.hashCode();
        }else{
            return path.hashCode() + classLoader.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final I18nBundle other = (I18nBundle) obj;
        return Objects.equals(this.path, other.path);
    }
}
