package me.kantrael.calcit.util;

import android.content.Context;

import me.kantrael.calcit.R;
import me.kantrael.calcit.calculator.BasicCalculator;

public class StringUtils {
    public static String doubleToString(double value) {
        if (value == (long) value) {
            return Long.toString((long) value);
        } else {
            return Double.toString(value);
        }
    }

    public static double stringToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String calculatorOperatorToCharacter(BasicCalculator.BinaryOperator operator, Context context) {
        if (operator != null) {
            switch (operator) {
                case ADD:
                    return context.getString(R.string.calculator_operation_sign_add);

                case SUBTRACT:
                    return context.getString(R.string.calculator_operation_sign_subtract);

                case MULTIPLY:
                    return context.getString(R.string.calculator_operation_sign_multiply);

                case DIVIDE:
                    return context.getString(R.string.calculator_operation_sign_divide);

                case REMAINDER:
                    return context.getString(R.string.calculator_operation_sign_remainder);

                case POWER:
                    return context.getString(R.string.calculator_operation_sign_power);
            }
        }
        return null;
    }
}
