package me.kantrael.calcit.util;

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

    public static String calculatorOperatorToCharacter(Calculator.CalculatorOperator operator) {
        if (operator != null) {
            switch (operator) {
                case ADD:
                    return "+";

                case SUBTRACT:
                    return "−";

                case MULTIPLY:
                    return "×";

                case DIVIDE:
                    return "÷";
            }
        }
        return null;
    }
}
