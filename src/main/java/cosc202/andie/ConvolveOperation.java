package cosc202.andie;

import java.awt.image.*;
import java.util.Arrays;

/* 
 * kernel reimplementation to account for dissapearing edge pixels, most functionality not carried over 
*/
public class ConvolveOperation {
    Kernel kernel;

    ConvolveOperation(Kernel kernel) {
        this.kernel = kernel;
    }
    boolean pos = true;

    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        
        try {
            if (src == dst) {
                throw new IllegalArgumentException("Illegal argument: src cannot be the same as dst");
            }
            int[] size = { src.getWidth(), src.getHeight() };
            int[] startPos = { src.getMinTileX(), src.getMinTileY() };

            int[] kSize = { kernel.getWidth(), kernel.getHeight() };
            float kStrength = 0;
            float[] temp = new float[kSize[0] * kSize[1]];
            for (float x : kernel.getKernelData(temp)) {
                kStrength += x;
                if(kStrength < 0){
                    this.pos = false;
                }
            }
            int count = 0;

            System.out.println(kStrength);
            System.out.println(Arrays.toString(kernel.getKernelData(temp)));
            System.out.println(src.getColorModel());

            int currentR;
            int currentG;
            int currentB;
            int currentColour;
            System.out.println();
            for (int x = startPos[0]; x < size[0]; x++) {
                System.out.println(x);
                for (int y = startPos[1]; y < size[1]; y++) {
                    currentR = 0;
                    currentG = 0;
                    currentB = 0;
                    count = 0;
                    for (int i = -(kSize[0]-1) / 2; i <= (kSize[0]-1)/2; i++) {
                        for (int j = -(kSize[1]-1) / 2; j <= (kSize[1]-1)/2; j++) {
                            currentColour = src.getRGB(squish(x + i,0,size[0]-1), squish(y + j,0,size[1]-1));
                            currentR += temp[count]*((currentColour & 0x00FF0000) >> 16);
                            currentG += temp[count]*((currentColour & 0x0000FF00) >> 8);
                            currentB += temp[count]*((currentColour & 0x000000FF));
                            count++;
                            //System.out.println(x+"  "+y+"   "+squish(x + i,0,size[0]-1)+"    "+ squish(y + j,0,size[1]-1)+"    "+currentColour+"  "+currentR+"    "+currentG+"    "+currentB);
                            //System.out.println(x);
                        }
                        //System.out.println("iterating I");
                    }
                    //System.out.println(x+","+y);
                    dst.setRGB(x, y, ((255 << 24) | (currentR << 16) | (currentG << 8) | currentB));
                }
                // x++;
                //System.out.println("finished x");
            }
            System.out.println("Applied filter");

        } catch (IllegalArgumentException d) {
            System.out.println(d+"Illegal Argument Exception ");
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Array out of bounds: "+e);
        }
        System.out.println("Yabadabadoo");
        return src;
    }


    private int squish(int val, int lower, int upper){ 
        if(val>upper){
            return upper;
        }else if(val<lower){
            return lower;
        }
        return val;
    }
}

// ColorModel: #pixelBits = 24 numComponents = 3 color space =
// java.awt.color.ICC_ColorSpace@3bb88d66 transparency = 1 has alpha = false
// isAlphaPre = false
