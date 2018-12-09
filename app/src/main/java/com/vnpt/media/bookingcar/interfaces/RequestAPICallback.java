package com.vnpt.media.bookingcar.interfaces;
import com.google.gson.JsonObject;
public interface RequestAPICallback {
    public void ListenerRequest(int typeRequestAPI, JsonObject jsonObject);
}
