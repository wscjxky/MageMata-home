package com.example.administrator.magemata.model;

import android.graphics.Bitmap;

import com.example.administrator.magemata.R;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/28.
 */

public class PublishInfo implements Serializable {
        private String content;
        private String title;
        private Bitmap bitmap;
        private String type;
        private String  price;
        private String createdAt;
        public void setContent(String content) {
            this.content=content    ;
        }

        public String getContent( ) {
            return content;
        }

        public String getTitle( ) {
            return title;
        }
        public void setTitle(String title ) {
            this.title= title;
        }

        public Bitmap getBitmap( ) {
            return bitmap;
        }
        public void setBitmap(Bitmap bitmap ) {
            this.bitmap= bitmap;
        }
        public String getPrice( ) {
            return price;
        }
        public void setPrice(String price ) {
            this.price= price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

