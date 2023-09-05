package fantasy.livematch.firstscore.util;


import fantasy.livematch.firstscore.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("HomeData")
    Call<ResponseModel> getHomeData(@Field("details") String device_id);

    @FormUrlEncoded
    @POST("GetMatchDetails")
    Call<ResponseModel> GetMatchDetails(@Field("details") String device_id);

    @FormUrlEncoded
    @POST("NotificationList")
    Call<ResponseModel> getNotification(@Field("details") String device_id);

    @FormUrlEncoded
    @POST("MoreApps")
    Call<ResponseModel> GetMoreApps(@Field("details") String device_id);


}
