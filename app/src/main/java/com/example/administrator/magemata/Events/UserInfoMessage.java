package com.example.administrator.magemata.Events;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/5/28.
 */
public class UserInfoMessage {
    private String username;
    private String grade;
    private String introduce;
    private Bitmap bitmap=null;

    public void setUsername(String username){
        this.username = username;
    }
    public void setGrade(String grade){
        this.grade = grade;
    }
    public void setIntroduce(String introduce){
        this.introduce = introduce;
    }
    public void setBitmap( Bitmap bit){
        bitmap = bit;
    }
    public String getUsername(){
        return username;
    }
    public String getGrade(){
        return grade;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public boolean hasBitmap(){
        return bitmap != null;
    }

    public String getIntroduce() {
        return introduce;
    }
}
