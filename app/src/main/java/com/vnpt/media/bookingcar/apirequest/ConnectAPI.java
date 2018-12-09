package com.vnpt.media.bookingcar.apirequest;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.vnpt.media.bookingcar.interfaces.RequestAPICallback;
import com.vnpt.media.bookingcar.utils.Utils;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Administrator on 8/4/2016.
 */
public class ConnectAPI {

    private RequestAPICallback listenerRequestIPA;
    private Activity mActivity;

    public ConnectAPI(Activity activity, RequestAPICallback listenerRequestIPA) {
        this.listenerRequestIPA = listenerRequestIPA;
        this.mActivity=activity;
        Ion.getDefault(mActivity).configure().setLogging("ion-sample", Log.DEBUG);
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                        return;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                        return;
                    }
                }
        };
        Ion.getDefault(mActivity).getHttpClient().getSSLSocketMiddleware().setTrustManagers(trustAllCerts);

        try {

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
//            Ion.getDefault(mActivity).getHttpClient().getSSLSocketMiddleware().setSSLContext(sc);
            Ion.getDefault(mActivity).configure().createSSLContext("TLS");
        } catch (java.security.NoSuchAlgorithmException e) {

        } catch (java.security.KeyManagementException e) {


        }
    }

    public void clientRequestAPI(final int typeRequest, String method, String severAPI, JsonObject jsonObjectParam){
        Future future = Ion.with(mActivity)
                .load(method, ServerPath.PATH_SERVICE + severAPI)
                .addQuery("action", "dummyAction")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer ")
                .setLogging("ION_VERBOSE_LOGGING", Log.VERBOSE)
                .setJsonObjectBody(jsonObjectParam)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        listenerRequestIPA.ListenerRequest(typeRequest, result);

                    }
                });

    }
    public void clientRequestAPIGET(final int typeRequest, String severAPI, String keyPara, String value){

        String url ="";
        if (keyPara.length()>0){
            url = ServerPath.PATH_SERVICE + severAPI +"?"+keyPara+"="+value;
        }else{
            url = ServerPath.PATH_SERVICE + severAPI;
        }

        Future future = Ion.with(mActivity)
                .load(url)
                .addQuery("action", "dummyAction")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " )
                .setLogging("ION_VERBOSE_LOGGING", Log.VERBOSE)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        //
                        listenerRequestIPA.ListenerRequest(typeRequest, result);

                    }
                });

    }
    public void requestMultiParam(final int typeRequest,String severAPI){
        Future future = Ion.with(mActivity)
                .load( ServerPath.PATH_SERVICE + severAPI)
                .addQuery("action", "dummyAction")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer ")
                .setLogging("ION_VERBOSE_LOGGING", Log.VERBOSE)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        listenerRequestIPA.ListenerRequest(typeRequest, result);

                    }
                });

    }
    public void clientRequestLogin(final int typeRequest , String method, String severAPI, String userName, String password) {
        Utils.showProgress(mActivity);
        Future future = Ion.with(mActivity)
                .load(method, ServerPath.PATH_SERVICE + severAPI)
                .setLogging("ION_VERBOSE_LOGGING", Log.VERBOSE)
                .setBodyParameter("username",userName)
                .setBodyParameter("password",password)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Utils.closeProgress();
                        listenerRequestIPA.ListenerRequest(typeRequest, result);

                    }
                });

    }

}
