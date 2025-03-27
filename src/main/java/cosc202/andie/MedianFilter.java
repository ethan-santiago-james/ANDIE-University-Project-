/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Median (simple blur) filter.
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
 * @author Ethan James
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
     * Construct a Median filter with the given size.
     * </p>
     *
     * <p>
     * The size of the filter is the 'radius' of the kernel that will be used
     * to search for the median pixel value
     * </p>
     *
     * @param radius The radius of the newly constructed MedianFilter
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
     * @param input The image to apply the Median filter to.
     * @return The resulting (blurred)) image.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        
        int size = (2 * radius + 1);
        ArrayList<Integer> neighborsA = new ArrayList<Integer>();
        ArrayList<Integer> neighborsR = new ArrayList<Integer>();
        ArrayList<Integer> neighborsG = new ArrayList<Integer>();
        ArrayList<Integer> neighborsB = new ArrayList<Integer>();
        
        BufferedImage output = new BufferedImage(input.getColorModel(),input.copyData(null),input.isAlphaPremultiplied(),null);
        
            for (int y = radius; y < input.getHeight() - radius; y++) { // LOOPS THROUGH ALL IMAGE Y PIXELS

                for (int x = radius; x < input.getWidth() - radius; x++) { // LOOPS THROUGH ALL IMAGE X PIXELS

                    /* LOOPS THROUGH THE NEIGHBORS */
                    for (int a = y - radius; a < (y + radius + 1); a++) {

                        for (int b = x - radius; b < (x + radius + 1); b++) {

                            int rgbA = input.getRGB(b,a);
   
                            int A = (rgbA & 0xFF000000) >>> 24;
                            int R = (rgbA & 0x00FF0000) >> 16;
                            int G = (rgbA & 0x0000FF00) >> 8;
                            int B = (rgbA & 0x000000FF);

                            neighborsA.add(A);
                            neighborsR.add(R);
                            neighborsG.add(G);
                            neighborsB.add(B);
                        }

                    }

                    int medianA = getMedian(neighborsA);
                    int medianR = getMedian(neighborsR);
                    int medianG = getMedian(neighborsG);
                    int medianB = getMedian(neighborsB);

                    int medianARGB = (medianA << 24) | (medianR << 16) | (medianG << 8) | medianB;
                    //System.out.print(" Old ARGB Value: " + output.getRGB(x,y));
                    output.setRGB(x,y,medianARGB);
                    //System.out.print(", New ARGB Value: " + output.getRGB(x,y) + "\n");
                    neighborsA.clear();
                    neighborsB.clear();
                    neighborsR.clear();
                    neighborsG.clear();

                }

            }

        return output;
        
    }
    
    /**
     * <p>
     * Return the median value of a given ArrayList of integers
     * </p>
     *
     * <p>
     * This method sorts the list, and then returns the middle index
     * </p>
     *
     * @param neighbors The list to retrieve the median value of.
     * @return The value that corresponds to the median index of the list.
     */
    public int getMedian(ArrayList<Integer> neighbors) {
        
        Collections.sort(neighbors);
        
        int medianIndex = (int)(Math.floor(neighbors.size()/2));
        return neighbors.get(medianIndex);
        
    }
} 
