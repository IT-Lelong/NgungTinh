package com.lelong.ngungtinh.KTnew;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lelong.ngungtinh.Create_Table;
import com.lelong.ngungtinh.OpenScaner;
import com.lelong.ngungtinh.R;

public class MainActivity2 extends AppCompatActivity {
    private Create_Table db = null;
    String g_xuong, g_vitri;

    //A1
    //TextView A101, A102, A103, A104;
    TextView A101, A102, A103, A104, A105, A106, A107, A108, A109, A110, A111, A112;
    //A2
    //TextView A201, A202, A203, A204;
    TextView A201, A202, A203, A204, A205, A206, A207, A208, A209, A210, A211, A212;
    //A3
    //TextView A301, A302, A303, A304;
    TextView A301, A302, A303, A304, A305, A306, A307, A308, A309, A310, A311, A312;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        db = new Create_Table(this);
        db.open();

        //A1
        A101 = this.findViewById(R.id.A101);
        A102 = this.findViewById(R.id.A102);
        A103 = this.findViewById(R.id.A103);
        A104 = this.findViewById(R.id.A104);

        A105 = this.findViewById(R.id.A105);
        A106 = this.findViewById(R.id.A106);
        A107 = this.findViewById(R.id.A107);
        A108 = this.findViewById(R.id.A108);

        A109 = this.findViewById(R.id.A109);
        A110 = this.findViewById(R.id.A110);
        A111 = this.findViewById(R.id.A111);
        A112 = this.findViewById(R.id.A112);

        //A2
        A201 = this.findViewById(R.id.A201);
        A202 = this.findViewById(R.id.A202);
        A203 = this.findViewById(R.id.A203);
        A204 = this.findViewById(R.id.A204);

        A205 = this.findViewById(R.id.A205);
        A206 = this.findViewById(R.id.A206);
        A207 = this.findViewById(R.id.A207);
        A208 = this.findViewById(R.id.A208);

        A209 = this.findViewById(R.id.A209);
        A210 = this.findViewById(R.id.A210);
        A211 = this.findViewById(R.id.A211);
        A212 = this.findViewById(R.id.A212);

        //A3
        A301 = this.findViewById(R.id.A301);
        A302 = this.findViewById(R.id.A302);
        A303 = this.findViewById(R.id.A303);
        A304 = this.findViewById(R.id.A304);

        A305 = this.findViewById(R.id.A305);
        A306 = this.findViewById(R.id.A306);
        A307 = this.findViewById(R.id.A307);
        A308 = this.findViewById(R.id.A308);

        A309 = this.findViewById(R.id.A309);
        A310 = this.findViewById(R.id.A310);
        A311 = this.findViewById(R.id.A311);
        A312 = this.findViewById(R.id.A312);

        //A1
        A101.setOnClickListener(btnlistener);
        A102.setOnClickListener(btnlistener);
        A103.setOnClickListener(btnlistener);
        A104.setOnClickListener(btnlistener);

        A105.setOnClickListener(btnlistener);
        A106.setOnClickListener(btnlistener);
        A107.setOnClickListener(btnlistener);
        A108.setOnClickListener(btnlistener);

        A109.setOnClickListener(btnlistener);
        A110.setOnClickListener(btnlistener);
        A111.setOnClickListener(btnlistener);
        A112.setOnClickListener(btnlistener);

        //A2
        A201.setOnClickListener(btnlistener);
        A202.setOnClickListener(btnlistener);
        A203.setOnClickListener(btnlistener);
        A204.setOnClickListener(btnlistener);

        A205.setOnClickListener(btnlistener);
        A206.setOnClickListener(btnlistener);
        A207.setOnClickListener(btnlistener);
        A208.setOnClickListener(btnlistener);

        A209.setOnClickListener(btnlistener);
        A210.setOnClickListener(btnlistener);
        A211.setOnClickListener(btnlistener);
        A212.setOnClickListener(btnlistener);

        //A3
        A301.setOnClickListener(btnlistener);
        A302.setOnClickListener(btnlistener);
        A303.setOnClickListener(btnlistener);
        A304.setOnClickListener(btnlistener);

        A305.setOnClickListener(btnlistener);
        A306.setOnClickListener(btnlistener);
        A307.setOnClickListener(btnlistener);
        A308.setOnClickListener(btnlistener);

