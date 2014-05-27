package org.fseek.simon.swingi18n;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * Helper class to inform all children of a swing container about a language
 * change and applies them.
 *
 * This is where the "magic" happens.
 *
 * @author Simon Wimmesberger
 */
public class I18nUtil {

    private class I18nPropertyChangeListener implements PropertyChangeListener {
        public I18nPropertyChangeListener() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("name")) {
                final Component c = (Component) evt.getSource();
                applyI18n(c);
            }
        }
    }

    private HashMap<String, ArrayList<I18nKey>> i18nComponentMapping;
    private ResourceBundle bundle;
    private final I18nPropertyChangeListener nameChangedListener = new I18nPropertyChangeListener();

    public I18nUtil(ResourceBundle bundle) {
        this.bundle = bundle;
        initI18nComponentMapping();
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
        initI18nComponentMapping();
    }

    public void applyI18nBundle(Container container) {
        HashMap<String, ArrayList<Component>> mapping = getComponentNameMapping(container);
        //we don't need to continue here the container has no children with component names
        if (mapping.isEmpty()) {
            return;
        }
        for (String key : mapping.keySet()) {
            if (i18nComponentMapping.containsKey(key)) {
                for (Component component : mapping.get(key)) {
                    applyI18n(component);
                }
            }
        }
    }

    public void applyI18n(Component component) {
        String compName = component.getName();
        if (i18nComponentMapping.containsKey(compName) == false) {
            return;
        }
        ArrayList<I18nKey> keyList = this.i18nComponentMapping.get(compName);

        for (I18nKey k : keyList) {
            boolean flag = applyI18n(component, k);
            if (k.getType() != null) {
                flag = applyTypeI18n(component, k);
            }
            if(flag){
                initUpdateNotification(component);
            }
        }
    }

    protected void initUpdateNotification(Component c) {
        //remove old listener
        for (PropertyChangeListener lis : c.getPropertyChangeListeners("name")) {
            if (lis == nameChangedListener) {
                return;
            }
            if (lis instanceof I18nPropertyChangeListener) {
                c.removePropertyChangeListener("name", lis);
            }
        }
        //add new listener
        c.addPropertyChangeListener("name", nameChangedListener);
    }

    protected void initI18nComponentMapping() {
        this.i18nComponentMapping = new HashMap<>();
        Enumeration keys = this.bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            I18nKey i18nKey = new I18nKey(key, bundle.getString(key));
            key = i18nKey.getKey();
            if (this.i18nComponentMapping.containsKey(key)) {
                this.i18nComponentMapping.get(key).add(i18nKey);
            } else {
                ArrayList<I18nKey> keyList = new ArrayList<>();
                keyList.add(i18nKey);
                this.i18nComponentMapping.put(key, keyList);
            }
        }
    }

    protected static boolean applyI18n(Component c, I18nKey key) {
        boolean flag = false;
        if (c instanceof I18nable) {
            I18nable lis = (I18nable) c;
            lis.setLocalizedText(key.getValue());
            flag = true;
        } else if (c instanceof JLabel) {
            JLabel lbl = (JLabel) c;
            lbl.setText(key.getValue());
            flag = true;
        } else if (c instanceof JButton) {
            JButton btn = (JButton) c;
            btn.setText(key.getValue());
            flag = true;
        } else if (c instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) c;
            checkBox.setText(key.getValue());
            flag = true;
        } else if (c instanceof JTabbedPane) {
            JTabbedPane tabPane = (JTabbedPane) c;
            for (int i = 0; i < tabPane.getTabCount(); i++) {
                Component comp = tabPane.getComponentAt(i);
                String compName = comp.getName();
                //if the second level part equals the name of the inner component apply this as the title of the tab
                if (key.getSecondLevel() != null && key.getSecondLevel().equals(compName)) {
                    tabPane.setTitleAt(i, key.getValue());
                    flag = true;
                }
            }
        } else if (c instanceof JMenu) {
            JMenu menu = (JMenu) c;
            menu.setText(key.getValue());
            flag = true;
        } else if (c instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) c;
            menuItem.setText(key.getValue());
            flag = true;
        }
        return flag;
    }

    protected static boolean applyTypeI18n(Component c, I18nKey key) {
        boolean flag = false;
        if (c instanceof JComponent) {
            JComponent jcomp = (JComponent) c;
            switch (key.getType()) {
                case "border":
                    Border border = jcomp.getBorder();
                    if (border != null) {
                        if (border instanceof I18nable) {
                            I18nable lis = (I18nable) border;
                            lis.setLocalizedText(key.getValue());
                            flag = true;
                        } else if (border instanceof TitledBorder) {
                            TitledBorder titledBorder = (TitledBorder) border;
                            titledBorder.setTitle(key.getValue());
                            flag = true;
                        }
                    }
                    break;
                case "tooltip":
                    jcomp.setToolTipText(key.getValue());
                    flag = true;
                    break;
            }
        }
        return flag;
    }

    /**
     * Recursivly gets all children of the container mapped by the component
     * name
     *
     * @param container
     * @return Key => Component name Value => List of all components with that
     * name
     */
    protected static HashMap<String, ArrayList<Component>> getComponentNameMapping(Container container) {
        HashMap<String, ArrayList<Component>> mapping = new HashMap<>();
        mapping = addComponentNameMapping(mapping, container);
        return mapping;
    }

    protected static HashMap<String, ArrayList<Component>> addComponentNameMapping(HashMap<String, ArrayList<Component>> mapping, Container container) {
        for (Component c : container.getComponents()) {
            //hotfix for menus becuase getComponents array is empty
            if (c instanceof JMenu) {
                JMenu menu = (JMenu) c;
                mapping = addComponentNameMapping(mapping, menu.getPopupMenu());
            }
            if (c instanceof Container) {
                mapping = addComponentNameMapping(mapping, (Container) c);
            }
            mapping = addComponentName(mapping, c);
        }
        return mapping;
    }

    protected static HashMap<String, ArrayList<Component>> addComponentName(HashMap<String, ArrayList<Component>> mapping, Component c) {
        String name = c.getName();
        if (name != null) {
            if (mapping.containsKey(name)) {
                mapping.get(name).add(c);
            } else {
                ArrayList<Component> nList = new ArrayList<>();
                nList.add(c);
                mapping.put(name, nList);
            }
        }
        return mapping;
    }

    protected static class I18nKey {

        private final String value;
        private String key;
        private String secondLevel;
        private String type;
        private final String fullKey;

        public I18nKey(String fullKey, String value) {
            this.fullKey = fullKey;
            this.value = value;
            //this is used for TabbedPane to treat each tab different
            if (fullKey.contains(".")) {
                String[] split = fullKey.split("\\.");
                //the component name
                this.key = split[0];
                //get the second level name of the key
                this.secondLevel = split[1];
                //get the type e.g. border, tooltip
                this.type = split[split.length - 1];
            } else {
                this.key = fullKey;
            }
        }

        public String getFullKey() {
            return fullKey;
        }

        public String getKey() {
            return key;
        }

        public String getSecondLevel() {
            return secondLevel;
        }

        public String getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }
}
