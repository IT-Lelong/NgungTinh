package com.lelong.ngungtinh;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

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

public class update_data {
    JSONArray tjsonupload, jsonupload;
    private Create_Table db = null;
    JSONObject ujobject;
    String g_server = "";
    private Context mCtxAPI = null;
    String g_package = "";
    String l_key;
    //String g_server = "PHP";
    private Create_Table createTable = null;
    res_interface res_interface;

    public update_data(Context ctx, String g_server, String key, res_interface res_interface) {
        this.g_server = g_server;
        this.mCtxAPI = ctx;
        this.l_key = key;
        g_package = mCtxAPI.getPackageName().toString();
        this.res_interface = res_interface;
    }



    public void up_oracle() {
        db = new Create_Table(this.mCtxAPI);
        db.open();
        createTable = new Create_Table(this.mCtxAPI);
        createTable.open();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor upl = db.getAll_tc_bac_1(l_key);
                if (upl.getCount() > 0) {

                    jsonupload = cur2Json(upl);
                    try {
                        ujobject = new JSONObject();
                        ujobject.put("ujson", jsonupload);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final String res = upload_all("http://172.16.40.20/" + g_server + "/WMS/upload_2.php");
                    if (res.contains("false")) {
                        createTable.upd_chk_scan(l_key,"N");
                        res_interface.loadData2("FALSE");
                    } else {
                        createTable.upd_chk_scan(l_key,"Y");
                        res_interface.loadData2("TRUE");
                    }
                }
            }
        }).

                start();
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
}
