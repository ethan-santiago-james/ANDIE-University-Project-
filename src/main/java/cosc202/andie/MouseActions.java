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

    public static Point startPoint = null;
    public static Point endPoint = null;

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
    public void mousePressed(MouseEvent e) {
        endPoint = null;
        target.repaint();
        startPoint = e.getPoint();
        int imageWidth = target.getImage().getCurrentImage().getWidth();
        int imageHeight = target.getImage().getCurrentImage().getHeight();
        
        if(startPoint.x > imageWidth){
            startPoint = null;
            return;
        }
        
        if(startPoint.y > imageHeight){
            startPoint = null;
            return;
        }
        
        if (imagePanel != null) {
            imagePanel.repaint();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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
        endPoint = e.getPoint();
        
        int imageWidth = (int) (target.getImage().getCurrentImage().getWidth() * (target.getZoom()/100));
        int imageHeight = (int) (target.getImage().getCurrentImage().getHeight() * (target.getZoom()/100));
        
        if (endPoint.x > imageWidth) {
            endPoint.x = imageWidth;
        }

        if (endPoint.y > imageHeight) {
            endPoint.y = imageHeight;
        }

        target.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Not used
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //not used
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // Not used
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
        System.out.println("Sqaure: drawn from " + MouseActions.startPoint + " to " + MouseActions.endPoint);
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
