package cosc202.andie;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * <p>
 * Allows user to change languages
 * </p>
 *
 * <p>
 * The language menu allows the user to switch between English, Spanish, and
 * French. This class manages the language menu and refreshes the GUI to the
 * selected language.
 * </p>
 *
 * @author Bradyn Salmon
 */
public class LanguageActions {

    //data-fields initialized
    private ResourceBundle bundle;
    protected ArrayList<Action> actions;
    
    /**
     * <p>
     * Create a set of Language menu actions
     * </p>
     * 
     * @param bundle The language bundle for switching languages
     */
    public LanguageActions(ResourceBundle bundle) {
        this.bundle = bundle;
        actions = new ArrayList<>();
        actions.add(new LanguageChangeAction(bundle.getString("ENGLISH"), null, "English", Locale.ENGLISH));
        actions.add(new LanguageChangeAction(bundle.getString("SPANISH"), null, "Spanish", new Locale("es", "ES")));
        actions.add(new LanguageChangeAction(bundle.getString("FRENCH"), null, "French", Locale.FRANCE));
    }

    /**
     * <p>
     * Create a menu containing the list of Language actions
     * </p>
     * 
     * @return The language menu UI element.
     */
    public JMenu createMenu() {
        JMenu languageMenu = new JMenu(bundle.getString("LANGUAGE"));
        for (Action action : actions) {
            languageMenu.add(new JMenuItem(action));
        }
        return languageMenu;
    }
    
    /**
     * <p>
     * Action to change the GUI language.
     * </p>
     * 
     * @see LanguageUtil
     */
    public class LanguageChangeAction extends AbstractAction {

        private Locale locale;
        
        /**
         * <p>
         * Creates language change action
         * </p>
         * 
         * @param name The name of the actions (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param locale The locale to set the language
         */
        LanguageChangeAction(String name, javax.swing.ImageIcon icon, String desc, Locale locale) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            this.locale = locale;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LanguageUtil.setLanguage(locale.getCountry(), locale.getLanguage());
            Andie.refreshGUI();
        }
    }
}
