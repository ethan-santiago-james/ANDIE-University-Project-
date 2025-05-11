package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * ImageOperation to crop an image based on user selection.
 *
 * @author linad885
 */
public class CropImage implements ImageOperation, Serializable {

    private Point startPoint;
    private Point endPoint;

    /**
     * Construct a crop operation from the selected points
     *
     * @param startPoint The starting point of the selection
     * @param endPoint The ending point of the selection
     */
    public CropImage(Point startPoint, Point endPoint) {
        this.startPoint = new Point(startPoint);
        this.endPoint = new Point(endPoint);
    }

    /**
     * Apply the crop operation to an image
     *
     * @param input The image to apply the crop operation to
     * @return The resulting cropped image
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        // Calculate the the crop region
        int xOrigin = Math.min(startPoint.x, endPoint.x);
        int yOrigin = Math.min(startPoint.y, endPoint.y);
        int cropWidth = Math.abs(endPoint.x - startPoint.x);
        int cropHeight = Math.abs(endPoint.y - startPoint.y);

        return input.getSubimage(xOrigin, yOrigin, cropWidth, cropHeight);
    }
}
