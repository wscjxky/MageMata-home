package com.example.administrator.magemata.activity.more;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.magemata.Events.CardMessage;
import com.example.administrator.magemata.Events.ImageMessage;
import com.example.administrator.magemata.Events.UserInfoMessage;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.BaseActivity;
import com.example.administrator.magemata.activity.circle.AddCardActivity;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Objects;

/**
 * Created by Administrator on 2017/5/26.
 */

public class ChangInfoActivity extends BaseActivity{
    private ImageView addimg;
    private Button changeskin;
    private Button submit;
    @ViewInject(R.id.changinfo_et_name)
    EditText name;
    @ViewInject(R.id.changinfo_et_introduce)
    EditText introduce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changinfo);
        x.view().inject(this);
        setListener();

    }

    private void setListener() {
        addimg=(ImageView)findViewById(R.id.changinfo_cv_addimg);
        changeskin=(Button)findViewById(R.id.changeinfo_but_changeskin);
        submit=(Button)findViewById(R.id.changinfo_but_submit);
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePortrait();
            }
        });
        changeskin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSkin();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    public void changePortrait(){
//        new Permission(activity);
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 200);
    }
    public void changeSkin(){
        ChangeSkinActivity.actionStart(this);
    }
    public void submit(){
        addimg.setDrawingCacheEnabled(true);
        Bitmap logo = Bitmap.createBitmap(addimg.getDrawingCache());
        addimg.setDrawingCacheEnabled(false);
        UserInfoMessage imageMessage=new UserInfoMessage();
        imageMessage.setBitmap(logo);
        imageMessage.setUsername(name.getText().toString());
        imageMessage.setIntroduce(introduce.getText().toString());
        showConfirmDialog(imageMessage);
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if(data !=null){
                Uri photoUri = data.getData();
                //获取照片路径
                crop(photoUri);
            }
        } else if (requestCode == 300) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                addimg.setImageBitmap(bitmap);
            }

        }
    }
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, 300);
    }

    public static void actionStart(Context context ){
        Intent intent=new Intent(context,ChangInfoActivity.class);
        context.startActivity(intent);
    }
    private void showConfirmDialog(final UserInfoMessage userInfoMessage) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(ChangInfoActivity.this);
        dialog.setTitle("确认发布吗");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EventBus.getDefault().post(userInfoMessage);
                dialog.dismiss();
                finish();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
