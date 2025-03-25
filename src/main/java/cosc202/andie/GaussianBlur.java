package cosc202.andie;

import java.awt.image.*;
import java.util.Arrays;

/**
 * <p>
 * ImageOperation to apply a gaussian blur filter.
 * </p>
 *
 * <p>
 * A gaussian blur filter gaussian blur an image via a convolution that makes
 * each pixel less similar to its neighbors.
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
public class GaussianBlur implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2
     * a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a gaussian blur filter with the given size.
     * </p>
     *
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used. A
     * size of 1 is a 3x3 filter, 2 is 5x5, and so on. Larger filters give a
     * stronger blurring effect.
     * </p>
     *
     * @param radius The radius of the newly constructed GaussianBblur
     */
    GaussianBlur(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Gaussian blur filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Gaussian Blur filter has radius 1.
     * </p>
     *
     * @see GaussianBlur(int)
     */
    @SuppressWarnings("unused")
    GaussianBlur() {
        this(1);
    }

    /**
     * <p>
     * Apply a Gaussian Blur filter to an image.
     * </p>
     *
     * <p>
     * As with many filters, the Gaussian Blur filter is implemented via
     * convolution. The size of the convolution kernel is specified by the
     * {@link radius}. Larger radii lead to stronger blurring.
     * </p>
     *
     * @param input The image to apply the Gaussian Blur filter to.
     * @return The resulting (GaussianBlurred) image.
     */
    @Override
    public BufferedImage apply(BufferedImage input){
        int kernelSize = (int)Math.pow(2*radius+1,2);
        float[] kernelArr = new float[kernelSize];
        int count = 0;
        float sum = 0;
        for(int x = -radius; x<=radius;x++){
            for(int y = -radius; y<=radius;  y++){
                kernelArr[count] = gaussian(x,y,radius);
                sum+=kernelArr[count];
                count++;
            }
        }
        for(int i =0; i<kernelSize; i++){
            kernelArr[i] = kernelArr[i]/sum;
        }
        System.out.println(Arrays.toString(kernelArr));
        
        System.out.println(gaussian(0,0,1)/sum);
        
        Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, kernelArr);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }
    
    public float gaussian(int x, int y, float r) {
        double theta = r / 3.0f;
        double left = 1.0 / (2.0 * Math.PI * Math.pow(theta, 2));
        double right = Math.pow(Math.E, -(((Math.pow(x, 2) + Math.pow(y, 2))) / (2 * Math.pow(theta, 2))));
        return (float) (left * right);
    }


}
