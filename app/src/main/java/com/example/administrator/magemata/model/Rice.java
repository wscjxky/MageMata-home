package com.example.administrator.magemata.model;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/7/5.
 */

public class Rice {
    public String name;
    public int mCount;
    public int length;
    public int price;
    public String content;
    public int shopImage;

    public Rice(String name, int price,int shopImage) {
        this.length = length;
        this.name = name;
        this.price = price;
        this.content=content;
        this.shopImage=shopImage;
    }
    public String getName() {
        return name;
    }
    public int getCount() {
        return mCount;
    }
    public Rice setCount(int count) {
        mCount = count;
        return this;
    }


}
