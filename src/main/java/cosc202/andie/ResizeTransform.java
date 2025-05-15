package cosc202.andie;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/*
* resises image custom amount
 * @author linad885
 */
public class ResizeTransform implements ImageOperation, java.io.Serializable {
    
    private double scaleFactor;
    
    /**
     * <p>
     * Create a new ResizeTransformation with the given scale factor.
     * </p>
     *
     * @param scaleFactor The factor to scale the image by.
     */
    public ResizeTransform(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
    
    /**
     * <p>
     * Apply the resize transformation to an image.
     * </p>
     *
     * <p>
     * Modifies the original image to allow for the operation to
     * be undone.
     * </p>
     *
     * @param input The image to apply the transformation to.
     * @return The resized image.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        int originalWidth = input.getWidth();
        int originalHeight = input.getHeight();
        int newWidth = (int) (originalWidth * scaleFactor);
        int newHeight = (int) (originalHeight * scaleFactor);
        
        // Create an AffineTransform for the scaling operation
        AffineTransform transform = new AffineTransform();
        transform.scale(scaleFactor, scaleFactor);
        AffineTransformOp scaleOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
        
        // Create a new BufferedImage with the new dimensions
        BufferedImage output = new BufferedImage(newWidth, newHeight, input.getType());
        
        // Apply the transformation
        scaleOp.filter(input, output);
        
        return output;
    }
}