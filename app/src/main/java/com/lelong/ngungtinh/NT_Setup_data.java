package com.lelong.ngungtinh;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NT_Setup_data extends AppCompatActivity {

    EditText st_plant;
    EditText st_region;
    EditText st_cpos;
    EditText st_sl;
    Button btn_Insert;
    ListView lv_setup;
    Cursor cursor_1, cursor_2;
    JSONArray djsonupload, jsonupload;
    JSONObject ujobject;
    String g_server = "";
    String vitriStr = "";
    private Create_Table db = null;

    private Create_Table createTable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nt_setup_data);
        Bundle getbundle = getIntent().getExtras();

        st_plant = findViewById(R.id.st_plant);
        st_region = findViewById(R.id.st_region);
        st_cpos = findViewById(R.id.st_cpos);
        //st_sl = findViewById(R.id.st_sl);
        btn_Insert = findViewById(R.id.btn_Insert);
        lv_setup = findViewById(R.id.lv_setup);

        g_server = getbundle.getString("SERVER");

        createTable = new Create_Table(this);
        createTable.open();


        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String g_xuong = st_plant.getText().toString().trim();
                String g_khu = st_region.getText().toString().trim();
                String g_vitri = st_cpos.getText().toString().trim();

                if (g_vitri.length() == 1){
                    vitriStr = "0".concat(g_vitri);
                } else {
                    vitriStr = String.valueOf(g_vitri);
                }

                //String g_sl = st_sl.getText().toString().trim();
                if (g_xuong.equals("") || g_khu.equals("") || vitriStr.equals("") /*|| g_sl.equals("")*/) {
                    Toast.makeText(NT_Setup_data.this, "Cần phải nhập dữ liệu mới có thể thêm mới", Toast.LENGTH_SHORT).show();
                } else {
                    Integer dcount = check_count(g_xuong, g_khu, vitriStr);
                    if (dcount > 0) {
                        Toast.makeText(NT_Setup_data.this, "Dữ liệu đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        createTable.insSetup_data(g_xuong,
                                st_region.getText().toString().trim(),
                                //st_cpos.getText().toString().trim());
                                vitriStr);
                    }
                    db = new Create_Table(NT_Setup_data.this);
                    db.open();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String bien = "A";
                            Cursor upl = db.getAll_tc_bae(g_xuong, g_khu, vitriStr);
                            if (upl.getCount() > 0) {

                                jsonupload = cur2Json(upl);

                                try {
                                    ujobject = new JSONObject();
                                    ujobject.put("ejson", jsonupload);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                final String res = upload_all("http://172.16.40.20/" + g_server + "/WMS/upload_4.php");
                                //final String res = upload_all("http://172.16.40.20/" + g_server + "/WMS/uploadTest.php");

                                runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                                    @Override
                                    public void run() {
                                        if (res.contains("false")) {
                                            //Toast.makeText(getApplicationContext(), getString(R.string.M09), Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getApplicationContext(), "Thêm mới dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            //Toast.makeText(getApplicationContext(), getString(R.string.M08), Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getApplicationContext(), "Thêm mới dữ liệu thành công ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }
                    }).start();
                    load_data();

                }
            }
        });
        createTable.del_setup();
        getcode_up();
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
                //new int[]{R.id.st_stt, R.id.st_plant, R.id.st_region, R.id.st_cpos, R.id.st_sl},
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

        lv_setup.setOnItemClickListener((parent, view, position, id) -> {

            // Tạo đối tượng PopupMenu
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END, 0, R.style.MyPopupMenu);

            // Nạp tệp menu vào PopupMenu
            popupMenu.getMenuInflater().inflate(R.menu.nt_setupdata_even, popupMenu.getMenu());

            // Show the PopupMenu.
            popupMenu.show();

            // Đăng ký sự kiện Popup Menu
            popupMenu.setOnMenuItemClickListener(item -> {

                TextView qry_xuong = view.findViewById(R.id.st_plant);
                TextView qry_khu = view.findViewById(R.id.st_region);
                TextView qry_vitri = view.findViewById(R.id.st_cpos);

                // Xử lý sự kiện khi người dùng chọn một lựa chọn trong menu
                switch (item.getItemId()) {
                    case R.id.clearsetup:
                        db = new Create_Table(NT_Setup_data.this);
                        db.open();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Cursor dupl = db.del_tc_bae(qry_xuong.getText().toString(), qry_khu.getText().toString(), qry_vitri.getText().toString());
                                if (dupl.getCount() > 0) {

                                    djsonupload = cur2Json(dupl);

                                    try {
                                        ujobject = new JSONObject();
                                        ujobject.put("djson", djsonupload);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    final String res = upload_all("http://172.16.40.20/" + g_server + "/WMS/upload_5.php");
                                    //final String res = upload_all("http://172.16.40.20/" + g_server + "/WMS/uploadTest.php");

                                    runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                                        @Override
                                        public void run() {
                                            if (res.contains("false")) {
                                                //Toast.makeText(getApplicationContext(), getString(R.string.M09), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getApplicationContext(), "Xóa dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //Toast.makeText(getApplicationContext(), getString(R.string.M08), Toast.LENGTH_SHORT).show();
                                                createTable.del_setup_data_file(qry_xuong.getText().toString(), qry_khu.getText().toString(), qry_vitri.getText().toString());
                                                Toast.makeText(getApplicationContext(), "Xóa dữ liệu thành công ", Toast.LENGTH_SHORT).show();
                                                load_data();
                                            }
                                        }
                                    });

                                }
                            }
                        }).start();
                        return true;
                }
                return true;
            });
        });


    }

    public String upload_all(String apiUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(999999);
            conn.setReadTimeout(999999);
            conn.setDoInput(true); //允許輸入流，即允許下載
            conn.setDoOutput(true); //允許輸出流，即允許上傳

            OutputStream os = conn.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);
            writer.write(ujobject.toString().getBytes("UTF-8"));
            writer.flush();
            writer.close();
            os.close();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String result = reader.readLine();
            reader.close();
            return result;
        } catch (Exception ex) {
            return "false";
        } finally {
            if (conn != null) {

                conn.disconnect();

            }
        }
    }

    public JSONArray cur2Json(Cursor cursor) {
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        return resultSet;
    }

    private void getcode_up() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                final String res = getcodedata("http://172.16.40.20/" + g_server + "/WMS/getDataSetup.php?item=setup");
                if (!res.equals("FALSE")) {
                    try {
                        JSONArray jsonarray = new JSONArray(res);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            String g_setup01 = jsonObject.getString("TC_BAE001"); //Xưởng
                            String g_setup02 = jsonObject.getString("TC_BAE002"); //Khu
                            String g_setup03 = jsonObject.getString("TC_BAE003"); //Vị trí con
                            createTable.append_setup(g_setup01, g_setup02, g_setup03);
                        }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_data();
                        }
                    });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Looper.loop();
            }

        }).start();

    }


    private String getcodedata(String s) {
        try {
            HttpURLConnection conn = null;
            URL url = new URL(s);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(999999);
            conn.setReadTimeout(999999);
            conn.setDoInput(true); //允許輸入流，即允許下載
            conn.setDoOutput(true); //允許輸出流，即允許上傳
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String jsonstring = reader.readLine();
            reader.close();
            if (!jsonstring.equals("FALSE")) {
                return jsonstring;
            } else {
                return "FALSE";
            }
        } catch (Exception e) {
            return "FALSE";
        }
    }


}