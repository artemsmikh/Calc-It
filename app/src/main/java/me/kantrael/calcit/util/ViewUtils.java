package me.kantrael.calcit.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {
    public static View getReusableView(View convertView, int viewResId, ViewGroup parent,
                                       LayoutInflater inflater) {
        if (convertView != null) {
            Integer tag = (Integer) convertView.getTag();
            if (tag != null && tag.equals(viewResId)) {
                return convertView;
            }
        }
        View view = inflater.inflate(viewResId, parent, false);
        view.setTag(viewResId);
        return view;
    }
}
