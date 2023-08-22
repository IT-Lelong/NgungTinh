package com.lelong.ngungtinh;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Menu extends AppCompatActivity implements Call_interface {

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
    Cursor cursor_1;
    String[] station = new String[0];
    ArrayAdapter<String> stationlist;
    JSONArray jsonupload;
    JSONObject ujobject;
    private Create_Table db = null;
    private NT_Loaddata NT_Loaddata = null;
    Dialog dialog;
    TextView tv_syncName;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //actionBar = getSupportActionBar();
        //actionBar.hide();
        Bundle getbundle = getIntent().getExtras();
        ID = getbundle.getString("ID");
        g_server = Constant_Class.server;
        menuID = (TextView) findViewById(R.id.menuID);
        new IDname().execute("http://172.16.40.20/" + Constant_Class.server + "/");

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
        checkAppUpdate = new CheckAppUpdate(this);
        checkAppUpdate.checkVersion();
    }

    //Khởi tạo menu trên thanh tiêu đề (S)
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opt, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String g_status;
        switch (item.getItemId()) {
            case R.id.refresh_datatable:
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.data_sync_layout);
                tv_syncName = dialog.findViewById(R.id.tv_syncName);
                progressBar = dialog.findViewById(R.id.impotDataProgressBar);

                tv_syncName.setText("");
                Cre_db.del_ima();
                Import_Data("ALLBATTERY");
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Import_Data(String g_item) {
        String baseUrl = "http://172.16.40.20/" + Constant_Class.server + "/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonArray> call = apiInterface.getDataTable(baseUrl + "get_ima_file.php?IMA01=" + g_item);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    JsonArray jsonArray = response.body();
                    //Cre_db.insertData(g_table,jsonArray);
                    Cre_db.setInsertCallback(Menu.this);
                    Create_Table.InsertDataTask insertDataTask = Cre_db.new InsertDataTask(progressBar);
                    insertDataTask.execute(String.valueOf(jsonArray));
                } else {
                    // Xử lý trường hợp response không thành công
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                // Xử lý trường hợp lỗi
            }
        });
    }

    //Khởi tạo menu trên thanh tiêu đề (E)

    @Override
    public void ImportData_onInsertComplete(String status) {
        dialog.cancel();
        Toast.makeText(this, "Hoàn tất đồng bộ dữ liệu", Toast.LENGTH_SHORT).show();
    }

    //取得登入者姓名
    private class IDname extends AsyncTask<String, Integer, String> {
        String result = "";

        @Override
        protected String doInBackground(String... params) {
            try {
                String baseUrl = params[0];
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                Call<List<JsonObject>> call = apiInterface.getData(baseUrl + "getidJson.php?ID=" + ID);
                Response<List<JsonObject>> response = call.execute();

                if (response.isSuccessful()) {
                    List<JsonObject> jsonObjects = response.body();
                    if (jsonObjects != null && jsonObjects.size() > 0) {
                        JsonObject jsonObject = jsonObjects.get(0);
                        result = jsonObject.toString(); // Convert JsonObject to a string
                    } else {
                        result = "FALSE";
                    }
                } else {
                    result = "FALSE";
                }
            } catch (Exception e) {
                result = "FALSE";
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!result.equals("FALSE")) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            menuID.setText(ID + " " + jsonObject.getString("TA_CPF001") + "\n" + jsonObject.getString("GEM02"));
                            Constant_Class.UserID = ID;
                            Constant_Class.UserName_zh = jsonObject.getString("CPF02");
                            Constant_Class.UserName_vn = jsonObject.getString("TA_CPF001");
                            Constant_Class.UserDepID = jsonObject.getString("CPF29");
                            Constant_Class.UserDepName = jsonObject.getString("GEM02");
                            Constant_Class.UserFactory = jsonObject.getString("CPF281");
                        } catch (JSONException e) {
                            Toast alert = Toast.makeText(Menu.this, e.toString(), Toast.LENGTH_LONG);
                            alert.show();
                        }
                    }

                }
            });
        }
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
                            bundle.putString("SERVER", g_server);
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
                            bundle.putString("SERVER", g_server);
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
                                            String bien = "N";
                                            Cursor upl = db.getAll_tc_bac(bien);
                                            //Cursor tupl = db.getAll_tc_bad(bien);
                                            if (upl.getCount() > 0) {

                                                jsonupload = cur2Json(upl);
                                                //tjsonupload = cur2Json(tupl);

                                                try {
                                                    ujobject = new JSONObject();
                                                    ujobject.put("u2json", jsonupload);
                                                    //ujobject.put("tjson", tjsonupload);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                final String res_1 = upload_all("http://172.16.40.20/" + g_server + "/WMS/upload.php");

                                                runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                                                    @Override
                                                    public void run() {
                                                        if (res_1.contains("false")) {
                                                            Toast.makeText(getApplicationContext(), "Kết chuyễn dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            try {
                                                                JSONObject jsonObject = new JSONObject(res_1);
                                                                JSONArray jsonarray = jsonObject.getJSONArray("TC_BAC000");
                                                                if (jsonarray.length() > 0) {
                                                                    for (int i = 0; i < jsonarray.length(); i++) {
                                                                        String g_key = jsonarray.getString(i);
                                                                        db.upd_chk_scan(g_key, "Y");
                                                                    }
                                                                }
                                                                Toast.makeText(getApplicationContext(), "Kết chuyển dữ liệu thành công ", Toast.LENGTH_SHORT).show();
                                                                al_dialog.dismiss();
                                                            } catch (JSONException e) {
                                                                throw new RuntimeException(e);
                                                            }
                                                        }

                                                    }
                                                });

                                            }else{
                                                runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        Toast.makeText(getApplicationContext(), "Không có dữ liệu để kết chuyển", Toast.LENGTH_SHORT).show();
                                                        al_dialog.dismiss();
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