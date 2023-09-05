package com.player.cricketfirst.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.player.cricketfirst.MyApplication;
import com.player.cricketfirst.R;
import com.player.cricketfirst.async.GetHomeDataAsync;
import com.player.cricketfirst.model.ResponseModel;
import com.player.cricketfirst.util.Utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity {

    ProgressBar probrMain;
    public static boolean isLoadSplash = false;
    public static ResponseModel responseMain;
    AppOpenAd.AppOpenAdLoadCallback loadCallback;
    AppOpenAd appOpenAdload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_splash_screen);
        probrMain = findViewById(R.id.probrMain);

        try {
            if (getIntent().getStringExtra("bundle") != null && getIntent().getStringExtra("bundle").trim().length() > 0) {
                Utility.setNotificationData(SplashScreenActivity.this, getIntent().getExtras().getString("bundle"));
                Utility.setIsFromnotification(SplashScreenActivity.this, true);
            } else {
                Utility.setIsFromnotification(SplashScreenActivity.this, false);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            Utility.setIsFromnotification(SplashScreenActivity.this, false);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("global");

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            FirebaseMessaging.getInstance().subscribeToTopic("globalV" + version);
            Utility.setAppVersion(SplashScreenActivity.this, version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        int todayOpen = Utility.getTodayOpen(SplashScreenActivity.this) + 1;
        Utility.setTodayOpen(SplashScreenActivity.this, todayOpen);

        if (Utility.getDate(SplashScreenActivity.this).matches("0")) {
            Utility.setDate(SplashScreenActivity.this, getCurrentDate());
        }

        if (!Utility.getDate(SplashScreenActivity.this).matches(getCurrentDate())) {
            Utility.setTodayOpen(SplashScreenActivity.this, 1);
            Utility.setTotalOpen(SplashScreenActivity.this, Utility.getTotalOpen(SplashScreenActivity.this) + 1);
            Utility.setDate(SplashScreenActivity.this, getCurrentDate());
            Utility.cleanDialogID(SplashScreenActivity.this);
        }

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            try {
                                if (deepLink != null) {
                                    String str = new Gson().toJson(splitQuery(new URL(deepLink.toString())));
                                    Utility.setReferData(SplashScreenActivity.this, str.toString());

                                } else {
                                    Utility.setReferData(SplashScreenActivity.this, "");
                                }

                            } catch (UnsupportedEncodingException | MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utility.setReferData(SplashScreenActivity.this, "");
                    }
                });

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String advertId = null;
                try {
                    advertId = idInfo.getId();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return advertId;
            }

            @Override
            protected void onPostExecute(String advertId) {
                Utility.setAdID(SplashScreenActivity.this, advertId);
            }
        };
        task.execute();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult();
                        Utility.setFCMRegId(SplashScreenActivity.this, token);
                    }
                });

        if (checkPermission()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new GetHomeDataAsync(SplashScreenActivity.this, "Spls");
                }
            }, 1200);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new GetHomeDataAsync(SplashScreenActivity.this, "Spls");
                }
            }, 3500);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLoadSplash = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isLoadSplash = false;
    }

    public String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");

        return curFormater.format(c);
    }


    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    public void setHomeData(Activity activity, ResponseModel responseModel) {

        if (responseModel != null) {

            responseMain = responseModel;

            if (responseModel.getLovin_bannerID() != null) {
                Utility.setLovinBanner(SplashScreenActivity.this, responseModel.getLovin_bannerID());
            }
            if (responseModel.getGoogole_bannerID() != null) {
                Utility.setGoogleBanner(SplashScreenActivity.this, responseModel.getGoogole_bannerID());
            }
            if (responseModel.getLovin_appOpenID() != null) {
                Utility.setLovinAppopen(SplashScreenActivity.this, responseModel.getLovin_appOpenID());
                Utility.setLoviOpenIDContext(getApplicationContext(), responseModel.getLovin_appOpenID());
            }

            if (responseModel.getGoogole_appOpenID() != null) {
                Utility.setGoogleAppOpen(SplashScreenActivity.this, responseModel.getGoogole_appOpenID());
                Utility.setGOpenIDContext(getApplicationContext(), responseModel.getGoogole_appOpenID());
            }
            if (responseModel.getLovin_indstrialID() != null) {
                Utility.setLovinInter(SplashScreenActivity.this, responseModel.getLovin_indstrialID());
            }
            if (responseModel.getGoogole_indstrialID() != null) {
                Utility.setGoogleInter(SplashScreenActivity.this, responseModel.getGoogole_indstrialID());
            }
            if (responseModel.getLovin_reawardID() != null) {
                Utility.setLovinReward(SplashScreenActivity.this, responseModel.getLovin_reawardID());
            }
            if (responseModel.getLovin_nativeID() != null) {
                Utility.setLovinNative(SplashScreenActivity.this, responseModel.getLovin_nativeID());
            }
            if (responseModel.getGoogole_nativeID() != null) {
                Utility.setGoogleNative(SplashScreenActivity.this, responseModel.getGoogole_nativeID());
            }
            if (responseModel.getIsApplovinAdshow() != null) {
                Utility.setIsLovinAdShow(SplashScreenActivity.this, responseModel.getIsApplovinAdshow());
            }
            if (responseModel.getAppOpenAdType() != null) {
                Utility.setIsGoogleAppOpen(SplashScreenActivity.this, responseModel.getAppOpenAdType());
                Utility.setIsGoogleAppOpenContext(getApplicationContext(), responseModel.getAppOpenAdType());
            }

            if (Utility.getIsGoogleAppOpen(SplashScreenActivity.this).matches("1")) {
                OpenAppAds();
            }
            else  if (Utility.getIsGoogleAppOpen(SplashScreenActivity.this).matches("2"))
            {
                AppLovinSdk.getInstance(SplashScreenActivity.this).setMediationProvider("max");
                AppLovinSdk.initializeSdk(this, new AppLovinSdk.SdkInitializationListener() {
                    @Override
                    public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
//                        if (Utility.getIsGoogleAppOpen(SplashScreenActivity.this).matches("1")) {
//                            loadAppAds();
//                        } else {
//
//                            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
//                            startActivity(i);
//                            finish();
//                        }
                        loadAppAds();
                    }
                });
            }
            else
            {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
    public void OpenAppAds() {

        this.loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            public void onAdLoaded(AppOpenAd appOpenAd) {

                appOpenAdload = appOpenAd;
                Log.e("app_open_ads", "load ");

                if (appOpenAdload != null) {
                    appOpenAdload.setFullScreenContentCallback(new FullScreenContentCallback() {
                        public void onAdDismissedFullScreenContent() {

                            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();

                        }

                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }

                        public void onAdShowedFullScreenContent() {

                        }
                    });
                    appOpenAdload.show(SplashScreenActivity.this);
                }
                else
                {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            public void onAdFailedToLoad(LoadAdError loadAdError) {

                appOpenAdload = null;
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                Log.e("loadAdError", "--) " + loadAdError.getMessage());
            }
        };

        AppOpenAd.load((Context) this, Utility.getGoogleAppOpen(SplashScreenActivity.this), new AdRequest.Builder().build(), 1, this.loadCallback);
    }

    public void loadAppAds() {



            MaxAppOpenAd appOpenAd = new MaxAppOpenAd(Utility.getLovinAppopen(SplashScreenActivity.this), SplashScreenActivity.this);
            appOpenAd.loadAd();
            Log.e("AppopenStart--)", "--)" + appOpenAd.getAdUnitId());
            appOpenAd.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {

                    if (appOpenAd.isReady()) {
                        appOpenAd.showAd(Utility.getLovinAppopen(SplashScreenActivity.this));
                    } else {
                        appOpenAd.loadAd();
                    }
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    MyApplication.initAppOpenAdAgain();
                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {

                    Log.e("Appopen--)", "Failed--)" + error);
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    Log.e("Appopen--)", "FailedDisplay--)" + error);

                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
    }


    public boolean checkPermission() {
        return NotificationManagerCompat.from(SplashScreenActivity.this).areNotificationsEnabled();
    }
}