package org.fseek.simon.swingi18n.examples.extended;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import org.fseek.simon.swingi18n.I18nBundleController;
import org.fseek.simon.swingi18n.I18nController;
import org.fseek.simon.swingi18n.components.I18nDialog;
import org.fseek.simon.swingi18n.components.I18nFrame;

/**
 * This class shows a bit more complex approach (but far from being complex).
 * With this approach you can use multiple ResourceBundles for multiple Frames/Dialogs but with one simple language switch
 * to switch the language on runtime.
 * It's also possible to use one ResourcePanel for panel1 and one for panel2 it works nearly the same as shown in this class.
 * @author Simon Wimmesberger
 */
public class ExtendedUsage extends I18nFrame {
    private final I18nBundleController bundleController;

    public ExtendedUsage() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bundleController = new I18nBundleController("org.fseek.simon.swingi18n.examples.extended.lang.ExtendedBundle1", I18nController.getLocale());
        //init all the components in constructor, because the default behaviour translates all components AFTER the frame is VISIBLE
        initComponents();
    }

    private void initComponents() {
        final JLabel usernameLabel = new JLabel();
        usernameLabel.setName("username");

        final JButton okBtn = new JButton();
        okBtn.setName("confirm");
        okBtn.setEnabled(false);

        final JButton cancelBtn = new JButton();
        cancelBtn.setName("cancel");
        cancelBtn.setEnabled(false);

        final JCheckBox checkbox = new JCheckBox();
        checkbox.setName("changeable");

        final JButton switchBtn = new JButton();
        switchBtn.setName("switch");
        switchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dynamically setting the name is working too
                //(only if the component already had a name before)
                //if the component had no name before you need to use
                //bundleController.getI18nSwingUtility().applyI18n(checkbox);
                if (checkbox.getName().equals("changeable")) {
                    checkbox.setName("changedTo");
                } else {
                    checkbox.setName("changeable");
                }
            }
        });

        final JButton sameBtn = new JButton();
        sameBtn.setName("open");
        sameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExtendedDialog dia = new ExtendedDialog();
                dia.setVisible(true);
            }
        });

        final JMenuBar menuBar = new JMenuBar();
        final JMenu exampleMenu = new JMenu();
        exampleMenu.setName("file");
        final JMenuItem exampleMenuItem = new JMenuItem();
        exampleMenuItem.setName("open");
        exampleMenu.add(exampleMenuItem);
        menuBar.add(exampleMenu);

        final JComboBox<Locale> languageSwitch = new JComboBox<>();
        Locale[] supportedLocales = bundleController.getSupportedLocales();
        for (Locale l : supportedLocales) {
            languageSwitch.addItem(l);
        }
        languageSwitch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    I18nController.setLocale((Locale) e.getItem());
                }
            }
        });
        //here we use the bundleController to determine the bundle locale
        Locale setLang = bundleController.getLocale();
        //we just use the language and ignore country or variant
        languageSwitch.setSelectedItem(new Locale(setLang.getLanguage()));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(usernameLabel);
        contentPane.add(okBtn);
        contentPane.add(cancelBtn);
        contentPane.add(checkbox);
        contentPane.add(switchBtn);
        contentPane.add(sameBtn);
        contentPane.add(languageSwitch);

        setJMenuBar(menuBar);

        this.pack();
    }

    @Override
    protected void i18nApplyBundle(Locale locale) {
        //use own bundleController here
        bundleController.setLocale(locale);
        bundleController.getI18nSwingUtility().applyI18nBundle(this);
    }

    public static class ExtendedDialog extends I18nDialog {

        private final I18nBundleController bundleController;

        public ExtendedDialog() {
            super();
            bundleController = new I18nBundleController("org.fseek.simon.swingi18n.examples.extended.lang.ExtendedBundle2", I18nController.getLocale());
            initComponents();
        }

        private void initComponents() {
            final JLabel passwordLabel = new JLabel();
            passwordLabel.setName("password");

            final JButton okBtn = new JButton();
            okBtn.setName("confirm");
            okBtn.setEnabled(false);

            final JButton cancelBtn = new JButton();
            cancelBtn.setName("cancel");
            cancelBtn.setEnabled(false);

            final JMenuBar menuBar = new JMenuBar();
            final JMenu exampleMenu = new JMenu();
            exampleMenu.setName("settings");
            final JMenuItem exampleMenuItem = new JMenuItem();
            exampleMenuItem.setName("language");
            exampleMenu.add(exampleMenuItem);
            menuBar.add(exampleMenu);

            Container contentPane = getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
            contentPane.add(passwordLabel);
            contentPane.add(okBtn);
            contentPane.add(cancelBtn);

            setJMenuBar(menuBar);

            this.pack();
        }

        @Override
        protected void i18nApplyBundle(Locale locale) {
            //use own bundleController here
            bundleController.setLocale(locale);
            bundleController.getI18nSwingUtility().applyI18nBundle(this);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //initialize NON GLOBAL ResourceBundle, ResourceBundle has to be done manually
        I18nController.init();

        //init nimbus LAF
        initLAF();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ExtendedUsage().setVisible(true);
            }
        });
    }

    private static void initLAF() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExtendedUsage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

}
