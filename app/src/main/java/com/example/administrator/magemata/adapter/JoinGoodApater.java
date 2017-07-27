package com.example.administrator.magemata.adapter;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.model.Follower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/30.
 */

public class JoinGoodApater extends BaseQuickAdapter<JoinGood, BaseViewHolder> {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public JoinGoodApater() {
        super(R.layout.recyclelist_item_joingood, getSampleData());
    }

    @Override
    protected void convert(BaseViewHolder helper, JoinGood item) {
//        helper.addOnClickListener(R.id.follow_item_moreimage);
        helper.setImageBitmap(R.id.joingood_item_shoplogo,item.getShopLogo());
        helper.setText(R.id.joingood_item_shopname, item.getShopname());
        helper.setText(R.id.joingood__item_goodsort, item.getShopSort());
        helper.setText(R.id.joingood__item_totalprice, item.getTotalPrice());
        helper.setChecked(R.id.joingood_item_checkbox,item.isEnough());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private  static List<JoinGood> getSampleData() {
        List<JoinGood> list = new ArrayList<>();
        JoinGood status = new JoinGood();
        status.setShopname("首尔之心");
        status.setShopsort("首饰" );
        status.setTotalPrice("5000");
        status.setIntroduction("首尔(谚文:서울;英文:Seoul)，全称首尔特别市(谚文:서울특별시/汉字:서울特别市)，旧称汉城。");
        JoinGood status1 = new JoinGood();
        status1.setShopname("蓝影态" );
        status1.setShopsort("服装" );
        status1.setTotalPrice("10000");
        status1.setCurrentPrice("10000");

        status1.setIntroduction("一个软萌妹子");
        list.add(status);
        list.add(status1);
        return list;
    }
}