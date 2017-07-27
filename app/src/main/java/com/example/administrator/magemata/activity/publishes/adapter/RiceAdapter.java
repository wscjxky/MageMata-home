package com.example.administrator.magemata.activity.publishes.adapter;

import android.app.Activity;
import android.graphics.Movie;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.adapter.JoinGood;
import com.example.administrator.magemata.model.Rice;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */

public class RiceAdapter extends BaseQuickAdapter<Rice,BaseViewHolder> {

    public RiceAdapter() {
        super(R.layout.rice_rv_item, getSampleData());
    }

    @Override
    protected void convert(BaseViewHolder helper, Rice item) {
//        helper.addOnClickListener(R.id.rice_item_addtoshop);
        helper.setText(R.id.rice_item_name, item.name);
        helper.setText(R.id.rice_item_price, "￥ "+item.price);
        helper.setBackgroundColor(R.id.rice_item_goodimg,item.shopImage);
        AnimShopButton animShopButton = helper.getView(R.id.rice_item_shopbut);
        animShopButton.setCount(0);
        animShopButton.setMaxCount(5);
        animShopButton.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int count) {
                item.setCount(count);
            }

            @Override
            public void onAddFailed(int count, FailType failType) {

            }

            @Override
            public void onDelSuccess(int count) {
                item.setCount(count);
            }

            @Override
            public void onDelFaild(int count, FailType failType) {

            }
        });
    }

    private  static List<Rice> getSampleData() {
        List<Rice> list = new ArrayList<>();
        Rice status = new Rice("韩国拌饭", 100, R.drawable.color1);
        Rice status1 = new Rice("砂锅居家", 200, R.drawable.color5);
        Rice status2 = new Rice("麻辣香锅", 300, R.drawable.color3);
        list.add(status);
        list.add(status1);
        list.add(status2);
        return list;
    }
}
