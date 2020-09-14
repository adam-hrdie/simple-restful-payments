package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DoubleUtil {

    private static DecimalFormat df = new DecimalFormat("0.00");

    private DoubleUtil() {
        // hide default constuctor
    }

    public static double round(double num) {
        return Math.round(num * 100.0) / 100.0;
    }

    public static String format(double num) {
        return df.format(num);
    }

    public static double subtract(double a, double b) {
        return BigDecimal.valueOf(a).subtract(BigDecimal.valueOf(b)).doubleValue();
    }

    public static double add(double a, double b) {
        return BigDecimal.valueOf(a).add(BigDecimal.valueOf(b)).doubleValue();
    }

}
