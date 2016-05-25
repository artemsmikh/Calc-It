package me.kantrael.calcit.calculator;

public class Calculator {
    protected OnCalculatorResultChangedListener onCalculatorResultChangedListener;
    protected CalculatorError error;

    protected void notifyListenerStateChanged() {
        if (onCalculatorResultChangedListener != null) {
            onCalculatorResultChangedListener.onCalculatorStateChanged();
        }
    }

    protected void clearErrorState() {
        error = null;
    }


    /*
     * Setters and getters
     */

    public void setOnCalculatorResultChangedListener(OnCalculatorResultChangedListener onCalculatorResultChangedListener) {
        this.onCalculatorResultChangedListener = onCalculatorResultChangedListener;
    }

    public CalculatorError getError() {
        return error;
    }


    /*
     * Enumerations and interfaces
     */

    public enum BinaryOperator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, REMAINDER, POWER
    }

    public enum UnaryOperator {
        INVERSE, RECIPROCAL, SQRT
    }

    public enum CalculatorDigit {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE
    }

    public enum CalculatorError {
        DIVIDE_BY_ZERO
    }

    public interface OnCalculatorResultChangedListener {
        void onCalculatorStateChanged();
    }
}
