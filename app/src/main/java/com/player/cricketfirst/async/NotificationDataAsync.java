package com.player.cricketfirst.async;

import android.app.Activity;
import android.provider.Settings;
import android.util.Log;
import com.player.cricketfirst.activity.NotificationActivity;
import com.player.cricketfirst.model.ResponseModel;
import com.player.cricketfirst.util.ApiClient;
import com.player.cricketfirst.util.ApiInterface;
import com.player.cricketfirst.util.UrlController;
import com.player.cricketfirst.util.Utility;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDataAsync {
    private Activity activity;
    private JSONObject jObject;
    UrlController urlControl;

    public NotificationDataAsync(final Activity activity) {
        this.activity = activity;
        try {
            urlControl = new UrlController(activity);
            urlControl.showAdLoader();
            jObject = new JSONObject();
            jObject.put("device_id", Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID));
            jObject.put("adId", Utility.getAdID(activity));
            jObject.put("todayOpen", String.valueOf(Utility.getTodayOpen(activity)));
            jObject.put("totalOpen", String.valueOf(Utility.getTotalOpen(activity)));
            jObject.put("deviceName", android.os.Build.MODEL);
            jObject.put("appVersion", Utility.getAppVersion(activity));
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Log.e("TaskObject", "" + jObject.toString());
            Call<ResponseModel> call = apiService.getNotification(jObject.toString());
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    onPostExecute(response.body());
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    if (!call.isCanceled()) {
                        Utility.NotifyFinish(activity, UrlController.APPNAME, UrlController.msg_Service_Error);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onPostExecute(ResponseModel responseModel) {
        try {
            urlControl.dismissADLoader();
            Log.e("RESPONSE", "" + new Gson().toJson(responseModel));
            if (responseModel.getStatus().equals(UrlController.STATUS_SUCCESS)) {
                ((NotificationActivity) activity).setData(responseModel);
            } else if (responseModel.getStatus().equals(UrlController.STATUS_ERROR)) {
                Utility.NotifyFinish(activity, UrlController.APPNAME, responseModel.getMessage());
            } else if (responseModel.getStatus().equals("2")) {
                Utility.NotifyFinish(activity, UrlController.APPNAME, responseModel.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            urlControl.dismissADLoader();
            Log.e("Exception--)", "" + e.getMessage());
            Utility.NotifyFinish(activity, UrlController.APPNAME, e.getMessage());
        }
    }
}
