package cosc202.andie;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 *
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly without reference to the rest of the image. This includes conversion
 * to greyscale in the sample code, but more operations will need to be added.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {

    private ResourceBundle bundle = ResourceBundle.getBundle("bundle");

    /**
     * A list of actions for the Colour menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     * @param bundle language bundle for switching languages
     */
    public ColourActions(ResourceBundle bundle) {
        this.bundle = bundle;
        actions = new ArrayList<>();
        actions.add(new ConvertToGreyAction(bundle.getString("GREYSCALE"), null, bundle.getString("CONVERT TO GREYSCALE"), KeyEvent.VK_G));
        actions.add(new CycleColorChannelsAction("RGB > RBG", null, bundle.getString("CYCLE COLOR CHANNELS"), KeyEvent.VK_1, 1));
        actions.add(new CycleColorChannelsAction("RGB > GRB", null, bundle.getString("CYCLE COLOR CHANNELS"), KeyEvent.VK_2, 2));
        actions.add(new CycleColorChannelsAction("RGB > GBR", null, bundle.getString("CYCLE COLOR CHANNELS"), KeyEvent.VK_3, 3));
        actions.add(new CycleColorChannelsAction("RGB > BRG", null, bundle.getString("CYCLE COLOR CHANNELS"), KeyEvent.VK_4, 4));
        actions.add(new CycleColorChannelsAction("RGB > BGR", null, bundle.getString("CYCLE COLOR CHANNELS"), KeyEvent.VK_5, 5));
        actions.add(new InvertColorsAction(bundle.getString("INVERT"), null, bundle.getString("INVERT COLORS"), KeyEvent.VK_I));
        actions.add(new ChangeColourAction(bundle.getString("CHANGE COLOUR"), null, bundle.getString("CHANGE COLOUR"), KeyEvent.VK_6));
    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     *
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("COLOUR"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     *
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            
            try {
                target.getImage().apply(new ConvertToGrey());
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex) {
                
                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
                
            }
        }

    }
    
    public class CycleColorChannelsAction extends ImageAction {
        
        private int cycleType;
        /**
         * <p>
         * Create a new cycle color channels action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         * @param cycleType The type of cycle selected (1 to 5)
         */
        CycleColorChannelsAction(String name, ImageIcon icon, String desc, Integer mnemonic, int cycleType) {
            super(name, icon, desc, mnemonic);
            this.cycleType = cycleType;
        }

        /**
         * <p>
         * Callback for when the cycle-color-channels action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the CycleColorChannelsAction is triggered.
         * It changes the image by cycling color channels.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            
            try {
               
                target.getImage().apply(new CycleColorChannels(cycleType));
                target.repaint();
                target.getParent().revalidate();
            } catch(Exception ex) {
                
                JOptionPane.showMessageDialog(null, "Please select an image");
            }
            
        }

    }
    
    /**
     * <p>
     * Action to invert image colors.
     * </p>
     *
     * @see InvertColors
     */
    public class InvertColorsAction extends ImageAction {

        /**
         * <p>
         * Create a new invert-colors action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        InvertColorsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the invert-colors action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the InvertColorsAction is triggered.
         * It changes the image to inverted colors.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            
            try {
                
               target.getImage().apply(new InvertColors());
               target.repaint();
               target.getParent().revalidate();    
            } catch(Exception ex) {
                
                JOptionPane.showMessageDialog(null, "Please select an image.");
                
            }
            
        }

    }


}


class ChangeColourAction extends ImageAction {

    private static ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

    /**
     * Creates a dialog to change the colour of drawn shapes
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ChangeColourAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    /**
     * Shows a dialog to change the R, G, B values of the shape colour
     *
     * @param e The event triggering this callback.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Create a panel for the input fields
        JPanel colorPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        // Create text fields for R, G, B values
        JTextField redField = new JTextField(5);
        JTextField greenField = new JTextField(5);
        JTextField blueField = new JTextField(5);
        
        // Set the text fields to the current colour values
        redField.setText(String.valueOf(MouseActions.shapeColour.getRed()));
        greenField.setText(String.valueOf(MouseActions.shapeColour.getGreen()));
        blueField.setText(String.valueOf(MouseActions.shapeColour.getBlue()));
        
        // Add labels and text fields to the panel
        colorPanel.add(new JLabel(bundle.getString("RED") + ":"));
        colorPanel.add(redField);
        colorPanel.add(new JLabel(bundle.getString("GREEN") + ":"));
        colorPanel.add(greenField);
        colorPanel.add(new JLabel(bundle.getString("BLUE") + ":"));
        colorPanel.add(blueField);
        
        // Show the dialog
        int result = JOptionPane.showConfirmDialog(
                null, 
                colorPanel, 
                bundle.getString("CHANGE COLOUR"), 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE);
        
        // Process the result
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Parse the input as integers
                int red = Integer.parseInt(redField.getText().trim());
                int green = Integer.parseInt(greenField.getText().trim());
                int blue = Integer.parseInt(blueField.getText().trim());
                
                // Validate the input values (0-255)
                red = Math.max(0, Math.min(255, red));
                green = Math.max(0, Math.min(255, green));
                blue = Math.max(0, Math.min(255, blue));
                
                // Set the new colour
                MouseActions.shapeColour = new Color(red, green, blue);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        bundle.getString("INVALID COLOUR"),
                        bundle.getString("ERROR"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
