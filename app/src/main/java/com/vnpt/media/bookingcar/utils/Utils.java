package com.vnpt.media.bookingcar.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Browser;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.view.ViewGroup;
import android.support.v4.view.ViewCompat;
import android.widget.TextView;

@SuppressWarnings("UnnecessaryLocalVariable")
public class Utils {

    final static String TAG = "Utils";
    public static void configuredHideYView(View v) {
        v.setScaleY(0);
        v.setPivotY(0);
    }

    public static String replateToLatex(String string) {
        String strLatex = string;
        if (string.length()>0) {
//             strLatex = "\\(" + string.replace(" ", "\\ ").replace("\n", "\\\\") + "\\)";
            String re1 = "class=\"math-tex\"";
            if (string.contains(re1)){
                strLatex = strLatex.replace(re1,"");
            }

        }
        return strLatex;
    }



    /**
     * calculate the optimal width for the image
     *
     * @param screenWidth
     * @return
     */
    public static int optimalImageWidth(int screenWidth) {
        int preOptimalWidth = screenWidth / 2;

        if (preOptimalWidth >= 720) {
            return 720;
        } else if (preOptimalWidth >= 540) {
            return 540;
        } else if (preOptimalWidth >= 360) {
            return 360;
        } else {
            return 360;
        }
    }


    /**
     * @param context
     * @param px
     * @return
     */
    public static float dpFromPx(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    /**
     * @param context
     * @param dp
     * @return
     */
    public static float pxFromDp(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public int dpToPx(Context mActivity,int dp) {
        DisplayMetrics displayMetrics = mActivity.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    /**
     * http://stackoverflow.com/questions/10854211/android-store-inputstream-in-file
     *
     * @param in
     * @param file
     */

    public static void copyInputStreamToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * http://stackoverflow.com/questions/3934331/android-how-to-encrypt-a-string
     *
     * @param s
     * @return
     */
    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String readFile(File file)
            throws IOException {
        return new Scanner(file, "UTF-8").useDelimiter("\\A").next();
    }

    /**
     * http://developer.android.com/training/basics/data-storage/files.html
     *
     * @param albumName
     * @return
     */
    public static File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.i("wall-splash", "Directory not created");
        }
        return file;
    }

    /**
     * http://developer.android.com/training/basics/data-storage/files.html
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static String getFileName(String pathFile){
        if(pathFile.equals("")){
            return "";
        }
        String fileName="";
        try{
            int index= pathFile.lastIndexOf("/");
            if (index>=0) {
                fileName = pathFile.substring(index + 1);
            }else{
                fileName = pathFile;
            }
        }catch(Exception e){}
        return fileName;
    }



    /*
    * kiem tra ket noi mang
    * */
    public static boolean hasConnection(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cmManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cmManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        NetworkInfo mobileNetwork = cmManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }
        NetworkInfo activeNetwork = cmManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }

    public static ProgressDialog progressDialog = null;

