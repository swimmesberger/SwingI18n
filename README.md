# SwingI18n
SwingI18n is a easy to use swing internationalisation library.

## Main Features
  - Switching language in runtime
  - Easy to use
  - Open Source

## Usage:

### Simple Init:
The most easy approch to do internationalisation with SwingI18n.
Initilize the localization handler
```java
    I18nController.init("org.example.lang.MyBundle");
```
Extend from the localization class
```java
    public class MyFrame extends I18nFrame
```
alternativly you can implement it yourself by simply implementing the interface
```java
    I18nFrame extends JFrame implements I18nController.I18nChangeListener
```

The only thing you have to do now is to initilize the frame with all the components and giving them names.<br/><br/>

### Extended Init:

A bit more complex approach (but far from being complex).
With this approach you can use multiple ResourceBundles for multiple Frames/Dialogs but with one simple language switch.

Initilize the localization handler
```java
    I18nController.init();
```
Setup:
```java
    public class MyFrame extends I18nFrame{
        private final I18nBundleController bundleController;
        public MyFrame() {
            bundleController = new I18nBundleController("org.example.lang.MyBundle1", I18nController.getLocale());
            initComponents();
        }
        @Override
        protected void i18nApplyBundle(Locale locale) {
            //use own bundleController here
            bundleController.setLocale(locale);
            bundleController.getI18nSwingUtility().applyI18nBundle(this);
        }
    }
```
With this setup you can use different Bundles for different Dialog/Frames and localization is done automatically by SwingI18n.
After setting the component names you are also done at this point.

## Naming:
--
The names of the components have to match the keys in the properties files:
```properties
    confirm=Confirm
    cancel=Cancel
```

```java
    final JButton okBtn = new JButton();
    okBtn.setName("confirm");

    final JButton cancelBtn = new JButton();
    cancelBtn.setName("cancel");
```

Its also possible to dynamically change the names of components while runtime, simply set the name and the system will recognize the change if the component is already known by the system (so if the component was localized before).


If the component is unkown by the system you can also manually add it to the system by doing:
```java
    //simple
    I18nController.getI18nSwingUtility().applyI18n(component);
    //extended
    bundleControllerInstance.getI18nSwingUtility().applyI18n(component);
```
You can also localize the tooltips or TitledBorders of your components simply use this form in the properties files:
```properties
    confirm=Confirm
    confirm.tooltip=This will confirm your action.
    cancel=Cancel
    cancel.tooltip=Cancel with caution
    panel1.border=This is my fancy localized TitledBorder
```
If you are using JTabbedPane there is also a way to localize the title of tabs, simply give the component which represents the tab a name and define the title in the properties files like that:
```properties
    myTabbedPane.tab1Panel=General
    myTabbedPane.tab2Panel=Extended
```
**Caution: **If you name your inner components "tooltip" or "border" you will have a big problem because these are reserved keywords, so simply don't do that

## License
MIT
**See LICENSE file**


    
