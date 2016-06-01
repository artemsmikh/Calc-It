package me.kantrael.calcit.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.simpleframework.xml.core.Persister;

import java.io.IOException;

import me.kantrael.calcit.R;
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

    private OkHttpClient client;


    public WolframAlphaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new OkHttpClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wolfram_alpha, container, false);

        editTextQuery = (EditText) view.findViewById(R.id.edit_text_query);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        listViewResults = (ListView) view.findViewById(R.id.list_view_results);

        Button buttonRequest = (Button) view.findViewById(R.id.button_test);
        buttonRequest.setOnClickListener(onSearchButtonClickListener());

        return view;
    }

    private View.OnClickListener onSearchButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editTextQuery.getText().toString();
                makeSearchRequest(query);
            }
        };
    }

    private void makeSearchRequest(String query) {
        if (query.isEmpty()) {
            Toast.makeText(getActivity(), R.string.error_empty_query, Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressBar();

        HttpUrl url = UrlUtils.buildWolframSearchUrl(query);

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
                    processResponseString(response.body().string());
                    hideProgressBar();
                } else {
                    onRequestError();
                }
            }
        });
    }

    private void onRequestError() {
        Toast.makeText(getActivity(), R.string.error_try_again, Toast.LENGTH_SHORT).show();
        hideProgressBar();
    }

    private void processResponseString(@NonNull String response){
        WolframResult result = null;

        try {
            Persister persister = new Persister();
            result = persister.read(WolframResult.class, response);
        } catch (Exception e) {
            e.printStackTrace();
            onRequestError();
        }

        if (result != null) {
            showResult(result);
        }
    }

    private void showResult(@NonNull WolframResult result) {

    }

    private void showProgressBar() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                listViewResults.setVisibility(View.GONE);
            }
        });
    }

    private void hideProgressBar() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                listViewResults.setVisibility(View.VISIBLE);
            }
        });
    }
}
