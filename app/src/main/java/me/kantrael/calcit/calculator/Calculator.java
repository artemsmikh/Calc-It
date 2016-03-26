package me.kantrael.calcit.calculator;

import android.util.Log;

public class Calculator {

    public interface OnCalculatorResultChangedListener {
        void onCalculatorStateChanged();
    }

    public enum CalculatorOperator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    public enum CalculatorDigit {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE
    }

    public enum CalculatorError {
        DIVIDE_BY_ZERO
    }


    private OnCalculatorResultChangedListener onCalculatorResultChangedListener;

    private double currentOperand;
    private double previousOperand;
    private CalculatorOperator operator;
    private CalculatorError error;
    private boolean needToClearOperandBeforeAppend;

    public Calculator() {
    }


    /*
     * Setters and getters
     */

    public void setOnCalculatorResultChangedListener(OnCalculatorResultChangedListener onCalculatorResultChangedListener) {
        this.onCalculatorResultChangedListener = onCalculatorResultChangedListener;
    }

    public double getCurrentOperand() {
        return currentOperand;
    }

    public double getPreviousOperand() {
        return previousOperand;
    }

    public CalculatorOperator getOperator() {
        return operator;
    }

    public boolean hasPreviousOperand() {
        return operator != null;
    }

    public CalculatorError getError() {
        return error;
    }

    /*
     * Actions
     */

    public void appendDigit(CalculatorDigit digit) {
        if (digit == null) {
            return;
        }

        clearErrorState();

        switch (digit) {
            case ZERO:
                appendOperandDigit(0);
                break;

            case ONE:
                appendOperandDigit(1);
                break;

            case TWO:
                appendOperandDigit(2);
                break;

            case THREE:
                appendOperandDigit(3);
                break;

            case FOUR:
                appendOperandDigit(4);
                break;

            case FIVE:
                appendOperandDigit(5);
                break;

            case SIX:
                appendOperandDigit(6);
                break;

            case SEVEN:
                appendOperandDigit(7);
                break;

            case EIGHT:
                appendOperandDigit(8);
                break;

            case NINE:
                appendOperandDigit(9);
                break;
        }
        notifyListenerStateChanged();
    }

    public void applyOperator(CalculatorOperator op) {
        if (op == null) {
            return;
        }

        clearErrorState();

        double operandToShow = currentOperand;
        if (calculateCurrentExpression()) {
            previousOperand = currentOperand;
            currentOperand = operandToShow;
            needToClearOperandBeforeAppend = true;
            operator = op;
        }

        notifyListenerStateChanged();
    }

    public void calculate() {
        clearErrorState();
        calculateCurrentExpression();
        clearPreviousOperandAndOperator();
        needToClearOperandBeforeAppend = true;
        notifyListenerStateChanged();
    }

    public void clear() {
        clearErrorState();
        clearPreviousOperandAndOperator();
        currentOperand = 0;

        notifyListenerStateChanged();
    }


    /*
     * Util methods
     */

    private void appendOperandDigit(int digit) {
        if (digit != 0 || currentOperand != 0) {
            if (needToClearOperandBeforeAppend) {
                currentOperand = 0;
                needToClearOperandBeforeAppend = false;
            }
            currentOperand *= 10;
            currentOperand += digit;
        }
    }

    private void notifyListenerStateChanged() {
        if (onCalculatorResultChangedListener != null) {
            onCalculatorResultChangedListener.onCalculatorStateChanged();
        }
    }

    private boolean calculateCurrentExpression() {
        if (operator == null) {
            return true;
        }

        switch (operator) {
            case ADD:
                currentOperand = previousOperand + currentOperand;
                break;

            case SUBTRACT:
                currentOperand = previousOperand - currentOperand;
                break;

            case MULTIPLY:
                currentOperand = previousOperand * currentOperand;
                break;

            case DIVIDE:
                if (currentOperand != 0) {
                    currentOperand = previousOperand / currentOperand;
                } else {
                    error = CalculatorError.DIVIDE_BY_ZERO;
                    return false;
                }
                break;
        }
        return true;
    }

    private void clearErrorState() {
        error = null;
    }

    private void clearPreviousOperandAndOperator() {
        previousOperand = 0;
        operator = null;
    }
}
