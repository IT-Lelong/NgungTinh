package com.lelong.ngungtinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Create_Table {

    private Context mCtx = null;
    String DATABASE_NAME = "NgungTinhDB.db";
    public SQLiteDatabase db = null;

    String TB_basic_data_file = "basic_data_file";
    String dulieu01 = "dulieu01"; //Mã Xưởng
    String dulieu02 = "dulieu02"; //Mã Khu
    String dulieu03 = "dulieu03"; //Mã Vị trí
    String dulieu04 = "dulieu04"; //Tổng sức chứa
    String dulieu05 = "dulieu05"; //Đã sử dụng

    String TB_scandata_file= "scandata_file";
    String scan01 = "scan01"; //Mã đơn công
    String scan02 = "scan02"; //Quy cách
    String scan03= "scan03"; //Sô lượng
    String scan04 = "scan04"; //Tuần
    String scan05= "scan05"; //Ngày sạc
    String scan06 = "scan06"; //Ngày ngưng tịnh
    String scanqrcode = "scanqrcode"; //mã tem
    String scanlocation = "scanlocation"; //Vị trí
    String scanfactory = "scanfactory"; //Xưởng




    String CREATE_TB_basic_data_file  = "CREATE TABLE IF NOT EXISTS " + TB_basic_data_file + " ("
            + dulieu01 + " TEXT," + dulieu02 + " TEXT," + dulieu03 + " TEXT,"
            + dulieu04 + " DECIMAL," + dulieu05 + " DECIMAL)";

    String CREATE_TABLE_scandata = "CREATE TABLE IF NOT EXISTS " + TB_scandata_file + " ("
            + scan01 + " TEXT," + scan02 + " TEXT," + scan03 + " TEXT,"
            + scan04 + " TEXT," + scan05 + " TEXT," + scan06 + " TEXT,"
            + scanqrcode + " TEXT," + scanlocation + " TEXT,"  + scanfactory + " TEXT )";


    public Create_Table(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
    }

    public void createTable() {
        try {
            db.execSQL(CREATE_TB_basic_data_file);
            db.execSQL(CREATE_TABLE_scandata);
        } catch (Exception e) {

        }
    }

    public void close() {
        try {
            String DROP_TABLE_TC_FAB = "DROP TABLE IF EXISTS " + TB_basic_data_file;
            db.execSQL(DROP_TABLE_TC_FAB);
            String DROP_TABLE_scandata_file = "DROP TABLE IF EXISTS " + TB_scandata_file;
            db.execSQL(DROP_TABLE_scandata_file);
            db.close();
        } catch (Exception e) {

        }
    }

    public String insBasicData(String g_dulieu01, String g_dulieu02, String g_dulieu03, int g_dulieu04, int g_dulieu05) {
        try {
            ContentValues args = new ContentValues();
            args.put(dulieu01, g_dulieu01);
            args.put(dulieu02, g_dulieu02);
            args.put(dulieu03, g_dulieu03);
            args.put(dulieu04, g_dulieu04);
            args.put(dulieu05, g_dulieu05);
            db.insert(TB_scandata_file, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    public String insScanData(String g_scan01, String g_scan02, String g_scan03, String g_scan04,
                              String g_scan05, String g_scan06, String g_scanqrcode, String g_scanlocation,String g_scanfactory) {
        try {
            ContentValues args = new ContentValues();
            args.put(scan01, g_scan01);
            args.put(scan02, g_scan02);
            args.put(scan03, g_scan03);
            args.put(scan04, g_scan04);
            args.put(scan05, g_scan05);
            args.put(scan06, g_scan06);
            args.put(scanqrcode, g_scanqrcode);
            args.put(scanlocation, g_scanlocation);
            args.put(scanfactory, g_scanfactory);
            db.insert(TB_scandata_file, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }



    public void delete_table() {
        db.delete(TB_basic_data_file, null, null);
    }

    public Cursor getall(String g_xuong, String idButton) {
        try {
            String selectQuery = "SELECT * FROM " + TB_scandata_file + " WHERE scanfactory='" + g_xuong + "' AND scanlocation='" + idButton+"'";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public void delData(String g_xuong, String idButton) {
        String condition = "scanfactory = ? and scanlocation = ?";
        String[] conditionArgs = new String[] {g_xuong,idButton};
        db.delete(TB_scandata_file, condition, conditionArgs);
    }

    //Kiểm tra vị trí được quét chưa
    public int check_tb_scandata_file(String g_scanlocation,String g_scanfactory) {
        int q_count = -1;
        Cursor res = db.query(TB_scandata_file,
                new String[]{"rowid _id", scan01},
                scanfactory + "=? AND " + scanlocation + "=? ",
                new String[]{g_scanfactory,g_scanlocation},
                null, null, null, null);
        res.moveToFirst();
        if (res.getCount() > 0) {
            q_count = res.getCount();
        } else {
            q_count = 0;
        }
        return q_count;
    }
}
