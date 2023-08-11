package com.lelong.ngungtinh;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import java.util.List;

public class GridView_Adapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mData;
    String ID, g_INOUT, conf_xuong, conf_khu, l_vtri, g_server;
    Cursor cursor, cursor_1, cursor_2;
    private Create_Table createTable = null;
    String[] station = new String[0];
    ListView lv_dsdata1;
    ntds_interface ntds_interface;

    public GridView_Adapter(Context context, List<String> data, String ID, String g_INOUT, String conf_xuong, String conf_khu, ListView lv_dsdata1, ntds_interface ntds_interface, String g_server) {
        super(context, R.layout.activity_nt_dsvt_item, data);
        this.mContext = context;
        this.mData = data;
        this.ID = ID;
        this.g_INOUT = g_INOUT;
        this.conf_xuong = conf_xuong;
        this.conf_khu = conf_khu;
        this.lv_dsdata1 = lv_dsdata1;
        this.ntds_interface = ntds_interface;
        this.g_server = g_server;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_nt_dsvt_item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(mData.get(position));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String l_vtri = mData.get(position);

                Intent SCAN = new Intent();
                SCAN.setClass(mContext, OpenScaner.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", ID);
                bundle.putString("INOUT", g_INOUT);
                bundle.putString("XUONG", conf_xuong);
                bundle.putString("KHU", conf_khu);
                bundle.putString("VITRI", l_vtri);
                bundle.putString("SERVER", g_server);
                SCAN.putExtras(bundle);
                mContext.startActivity(SCAN);
            }
        });

        Button btn_IOScan = convertView.findViewById(R.id.btn_IOScan);
        btn_IOScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String l_vtri = mData.get(position);
                ntds_interface.loadData1(conf_xuong, conf_khu, l_vtri);



            }
        });

        return convertView;
    }

}
