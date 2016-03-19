package me.kantrael.calcit.util;

public class StringUtils {
    public static String doubleToString(double value) {
        if (value == (long) value) {
            return Long.toString((long) value);
        } else {
            return Double.toString(value);
        }
    }
}
