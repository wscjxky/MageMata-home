package com.example.administrator.magemata.activity.publishes.Delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.BaseActivity;
import com.example.administrator.magemata.util.PublicMethod;
import com.youth.banner.Banner;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */

public class ExpressActivity extends BaseActivity {
    @ViewInject(R.id.banner)
    private Banner banner;

    List<Integer> images= new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);
        x.view().inject(this);
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ExpressActivity.class);
        context.startActivity(intent);
    }
    @Event(R.id.express_btn_submit)
    private void submit(View view){
        PublicMethod.showConfirmDia_finish(ExpressActivity.this,"将花费3金币,确定吗?");
    }
}
