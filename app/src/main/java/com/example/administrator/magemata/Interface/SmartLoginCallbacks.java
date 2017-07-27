package com.example.administrator.magemata.Interface;

import studios.codelight.smartloginlibrary.users.SmartUser;
import studios.codelight.smartloginlibrary.util.SmartLoginException;

/**
 * Created by Administrator on 2017/7/26.
 */

public interface SmartLoginCallbacks {
    void onLoginSuccess(SmartUser user);
    void onLoginFailure(SmartLoginException e);
    SmartUser doCustomLogin();
    SmartUser doCustomSignup();
}