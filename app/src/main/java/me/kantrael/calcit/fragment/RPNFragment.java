package me.kantrael.calcit.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.kantrael.calcit.R;
import me.kantrael.calcit.adapter.ResultsListAdapter;
import me.kantrael.calcit.calculator.Calculator;
import me.kantrael.calcit.calculator.RPNCalculator;

public class RPNFragment extends Fragment implements
        View.OnClickListener {

    private RPNCalculator calculator;
    private List<String> operandsStack;

    private ListView listViewOperators;
    private TextView textViewResult;
    private TextView textViewMemory;


    public RPNFragment() {
        // Required empty public constructor
    }


    /*
     * Fragment lifecycle
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculator = new RPNCalculator();
        operandsStack = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rpn, container, false);

        initUI(view);

        updateCalculatorView();

        return view;
    }


    /*
     * UI
     */

    private void initUI(View parent) {
        if (parent != null) {
            textViewResult = (TextView) parent.findViewById(R.id.text_view_result);
            textViewMemory = (TextView) parent.findViewById(R.id.text_view_memory);

            listViewOperators = (ListView) parent.findViewById(R.id.list_view_operators);
            if (listViewOperators != null) {
                ResultsListAdapter adapter = new ResultsListAdapter(operandsStack, getActivity());
                listViewOperators.setAdapter(adapter);
            }

            initButtons(parent);
        }
    }

    private void initButtons(View parent) {
        if (parent != null) {
            int[] buttons = {
                    R.id.button_0,
                    R.id.button_1,
                    R.id.button_2,
                    R.id.button_3,
                    R.id.button_4,
                    R.id.button_5,
                    R.id.button_6,
                    R.id.button_7,
                    R.id.button_8,
                    R.id.button_9,
                    R.id.button_add,
                    R.id.button_subtract,
                    R.id.button_multiply,
                    R.id.button_divide,
                    R.id.button_calculate,
                    R.id.button_clear,
                    R.id.button_memory_clear,
                    R.id.button_memory_restore,
                    R.id.button_memory_save,
                    R.id.button_remainder,
                    R.id.button_reciprocal,
                    R.id.button_sqrt,
                    R.id.button_inverse,
                    R.id.button_dot,
                    R.id.button_erase,
                    R.id.button_power,
                    R.id.button_enter
            };

            for (int buttonId : buttons) {
                View button = parent.findViewById(buttonId);
                if (button != null) {
                    button.setOnClickListener(this);
                }
            }
        }
    }

    private void updateCalculatorView() {

    }

    private void scrollResultsListViewToBottom() {
        listViewOperators.post(new Runnable() {
            @Override
            public void run() {
                listViewOperators.setSelection(listViewOperators.getAdapter().getCount() - 1);
            }
        });
    }

    /*
     * View.OnClickListener
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_0:
                calculator.appendDigit(Calculator.CalculatorDigit.ZERO);
                break;

            case R.id.button_1:
                calculator.appendDigit(Calculator.CalculatorDigit.ONE);
                break;

            case R.id.button_2:
                calculator.appendDigit(Calculator.CalculatorDigit.TWO);
                break;

            case R.id.button_3:
                calculator.appendDigit(Calculator.CalculatorDigit.THREE);
                break;

            case R.id.button_4:
                calculator.appendDigit(Calculator.CalculatorDigit.FOUR);
                break;

            case R.id.button_5:
                calculator.appendDigit(Calculator.CalculatorDigit.FIVE);
                break;

            case R.id.button_6:
                calculator.appendDigit(Calculator.CalculatorDigit.SIX);
                break;

            case R.id.button_7:
                calculator.appendDigit(Calculator.CalculatorDigit.SEVEN);
                break;

            case R.id.button_8:
                calculator.appendDigit(Calculator.CalculatorDigit.EIGHT);
                break;

            case R.id.button_9:
                calculator.appendDigit(Calculator.CalculatorDigit.NINE);
                break;

            case R.id.button_add:
                calculator.applyBinaryOperator(Calculator.BinaryOperator.ADD);
                break;

            case R.id.button_subtract:
                calculator.applyBinaryOperator(Calculator.BinaryOperator.SUBTRACT);
                break;

            case R.id.button_multiply:
                calculator.applyBinaryOperator(Calculator.BinaryOperator.MULTIPLY);
                break;

            case R.id.button_divide:
                calculator.applyBinaryOperator(Calculator.BinaryOperator.DIVIDE);
                break;

            case R.id.button_remainder:
                calculator.applyBinaryOperator(Calculator.BinaryOperator.REMAINDER);
                break;

            case R.id.button_power:
                calculator.applyBinaryOperator(Calculator.BinaryOperator.POWER);
                break;

            case R.id.button_calculate:
                calculator.calculate();
                break;

            case R.id.button_clear:
                calculator.clear();
                break;

            case R.id.button_memory_clear:
                calculator.memoryClear();
                break;

            case R.id.button_memory_restore:
                calculator.memoryRestore();
                break;

            case R.id.button_memory_save:
                calculator.memorySave();
                break;

            case R.id.button_reciprocal:
                calculator.applyUnaryOperator(Calculator.UnaryOperator.RECIPROCAL);
                break;

            case R.id.button_sqrt:
                calculator.applyUnaryOperator(Calculator.UnaryOperator.SQRT);
                break;

            case R.id.button_inverse:
                calculator.applyUnaryOperator(Calculator.UnaryOperator.INVERSE);
                break;

            case R.id.button_dot:
                calculator.appendDot();
                break;

            case R.id.button_erase:
                calculator.erase();
                break;

            case R.id.button_enter:
                calculator.enter();
                break;
        }
    }
}

