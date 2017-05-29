package com.example.administrator.magemata.Events;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/5/14.
 */

public class ImageMessage {
    private String user;
    private String title;
    private String content;
    private String price;
    private String time;
    private Bitmap bitmap=null;
    private String type;
    public void setContent(String cont){
        content = cont;
    }
    public void setUser(String use){
        user = use;
    }
    public void setTitle(String tit){
        title = tit;
    }
    public void setBitmap( Bitmap bit){
        bitmap = bit;
    }
    public String getContent(){
        return content;
    }
    public String getTitle(){
        return title;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public boolean hasBitmap(){
        return bitmap != null;
    }
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type=type;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price=price;
    }
}
