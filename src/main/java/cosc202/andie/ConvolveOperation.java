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
        System.out.println(Arrays.toString(kernelArray));
        int kRadius = (kWidth - 1)/2;
        
        for (int y = 0; y < input.getHeight(); y++) {
           
           for (int x = 0; x < input.getWidth(); x++) {
               
               
               
               int rgbA = input.getRGB(x,y);
               int A = (rgbA & 0xFF000000) >>> 24;
               int counter = 0;
                
               float R = 0;
               float G = 0;
               float B = 0;
               //System.out.println(j);
               for (int k = y - kRadius; k < y + kRadius + 1; k++) {
                   
                   //System.out.println(k);
                   for (int l = x - kRadius; l < x + kRadius + 1; l++) {
                       
                       //System.out.println(l);
                       
                       int actualX = Math.max(l, 0);
                       actualX = Math.min(actualX, input.getWidth() - 1);

                       int actualY = Math.max(k, 0);
                       actualY = Math.min(actualY, input.getHeight() - 1);
                       int RGBA = input.getRGB(actualX,actualY);

                       int a = (RGBA & 0xFF000000) >>> 24;
                       int r = (RGBA & 0x00FF0000) >> 16;
                       int g = (RGBA & 0x0000FF00) >> 8;
                       int b = (RGBA & 0x000000FF);
                       
                       R += r*(kernelArray[counter]);
                       G += g*(kernelArray[counter]);
                       B += b*(kernelArray[counter]);
                       counter++;
                       
                   }
                   
               }

               int r = (int)Math.min(R+128, 255);
               int g = (int)Math.min(G+128, 255);
               int b = (int)Math.min(B+128, 255);
               int ARGB = (A << 24) | (r << 16) | (g << 8) | b;
               output.setRGB(x,y, ARGB);

               counter = 0;
               
           }
            
        }
        
        return output;
        
        
        
    }
    
    /**
     * Method used to apply a combined vertical, and horizontal edge detection feature
     * @param kerTwo
     * @param input
     * @param output
     * @return 
     */
    public BufferedImage filterCombined(Kernel kerTwo,BufferedImage input, BufferedImage output) {
        
        int width = input.getWidth();
        int height = input.getHeight();
        
        int kWidth = kernel.getWidth();
        int kHeight = kernel.getHeight();
        
        float[] kernelArray = kernel.getKernelData(null);
        float[] kernelArrayTwo = kerTwo.getKernelData(null);
        
        System.out.println(Arrays.toString(kernelArray));
        System.out.println(Arrays.toString(kernelArrayTwo));
        int kRadius = (kWidth - 1)/2;
        
        for (int y = 0; y < input.getHeight(); y++) {
           
           for (int x = 0; x < input.getWidth(); x++) {
               
               
               
               int rgbA = input.getRGB(x,y);
               int A = (rgbA & 0xFF000000) >>> 24;
               int counter = 0;
                
               float R = 0;
               float G = 0;
               float B = 0;
               
               float R2 = 0;
               float G2 = 0;
               float B2 = 0;
               //System.out.println(j);
               for (int k = y - kRadius; k < y + kRadius + 1; k++) {
                   
                   //System.out.println(k);
                   for (int l = x - kRadius; l < x + kRadius + 1; l++) {
                       
                       //System.out.println(l);
                       
                       int actualX = Math.max(l, 0);
                       actualX = Math.min(actualX, input.getWidth() - 1);

                       int actualY = Math.max(k, 0);
                       actualY = Math.min(actualY, input.getHeight() - 1);
                       int RGBA = input.getRGB(actualX,actualY);

                       int a = (RGBA & 0xFF000000) >>> 24;
                       int r = (RGBA & 0x00FF0000) >> 16;
                       int g = (RGBA & 0x0000FF00) >> 8;
                       int b = (RGBA & 0x000000FF);
                       
                       R += r*(kernelArray[counter]);
                       G += g*(kernelArray[counter]);
                       B += b*(kernelArray[counter]);
                       
                       R2 += r*(kernelArrayTwo[counter]);
                       G2 += g*(kernelArrayTwo[counter]);
                       B2 += b*(kernelArrayTwo[counter]);
                       counter++;
                       
                   }
                   
               }
               
               
               int newR = (int)Math.sqrt(Math.pow(R,2) + Math.pow(R2,2));
               int newG = (int)Math.sqrt(Math.pow(G,2) + Math.pow(G2,2));
               int newB = (int)Math.sqrt(Math.pow(B,2) + Math.pow(B2,2));
               
               newR = Math.min(newR+128, 255);
               newG = Math.min(newG+128, 255);
               newB = Math.min(newB+128, 255);
               
               int ARGB = (A << 24) | (newR << 16) | (newG << 8) | newB;
               output.setRGB(x,y, ARGB);
               counter = 0;
               
           }
            
        }
        
        return output;
        
        
        
    }
   
}

// ColorModel: #pixelBits = 24 numComponents = 3 color space =
// java.awt.color.ICC_ColorSpace@3bb88d66 transparency = 1 has alpha = false
// isAlphaPre = false
