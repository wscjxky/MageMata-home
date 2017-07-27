package com.example.administrator.magemata.activity.publishes.Delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.BaseActivity;
import com.example.administrator.magemata.activity.publishes.adapter.RiceAdapter;
import com.example.administrator.magemata.model.Rice;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */

public class RiceActivity extends BaseActivity {
    @ViewInject(R.id.rice_rv)
    RecyclerView mRecyclerView;

    RiceAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice);
        x.view().inject(this);
        mAdapter = new RiceAdapter();
        mAdapter.openLoadAnimation();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

    }
    @Event(value = R.id.rice_but_tocart)
    private void toCart(View view){
        List<Rice> list = new ArrayList<>();
        list=mAdapter.getData();
        EventBus.getDefault().postSticky(list);
        RiceConfirmActivity.actionStart(RiceActivity.this);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RiceActivity.class);
        context.startActivity(intent);
    }
}
