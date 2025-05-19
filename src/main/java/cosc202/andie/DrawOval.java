package cosc202.andie;
import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * <p>
 * Draws an Oval between two points
 * </p>
 *
 * <p>
 * Draws a coloured Oval between two selected points highlighted with the mouse
 * </p>
 *
 *
 * @author Adam Lindbom
 */
public class DrawOval implements ImageOperation {
    private Point startPoint;
    private Point endPoint;
    private Color colour;
    private boolean fill;
    
    /**
     * Create a new oval drawing operation from the selected points
     *
     * @param startPoint Initial point of the selection
     * @param endPoint Final point of the selection
     * @param colour Colour to draw the oval with
     * @param fill Whether to fill the oval or just draw the outline
     */
    public DrawOval(Point startPoint, Point endPoint, Color colour, boolean fill) {
        this.startPoint = new Point(startPoint);
        this.endPoint = new Point(endPoint);
        this.colour = colour;
        this.fill = fill;
    }
    
    /**
     * Apply the oval drawing operation to an image
     *
     * @param input The image to draw the oval on
     * @return The resulting image with the oval drawn
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        // Create a copy of the image
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
        Graphics2D g2d = output.createGraphics();
        
        // Draw the original image first
        g2d.drawImage(input, 0, 0, null);
        
        // Set the colour
        g2d.setColor(colour);
        
        // Get current zoom level from the target
        double zoomFactor = ImageAction.target.getZoom() / 100.0;
        
        // Adjust points for zoom
        int x1 = (int)(startPoint.x / zoomFactor);
        int y1 = (int)(startPoint.y / zoomFactor);
        int x2 = (int)(endPoint.x / zoomFactor);
        int y2 = (int)(endPoint.y / zoomFactor);
        
        // Calculate oval dimensions
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        
        // Draw the oval
        if (fill) {
            g2d.fillOval(x, y, width, height);
        } else {
            g2d.drawOval(x, y, width, height);
        }
        
        // Clean up
        g2d.dispose();
        
        return output;
    }
}