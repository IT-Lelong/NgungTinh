package com.lelong.ngungtinh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GridView_Adapter_lv_dsdata1 extends ArrayAdapter<nt_lv_dsdata1> {
    Context context;
    int resource;
    List<nt_lv_dsdata1> objects;

    public GridView_Adapter_lv_dsdata1(@NonNull Context context, int resource, ArrayList<nt_lv_dsdata1> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    private class ViewHolder {
        TextView stt;
        TextView vtri;
        TextView doncong;
        TextView quycach;
        TextView msac;
        TextView dcuc;
        TextView sluong;
    }

    @SuppressLint({"WrongViewCast", "ResourceType"})
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resource, null);

            holder.stt = (TextView) convertView.findViewById(R.id.ntds_stt);
            holder.vtri = (TextView) convertView.findViewById(R.id.ntds_vitri);
            holder.doncong = (TextView) convertView.findViewById(R.id.ntds_doncong);
            holder.quycach = (TextView) convertView.findViewById(R.id.ntds_quycach);
            holder.msac = (TextView) convertView.findViewById(R.id.ntds_mausac);
            holder.dcuc = (TextView) convertView.findViewById(R.id.ntds_diencuc);
            holder.sluong = (TextView) convertView.findViewById(R.id.ntds_sl);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        nt_lv_dsdata1 vt = getItem(position);
        if (vt != null) {
            holder = (ViewHolder) convertView.getTag();
            // Gán giá trị cho các TextView
            holder.stt.setText(String.valueOf(position + 1));
            holder.vtri.setText(objects.get(position).getImg02());
            holder.doncong.setText(objects.get(position).getImg03());
            holder.quycach.setText(objects.get(position).getImg01());
            holder.msac.setText(objects.get(position).getTen());
            holder.dcuc.setText(String.valueOf(objects.get(position).getIma27()));
            holder.sluong.setText(String.valueOf(objects.get(position).getImg10()));

        }

        return convertView;
    }
}
