package com.example.administrator.magemata.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.administrator.magemata.Interface.SmartLoginCallbacks;
import com.example.administrator.magemata.R;
import com.example.administrator.magemata.R2;
import com.example.administrator.magemata.model.Message;
import com.example.administrator.magemata.util.PublicMethod;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import studios.codelight.smartloginlibrary.LoginType;
import studios.codelight.smartloginlibrary.SmartLogin;
import studios.codelight.smartloginlibrary.SmartLoginConfig;
import studios.codelight.smartloginlibrary.SmartLoginFactory;
import studios.codelight.smartloginlibrary.UserSessionManager;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;
import studios.codelight.smartloginlibrary.util.SmartLoginException;

import static com.example.administrator.magemata.fragment.MychatFragment.OTHER;
import static com.facebook.internal.CallbackManagerImpl.RequestCodeOffset.Login;

/**
 * Created by Administrator on 2017/4/24.
 */

public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.login_et_username)
    EditText et_username;
    @ViewInject(R.id.login_et_password)
    EditText et_password;


    @Override
    protected void onResume() {
        super.onResume();
        SmartUser currentUser = UserSessionManager.getCurrentUser(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);
    }
    @Event(R.id.facebook_login_button)
    private void qq(View view) {
        MainActivity.actionStart(LoginActivity.this);
    }
    @Event(R.id.google_login_button)
    private void message(View view) {
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String phone = (String) phoneMap.get("phone");
                    MainActivity.actionStart(LoginActivity.this);
                }
            }
        });
        registerPage.show(LoginActivity.this);
    }
    @Event(R.id.custom_signin_button)
    private void siginIn(View view) {
        SmartUser user= new SmartUser();
        if (TextUtils.isEmpty(et_username.getText()) || TextUtils.isEmpty(et_username.getText())){
            PublicMethod.showMessageDialog(LoginActivity.this,"密码或账号不能为空");
        }
        else {
            MaterialDialog builder=PublicMethod.showProgress(LoginActivity.this);
            user.setUsername(et_username.getText().toString());
            user.setLastName(et_password.getText().toString());
            RequestParams requestParams = new RequestParams("http://47.94.251.202/api/index.php/user/"+ user.getUsername());
            x.http().get(requestParams, new Callback.CacheCallback<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    try {
                        String password = jsonObject.getString("password");
                        if (!Objects.equals(user.getLastName(), password)) {
                            PublicMethod.showMessageDialog(LoginActivity.this, "用户密码输入错误请重新输入");
                        }
                        else {
                            PublicMethod.showToast(LoginActivity.this, "登陆成功");
                            MainActivity.actionStart(LoginActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    PublicMethod.showMessageDialog(LoginActivity.this, "用户不存在,请注册");
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
                public boolean onCache(JSONObject result) {
                    return false;
                }
            });
        }
    }

    @Event(R.id.custom_signup_button)
    private void signUp(View view) {
        SignUpActivity.actionStart(LoginActivity.this);
    }


//    @Event(R.id.logout_button)
//    private void logOut(View view) {
//        if (currentUser != null) {
//            if (currentUser instanceof SmartFacebookUser) {
//                smartLogin = SmartLoginFactory.build(LoginType.Facebook);
//            } else if (currentUser instanceof SmartGoogleUser) {
//                smartLogin = SmartLoginFactory.build(LoginType.Google);
//            } else {
//                smartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
//            }
//            boolean result = smartLogin.logout(LoginActivity.this);
//            if (result) {
//                Toast.makeText(LoginActivity.this, "User logged out successfully", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }




    public static void actionStart(Context context ){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }


//    private void refreshLayout() {
//        currentUser = UserSessionManager.getCurrentUser(this);
//        if (currentUser != null) {
//            Log.d("Smart Login", "Logged in user: " + currentUser.toString());
//            facebookLoginButton.setVisibility(View.GONE);
//            googleLoginButton.setVisibility(View.GONE);
//            customSigninButton.setVisibility(View.GONE);
//            customSignupButton.setVisibility(View.GONE);
//            emailEditText.setVisibility(View.GONE);
//            passwordEditText.setVisibility(View.GONE);
//            logoutButton.setVisibility(View.VISIBLE);
//        } else {
//            facebookLoginButton.setVisibility(View.VISIBLE);
//            googleLoginButton.setVisibility(View.VISIBLE);
//            customSigninButton.setVisibility(View.VISIBLE);
//            customSignupButton.setVisibility(View.VISIBLE);
//            emailEditText.setVisibility(View.VISIBLE);
//            passwordEditText.setVisibility(View.VISIBLE);
//            logoutButton.setVisibility(View.GONE);
//        }
//    }

}
