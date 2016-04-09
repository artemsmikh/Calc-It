package me.kantrael.calcit.calculator;

import me.kantrael.calcit.util.StringUtils;

public class Calculator {

    private OnCalculatorResultChangedListener onCalculatorResultChangedListener;
    private String previousOperand;
    private String currentOperand;
    private CalculatorOperator operator;
    private CalculatorError error;
    private boolean needToClearOperandBeforeAppend;

    public Calculator() {
        currentOperand = "0";
    }


    /*
     * Setters and getters
     */

    public void setOnCalculatorResultChangedListener(OnCalculatorResultChangedListener onCalculatorResultChangedListener) {
        this.onCalculatorResultChangedListener = onCalculatorResultChangedListener;
    }

    public String getCurrentOperand() {
        return currentOperand;
    }

    public String getPreviousOperand() {
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
                appendOperandDigit("0");
                break;

            case ONE:
                appendOperandDigit("1");
                break;

            case TWO:
                appendOperandDigit("2");
                break;

            case THREE:
                appendOperandDigit("3");
                break;

            case FOUR:
                appendOperandDigit("4");
                break;

            case FIVE:
                appendOperandDigit("5");
                break;

            case SIX:
                appendOperandDigit("6");
                break;

            case SEVEN:
                appendOperandDigit("7");
                break;

            case EIGHT:
                appendOperandDigit("8");
                break;

            case NINE:
                appendOperandDigit("9");
                break;
        }
        notifyListenerStateChanged();
    }

    public void applyOperator(CalculatorOperator op) {
        if (op == null) {
            return;
        }

        clearErrorState();

        String operandToShow = currentOperand;
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
        currentOperand = "0";

        notifyListenerStateChanged();
    }


    /*
     * Private methods
     */

    private void appendOperandDigit(String digit) {
        if (digit == null) {
            return;
        }

        if (!digit.equals("0") || !currentOperand.equals("0")) {
            if (needToClearOperandBeforeAppend) {
                currentOperand = "0";
                needToClearOperandBeforeAppend = false;
            }
            if (currentOperand.equals("0")) {
                currentOperand = digit;
            } else {
                currentOperand += digit;
            }
        }
    }

    private void notifyListenerStateChanged() {
        if (onCalculatorResultChangedListener != null) {
            onCalculatorResultChangedListener.onCalculatorStateChanged();
        }
    }

    private boolean calculateCurrentExpression() {
        if (operator == null || currentOperand == null || previousOperand == null) {
            return true;
        }

        double currentValue = StringUtils.stringToDouble(currentOperand);
        double previousValue = StringUtils.stringToDouble(previousOperand);

        switch (operator) {
            case ADD:
                currentOperand = StringUtils.doubleToString(previousValue + currentValue);
                break;

            case SUBTRACT:
                currentOperand = StringUtils.doubleToString(previousValue - currentValue);
                break;

            case MULTIPLY:
                currentOperand = StringUtils.doubleToString(previousValue * currentValue);
                break;

            case DIVIDE:
                if (currentValue != 0) {
                    currentOperand = StringUtils.doubleToString(previousValue / currentValue);
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
        previousOperand = null;
        operator = null;
    }


    /*
     * Enumerations and interfaces
     */

    public enum CalculatorOperator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
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
