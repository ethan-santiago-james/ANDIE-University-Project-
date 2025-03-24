package cosc202.andie;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class LanguageActions {

    private ResourceBundle bundle;
    protected ArrayList<Action> actions;

    public LanguageActions(ResourceBundle bundle) {
        this.bundle = bundle;
        actions = new ArrayList<>();
        actions.add(new LanguageChangeAction(bundle.getString("ENGLISH"), null, "English", Locale.ENGLISH));
        actions.add(new LanguageChangeAction(bundle.getString("SPANISH"), null, "Spanish", new Locale("es", "ES")));
        actions.add(new LanguageChangeAction(bundle.getString("FRENCH"), null, "French", Locale.FRANCE));
    }

    public JMenu createMenu() {
        JMenu languageMenu = new JMenu(bundle.getString("LANGUAGE"));
        for (Action action : actions) {
            languageMenu.add(new JMenuItem(action));
        }
        return languageMenu;
    }

    public class LanguageChangeAction extends AbstractAction {
        private Locale locale;

        LanguageChangeAction(String name, javax.swing.ImageIcon icon, String desc, Locale locale) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            this.locale = locale;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cosc202.andie.LanguageUtil.setLanguage(locale.getCountry(), locale.getLanguage());
            Andie.refreshGUI();
        }
    }
}