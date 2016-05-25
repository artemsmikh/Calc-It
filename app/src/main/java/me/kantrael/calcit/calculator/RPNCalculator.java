package me.kantrael.calcit.calculator;

import java.util.List;
import java.util.Stack;

public class RPNCalculator extends Calculator {

    private Stack<String> operandsStack;
    private String currentOperand;
    private String memoryOperand;
    private boolean needToClearOperandBeforeAppend;

    public RPNCalculator() {
        operandsStack = new Stack<>();
        currentOperand = "0";
    }


    /*
     * Setters and getters
     */

    public String getCurrentOperand() {
        return currentOperand;
    }

    public List<String> getOperandsStack() {
        return operandsStack;
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
    }

    public void applyUnaryOperator(UnaryOperator op) {
    }

    public void calculate() {
    }

    public void clear() {
        clearErrorState();
        operandsStack.clear();
        currentOperand = "0";

        notifyListenerStateChanged();
    }

    public void memorySave() {
        memoryOperand = getCurrentOperandWithoutTrailingDot();
        needToClearOperandBeforeAppend = true;
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
                if (!operandsStack.isEmpty()) {
                    currentOperand = operandsStack.pop();
                }
            }
        }

        notifyListenerStateChanged();
    }

    public void enter() {
        operandsStack.push(getCurrentOperandWithoutTrailingDot());
        needToClearOperandBeforeAppend = true;

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
