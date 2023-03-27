package com.lelong.ngungtinh;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class NT_DSVT extends AppCompatActivity {
    String ID, g_INOUT, conf_xuong, conf_khu,l_khu;
    Cursor cursor_1, cursor_2;
    private Create_Table createTable = null;
    String[] station = new String[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nt_dsvt);

        Bundle getbundle = getIntent().getExtras();
        ID = getbundle.getString("ID");
        g_INOUT = getbundle.getString("INOUT");
        conf_xuong = getbundle.getString("XUONG");
        conf_khu = getbundle.getString("KHU");

        GridView gridView = findViewById(R.id.gridView_DS);
        List<String> data = new ArrayList<>();

        createTable = new Create_Table(this);
        createTable.open();

        cursor_1 = createTable.getAll_stand_01(conf_xuong, conf_khu);
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        station = new String[num];

        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String setup03 = cursor_1.getString(cursor_1.getColumnIndex("setup03"));
                String g_setup03 = setup03;
                l_khu = g_setup03;
                station[i] = g_setup03;

            } catch (Exception e) {
                String err = e.toString();
            }
            data.add(l_khu);
            cursor_1.moveToNext();
        }



        GridView_Adapter adapter = new GridView_Adapter(this, data);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor_1.moveToPosition(i);
                String l_vtri = cursor_1.getString(cursor_1.getColumnIndexOrThrow("setup03"));

                Intent SCAN = new Intent();
                SCAN.setClass(NT_DSVT.this, OpenScaner.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", ID);
                bundle.putString("INOUT", g_INOUT);
                bundle.putString("XUONG", conf_xuong);
                bundle.putString("KHU", conf_khu);
                bundle.putString("VITRI", l_vtri);
                SCAN.putExtras(bundle);
                startActivity(SCAN);
            }
        });
    }
}