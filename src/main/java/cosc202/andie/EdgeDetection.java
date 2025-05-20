package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a sharpening filter.
 * </p>
 *
 * <p>
 * A Sharpen filter sharpens an image by replacing each pixel by an average of
 * the
 * pixels in a surrounding neighbourhood that is weighted away from the
 * surrounding pixels, and can be implemented by a
 * convolution.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @see java.awt.image.ConvolveOp
 * @author Steven Mills
 * @version 1.0
 */
public class EdgeDetection implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Default constructor for EdgeDetection
     * </p
     *
     */
    @SuppressWarnings("unused")
    EdgeDetection() {
    }

    /**
     * <p>
     * Apply a Sharpen filter to an image.
     * </p>
     *
     * <p>
     * As with many filters, the Sharpen filter is implemented via convolution. 
     * This filter uses a predefined convolution kernel so there is no way to adjust how sharp the blur should be.
     * </p>
     *
     * @return The resulting (blurred)) image.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        float[] horizontalArray = {-0.5f,  0f, 0.5f,
                                  -1f,    0f, 1f,
                                  -0.5f,  0f, 0.5f};
        
        float[] verticalArray = {-0.5f, -1f,   -0.5f,
                                  0f,    0f,   0f,
                                  0.5f,  1f,    0.5f};
        

        Kernel kernel = new Kernel(3, 3, horizontalArray);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        kernel = new Kernel(3, 3, verticalArray);
        convOp = new ConvolveOp(kernel);
        output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }

}
