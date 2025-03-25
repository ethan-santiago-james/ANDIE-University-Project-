package cosc202.andie;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.ResourceBundle;

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

    // Tracks whether image is flipped horizontally
    private static boolean isFlippedHorizontally = false;

    // Tracks whether image is flipped vertically
    private static boolean isFlippedVertically = false;

    // Tracks the images current rotation state (0=0, 1=90, 2=180, 3=270)
    private static int rotationState = 0;

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
     * A class that handles the actual image transformation based on state of variables.
     */
    public class TransformImage extends ImageAction {

        /**
         * Create a new TransformImage action.
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        TransformImage(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * Apply the current transformation to the image based on rotation and
         * flip states.
         */
        public void applyTransformation() {
            // Get the dimensions of the current image
            int width = target.getImage().getCurrentImage().getWidth();
            int height = target.getImage().getCurrentImage().getHeight();

            // Create a new transformation
            AffineTransform at = new AffineTransform();

            //check the current number of rotates and transform accordingly
            if (rotationState == 0) {
            } else if (rotationState == 1) {
                at.translate(height, 0);
                at.rotate(Math.PI / 2);
            } else if (rotationState == 2) {
                at.translate(width, height);
                at.rotate(Math.PI);
            } else if (rotationState == 3) {
                at.translate(0, width);
                at.rotate(3 * Math.PI / 2);
            }

            //flips based on rotation state
            if (rotationState == 0 || rotationState == 2) {
                // Original orientation or 180-degree rotation
                if (isFlippedHorizontally) {
                    at.translate(width, 0);
                    at.scale(-1, 1);
                }
                if (isFlippedVertically) {
                    at.translate(0, height);
                    at.scale(1, -1);
                }
            } else {
                // 90 or 270-degree rotation (width and height are swapped)
                if (isFlippedHorizontally) {
                    at.translate(0, height);
                    at.scale(1, -1);
                }
                if (isFlippedVertically) {
                    at.translate(width, 0);
                    at.scale(-1, 1);
                }
            }

            // Apply the transformation to the target
            target.setTransform(at);
            target.repaint();
            target.getParent().revalidate();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    /**
     * Action to flip the image horizontally.
     */
    public class FlipHorizontal extends TransformImage {

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
         * Toggle the horizontal flip state and apply transformation.
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Toggle the horizontal flip state
            isFlippedHorizontally = !isFlippedHorizontally;

            // Apply the transformation
            applyTransformation();
        }
    }

    /**
     * Action to flip the image vertically.
     */
    public class FlipVertical extends TransformImage {

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
         * Toggle the vertical flip state and apply transformation.
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Toggle the vertical flip state
            isFlippedVertically = !isFlippedVertically;

            // Apply the transformation
            applyTransformation();
        }
    }

    /**
     * Action to rotate the image 90 degrees clockwise.
     */
    public class RotateClockwise extends TransformImage {

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
         * Increment the rotation state (clockwise) and apply transformation.
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Increment rotation state (clockwise)
            rotationState += 1;

            if (rotationState >= 4) {
                rotationState = 0;
            }

            // Apply the transformation
            applyTransformation();
        }
    }

    /**
     * Action to rotate the image 90 degrees counter-clockwise.
     */
    public class RotateAntiClockwise extends TransformImage {

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
         * Decrement the rotation state (counter-clockwise) and apply
         * transformation.
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Increment by 3 mod 4 is equivalent to decrementing by 1 mod 4
            rotationState -= 1;

            if (rotationState <= -1) {
                rotationState = 3;
            }

            // Apply the transformation
            applyTransformation();
        }
    }
}
