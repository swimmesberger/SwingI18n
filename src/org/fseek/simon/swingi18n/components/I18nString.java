
package org.fseek.simon.swingi18n.components;

import org.fseek.simon.swingi18n.I18nController;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nString{
    private final String key;
    public I18nString(String key){
        this.key = key;
    }

    @Override
    public String toString() {
        return I18nController.getText(this.key);
    }

    public String getKey() {
        return key;
    }
}
