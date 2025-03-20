package cosc202.andie;

import java.awt.image.*;

/**
 * ImageOperation to invert color channels to give an inverted color-image.
 *
 * @author Bradyn Salmon
 */
public class InvertColors implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Create a new InvertColors operation.
     * </p>
     */
    InvertColors() {

    }

    /**
     * <p>
     * Apply inversion to an image.
     * </p>
     *
     * <p>
     * Subtracts RGB values from 255 to get complement, resulting in inverted
     * color image
     * </p>
     *
     * @param input The image to be inverted
     * @return The resulting inverted image.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                /* >> sets shifted-in bits to match the sign (high order) bit
                * >>> sets shifted-in bits to zero always
                 */
                int a = (argb & 0xFF000000) >>> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                argb = (a << 24) | (255 - r << 16) | (255 - g << 8) | 255 - b;
                input.setRGB(x, y, argb);
            }
        }

        return input;
    }

}
