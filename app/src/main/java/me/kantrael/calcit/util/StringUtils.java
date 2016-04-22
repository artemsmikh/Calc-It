package me.kantrael.calcit.util;

import android.content.Context;

import me.kantrael.calcit.R;
import me.kantrael.calcit.calculator.Calculator;

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

    public static String calculatorOperatorToCharacter(Calculator.BinaryOperator operator, Context context) {
        if (operator != null) {
            switch (operator) {
                case ADD:
                    return context.getString(R.string.calculator_operation_add);

                case SUBTRACT:
                    return context.getString(R.string.calculator_operation_subtract);

                case MULTIPLY:
                    return context.getString(R.string.calculator_operation_multiply);

                case DIVIDE:
                    return context.getString(R.string.calculator_operation_divide);

                case REMAINDER:
                    return context.getString(R.string.calculator_operation_remainder);
            }
        }
        return null;
    }
}
