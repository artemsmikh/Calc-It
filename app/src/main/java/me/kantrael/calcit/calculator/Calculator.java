package me.kantrael.calcit.calculator;

import android.util.Log;

public class Calculator {

    public interface OnCalculatorResultChangedListener {
        void onCalculatorResultChanged();
    }

    private OnCalculatorResultChangedListener onCalculatorResultChangedListener;

    private double result = 0;

    public Calculator() {
    }


    /*
     * Setters and getters
     */

    public void setOnCalculatorResultChangedListener(OnCalculatorResultChangedListener onCalculatorResultChangedListener) {
        this.onCalculatorResultChangedListener = onCalculatorResultChangedListener;
    }

    public double getResult() {
        return result;
    }


    /*
     * Actions
     */

    public void appendDigit(int digit) {
        Log.d("calc", "append digit: " + digit);
    }

    public void applyAddOperation() {
        Log.d("calc", "apply add operation");
    }

    public void applySubtractOperation() {
        Log.d("calc", "apply subtract operation");
    }

    public void applyMultiplyOperation() {
        Log.d("calc", "apply multiply operation");
    }

    public void applyDivideOperation() {
        Log.d("calc", "apply divide operation");
    }

    public void calculate() {
        Log.d("calc", "calculate result");
    }

    public void clear() {
        Log.d("calc", "clear result");
    }
}
