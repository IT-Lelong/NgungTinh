package com.lelong.ngungtinh.KTnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lelong.ngungtinh.OpenScaner;
import com.lelong.ngungtinh.R;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //A1
        TextView A101 =this.findViewById(R.id.A101);
        TextView A102 =this.findViewById(R.id.A102);
        TextView A103 =this.findViewById(R.id.A103);
        TextView A104 =this.findViewById(R.id.A104);
        //A2
        TextView A201 =this.findViewById(R.id.A201);
        TextView A202 =this.findViewById(R.id.A202);
        TextView A203 =this.findViewById(R.id.A203);
        TextView A204 =this.findViewById(R.id.A204);
        //A3
        TextView A301 =this.findViewById(R.id.A301);
        TextView A302 =this.findViewById(R.id.A302);
        TextView A303 =this.findViewById(R.id.A303);
        TextView A304 =this.findViewById(R.id.A304);

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


    }
    private View.OnClickListener btnlistener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //A1
                case R.id.A101: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A101");
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                case R.id.A102: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A102");
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A103: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A103");
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A104: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A104");
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
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                case R.id.A202: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A202");
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A203: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A203");
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A204: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A204");
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
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                case R.id.A302: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A302");
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A303: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A303");
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
                case R.id.A304: {
                    Intent QR010 = new Intent();
                    QR010.setClass(MainActivity2.this, OpenScaner.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("IDButton", "A304");
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }
            }
        }
    };
}