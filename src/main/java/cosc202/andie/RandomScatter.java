package cosc202.andie;
import java.awt.image.*;
import java.util.Random;
/**
 * ImageOperation to randomly scatter pixels in image.
 * Goes through each pixel and randomly scatters pixels
 * at a user-specified radius.
 * 
 * @author Bradyn Salmon
 */
public class RandomScatter implements ImageOperation, java.io.Serializable {

    private int radius;
    
    /**
     * <p>
     * Create a new RandomScatter operation.
     * </p>
     * 
     * @param radius The radius of which the random scatter will be applied to.
     */
    public RandomScatter(int radius){
    
        this.radius = radius;
    
    }
    
    /**
     * <p>
     * Apply random scattering to input image.
     * </p>
     *
     * <p>
     * Randomly scatters pixels by sampling from a user specified
     * radius around the pixel to be changed. This is achieved by
     * randomly generating x and y values within the pixel's neighbourhood
     * as determined by it's radius, which selects a random pixel, takes
     * the values from an unaltered version of the image to stop resampling,
     * and applies them to the target, does this for every pixel in image.
     * </p>
     * 
     * @param input The image to be random scattered
     * @return The image with random scatter effect applied
     */
    @Override
    public BufferedImage apply(BufferedImage input){
        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage original = new BufferedImage(width, height, input.getType());
        original.setData(input.getData()); //now have a copy of the image to sample from to avoid sampling an already sampled pixel
        Random rand = new Random();
        for (int y = 0; y < height; ++y){
            
            for(int x = 0; x < width; ++x){
                
                int xOffset = rand.nextInt(2*radius+1) - radius;
                int yOffset = rand.nextInt(2*radius+1) - radius;
                
                int xSource = x + xOffset;
                int ySource = y + yOffset;
                
                if(xSource < 0){
                    xSource = 0;
                }
                else if(xSource >= width){
                    xSource = width - 1;
                }
                
                if(ySource < 0){
                    ySource = 0;
                }
                else if(ySource >= height){
                    ySource = height - 1;
                }
                
                int argb = original.getRGB(xSource, ySource);
                input.setRGB(x, y, argb);
                
            }
            
        }
        return input;
    
    }
    
}
