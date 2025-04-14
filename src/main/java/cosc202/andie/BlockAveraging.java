package cosc202.andie;

import java.awt.image.*;

/**
 *
 * @author Bradyn Salmon
 */
public class BlockAveraging implements ImageOperation, java.io.Serializable {

    private int xBA;
    private int yBA;

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
     * This method iterates through each block, the size of which is controlled
     * by the user, and takes their RGB values and averages them to give a block
     * pattern look to the image.
     * </p>
     *
     * @param input The image to be block averaged
     * @return The image with block averaging applied
     */
    @Override
    public BufferedImage apply(BufferedImage input) {

        for (int y = 0; y < input.getHeight(); y += yBA) {

            for (int x = 0; x < input.getWidth(); x += xBA) {

                long totalR = 0;
                long totalG = 0;
                long totalB = 0;
                int pixelCount = 0;

                for (int blockY = 0; blockY < yBA; blockY++) {

                    for (int blockX = 0; blockX < xBA; blockX++) {

                        int currentX = x + blockX;
                        int currentY = y + blockY;

                        if (currentX < input.getWidth() && currentY < input.getHeight()) {

                            int argb = input.getRGB(currentX, currentY);
                            /* >> sets shifted-in bits to match the sign (high order) bit
                             * >>> sets shifted-in bits to zero always
                             */
                            int a = (argb & 0xFF000000) >>> 24;
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);

                            totalR += r;
                            totalG += g;
                            totalB += b;
                            pixelCount++;
                        }

                    }

                }
                int avgR = (int) (totalR / pixelCount);
                int avgG = (int) (totalG / pixelCount);
                int avgB = (int) (totalB / pixelCount);

                for (int blockY = 0; blockY < yBA; blockY++) {

                    for (int blockX = 0; blockX < xBA; blockX++) {

                        int currentX = x + blockX;
                        int currentY = y + blockY;

                        if (currentX < input.getWidth() && currentY < input.getHeight()) {

                            int argb = input.getRGB(currentX, currentY);
                            /* >> sets shifted-in bits to match the sign (high order) bit
                             * >>> sets shifted-in bits to zero always
                             */
                            int a = (argb & 0xFF000000) >>> 24;
                            argb = (a << 24) | (avgR << 16) | (avgG << 8) | avgB;
                            input.setRGB(currentX, currentY, argb);

                        }

                    }
                }
            }

        }
        return input;
    }
}
