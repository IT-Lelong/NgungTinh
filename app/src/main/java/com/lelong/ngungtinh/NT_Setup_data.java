package com.lelong.ngungtinh;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class NT_Setup_data extends AppCompatActivity {

    EditText st_plant;
    EditText st_region;
    EditText st_cpos;
    Button btn_Insert;
    ListView lv_setup;
    Cursor cursor_1, cursor_2;

    private Create_Table createTable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nt_setup_data);

        st_plant = findViewById(R.id.st_plant);
        st_region = findViewById(R.id.st_region);
        st_cpos = findViewById(R.id.st_cpos);
        btn_Insert = findViewById(R.id.btn_Insert);
        lv_setup = findViewById(R.id.lv_setup);

        createTable = new Create_Table(this);
        createTable.open();


        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String g_xuong = st_plant.getText().toString().trim();
                String g_khu = st_region.getText().toString().trim();
                String g_vitri = st_cpos.getText().toString().trim();
                if (g_xuong.equals("") || g_khu.equals("") || g_vitri.equals("")) {
                    Toast.makeText(NT_Setup_data.this, "Cần phải nhập dữ liệu mới có thể thêm mới", Toast.LENGTH_SHORT).show();
                } else {
                    Integer dcount = check_count(g_xuong, g_khu, g_vitri);
                    if (dcount > 0) {
                        Toast.makeText(NT_Setup_data.this, "Dữ liệu đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        createTable.insSetup_data(g_xuong,
                                st_region.getText().toString().trim(),
                                st_cpos.getText().toString().trim());
                        load_data();
                    }
                }
            }
        });
        load_data();
    }

    private int check_count(String lc_xuong, String lc_khu, String lc_vitri) {
        cursor_1 = createTable.getAll_count_01(lc_xuong, lc_khu, lc_vitri);
        cursor_1.moveToFirst();
        int n_count = cursor_1.getCount();
        return n_count;
    }

    private void load_data() {

        Cursor cursor = createTable.getAll_setup_data();
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.nt_setupdata_row, cursor,
                new String[]{"_id", "setup01", "setup02", "setup03"},
                new int[]{R.id.st_stt, R.id.st_plant, R.id.st_region, R.id.st_cpos},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.st_stt) {
                    int rowNumber = cursor.getPosition() + 1;
                    ((TextView) view).setText(String.valueOf(rowNumber));
                    return true;
                }
                return false;
            }
        });

        lv_setup.setAdapter(simpleCursorAdapter);

    }
}