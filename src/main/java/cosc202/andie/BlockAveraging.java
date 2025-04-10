package cosc202.andie;

import java.awt.image.*;

/**
 *
 * @author Bradyn Salmon
 */
public class BlockAveraging implements ImageOperation, java.io.Serializable {

    private int xBA;
    private int yBA;
    
    public static void blockAverage(int x, int y, BufferedImage input, int[] blockArr){
    
        for blockArr{
            
            int a = (argb & 0xFF000000) >>> 24;
            int r = (argb & 0x00FF0000) >> 16;
            int g = (argb & 0x0000FF00) >> 8;
            int b = (argb & 0x000000FF);
            blockArr[i];
            rtotal++;
            gtotal++;
            btotal++;
            
        
        }
        rAve = rtotal/blockArr.length;
        gAve = gtotal/blockArr.length;
        bAve = btotal/blockArr.length;
        
        for yBA{
        
            for xBA{
            
                XiYiR = rAve;
                XiYiG = gAve;
                XiYiB = bAve
            
            }
        
        }
    
    }

    public BlockAveraging(int x, int y) {

        this.xBA = x;
        this.yBA = y;

    }

    /**
     * <p>
     * Apply the block averaging to image.
     * </p>
     *
     * <p>
     * This method iterates through each pixel of the input image, applies the
     * brightness and contrast adjustments to the rgb color channels, and
     * returns the adjusted image.
     * </p>
     *
     * @param input The image to be blovk averaged
     * @return The image with block averaging applied
     */
    @Override
    public BufferedImage apply(BufferedImage input) {

        for (int y = 1; y < input.getHeight(); y = y + yBA) {

            for (int x = 1; x < input.getWidth(); x = x + xBA) {
                
                int [] blockArr = new int[xBA*yBA];
                for (int i = 0; i < yBA; i++) {

                    for (int j = 0; j < xBA; j++) {

                        int argb = input.getRGB(x + j, y + i);
                        /* >> sets shifted-in bits to match the sign (high order) bit
                        * >>> sets shifted-in bits to zero always
                         */
                        int a = (argb & 0xFF000000) >>> 24;
                        int r = (argb & 0x00FF0000) >> 16;
                        int g = (argb & 0x0000FF00) >> 8;
                        int b = (argb & 0x000000FF);

                        argb = (a << 24) | ((int) newR << 16) | ((int) newG << 8) | (int) newB;
                        input.setRGB(x, y, argb);

                    }

                }

            }

        }
        return input;
    }
}
