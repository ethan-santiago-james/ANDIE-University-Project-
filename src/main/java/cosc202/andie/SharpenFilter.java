package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a sharpening filter.
 * </p>
 *
 * <p>
 * A Sharpen filter sharpens an image by replacing each pixel by an average of the
 * pixels in a surrounding neighbourhood that is weighted away from the surrounding pixels, and can be implemented by a
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
public class SharpenFilter implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Default constructor for SharpenFilter
     * </p
     *
     */
    @SuppressWarnings("unused")
    SharpenFilter() {
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
        float[] sharpenArray = {0f,-0.5f,0f,-0.5f,3f,-0.5f,0f,-0.5f,0f};
        

        Kernel kernel = new Kernel(3, 3, sharpenArray);
        ConvolveOp convOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }

}
