package com.vnpt.media.bookingcar.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by DungHT on 03/12/2015.
 */
public class User implements Serializable {
    public static final int ACTIVE_STATUS = 1;
    public String username = "";
    public String name = "";
    public String name_display = "";
    public String email = "";
    public String phone = "";
    public String user_group_id = "";
    public String avatar_type = "";
    public String avatar_file_ext = "";
    public String avatar_content = "";
    public String avatar_url = "";
    public String avatar_id = "";


    public User() {

    }

    public User(String jsoObject) {
        try {
            JSONObject jso = new JSONObject(jsoObject);
            username = jso.optString("username");
            name = jso.optString("name");
            name_display = jso.optString("name_display");
            email = jso.optString("email");
            phone = jso.optString("phone");
            user_group_id = jso.optString("user_group_id");
            avatar_type = jso.optString("avatar_type");
            avatar_file_ext = jso.optString("avatar_file_ext");
            avatar_content = jso.optString("avatar_content");
            avatar_url = jso.optString("avatar_url");
            avatar_id = jso.optString("avatar_id");

        } catch (Exception e) {
        }
    }

    public String toJSONString() {
        JSONObject jso = new JSONObject();
        try {
            jso.accumulate("username", this.username);
            jso.accumulate("name", this.name);
            jso.accumulate("name_display", this.name_display);
            jso.accumulate("email", this.email);
            jso.accumulate("phone", this.phone);
            jso.accumulate("user_group_id", this.user_group_id);
            jso.accumulate("avatar_type", this.avatar_type);
            jso.accumulate("avatar_file_ext", this.avatar_file_ext);
            jso.accumulate("avatar_content", this.avatar_content);
            jso.accumulate("avatar_url", this.avatar_url);
            jso.accumulate("avatar_id", this.avatar_id);

        } catch (Exception e) {
        }
        return jso.toString();
    }

    public static User ParseJSON(String json) {
        if (json.length() > 0) {
            try {
                JSONObject objectJson = new JSONObject(json);
                JSONObject objectItem = objectJson.optJSONObject("user");
                User user = new User();
                user.username = (objectItem.optString("username") == null ? "" : objectItem.optString("username"));
                user.name = (objectItem.optString("name") == null ? "" : objectItem.optString("name"));
                user.name_display = (objectItem.optString("name_display") == null ? "" : objectItem.optString("name_display"));
                user.email = (objectItem.optString("email") == null ? "" : objectItem.optString("email"));
                user.phone = (objectItem.optString("phone") == null ? "" : objectItem.optString("phone"));
                user.user_group_id = (objectItem.optString("user_group_id") == null ? "" : objectItem.optString("user_group_id"));
                user.avatar_type = (objectItem.optString("avatar_type") == null ? "" : objectItem.optString("avatar_type"));
                user.avatar_file_ext = (objectItem.optString("avatar_file_ext") == null ? "" : objectItem.optString("avatar_file_ext"));
                user.avatar_content = (objectItem.optString("avatar_content") == null ? "" : objectItem.optString("avatar_content"));
                user.avatar_url = (objectItem.optString("avatar_url") == null ? "" : objectItem.optString("avatar_url"));
                user.avatar_id = (objectItem.optString("avatar_id") == null ? "" : objectItem.optString("avatar_id"));

                return user;
            }
            catch (JSONException e) {
                Log.i("Authenticate", "ParseJSON" + e.toString());
                return null;
            }
        }
        else {
            Log.i("Authenticate", "Couldn't get any data from the url");
            return null;
        }
    }

}
