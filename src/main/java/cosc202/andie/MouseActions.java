package cosc202.andie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ResourceBundle;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Implements mouse actions for selection and drawing on an image
 *
 * @author linad885
 */
public class MouseActions extends ImageAction implements MouseListener, MouseMotionListener {

    public static boolean isDragged = false;

    // Selection coordinates
    private Point startPoint = null;
    private Point endPoint = null;

    // Reference to the image panel
    private JPanel imagePanel;

    public MouseActions(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    public MouseActions(ResourceBundle bundle) {
        super(null, null, null, null);
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

    public static Action getDrawSquare() {
        return new DrawSquare("Square Selection", null, "Create a square selection", null);
    }

    public static Action getDrawCircle() {
        return new DrawCircle("Circle Selection", null, "Create a circle selection", null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Not used for selection
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
        System.out.println("Selection started at: " + startPoint);
        if (imagePanel != null) {
            imagePanel.repaint();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        endPoint = e.getPoint();
        if (endPoint.x > target.getImage().getCurrentImage().getWidth()) {
            endPoint.x = target.getImage().getCurrentImage().getWidth();
        }
        if (endPoint.y > target.getImage().getCurrentImage().getWidth()) {
            endPoint.y = target.getImage().getCurrentImage().getWidth();
        }
        System.out.println("Selected area from" + startPoint + " to " + endPoint);
        //drawSelectionSquare();

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        isDragged = true;
        System.out.println("Drawing...");

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Not used
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //not used
    }

    public void drawSelectionSquare(Graphics g) {
        //super.drawSelectionSquare(g);
        
        // Set the rectangle outline color to grey
        g.setColor(Color.GRAY);

        // Calculate the rectangle parameters
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(endPoint.x - startPoint.x);
        int height = Math.abs(endPoint.y - startPoint.y);

        // Draw just the rectangle outline
        g.drawRect(x, y, width, height);
    }
}

/**
 * Action to draw square
 */
class DrawSquare extends ImageAction {

    /**
     * draw a circle
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
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
        System.out.println("Sqaure: drawn");
    }
}

/**
 * Action to draw circle
 */
class DrawCircle extends ImageAction {

    /**
     * draw a square
     *
     * @param name The name of the action (ignored if null).
     * @param icon An icon to use to represent the action (ignored if null).
     * @param desc A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    DrawCircle(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    /**
     * draws a circle in the highlighted region
     *
     * @param e The event triggering this callback.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Curcle: drawn");
    }
}
