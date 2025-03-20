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
public class CycleColorChannels implements ImageOperation, java.io.Serializable {
    
    private int cycleType;
    
    /**
     * <p>
     * Create a new CycleColorChannels operation
     * </p>
     * 
     * @param cycleType The type of color channel cycle to be performed
     */
    public CycleColorChannels(int cycleType){
    
        this.cycleType = cycleType;
    
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
                
                //Initializing with default values stops netbeans error messages
                int newR = r;
                int newG = g;
                int newB = b;
                
                /**
                 * switch allows for me to go through all the options
                 * without plenty of if-else statements
                 */
                switch(cycleType){
                
                    case 1: //RBG
                        newR = r;
                        newG = b;
                        newB = g;
                        break;
                    case 2: //GRB
                        newR = g;
                        newG = r;
                        newB = b;
                        break;
                    case 3: //GBR
                        newR = g;
                        newG = b;
                        newB = r;
                        break;
                    case 4: //BRG
                        newR = b;
                        newG = r;
                        newB = g;
                        break;
                    case 5: //BGR
                        newR = b;
                        newG = g;
                        newB = r;
                        break;
                    
                }
                
                argb = (a << 24) | (newR << 16) | (newG << 8) | newB;
                input.setRGB(x, y, argb);
                
            }
            
        }
    return input;
    }
}
