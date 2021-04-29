import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Util {

    /**
     * @param myPicture Image that needs to be converted
     * @param colors List of available colors to convert to
     * @param finalWidth Width of the final image. Used for resizing.
     * @param finalHeight Height of the final image. Used for resizing.
     * @return BufferedImage with limited palette
     */
    static BufferedImage ConvertToColorPalette(BufferedImage myPicture, ArrayList<ColorSlot> colors, int finalWidth, int finalHeight) throws IOException {
        myPicture = resizeImage(myPicture, finalWidth, finalHeight);
        BufferedImage newPicture = new BufferedImage(finalWidth,finalHeight,BufferedImage.TYPE_INT_RGB);

        int width = myPicture.getWidth();
        int height = myPicture.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int currentPixel = myPicture.getRGB(i, j);
                int currentR = (currentPixel >> 16) & 0xff;
                int currentG = (currentPixel >> 8) & 0xff;
                int currentB = currentPixel & 0xff;

                Color currentColor = new Color(currentR, currentG, currentB);
                Color newColor = Util.getClosestColorRGB(currentColor,colors);

                int newP = (newColor.getRed() << 16) | (newColor.getGreen() << 8) | newColor.getBlue();
                newPicture.setRGB(i, j, newP);

            }
        }
        return newPicture;
    }

    static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    /**
     * Gets the closest color from a list of available colors.
     * Uses modified RGB Euclidean Distance function.
     * Works better with a large palette with both saturated and unsaturated colors.
     */
    public static Color getClosestColorRGB(Color c, ArrayList<ColorSlot> colors) {
        Color minColor = new Color(255, 255, 255);
        double minDist = 50000;
        for (ColorSlot i : colors) {
            double dist = colorDistanceRGB(c, i.c);
            if (dist < minDist) {
                minDist = dist;
                minColor = new Color(i.c.getRed(), i.c.getGreen(), i.c.getBlue());
            }
        }
        return minColor;
    }

    /**
     * Gets the closest color from a list of available colors.
     * Only uses the Hue component
     * Works better with more saturated colors, like the Skribbl.io palette
     */
    private static Color getClosestColorHueOnly(Color c, ArrayList<ColorSlot> colors) {
        return new Color(0,0,0);
    }

    static double colorDistanceRGB(Color c1, Color c2) {
        int red1 = c1.getRed();
        int red2 = c2.getRed();
        int r_mean = (red1 + red2) >> 1;
        int r = red1 - red2;
        int g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        return Math.sqrt((((512 + r_mean) * r * r) >> 8) + 4 * g * g + (((767 - r_mean) * b * b) >> 8));
    }

    static void ColorsFirstParse(Robot robot, BufferedImage myPicture, ArrayList<ColorSlot> colors, int pixelInterval, int width, int height, int offsetX, int offsetY, long timeLimit) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        for (ColorSlot colorSlot : colors) {
            //skip white
            if (colorSlot.c.equals(new Color(255, 255, 255))) continue;

            //select the color
            robot.mouseMove(colorSlot.location.x, colorSlot.location.y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            //for every available color, draw all the pixels with that color
            for (int i = 0; i < width; i += pixelInterval) {
                for (int j = 0; j < height; j += pixelInterval) {
                    int currentPixel = myPicture.getRGB(i, j);
                    int currentR = (currentPixel >> 16) & 0xff;
                    int currentG = (currentPixel >> 8) & 0xff;
                    int currentB = currentPixel & 0xff;
                    Color currentColor = new Color(currentR, currentG, currentB);
                    if (currentColor.equals(colorSlot.c)) {
                        robot.mouseMove(i + offsetX, j + offsetY);
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        Thread.sleep(5);
                    }

                    if (System.currentTimeMillis() - startTime > timeLimit) return;
                }
            }
        }
    }

    static void PixelsFirstParse(Robot robot, BufferedImage myPicture, ArrayList<ColorSlot> colors, int pixelInterval, int width, int height, int offsetX, int offsetY, long timeLimit) throws InterruptedException {

        long startTime = System.currentTimeMillis();

        Color prevColor = new Color(0, 0, 0);
        for (int i = 0; i < width; i += pixelInterval) {
            for (int j = 0; j < height; j += pixelInterval) {

                //get the current pixel
                int currentPixel = myPicture.getRGB(i, j);
                int currentR = (currentPixel >> 16) & 0xff;
                int currentG = (currentPixel >> 8) & 0xff;
                int currentB = currentPixel & 0xff;
                Color currentColor = new Color(currentR, currentG, currentB);
                if (currentColor.equals(new Color(255, 255, 255))) {
                    prevColor = currentColor;
                    continue;
                }

                if (!currentColor.equals(prevColor)) {
                    Point finalLocation = new Point(0, 0);
                    //look up the current location on the screen of the color
                    for (ColorSlot k : colors) {
                        if (k.c.equals(currentColor)) {
                            //we found the corresponding color so remember its position
                            finalLocation = k.location;
                            break;
                        }
                    }

                    //move to that location then click it to select the color
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseMove(finalLocation.x, finalLocation.y);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(10);
                }

                //go to the pixel to draw it
                robot.mouseMove(i + offsetX, j + offsetY);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);


                if (System.currentTimeMillis() - startTime > timeLimit) return;
                prevColor = currentColor;
            }
        }
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}