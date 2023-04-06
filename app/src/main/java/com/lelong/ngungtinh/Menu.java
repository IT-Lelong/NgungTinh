package com.lelong.ngungtinh;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lelong.ngungtinh.KTnew.nt_dialog1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
    JSONArray tjsonupload,jsonupload;
    JSONObject ujobject;
    private Create_Table db = null;

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

                case R.id.btn_NT04: {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
                    builder.setMessage(Menu.this.getString(R.string.M05))
                            .setPositiveButton(Menu.this.getString(R.string.btn_ok), null)
                            .setNegativeButton(Menu.this.getString(R.string.btn_cancel), null);


                    AlertDialog al_dialog = builder.create();
                    al_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            TextView messageView = ((AlertDialog) dialogInterface).findViewById(android.R.id.message);
                            messageView.setTextSize(30);

                            Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                            positiveButton.setTextColor(ContextCompat.getColor(Menu.this, R.color.blue));
                            positiveButton.setTextSize(15);
                            Button negativeButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_NEGATIVE);
                            negativeButton.setTextColor(ContextCompat.getColor(Menu.this, R.color.red));
                            negativeButton.setTextSize(15);

                            positiveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    db = new Create_Table(Menu.this);
                                    db.open();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String bien = "A";
                                            Cursor upl = db.getAll_tc_bac(bien);
                                            //Cursor tupl = db.getAll_tc_bad(bien);
                                            if (upl.getCount() > 0) {

                                                jsonupload = cur2Json(upl);
                                                //tjsonupload = cur2Json(tupl);

                                                try {
                                                    ujobject = new JSONObject();
                                                    ujobject.put("ujson", jsonupload);
                                                    //ujobject.put("tjson", tjsonupload);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                final String res = upload_all("http://172.16.40.20/" + g_server + "/WMS/upload.php");

                                                runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                                                    @Override
                                                    public void run() {
                                                        if (res.contains("false")) {
                                                            //Toast.makeText(getApplicationContext(), getString(R.string.M09), Toast.LENGTH_SHORT).show();
                                                            Toast.makeText(getApplicationContext(), "Kết chuyễn dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            //Toast.makeText(getApplicationContext(), getString(R.string.M08), Toast.LENGTH_SHORT).show();
                                                            Toast.makeText(getApplicationContext(), "Kết chuyễn dữ liệu thành công ", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                            }
                                        }
                                    }).start();


                                }
                            });
                        }
                    });

                    al_dialog.show();
                    break;
                }

                case R.id.btn_NT05: {
                    Intent NT05 = new Intent();
                    NT05.setClass(Menu.this, NT_Setup_data.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putString("SERVER", g_server);
                    NT05.putExtras(bundle);
                    startActivity(NT05);
                    break;
                }


            }
        }
    };

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