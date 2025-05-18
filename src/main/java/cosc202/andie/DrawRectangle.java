/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 *
 * @author linad885
 */
public class DrawRectangle implements ImageOperation {
    private Point startPoint;
    private Point endPoint;
    private Color color;
    private boolean fill;
    private int strokeWidth;
    
    /**
     * draw a rectangle drawing from the selected points
     *
     * @param startPoint
     * @param endPoint
     * @param color
     * @param fill
     * @param strokeWidth
     */
    public DrawRectangle(Point startPoint, Point endPoint, Color color, boolean fill, int strokeWidth) {
        this.startPoint = new Point(startPoint);
        this.endPoint = new Point(endPoint);
        this.color = color;
        this.fill = fill;
        this.strokeWidth = strokeWidth;
    }
    
    /**
     * Apply the rectangle drawing operation to an image
     *
     * @param input The image to draw the rectangle on
     * @return The resulting image with the rectangle drawn
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        // Create a copy of the input image
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
        Graphics2D g2d = output.createGraphics();
        
        // Draw the original image first
        g2d.drawImage(input, 0, 0, null);
        
        // Set the color and stroke
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));
        
        // Calculate rectangle dimensions
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(endPoint.x - startPoint.x);
        int height = Math.abs(endPoint.y - startPoint.y);
        
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
