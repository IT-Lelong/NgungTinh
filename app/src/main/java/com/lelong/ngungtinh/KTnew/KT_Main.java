package com.lelong.ngungtinh.KTnew;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lelong.ngungtinh.Create_Table;
import com.lelong.ngungtinh.R;

import java.util.ArrayList;

public class KT_Main extends AppCompatActivity {
    String g_xuong;
    private Create_Table db = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kt_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        db = new Create_Table(this);
        db.open();

        Button btnok = findViewById(R.id.btnok);

        Spinner sp_xuong = findViewById(R.id.cbxxuong);

        String[] type = {"C", "D", "I"};
        ArrayAdapter<String> typelist = new ArrayAdapter<>(KT_Main.this,
                android.R.layout.simple_spinner_dropdown_item,
                type);
        sp_xuong.setAdapter(typelist);
        //sp_EdgeIP.setSelection(0);

        sp_xuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                g_xuong = sp_xuong.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.btnok: {

                        Intent QR020 = new Intent();
                        QR020.setClass(KT_Main.this, MainActivity2.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("xuong", g_xuong);
                        QR020.putExtras(bundle);
                        startActivity(QR020);

                        break;
                    }
                }
            }
        });


        BarChart barChart = findViewById(R.id.barchart);
        barChart.setDrawBarShadow(false);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 10));
        barEntries.add(new BarEntry(2, 90));
        barEntries.add(new BarEntry(3, 65));
        //barEntries.add(new BarEntry(4, 50));

        ArrayList<String> labels = new ArrayList<>();
        labels.add("C棟");
        labels.add("D棟");
        labels.add("I棟");
        //labels.add("Column 4");

        BarDataSet barDataSet = new BarDataSet(barEntries, "棟別");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);
        barChart.setData(barData);
        barChart.invalidate();
    }

}