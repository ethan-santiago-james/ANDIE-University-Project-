/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>
 * Draws line between two points
 * </p>
 *
 * <p>
 * Draws a coloured line between two selected points highlighted with the mouse
 * </p>
 *
 *
 * @author Adam Lindbom
 */
public class DrawLine implements ImageOperation, java.io.Serializable {

    private Point startPoint;
    private Point endPoint;
    private Color colour;

    /**
     * draw a line drawing from the selected points
     *
     * @param startPoint
     * @param endPoint
     * @param colour
     */
    public DrawLine(Point startPoint, Point endPoint, Color colour) {
        this.startPoint = new Point(startPoint);
        this.endPoint = new Point(endPoint);
        this.colour = colour;
    }

    /**
     * Apply the line drawing operation to an image
     *
     * @param input The image to draw the line on
     * @return The resulting image with the line drawn
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        // Create a copy of the image
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
        Graphics2D g2d = output.createGraphics();

        // Draw the original image first
        g2d.drawImage(input, 0, 0, null);

        // Set the colour and stroke
        g2d.setColor(colour);

        // Get current zoom level from the target
        double zoomFactor = ImageAction.target.getZoom() / 100.0;

        // Adjust points for zoom
        int x1 = (int) (startPoint.x / zoomFactor);
        int y1 = (int) (startPoint.y / zoomFactor);
        int x2 = (int) (endPoint.x / zoomFactor);
        int y2 = (int) (endPoint.y / zoomFactor);

        // Draw the line
        g2d.drawLine(x1, y1, x2, y2);

        // Clean up
        g2d.dispose();

        return output;
    }
}
