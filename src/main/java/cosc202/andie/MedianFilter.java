/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Mean (simple blur) filter.
 * </p>
 *
 * <p>
 * A Median filter blurs an image by replacing each pixel with the median of the
 * pixels in a surrounding neighborhood.
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
public class MedianFilter implements ImageOperation, java.io.Serializable {
    
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
     * The size of the filter is the 'radius' of the kernel that will be used
     * to search for the median pixel value
     * </p>
     *
     * @param radius The radius of the newly constructed MeanFilter
     */
    MedianFilter(int radius) {
        
        this.radius = radius;
    }
    
    /**
     * <p>
     * Apply a Median filter to an image.
     * </p>
     *
     * <p>
     * The Median filter is implemented by scanning the pixel values in a surrounding
     * neighborhood, and assigning them to be of the median pixel value. The
     * size of the kernel is specified by the {@link radius}. Larger
     * radii lead to stronger blurring.
     * </p>
     *
     * @param input The image to apply the Mean filter to.
     * @return The resulting (blurred)) image.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        
        int size = (2 * radius + 1);
        float[][] array = new float[size][size];
        
        for(int y = 1; y < input.getHeight() - 1; y += size) {
            
            for (int x = 1; x < input.getWidth() - 1; x += size) {

                int argb = input.getRGB(x, y);
                
                for (int a = y - 1; a < y + 2; a++) {
                    
                    for (int b = x - 1; b < x + 2; b++) {
                        
                        array[a - (y - 1)][b - (x - 1)] = input.getRGB(a,b);
                        
                    }
                    
                }
                
                
            }
            
        }
        
        return input;
        
    }
    
    public float getMedian(float[][] arr) {
        
        
        
    }
} 
