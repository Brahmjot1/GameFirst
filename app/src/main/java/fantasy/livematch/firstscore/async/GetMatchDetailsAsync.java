package fantasy.livematch.firstscore.async;

import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import fantasy.livematch.firstscore.activity.MatchDetailsActivity;
import fantasy.livematch.firstscore.model.RequestModel;
import fantasy.livematch.firstscore.model.ResponseModel;
import fantasy.livematch.firstscore.util.ApiClient;
import fantasy.livematch.firstscore.util.ApiInterface;
import fantasy.livematch.firstscore.util.Global_App;
import fantasy.livematch.firstscore.util.Utility;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetMatchDetailsAsync {

    private Activity activity;
    private JSONObject jObject;

    public GetMatchDetailsAsync(final Activity activity, RequestModel requestModel) {
        this.activity = activity;
        try {

            jObject = new JSONObject();
            jObject.put("device_id", Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID));
            jObject.put("matchId", requestModel.getMatchId());
            jObject.put("FCMID", Utility.getFCMRegId(activity));
            jObject.put("adId", Utility.getAdID(activity));
            if (Utility.getReferData(activity).length() > 0) {
                jObject.put("deplinkdata", new JSONObject(Utility.getReferData(activity)));
            } else {
                jObject.put("deplinkdata", "");
            }
            jObject.put("deviceName", Build.MODEL);
            jObject.put("deviceVersion", Build.VERSION.RELEASE);
            jObject.put("appVersion", Utility.getAppVersion(activity));
            jObject.put("verifyInstallerId", Utility.verifyInstallerId(activity));
            jObject.put("todayOpen", String.valueOf(Utility.getTodayOpen(activity)));
            jObject.put("totalOpen", String.valueOf(Utility.getTotalOpen(activity)));

            Log.e("jObject--)",""+jObject.toString());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<ResponseModel> call = apiService.GetMatchDetails(jObject.toString());
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    onPostExecute(response.body());
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

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
                ((MatchDetailsActivity) activity).setHomeData(activity, responseModel);
            } else if (responseModel.getStatus().equals(Global_App.STATUS_ERROR)) {
                Utility.NotifyFinish(activity, Global_App.APPNAME, responseModel.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
          //  Utility.NotifyFinish(activity, Global_App.APPNAME, e.getMessage());
        }
    }
}
