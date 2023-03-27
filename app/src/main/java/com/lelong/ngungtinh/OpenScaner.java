package com.lelong.ngungtinh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OpenScaner extends AppCompatActivity {
    SurfaceView surfaceView;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    boolean firstDetected = true;
    String ID, g_INOUT, conf_xuong, conf_khu, l_vtri;
    Cursor cursor_1, cursor_2;
    private Create_Table createTable = null;
    String[] station = new String[0];
    ArrayAdapter<String> stationlist;

    Locale locale;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    String pattern = "###,###";
    DecimalFormat decimalFormat;
    Create_Table create_table = null;

    TextView tv_qrcode, tv_ngayvao, tv_ngaysac, tv_tuan, tv_soluong, tv_qc;
    Button btn_addQrcode, btn_DelQrcode;
    String IDButton, g_xuong, g_server, SaveCode;
    String g_khu, g_vitri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_scanner);
        addControlEvent();

        Bundle getBundle = getIntent().getExtras();
        IDButton = getBundle.getString("IDButton");

        ID = getBundle.getString("ID");
        g_INOUT = getBundle.getString("INOUT");
        conf_xuong = getBundle.getString("XUONG");
        conf_khu = getBundle.getString("KHU");
        l_vtri = getBundle.getString("VITRI");
        createTable = new Create_Table(this);
        createTable.open();


        /*g_xuong = getBundle.getString("xuong");
        g_server = getBundle.getString("SERVER");
        g_khu = IDButton.substring(0, 2);
        g_vitri = IDButton.substring(2, 4);*/
        g_server = "PHPtest";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("廠別： " + conf_xuong + "  儲位:  " + conf_khu + "  子位置:  " + l_vtri);

        Locale locales = new Locale("en", "EN");
        decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locales);
        decimalFormat.applyPattern(pattern);

        CameraSetting();
        firstDetected = true;

        create_table = new Create_Table(this);
        create_table.open();

        //loadData();

    }

    private void addControlEvent() {


        tv_qrcode = findViewById(R.id.tv_qrcode);
        tv_ngayvao = findViewById(R.id.tv_ngayvao);
        //tv_ngaysac = findViewById(R.id.tv_ngaysac);
        //tv_tuan = findViewById(R.id.tv_tuan);
        tv_soluong = findViewById(R.id.tv_soluong);
        tv_qc = findViewById(R.id.tv_qc);
        btn_addQrcode = findViewById(R.id.btn_addQrcode);
        btn_DelQrcode = findViewById(R.id.btn_DelQrcode);


        btn_addQrcode.setOnClickListener(v -> {
            String res = "";
            String res1 = "";
            String res2 = "";
            //if (!SaveCode.equals("")) {
            if (tv_qrcode.getText().toString().length() > 0) {
                //
                if (Integer.parseInt(tv_soluong.getText().toString()) > 0) {
                    //Kiểm tra trong table tổng đã có dữ liệu hay chưa
                    Integer count1 = check_total(conf_xuong, conf_khu, l_vtri, tv_qrcode.getText().toString().trim());

                    if (count1 > 0) {
                        //update dữ liệu vào table tổng
                         create_table.upd_total_sdata(conf_xuong, conf_khu,
                                l_vtri, tv_qrcode.getText().toString().trim(), tv_soluong.getText().toString().trim().replace(",", ""),g_INOUT);
                    } else {
                        //insert dữ liệu vào table tổng
                        res1 = create_table.insTotal_sdata(conf_xuong, conf_khu,
                                l_vtri, tv_qrcode.getText().toString().trim(), tv_soluong.getText().toString().trim().replace(",", ""));
                        if (res1.equals("TRUE")) {
                            firstDetected = true;
                        } else {
                            Toast.makeText(this, "更新到insSetup_data失敗 Cập nhật insSetup_data thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //Insert dữ liệu lưu vào table lịch sử quét
                    res = create_table.insScanData(
                            tv_qrcode.getText().toString().trim(),
                            tv_qc.getText().toString().trim(),
                            tv_soluong.getText().toString().trim().replace(",", ""),
                            //tv_tuan.getText().toString().trim(),
                            //tv_ngaysac.getText().toString().trim(),
                            tv_ngayvao.getText().toString().trim(),

                            conf_xuong, conf_khu, l_vtri, g_INOUT, ID, tv_ngayvao.getText().toString().trim());

                    //create_table.upd_BasicData(conf_xuong, conf_khu, l_vtri, "+ 1");

                    if (res.equals("TRUE")) {
                        Toast.makeText(this, "完成存放 Lưu trữ hoàn tất", Toast.LENGTH_SHORT).show();
                        clear_map();
                        firstDetected = true;
                    } else {
                        Toast.makeText(this, "存放失敗 Lưu trữ thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "數量不能是 0 Số lượng không thể là 0", Toast.LENGTH_SHORT).show();
                }

            }
            //}
        });

        btn_DelQrcode.setOnClickListener(v -> {
            //create_table.delData(g_xuong, IDButton);
            //create_table.upd_BasicData(g_xuong, g_khu, g_vitri, "- 1");
            clear_map();
        });
    }

    //Kiểm tra đã có dữ liệu tồn tại trong table total hay chưa
    private int check_total(String tt_xuong, String tt_khu, String tt_vitri, String tt_doncong) {
        cursor_1 = createTable.getAll_count_total_sdata(tt_xuong, tt_khu, tt_vitri, tt_doncong);
        cursor_1.moveToFirst();
        int n_count = cursor_1.getInt(0);
        return n_count;
    }

    private void clear_map() {
        SaveCode = "";
        tv_qrcode.setText("");
        tv_ngayvao.setText("");
        //tv_ngaysac.setText("");
        //tv_tuan.setText("");
        tv_soluong.setText("");
        tv_qc.setText("");
        firstDetected = true;
    }

    private void loadData() {
        Cursor cursor = create_table.getall(conf_xuong, conf_khu, l_vtri);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int num = cursor.getCount();
            for (int i = 0; i < num; i++) {
                @SuppressLint("Range") String scan01 = cursor.getString(cursor.getColumnIndex("scan01"));
                @SuppressLint("Range") String scan02 = cursor.getString(cursor.getColumnIndex("scan02"));
                @SuppressLint("Range") String scan03 = cursor.getString(cursor.getColumnIndex("scan03"));
                //@SuppressLint("Range") String scan04 = cursor.getString(cursor.getColumnIndex("scan04"));
                //@SuppressLint("Range") String scan05 = cursor.getString(cursor.getColumnIndex("scan05"));
                @SuppressLint("Range") String scan06 = cursor.getString(cursor.getColumnIndex("scan06"));
                /*@SuppressLint("Range") String scan07 = cursor.getString(cursor.getColumnIndex("scan07"));
                @SuppressLint("Range") String scan08 = cursor.getString(cursor.getColumnIndex("scan08"));
                @SuppressLint("Range") String scan09 = cursor.getString(cursor.getColumnIndex("scan09"));
                @SuppressLint("Range") String scan10 = cursor.getString(cursor.getColumnIndex("scan10"));
                @SuppressLint("Range") String scan11 = cursor.getString(cursor.getColumnIndex("scan11"));
                @SuppressLint("Range") String scan12 = cursor.getString(cursor.getColumnIndex("scan12"));*/
                //@SuppressLint("Range") String scanqrcode = cursor.getString(cursor.getColumnIndex("scanqrcode"));

                tv_qrcode.setText(scan01);
                tv_qc.setText(scan02);
                tv_soluong.setText(scan03);
                //tv_tuan.setText(scan04);
                //tv_ngaysac.setText(scan05);
                tv_ngayvao.setText(scan06);
                //SaveCode = scanqrcode;
            }
        } else {
            SaveCode = "";
            tv_qrcode.setText("");
            tv_ngayvao.setText("");
            //tv_ngaysac.setText("");
            //tv_tuan.setText("");
            tv_soluong.setText("");
            tv_qc.setText("");
        }
    }

    private void getcode(String g_code) {
        if (g_code.length() > 0) {
            String g_doncong = g_code.substring(0, 16).trim();
            final String res = getcodedata("http://172.16.40.20/" + g_server + "/TechAPP/getDataPallet.php?item=" + g_doncong);
            if (res.length() > 0 && !res.equals("FALSE")) {
                //[{"SFB01":"BC51A-2112000001","IMAUD04":"WTZ6VIS(COS)","SFB08":"15000","TA_SFB010":"2021048"}
                try {
                    JSONArray jsonArray = new JSONArray(res);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    tv_qrcode.setText(g_doncong);
                    tv_qc.setText(jsonObject.getString("IMAUD04").toString().trim());
                    int g_sfb08 = Integer.parseInt(jsonObject.getString("SFB08"));
                    //tv_soluong.setText(String.valueOf(decimalFormat.format(g_sfb08)));
                    tv_soluong.setText(String.valueOf(decimalFormat.format(0)));
                    //tv_tuan.setText(jsonObject.getString("TA_SFB010").toString().trim());
                    //tv_ngaysac.setText(dateFormat.format(new Date()).toString());
                    tv_ngayvao.setText(dateFormat.format(new Date()).toString());
                    SaveCode = g_code;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            } else {
                tv_qrcode.setText(g_code.substring(0, 16));
                tv_qc.setText("WTZ5S(COS)");
                tv_soluong.setText("15,000");
                //tv_tuan.setText("2023010");
                //tv_ngaysac.setText(dateFormat.format(new Date()).toString());
                tv_ngayvao.setText(dateFormat.format(new Date()).toString());
                SaveCode = g_code;
            }
        }
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

    private void CameraSetting() {
        surfaceView = (SurfaceView) this.findViewById(R.id.sfv_scanner);
        //barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS | Barcode.CODE_128 | Barcode.QR_CODE).build();
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(300, 300).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                    return;
                try {

                    cameraSource.start(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0 && firstDetected) {
                    firstDetected = false;
                    final String g_code = qrCodes.valueAt(0).displayValue;
                    getcode(g_code);
                }
            }
        });
    }

    //Cursor 轉 Json
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

    //設定語言
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
            case 2:
                locale = new Locale("en");
                Locale.setDefault(locale);
                configuration.setLocale(locale);
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        firstDetected = false;
    }
}