package com.lelong.ngungtinh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NT_DSVT extends AppCompatActivity implements ntds_interface {
    String ID, g_INOUT, conf_xuong, conf_khu, l_khu, l_tt1;
    Cursor cursor_1, cursor_2;
    private Create_Table createTable = null;
    String[] station = new String[0];
    ListView lv_dsdata1;
    String g_server = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nt_dsvt);

        Bundle getbundle = getIntent().getExtras();
        ID = getbundle.getString("ID");
        g_INOUT = getbundle.getString("INOUT");
        conf_xuong = getbundle.getString("XUONG");
        conf_khu = getbundle.getString("KHU");
        lv_dsdata1 = findViewById(R.id.lv_dsdata1);
        g_server = getbundle.getString("SERVER");

        GridView gridView = findViewById(R.id.gridView_DS);
        List<String> data = new ArrayList<>();

        if (g_INOUT.equals("IN")) {
            l_tt1 = "Quét nhập";
        } else {
            l_tt1 = "Quét xuất";
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Xưởng： " + conf_xuong + "  Khu:  " + conf_khu + "  Trạng thái:  " + l_tt1);

        createTable = new Create_Table(this);
        createTable.open();

        cursor_1 = createTable.getAll_stand_01(conf_xuong, conf_khu);
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        station = new String[num];

        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String setup03 = cursor_1.getString(cursor_1.getColumnIndex("setup03"));
                int number = Integer.parseInt(setup03);
                String g_setup03 = setup03;
                l_khu = g_setup03;
                station[i] = g_setup03;

            } catch (Exception e) {
                String err = e.toString();
            }
            data.add(l_khu);
            cursor_1.moveToNext();
        }


        GridView_Adapter adapter = new GridView_Adapter(this, data, ID, g_INOUT, conf_xuong, conf_khu, lv_dsdata1, this, g_server);
        gridView.setAdapter(adapter);

        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });*/
    }

    private void load_data1(String conf_xuong, String conf_khu, String l_vtri) {

        Cursor cursor = createTable.getAll_ntds_data(conf_xuong, conf_khu, l_vtri);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.activity_nt_dsvt_row, cursor,
                new String[]{"_id", "sdata03", "sdata04", "sdata06", "sdata05"},
                new int[]{R.id.ntds_stt, R.id.ntds_vitri, R.id.ntds_doncong, R.id.ntds_quycach, R.id.ntds_sl},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.ntds_stt) {
                    int rowNumber = cursor.getPosition() + 1;
                    ((TextView) view).setText(String.valueOf(rowNumber));
                    return true;
                }
                return false;
            }
        });

        lv_dsdata1.setAdapter(simpleCursorAdapter);

    }

    @Override
    public void loadData1(String conf_xuong, String conf_khu, String l_vtri) {
        load_data1(conf_xuong, conf_khu, l_vtri);
    }

}