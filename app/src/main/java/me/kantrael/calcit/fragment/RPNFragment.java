package me.kantrael.calcit.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.kantrael.calcit.R;
import me.kantrael.calcit.adapter.ResultsListAdapter;

public class RPNFragment extends Fragment {
    private ListView resultsListView;
    private List<String> resultsData;


    public RPNFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultsData = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rpn, container, false);

        resultsListView = (ListView) view.findViewById(R.id.fragment_rpn_listView_result);
        if (resultsListView != null) {
            // TODO: Get data from calculator model class
            resultsData.add("100000");
            resultsData.add("2000");
            resultsData.add("2020.323224");
            resultsData.add("+");

            ResultsListAdapter adapter = new ResultsListAdapter(resultsData, getActivity());
            resultsListView.setAdapter(adapter);

            scrollResultsListViewToBottom();
        }

        Button buttonAddResult = (Button) view.findViewById(R.id.fragment_rpn_button_add_result);
        if (buttonAddResult != null) {
            buttonAddResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addResult();
                }
            });
        }

        return view;
    }

    private void addResult() {
        resultsData.add(String.format("%d", (int) (Math.random() * 10000)));
        ((ResultsListAdapter) resultsListView.getAdapter()).notifyDataSetChanged();
        scrollResultsListViewToBottom();
    }

    private void scrollResultsListViewToBottom() {
        resultsListView.post(new Runnable() {
            @Override
            public void run() {
                resultsListView.setSelection(resultsListView.getAdapter().getCount() - 1);
            }
        });
    }
}
