/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>
 * Draws a rectangle between two points
 * </p>
 *
 * <p>
 * Draws a coloured rectangle between two selected points highlighted with the mouse
 * </p>
 *
 *
 * @author Adam Lindbom
 */
public class DrawRectangle implements ImageOperation, java.io.Serializable {
    private Point startPoint;
    private Point endPoint;
    private Color colour;
    private boolean fill;
    
    /**
     * draw a rectangle drawing from the selected points
     *
     * @param startPoint
     * @param endPoint
     * @param colour
     * @param fill
     */
    public DrawRectangle(Point startPoint, Point endPoint, Color colour, boolean fill) {
        this.startPoint = new Point(startPoint);
        this.endPoint = new Point(endPoint);
        this.colour = colour;
        this.fill = fill;
    }
    
    /**
     * Apply the rectangle drawing operation to an image
     *
     * @param input The image to draw the rectangle on
     * @return The resulting image with the rectangle drawn
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
        
        // Calculate rectangle dimensions
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
        
        // Draw the rectangle
        if (fill) {
            g2d.fillRect(x, y, width, height);
        } else {
            g2d.drawRect(x, y, width, height);
        }
        
        // Clean up
        g2d.dispose();
        
        return output;
    }
}
