package com.mygame.myfellowship.utils;

import com.google.gson.Gson;
import com.mygame.myfellowship.bean.Constant;
import com.mygame.myfellowship.info.User;
import com.mygame.myfellowship.login.LoginActivity;

public class UserUtils {

	public static void saveUserInfo(LoginActivity activity, User response) {
		 String json = new Gson().toJson(response);
		 Constant.Preference.getSharedPreferences(activity).edit().putString(Constant.Preference.LOGIN_USER, json).commit();
	}

}
