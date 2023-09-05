package com.player.cricketfirst.async;

import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.player.cricketfirst.activity.SplashScreenActivity;
import com.google.gson.Gson;
import com.player.cricketfirst.model.ResponseModel;
import com.player.cricketfirst.util.ApiClient;
import com.player.cricketfirst.util.ApiInterface;
import com.player.cricketfirst.util.Global_App;
import com.player.cricketfirst.util.Utility;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetHomeDataAsync {
    private Activity activity;
    private String from;
    private JSONObject jObject;

    public GetHomeDataAsync(final Activity activity, String from) {
        this.activity = activity;
        this.from = from;
        try {
            jObject = new JSONObject();
            jObject.put("device_id", Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID));
            jObject.put("FCMID", Utility.getFCMRegId(activity));
            jObject.put("adId", Utility.getAdID(activity));
            if (Utility.getReferData(activity).length() > 0) {
                jObject.put("deplinkdata", new JSONObject(Utility.getReferData(activity)));
            } else {
                jObject.put("deplinkdata", "");
            }
            jObject.put("todayOpen", String.valueOf(Utility.getTodayOpen(activity)));
            jObject.put("totalOpen", String.valueOf(Utility.getTotalOpen(activity)));
            jObject.put("deviceName", Build.MODEL);
            jObject.put("deviceVersion", Build.VERSION.RELEASE);
            jObject.put("appVersion", Utility.getAppVersion(activity));
            jObject.put("verifyInstallerId", Utility.verifyInstallerId(activity));
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Log.e("jObject--)",""+jObject.toString());
            Call<ResponseModel> call = apiService.getHomeData(jObject.toString());
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                    Log.e("Reposne--)",""+new Gson().toJson(response.body()));
                    onPostExecute(response.body());

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                    Log.e("Error--)",""+t.getMessage());
                    if (!call.isCanceled()) {
                        Utility.NotifyFinish(activity, Global_App.APPNAME, Global_App.msg_Service_Error);
                    }
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void onPostExecute(ResponseModel responseModel) {
        try {
            if (responseModel.getStatus().equals(Global_App.STATUS_SUCCESS)) {
                Utility.setAsync(activity, "HomeData", new Gson().toJson(responseModel));
                ((SplashScreenActivity) activity).setHomeData(activity, responseModel);
            } else if (responseModel.getStatus().equals(Global_App.STATUS_ERROR)) {
                Utility.Notify(activity, Global_App.APPNAME, responseModel.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
