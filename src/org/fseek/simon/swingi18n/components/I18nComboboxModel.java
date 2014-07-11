
package org.fseek.simon.swingi18n.components;

import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Simon Wimmesberger
 */
public class I18nComboboxModel extends DefaultComboBoxModel<I18nString>{
    public I18nComboboxModel() {
        super();
    }
    
    public I18nComboboxModel(final I18nString items[]) {
        super(items);
    }
    
    public I18nComboboxModel(final String items[]) {
        I18nString[] i18nStrings = new I18nString[items.length];
        for(int i = 0; i<i18nStrings.length; i++){
            addElement(new I18nString(items[i]));
        }
    }
}
