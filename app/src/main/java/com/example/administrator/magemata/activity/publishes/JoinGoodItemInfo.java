package com.example.administrator.magemata.activity.publishes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.magemata.Events.UserInfoMessage;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.BaseActivity;
import com.example.administrator.magemata.adapter.JoinGood;
import com.example.administrator.magemata.adapter.JoinGoodCustomer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/5/30.
 */
public class JoinGoodItemInfo  extends BaseActivity{
    private int TOTALPRICE;
    @ViewInject(R.id.joingoodinfo_tv_shopname)
    TextView shopnameet;
    @ViewInject(R.id.joingoodinfo_tv_shopsort)
    TextView shopsortet;
    @ViewInject(R.id.joingoodinfo_tv_totalprice)
    TextView totalpriceet;
    @ViewInject(R.id.joingoodinfo_tv_content)
    TextView contentet;
    @ViewInject(R.id.joingoodinfo_iv_logo)
    ImageView logoiv;
    @ViewInject(R.id.joingoodinfo_tv_remainprice)
    TextView remainprice;

    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joingooditeminfo);
        x.view().inject(this);
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(JoinGood event) {
        shopnameet.setText(event.getShopname());
        shopsortet.setText(event.getShopSort());
        totalpriceet.setText(event.getTotalPrice());
        contentet.setText(event.getIntroduction());
        logoiv.setImageBitmap(event.getShopLogo());
        TOTALPRICE=Integer.valueOf(event.getTotalPrice());
        remainprice.setText(event.remainPrice());
        EventBus.getDefault().removeStickyEvent(event);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPrice(JoinGoodCustomer event) {
        remainprice.setText((TOTALPRICE-event.getCurrentPrice()+""));
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, JoinGoodItemInfo.class);
        context.startActivity(intent);
    }
    @Event(value = R.id.joingoodinfo_btn_join)
    private void join(View view){
        JoinGoodCustomerActivity.actionStart(JoinGoodItemInfo.this);
    }
}
