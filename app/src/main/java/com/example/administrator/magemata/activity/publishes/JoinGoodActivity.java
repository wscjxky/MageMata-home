package com.example.administrator.magemata.activity.publishes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.magemata.Events.ImageMessage;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.BaseActivity;
import com.example.administrator.magemata.activity.MychatActivity;
import com.example.administrator.magemata.activity.publishes.base.AddItemBase;
import com.example.administrator.magemata.activity.publishes.base.PublishBase;
import com.example.administrator.magemata.activity.publishes.base.SpaceItemDecoration;
import com.example.administrator.magemata.activity.publishes.pubfragment.AddJoinGoodActivity;
import com.example.administrator.magemata.adapter.FollowercyAdapter;
import com.example.administrator.magemata.adapter.JoinGood;
import com.example.administrator.magemata.adapter.JoinGoodApater;
import com.example.administrator.magemata.constant.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/14.
 */
public class JoinGoodActivity extends BaseActivity {
    @ViewInject(R.id.joingood_rv_baselist)
    RecyclerView recyclerView;
    private JoinGoodApater joinGoodApater;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joingood);
        x.view().inject(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initAdapter();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JoinGood event) {
        if(!event.isSticked()) {
            joinGoodApater.addData(event);
        }
    }
    private void initAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(40));
        joinGoodApater = new JoinGoodApater();
        joinGoodApater.openLoadAnimation();

        joinGoodApater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                JoinGood joinGood=joinGoodApater.getData().get(position);joinGood.stickedTotrue();
                EventBus.getDefault().postSticky(joinGood);
                JoinGoodItemInfo.actionStart(JoinGoodActivity.this);
            }
        });
        recyclerView.setAdapter(joinGoodApater);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, JoinGoodActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.circle_menu, menu);
        menu.add("发布新凑单");
        menu.add("测试");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()){
            case 0:
                AddJoinGoodActivity.actionStart(this);
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
