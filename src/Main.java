import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static String imagePath = "C:\\Users\\Vlad\\Documents\\SkirbblBot\\images\\input.jpg";
    private static String outputPath = "C:\\Users\\Vlad\\Documents\\SkirbblBot\\images\\output.jpg";

    private static ArrayList<ColorSlot> colors;

    private static int finalWidth = 800;
    private static int finalHeight = 600;

    private static int pixelInterval = 5; //draw each 5 pixels
    private static int offsetX = 477;
    private static int offsetY = 222;

    private static long timeLimit = 100000; //100 second time limit

    public static void main(String[] args) throws IOException, InterruptedException, AWTException {

        colors = new ArrayList<>();
        addColors();
        Robot robot = new Robot();

        BufferedImage myPicture;
        myPicture = ImageIO.read(new File(imagePath));
        myPicture = Util.resizeImage(myPicture, finalWidth, finalHeight);

        BufferedImage newPicture = Util.ConvertToColorPalette(myPicture, colors, finalWidth, finalHeight);
        ImageIO.write(newPicture, "jpg", new File(outputPath));

        Thread.sleep(2000);

        Util.ColorsFirstParse(robot, newPicture, colors, pixelInterval, finalWidth, finalHeight, offsetX, offsetY, timeLimit);
        //Util.PixelsFirstParse(robot, newPicture, colors, pixelInterval, finalWidth, finalHeight, offsetX, offsetY, timeLimit);


    }

    private static void addColors() {
        colors.add(new ColorSlot(new Color(255, 255, 255), new Point(585, 862))); //WHITE
        colors.add(new ColorSlot(new Color(193, 193, 193), new Point(610, 862))); //LIGHT GRAY
        colors.add(new ColorSlot(new Color(76, 76, 76), new Point(610, 88))); //DARK GRAY
        colors.add(new ColorSlot(new Color(0, 0, 0), new Point(585, 888))); //BLACK
        colors.add(new ColorSlot(new Color(239, 19, 11), new Point(611, 862))); //BRIGHT RED
        colors.add(new ColorSlot(new Color(116, 11, 7), new Point(611, 888))); //DARK RED
        colors.add(new ColorSlot(new Color(194, 56, 0), new Point(660, 888))); //DARK ORANGE
        colors.add(new ColorSlot(new Color(255, 113, 0), new Point(660, 862))); //LIGHT ORANGE
        colors.add(new ColorSlot(new Color(255, 228, 0), new Point(685, 862))); //BRIGHT YELLOW
        colors.add(new ColorSlot(new Color(232, 162, 0), new Point(685, 888))); //DARK YELLOW
        colors.add(new ColorSlot(new Color(0, 204, 0), new Point(710, 862))); // LIGHT GREEN
        colors.add(new ColorSlot(new Color(0, 85, 16), new Point(710, 888))); //DARK GREEN
        colors.add(new ColorSlot(new Color(0, 178, 255), new Point(735, 862))); //LIGHT BLUE
        colors.add(new ColorSlot(new Color(0, 86, 158), new Point(735, 888))); //DARKER BLUE
        colors.add(new ColorSlot(new Color(35, 31, 211), new Point(760, 862))); //DARKEST BLUE
        colors.add(new ColorSlot(new Color(14, 8, 101), new Point(760, 888))); //DARKEST++ BLUE
        colors.add(new ColorSlot(new Color(163, 0, 186), new Point(785, 862))); //LIGHT PURPLE
        colors.add(new ColorSlot(new Color(85, 0, 105), new Point(785, 888))); //DARK PURPLE
        colors.add(new ColorSlot(new Color(211, 124, 170), new Point(810, 862))); //LIGHT PINK
        colors.add(new ColorSlot(new Color(167, 85, 116), new Point(810, 888))); //DARK PINK
        colors.add(new ColorSlot(new Color(160, 82, 45), new Point(835, 862))); //LIGHT BROWN
        colors.add(new ColorSlot(new Color(99, 48, 13), new Point(835, 888))); //DARK BROWN

    }
}
