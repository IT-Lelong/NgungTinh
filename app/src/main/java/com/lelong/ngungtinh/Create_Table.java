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

    String TB_scandata_file = "scandata_file";
    String scan01 = "scan01"; //Mã đơn công
    String scan02 = "scan02"; //Quy cách
    String scan03 = "scan03"; //Sô lượng
    //String scan04 = "scan04"; //Tuần
    //String scan05 = "scan05"; //Ngày sạc
    String scan06 = "scan06"; //Ngày ngưng tịnh

    String scan07 = "scan07"; //Xưởng
    String scan08 = "scan08"; //Khu
    String scan09 = "scan09"; //Vị trí
    String scan10 = "scan10"; //INOUT
    String scan11 = "scan11"; //ID người quét
    String scan12 = "scan12"; //Ngày quét

    /*String scanqrcode = "scanqrcode"; //mã tem
    String scanlocation = "scanlocation"; //Vị trí
    String scanfactory = "scanfactory"; //Xưởng*/

    //thiết lập dữ liệu xưởng
    String TB_setup_data_file = "setup_data_file";
    String setup01 = "setup01"; //Xưởng
    String setup02 = "setup02"; //Khu
    String setup03 = "setup03"; //Vị trí con

    //tổng dữ liệu quét nhập xuất
    String TB_total_sdata_file = "total_sdata_file";
    String sdata01 = "sdata01"; //Xưởng
    String sdata02 = "sdata02"; //Khu
    String sdata03 = "sdata03"; //Vị trí con
    String sdata04 = "sdata04"; //Đơn công
    String sdata05 = "sdata05"; //Số lượng


    String CREATE_TB_basic_data_file = "CREATE TABLE IF NOT EXISTS " + TB_basic_data_file + " ("
            + dulieu01 + " TEXT," + dulieu02 + " TEXT," + dulieu03 + " TEXT,"
            + dulieu04 + " DECIMAL," + dulieu05 + " DECIMAL)";

    String CREATE_TABLE_scandata = "CREATE TABLE IF NOT EXISTS " + TB_scandata_file + " ("
            + scan01 + " TEXT," + scan02 + " TEXT," + scan03 + " TEXT,"
            //+ scan04 + " TEXT," + scan05 + " TEXT," + scan06 + " TEXT,"
            + scan06 + " TEXT,"
            + scan07 + " TEXT," + scan08 + " TEXT," + scan09 + " TEXT,"     // by Andy added 23032301
            + scan10 + " TEXT," + scan11 + " TEXT," + scan12 + " TEXT )";   // by Andy added 23032301
    //+ scanqrcode + " TEXT," + scanlocation + " TEXT," + scanfactory + " TEXT )";  // by Andy mark 23032301

    String CREATE_TB_setup_data_file = "CREATE TABLE IF NOT EXISTS " + TB_setup_data_file + " ("
            + sdata01 + " TEXT," + sdata02 + " TEXT," + sdata03 + " TEXT," + sdata04 + " TEXT," + sdata05 + " TEXT )";

    String CREATE_TB_total_sdata_file = "CREATE TABLE IF NOT EXISTS " + TB_total_sdata_file + " ("
            + sdata01 + " TEXT," + sdata02 + " TEXT," + sdata03 + " TEXT," + sdata04 + " TEXT," + sdata05 + " TEXT )";

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
            db.execSQL(CREATE_TB_setup_data_file);
            db.execSQL(CREATE_TB_total_sdata_file);
        } catch (Exception e) {

        }
    }

    public void close() {
        try {
            String DROP_TABLE_TC_FAB = "DROP TABLE IF EXISTS " + TB_basic_data_file;
            db.execSQL(DROP_TABLE_TC_FAB);
            String DROP_TABLE_scandata_file = "DROP TABLE IF EXISTS " + TB_scandata_file;
            db.execSQL(DROP_TABLE_scandata_file);
            String DROP_TABLE_TB_setup_data_file = "DROP TABLE IF EXISTS " + TB_setup_data_file;
            db.execSQL(DROP_TABLE_TB_setup_data_file);
            String DROP_TABLE_TB_total_sdata_file = "DROP TABLE IF EXISTS " + TB_total_sdata_file;
            db.execSQL(DROP_TABLE_TB_total_sdata_file);
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
            db.insert(TB_basic_data_file, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    /*public String insScanData(String g_scan01, String g_scan02, String g_scan03, String g_scan04,
                              String g_scan05, String g_scan06, String g_scanqrcode, String g_scanlocation, String g_scanfactory) {*/

    public String insScanData(String g_scan01, String g_scan02, String g_scan03, String g_scan06,
                              //String g_scan04, String g_scan05, String g_scan07, String g_scan08, String g_scan09,
                              String g_scan07, String g_scan08, String g_scan09,
                              String g_scan10, String g_scan11, String g_scan12) {
        try {
            ContentValues args = new ContentValues();
            args.put(scan01, g_scan01);
            args.put(scan02, g_scan02);
            args.put(scan03, g_scan03);
            args.put(scan06, g_scan06);
            //args.put(scan04, g_scan04);
            //args.put(scan05, g_scan05);
            args.put(scan07, g_scan07);
            args.put(scan08, g_scan08);
            args.put(scan09, g_scan09);
            args.put(scan10, g_scan10);
            args.put(scan11, g_scan11);
            args.put(scan12, g_scan12);
            /*args.put(scanqrcode, g_scanqrcode);
            args.put(scanlocation, g_scanlocation);
            args.put(scanfactory, g_scanfactory);*/
            db.insert(TB_scandata_file, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    //thiết lập dữ liệu
    public Cursor getAll_setup_data() {
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT 0 _id,setup01,setup02,setup03 FROM setup_data_file  ORDER BY 2,3,4  ";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    //kiểm tra dữ liệu show lên dialog
    public Cursor getAll_setup_01() {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT distinct setup01 FROM setup_data_file ORDER BY 1";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_setup_02(String l_xuong) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT distinct setup02 FROM setup_data_file WHERE setup01='" + l_xuong + "' ORDER BY 1";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    //Lấy dữ liệu show lên stand
    public Cursor getAll_stand_01(String l_stxuong, String l_stkhu) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT setup03 FROM setup_data_file WHERE setup01='" + l_stxuong + "' AND setup02='" + l_stkhu + "' ORDER BY 1";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    //check đã có dữ liệu TB_total_sdata_file
    public Cursor getAll_count_total_sdata(String sd_Xuong, String sd_Khu, String sd_Vitri, String sd_Doncong) {
        Cursor a;
        try {
            String selectQuery = "SELECT count(*) as count_sdata FROM total_sdata_file WHERE sdata01='" + sd_Xuong + "' " +
                    "and sdata02='" + sd_Khu + "' and sdata03='" + sd_Vitri + "' and sdata04='" + sd_Doncong + "' ORDER BY 1";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_count_01(String l_count1, String l_count2, String l_count3) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT distinct setup02 FROM setup_data_file WHERE setup01='" + l_count1 + "' " +
                    "and setup02='" + l_count2 + "' and setup03='" + l_count3 + "' ORDER BY 1";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    // insert setup_data
    public String insSetup_data(String g_setup01, String g_setup02, String g_setup03) {
        try {
            ContentValues args = new ContentValues();
            args.put(setup01, g_setup01);
            args.put(setup02, g_setup02);
            args.put(setup03, g_setup03);
            db.insert(TB_setup_data_file, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    //insert  total_sdata
    public String insTotal_sdata(String g_sdata01, String g_sdata02, String g_sdata03, String g_sdata04, String g_sdata05) {
        try {
            ContentValues args = new ContentValues();
            args.put(sdata01, g_sdata01);
            args.put(sdata02, g_sdata02);
            args.put(sdata03, g_sdata03);
            args.put(sdata04, g_sdata04);
            args.put(sdata05, g_sdata05);
            db.insert(TB_total_sdata_file, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }


    //update số lượng vào TB_total_sdata_file
    public void upd_total_sdata(String gXuong, String gKhu, String gVitri, String gDoncong, String soluong, String gINOUT) {
        if (gINOUT.equals("IN")) {
            db.execSQL("UPDATE " + TB_total_sdata_file +
                    " SET sdata05 = (sdata05 + " + soluong + ") " +
                    " WHERE sdata01 = '" + gXuong + "' And sdata02 = '" + gKhu + "' And sdata03 = '" + gVitri + "' " +
                    " And sdata04 = '" + gDoncong + "' ");
        } else {
            db.execSQL("UPDATE " + TB_total_sdata_file +
                    " SET sdata05 = (sdata05 - " + soluong + ") " +
                    " WHERE sdata01 = '" + gXuong + "' And sdata02 = '" + gKhu + "' And sdata03 = '" + gVitri + "' " +
                    " And sdata04 = '" + gDoncong + "' ");
        }

    }

    public void delete_table() {
        db.delete(TB_basic_data_file, null, null);
    }

    public Cursor getall(String g_xuong, String g_khu, String g_vitri) {
        try {
            String selectQuery = "SELECT * FROM " + TB_scandata_file + " WHERE scan07='" + g_xuong + "' " +
                    "AND scan07='" + g_khu + "' AND scan09='" + g_vitri + "'";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public void delData(String g_xuong, String idButton) {
        String condition = "scanfactory = ? and scanlocation = ?";
        String[] conditionArgs = new String[]{g_xuong, idButton};
        db.delete(TB_scandata_file, condition, conditionArgs);
    }

    //Kiểm tra vị trí được quét chưa
    public int check_tb_scandata_file(String g_scanlocation, String g_scanfactory) {
        int q_count = -1;
        Cursor res = db.query(TB_scandata_file,
                new String[]{"rowid _id", scan01},
                /*scanfactory + "=? AND " + scanlocation + "=? ",
                new String[]{g_scanfactory, g_scanlocation},*/
                scan07 + "=? AND " + scan08 + "=? AND " + scan09 + "=? ",
                new String[]{g_scanfactory, g_scanlocation},
                null, null, null, null);
        res.moveToFirst();
        if (res.getCount() > 0) {
            q_count = res.getCount();
        } else {
            q_count = 0;
        }
        return q_count;
    }

    public void upd_BasicData(String gXuong, String gKhu, String gVitri, String i) {
        db.execSQL("UPDATE " + TB_basic_data_file +
                " SET dulieu05 = dulieu05 " + i + " " +
                " WHERE dulieu01 = '" + gXuong + "' And dulieu02 = '" + gKhu + "' And dulieu03 = '" + gVitri + "' ");
    }
}
