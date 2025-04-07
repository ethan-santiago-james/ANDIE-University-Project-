package cosc202.andie;
import java.awt.image.*;
/**
 * ImageOperation to cycle color channels in image.
 * Takes default color configuration RGB and splits into
 * 5 different options, including:
 * RBG, GRB, GBR, BRG, BGR
 * One of these options will be selected by the user
 * 
 * @author Bradyn Salmon
 */
public class BrightnessContrast implements ImageOperation, java.io.Serializable {
    
    private int brightness;
    private int contrast;
    
    /**
     * <p>
     * Create a new CycleColorChannels operation
     * </p>
     * 
     * @param brightness chosen by user to be applied to image
     */
    public BrightnessContrast(int brightness, int contrast){
    
        this.brightness = brightness;
        this.contrast = contrast;
    
    }
    
    /**
     * <p>
     * Apply color channel cycling to image
     * </p>
     * 
     * @param input The image to be cycled
     * @return The image with cycled color channels
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
                
                double newR = r;
                double newG = g;
                double newB = b;
                
                newR = 1;
                
                argb = (a << 24) | ((int)newR << 16) | ((int)newG << 8) | (int)newB;
                input.setRGB(x, y, argb);
                
            }
            
        }
    return input;
    }
}
