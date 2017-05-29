package com.example.administrator.magemata.activity.publishes.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.MychatActivity;
import com.example.administrator.magemata.adapter.SkinSettingManager;
import com.example.administrator.magemata.fragment.MychatFragment;
import com.example.administrator.magemata.model.Message;

import java.util.Objects;

/**
 * Created by Administrator on 2017/5/4.
 */

public class InfoBase extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String content=intent.getStringExtra("content");
        String price=intent.getStringExtra("price");

        Bitmap bitmap=intent.getParcelableExtra("bitmap");
        if(Objects.equals(intent.getStringExtra("type"), "usedgood")) {
            setContentView(R.layout.activity_usedgood);
            ImageView imageView=(ImageView)findViewById(R.id.usedgood_iv_logo);
            TextView titlev=(TextView)findViewById(R.id.usedgood_tv_title);
            TextView contentv=(TextView)findViewById(R.id.usedgood_tv_content);
            TextView pricetv=(TextView)findViewById(R.id.usedgood_tv_price);
//            TextView pricev=(TextView)findViewById(R.id.usedgood_tv_price);
            if(bitmap!=null)
                imageView.setImageBitmap(bitmap);

            titlev.setText(title);contentv.setText(content);pricetv.setText(price);
        }
        else {
            setContentView(R.layout.activity_infobase);
            ImageView imageView=(ImageView)findViewById(R.id.base_iv_logo);
            TextView titlev=(TextView)findViewById(R.id.base_tv_title);
            TextView contentv=(TextView)findViewById(R.id.base_tv_content);
            if(bitmap!=null)
                imageView.setImageBitmap(bitmap);
            titlev.setText(title);contentv.setText(content);
        }

        Button contactbtu=(Button)findViewById(R.id.infobase_btu_contactuser);
        contactbtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MychatFragment.ADDUSER=true;
                MychatActivity.actionStart(InfoBase.this);
            }
        });
    }

    public static void actionStart(Context context,String title,String content,@Nullable Bitmap bitmap,@Nullable String type,@Nullable String price) {
        Intent intent=new Intent(context,InfoBase.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("bitmap",bitmap);
        intent.putExtra("type",type);
        intent.putExtra("price",price);

        context.startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SkinSettingManager mSettingManager = new SkinSettingManager(this);
        mSettingManager.initSkins();
    }
}