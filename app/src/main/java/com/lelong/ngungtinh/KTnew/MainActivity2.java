package com.lelong.ngungtinh.KTnew;

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
    TextView A101 ,A102 ,A103 ,A104 ;
    //A2
    TextView A201 ,A202,A203 ,A204 ;
    //A3
    TextView A301 ,A302 ,A303 ,A304 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        db = new Create_Table(this);
        db.open();

        //A1
         A101 = this.findViewById(R.id.A101);
         A102 = this.findViewById(R.id.A102);
         A103 = this.findViewById(R.id.A103);
         A104 = this.findViewById(R.id.A104);
        //A2
         A201 = this.findViewById(R.id.A201);
         A202 = this.findViewById(R.id.A202);
         A203 = this.findViewById(R.id.A203);
         A204 = this.findViewById(R.id.A204);
        //A3
         A301 = this.findViewById(R.id.A301);
         A302 = this.findViewById(R.id.A302);
         A303 = this.findViewById(R.id.A303);
         A304 = this.findViewById(R.id.A304);

        //A1
        A101.setOnClickListener(btnlistener);
        A102.setOnClickListener(btnlistener);
        A103.setOnClickListener(btnlistener);
        A104.setOnClickListener(btnlistener);
        //A2
        A201.setOnClickListener(btnlistener);
        A202.setOnClickListener(btnlistener);
        A203.setOnClickListener(btnlistener);
        A204.setOnClickListener(btnlistener);
        //A3
        A301.setOnClickListener(btnlistener);
        A302.setOnClickListener(btnlistener);
        A303.setOnClickListener(btnlistener);
        A304.setOnClickListener(btnlistener);

        Bundle getBundle = getIntent().getExtras();
        g_xuong = getBundle.getString("xuong");
        KT_Dulieu(A101);
        KT_Dulieu(A102);
        KT_Dulieu(A103);
        KT_Dulieu(A104);
        KT_Dulieu(A201);
        KT_Dulieu(A202);
        KT_Dulieu(A203);
        KT_Dulieu(A204);
        KT_Dulieu(A301);
        KT_Dulieu(A302);
        KT_Dulieu(A303);
        KT_Dulieu(A304);
    }

    @Override
    protected void onResume() {
        super.onResume();
        KT_Dulieu(A101);
        KT_Dulieu(A102);
        KT_Dulieu(A103);
        KT_Dulieu(A104);
        KT_Dulieu(A201);
        KT_Dulieu(A202);
        KT_Dulieu(A203);
        KT_Dulieu(A204);
        KT_Dulieu(A301);
        KT_Dulieu(A302);
        KT_Dulieu(A303);
        KT_Dulieu(A304);
    }


    private void KT_Dulieu(TextView g_vitri) {
        int chk_num = db.check_tb_scandata_file(String.valueOf(g_vitri).substring(String.valueOf(g_vitri).length() - 5, String.valueOf(g_vitri).length() - 1), g_xuong);
        if (chk_num > 0) {
            g_vitri.setBackgroundResource(R.drawable.border_listview_red);
            g_vitri.setTextColor(getResources().getColor(R.color.white));
            } else {
            g_vitri.setBackgroundResource(R.drawable.boder_title_view);
            g_vitri.setTextColor(getResources().getColor(R.color.white));
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
            }
        }
    };
}