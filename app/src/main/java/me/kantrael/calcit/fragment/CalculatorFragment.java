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
        updateResultTextView();

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

    private void updateResultTextView() {
        if (textViewResult != null && calculator != null) {
            textViewResult.setText(StringUtils.doubleToString(calculator.getResult()));
        }
    }


    /*
     * View.OnClickListener
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_calculator_button_0:
                calculator.appendDigit(0);
                break;

            case R.id.fragment_calculator_button_1:
                calculator.appendDigit(1);
                break;

            case R.id.fragment_calculator_button_2:
                calculator.appendDigit(2);
                break;

            case R.id.fragment_calculator_button_3:
                calculator.appendDigit(3);
                break;

            case R.id.fragment_calculator_button_4:
                calculator.appendDigit(4);
                break;

            case R.id.fragment_calculator_button_5:
                calculator.appendDigit(5);
                break;

            case R.id.fragment_calculator_button_6:
                calculator.appendDigit(6);
                break;

            case R.id.fragment_calculator_button_7:
                calculator.appendDigit(7);
                break;

            case R.id.fragment_calculator_button_8:
                calculator.appendDigit(8);
                break;

            case R.id.fragment_calculator_button_9:
                calculator.appendDigit(9);
                break;

            case R.id.fragment_calculator_button_add:
                calculator.applyAddOperation();
                break;

            case R.id.fragment_calculator_button_subtract:
                calculator.applySubtractOperation();
                break;

            case R.id.fragment_calculator_button_multiply:
                calculator.applyMultiplyOperation();
                break;

            case R.id.fragment_calculator_button_divide:
                calculator.applyDivideOperation();
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
    public void onCalculatorResultChanged() {
        updateResultTextView();
    }
}
