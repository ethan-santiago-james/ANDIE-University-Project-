package cosc202.andie;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * A class that handles image rotations and flips using AffineTransform.
 *
 * @author linad885
 */
public class RotateImage implements ImageOperation, java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private boolean isFlippedHorizontally;
    private boolean isFlippedVertically;
    private int rotationState; // 0=0, 1=90, 2=180, 3=270

    /**
     * Create a new RotateImage for horizontal or vertical flip.
     *
     * @param isFlipping will always be true, tells BufferedImage to do a rotate
     * @param isVertical true for vertical flip, false for horizontal flip
     */
    public RotateImage(boolean isFlipping, boolean isVertical) {
        if (isVertical) {
            this.isFlippedVertically = isFlipping;
        } else {
            this.isFlippedHorizontally = isFlipping;
        }
    }

    /**
     * Create a new RotateImage for rotation.
     *
     * @param rotationState The rotation state (0-3)
     */
    public RotateImage(int rotationState) {
        this.rotationState = rotationState;
    }

    /**
     * Apply the transformation to the input image using AffineTransform.
     *
     * @param input The image to transform
     * @return The transformed image
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();

        // Create a new transformation
        AffineTransform at = new AffineTransform();

        // Determine dimensions of result image based on rotation
        BufferedImage result = new BufferedImage(width, height, input.getType());
        if (rotationState == 1 || rotationState == 3) {
            // For 90° or 270° rotations, we need to swap width and height
            result = new BufferedImage(height, width, input.getType());
        }

        // Apply rotation
        switch (rotationState) {
            case 0: // No rotation
                break;
            case 1: // 90° clockwise
                at.translate(height, 0);
                at.rotate(Math.PI / 2);
                break;
            case 2: // 180°
                at.translate(width, height);
                at.rotate(Math.PI);
                break;
            case 3: // 270° clockwise (90° anticlockwise)
                at.translate(0, width);
                at.rotate(-Math.PI / 2);
                break;
        }

        // Apply flips if needed
        if (isFlippedHorizontally) {
            if (rotationState == 0 || rotationState == 2) {
                // In original orientation or 180 degrees
                at.concatenate(AffineTransform.getTranslateInstance(-width, 0));
            } else {
                // In 90 or 270 degrees (width and height are swapped)
                at.concatenate(AffineTransform.getTranslateInstance(-height, 0));
            }
        }

        if (isFlippedVertically) {
            if (rotationState == 0 || rotationState == 2) {
                // In original orientation or 180 degrees
                at.concatenate(AffineTransform.getTranslateInstance(0, -height));
            } else {
                // In 90 or 270 degrees (width and height are swapped)
                at.concatenate(AffineTransform.getTranslateInstance(0, -width));
            }
        }

        // Create and apply the transformation with better interpolation
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        op.filter(input, result);
        return result;
    }
}