        A309.setOnClickListener(btnlistener);
        A310.setOnClickListener(btnlistener);
        A311.setOnClickListener(btnlistener);
        A312.setOnClickListener(btnlistener);
        Bundle getBundle = getIntent().getExtras();
        g_xuong = getBundle.getString("xuong");
        KT_Dulieu(A101);
        KT_Dulieu(A102);
        KT_Dulieu(A103);
        KT_Dulieu(A104);
        KT_Dulieu(A105);
        KT_Dulieu(A106);
        KT_Dulieu(A107);
        KT_Dulieu(A108);
        KT_Dulieu(A109);
        KT_Dulieu(A110);
        KT_Dulieu(A111);
        KT_Dulieu(A112);

        KT_Dulieu(A201);
        KT_Dulieu(A202);
        KT_Dulieu(A203);
        KT_Dulieu(A204);
        KT_Dulieu(A205);
        KT_Dulieu(A206);
        KT_Dulieu(A207);
        KT_Dulieu(A208);
        KT_Dulieu(A209);
        KT_Dulieu(A210);
        KT_Dulieu(A211);
        KT_Dulieu(A212);

        KT_Dulieu(A301);
        KT_Dulieu(A302);
        KT_Dulieu(A303);
        KT_Dulieu(A304);
        KT_Dulieu(A305);
        KT_Dulieu(A306);
        KT_Dulieu(A307);
        KT_Dulieu(A308);
        KT_Dulieu(A309);
        KT_Dulieu(A310);
        KT_Dulieu(A311);
        KT_Dulieu(A312);

    }

    @Override
    protected void onResume() {
        super.onResume();
        KT_Dulieu(A101);
        KT_Dulieu(A102);
        KT_Dulieu(A103);
        KT_Dulieu(A104);
        KT_Dulieu(A105);
        KT_Dulieu(A106);
        KT_Dulieu(A107);
        KT_Dulieu(A108);
        KT_Dulieu(A109);
        KT_Dulieu(A110);
        KT_Dulieu(A111);
        KT_Dulieu(A112);

        KT_Dulieu(A201);
        KT_Dulieu(A202);
        KT_Dulieu(A203);
        KT_Dulieu(A204);
        KT_Dulieu(A205);
        KT_Dulieu(A206);
        KT_Dulieu(A207);
        KT_Dulieu(A208);
        KT_Dulieu(A209);
        KT_Dulieu(A210);
        KT_Dulieu(A211);
        KT_Dulieu(A212);

        KT_Dulieu(A301);
        KT_Dulieu(A302);
        KT_Dulieu(A303);
        KT_Dulieu(A304);
        KT_Dulieu(A305);
        KT_Dulieu(A306);
        KT_Dulieu(A307);
        KT_Dulieu(A308);
        KT_Dulieu(A309);
        KT_Dulieu(A310);
        KT_Dulieu(A311);
        KT_Dulieu(A312);
    }


    private void KT_Dulieu(TextView g_vitri) {
        int chk_num = db.check_tb_scandata_file(String.valueOf(g_vitri).substring(String.valueOf(g_vitri).length() - 5, String.valueOf(g_vitri).length() - 1), g_xuong);
        if (chk_num > 0) {
            g_vitri.setBackgroundResource(R.drawable.border_listview_red);
            g_vitri.setTextColor(getResources().getColor(R.color.white));
            g_vitri.setPadding(0,3,0,3);
        } else {
            g_vitri.setBackgroundResource(R.drawable.boder_title_view);
            g_vitri.setTextColor(getResources().getColor(R.color.white));
            g_vitri.setPadding(0,3,0,3);
        }
    }

    private View.OnClickListener btnlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //A1
                case R.id.A101: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A101");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A102: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A102");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A103: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A103");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A104: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A104");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                case R.id.A105: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A105");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A106: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A106");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A107: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A107");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A108: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A108");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                case R.id.A109: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A109");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A110: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A110");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A111: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A111");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A112: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A112");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                //A2
                case R.id.A201: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A201");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A202: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A202");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A203: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A203");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A204: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A204");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                case R.id.A205: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A205");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A206: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A206");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A207: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A207");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A208: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A208");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                case R.id.A209: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A209");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A210: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A210");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A211: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A211");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A212: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A212");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                //A03
                case R.id.A301: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A301");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A302: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A302");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A303: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A303");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A304: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A304");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                case R.id.A305: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A305");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A306: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A306");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A307: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A307");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A308: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A308");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                case R.id.A309: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A309");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A310: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A310");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A311: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A311");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A312: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A312");
                    bundle.putString("xuong", g_xuong);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
            }
        }
    };
}