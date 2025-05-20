package cosc202.andie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * <p>
 * UI Buttons and action calls for Mouse related actions
 * </p>
 *
 * <p>
 * Handles the actions for mouse related calls, such as cropping to selected area or
 * drawing shapes to a selection, as well as actions related to the shapes
 * being drawn
 * </p>
 *
 *
 * @author Adam Lindbom
 */
public class MouseActions extends ImageAction implements MouseListener, MouseMotionListener {

    public static Point startPoint = null;
    public static Point endPoint = null;
    public static Color shapeColour = new Color(255, 0, 0);
    public static boolean fillShape = false;

    private JPanel imagePanel;
    private ResourceBundle bundle = ResourceBundle.getBundle("bundle");

    public MouseActions(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    public MouseActions(ResourceBundle bundle) {
        super(null, null, null, null);
        this.bundle = bundle;
    }

    /**
     * Set the image panel that this mouse action will operate on
     *
     * @param panel The image panel to modify
     */
    public void setImagePanel(JPanel panel) {
        this.imagePanel = panel;
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
    }

    public Action getDrawSquare() throws IOException{
        return new DrawSquare(null, Andie.getIcon("icons/square.png"), bundle.getString("DRAW SQUARE"), null);
    }

    public Action getDrawCircle() throws IOException{
        return new DrawCircle(null, Andie.getIcon("icons/circle.png"), bundle.getString("DRAW CIRCLE"), null);
    }
    
    public Action getDrawLine() {
        return new DrawLineAction(bundle.getString("DRAW LINE"), null, bundle.getString("DRAW LINE"), null);
    }

    public Action getCropImage() throws IOException{
        return new CropImageAction(null, Andie.getIcon("icons/crop.png"), bundle.getString("CROP IMAGE"), null);
    }

    public Action getFillShape() {
        return new FillShapeAction(bundle.getString("FILL SHAPE"), null, bundle.getString("FILL SHAPE"), null);
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        if (!target.getImage().hasImage()) {
            return;
        }
        endPoint = null;
        target.repaint();
        startPoint = e.getPoint();
        int imageWidth = target.getImage().getCurrentImage().getWidth();
        int imageHeight = target.getImage().getCurrentImage().getHeight();

        if (startPoint.x > imageWidth) {
            startPoint = null;
            return;
        }

        if (startPoint.y > imageHeight) {
            startPoint = null;
            return;
        }

        if (imagePanel != null) {
            imagePanel.repaint();
        }

    }

    /** Compulsory mouse listener interface method not used */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /** Compulsory mouse listener interface method not used */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Not used
    }

    /** Compulsory mouse listener interface method not used */
    @Override
    public void mouseExited(MouseEvent e) {
        // Not used
    }

    /** Mouse listener interface method that listens to when the mouse is dragged */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (!target.getImage().hasImage()) {
            return;
        }
        endPoint = e.getPoint();

        int imageWidth = (int) (target.getImage().getCurrentImage().getWidth() * (target.getZoom() / 100));
        int imageHeight = (int) (target.getImage().getCurrentImage().getHeight() * (target.getZoom() / 100));

        if (endPoint.x > imageWidth) {
            endPoint.x = imageWidth;
        }

        if (endPoint.x < 0) {
            endPoint.x = 0;
        }

        if (endPoint.y > imageHeight) {
            endPoint.y = imageHeight;
        }
        if (endPoint.y < 0) {
            endPoint.y = 0;
        }

        target.repaint();
    }

    /** Compulsory mouse listener interface method not used */
    @Override
    public void mouseMoved(MouseEvent e) {
        // Not used
    }

    /** Compulsory mouse listener interface method not used */
    @Override
    public void actionPerformed(ActionEvent e) {
        //not used
    }

    /** Compulsory mouse listener interface method not used */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Not used
    }

}

/**
 * Action to draw square
 */
class DrawSquare extends ImageAction {

    private static ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

