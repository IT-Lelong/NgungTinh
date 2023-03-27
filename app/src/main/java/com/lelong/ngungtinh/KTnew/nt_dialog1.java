package com.lelong.ngungtinh.KTnew;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.lelong.ngungtinh.Create_Table;
import com.lelong.ngungtinh.R;

public class nt_dialog1 extends AppCompatActivity {

    Spinner cbx_xuong;
    Spinner cbx_dkhu;
    Button btn_dconf;
    private Create_Table createTable = null;
    Cursor cursor_1, cursor_2;
    String[] station = new String[0];
    ArrayAdapter<String> stationlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nt_dialog1);
//Xưởng
        cbx_xuong = findViewById(R.id.cbx_dxuong);
        createTable = new Create_Table(this);
        createTable.open();

        cursor_1 = createTable.getAll_setup_01();
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        station = new String[num];
        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String setup01 = cursor_1.getString(cursor_1.getColumnIndex("setup01"));

                String g_setup01 = setup01;
                station[i] = g_setup01;

            } catch (Exception e) {
                String err = e.toString();
            }
            stationlist = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, station);
            cbx_xuong.setAdapter(stationlist);
            cbx_xuong.setSelection(0);
            cursor_1.moveToNext();

//Khu
        cbx_dkhu = findViewById(R.id.cbx_dkhu);
        btn_dconf = findViewById(R.id.btn_dconf);

        /*cursor_1 = createTable.getAll_fia_02();
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        station = new String[num];
        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String fiaud03 = cursor_1.getString(cursor_1.getColumnIndex("fiaud03"));

                String g_fiaud03 = fiaud03;
                station[i] = g_fiaud03;

            } catch (Exception e) {
                String err = e.toString();
            }
            stationlist = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_spinner_item, station);
            cbxsoxe.setAdapter(stationlist);
            cbxsoxe.setSelection(0);
            cursor_1.moveToNext();
        }*/


    }
}
}
