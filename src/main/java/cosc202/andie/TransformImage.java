package cosc202.andie;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
/**
 *
 * @author linad885
 */
public class TransformImage implements ImageOperation, java.io.Serializable {
    private boolean isFlippedHorizontally;
    private boolean isFlippedVertically;
    private int rotationState; // 1 = rotate clockwise, 2 = rotate anti
    /**
     * Create a new TransformImage for horizontal or vertical flip.
     *
     * @param isFlipping will always be true, tells BufferedImage to do a flip
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
     * @param rotationState The rotation value, being 1 for clockwise or 2 for antiCW, or 0 for no rotate
     */
    public TransformImage(int rotationState) {
        this.rotationState = rotationState;
    }
    /**
     * Apply the transformation to the input image using AffineTransform.
     * Transforms are applied in-place.
     *
     * @param input The image to transform
     * @return The transformed image
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        
        // For rotation
        if (rotationState == 1 || rotationState == 2) {
            BufferedImage result = new BufferedImage(height, width, input.getType());
            AffineTransform at = new AffineTransform();
            
            if (rotationState == 1) { // 90 degrees clockwise
                at.translate(height, 0);  // Move to the right position
                at.rotate(Math.PI / 2);   // Rotate 90 degrees
            } else { // 90 degrees counter-clockwise
                at.translate(0, width);   // Move to the right position
                at.rotate(-Math.PI / 2);  // Rotate -90 degrees
            }
            
            //apply
            AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
            op.filter(input, result);
            return result; // Rotations change dimensions, so return the new image
        } else {
            // For flips
            AffineTransform at = new AffineTransform();
            if (isFlippedHorizontally) {
                at.scale(-1, 1); //scales it horizontally by -1, performing flip
                at.translate(-width, 0); //relocates it to where the image should be
            }
            if (isFlippedVertically) {
                at.scale(1, -1); //scales it vertically by -1, performing flip
                at.translate(0, -height); //relocates it to where the image should be
            }
            
            // Create a temporary copy of the input to apply transformation
            BufferedImage temp = new BufferedImage(width, height, input.getType());
            temp.createGraphics().drawImage(input, 0, 0, null);
            
            // Apply transformation directly to input image
            AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
            op.filter(temp, input);
        }
        
        // Reset states
        rotationState = 0;
        isFlippedVertically = false;
        isFlippedHorizontally = false;
        return input;
    }
}