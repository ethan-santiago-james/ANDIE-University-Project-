package cosc202.andie;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * A class that handles image rotations and flips using AffineTransform.
 *
 * @author linad885
 */
public class TransformImage implements ImageOperation, java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private boolean isFlippedHorizontally;
    private boolean isFlippedVertically;
    private int rotationState; // 1 = rotate clockwise, 2 = rotate anti

    /**
     * Create a new RotateImage for horizontal or vertical flip.
     *
     * @param isFlipping will always be true, tells BufferedImage to do a rotate
     * @param isVertical true for vertical flip, false for horizontal flip
     */
    public TransformImage(boolean isFlipping, boolean isVertical) {
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
    public TransformImage(int rotationState) {
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
        BufferedImage result;

        // Handle rotation
        if (rotationState == 1) { // 90 degrees clockwise
            result = new BufferedImage(height, width, input.getType());
            AffineTransform at = new AffineTransform();
            at.translate(height, 0);  // Move to the right position
            at.rotate(Math.PI / 2);   // Rotate 90 degrees

            AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
            op.filter(input, result);
        } else if (rotationState == 2) { // 90 degrees counter-clockwise
            result = new BufferedImage(height, width, input.getType());
            AffineTransform at = new AffineTransform();
            at.translate(0, width);   // Move to the right position
            at.rotate(-Math.PI / 2);  // Rotate -90 degrees

            AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
            op.filter(input, result);
        } else {
            // For flips
            result = new BufferedImage(width, height, input.getType());
            AffineTransform at = new AffineTransform();

            if (isFlippedHorizontally) {
                at.scale(-1, 1);
                at.translate(-width, 0);
            }
            if (isFlippedVertically) {
                at.scale(1, -1);
                at.translate(0, -height);
            }

            AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
            op.filter(input, result);
        }

        // Reset states
        rotationState = 0;
        isFlippedVertically = false;
        isFlippedHorizontally = false;
        return result;
    }
}
