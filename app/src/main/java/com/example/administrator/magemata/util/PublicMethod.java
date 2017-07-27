package com.example.administrator.magemata.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.activity.publishes.pubfragment.AddJoinGoodActivity;
import com.example.administrator.magemata.adapter.JoinGood;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2017/5/30.
 */

public class PublicMethod {
    public static void   showConfirmlog(final JoinGood message, final Activity activity){
        new MaterialDialog.Builder(activity)
                .title("注意")
                .content("确认要提交吗")
                .positiveText("确定")
                .negativeText("我还要改改")
                .cancelable(true)
                .onPositive(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        EventBus.getDefault().post(message);
                        activity.finish();
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    public static void   showConfirmDia_finish(final Activity activity,final String content){
        new MaterialDialog.Builder(activity)
                .title("注意")
                .content(content)
                .positiveText("确定")
                .negativeText("我还要改改")
                .cancelable(true)
                .onPositive(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showToast(activity,"操作成功");
                        activity.finish();
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    public static void   showConfirmDialog(final Activity activity,final String content){
        new MaterialDialog.Builder(activity)
                .title("注意")
                .content(content)
                .positiveText("确定")
                .negativeText("我还要改改")
                .cancelable(true)
                .onPositive(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showToast(activity,"操作成功");
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    public static void   showMessageDialog(final Activity activity,final String content){
        new MaterialDialog.Builder(activity)
                .title("通知")
                .content(content)
                .cancelable(true)
                .positiveText("知道了")
                .onPositive(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();

                    }
                })
                .show();
    }
    public static void   showMessageDia_finish(final Activity activity,final String content){
        new MaterialDialog.Builder(activity)
                .title("通知")
                .content(content)
                .cancelable(true)
                .positiveText("知道了")
                .onPositive(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        activity.finish();
                    }
                })
                .show();
    }
    public static void showToast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
    public static MaterialDialog showProgress(Context context) {
        return new MaterialDialog.Builder(context)
                .content(R.string.progress)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }
}
