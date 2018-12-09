package com.vnpt.media.bookingcar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import org.json.JSONObject;

import java.util.HashMap;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	SharedPreferences prefLoginInfor;

	// Editor for Shared preferences
	Editor editor;
	Editor editorLoginInfor;

	// Context
	Context _context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	// Sharedpref file name
	public static final String PREF_NAME = "com.app.trafic";
	public static final String PREF_NAME_LOGIN_INFOR = "PREF_NAME_LOGIN_INFOR";
	
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	public static final String KEY_NAME = "jsonUserInfor";
	public static final String KEY_USERNAME = "KEY_USERNAME";
	public static final String KEY_PASSWORD = "KEY_PASSWORD";
	public static final String KEY_FULLNAME= "KEY_FULLNAME";
	public static final String KEY_LOGIN_WITH_EMAIL= "KEY_LOGIN_WITH_EMAIL";
	public static final String KEY_SAVE_INFOR_LOGIN= "KEY_SAVE_INFOR_LOGIN";
	public static final String KEY_INFOR_ACC= "InforAcc";// lưu thông tin đăng nhập cho lần sau

	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
		prefLoginInfor = _context.getSharedPreferences(PREF_NAME_LOGIN_INFOR, PRIVATE_MODE);
		editorLoginInfor = prefLoginInfor.edit();
	}
	
	/**
	 * Create login session
	 * */
	public void createLoginSession(String json){
		// Storing name in pref
		editor.putString(KEY_NAME, json);
		// commit changes
		editor.commit();
	}

	public void saveLoginInfo(String username, String password, String fullName){
		// Storing login value as TRUE
		editor.putInt(IS_LOGIN, 1);
		editor.putString(KEY_USERNAME, username);
		editor.putString(KEY_PASSWORD, password);
		editor.putString(KEY_FULLNAME, fullName);
		// commit changes
		editor.commit();
	}

	public void saveSaveInforLogin(int checkSave){
		editorLoginInfor.putInt(KEY_SAVE_INFOR_LOGIN, checkSave);
		editorLoginInfor.commit();
	}
	public int getSaveInforLogin(){
		return prefLoginInfor.getInt(KEY_SAVE_INFOR_LOGIN, 0);
	}
	// luu thông tin đăng  nhập để hiển thị vào ô đăng nhập
	public void saveInforAcc(String userName, String pass){
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userName",userName);
			jsonObject.put("pass",pass);
			editorLoginInfor.putString(KEY_INFOR_ACC, jsonObject.toString());
			editorLoginInfor.commit();
		}catch (Exception e){}

	}
	public String getInforAcc(){
		return prefLoginInfor.getString(KEY_INFOR_ACC, "");
	}


	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));

		// return user
		return user;
	}

	public User getUser(){
		String jsonUser = pref.getString(KEY_NAME, null);
		User user=null;
		try {
			if (jsonUser != null) {
				user = new User(jsonUser);
			}
		}catch (Exception e){}
		return user;
	}
	public void setUser(String fullName, String phone, String avatarId, String avatar_url){
		String jsonUser = pref.getString(KEY_NAME, null);
		try {
			if (jsonUser != null) {
				JSONObject jso = new JSONObject(jsonUser);
				jso.remove("name");
				jso.remove("phone");
				jso.remove("avatar_id");
//				jso.remove("avatar_url");
				//
				jso.put("name",fullName);
				jso.put("phone",phone);
				jso.put("avatar_id",avatarId);
//				jso.put("avatar_url",avatar_url);
				//
				createLoginSession(jso.toString());

			}
		}catch (Exception e){}
	}

	public String getUsername(){
		return pref.getString(KEY_USERNAME, "");
	}
	public String getPassword(){
		return pref.getString(KEY_PASSWORD, "");
	}
	public void setPassword(String password){
		editor.putString(KEY_PASSWORD, password);
		editor.commit();
	}
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
		
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public int isLoggedIn(){
		try{
			return pref.getInt(IS_LOGIN, 0);
		}catch (Exception e){
			return 0;
		}
	}
	public void setLoginInfo(String username, String password, int isLogin){
		// Storing login value as TRUE
		editor.putInt(IS_LOGIN, 1);
		editor.putString(KEY_USERNAME, username);
		editor.putString(KEY_PASSWORD, password);
		// commit changes
		editor.commit();
	}
	//
	// Get Login State
	public int isLogInWithEmail(){
		try{
			return pref.getInt(KEY_LOGIN_WITH_EMAIL, 0);
		}catch (Exception e){
			return 0;
		}
	}
	public void setLogInWithEmail(int withEmail){
		editor.putInt(KEY_LOGIN_WITH_EMAIL, withEmail);
		editor.commit();
	}
}
