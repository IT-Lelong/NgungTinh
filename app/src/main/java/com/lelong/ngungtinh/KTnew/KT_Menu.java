package com.lelong.ngungtinh.KTnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.lelong.ngungtinh.Create_Table;
import com.lelong.ngungtinh.R;

public class KT_Menu extends AppCompatActivity {
    //Button btn_map, btn_search, btn_time;
    ImageButton btn_map, btn_search, btn_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kt_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btn_map = findViewById(R.id.img_iconmap);
        btn_search = findViewById(R.id.img_iconsearch);
        btn_time = findViewById(R.id.img_icontime);

        btn_map.setOnClickListener(btnlistener);
        btn_search.setOnClickListener(btnlistener);
        btn_time.setOnClickListener(btnlistener);

        Create_Table create_table = new Create_Table(this);
        create_table.open();
        create_table.createTable();
        /*create_table.insBasicData("C","A1","01",3,0);
        create_table.insBasicData("C","A1","02",3,0);
        create_table.insBasicData("C","A1","03",3,0);
        create_table.insBasicData("C","A1","04",3,0);
        create_table.insBasicData("C","A2","01",3,0);
        create_table.insBasicData("C","A2","02",3,0);
        create_table.insBasicData("C","A2","03",3,0);
        create_table.insBasicData("C","A2","04",3,0);
        create_table.insBasicData("C","A3","01",3,0);
        create_table.insBasicData("C","A3","02",3,0);
        create_table.insBasicData("C","A3","03",3,0);
        create_table.insBasicData("C","A3","04",3,0);
        create_table.insBasicData("C","B1","01",3,0);
        create_table.insBasicData("C","B1","02",3,0);
        create_table.insBasicData("C","B1","03",3,0);
        create_table.insBasicData("C","B1","04",3,0);
        create_table.insBasicData("C","B2","01",3,0);
        create_table.insBasicData("C","B2","02",3,0);
        create_table.insBasicData("C","B2","03",3,0);
        create_table.insBasicData("C","B2","04",3,0);
        create_table.insBasicData("C","B3","01",3,0);
        create_table.insBasicData("C","B3","02",3,0);
        create_table.insBasicData("C","B3","03",3,0);
        create_table.insBasicData("C","B3","04",3,0);
        create_table.insBasicData("D","A1","01",3,0);
        create_table.insBasicData("D","A1","02",3,0);
        create_table.insBasicData("D","A1","03",3,0);
        create_table.insBasicData("D","A1","04",3,0);
        create_table.insBasicData("D","A2","01",3,0);
        create_table.insBasicData("D","A2","02",3,0);
        create_table.insBasicData("D","A2","03",3,0);
        create_table.insBasicData("D","A2","04",3,0);
        create_table.insBasicData("D","A3","01",3,0);
        create_table.insBasicData("D","A3","02",3,0);
        create_table.insBasicData("D","A3","03",3,0);
        create_table.insBasicData("D","A3","04",3,0);
        create_table.insBasicData("D","B1","01",3,0);
        create_table.insBasicData("D","B1","02",3,0);
        create_table.insBasicData("D","B1","03",3,0);
        create_table.insBasicData("D","B1","04",3,0);
        create_table.insBasicData("D","B2","01",3,0);
        create_table.insBasicData("D","B2","02",3,0);
        create_table.insBasicData("D","B2","03",3,0);
        create_table.insBasicData("D","B2","04",3,0);
        create_table.insBasicData("D","B3","01",3,0);
        create_table.insBasicData("D","B3","02",3,0);
        create_table.insBasicData("D","B3","03",3,0);
        create_table.insBasicData("D","B3","04",3,0);
        create_table.insBasicData("I","A1","01",3,0);
        create_table.insBasicData("I","A1","02",3,0);
        create_table.insBasicData("I","A1","03",3,0);
        create_table.insBasicData("I","A1","04",3,0);
        create_table.insBasicData("I","A2","01",3,0);
        create_table.insBasicData("I","A2","02",3,0);
        create_table.insBasicData("I","A2","03",3,0);
        create_table.insBasicData("I","A2","04",3,0);
        create_table.insBasicData("I","A3","01",3,0);
        create_table.insBasicData("I","A3","02",3,0);
        create_table.insBasicData("I","A3","03",3,0);
        create_table.insBasicData("I","A3","04",3,0);
        create_table.insBasicData("I","B1","01",3,0);
        create_table.insBasicData("I","B1","02",3,0);
        create_table.insBasicData("I","B1","03",3,0);
        create_table.insBasicData("I","B1","04",3,0);
        create_table.insBasicData("I","B2","01",3,0);
        create_table.insBasicData("I","B2","02",3,0);
        create_table.insBasicData("I","B2","03",3,0);
        create_table.insBasicData("I","B2","04",3,0);
        create_table.insBasicData("I","B3","01",3,0);
        create_table.insBasicData("I","B3","02",3,0);
        create_table.insBasicData("I","B3","03",3,0);
        create_table.insBasicData("I","B3","04",3,0);*/

    }

    private ImageButton.OnClickListener btnlistener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.img_iconmap: {
                    Intent QR010 = new Intent();
                    QR010.setClass(KT_Menu.this, nt_dialog1.class);
                    Bundle bundle = new Bundle();
                    //bundle.putString("ID", ID);
                    //bundle.putString("SERVER", g_server);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }

                case R.id.img_iconsearch: {
                    //Activity activity = Menu.this;
                    //loginkt02.login_dialogkt02(v.getContext(),
                            //menuID.getText().toString(),activity);
                    break;
                }

                case R.id.img_icontime: {
                    //Activity activity = Menu.this;
                    //loginkt02.login_dialogkt02(v.getContext(),
                    //menuID.getText().toString(),activity);
                    break;
                }
            }
        }
    };
}