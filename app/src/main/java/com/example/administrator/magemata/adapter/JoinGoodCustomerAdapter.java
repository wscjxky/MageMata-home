package com.example.administrator.magemata.adapter;

import android.graphics.Paint;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.model.Follower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/30.
 */

public class JoinGoodCustomerAdapter extends BaseQuickAdapter<JoinGoodCustomer, BaseViewHolder> {

    public JoinGoodCustomerAdapter() {
        super(R.layout.recyclelist_cv_joingoodcustomer, getSampleData(3));
    }

    @Override
    protected void convert(BaseViewHolder helper, JoinGoodCustomer item) {
        helper.addOnClickListener(R.id.customer_item_portrait);
        helper.setBackgroundRes(R.id.customer_item_portrait,R.mipmap.logo2);
        helper.setText(R.id.customer_item_name, item.getUserName());
        if(item.isNumber())
            helper.setText(R.id.customer_item_content,"发布了一个"+item.getContent()+"元的凑单" );
        else
        helper.setText(R.id.customer_item_content,item.getContent());

    }

    private static List<JoinGoodCustomer> getSampleData(int lenth) {
        List<JoinGoodCustomer> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            JoinGoodCustomer status = new JoinGoodCustomer();
            status.setUserName("胖胖" + i);
            status.setCreatedAt("04/05/" + i);
            status.setContent("楼下品味不错");
            list.add(status);
        }
        return list;
    }
}