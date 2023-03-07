package com.lelong.ngungtinh.KTnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.lelong.ngungtinh.R;

public class KT_Menu extends AppCompatActivity {
    //Button btn_map, btn_search, btn_time;
    ImageButton btn_map, btn_search, btn_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kt_menu);

        btn_map = findViewById(R.id.img_iconmap);
        btn_search = findViewById(R.id.img_iconsearch);
        btn_time = findViewById(R.id.img_icontime);

        btn_map.setOnClickListener(btnlistener);
        btn_search.setOnClickListener(btnlistener);
        btn_time.setOnClickListener(btnlistener);


    }
    private ImageButton.OnClickListener btnlistener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.img_iconmap: {
                    Intent QR010 = new Intent();
                    QR010.setClass(KT_Menu.this, KT_Main.class);
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