package com.example.administrator.magemata.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.example.administrator.magemata.Events.UserInfoMessage;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.more.ChangInfoActivity;
import com.example.administrator.magemata.util.PublicMethod;
import com.example.administrator.magemata.util.SkinManager;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import studios.codelight.smartloginlibrary.users.SmartUser;

/**
 * Created by Administrator on 2017/7/26.
 */

public class SignUpActivity extends BaseActivity {
    private ImageView addimg;
    @ViewInject(R.id.signup_et_username)
    EditText et_username;
    @ViewInject(R.id.signup_et_password)
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        x.view().inject(this);
        setListener();

    }

    private void setListener() {
        addimg=(ImageView)findViewById(R.id.changinfo_cv_addimg);
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePortrait();
            }
        });
    }

    @Event(R.id.changinfo_but_submit)
    private void submit(View view){
        SmartUser user= new SmartUser();
        if (TextUtils.isEmpty(et_username.getText()) || TextUtils.isEmpty(et_username.getText())){
            PublicMethod.showMessageDialog(SignUpActivity.this,"密码或账号不能为空");
        }
        else {
            MaterialDialog builder=PublicMethod.showProgress(SignUpActivity.this);
            user.setUsername(et_username.getText().toString());
            user.setLastName(et_password.getText().toString());
            RequestParams requestParams = new RequestParams("http://47.94.251.202/api/index.php/user/"+ user.getUsername()+'/'+user.getLastName());
            x.http().post(requestParams, new Callback.CacheCallback<String>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onSuccess(String stage) {
                    PublicMethod.showMessageDia_finish(SignUpActivity.this, "用户注册成功请登录");

                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                    PublicMethod.showMessageDialog(SignUpActivity.this, "用户已经存在,请另取别名");
//                    PublicMethod.showMessageDia(LoginActivity.this,ex.toString());
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                    builder.dismiss();
                }

                @Override
                public boolean onCache(String result) {
                    return false;
                }
            });
        }
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
        if(android.os.Build.MODEL.contains("HUAWEI")){
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        }
        else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", false);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, 300);
    }

    public static void actionStart(Context context ){
        Intent intent=new Intent(context,SignUpActivity.class);
        context.startActivity(intent);
    }


}

