package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Mean (simple blur) filter.
 * </p>
 *
 * <p>
 * A Mean filter blurs an image by replacing each pixel by the average of the
 * pixels in a surrounding neighbourhood, and can be implemented by a
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
public class MeanFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2
     * a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Mean filter with the given size.
     * </p>
     *
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used. A
     * size of 1 is a 3x3 filter, 2 is 5x5, and so on. Larger filters give a
     * stronger blurring effect.
     * </p>
     *
     * @param radius The radius of the newly constructed MeanFilter
     */
    MeanFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Mean filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Mean filter has radius 1.
     * </p>
     *
     * @see MeanFilter(int)
     */
    @SuppressWarnings("unused")
    MeanFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Mean filter to an image.
     * </p>
     *
     * <p>
     * As with many filters, the Mean filter is implemented via convolution. The
     * size of the convolution kernel is specified by the {@link radius}. Larger
     * radii lead to stronger blurring.
     * </p>
     *
     * @param input The image to apply the Mean filter to.
     * @return The resulting (blurred)) image.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        ArrayList<Integer> rgbas = new ArrayList<Integer>();
        int radius = 1; // Or whatever value you choose

        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {

                int sumA = 0, sumR = 0, sumG = 0, sumB = 0;
                int count = 0;

                for (int a = y - radius; a <= y + radius; a++) {
                    for (int b = x - radius; b <= x + radius; b++) {
                        int actualA = Math.max(0, Math.min(a, input.getHeight() - 1));
                        int actualB = Math.max(0, Math.min(b, input.getWidth() - 1));

                        int argb = input.getRGB(actualB, actualA);

                        int alpha = (argb >> 24) & 0xff;
                        int red   = (argb >> 16) & 0xff;
                        int green = (argb >> 8) & 0xff;
                        int blue  = argb & 0xff;

                        sumA += alpha;
                        sumR += red;
                        sumG += green;
                        sumB += blue;
                        count++;
                    }
                }

                // Compute mean
                int avgA = sumA / count;
                int avgR = sumR / count;
                int avgG = sumG / count;
                int avgB = sumB / count;

                int newARGB = (avgA << 24) | (avgR << 16) | (avgG << 8) | avgB;
                output.setRGB(x, y, newARGB);
            }
        }

        return output;
    }

}
