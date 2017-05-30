package com.example.administrator.magemata.activity.publishes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.BaseActivity;
import com.example.administrator.magemata.activity.MychatActivity;
import com.example.administrator.magemata.activity.publishes.pubfragment.AddJoinGoodActivity;
import com.example.administrator.magemata.adapter.FollowercyAdapter;
import com.example.administrator.magemata.adapter.JoinGood;
import com.example.administrator.magemata.adapter.JoinGoodApater;
import com.example.administrator.magemata.adapter.JoinGoodCustomer;
import com.example.administrator.magemata.adapter.JoinGoodCustomerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/5/30.
 */

public class JoinGoodCustomerActivity  extends BaseActivity{
    @ViewInject(R.id.joingood_rv_baselist)
    private RecyclerView mRecyclerView;
    private JoinGoodCustomerAdapter mFollowerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joingoodcustomer);
        x.view().inject(this);
        initRecyclerView();

    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(JoinGoodCustomerActivity.this));
        mFollowerAdapter = new JoinGoodCustomerAdapter();
        mFollowerAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId()==R.id.customer_item_portrait)
                MychatActivity.actionStart(JoinGoodCustomerActivity.this);
        });
        mFollowerAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mFollowerAdapter);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, JoinGoodCustomerActivity.class);
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
        switch (item.getItemId()){
            case 0:
                CharSequence[] list={"金额信息","文字信息"};
                new MaterialDialog.Builder(this)
                        .title("需要发布的类型")
                        .items(list)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position){
                                    default:
                                        showInputDialog();
                                        break;
                                }
                            }
                        })
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private  void showInputDialog(){
        new MaterialDialog.Builder(this)
                .title("输入信息")
                .inputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE
                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(1, 50)
                .positiveText("提交")
                .input("最长50字","",false,(dialog1,input) ->{
                    JoinGoodCustomer status1 = new JoinGoodCustomer();
                    status1.setUserName("只是一只卷");
                    status1.setContent(input.toString());
                    if(status1.isNumber()){
                        EventBus.getDefault().post(status1);
                    }
                    mFollowerAdapter.addData(status1);
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
