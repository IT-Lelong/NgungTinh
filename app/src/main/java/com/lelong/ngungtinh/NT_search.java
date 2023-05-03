package com.lelong.ngungtinh;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Looper;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//23041001 by Andy modify


public class NT_search extends AppCompatActivity {
    EditText /*edt_vaont,*/ edt_dc;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle getbundle = getIntent().getExtras();
        setContentView(R.layout.activity_nt_search);
        g_server = getbundle.getString("SERVER");

        //edt_vaont = findViewById(R.id.edt_vaont);
        //edt_rant = findViewById(R.id.edt_rant);
        edt_dc = findViewById(R.id.edt_dc);
        cbx_qc = findViewById(R.id.cbx_qc);
        cbx_x = findViewById(R.id.cbx_x);
        btn_Search = findViewById(R.id.btn_Search);
        lv_search = findViewById(R.id.lv_search);
        //g_server = "PHP";
        //g_server = "PHPtest";
        createTable = new Create_Table(this);
        createTable.open();
        //23041001 by Andy added (S)
        createTable.del_total();
        getcode_up();
        //23041001 by Andy added (E)
//23041001 by Andy mark (S)
        /*if (cbx_qc != null && cbx_qc.getCount() > 0) {
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
        }*/
        /*edt_vaont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(NT_search.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        //edt_vaont.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        edt_vaont.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });
        edt_rant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(NT_search.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        edt_rant.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        //edt_rant.setText(dateFormat.format((dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });*/
        //23041001 by Andy mark (E)
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
                String g_xuong = "";
                String g_qc = "";
                String g_dc = edt_dc.getText().toString().trim();
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
                Cursor cursor = createTable.getAll_search_data(g_dc, g_qc, g_xuong);
                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(NT_search.this,
                        R.layout.activity_nt_search_row, cursor,
                        new String[]{"_id", "sdata01", "sdata02", "sdata03", "sdata04", "sdata06", "sdata05"},
                        //new int[]{R.id.tv_stt, R.id.tv_dc, R.id.tv_qc, R.id.tv_sl, R.id.tv_xuong, R.id.tv_khu, R.id.tv_vitri},
                        new int[]{R.id.tv_stt, R.id.tv_xuong, R.id.tv_khu, R.id.tv_vitri, R.id.tv_dc, R.id.tv_qc, R.id.tv_sl},
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
        // cursor_1.moveToNext();
    }


    //private int check_count(String lc_xuong, String lc_khu, String lc_vitri) {
    //cursor_1 = createTable.getAll_count_01(lc_xuong, lc_khu, lc_vitri);
    //cursor_1.moveToFirst();
    //int n_count = cursor_1.getCount();
    // return n_count;
    // }

    //private void load_data() {

    //Cursor cursor = createTable.getAll_setup_data();
    //SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
    //    R.layout.nt_setupdata_row, cursor,
    //   new String[]{"_id", "setup01", "setup02", "setup03"},
    //   new int[]{R.id.st_stt, R.id.st_plant, R.id.st_region, R.id.st_cpos},
    //   SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

    //simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
    //   @Override
    //  public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
    //     if (view.getId() == R.id.st_stt) {
    //        int rowNumber = cursor.getPosition() + 1;
    //        ((TextView) view).setText(String.valueOf(rowNumber));
    //        return true;
    //    }
    //    return false;
    // }
    // });

    //  lv_setup.setAdapter(simpleCursorAdapter);

    //}

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
                            createTable.append_total(g_sdata01, g_sdata02, g_sdata03, g_sdata04, g_sdata05, g_sdata06);
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