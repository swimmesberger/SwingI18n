package org.fseek.simon.swingi18n.examples.simple;

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
import org.fseek.simon.swingi18n.I18nController;
import org.fseek.simon.swingi18n.components.I18nFrame;

/**
 * This class shows the simple usage of SwingI18n with the global BundleController
 * This usage expects only one ResourceBundle for smaller projects.
 * If you want to use multiple ResourceBundles because you want to split them for bigger projects then you might want to check out the ExtendedUsage class
 * 
 * @author Simon Wimmesberger
 */
public class SimpleUsage extends I18nFrame {
    public SimpleUsage() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
                //I18nController.getI18nSwingUtility().applyI18n(checkbox);
                if(checkbox.getName().equals("changeable")){
                    checkbox.setName("changedTo");
                }else{
                    checkbox.setName("changeable");
                }
            }
        });
        
        final JButton sameBtn = new JButton();
        sameBtn.setName("open");
        sameBtn.setEnabled(false);
        
        final JMenuBar menuBar = new JMenuBar();
        final JMenu exampleMenu = new JMenu();
        exampleMenu.setName("file");
        final JMenuItem exampleMenuItem = new JMenuItem();
        exampleMenuItem.setName("open");
        exampleMenu.add(exampleMenuItem);
        menuBar.add(exampleMenu);
        
        final JComboBox<Locale> languageSwitch = new JComboBox<>();
        for(Locale l : I18nController.getSupportedLocales()){
            languageSwitch.addItem(l);
        }
        languageSwitch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    I18nController.setLocale((Locale)e.getItem());
                }
            }
        });
        languageSwitch.setSelectedItem(I18nController.getLocale());
        
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //initialize global ResourceBundle used for everything
        I18nController.init("org.fseek.simon.swingi18n.examples.simple.lang.MainBundle");

        //init nimbus LAF
        initLAF();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleUsage().setVisible(true);
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
            java.util.logging.Logger.getLogger(SimpleUsage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

}
