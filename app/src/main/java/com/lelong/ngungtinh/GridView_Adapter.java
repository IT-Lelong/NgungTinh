package com.lelong.ngungtinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GridView_Adapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mData;

    public GridView_Adapter(Context context, List<String> data) {
        super(context, R.layout.activity_nt_dsvt_item, data);
        this.mContext = context;
        this.mData = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_nt_dsvt_item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(mData.get(position));
        return convertView;
    }
}