    public static void showProgress(Activity mActivity) {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(mActivity);
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Đang xử lý...");
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeProgress() {
        if (progressDialog != null & progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static boolean emailValidate(String str) {
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean phoneValidate(String str) {
        boolean valid = false;
        if(str.equals("")){
            valid = false;
        }else{
            if(str.matches("\\d+")){
                Pattern pattern = Pattern.compile("^[0-9]{10,11}$");
                Matcher matcher = pattern.matcher(str);
                valid = matcher.matches();
            }
        }
        return valid;
    }

	public static boolean canScroll(View v, boolean checkV, int dy, int x, int y) {
		if (v instanceof ViewGroup) {
			final ViewGroup group = (ViewGroup) v;
			final int scrollX = v.getScrollX();
			final int scrollY = v.getScrollY();
			final int count = group.getChildCount();
			for (int i = count - 1; i >= 0; i--) {
				final View child = group.getChildAt(i);
				if (x + scrollX >= child.getLeft()
						&& x + scrollX < child.getRight()
						&& y + scrollY >= child.getTop()
						&& y + scrollY < child.getBottom()
						&& canScroll(child, true, dy,
								x + scrollX - child.getLeft(), y + scrollY
										- child.getTop())) {
					return true;
				}
			}
		}
		return checkV && ViewCompat.canScrollVertically(v, -dy);
	}

    public ProgressDialog showDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.show();
        return progressDialog;
    }

    public static String formatDateTime(String strCurrentDate){
        String dateTime = strCurrentDate;
        try{
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newDate = formatDate.parse(strCurrentDate);
            formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            dateTime = formatDate.format(newDate);
        }catch(Exception e){}
        return dateTime;
    }

    public static String formatDate(String strtDate){
        String dateTime = strtDate;
        try{
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newDate = formatDate.parse(strtDate);
            formatDate = new SimpleDateFormat("dd/MM/yyyy");
            dateTime = formatDate.format(newDate);
        }catch(Exception e){}
        return dateTime;
    }

    public static boolean checkFormatVNDate(String strtDate){
        boolean isDate = false;
        try{
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            Date newDate = formatDate.parse(strtDate);
            if(newDate!=null){
                isDate = true;
            }
        }catch(Exception e){}
        return isDate;
    }

    public static String chatingSendTime(String strDate){
        String dateTime = strDate;
        try{
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newDate = formatDate.parse(strDate);
            formatDate = new SimpleDateFormat("HH:mm");
            dateTime = formatDate.format(newDate);
        }catch(Exception e){}
        return dateTime;
    }

    public static boolean isDifferentDate(String strDate1, String strDate2){
        boolean isDifferent = false;
        try{
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = formatDate.parse(strDate1);
            Date date2 = formatDate.parse(strDate2);
            Log.i(TAG, "isDifferentDate->" + strDate1.substring(0,10) + "#" + strDate2.substring(0,10));
            if(!strDate1.substring(0,10).equals(strDate2.substring(0,10))){
                isDifferent = true;
            }
        }catch(Exception e){}
        return isDifferent;
    }

    public static String displayPostDate(String date){
        SimpleDateFormat decodeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Calendar c = Calendar.getInstance();
        try{
            date = date.replace(".0", "");
            Date d1 = decodeDateFormat.parse(date);
            Date d2 = new Date();
            long time = d2.getTime() - d1.getTime();

            long second = 1000;
            long minute = 60*1000;
            long hour = minute*60;
            long day =hour*24;
            long week = day*7;
            long month = day*30;

            if(time >= day){
                c.setTime(d1);
                return dateFormat.format(c.getTime());
            }else if((time>=hour) && (time<day)){
                Calendar c1 = Calendar.getInstance();
                c1.setTime(d1);
                int dayOfMonth1 = c1.get(Calendar.DAY_OF_MONTH);
                Calendar c2 = Calendar.getInstance();
                c2.setTime(d2);
                int dayOfMonth2 = c2.get(Calendar.DAY_OF_MONTH);
                if(dayOfMonth1==dayOfMonth2) {
                    int hours = (int) (time / hour);
                    return "" + hours + " giờ trước";
                }else{
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    String time1 = timeFormat.format(d1);
                    return "Hôm qua: " + time1;
                }
            }else if((time>=minute) && (time<hour)) {
                int minutes = (int) (time / minute);
                return "" + minutes + " phút trước";
            }else if(time>second && time<minute){
                int seconds = (int)(time/second);
                return "" + seconds + " giây trước";
            }else{
                return "1 giây trước";
            }
        }catch (Exception ex) {
            return date;
        }
    }

    public static int getCountDownTime(String date){
        SimpleDateFormat decodeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        int seconds = 0;
        try{
            date = date.replace(".0", "");
            Date d1 = decodeDateFormat.parse(date);
            Date d2 = new Date();
            long time = d1.getTime() - d2.getTime();
            long second = 1000;
            seconds = (int)(time/second);
            if(seconds<0){
                seconds=0;
            }
        }catch (Exception ex) {
            return 0;
        }
        return seconds;
    }

    public static String displayCountDownTime(int countDownTime){
        String timeDisplay = "";
        if(countDownTime<=0){
            return "00:00";
        }
        try{
            int minutes = (int)(countDownTime/60);
            int seconds = countDownTime % 60;
            String mins = minutes<10? "0" + minutes: "" + minutes;
            String secs = seconds<10? "0" + seconds: "" + seconds;
            timeDisplay = mins + ":" + secs;
        }catch (Exception ex) {
            return "";
        }
        return timeDisplay;
    }

    public static Date getDate(String date){
        SimpleDateFormat decodeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        Date d1=null;
        try{
            date = date.replace(".0", "");
            d1 = decodeDateFormat.parse(date);
        }catch (Exception ex) {
        }
        return d1;
    }

    public static String getSafeSubstring(String s, int maxLength){
        if(!s.isEmpty()){
            try {
                if (s.length() >= maxLength) {
                    int index = s.indexOf(" ", maxLength - 6);
                    if (index != -1) {
                        return s.substring(0, index) + "...";
                    } else {
                        return s.substring(0, maxLength);
                    }
                }
            }catch (Exception e){}
        }
        return s;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
            (float) maxImageSize / realImage.getWidth(),
            (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());
        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();
            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public static TextView handleText(TextView tv, String content) {
        SpannableStringBuilder sp = new SpannableStringBuilder(content);
        //又碰上一个坑，在Android中的\p{Alnum}和Java中的\p{Alnum}不是同一个值，非得要我换成[a-zA-Z0-9]才行
        Pattern pattern = Pattern.compile("(http|https|ftp|svn)://([a-zA-Z0-9]+[/?.?])" +
                "+[a-zA-Z0-9]*\\??([a-zA-Z0-9]*=[a-zA-Z0-9]*&?)*");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String url = matcher.group();
            int start = content.indexOf(url);
            if (start >= 0) {
                int end = start + url.length();
                sp.setSpan(new URLSpan(url), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(getClickableSpan(url), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        tv.setText(sp);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        return tv;
    }

    /**
     * 处理html数据的高亮与响应
     *
     * @param tv
     * @param content
     * @return
     */
    public static TextView handleHtmlText(TextView tv, String content) {
        SpannableStringBuilder sp = new SpannableStringBuilder(Html.fromHtml(content));
        URLSpan[] urlSpans = sp.getSpans(0, sp.length(), URLSpan.class);
        for (final URLSpan span : urlSpans) {
            int start = sp.getSpanStart(span);
            int end = sp.getSpanEnd(span);
            sp.setSpan(getClickableSpan(span.getURL()), start, end, Spanned
                    .SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(sp);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        return tv;
    }
    private static ClickableSpan getClickableSpan(final String url) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Uri uri = Uri.parse(url);
                Context context = widget.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
                context.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
    }

    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static final String getVideoIdFromYoutubeURL(String url){
        String videoId = "";
        try{
            if (url.length()>0 && url.indexOf("youtube")>0){
                String[] separated = url.split("v=");
                if(separated.length>0){
                    videoId = separated[1];
                }
            }else if(url.indexOf("http")==-1){
                videoId = url;
            }
        }catch (Exception e){}
        return videoId;
    }

    public static String getHLSVP(String content) {
        String hlsvp = "";
        try {
            if (content != null) {
                String[] param = content.split("&");
                for (String row : param) {
                    String[] param1 = row.split("=");
                    if (param1[0].equals("hlsvp")) {
                        hlsvp = param1[1];
                        break;
                    }
                }
            }
            if(hlsvp.length()>0){
                hlsvp = URLDecoder.decode(hlsvp,"UTF-8");
            }
        } catch (Exception ex) {
            Log.e("SampleChooserActivity", ex.toString());
        }
        return hlsvp;
    }

    public static String getDASHMPD(String content) {
        String dashmpd = "";
        try {
            if (content != null) {
                String[] param = content.split("&");
                for (String row : param) {
                    String[] param1 = row.split("=");
                    if (param1[0].equals("dashmpd")) {
                        dashmpd = param1[1];
                        break;
                    }
                }
            }
            if(dashmpd.length()>0){
                dashmpd = URLDecoder.decode(dashmpd,"UTF-8");
            }
        } catch (Exception ex) {
            Log.e("SampleChooserActivity", ex.toString());
        }

        return dashmpd;
    }

    public static int dpToPx(Activity mActivity,int dp) {
        DisplayMetrics displayMetrics = mActivity.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
