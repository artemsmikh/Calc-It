package me.kantrael.calcit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.kantrael.calcit.R;

public class ResultsListAdapter extends BaseAdapter {
    private List<String> data;
    private Context context;

    public ResultsListAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
            );
            view = inflater.inflate(R.layout.results_list_item, parent, false);
        }

        TextView textViewTitle = (TextView) view.findViewById(R.id.results_list_item_textView);
        if (textViewTitle != null) {
            textViewTitle.setText(data.get(position));
        }

        return view;
    }
}
