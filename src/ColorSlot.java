import java.awt.*;

public class ColorSlot {
    public Color c;
    public Point location;
    public HSBColor hsb_c;

    public ColorSlot(Color c, Point location) {
        this.c = c;
        this.location = location;
    }

    public ColorSlot(HSBColor c, Point location) {
        this.hsb_c = c;
        this.location = location;
    }
}
