package com.vnpt.media.bookingcar.firebase;

import android.app.NotificationManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vnpt.media.bookingcar.utils.NotificationUtils;

import org.json.JSONObject;


/**
 *
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    private NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        JSONObject msgObj = null;
        try {



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //




}
