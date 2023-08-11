package com.lelong.ngungtinh;

public class nt_lv_dsdata1 {
    String vtri;
    String doncong;
    String quycach;
    String msac;
    String dcuc;
    int sluong;


    public void setImg02(String vtri) {
        this.vtri = vtri;
    }

    public void setImg03(String doncong) {
        this.doncong = doncong;
    }

    public void setImg01(String quycach) {
        this.quycach = quycach;
    }

    public void setTen(String msac) {
        this.msac = msac;
    }

    public void setIma27(String dcuc) {
        this.dcuc = dcuc;
    }

    public void setImg10(int sluong) {
        this.sluong = sluong;
    }

    public nt_lv_dsdata1(String vtri, String doncong, String quycach, String msac, String dcuc, int sluong) {
        this.vtri = vtri;
        this.doncong = doncong;
        this.quycach = quycach;
        this.msac = msac;
        this.dcuc = dcuc;
        this.sluong = sluong;

        //this.checkBox = checkBox;
    }
    public String getImg02() {
        return vtri;
    }

    public String getImg03() {
        return doncong;
    }

    public String getImg01() {
        return quycach;
    }

    public String getTen() {
        return msac;
    }

    public String getIma27() {
        return dcuc;
    }

    public int getImg10() {
        return sluong;
    }
}
