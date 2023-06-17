package com.lelong.ngungtinh;

import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NT_Loaddata {
    private Create_Table db = null;
    private Create_Table createTable = null;
    private Context mCtxAPI = null;
    String g_package = "";
    String g_server = "";

    public NT_Loaddata(Context ctx, String g_server) {
        this.g_server = g_server;
        this.mCtxAPI = ctx;
        g_package = mCtxAPI.getPackageName().toString();
    }

    public void load_data_bad(){
        createTable = new Create_Table(this.mCtxAPI);
        createTable.open();

        createTable.del_total();
        getcode_up();
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
