package me.kantrael.calcit.calculator;

import java.util.List;
import java.util.Stack;

import me.kantrael.calcit.util.StringUtils;

public class RPNCalculator extends Calculator {

    private Stack<String> operandsStack;
    private String currentOperand;
    private String memoryOperand;
    private boolean needToClearOperandBeforeAppend;
    private boolean needToAddOperandToStack;

    public RPNCalculator() {
        operandsStack = new Stack<>();
        setCurrentOperand("0");
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

    private void setCurrentOperand(String currentOperand) {
        if (needToAddOperandToStack) {
            needToAddOperandToStack = false;
            operandsStack.push(getCurrentOperandWithoutTrailingDot());
        }
        this.currentOperand = currentOperand;
    }

    private void appendToCurrentOperand(String append) {
        setCurrentOperand(getCurrentOperand() + append);
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
                setCurrentOperand("0.");
                needToClearOperandBeforeAppend = false;
            } else {
                appendToCurrentOperand(".");
            }

            notifyListenerStateChanged();
        }
    }

    public void applyBinaryOperator(BinaryOperator op) {
        if (op == null) {
            return;
        }

        clearErrorState();

        if (needToAddOperandToStack) {
            needToAddOperandToStack = false;
        }

        if (calculateCurrentExpression(op)) {
            needToClearOperandBeforeAppend = true;
            needToAddOperandToStack = true;
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
                setCurrentOperand(StringUtils.doubleToString(currentValue * -1));
                break;

            case RECIPROCAL:
                if (currentValue == 0) {
                    error = CalculatorError.DIVIDE_BY_ZERO;
                } else {
                    setCurrentOperand(StringUtils.doubleToString(1 / currentValue));
                    needToClearOperandBeforeAppend = true;
                }
                break;

            case SQRT:
                setCurrentOperand(StringUtils.doubleToString(Math.sqrt(currentValue)));
                needToClearOperandBeforeAppend = true;
                break;
        }

        notifyListenerStateChanged();
    }

    public void clear() {
        clearErrorState();
        operandsStack.clear();
        needToAddOperandToStack = false;
        needToClearOperandBeforeAppend = false;
        setCurrentOperand("0");

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
            setCurrentOperand(memoryOperand);
            notifyListenerStateChanged();
        }
    }

    public void erase() {
        if (needToAddOperandToStack) {
            needToAddOperandToStack = false;
        }
        if (needToClearOperandBeforeAppend) {
            setCurrentOperand("0");
            needToClearOperandBeforeAppend = false;
        } else {
            if (!currentOperand.equals("0")) {
                boolean setToZero = currentOperand.length() == 1
                        || (currentOperand.length() == 2 && currentOperand.substring(0, 1).equals("-"));
                if (setToZero) {
                    setCurrentOperand("0");
                } else {
                    setCurrentOperand( currentOperand.substring(0, currentOperand.length() - 1));
                }
            } else {
                // Operand is 0
                if (!operandsStack.isEmpty()) {
                    setCurrentOperand(operandsStack.pop());
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
                setCurrentOperand("0");
                needToClearOperandBeforeAppend = false;
            }
            if (currentOperand.equals("0")) {
                setCurrentOperand(digit);
            } else {
                appendToCurrentOperand(digit);
            }
        }
    }

    private boolean calculateCurrentExpression(BinaryOperator operator) {
        if (operandsStack.isEmpty()) {
            return true;
        }

        double currentValue = StringUtils.stringToDouble(getCurrentOperandWithoutTrailingDot());
        double previousValue = StringUtils.stringToDouble(operandsStack.pop());

        switch (operator) {
            case ADD:
                setCurrentOperand(StringUtils.doubleToString(previousValue + currentValue));
                break;

            case SUBTRACT:
                setCurrentOperand(StringUtils.doubleToString(previousValue - currentValue));
                break;

            case MULTIPLY:
                setCurrentOperand(StringUtils.doubleToString(previousValue * currentValue));
                break;

            case DIVIDE:
                if (currentValue != 0) {
                    setCurrentOperand(StringUtils.doubleToString(previousValue / currentValue));
                } else {
                    error = CalculatorError.DIVIDE_BY_ZERO;
                    return false;
                }
                break;

            case REMAINDER:
                if (currentValue != 0) {
                    setCurrentOperand(StringUtils.doubleToString(previousValue % currentValue));
                } else {
                    error = CalculatorError.DIVIDE_BY_ZERO;
                    return false;
                }
                break;

            case POWER:
                setCurrentOperand(StringUtils.doubleToString(Math.pow(previousValue, currentValue)));
                break;
        }
        return true;
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
