package com.lelong.ngungtinh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NT_DSVT extends AppCompatActivity implements ntds_interface {
    String ID, g_INOUT, conf_xuong, conf_khu, l_khu, l_tt1;
    Cursor cursor_1, cursor_2;
    private Create_Table createTable = null;
    String[] station = new String[0];
    ListView lv_dsdata1;
    String g_server = "";
    EditText dg_soluong;
    Button btn_conf2;
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
    private update_data Update_data = null;

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

    }

    private void load_data1(String conf_xuong, String conf_khu, String l_vtri) {

        Cursor cursor = createTable.getAll_ntds_data(conf_xuong, conf_khu, l_vtri);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.activity_nt_dsvt_row, cursor,
                new String[]{"_id", "sdata03", "sdata04", "sdata06","sdata08","sdata09","sdata05"},
                new int[]{R.id.ntds_stt, R.id.ntds_vitri, R.id.ntds_doncong, R.id.ntds_quycach,R.id.ntds_mausac,R.id.ntds_diencuc, R.id.ntds_sl},
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

        lv_dsdata1.setOnItemClickListener((parent, view, position, id) -> {

            // Tạo đối tượng PopupMenu
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END, 0, R.style.MyPopupMenu);

            // Nạp tệp menu vào PopupMenu
            popupMenu.getMenuInflater().inflate(R.menu.nt_dsvt_even, popupMenu.getMenu());

            // Show the PopupMenu.
            popupMenu.show();

            // Đăng ký sự kiện Popup Menu
            popupMenu.setOnMenuItemClickListener(item -> {

                TextView dsvt_stt = view.findViewById(R.id.ntds_stt);
                TextView dsvt_vitri = view.findViewById(R.id.ntds_vitri);
                TextView dsvt_doncong = view.findViewById(R.id.ntds_doncong);
                TextView dsvt_qc = view.findViewById(R.id.ntds_quycach);
                TextView dsvt_sl = view.findViewById(R.id.ntds_sl);
                TextView dsvt_mausac = view.findViewById(R.id.ntds_mausac);
                TextView dsvt_diencuc = view.findViewById(R.id.ntds_diencuc);


                // Xử lý sự kiện khi người dùng chọn một lựa chọn trong menu
                switch (item.getItemId()) {
                    case R.id.post_dt:
                        if (g_INOUT.equals("OUT")) {
                            Dialog nt_dialog2 = new Dialog(view.getContext());
                            nt_dialog2.setContentView(R.layout.nt_dialog2);

                            dg_soluong = nt_dialog2.findViewById(R.id.dg_soluong);
                            dg_soluong.setText(dsvt_sl.getText());
                            btn_conf2 = nt_dialog2.findViewById(R.id.btn_conf2);
                            LocalTime now = LocalTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
                            String formattedTime = now.format(formatter);
                            System.out.println(formattedTime);
                            btn_conf2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String l_xuong2 = conf_xuong;
                                    String l_khu2 = conf_khu;
                                    String l_vitri2 = dsvt_vitri.getText().toString().trim();
                                    String l_doncong2 = dsvt_doncong.getText().toString().trim();
                                    String l_quycach2 = dsvt_qc.getText().toString().trim();
                                    String l_mausac  = dsvt_mausac.getText().toString().trim();
                                    String l_diencuc = dsvt_diencuc.getText().toString().trim();
                                    Integer l_trave = sum_total2(l_xuong2, l_khu2, l_vitri2, l_doncong2);
                                    String res = "";
                                    //tv_ngayvao.setText(dateFormat1.format(new Date()).toString());
                                    String ngay_vao = dateFormat1.format(new Date()).toString();

                                    if (Integer.parseInt(dg_soluong.getText().toString()) > l_trave) {
                                        String message = String.format("輸入數量超過庫存量 Số lượng vượt quá lượng tồn kho: %s", l_trave);
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        //update dữ liệu vào table tổng
                                        createTable.upd_total_sdata(conf_xuong, conf_khu,
                                                l_vtri, l_doncong2, dg_soluong.getText().toString().trim().replace(",", ""), g_INOUT);

                                        //Insert dữ liệu lưu vào table lịch sử quét
                                        String str2 = conf_xuong.concat("/").concat(conf_khu).concat("/").concat(l_vtri).
                                                concat("/").concat(g_INOUT).concat("/").concat(ngay_vao)
                                                .concat("/").concat(formattedTime).concat("/").concat(l_doncong2);
                                        String s_date2 = "";
                                        res = createTable.insScanData(str2, l_doncong2, l_quycach2,
                                                dg_soluong.getText().toString().replace(",", ""),
                                                "",
                                                s_date2,
                                                ngay_vao, conf_xuong, conf_khu, l_vtri, g_INOUT, ID, ngay_vao,l_mausac,l_diencuc, "");

                                        if (res.equals("TRUE")) {
                                            res_interface resInterface = new res_interface() {
                                                @Override
                                                public void loadData2(String l_res) {
                                                    runOnUiThread(new Runnable() {
                                                        public void run() {
                                                            if (l_res.equals("TRUE")) {
                                                                Toast.makeText(getApplicationContext(), "完成存放 Lưu trữ hoàn tất!", Toast.LENGTH_SHORT).show();
                                                                load_data1(conf_xuong,conf_khu,l_vtri);
                                                                nt_dialog2.dismiss();
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "存放失敗 Lưu trữ thất bại! (2)", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            };

                                            Update_data = new update_data(NT_DSVT.this, g_server, str2, resInterface);
                                            Update_data.up_oracle();

                                        } else {
                                            //Toast.makeText(this, "存放失敗 Lưu trữ thất bại (1)", Toast.LENGTH_SHORT).show();
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(), "存放失敗 Lưu trữ thất bại (1)", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                            //return true;
                            nt_dialog2.show();
                        } else {
                            Toast.makeText(getApplicationContext(), "這個功能不能用進庫 Chức năng này không thể sử dụng quét nhập", Toast.LENGTH_SHORT).show();
                        }
                }
                return true;
            });
        });

    }

    @Override
    public void loadData1(String conf_xuong, String conf_khu, String l_vtri) {
        load_data1(conf_xuong, conf_khu, l_vtri);
    }


    private int sum_total2(String tt_xuong, String tt_khu, String tt_vitri, String tt_doncong) {
        cursor_2 = createTable.getAll_check_total_sdata2(tt_xuong, tt_khu, tt_vitri, tt_doncong);
        if (cursor_2 != null && cursor_2.moveToFirst()) {
            int n_count22 = cursor_2.getInt(0);
            cursor_2.close(); // đóng Cursor sau khi sử dụng xong
            return n_count22;
        } else {
            return 0;
        }
    }
}