    /**
     * draw a circle
     *
     * @param name
     * @param icon
     * @param desc
     * @param mnemonic
     */
    DrawSquare(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    /**
     * draws a square in the highlighted region
     *
     *
     * @param e The event triggering this callback.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!target.getImage().hasImage()) {
            JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            return;
        }

        // Check if area selected
        if (MouseActions.startPoint == null || MouseActions.endPoint == null) {
            JOptionPane.showMessageDialog(null, bundle.getString("MAKE SELECTION"), bundle.getString("NO SELECTION"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Apply the draw rectangle method
        target.getImage().apply(new DrawRectangle(
                MouseActions.startPoint,
                MouseActions.endPoint,
                MouseActions.shapeColour,
                MouseActions.fillShape
        ));

        MouseActions.startPoint = null;

        target.repaint();
        target.getParent().revalidate();

    }
}

/**
 * Action to draw circle
 */
class DrawCircle extends ImageAction {

    private static ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

    /**
     * draw a circle
     *
     * @param name
     * @param icon
     * @param desc
     * @param mnemonic
     */
    DrawCircle(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    /**
     * draws a circle in the highlighted region
     *
     *
     * @param e The event triggering this callback.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!target.getImage().hasImage()) {
            JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            return;
        }

        // Check if area selected
        if (MouseActions.startPoint == null || MouseActions.endPoint == null) {
            JOptionPane.showMessageDialog(null, bundle.getString("MAKE SELECTION"), bundle.getString("NO SELECTION"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Apply the draw circle method
        target.getImage().apply(new DrawOval(
                MouseActions.startPoint,
                MouseActions.endPoint,
                MouseActions.shapeColour,
                MouseActions.fillShape
        ));

        MouseActions.startPoint = null;

        target.repaint();
        target.getParent().revalidate();

    }
}

/**
 * Action to crop an image
 */
class CropImageAction extends ImageAction {

    private static ResourceBundle bundle = ResourceBundle.getBundle("bundle");

    /**
     * crops image to the highlighted area
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    CropImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    /**
     * crops to highlighted reigon
     *
     * @param e The event triggering this callback.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!target.getImage().hasImage()) {
            JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            return;
        }

        //check if area selected
        if (MouseActions.startPoint == null || MouseActions.endPoint == null) {
            JOptionPane.showMessageDialog(null, bundle.getString("MAKE SELECTION"), bundle.getString("NO SELECTION"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Apply the crop operation
        target.getImage().apply(new CropImage(MouseActions.startPoint, MouseActions.endPoint));

        // Reset selection points
        MouseActions.startPoint = null;
        MouseActions.endPoint = null;

        // Update the display
        target.repaint();
        target.getParent().revalidate();

    }
}

/**
 * Action to crop an image
 */
class DrawLineAction extends ImageAction {

    private static ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

    /**
     * draws line between two points
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    DrawLineAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    /**
     * draws line to highlighted reigon
     *
     * @param e The event triggering this callback.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!target.getImage().hasImage()) {
            JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            return;
        }

        // Check if area selected
        if (MouseActions.startPoint == null || MouseActions.endPoint == null) {
            JOptionPane.showMessageDialog(null, bundle.getString("MAKE SELECTION"), bundle.getString("NO SELECTION"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Apply the draw circle method
        target.getImage().apply(new DrawLine(
                MouseActions.startPoint,
                MouseActions.endPoint,
                MouseActions.shapeColour
        ));

        MouseActions.startPoint = null;

        target.repaint();
        target.getParent().revalidate();

    }
}

/**
 * Action to toggle fill
 */
class FillShapeAction extends ImageAction {

    private static ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

    /**
     * toggles if drawn shape is filled in or not
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    FillShapeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    /**
     * toggles if drawn shape is filled in or not
     *
     * @param e The event triggering this callback.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (MouseActions.fillShape) {
            MouseActions.fillShape = false;
        } else {
            MouseActions.fillShape = true;
        }
    }
}
