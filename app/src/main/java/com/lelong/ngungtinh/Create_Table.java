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

    String TB_dulieucoban_file = "dulieucoban_file";
    String dulieu01 = "dulieu01"; //Mã Xưởng
    String dulieu02 = "dulieu02"; //Mã Khu
    String dulieu03 = "dulieu03"; //Mã Vị trí
    String dulieu04 = "dulieu04"; //Tổng sức chứa
    String dulieu05 = "dulieu05"; //Đã sử dụng

    String TABLE_NAME_TC_FAC = "tc_fac_file";
    String tc_fac001 = "tc_fac001"; //Mã hạng mục
    String tc_fac002 = "tc_fac002"; //Mã báo biểu
    String tc_fac003 = "tc_fac003"; //Mã hạng mục chi tiết
    String tc_fac004 = "tc_fac004"; //Mã tổng
    String tc_fac005 = "tc_fac005"; //tên tiếng hoa
    String tc_fac006 = "tc_fac006"; //tên tiếng việt
    String tc_fac007 = "tc_fac007"; //Điểm số
    String tc_fac008 = "tc_fac008"; //Hãng sản xuất
    String tc_fac011 = "tc_fac011"; //Dãy đo thiết bị


    String CREATE_TB_dulieucoban_file  = "CREATE TABLE IF NOT EXISTS " + TB_dulieucoban_file + " ("
            + dulieu01 + " TEXT," + dulieu02 + " TEXT," + dulieu03 + " TEXT,"
            + dulieu04 + " DECIMAL," + dulieu05 + " DECIMAL)";

    String CREATE_TABLE_FAC = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAC + " ("
            + tc_fac001 + " TEXT," + tc_fac002 + " TEXT," + tc_fac003 + " TEXT,"
            + tc_fac004 + " TEXT," + tc_fac005 + " TEXT," + tc_fac006 + " TEXT,"
            + tc_fac007 + " TEXT," + tc_fac008 + " TEXT," + tc_fac011 + " TEXT )";


    public Create_Table(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
    }

    public void createTable() {
        try {
            db.execSQL(CREATE_TB_dulieucoban_file);
        } catch (Exception e) {

        }
    }

    public void close() {
        try {
            String DROP_TABLE_TC_FAB = "DROP TABLE IF EXISTS " + TB_dulieucoban_file;
            db.execSQL(DROP_TABLE_TC_FAB);
            db.close();
        } catch (Exception e) {

        }
    }

    public String append(String g_dulieu01, String g_dulieu02, String g_dulieu03, int g_dulieu04, int g_dulieu05) {
        try {
            ContentValues args = new ContentValues();
            args.put(dulieu01, g_dulieu01);
            args.put(dulieu02, g_dulieu02);
            args.put(dulieu03, g_dulieu03);
            args.put(dulieu04, g_dulieu04);
            args.put(dulieu05, g_dulieu05);
            db.insert(TB_dulieucoban_file, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }



    public void delete_table() {
        db.delete(TB_dulieucoban_file, null, null);
    }
    
}
