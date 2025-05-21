package cosc202.andie;

import java.awt.image.*;
import java.util.Arrays;

/**
 * kernel reimplementation to account for dissapearing edge pixels, most functionality not carried over 
*/
public class ConvolveOperation {
    Kernel kernel;

    /**
     * Constructor for ConvolveOperation
     * @param kernel 
     */
    ConvolveOperation(Kernel kernel) {
        this.kernel = kernel;
    }
    boolean pos = true;

    /**
     * Method used to filter the image based on the kernel with negative values shifted
     * @param input
     * @param output
     * @return 
     */
    public BufferedImage filter(BufferedImage input, BufferedImage output) {
        
        int width = input.getWidth();
        int height = input.getHeight();
        
       
        
        int kWidth = kernel.getWidth();
        int kHeight = kernel.getHeight();
        
        float[] kernelArray = kernel.getKernelData(null);
        int kRadius = (kWidth - 1)/2;
        
        for (int y = kRadius; y < input.getHeight() - kRadius; y++) {
           
           for (int x = kRadius; x < input.getWidth() - kRadius; x++) {
               
               int rgbA = input.getRGB(x,y);
               int A = (rgbA & 0xFF000000) >>> 24;
               int counter = 0;
                
               int R = 0;
               int G = 0;
               int B = 0;
               //System.out.println(j);
               for (int k = y - kRadius; k < y + kRadius + 1; k++) {
                   
                   //System.out.println(k);
                   for (int l = x - kRadius; l < x + kRadius + 1; l++) {
                       
                       //System.out.println(l);
                       int RGBA = input.getRGB(l,k);

                       int a = (RGBA & 0xFF000000) >>> 24;
                       int r = (RGBA & 0x00FF0000) >> 16;
                       int g = (RGBA & 0x0000FF00) >> 8;
                       int b = (RGBA & 0x000000FF);
                       
                       R += r*(int)(kernelArray[counter]);
                       G += g*(int)(kernelArray[counter]);
                       B += b*(int)(kernelArray[counter]);
                       counter++;
                       
                   }
                   
               }
               
               R = Math.min(R+128, 255);
               G = Math.min(G+128, 255);
               B = Math.min(B+128, 255);
               int ARGB = (A << 24) | (R << 16) | (G << 8) | B;
               output.setRGB(x,y, ARGB);
               counter = -1;
               
           }
            
        }
        
        return output;
        
        
        
    }
   
}

// ColorModel: #pixelBits = 24 numComponents = 3 color space =
// java.awt.color.ICC_ColorSpace@3bb88d66 transparency = 1 has alpha = false
// isAlphaPre = false
