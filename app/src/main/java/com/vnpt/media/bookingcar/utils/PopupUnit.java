package com.vnpt.media.bookingcar.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.vnpt.media.bookingcar.R;

public class PopupUnit {

    // popup chỉ thông báo thông tin và không có xác nhận
    public static Dialog dialogAlert;

    // cửa sổ chọn các lựa chọn
    public static void ShowPopupMenuDown() {

    }

    //
    public static void ShowPopupMessage() {

    }

    //
    public static void ShowPopupConfirm() {

    }

    public static void openPopupAlert(Activity mContext, String strMessage) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width;
        int height;
        if (metrics.widthPixels < metrics.heightPixels) {
            width = metrics.widthPixels * 90 / 100;
//            height = metrics.heightPixels * 50 / 100;
        } else {
            width = metrics.widthPixels * 50 / 100;
//            height = metrics.heightPixels * 90 / 100;
        }
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.popup_alert_layout, null);
        dialogAlert = new Dialog(mContext, R.style.DialogThemeCorner);
        dialogAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAlert.setContentView(dialogView);
        dialogAlert.setCancelable(false);
        dialogAlert.show();
        dialogAlert.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogAlert.getWindow().setGravity(Gravity.CENTER);
        TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvTitle);
        TextView tvMessage = (TextView) dialogView.findViewById(R.id.tvMessage);
        TextView tvCancel = (TextView) dialogView.findViewById(R.id.tvClosePopup);
        String message = strMessage;
        tvMessage.setText(message);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopupAlert();
            }
        });

    }

    public static void closePopupAlert() {
        if (dialogAlert != null && dialogAlert.isShowing()) {
            dialogAlert.dismiss();
            dialogAlert = null;
        }
    }
}
