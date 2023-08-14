package com.lelong.ngungtinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Create_Table {
    private Call_interface callInterface;
    private Context mCtx = null;
    String DATABASE_NAME = "NgungTinhDB.db";
    public SQLiteDatabase db = null;

    //Tạo table chứa mã vật liệu
    String TABLE_NAME_IMA = "ima_file";

    //
    String TB_basic_data_file = "basic_data_file";
    String dulieu01 = "dulieu01"; //Mã Xưởng
    String dulieu02 = "dulieu02"; //Mã Khu
    String dulieu03 = "dulieu03"; //Mã Vị trí
    String dulieu04 = "dulieu04"; //Tổng sức chứa
    String dulieu05 = "dulieu05"; //Đã sử dụng

    //Thiết lập dữ liệu lưu lịch sử quét
    String TB_scandata_file = "scandata_file";
    String scan00 = "scan00"; //Key
    String scan01 = "scan01"; //Mã đơn công
    String scan02 = "scan02"; //Quy cách
    String scan03 = "scan03"; //Sô lượng
    String scan04 = "scan04"; //Tuan
    String scan05 = "scan05"; //Ngày sx
    String scan06 = "scan06"; //Ngày ngưng tịnh
    String scan07 = "scan07"; //Xưởng
    String scan08 = "scan08"; //Khu
    String scan09 = "scan09"; //Vị trí
    String scan10 = "scan10"; //INOUT
    String scan11 = "scan11"; //ID người quét
    String scan12 = "scan12"; //Ngày quét
    String scan13 = "scan13"; //Màu sắc
    String scan14 = "scan14"; //Đầu cực
    String scanchk = "scanchk"; //Check


    //thiết lập dữ liệu xưởng
    String TB_setup_data_file = "setup_data_file";
    String setup01 = "setup01"; //Xưởng
    String setup02 = "setup02"; //Khu
    String setup03 = "setup03"; //Vị trí con
    //String setup04 = "setup04"; //Số lượng

    //tổng dữ liệu quét nhập xuất
    String TB_total_sdata_file = "total_sdata_file";
    String sdata01 = "sdata01"; //Xưởng
    String sdata02 = "sdata02"; //Khu
    String sdata03 = "sdata03"; //Vị trí con
    String sdata04 = "sdata04"; //Đơn công
    String sdata05 = "sdata05"; //Số lượng
    String sdata06 = "sdata06"; //Quy cách
    String sdata07 = "sdata07"; //Ngày quét vào
    String sdata08 = "sdata08"; //Màu sắc
    String sdata09 = "sdata09"; //Điện cuc

    String CREATE_TB_basic_data_file = "CREATE TABLE IF NOT EXISTS " + TB_basic_data_file + " ("
            + dulieu01 + " TEXT," + dulieu02 + " TEXT," + dulieu03 + " TEXT,"
            + dulieu04 + " DECIMAL," + dulieu05 + " DECIMAL)";

    String CREATE_TABLE_scandata = "CREATE TABLE IF NOT EXISTS " + TB_scandata_file + " ("
            + scan00 + " TEXT," + scan01 + " TEXT," + scan02 + " TEXT," + scan03 + " TEXT,"
            + scan04 + " TEXT," + scan05 + " TEXT," + scan06 + " TEXT,"
            //+ scan06 + " TEXT,"
            + scan07 + " TEXT," + scan08 + " TEXT," + scan09 + " TEXT,"     // by Andy added 23032301
            + scan10 + " TEXT," + scan11 + " TEXT," + scan12 + " TEXT," + scan13 + " TEXT,"
            + scan14 + " TEXT," + scanchk + " TEXT )";   // by Andy added 23032301
    //+ scanqrcode + " TEXT," + scanlocation + " TEXT," + scanfactory + " TEXT )";  // by Andy mark 23032301

    String CREATE_TB_setup_data_file = "CREATE TABLE IF NOT EXISTS " + TB_setup_data_file + " ("
            + setup01 + " TEXT," + setup02 + " TEXT," + setup03 + " TEXT)";

    String CREATE_TB_total_sdata_file = "CREATE TABLE IF NOT EXISTS " + TB_total_sdata_file + " ("
            + sdata01 + " TEXT," + sdata02 + " TEXT," + sdata03 + " TEXT," + sdata04 + " TEXT," + sdata05 + " TEXT,"
            + sdata06 + " TEXT," + sdata07 + " TEXT," + sdata08 + " TEXT," + sdata09 + " TEXT )";

    String CREATE_TABLE_IMA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_IMA + " (ima01 TEXT, imaud04 TEXT)";

    public Create_Table(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
    }
    public void setInsertCallback(Call_interface callback) {
        this.callInterface = callback;
    }

    public void createTable() {
        try {
            db.execSQL(CREATE_TB_basic_data_file);
            db.execSQL(CREATE_TABLE_scandata);
            db.execSQL(CREATE_TB_setup_data_file);
            db.execSQL(CREATE_TB_total_sdata_file);
            db.execSQL(CREATE_TABLE_IMA);
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
            String DROP_TABLE_TB_ima_file = "DROP TABLE IF EXISTS " + TABLE_NAME_IMA;
            db.execSQL(DROP_TABLE_TB_ima_file);
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

    public String insScanData(String g_scan00, String g_scan01, String g_scan02, String g_scan03,
                              String g_scan04, String g_scan05, String g_scan06, String g_scan07,
                              String g_scan08, String g_scan09, String g_scan10, String g_scan11,
                              String g_scan12, String g_scan13, String g_scan14, String g_scanchk) {
        try {
            ContentValues args = new ContentValues();
            args.put(scan00, g_scan00);
            args.put(scan01, g_scan01);
            args.put(scan02, g_scan02);
            args.put(scan03, g_scan03);
            args.put(scan06, g_scan06);
            args.put(scan04, g_scan04);
            args.put(scan05, g_scan05);
            args.put(scan07, g_scan07);
            args.put(scan08, g_scan08);
            args.put(scan09, g_scan09);
            args.put(scan10, g_scan10);
            args.put(scan11, g_scan11);
            args.put(scan12, g_scan12);
            args.put(scan13, g_scan13);
            args.put(scan14, g_scan14);
            args.put(scanchk, g_scanchk);
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
            String selectQuery = "SELECT 0 _id,setup01,setup02,setup03  FROM setup_data_file  ORDER BY setup01,setup02,setup03 ASC  ";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    //show dữ liệu vị trí

    public Cursor getAll_ntds_data(String ntds_xuong, String ntds_khu, String ntds_vitri) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT 0 _id,sdata03,sdata04,sdata06,sdata08,sdata09,sdata05  FROM total_sdata_file " +
                    "WHERE sdata01='" + ntds_xuong + "' and sdata02='" + ntds_khu + "' and sdata03='" + ntds_vitri + "' and sdata05 >0 ORDER BY 2,3,4  ";
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

    //Kiểm tra số lượng hiện có trong vị trí con
    public Cursor getAll_check_total_sdata(String sd_Xuong, String sd_Khu, String sd_Vitri) {
        Cursor a;
        try {
            String selectQuery = "SELECT * FROM (SELECT ifnull(sum(sdata05),0) as sdata05 FROM total_sdata_file WHERE sdata01='" + sd_Xuong + "' " +
                    "and sdata02='" + sd_Khu + "' and sdata03='" + sd_Vitri + "'  ORDER BY 1) LIMIT 0, 1 ";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    //Kiểm tra số lượng hiện có trong vị trí con
    public Cursor getAll_check_total_sdata2(String sd_Xuong, String sd_Khu, String sd_Vitri, String sd_doncong) {
        Cursor a;
        try {
            String selectQuery = "SELECT * FROM (SELECT ifnull(sdata05,0) as sdata05 FROM total_sdata_file WHERE sdata01='" + sd_Xuong + "' " +
                    "and sdata02='" + sd_Khu + "' and sdata03='" + sd_Vitri + "' and sdata04='" + sd_doncong + "'  ORDER BY 1) LIMIT 0, 1 ";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    //Trả về số lượng có thể chứa hiện có trong vị trí con
    /*public Cursor getAll_standard(String sd_Xuong, String sd_Khu, String sd_Vitri) {
        Cursor a;
        try {
            String selectQuery = "SELECT setup04 FROM setup_data_file WHERE setup01='" + sd_Xuong + "' " +
                    "and setup02='" + sd_Khu + "' and setup03='" + sd_Vitri + "' ORDER BY 1";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }*/

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
    public String insTotal_sdata(String g_sdata01, String g_sdata02, String g_sdata03, String g_sdata04, String g_sdata05,
                                 String g_sdata06, String g_sdata07, String g_sdata08, String g_sdata09) {
        try {
            ContentValues args = new ContentValues();
            args.put(sdata01, g_sdata01);
            args.put(sdata02, g_sdata02);
            args.put(sdata03, g_sdata03);
            args.put(sdata04, g_sdata04);
            args.put(sdata05, g_sdata05);
            args.put(sdata06, g_sdata06);
            args.put(sdata07, g_sdata07);
            args.put(sdata08, g_sdata08);
            args.put(sdata09, g_sdata09);
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

    //update số lượng vào TB_total_sdata_file
    public void upd_chk_scan(String gKey, String gChk) {

        db.execSQL("UPDATE " + TB_scandata_file +
                " SET scanchk = '" + gChk + "'" +
                " WHERE scan00 = '" + gKey + "' ");
    }

    //del setup_file trên máy
    public void del_setup() {
        db.execSQL("delete from " + TB_setup_data_file);
    }

    //del total_file trên máy
    public void del_total() {
        db.execSQL("delete from " + TB_total_sdata_file);
    }

    //update setup_file
    /*public void upd_setup_file(String gXuong, String gKhu, String gVitri, String gsl) {
        db.execSQL("UPDATE " + TB_setup_data_file +
                " SET setup04 = '" + gsl + "' " +
                " WHERE setup01 = '" + gXuong + "' And setup02 = '" + gKhu + "' And setup03 = '" + gVitri + "' ");
    }*/

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

    //kiểm tra dữ liệu quy cach
    public Cursor getAll_sdata_06() {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT distinct sdata06 FROM total_sdata_file ORDER BY 1";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    //upload data tc_bac
    public Cursor getAll_tc_bac(String chk) {
        try {
            return db.rawQuery("SELECT scan00,scan01,scan02,scan03,scan04,scan05,scan06,scan07,scan08,scan09,scan10,scan11,scan12,scan13,scan14"

                    + " FROM " + TB_scandata_file + " where scanchk = '" + chk + "'  ", null);
        } catch (Exception e) {
            return null;
        }
    }

    //upload data tc_bac_1
    public Cursor getAll_tc_bac_1(String l_key) {
        try {
            return db.rawQuery("SELECT scan00,scan01,scan02,scan03,scan04,scan05,scan06,scan07,scan08,scan09,scan10,scan11,scan12,scan13,scan14"

                    + " FROM " + TB_scandata_file + " where scan00 = '" + l_key + "' ", null);
        } catch (Exception e) {
            return null;
        }
    }

    //upload data tc_bad
    public Cursor getAll_tc_bad(String app) {
        try {
            return db.rawQuery("SELECT sdata01,sdata02,sdata03,sdata04,sdata05,sdata06"

                    + " FROM " + TB_total_sdata_file + "", null);
        } catch (Exception e) {
            return null;
        }
    }

    //upload data tc_bae
    public Cursor getAll_tc_bae(String tsetup01, String tsetup02, String tsetup03) {
        try {
            String selectQuery = "SELECT setup01,setup02,setup03 FROM setup_data_file WHERE setup01='" + tsetup01 + "' " +
                    "AND setup02='" + tsetup02 + "' AND setup03='" + tsetup03 + "' ORDER BY 1";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    //delete data tc_bae
    public Cursor del_tc_bae(String dsetup01, String dsetup02, String dsetup03) {
        try {
            String selectQuery = "SELECT setup01,setup02,setup03 FROM setup_data_file WHERE setup01='" + dsetup01 + "' " +
                    "AND setup02='" + dsetup02 + "' AND setup03='" + dsetup03 + "' ORDER BY 1";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    //delete setup_data_file
    public void del_setup_data_file(String qry_xuong, String qry_khu, String qry_vitri) {
        String whereClause_hm0102 = "setup01=? AND setup02=? AND setup03=?";
        String[] strings = new String[]{qry_xuong, qry_khu, qry_vitri};
        db.delete(TB_setup_data_file, whereClause_hm0102, strings);
    }

    public Cursor getAll_sdata_01(String l_quycach) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT distinct sdata01 FROM total_sdata_file WHERE sdata06='" + l_quycach + "' ORDER BY 1";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    //ins_setup
    public String append_setup(String g_setup01, String g_setup02, String g_setup03) {
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

    //ins_total
    public String append_total(String g_setup01, String g_setup02, String g_setup03, String g_setup04, String g_setup05, String g_setup06, String g_setup07, String g_setup08, String g_setup09) {
        try {
            ContentValues args = new ContentValues();
            args.put(sdata01, g_setup01);
            args.put(sdata02, g_setup02);
            args.put(sdata03, g_setup03);
            args.put(sdata04, g_setup04);
            args.put(sdata05, g_setup05);
            args.put(sdata06, g_setup06);
            args.put(sdata07, g_setup07);
            args.put(sdata08, g_setup08);
            args.put(sdata09, g_setup09);
            db.insert(TB_total_sdata_file, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }

    }

    public Cursor getAll_search_data(String l_doncong, String l_quycach, String l_xuong, String l_mausac, String l_daucuc) {
        Cursor a;
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String str = "";
            /*if (!l_vaont.equals("") && !l_rant.equals("")) {
                str = str + " AND scan06 BETWEEN '" + l_vaont + "' AND '" + l_rant + "'  ";
            }*/
            if (!l_doncong.equals("")) {
                str = str + " AND sdata04 = '" + l_doncong + "' ";
            }
            if (!l_quycach.equals("")) {
                str = str + " AND sdata06= '" + l_quycach + "' ";
            }
            if (!l_xuong.equals("")) {
                str = str + " AND sdata01= '" + l_xuong + "' ";
            }
            if (!l_mausac.equals("")) {
                str = str + " AND sdata08= '" + l_mausac + "' ";
            }
            if (!l_daucuc.equals("")) {
                str = str + " AND sdata09= '" + l_daucuc + "' ";
            }

            String selectQuery = "SELECT distinct 0 _id,sdata01,sdata02,sdata03, sdata04,sdata06,sdata05,sdata08,sdata09,sdata07 FROM total_sdata_file  " +
                    " WHERE 1 = 1  ";
            String sqlorder = "AND sdata05 > 0  ORDER BY 2,3,4,5,8 ";
            selectQuery = selectQuery + str + sqlorder;
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public class InsertDataTask extends AsyncTask<String, Integer, Integer> {
        int progress;
        private ProgressBar progressBar;

        public InsertDataTask(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        protected Integer doInBackground(String... params) {
            String jsonData = params[0];
            try {
                JSONArray jsonArray = new JSONArray(jsonData);
                int totalItems = jsonArray.length();
                for (int i = 0; i < totalItems; i++) {
                    JSONObject jsonObject;
                    // Thực hiện insert dữ liệu từ jsonObject
                    try {
                        jsonObject = jsonArray.getJSONObject(i);
                        String g_ima01 = jsonObject.getString("IMA01");
                        String g_imaud04 = jsonObject.getString("IMAUD04");

                        insert_ima_file(g_ima01, g_imaud04);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    // Cập nhật tiến độ
                    progress = (int) (((i + 1) / (float) totalItems) * 100);
                    publishProgress(progress);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return progress;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
            // Cập nhật tiến độ insert dữ liệu trên giao diện
            progressBar.setProgress(progress); // Cập nhật tiến trình trên ProgressBar
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Hoàn thành quá trình insert
            if (result == 100) {
                callInterface.ImportData_onInsertComplete("OK");
            }
        }


    }

    private void insert_ima_file(String g_ima01, String g_imaud04) {
        try {
            ContentValues args = new ContentValues();
            args.put("ima01", g_ima01);
            args.put("imaud04", g_imaud04);
            db.insert(TABLE_NAME_IMA, null, args);
        } catch (Exception e) {
        }
    }

    public Cursor getBatteryData() {
        String selectQuery = "SELECT ima01,imaud04 FROM ima_file ORDER BY imaud04,ima01";
        return db.rawQuery(selectQuery, null);
    }
}
