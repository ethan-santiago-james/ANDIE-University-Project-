package cosc202.andie;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

/**
 * A class for handling image transformation operations in ANDIE.
 *
 * @author linad885
 */
public class TransformActions {

    /**
     * A list of actions for the Transform menu.
     */
    protected ArrayList<Action> actions;
    private ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

    /**
     * Create a set of Transform menu actions.
     */
    public TransformActions(ResourceBundle bundle) {
        this.bundle = bundle;
        actions = new ArrayList<>();
        actions.add(new FlipVertical(bundle.getString("FLIP_VERTICAL"), null, bundle.getString("FLIP_VERTICAL"), KeyEvent.VK_5));
        actions.add(new FlipHorizontal(bundle.getString("FLIP_HORIZONTAL"), null, bundle.getString("FLIP_HORIZONTAL"), KeyEvent.VK_5));
        actions.add(new RotateClockwise(bundle.getString("ROTATE_90_CLOCKWISE"), null, bundle.getString("ROTATE_90_CLOCKWISE"), KeyEvent.VK_4));
        actions.add(new RotateAntiClockwise(bundle.getString("ROTATE_90_ANTICLOCKWISE"), null, bundle.getString("ROTATE_90_ANTICLOCKWISE"), KeyEvent.VK_4));
    }

    /**
     * Creates a menu button for the Transform tab
     *
     * @return Transform menu button
     */
    public JMenu createMenu() {
        JMenu transformMenu = new JMenu(bundle.getString("TRANSFORM"));

        for (Action action : actions) {
            transformMenu.add(new JMenuItem(action));
        }

        return transformMenu;
    }

    /**
     * Action to flip the image horizontally.
     */
    public class FlipHorizontal extends ImageAction {

        /**
         * Create a new FlipHorizontal action.
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        FlipHorizontal(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * transforms image calling FALSE, signifying to flip the image horizontally
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Apply the transformation
            
            try {
                
                target.getImage().apply(new TransformImage(true, false));
                target.repaint();
                target.getParent().revalidate(); 
            } catch(Exception ex) {
                
                JOptionPane.showMessageDialog(null,bundle.getString("PLEASE SELECT AN IMAGE."));
            }
            
        }
    }

    /**
     * Action to flip the image vertically.
     */
    public class FlipVertical extends ImageAction {

        /**
         * Create a new FlipVertical action.
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        FlipVertical(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * transforms image calling TRUE, signifying to flip the image vertically
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Apply transform - second paramater is to use the other constructor
            
            try {
                
                target.getImage().apply(new TransformImage(true, true));
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex) {
                
                JOptionPane.showMessageDialog(null,bundle.getString("PLEASE SELECT AN IMAGE."));
            }
            
        }
    }

    /**
     * Action to rotate the image 90 degrees clockwise.
     */
    public class RotateClockwise extends ImageAction {

        /**
         * Create a new RotateClockwise action.
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        RotateClockwise(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * calls the transform method with int of  1, signifying a clockwise rotate
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                
                target.getImage().apply(new TransformImage(1));
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex) {
                
                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            }
            
        }
    }

    /**
     * Action to rotate the image 90 degrees counter-clockwise.
     */
    public class RotateAntiClockwise extends ImageAction {

        /**
         * Create a new RotateAntiClockwise action.
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        RotateAntiClockwise(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * calls the transform method with int of 2, signifying a anticlockwise rotate
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                
                target.getImage().apply(new TransformImage(2));
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex) {
                
                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            }
            
        }
    }
}
