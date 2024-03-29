package com.lelong.ngungtinh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
//23041001 by Andy modify


public class NT_search extends AppCompatActivity {
    EditText /*edt_vaont,*/ edt_dc;
    EditText edt_mausac, edt_daucuc;  // mausacdaucuc
    DatePickerDialog datePickerDialog;
    //EditText edt_rant;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    ;
    Button btn_Search;
    ListView lv_search;
    Spinner cbx_qc, cbx_x;
    Cursor cursor_1, cursor_2;
    String[] station = new String[0];
    ArrayAdapter<String> stationlist;
    String g_server;
    private Create_Table createTable = null;
    TextView tv_bdate, tv_edate;
    DecimalFormat decimalFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle getbundle = getIntent().getExtras();
        setContentView(R.layout.activity_nt_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //setContentView(R.layout.demo);
        g_server = getbundle.getString("SERVER");

        //edt_vaont = findViewById(R.id.edt_vaont);
        //edt_rant = findViewById(R.id.edt_rant);
        //edt_dc = findViewById(R.id.edt_dc);
        cbx_qc = findViewById(R.id.cbx_qc);
        cbx_x = findViewById(R.id.cbx_x);
        btn_Search = findViewById(R.id.btn_Search);
        lv_search = findViewById(R.id.lv_search);
        //mausac,dauccuc
        edt_mausac = findViewById(R.id.edt_mausac);
        edt_daucuc = findViewById(R.id.edt_daucuc);
        tv_bdate = findViewById(R.id.tv_bdate);
        tv_edate = findViewById(R.id.tv_edate);

        String pattern = "#,###.##";
        decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        decimalFormat.applyPattern(pattern);
        //g_server = "PHP";
        //g_server = "PHPtest";
        createTable = new Create_Table(this);
        createTable.open();
        //23041001 by Andy added (S)
        createTable.del_total();
        getcode_up();
        //23041001 by Andy added (E)
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String g_vaont = "";
                //String g_rant = "";
                //Date date1 = null;
                //Date date2 = null;
                /*try {
                    if (!(edt_vaont.getText().toString().trim()).equals("")) {
                        date1 = dateFormat.parse(edt_vaont.getText().toString().trim());
                        g_vaont = dateFormat.format(date1);
                    }
                    if (!(edt_rant.getText().toString().trim()).equals("")) {
                        date2 = dateFormat.parse(edt_rant.getText().toString().trim());
                        g_rant = dateFormat.format(date2);
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }*/
                //mausac,daucuc
                String g_mausac = edt_mausac.getText().toString().trim();
                String g_daucuc = edt_daucuc.getText().toString().trim();
                //
                String g_xuong = "";
                String g_qc = "";

                //String g_dc = edt_dc.getText().toString().trim();
                if (cbx_x.getSelectedItem() == null) {
                    g_xuong = "";
                } else {
                    g_xuong = cbx_x.getSelectedItem().toString().trim();
                }
                if (cbx_qc.getSelectedItem() == null) {
                    g_qc = "";
                } else {
                    g_qc = cbx_qc.getSelectedItem().toString().trim();
                }

                /*if (!g_vaont.equals("") && g_rant.equals("")) {
                    g_rant = dateFormat.format(Calendar.getInstance().getTime());
                } else if (g_vaont.equals("") && !g_rant.equals("")) {
                    g_vaont = g_rant;
                }*/

                String l_ngaybd = tv_bdate.getText().toString().trim();
                String l_ngaykt = tv_edate.getText().toString().trim();
                /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate bdate = LocalDate.parse(tv_bdate.getText().toString().trim(), formatter);
                LocalDate edate = LocalDate.parse(tv_edate.getText().toString().trim(), formatter);

                String formattl_ngaybd = bdate.format(String.valueOf(formatter));
                String formattl_ngaykt = edate.format(String.valueOf(formatter));*/

                Cursor cursor = createTable.getAll_search_data("", g_qc, g_xuong, g_mausac, g_daucuc, l_ngaybd, l_ngaykt);
                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(NT_search.this,
                        R.layout.activity_nt_search_row, cursor,
                        new String[]{"_id", "sdata01", "sdata02", "sdata03", "sdata06", "sdata05", "sdata08", "sdata09", "sdata07"},
                        //new int[]{R.id.tv_stt, R.id.tv_dc, R.id.tv_qc, R.id.tv_sl, R.id.tv_xuong, R.id.tv_khu, R.id.tv_vitri},
                        new int[]{R.id.tv_stt, R.id.tv_xuong, R.id.tv_khu, R.id.tv_vitri, R.id.tv_qc, R.id.tv_sl, R.id.tv_mausac, R.id.tv_dauccuc, R.id.tv_date},
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

                simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                        if (view.getId() == R.id.tv_stt) {
                            int rowNumber = cursor.getPosition() + 1;
                            ((TextView) view).setText(String.valueOf(rowNumber));
                            return true;
                        }
                        return false;
                    }
                });

                lv_search.setAdapter(simpleCursorAdapter);
                if (lv_search.getCount() == 0) {
                    Toast.makeText(NT_search.this, "沒有可用的查找數據 Không có dữ liệu tra cứu", Toast.LENGTH_SHORT).show();
                }
            }

        });

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int id = v.getId();
                final int DRAWABLE_RIGHT = 2;
                switch (id) {
                    case R.id.tv_bdate:
                        // xử lý khi nhấn vào TextView tv_bdate_TraHang
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (event.getRawX() >= (tv_bdate.getRight() - tv_bdate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                showDatePickerDialog(tv_bdate, v);
                                return true;
                            }
                        }
                        break;
                    case R.id.tv_edate:
                        // xử lý khi nhấn vào TextView tv_edate_TraHang
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (event.getRawX() >= (tv_edate.getRight() - tv_edate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                showDatePickerDialog(tv_edate, v);
                                return true;
                            }
                        }
                        break;
                }
                return false;
            }
        };
        // set sự kiện onTouchListener cho 4 TextView
        tv_bdate.setOnTouchListener(touchListener);
        tv_edate.setOnTouchListener(touchListener);
        //btn_Insert.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View view) {

        //String g_xuong = st_plant.getText().toString().trim();
        //String g_khu = st_region.getText().toString().trim();
        //String g_vitri = st_cpos.getText().toString().trim();
        //if (g_xuong.equals("") || g_khu.equals("") || g_vitri.equals("")) {
        //Toast.makeText(NT_Setup_data.this, "Cần phải nhập dữ liệu mới có thể thêm mới", Toast.LENGTH_SHORT).show();
        //} else {
        //Integer dcount = check_count(g_xuong, g_khu, g_vitri);
        //if (dcount > 0) {
        //Toast.makeText(NT_Setup_data.this, "Dữ liệu đã tồn tại", Toast.LENGTH_SHORT).show();
        //} else {
        //createTable.insSetup_data(g_xuong,
        //st_region.getText().toString().trim(),
        //st_cpos.getText().toString().trim());
        //load_data();
        //}
        //}
        //}
        //});
        //load_data();
    }

    private void showDatePickerDialog(TextView g_textView, View v) {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                g_textView.setText(year + "/" + String.format("%02d", monthOfYear) + "/" + String.format("%02d", dayOfMonth));

                if (g_textView.getId() == R.id.tv_bdate && tv_edate.getText().toString().length() == 0) {
                    tv_edate.setText(tv_bdate.getText().toString().trim());
                }

                if (g_textView.getId() == R.id.tv_edate) {
                    // Chuyển đổi chuỗi thành LocalDate
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");
                    LocalDate bdate = LocalDate.parse(tv_bdate.getText().toString().trim(), formatter);
                    LocalDate edate = LocalDate.parse(tv_edate.getText().toString().trim(), formatter);

                    // So sánh hai ngày
                    if (bdate.compareTo(edate) > 0) {
                        // bdate lớn hơn edate
                        Toast.makeText(NT_search.this, "Ngày bắt đẩu không thể lớn hơn ngày kết thúc", Toast.LENGTH_SHORT).show();
                        tv_edate.setText(tv_bdate.getText().toString().trim());
                    } else if (bdate.compareTo(edate) < 0) {
                        // bdate nhỏ hơn edate
                    } else {
                        // bdate bằng edate
                    }
                }
            }
        };
        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        int ngay = currentDate.getDayOfMonth();
        int thang = currentDate.getMonthValue();
        int nam = currentDate.getYear();

        ;
        DatePickerDialog pic = new DatePickerDialog(
                v.getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                callback, nam, thang, ngay);
        pic.show();
    }

    //Kiểm tra đã có dữ liệu tồn tại trong table total hay chưa
    private int check_total(String tt_xuong, String tt_khu, String tt_vitri, String tt_doncong) {
        cursor_1 = createTable.getAll_count_total_sdata(tt_xuong, tt_khu, tt_vitri, tt_doncong);
        cursor_1.moveToFirst();
        int n_count = cursor_1.getInt(0);
        return n_count;
    }

    private void check_plant() {
        cursor_1 = createTable.getAll_sdata_06();
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        station = new String[num + 1];
        //station = new String[1];

        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String sdata06 = cursor_1.getString(cursor_1.getColumnIndex("sdata06"));

                String g_sdata06 = sdata06; //Quy cách
                station[i] = g_sdata06;

            } catch (Exception e) {
                String err = e.toString();
            }
            cursor_1.moveToNext();
        }
        station[num] = " ";
        stationlist = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, station) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };
        stationlist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbx_qc.setAdapter(stationlist);
        cbx_qc.setSelection(stationlist.getCount());
        //cursor_1.moveToNext();
    }

    private void check_region(String lquycach) {
        cursor_1 = createTable.getAll_sdata_01(lquycach);
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        station = new String[num + 1];

        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String sdata01 = cursor_1.getString(cursor_1.getColumnIndex("sdata01"));
                String g_sdata01 = sdata01;
                station[i] = g_sdata01;

            } catch (Exception e) {
                String err = e.toString();
            }
            cursor_1.moveToNext();
        }
        station[num] = " ";
        stationlist = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, station) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };
        stationlist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbx_x.setAdapter(stationlist);
        cbx_x.setSelection(stationlist.getCount());

    }


    private void getcode_up() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                final String res = getcodedata("http://172.16.40.20/" + g_server + "/WMS/getDataSetup.php?item=search");
                if (!res.equals("FALSE")) {
                    try {
                        JSONArray jsonarray = new JSONArray(res);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            String g_sdata01 = jsonObject.getString("TC_BAD001"); //Xưởng
                            String g_sdata02 = jsonObject.getString("TC_BAD002"); //Khu
                            String g_sdata03 = jsonObject.getString("TC_BAD003"); //Vị trí con
                            String g_sdata04 = jsonObject.getString("TC_BAD004"); //Xưởng
                            String g_sdata05 = jsonObject.getString("TC_BAD005"); //Khu
                            String g_sdata06 = jsonObject.getString("TC_BAD006"); //Vị trí con
                            String g_sdata07 = jsonObject.getString("NGAY"); //Ngày
                            String g_sdata08 = jsonObject.getString("TC_BAD007"); //Màu sắc
                            String g_sdata09 = jsonObject.getString("TC_BAD008"); //đầu cực
                            createTable.append_total(g_sdata01, g_sdata02, g_sdata03, g_sdata04, g_sdata05, g_sdata06, g_sdata07, g_sdata08, g_sdata09);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                check_plant();
                                if (cbx_qc != null && cbx_qc.getCount() > 0) {
                                    cbx_qc.getSelectedItem().toString();
                                    String s_quycach = cbx_qc.getSelectedItem().toString();
                                    check_region(s_quycach);
                                    cbx_qc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if (position >= 0) {
                                                cbx_qc.getSelectedItem().toString();
                                                String s_quycach = cbx_qc.getSelectedItem().toString();
                                                check_region(s_quycach);
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }

                                    });
                                }
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