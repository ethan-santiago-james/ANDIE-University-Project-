package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the View menu.
 * </p>
 *
 * <p>
 * The View menu contains actions that affect how the image is displayed in the
 * application. These actions do not affect the contents of the image itself,
 * just the way it is displayed.
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
public class ViewActions {


    private static ResourceBundle bundle = ResourceBundle.getBundle("bundle");

    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     *
     * @param bundle language bundle for switching languages
     */
    public ViewActions(ResourceBundle bundle) {
        this.bundle = bundle;
        actions = new ArrayList<>();
        actions.add(new ZoomInAction(bundle.getString("ZOOM IN"), null, bundle.getString("ZOOM IN"), KeyEvent.VK_PLUS));
        actions.add(new ZoomOutAction(bundle.getString("ZOOM OUT"), null, bundle.getString("ZOOM OUT"), KeyEvent.VK_MINUS));
        actions.add(new ZoomTo150(bundle.getString("RESIZE 150%"), null, bundle.getString("RESIZE 150%"), KeyEvent.VK_2));
        actions.add(new ZoomTo50(bundle.getString("RESIZE 50%"), null, bundle.getString("RESIZE 50%"), KeyEvent.VK_3));
        actions.add(new ZoomFullAction(bundle.getString("ZOOM FULL"), null, bundle.getString("ZOOM FULL"), KeyEvent.VK_1));
        actions.add(new CustomZoom(bundle.getString("CUSTOM ZOOM"), null, bundle.getString("CUSTOM ZOOM"), KeyEvent.VK_Z));
    }

    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     *
     * @return The view menu UI element.
     */
    public JMenu createMenu() {
        JMenu viewMenu = new JMenu(bundle.getString("VIEW"));

        for (Action action : actions) {
            viewMenu.add(new JMenuItem(action));
        }

        return viewMenu;
    }

    public Action getZoomInAction() throws IOException {
        return new ZoomInAction(null, Andie.getIcon("icons/zoom-in.png"), bundle.getString("ZOOM IN"), null);
    }

    public Action getZoomOutAction() throws IOException {
        return new ZoomOutAction(null, Andie.getIcon("icons/zoom-out.png"), bundle.getString("ZOOM OUT"), null);
    }

    public Action getZoomTo50() {
        return new ZoomTo50("Z50", null, bundle.getString("RESIZE 50%"), null);
    }

    public Action getZoomTo150() {
        return new ZoomTo150("Z150", null, bundle.getString("RESIZE 150"), null);
    }

    public Action getCustomZoom() throws IOException {
        return new CustomZoom(null, Andie.getIcon("icons/resize.png"), bundle.getString("CUSTOM ZOOM"), null);
    }

    /**
     * <p>
     * Action to zoom in on an image.
     * </p>
     *
     * <p>
     * Note that this action only affects the way the image is displayed, not
     * its actual contents.
     * </p>
     */
    public class ZoomInAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-in action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ZoomInAction is triggered. It
         * increases the zoom level by 10%, to a maximum of 200%.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            zoomIn();
        }

        public static void zoomIn() {

            if (!target.getImage().hasImage()) {

                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
                return;
            }
            target.setZoom(target.getZoom() + 10);
            target.repaint();
            target.getParent().revalidate();

        }

    }

    /**
     * <p>
     * Action to zoom out of an image.
     * </p>
     *
     * <p>
     * Note that this action only affects the way the image is displayed, not
     * its actual contents.
     * </p>
     */
    public class ZoomOutAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-out action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-out action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ZoomOutAction is triggered. It
         * decreases the zoom level by 10%, to a minimum of 50%.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            zoomOut();
        }

        public static void zoomOut() {
            if (!target.getImage().hasImage()) {

                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
                return;
            }
            target.setZoom(target.getZoom() - 10);
            target.repaint();
            target.getParent().revalidate();

        }

    }

    /**
     * <p>
     * Action to reset the zoom level to the original image size.
     * </p>
     *
     * <p>
     * Note that this action only affects the way the image is displayed, not
     * its actual contents.
     * </p>
     */
    public class ZoomFullAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-full action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ZoomFullAction is triggered. It
         * resets the zoom level to match the original image size.
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
            // Reset to the original image's zoom value (100% is the default)
            target.setZoom(100);
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to resize the image to 150% of its original size.
     * </p>
     *
     * <p>
     * This action actually changes the image content, not just the display.
     * </p>
     */
    public class ZoomTo150 extends ImageAction {

        /**
         * <p>
         * Create a new resize to 150% action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ZoomTo150(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the resize to 150% action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ZoomTo150 is triggered. It resizes
         * the image to 150% of its original size.
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
            // Apply a resize operation to the image
            target.setZoom(150);
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to resize the image to 50% of its original size.
     * </p>
     *
     * <p>
     * This action actually changes the image content, not just the display.
     * </p>
     */
    public class ZoomTo50 extends ImageAction {

        /**
         * <p>
         * Create a new resize to 50% action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ZoomTo50(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the resize to 50% action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ZoomTo50 is triggered. It resizes
         * the image to 50% of its original size.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Apply a resize operation to the image
            if (!target.getImage().hasImage()) {

                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
                return;
            }
            target.setZoom(50);
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to resize the image by a custom percentage specified by the user.
     * </p>
     *
     * <p>
     * This action opens a dialog box to get the resize percentage from the
     * user.
     * </p>
     */
    public class CustomZoom extends ImageAction {

        /**
         * <p>
         * Create a new custom resize action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        CustomZoom(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the custom resize action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the CustomZoomAction is triggered. It
         * displays a dialog box to get the resize percentage and then applies
         * that resize to the image.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a dialog to get the resize percentage
            String input = JOptionPane.showInputDialog(null,
                    bundle.getString("ENTER ZOOM PERCENTAGE"),
                    bundle.getString("CUSTOM ZOOM"),
                    JOptionPane.QUESTION_MESSAGE);

            // Check if the user cancelled the dialog
            if (input == null) {
                return;
            }

            if (!target.getImage().hasImage()) {

                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
                return;
            }

            try {
                // Parse the input as a double
                double percentage = Double.parseDouble(input);

                // Validate the input
                if (percentage <= 0) {
                    JOptionPane.showMessageDialog(null,
                            bundle.getString("INVALID ZOOM VALUE"),
                            bundle.getString(""),
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Convert the percentage to a scale factor
                double scaleFactor = percentage / 100.0;

                // Apply the resize transformation
                target.getImage().apply(new ResizeTransform(scaleFactor));
                target.repaint();
                target.getParent().revalidate();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        bundle.getString("INVALID ZOOM VALUE"),
                        bundle.getString(""),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
