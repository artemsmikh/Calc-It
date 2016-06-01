package me.kantrael.calcit.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.simpleframework.xml.core.Persister;

import java.io.IOException;

import me.kantrael.calcit.R;
import me.kantrael.calcit.adapter.WolframResultsListAdapter;
import me.kantrael.calcit.model.WolframResult;
import me.kantrael.calcit.util.UrlUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WolframAlphaFragment extends Fragment {
    private EditText editTextQuery;
    private ProgressBar progressBar;
    private ListView listViewResults;
    private TextView textViewEnterQuery;

    private OkHttpClient client;
    private WolframResultsListAdapter adapter;


    public WolframAlphaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new OkHttpClient();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (editTextQuery != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE
            );
            imm.showSoftInput(editTextQuery, InputMethodManager.SHOW_IMPLICIT);
            editTextQuery.requestFocus();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wolfram_alpha, container, false);

        editTextQuery = (EditText) view.findViewById(R.id.edit_text_query);
        editTextQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        listViewResults = (ListView) view.findViewById(R.id.list_view_results);
        textViewEnterQuery = (TextView) view.findViewById(R.id.text_view_enter_query);

        adapter = new WolframResultsListAdapter(getActivity());
        listViewResults.setAdapter(adapter);

        TextView buttonSearch = (TextView) view.findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        return view;
    }

    private void performSearch() {
        String query = editTextQuery.getText().toString();
        makeSearchRequest(query);
    }

    private void makeSearchRequest(String query) {
        if (query.isEmpty()) {
            showToast(R.string.error_empty_query);
            return;
        }

        showProgressBar();

        int width = listViewResults.getWidth();

        HttpUrl url = UrlUtils.buildWolframSearchUrl(query, width);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                onRequestError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    boolean responseProcessed = processResponseString(response.body().string());

                    if (responseProcessed) {
                        showResultList();
                    } else {
                        onEmptyResult();
                    }
                } else {
                    onRequestError();
                }
            }
        });
    }

    private void onRequestError() {
        showToast(R.string.error_try_again);
        showEmptyScreenMessage();
    }

    private void onEmptyResult() {
        showToast(R.string.message_no_results);
        showEmptyScreenMessage();
    }

    private void showToast(final int stringResId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), stringResId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean processResponseString(@NonNull String response) {
        WolframResult result = null;

        try {
            Persister persister = new Persister();
            result = persister.read(WolframResult.class, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result != null) {
            updateResults(result);
            return true;
        }
        return false;
    }

    private void updateResults(final @NonNull WolframResult result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setResult(result);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void showProgressBar() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewEnterQuery.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                listViewResults.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showResultList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                listViewResults.setVisibility(View.VISIBLE);
                textViewEnterQuery.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showEmptyScreenMessage() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                listViewResults.setVisibility(View.INVISIBLE);
                textViewEnterQuery.setVisibility(View.VISIBLE);
            }
        });
    }
}
