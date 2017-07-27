package com.example.administrator.magemata.activity.publishes.Delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.BaseActivity;
import com.example.administrator.magemata.activity.publishes.adapter.GlideImageLoader;
import com.youth.banner.Banner;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */

public class DeliveryActivity extends BaseActivity {
    @ViewInject(R.id.banner)
    private Banner banner;

    List <Integer> images= new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        x.view().inject(this);
        images.add(R.drawable.color1);
        images.add(R.drawable.color2);
        images.add(R.drawable.color3);
        images.add(R.drawable.color4);
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DeliveryActivity.class);
        context.startActivity(intent);
    }
    @Event(value = R.id.delivery_linel_rice)
    private   void init_rice(View view ) {
        RiceActivity.actionStart(DeliveryActivity.this);
    }
    @Event(value = R.id.delivery_linel_shop)
    private  void init_shop( View view ) {

    }
    @Event(value = R.id.delivery_linel_express)
    private  void init_express(View view  ) {
        ExpressActivity.actionStart(DeliveryActivity.this);
    }

}
