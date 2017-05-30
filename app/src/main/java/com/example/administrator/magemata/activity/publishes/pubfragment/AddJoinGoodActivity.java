package com.example.administrator.magemata.activity.publishes.pubfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.BaseActivity;
import com.example.administrator.magemata.adapter.JoinGood;
import com.rey.material.widget.EditText;
import com.rey.material.widget.ImageView;


import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/5/30.
 */
public class AddJoinGoodActivity  extends BaseActivity {
    @ViewInject(R.id.addjoingood_et_name)
    EditText shopnameet;
    @ViewInject(R.id.aaddjoingood_et_shopsort)
    EditText shopsortet;
    @ViewInject(R.id.addjoingood_et_totalprice)
    EditText totalpriceet;
    @ViewInject(R.id.addjoingood_et_content)
    EditText contentet;
    @ViewInject(R.id.addjoingood_iv_addimg)
    ImageView addimgiv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addjoingood);
        x.view().inject(this);
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddJoinGoodActivity.class);
        context.startActivity(intent);
    }
    @Event(value = R.id.addjoingood_btn_submit)
    private void submit(View view){
        JoinGood joinGood=new JoinGood();
        joinGood.setShopname(shopnameet.getText().toString());joinGood.setShopsort(shopsortet.getText().toString());joinGood.setIntroduction(contentet.getText().toString());joinGood.setTotalPrice(totalpriceet.getText().toString());
        addimgiv.setDrawingCacheEnabled(true);
        Bitmap logo = Bitmap.createBitmap(addimgiv.getDrawingCache());
        addimgiv.setDrawingCacheEnabled(false);
        joinGood.setShopLogo(logo);
        showConfirmlog(joinGood);

    }
    private void showConfirmlog(final JoinGood joinGood){
        new MaterialDialog.Builder(this)
                .title("注意")
                .content("确认要提交吗")
                .positiveText("确定")
                .negativeText("我还要改改")
                .cancelable(true)
                .onPositive(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        EventBus.getDefault().post(joinGood);
                        AddJoinGoodActivity.this.finish();
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                    })
                .show();
    }

}

