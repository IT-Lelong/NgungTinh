package com.lelong.ngungtinh;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lelong.ngungtinh.KTnew.nt_dialog1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class Menu extends AppCompatActivity {

    private Create_Table Cre_db = null;
    String g_server = "";
    Button btn_NT01, btn_NT02, btn_NT03, btn_NT04, btn_NT05;
    TextView menuID;
    String ID;
    Locale locale;
    private CheckAppUpdate checkAppUpdate = null;
    //show dialog
    Spinner cbx_xuong;
    Spinner cbx_khu;
    Button btn_dconf;
    private Create_Table createTable = null;
    Cursor cursor_1, cursor_2;
    String[] station = new String[0];
    ArrayAdapter<String> stationlist;
    String v_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //actionBar = getSupportActionBar();
        //actionBar.hide();
        Bundle getbundle = getIntent().getExtras();
        ID = getbundle.getString("ID");
        g_server = getbundle.getString("SERVER");
        menuID = (TextView) findViewById(R.id.menuID);
        new IDname().execute("http://172.16.40.20/" + g_server + "/getid.php?ID=" + ID);

        Cre_db = new Create_Table(this);
        Cre_db.open();
        Cre_db.createTable();

        btn_NT01 = findViewById(R.id.btn_NT01);
        btn_NT02 = findViewById(R.id.btn_NT02);
        btn_NT03 = findViewById(R.id.btn_NT03);
        btn_NT04 = findViewById(R.id.btn_NT04);
        btn_NT05 = findViewById(R.id.btn_NT05);

        btn_NT01.setOnClickListener(btnlistener);
        btn_NT02.setOnClickListener(btnlistener);
        btn_NT03.setOnClickListener(btnlistener);
        btn_NT04.setOnClickListener(btnlistener);
        btn_NT05.setOnClickListener(btnlistener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAppUpdate = new CheckAppUpdate(this, g_server);
        checkAppUpdate.checkVersion();
    }

    //取得登入者姓名
    private class IDname extends AsyncTask<String, Integer, String> {
        String result = "";

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                result = reader.readLine();
                reader.close();
            } catch (Exception e) {
                result = "";
            } finally {
                return result;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        protected void onPostExecute(String result) {
            menuID.setText(result);
        }
    }


    private Button.OnClickListener btnlistener = new Button.OnClickListener() {
        public void onClick(View v) {
            //利用switch case方法，之後新增按鈕只需新增case即可
            switch (v.getId()) {
                //Quét nhập
                case R.id.btn_NT01: {
                    String INOUT = "IN";

                    Dialog dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.nt_dialog1);

                    cbx_xuong = dialog.findViewById(R.id.cbx_dxuong);
                    createTable = new Create_Table(dialog.getContext());
                    createTable.open();
                    check_plant(v.getContext());
                    cbx_xuong.getSelectedItem().toString();
                    String s_xuong = cbx_xuong.getSelectedItem().toString();
                    cbx_khu = dialog.findViewById(R.id.cbx_dkhu);
                    check_region(v.getContext(), s_xuong);
                    cbx_xuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cbx_xuong.getSelectedItem().toString();
                            String s_xuong = cbx_xuong.getSelectedItem().toString();
                            check_region(v.getContext(), s_xuong);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    btn_dconf = dialog.findViewById(R.id.btn_dconf);
                    btn_dconf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String conf_xuong = cbx_xuong.getSelectedItem().toString();
                            String conf_khu = cbx_khu.getSelectedItem().toString();
                            Intent DSVT = new Intent();
                            DSVT.setClass(Menu.this, NT_DSVT.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("ID", ID);
                            bundle.putString("INOUT", INOUT);
                            bundle.putString("XUONG", conf_xuong);
                            bundle.putString("KHU", conf_khu);
                            DSVT.putExtras(bundle);
                            startActivity(DSVT);
                        }
                    });


                    dialog.show();
                    break;
                }
                //Quét xuất
                case R.id.btn_NT02: {
                    String INOUT = "OUT";

                    Dialog dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.nt_dialog1);

                    cbx_xuong = dialog.findViewById(R.id.cbx_dxuong);
                    createTable = new Create_Table(dialog.getContext());
                    createTable.open();
                    check_plant(v.getContext());
                    cbx_xuong.getSelectedItem().toString();
                    String s_xuong = cbx_xuong.getSelectedItem().toString();
                    cbx_khu = dialog.findViewById(R.id.cbx_dkhu);
                    check_region(v.getContext(), s_xuong);
                    cbx_xuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cbx_xuong.getSelectedItem().toString();
                            String s_xuong = cbx_xuong.getSelectedItem().toString();
                            check_region(v.getContext(), s_xuong);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    btn_dconf = dialog.findViewById(R.id.btn_dconf);
                    btn_dconf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String conf_xuong = cbx_xuong.getSelectedItem().toString();
                            String conf_khu = cbx_khu.getSelectedItem().toString();
                            Intent DSVT = new Intent();
                            DSVT.setClass(Menu.this, NT_DSVT.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("ID", ID);
                            bundle.putString("INOUT", INOUT);
                            bundle.putString("XUONG", conf_xuong);
                            bundle.putString("KHU", conf_khu);
                            DSVT.putExtras(bundle);
                            startActivity(DSVT);
                        }
                    });


                    dialog.show();
                    break;
                }

                case R.id.btn_NT03: {
                    Intent NT03 = new Intent();
                    NT03.setClass(Menu.this, NT_search.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putString("SERVER", g_server);
                    NT03.putExtras(bundle);
                    startActivity(NT03);
                    break;
                }

                case R.id.btn_NT05: {
                    Intent QR010 = new Intent();
                    QR010.setClass(Menu.this, NT_Setup_data.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putString("SERVER", g_server);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }




                /*case R.id.btn_KT04: {
                    Intent QR010 = new Intent();
                    QR010.setClass(Menu.this, KT_1.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putString("SERVER", g_server);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }*/


            }
        }
    };

    //Khởi tạo menu trên thanh tiêu đề (S)
    /*@Override
    public boolean onCreateOptionsMenu(@NonNull android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opt, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_datatable:
                //Cre_db.delete_table();
                //Refresh_Datatable();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Refresh_Datatable() {
        Thread api = new Thread(new Runnable() {
            @Override
            public void run() {
                String res_fab = get_DataTable("http://172.16.40.20/PHPtest/TechAPP/getDataTable.php?item=fab");
                if (!res_fab.equals("FALSE")) {
                    try {
                        JSONArray jsonarray = new JSONArray(res_fab);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            String g_tc_fab001 = jsonObject.getString("TC_FAB001"); //Mã báo biểu
                            String g_tc_fab002 = jsonObject.getString("TC_FAB002"); //Mã hạng mục
                            String g_tc_fab003 = jsonObject.getString("TC_FAB003"); //Tên hạng mục( tiếng hoa)
                            String g_tc_fab004 = jsonObject.getString("TC_FAB004"); //Tên hạng mục( tiếng việt)

                            Cre_db.append(g_tc_fab001, g_tc_fab002, g_tc_fab003, g_tc_fab004);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String res_fac = get_DataTable("http://172.16.40.20/PHPtest/TechAPP/getDataTable.php?item=fac");
                    if (!res_fac.equals("FALSE")) {
                        try {
                            JSONArray jsonarray = new JSONArray(res_fac);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonObject = jsonarray.getJSONObject(i);
                                String g_tc_fac001 = jsonObject.getString("TC_FAC001"); //Mã hạng mục
                                String g_tc_fac002 = jsonObject.getString("TC_FAC002"); //Mã báo biểu
                                String g_tc_fac003 = jsonObject.getString("TC_FAC003"); //Mã hạng mục chi tiết
                                String g_tc_fac004 = jsonObject.getString("TC_FAC004"); //Mã tổng
                                String g_tc_fac005 = jsonObject.getString("TC_FAC005"); //Tên hạng mục chi tiết( tiếng hoa)
                                String g_tc_fac006 = jsonObject.getString("TC_FAC006"); //Tên hạng mục chi tiết( tiếng việt)
                                String g_tc_fac007 = jsonObject.getString("TC_FAC007"); //Điểm số
                                String g_tc_fac008 = jsonObject.getString("TC_FAC008"); //Hãng sản xuất
                                String g_tc_fac011 = jsonObject.getString("TC_FAC011"); //Dãy đo thiết bị

                                Cre_db.append(g_tc_fac001, g_tc_fac002, g_tc_fac003,
                                        g_tc_fac004, g_tc_fac005, g_tc_fac006,
                                        g_tc_fac007, g_tc_fac008, g_tc_fac011);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        api.start();
    }

    private String get_DataTable(String s) {
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
    }*/
    //Khởi tạo menu trên thanh tiêu đề (E)

    private void setLanguage() {
        SharedPreferences preferences = getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        switch (language) {
            case 0:
                configuration.setLocale(Locale.TRADITIONAL_CHINESE);
                break;
            case 1:
                locale = new Locale("vi");
                Locale.setDefault(locale);
                configuration.setLocale(locale);
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private void check_plant(Context dialog1) {
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
            cursor_1.moveToNext();
        }
        stationlist = new ArrayAdapter<>(dialog1, android.R.layout.simple_spinner_item, station);
        cbx_xuong.setAdapter(stationlist);
        cbx_xuong.setAdapter(stationlist);
        cbx_xuong.setSelection(0);
    }

    private void check_region(Context dialog2, String lxuong) {
        cursor_1 = createTable.getAll_setup_02(lxuong);
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        station = new String[num];
        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String setup02 = cursor_1.getString(cursor_1.getColumnIndex("setup02"));

                String g_setup02 = setup02;
                station[i] = g_setup02;

            } catch (Exception e) {
                String err = e.toString();
            }
            cursor_1.moveToNext();
        }
        stationlist = new ArrayAdapter<>(dialog2, android.R.layout.simple_spinner_item, station);
        cbx_khu.setAdapter(stationlist);
        cbx_khu.setAdapter(stationlist);
        cbx_khu.setSelection(0);

    }

}