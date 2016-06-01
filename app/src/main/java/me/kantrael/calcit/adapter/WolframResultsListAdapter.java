package me.kantrael.calcit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.kantrael.calcit.R;
import me.kantrael.calcit.model.WolframImage;
import me.kantrael.calcit.model.WolframPod;
import me.kantrael.calcit.model.WolframResult;
import me.kantrael.calcit.model.WolframSubPod;
import me.kantrael.calcit.util.ViewUtils;

public class WolframResultsListAdapter extends BaseAdapter {
    private Context context;
    private List<Result> results;

    public WolframResultsListAdapter(Context context) {
        this.context = context;
    }

    public void setResult(@NonNull WolframResult result) {
        results = new ArrayList<>();

        for (WolframPod pod : result.pods) {
            if (!pod.error) {
                results.add(new ResultTitle(pod.title));

                for (WolframSubPod subPod : pod.subPods) {
                    results.add(new ResultItem(subPod.text, subPod.image));
                }
            }
        }
    }

    @Override
    public int getCount() {
        return results != null ? results.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );

        Result result = (Result) getItem(position);

        if (result instanceof ResultTitle) {
            View view = ViewUtils.getReusableView(
                    convertView, R.layout.wolfram_result_item_title, parent, inflater
            );

            prepareResultTitleView(view, (ResultTitle) result);

            return view;
        } else if (result instanceof ResultItem) {
            View view = ViewUtils.getReusableView(
                    convertView, R.layout.wolfram_result_item, parent, inflater
            );

            prepareResultItemView(view, (ResultItem) result);

            return view;
        }

        return null;
    }

    private void prepareResultTitleView(@NonNull View view, @NonNull ResultTitle resultTitle) {
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText(resultTitle.title);
    }

    private void prepareResultItemView(@NonNull View view, @NonNull ResultItem resultItem) {
        final TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText(resultItem.text);
        textView.setVisibility(View.VISIBLE);

        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        Picasso.with(context)
                .load(resultItem.image.url)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        textView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    class ResultTitle extends Result {
        public String title;

        public ResultTitle(String title) {
            this.title = title;
        }
    }

    class ResultItem extends Result {
        public String text;
        public WolframImage image;

        public ResultItem(String text, WolframImage image) {
            this.text = text;
            this.image = image;
        }
    }

    class Result {
    }
}
