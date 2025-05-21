package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply an edge detection filter.
 * </p>
 *
 * <p>
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
public class EdgeDetection implements ImageOperation, java.io.Serializable {

    String flavour;

    /**
     * <p>
     * Default constructor for EdgeDetection
     * </p
     *
     */
    @SuppressWarnings("unused")
    EdgeDetection() {
        this.flavour = "";
    }

    EdgeDetection(String flavour){
        this.flavour = flavour;
    }

    /**
     * <p>
     * Apply a Edge detectio nfilter to an image.
     * </p>
     *
     * <p>
     * As with many filters, the Edge detection filter is implemented via convolution. 
     * This filter uses a predefined convolution kernel so there is no way to adjust how sharp the blur should be.
     * </p>
     *
     * @return The resulting (blurred)) image.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        
         BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
         
        float[] horizontalSobelArray = {-0.5f,  0f, 0.5f,
                                  -1f,    0f, 1f,
                                  -0.5f,  0f, 0.5f};
                                  
        float[] verticalSobelArray = {-0.5f,  -1f, -0.5f,
                                  0f,    0f, 0f,
                                  0.5f,  1f, 0.5f};

        float[] emboss1 = { 0, 0, 0,
                            1, 0, -1,
                            0, 0, 0};
        
        float[] emboss2 = { 1, 0, 0,
                            0, 0, 0,
                            0, 0, -1};
        
        float[] emboss3 = { 0, 1, 0,
                            0, 0, 0,
                            0, -1, 0};
        
        float[] emboss4 = { 0, 0, 1,
                            0, 0, 0,
                            -1, 0, 0};
        
        float[] emboss5 = { 0, 0, 0,
                            -1, 0, 1,
                            0, 0, 0};
        
        float[] emboss6 = { -1, 0, 0,
                            0, 0, 0,
                            0, 0, 1};
        
        float[] emboss7 = { 0, -1, 0,
                            0, 0, 0,
                            0, 1, 0};
        
        float[] emboss8 = { 0, 0, -1,
                            0, 0, 0,
                            1, 0, 0};

                            
        float[] convolveArray = horizontalSobelArray;

        switch(this.flavour){
            case "hSoblel":  //HorizontalSobel
                convolveArray = horizontalSobelArray;
                break;

            case "vSoblel":  //VerticalSobel
                convolveArray = verticalSobelArray;
                break;


            //the 8 emboss filters are referred to as emboss1 through 8, 
            //starting with a vertical one with the positive "pointing" up and moving clockwise

            case "emboss1":
                convolveArray = emboss1;
                break;

            case "emboss2":
                convolveArray = emboss2;
                break;

            case "emboss3":
                convolveArray = emboss3;
                break;

            case "emboss4":
                convolveArray = emboss4;
                break;

            case "emboss5":
                convolveArray = emboss5;
                break;

            case "emboss6":
                convolveArray = emboss6;
                break;

            case "emboss7":
                convolveArray = emboss7;
                break;

            case "emboss8":
                convolveArray = emboss8;
                break;

            default:

                Kernel kernelOne = new Kernel(3, 3, horizontalSobelArray);
                Kernel kernelTwo = new Kernel(3, 3,verticalSobelArray);
                
                ConvolveOperation convOp = new ConvolveOperation(kernelOne);
                convOp.filterCombined(kernelTwo, input, output);

                return output;
        }
        
        Kernel kernel = new Kernel(3, 3, convolveArray);
        ConvolveOperation convOp = new ConvolveOperation(kernel);
        convOp.filter(input, output);   

        
         return output;
    }
    
}
