package com.lelong.ngungtinh;

import java.io.Serializable;

public class List_setupdata {
    private String setup01;
    private String setup02;
    private String setup03;
    private String setup04;

    public String getSetup01() {
        return setup01;
    }

    public void setSetup01(String setup01) {
        this.setup01 = setup01;
    }

    public String getSetup02() {
        return setup02;
    }

    public void setSetup02(String setup02) {
        this.setup02 = setup02;
    }

    public String getSetup03() {
        return setup03;
    }

    public void setSetup03(String setup03) {
        this.setup03 = setup03;
    }

    public String getSetup04() {
        return setup04;
    }

    public void setSetup04(String setup04) {
        this.setup04 = setup04;
    }

    public List_setupdata(String setup01, String setup02, String setup03, String setup04) {
        this.setup01 = setup01;
        this.setup02 = setup02;
        this.setup03 = setup03;
        this.setup04 = setup04;
    }
}
