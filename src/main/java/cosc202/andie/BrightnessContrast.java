package cosc202.andie;
import java.awt.image.*;
/**
 * <p>
 * ImageOperation to adjust the brightness and contrast of an image.
 * </p>
 *
 * <p>
 * This class allows adjusting the brightness and contrast levels of the input image
 * based on percentage values provided by the user. The adjustments are applied
 * independently to the Red, Green, and Blue channels of each pixel.
 * </p>
 *
 * @author Bradyn Salmon
 */
public class BrightnessContrast implements ImageOperation, java.io.Serializable {
    
    private int brightness;
    private int contrast;
    
    /**
     * <p>
     * Helper method to apply brightness and contrast adjustment to a single color channel value.
     * </p>
     * 
     * Uses formula in lab book to adjust brightness and contrast of input image.
     * 
     * @param col The original color value (0-255).
     * @param c The contrast percentage.
     * @param b The brightness percentage.
     * @return The adjusted color value (0-255).
     */
    private static double apply(int col, int c, int b){
    
        double v = (1+c/100.0)*(col-127.5) + 127.5*(1+b/100.0);
        if(v>255) v = 255;
        if(v<0) v = 0;
        return v;
    
    }
    
    /**
     * <p>
     * Create a new BrightnessContrast operation.
     * </p>
     *
     * @param brightness The percentage adjustment for brightness (e.g., -100 to 100).
     * @param contrast The percentage adjustment for contrast (e.g., -100 to 100).
     */
    public BrightnessContrast(int brightness, int contrast){
    
        this.brightness = brightness;
        this.contrast = contrast;
    
    }
    
    /**
     * <p>
     * Apply the brightness and contrast adjustment to an image.
     * </p>
     * 
     * <p>
     * This method iterates through each pixel of the input image, applies the
     * brightness and contrast adjustments to the rgb color channels, and
     * returns the adjusted image.
     * </p>
     * 
     * @param input The image to be adjust
     * @return The image with contrast and brightness adjusted
     */
    @Override
    public BufferedImage apply(BufferedImage input){
    
        for (int y = 0; y < input.getHeight(); ++y){
            
            for(int x = 0; x < input.getWidth(); ++x){
                
               int argb = input.getRGB(x, y);
                /* >> sets shifted-in bits to match the sign (high order) bit
                * >>> sets shifted-in bits to zero always
                 */
                int a = (argb & 0xFF000000) >>> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);
                
                double newR = apply(r, contrast, brightness);
                double newG = apply(g, contrast, brightness);
                double newB = apply(b, contrast, brightness);
                
                argb = (a << 24) | ((int)newR << 16) | ((int)newG << 8) | (int)newB;
                input.setRGB(x, y, argb);
                
            }
            
        }
    return input;
    }
}
