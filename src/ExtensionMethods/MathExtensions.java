package ExtensionMethods;

public class MathExtensions {
    public static final float PI = 3.14159265358979323f;

    public static float sin(double x) {
        return (float) Math.sin(x);
    }

    public static float cos(double x) {
        return (float) Math.cos(x);
    }

    public static float atan2(double y, double x) {
        return (float) Math.atan2(y, x);
    }

    public static float sqrt(double x) {
        return (float) Math.sqrt(x);
    }

    public static float abs(double value) {
        return (float) Math.abs(value);
    }

    public static float clamp(float value, float lower, float upper) {
        if (value < lower) {
            return lower;
        }
        if (value > upper) {
            return upper;
        }
        return value;
    }
}
