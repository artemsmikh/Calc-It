package me.kantrael.calcit.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.kantrael.calcit.R;
import me.kantrael.calcit.calculator.Calculator;
import me.kantrael.calcit.util.StringUtils;

public class CalculatorFragment extends Fragment implements
        View.OnClickListener,
        Calculator.OnCalculatorResultChangedListener {

    private Calculator calculator;
    private TextView textViewResult;
    private TextView textViewPreviousOperand;
    private TextView textViewMemory;


    public CalculatorFragment() {
        // Required empty public constructor
    }


    /*
     * Fragment lifecycle
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculator = new Calculator();
        calculator.setOnCalculatorResultChangedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        textViewResult = (TextView) view.findViewById(
                R.id.fragment_calculator_textView_result
        );
        textViewPreviousOperand = (TextView) view.findViewById(
                R.id.fragment_calculator_textView_previous_operand
        );
        textViewMemory = (TextView) view.findViewById(
                R.id.fragment_calculator_textView_memory
        );

        updateCalculatorView();

        int[] buttons = {
                R.id.fragment_calculator_button_0,
                R.id.fragment_calculator_button_1,
                R.id.fragment_calculator_button_2,
                R.id.fragment_calculator_button_3,
                R.id.fragment_calculator_button_4,
                R.id.fragment_calculator_button_5,
                R.id.fragment_calculator_button_6,
                R.id.fragment_calculator_button_7,
                R.id.fragment_calculator_button_8,
                R.id.fragment_calculator_button_9,
                R.id.fragment_calculator_button_add,
                R.id.fragment_calculator_button_subtract,
                R.id.fragment_calculator_button_multiply,
                R.id.fragment_calculator_button_divide,
                R.id.fragment_calculator_button_calculate,
                R.id.fragment_calculator_button_clear,
                R.id.fragment_calculator_button_memory_clear,
                R.id.fragment_calculator_button_memory_restore,
                R.id.fragment_calculator_button_memory_save,
                R.id.fragment_calculator_button_remainder,
                R.id.fragment_calculator_button_reciprocal,
                R.id.fragment_calculator_button_sqrt,
                R.id.fragment_calculator_button_inverse,
                R.id.fragment_calculator_button_dot
        };

        for (int buttonId : buttons) {
            Button button = (Button) view.findViewById(buttonId);
            if (button != null) {
                button.setOnClickListener(this);
            }
        }

        return view;
    }


    /*
     * UI
     */

    private void updateCalculatorView() {
        if (calculator == null) {
            return;
        }

        String resultText;
        if (calculator.getError() == Calculator.CalculatorError.DIVIDE_BY_ZERO) {
            resultText = getString(R.string.calculator_message_error);
        } else {
            resultText = calculator.getCurrentOperand();
        }
        textViewResult.setText(resultText);

        String previousOperandText = "";
        if (calculator.hasPreviousOperand()) {
            String operand = calculator.getPreviousOperand();
            String operator = StringUtils.calculatorOperatorToCharacter(
                    calculator.getOperator(),
                    getActivity()
            );

            if (operand != null && operator != null) {
                previousOperandText = String.format("%s %s", operand, operator);
            }
        }
        textViewPreviousOperand.setText(previousOperandText);

        textViewMemory.setText(calculator.hasOperandInMemory()
                ? getString(R.string.calculator_message_memory)
                : null
        );
    }


    /*
     * View.OnClickListener
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_calculator_button_0:
                calculator.appendDigit(Calculator.CalculatorDigit.ZERO);
                break;

            case R.id.fragment_calculator_button_1:
                calculator.appendDigit(Calculator.CalculatorDigit.ONE);
                break;

            case R.id.fragment_calculator_button_2:
                calculator.appendDigit(Calculator.CalculatorDigit.TWO);
                break;

            case R.id.fragment_calculator_button_3:
                calculator.appendDigit(Calculator.CalculatorDigit.THREE);
                break;

            case R.id.fragment_calculator_button_4:
                calculator.appendDigit(Calculator.CalculatorDigit.FOUR);
                break;

            case R.id.fragment_calculator_button_5:
                calculator.appendDigit(Calculator.CalculatorDigit.FIVE);
                break;

            case R.id.fragment_calculator_button_6:
                calculator.appendDigit(Calculator.CalculatorDigit.SIX);
                break;

            case R.id.fragment_calculator_button_7:
                calculator.appendDigit(Calculator.CalculatorDigit.SEVEN);
                break;

            case R.id.fragment_calculator_button_8:
                calculator.appendDigit(Calculator.CalculatorDigit.EIGHT);
                break;

            case R.id.fragment_calculator_button_9:
                calculator.appendDigit(Calculator.CalculatorDigit.NINE);
                break;

            case R.id.fragment_calculator_button_add:
                calculator.applyBinaryOperator(Calculator.BinaryOperator.ADD);
                break;

            case R.id.fragment_calculator_button_subtract:
                calculator.applyBinaryOperator(Calculator.BinaryOperator.SUBTRACT);
                break;

            case R.id.fragment_calculator_button_multiply:
                calculator.applyBinaryOperator(Calculator.BinaryOperator.MULTIPLY);
                break;

            case R.id.fragment_calculator_button_divide:
                calculator.applyBinaryOperator(Calculator.BinaryOperator.DIVIDE);
                break;

            case R.id.fragment_calculator_button_remainder:
                calculator.applyBinaryOperator(Calculator.BinaryOperator.REMAINDER);
                break;

            case R.id.fragment_calculator_button_calculate:
                calculator.calculate();
                break;

            case R.id.fragment_calculator_button_clear:
                calculator.clear();
                break;

            case R.id.fragment_calculator_button_memory_clear:
                calculator.memoryClear();
                break;

            case R.id.fragment_calculator_button_memory_restore:
                calculator.memoryRestore();
                break;

            case R.id.fragment_calculator_button_memory_save:
                calculator.memorySave();
                break;

            case R.id.fragment_calculator_button_reciprocal:
                calculator.applyUnaryOperator(Calculator.UnaryOperator.RECIPROCAL);
                break;

            case R.id.fragment_calculator_button_sqrt:
                calculator.applyUnaryOperator(Calculator.UnaryOperator.SQRT);
                break;

            case R.id.fragment_calculator_button_inverse:
                calculator.applyUnaryOperator(Calculator.UnaryOperator.INVERSE);
                break;

            case R.id.fragment_calculator_button_dot:
                calculator.appendDot();
                break;
        }
    }


    /*
     * Calculator.OnCalculatorResultChangedListener
     */

    @Override
    public void onCalculatorStateChanged() {
        updateCalculatorView();
    }
}
