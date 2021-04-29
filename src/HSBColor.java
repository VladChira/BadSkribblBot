import java.awt.*;

class HSBColor {
    float h,s,b;
    HSBColor(float h, float s, float b) {
        this.h = h;
        this.s = s;
        this.b = b;
    }


    public static HSBColor RGB_TO_HSB(Color c) {
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        float h = hsb[0] * 360.0f;
        float s = hsb[1];
        float b = hsb[2];
        return new HSBColor(h, s, b);
    }
}
