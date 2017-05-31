package com.example.administrator.magemata.activity.more;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.magemata.Events.ImageMessage;
import com.example.administrator.magemata.Events.UserInfoMessage;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.R2;
import com.example.administrator.magemata.activity.BaseActivity;
import com.example.administrator.magemata.activity.MainActivity;
import com.example.administrator.magemata.activity.MychatActivity;
import com.example.administrator.magemata.adapter.MainAdapter;
import com.example.administrator.magemata.fragment.MychatFragment;
import com.example.administrator.magemata.model.User;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/26.
 */
public class UserInfoActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;
    private int mMaxScrollSize;
    private boolean followed=false;
    @ViewInject(R.id.userinfo_tv_introduce)
    TextView introduce;
    @ViewInject(R.id.userinfo_tv_username)
    TextView username;
    @ViewInject(R.id.userinfo_tl_tabs)
    TabLayout tabLayout;
    @ViewInject(R.id.userinfo_vp_viewpager)
    ViewPager viewPager;
    @ViewInject(R.id.userinfo_al_appbar)
    AppBarLayout appbarLayout;
    @ViewInject(R.id.user_cv_userimage)
    CircleImageView mProfileImage;
    @ViewInject(R.id.userinfo_profile_backdrop)
    ImageView backimage;
    public String[] Title={"关注","粉丝"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        x.view().inject(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        useRxjava();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UserInfoMessage event) {
        username.setText(event.getUsername());
        introduce.setText(event.getIntroduce());
        if(event.hasBitmap())
            mProfileImage.setImageBitmap(event.getBitmap());
    }
    private void  useRxjava() {
        Observable.create(new Observable.OnSubscribe<MainAdapter>() {
            @Override
            public void call(Subscriber<? super MainAdapter> subscriber) {
                appbarLayout.addOnOffsetChangedListener(UserInfoActivity.this);
                mMaxScrollSize = appbarLayout.getTotalScrollRange();
                viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(),Title));
                tabLayout.setupWithViewPager(viewPager);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe();
    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;

            mProfileImage.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    private static class TabsAdapter extends FragmentPagerAdapter {
        private  static final int TAB_COUNT = 2;
        private String[] mTitle;
        TabsAdapter(FragmentManager fm ,String[] mTitle) {
            super(fm);
            this.mTitle=mTitle;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int i) {
            return UserPageFragment.newInstance();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }

    @Event(value = R.id.user_cv_userimage,type = View.OnClickListener.class)
    private void change(View view){

    }
    @Event(value = R.id.userinfo_btn_change,type = View.OnClickListener.class)
    private void changeUser(View view){
            ChangInfoActivity.actionStart(UserInfoActivity.this);
    }
    @Event(value = R.id.userinfo_btn_chat,type = View.OnClickListener.class)
    private void startChat(View view){
        MychatFragment.ADDUSER=true;
        MychatActivity.actionStart(UserInfoActivity.this);
    }
    @Event(value = R.id.userinfo_profile_backdrop,type = View.OnClickListener.class)
    private void changeBackground(View view){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if(data !=null){
                Uri photoUri = data.getData();
                //获取照片路径
                crop(photoUri);
            }
        } else if (requestCode == 200) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                backimage.setImageBitmap(bitmap);
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
        startActivityForResult(intent, 200);
    }

    public static void actionStart(Context context ){
        Intent intent=new Intent(context,UserInfoActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.e("stop,","asd");
        super.onDestroy();
    }
}
