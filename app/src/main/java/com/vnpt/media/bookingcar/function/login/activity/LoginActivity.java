package com.vnpt.media.bookingcar.function.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.vnpt.media.bookingcar.R;
import com.vnpt.media.bookingcar.apirequest.ConnectAPI;
import com.vnpt.media.bookingcar.apirequest.ServerPath;
import com.vnpt.media.bookingcar.function.bookingcar.activity.MainActivity;
import com.vnpt.media.bookingcar.interfaces.RequestAPICallback;
import com.vnpt.media.bookingcar.utils.NotificationUtils;
import com.vnpt.media.bookingcar.utils.SessionManager;
import com.vnpt.media.bookingcar.utils.TypeRequestAPIs;

import customfonts.EditText__SF_Pro_Display_Light;
import customfonts.MyTextView_Roboto_Regular;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static String TAG = LoginActivity.class.getSimpleName();
    private MyTextView_Roboto_Regular imgLogin;
    private EditText__SF_Pro_Display_Light edUserName, edPassword;
    private NotificationUtils notificationUtils;
    private ConnectAPI connectAPI;
    private Activity mActivity;
    private SessionManager session;
    private RequestAPICallback requestAPICallback = new RequestAPICallback() {
        @Override
        public void ListenerRequest(int typeRequestAPI, JsonObject jsonObject) {
            switch (typeRequestAPI) {
                case TypeRequestAPIs.TYPE_REQUEST_LOGIN:
                    if (jsonObject != null) {
                        JsonObject header = jsonObject.getAsJsonObject("header");
                        Log.e(TAG, "Json login-->" + jsonObject);
                        if (header.has("success")) {
                            boolean success = header.get("success").getAsBoolean();
                            Log.e(TAG, success + "");
                            if (success) {
                                session.createLoginSession(jsonObject.get("object").toString());
                                Log.e(TAG, "object -->" + jsonObject.get("object").toString());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.mess_login_failed), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.mess_login_failed), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mActivity, mActivity.getResources().getString(R.string.mess_login_failed), Toast.LENGTH_SHORT).show();
                    }


                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_icar);
        mActivity = this;
        connectAPI = new ConnectAPI(mActivity, requestAPICallback);
        session = new SessionManager(mActivity);
        notificationUtils = new NotificationUtils(mActivity);

        imgLogin = findViewById(R.id.img_login);
        imgLogin.setOnClickListener(this);
        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_login:
                if (edUserName.getText().toString().trim().length() > 0 && edPassword.getText().toString().trim().length() > 0) {
                    connectAPI.clientRequestLogin(TypeRequestAPIs.TYPE_REQUEST_LOGIN, "POST", ServerPath.ACCOUNT_LOGIN, edUserName.getText().toString().trim(), edPassword.getText().toString().trim());
                }
                break;
        }
    }


}
