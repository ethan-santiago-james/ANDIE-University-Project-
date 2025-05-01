package cosc202.andie;

import java.awt.GridLayout;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 *
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood. This includes a mean filter (a simple blur)
 * in the sample code, but more operations will need to be added.
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
public class FilterActions {

    private ResourceBundle bundle = ResourceBundle.getBundle("bundle");

    /**
     * A list of actions for the Filter menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     * @param bundle language bundle for switching languages
     */
    public FilterActions(ResourceBundle bundle) {
        this.bundle = bundle;
        actions = new ArrayList<>();
        actions.add(new MeanFilterAction(bundle.getString("MEAN FILTER"), null, bundle.getString("APPLY A MEAN FILTER"), KeyEvent.VK_M));
        actions.add(new GaussianBlurFilterAction(bundle.getString("GAUSSIAN FILTER"), null, bundle.getString("APPLY A GAUSSIAN FILTER"), KeyEvent.VK_M));
        actions.add(new SharpenFilterAction(bundle.getString("SHARPEN FILTER"), null, bundle.getString("APPLY A SHARPEN FILTER"), KeyEvent.VK_M));
        actions.add(new MedianFilterAction(bundle.getString("MEDIAN FILTER"), null, bundle.getString("APPLY A MEDIAN FILTER"), 0));
        actions.add(new BlockAveragingAction(bundle.getString("BLOCK FILTER"), null, bundle.getString("APPLY BLOCK FILTER"), KeyEvent.VK_B));
    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     *
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("FILTER"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     *
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the MeanFilterAction is triggered. It
         * prompts the user for a filter radius, then applies an appropriately
         * sized {@link MeanFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;

            if (!target.getImage().hasImage()) {

                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
                return;
            }
            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("ENTER FILTER RADIUS"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            try {

                target.getImage().apply(new MeanFilter(radius));
                target.repaint();
                target.getParent().revalidate();
                
            } catch(Exception ex) {
                
                JOptionPane.showMessageDialog(null,bundle.getString("PLEASE SELECT AN IMAGE."));
            }
        }

    }

    /**
     * <p>
     * Action to blur an image with a median filter.
     * </p>
     *
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median-filter action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the median filter action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an
         * appropriately sized {@link MeanFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;
            if (!target.getImage().hasImage()) {

                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
                return;
            }

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("ENTER FILTER RADIUS"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            try {

                target.getImage().apply(new MedianFilter(radius));
                target.repaint();
                target.getParent().revalidate();
                
            } catch(Exception ex) {
                
                JOptionPane.showMessageDialog(null,bundle.getString("PLEASE SELECT AN IMAGE."));      
                
            }

        }

    }

    /**
     * <p>
     * Action to blur an image with a gaussian blur filter.
     * </p>
     *
     * @see MeanFilter
     */
    public class GaussianBlurFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new gaussian-blur-filter action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        GaussianBlurFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the GaussianBlurFilterAction is
         * triggered. It prompts the user for a filter radius, then applies an
         * appropriately sized {@link MeanFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, bundle.getString("ENTER FILTER RADIUS"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }
            try {

                // Create and apply the filter
                target.getImage().apply(new GaussianBlur(radius));
                target.repaint();
                target.getParent().revalidate();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));

            }

        }
    }

    /**
     * <p>
     * Action to sharpen an image with a covolution based filter.
     * </p>
     *
     * @see MeanFilter
     */
    public class SharpenFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sharpen-filter action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the SharpenFilterAction is triggered.
         * it applies a sharpening filter. sized {@link MeanFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                // Create and apply the filter
                target.getImage().apply(new SharpenFilter());
                target.repaint();
                target.getParent().revalidate();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));

            }

        }

    }
    
        /**
     * <p>
     * Action to apply block averaging to image.
     * </p>
     *
     * @see BlockAveraging
     */
    public class BlockAveragingAction extends ImageAction {
        
        /**
         * <p>
         * Create a new block-averaging action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        BlockAveragingAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        
        /**
         * <p>
         * Callback for when the block-averaging action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the BlockAveragingAction is triggered.
         * It changes creates blocks to pixelate the image.
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

            //brightness input
            JLabel heightLabel = new JLabel(bundle.getString("BLOCK HEIGHT"));
            SpinnerNumberModel heightModel = new SpinnerNumberModel(2, 2, 100, 1);
            JSpinner heightSpinner = new JSpinner(heightModel);

            //contrast input
            JLabel widthLabel = new JLabel(bundle.getString("BLOCK WIDTH"));
            SpinnerNumberModel widthModel = new SpinnerNumberModel(2, 2, 100, 1);
            JSpinner widthSpinner = new JSpinner(widthModel);

            panel.add(heightLabel);
            panel.add(heightSpinner);
            panel.add(widthLabel);
            panel.add(widthSpinner);

            //show the option dialog with the panel
            int option = JOptionPane.showOptionDialog(
                    null,
                    panel,
                    bundle.getString("ENTER HEIGHT AND WIDTH OF BLOCKS"),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null
            );

            int height = 0;
            int width = 0;
            if (option == JOptionPane.OK_OPTION) {
                height = heightModel.getNumber().intValue();
                width = widthModel.getNumber().intValue();
            } else {
                return;
            }
            
            try {
                target.getImage().apply(new BlockAveraging(width, height));
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            }
        }
    }

}
