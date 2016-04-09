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

        textViewResult = (TextView) view.findViewById(R.id.fragment_calculator_textView_result);
        textViewPreviousOperand = (TextView) view.findViewById(R.id.fragment_calculator_textView_previous_operand);

        updateCalculatorView();

        assignButtonClickListener(R.id.fragment_calculator_button_0, view);
        assignButtonClickListener(R.id.fragment_calculator_button_1, view);
        assignButtonClickListener(R.id.fragment_calculator_button_2, view);
        assignButtonClickListener(R.id.fragment_calculator_button_3, view);
        assignButtonClickListener(R.id.fragment_calculator_button_4, view);
        assignButtonClickListener(R.id.fragment_calculator_button_5, view);
        assignButtonClickListener(R.id.fragment_calculator_button_6, view);
        assignButtonClickListener(R.id.fragment_calculator_button_7, view);
        assignButtonClickListener(R.id.fragment_calculator_button_8, view);
        assignButtonClickListener(R.id.fragment_calculator_button_9, view);
        assignButtonClickListener(R.id.fragment_calculator_button_add, view);
        assignButtonClickListener(R.id.fragment_calculator_button_subtract, view);
        assignButtonClickListener(R.id.fragment_calculator_button_multiply, view);
        assignButtonClickListener(R.id.fragment_calculator_button_divide, view);
        assignButtonClickListener(R.id.fragment_calculator_button_calculate, view);
        assignButtonClickListener(R.id.fragment_calculator_button_clear, view);

        return view;
    }


    /*
     * Actions
     */

    private void assignButtonClickListener(int buttonResId, View parent) {
        if (parent != null) {
            Button button = (Button) parent.findViewById(buttonResId);
            if (button != null) {
                button.setOnClickListener(this);
            }
        }
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
            resultText = getString(R.string.fragment_calculator_error);
        } else {
            resultText = calculator.getCurrentOperand();
        }
        textViewResult.setText(resultText);

        String previousOperandText = "";
        if (calculator.hasPreviousOperand()) {
            String operand = calculator.getPreviousOperand();
            String operator = StringUtils.calculatorOperatorToCharacter(calculator.getOperator());

            if (operand != null && operator != null) {
                previousOperandText = String.format("%s %s", operand, operator);
            }
        }
        textViewPreviousOperand.setText(previousOperandText);
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
                calculator.applyOperator(Calculator.CalculatorOperator.ADD);
                break;

            case R.id.fragment_calculator_button_subtract:
                calculator.applyOperator(Calculator.CalculatorOperator.SUBTRACT);
                break;

            case R.id.fragment_calculator_button_multiply:
                calculator.applyOperator(Calculator.CalculatorOperator.MULTIPLY);
                break;

            case R.id.fragment_calculator_button_divide:
                calculator.applyOperator(Calculator.CalculatorOperator.DIVIDE);
                break;

            case R.id.fragment_calculator_button_calculate:
                calculator.calculate();
                break;

            case R.id.fragment_calculator_button_clear:
                calculator.clear();
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
