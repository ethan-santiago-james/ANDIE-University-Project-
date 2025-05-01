package cosc202.andie;

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
        actions.add(new BrightnessContrastAction(bundle.getString("ADJUST"), null, bundle.getString("ADJUST BRIGHTNESS AND CONTRAST"), KeyEvent.VK_A));
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
     * Action to change brightness and contrast of image.
     * </p>
     *
     * @see BrightnessContrast
     */
    public class BrightnessContrastAction extends ImageAction {
        
        /**
         * <p>
         * Create a new brightness-contrast action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        BrightnessContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        
        /**
         * <p>
         * Callback for when the brightness-contrast action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the BrightnessContrastAction is triggered.
         * It changes the image's brightness and contrast.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!target.getImage().hasImage()) {
                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
                return;
            }

            //create a JPanel to hold both spinners
            JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5)); //2 rows, 2 columns, 5 spacing

            //brightness input actually contrast
            JLabel brightnessLabel = new JLabel(bundle.getString("CONTRAST"));
            SpinnerNumberModel brightnessModel = new SpinnerNumberModel(0, -100, 100, 1);
            JSpinner brightnessSpinner = new JSpinner(brightnessModel);

            //contrast input actually brightness
            JLabel contrastLabel = new JLabel(bundle.getString("BRIGHTNESS"));
            SpinnerNumberModel contrastModel = new SpinnerNumberModel(0, -100, 100, 1);
            JSpinner contrastSpinner = new JSpinner(contrastModel);

            panel.add(brightnessLabel);
            panel.add(brightnessSpinner);
            panel.add(contrastLabel);
            panel.add(contrastSpinner);

            //show the option dialog with the panel
            int option = JOptionPane.showOptionDialog(
                    null,
                    panel,
                    bundle.getString("ENTER BRIGHTNESS AND CONTRAST"),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null
            );

            int brightness = 0;
            int contrast = 0;
            if (option == JOptionPane.OK_OPTION) {
                brightness = brightnessModel.getNumber().intValue();
                contrast = contrastModel.getNumber().intValue();
            } else {
                return;
            }
            
            try {
                target.getImage().apply(new BrightnessContrast(brightness, contrast));
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            }
        }
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
