package me.kantrael.calcit.calculator;

import me.kantrael.calcit.util.StringUtils;

public class BasicCalculator extends Calculator {

    private String previousOperand;
    private String currentOperand;
    private String memoryOperand;
    private BinaryOperator operator;
    private boolean needToClearOperandBeforeAppend;

    public BasicCalculator() {
        currentOperand = "0";
    }


    /*
     * Setters and getters
     */

    public String getCurrentOperand() {
        return currentOperand;
    }

    public String getPreviousOperand() {
        return previousOperand;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public boolean hasPreviousOperand() {
        return operator != null;
    }

    public boolean hasOperandInMemory() {
        return memoryOperand != null;
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

    public void appendDot() {
        if (!currentOperandHasDot() || needToClearOperandBeforeAppend) {
            clearErrorState();

            if (needToClearOperandBeforeAppend) {
                currentOperand = "0.";
                needToClearOperandBeforeAppend = false;
            } else {
                currentOperand += ".";
            }

            notifyListenerStateChanged();
        }
    }

    public void applyBinaryOperator(BinaryOperator op) {
        if (op == null) {
            return;
        }

        clearErrorState();

        String operandToShow = getCurrentOperandWithoutTrailingDot();
        if (calculateCurrentExpression()) {
            previousOperand = getCurrentOperandWithoutTrailingDot();
            currentOperand = operandToShow;
            needToClearOperandBeforeAppend = true;
            operator = op;
        }

        notifyListenerStateChanged();
    }

    public void applyUnaryOperator(UnaryOperator op) {
        if (op == null || getCurrentOperandWithoutTrailingDot() == null) {
            return;
        }

        clearErrorState();

        double currentValue = StringUtils.stringToDouble(getCurrentOperandWithoutTrailingDot());

        switch (op) {
            case INVERSE:
                currentOperand = StringUtils.doubleToString(currentValue * -1);
                break;

            case RECIPROCAL:
                if (currentValue == 0) {
                    error = CalculatorError.DIVIDE_BY_ZERO;
                } else {
                    currentOperand = StringUtils.doubleToString(1 / currentValue);
                    needToClearOperandBeforeAppend = true;
                }
                break;

            case SQRT:
                currentOperand = StringUtils.doubleToString(Math.sqrt(currentValue));
                needToClearOperandBeforeAppend = true;
                break;
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
        needToClearOperandBeforeAppend = false;

        notifyListenerStateChanged();
    }

    public void memorySave() {
        memoryOperand = getCurrentOperandWithoutTrailingDot();
        notifyListenerStateChanged();
    }

    public void memoryClear() {
        memoryOperand = null;
        notifyListenerStateChanged();
    }

    public void memoryRestore() {
        if (memoryOperand != null) {
            currentOperand = memoryOperand;
            needToClearOperandBeforeAppend = true;
            notifyListenerStateChanged();
        }
    }

    public void erase() {
        if (needToClearOperandBeforeAppend) {
            currentOperand = "0";
            needToClearOperandBeforeAppend = false;
        } else {
            if (!currentOperand.equals("0")) {
                boolean setToZero = currentOperand.length() == 1
                        || (currentOperand.length() == 2 && currentOperand.substring(0, 1).equals("-"));
                if (setToZero) {
                    currentOperand = "0";
                } else {
                    currentOperand = currentOperand.substring(0, currentOperand.length() - 1);
                }
            } else {
                // Operand is 0
                if (operator != null && previousOperand != null) {
                    currentOperand = previousOperand;
                    previousOperand = null;
                    operator = null;
                }
            }
        }

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

    private boolean calculateCurrentExpression() {
        if (operator == null || getCurrentOperandWithoutTrailingDot() == null || previousOperand == null) {
            return true;
        }

        double currentValue = StringUtils.stringToDouble(getCurrentOperandWithoutTrailingDot());
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

            case REMAINDER:
                if (currentValue != 0) {
                    currentOperand = StringUtils.doubleToString(previousValue % currentValue);
                } else {
                    error = CalculatorError.DIVIDE_BY_ZERO;
                    return false;
                }
                break;

            case POWER:
                currentOperand = StringUtils.doubleToString(Math.pow(previousValue, currentValue));
                break;
        }
        return true;
    }

    private void clearPreviousOperandAndOperator() {
        previousOperand = null;
        operator = null;
    }

    private boolean currentOperandHasDot() {
        return currentOperand != null && currentOperand.contains(".");
    }

    private boolean currentOperandHasDotAtTheEnd() {
        return currentOperand != null && currentOperand.endsWith(".");
    }

    private String getCurrentOperandWithoutTrailingDot() {
        if (currentOperandHasDotAtTheEnd()) {
            return currentOperand.substring(0, currentOperand.length() - 1);
        }
        return currentOperand;
    }
}
