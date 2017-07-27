package com.example.administrator.magemata.activity.publishes.Delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.BaseActivity;
import com.example.administrator.magemata.model.Rice;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */

public class RiceConfirmActivity extends BaseActivity {
    @ViewInject(R.id.riceconfirm_linel_address)
    LinearLayout linel_address;
    @ViewInject(R.id.riceconfirm_linel_menu)
    LinearLayout linel_menu;
    @ViewInject(R.id.riceconfirm_listview)
    ListView listView;
    @ViewInject(R.id.riceconfirm_btn_submit)
    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riceconfirm);
        x.view().inject(this);
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RiceConfirmActivity.class);
        context.startActivity(intent);
    }
    @Subscribe(sticky = true ,threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<Rice> event) {
        List<String> datalist=new ArrayList<String>();
        int totalprice=0;
        for (int i=0 ;i<3;i++) {
            datalist.add(event.get(i).getName()+"                          *"+event.get(i).getCount());
            totalprice+=event.get(i).getCount()*200;
        }
        btn.setText("确认支付    ￥ "+String.valueOf(totalprice));
        listView.setAdapter(new ArrayAdapter<String>(RiceConfirmActivity.this,android.R.layout.select_dialog_item,datalist));
        EventBus.getDefault().removeStickyEvent(event);
    }
    @Override
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
    @Event(R.id.riceconfirm_linel_address)
    private void showAddress(View view) {
        final MaterialSimpleListAdapter adapter =
                new MaterialSimpleListAdapter((dialog, index, item) -> {
                    if(index==2){
                        new MaterialDialog.Builder(this)
                                .title("新增收货地址")
                                .content("姓名")
                                .inputType(
                                        InputType.TYPE_CLASS_TEXT
                                                | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                                .inputRange(2, 16)
                                .positiveText("确认提交")
                                .input(
                                        "张三",
                                        null,
                                        false,
                                        (dialog1, input) -> dialog1.dismiss()
                                )
                                .show();
                    }
                    Toast.makeText(RiceConfirmActivity.this,item.getContent().toString(),Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
                );
        adapter.add(
                new MaterialSimpleListItem.Builder(this)
                        .content("第一个收货地址")
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(this)
                        .content("第二个收货地址")
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(this)
                        .content(R.string.addaddress)
                        .icon(R.drawable.btn_add)
                        .iconPaddingDp(8)
                        .build());

        new MaterialDialog.Builder(this).title(R.string.address).adapter(adapter, null).show();
    }
    @Event(R.id.riceconfirm_btn_submit)
    private void submit(View view) {
        new MaterialDialog.Builder(this)
                .content(R.string.progress)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }
}
