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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class OpenScaner extends AppCompatActivity {
    SurfaceView surfaceView, surfaceView_upload;
    CameraSource cameraSource, cameraSource_upload;
    BarcodeDetector barcodeDetector, barcodeDetector_upload;
    boolean firstDetected = true;
    boolean firstDetected_upload = true;
    JSONArray jsonupload;
    JSONObject ujobject;
    Locale locale;

    Create_Table create_table = null;

    TextView tv_qrcode, tv_ngayvao, tv_ngaysac, tv_tuan, tv_soluong, tv_qc;
    Button btn_addQrcode, btn_DelQrcode;
    String IDButton, g_xuong, g_server, SaveCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_scanner);
        addControlEvent();

        Bundle getBundle = getIntent().getExtras();
        IDButton = getBundle.getString("IDButton");
        g_xuong = getBundle.getString("Xuong");
        g_server = getBundle.getString("SERVER");
        g_server = "PHPtest";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("棟別: " + g_xuong + "位置: " + IDButton);

        CameraSetting();
        firstDetected = true;

        create_table = new Create_Table(this);
        create_table.open();

        loadData();

    }

    private void addControlEvent() {
        tv_qrcode = findViewById(R.id.tv_qrcode);
        tv_ngayvao = findViewById(R.id.tv_ngayvao);
        tv_ngaysac = findViewById(R.id.tv_ngaysac);
        tv_tuan = findViewById(R.id.tv_tuan);
        tv_soluong = findViewById(R.id.tv_soluong);
        tv_qc = findViewById(R.id.tv_qc);
        btn_addQrcode = findViewById(R.id.btn_addQrcode);
        btn_DelQrcode = findViewById(R.id.btn_DelQrcode);

        btn_addQrcode.setOnClickListener(v -> {
            String res = "";
            if (!SaveCode.equals("")) {
                if (tv_qrcode.getText().toString().length() > 0) {
                    res = create_table.insScanData(
                            tv_qrcode.getText().toString().trim(),
                            tv_qc.getText().toString().trim(),
                            tv_soluong.getText().toString().trim(),
                            tv_tuan.getText().toString().trim(),
                            tv_ngaysac.getText().toString().trim(),
                            tv_ngayvao.getText().toString().trim(),
                            SaveCode, IDButton, g_xuong);

                    if (res.equals("TRUE")) {
                        Toast.makeText(this, "完成存放", Toast.LENGTH_SHORT).show();
                        firstDetected = true;
                    } else {
                        Toast.makeText(this, "存放失敗", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_DelQrcode.setOnClickListener(v -> {
            create_table.delData(g_xuong, IDButton);
            SaveCode = "";
            tv_qrcode.setText("");
            tv_ngayvao.setText("");
            tv_ngaysac.setText("");
            tv_tuan.setText("");
            tv_soluong.setText("");
            tv_qc.setText("");
            firstDetected = true;
        });
    }

    private void loadData() {
        Cursor cursor = create_table.getall(g_xuong, IDButton);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int num = cursor.getCount();
            for (int i = 0; i < num; i++) {
                @SuppressLint("Range") String scan01 = cursor.getString(cursor.getColumnIndex("scan01"));
                @SuppressLint("Range") String scan02 = cursor.getString(cursor.getColumnIndex("scan02"));
                @SuppressLint("Range") String scan03 = cursor.getString(cursor.getColumnIndex("scan03"));
                @SuppressLint("Range") String scan04 = cursor.getString(cursor.getColumnIndex("scan04"));
                @SuppressLint("Range") String scan05 = cursor.getString(cursor.getColumnIndex("scan05"));
                @SuppressLint("Range") String scan06 = cursor.getString(cursor.getColumnIndex("scan06"));
                @SuppressLint("Range") String scanqrcode = cursor.getString(cursor.getColumnIndex("scanqrcode"));
                tv_qrcode.setText(scan01);
                tv_qc.setText(scan02);
                tv_soluong.setText(scan03);
                tv_tuan.setText(scan04);
                tv_ngaysac.setText(scan05);
                tv_ngayvao.setText(scan06);
                SaveCode = scanqrcode;
            }
        } else {
            SaveCode = "";
            tv_qrcode.setText("");
            tv_ngayvao.setText("");
            tv_ngaysac.setText("");
            tv_tuan.setText("");
            tv_soluong.setText("");
            tv_qc.setText("");
        }
    }

    private void getcode(String g_code) {
        if (g_code.length() > 0) {
            String g_doncong = g_code.substring(0, 16).trim();
            final String res = getcodedata("http://172.16.40.20/" + g_server + "/TechAPP/getDataPallet.php?item=" + g_doncong);
            if (res.length() > 0 && !res.equals("FALSE")) {
                tv_qrcode.setText(g_doncong);
                tv_qc.setText("WTZ5S(COS)");
                tv_soluong.setText("15,000");
                tv_tuan.setText("2023010");
                tv_ngaysac.setText("2023/03/10");
                tv_ngayvao.setText("2023/03/10");
                SaveCode = g_code;
            } else {
                tv_qrcode.setText(g_code.substring(0, 16));
                tv_qc.setText("WTZ5S(COS)");
                tv_soluong.setText("15,000");
                tv_tuan.setText("2023010");
                tv_ngaysac.setText("2023/03/10");
                tv_ngayvao.setText("2023/03/10");
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