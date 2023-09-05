package com.player.cricketfirst;

import static androidx.lifecycle.Lifecycle.Event.ON_START;
import static com.player.cricketfirst.activity.SplashScreenActivity.isLoadSplash;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.applovin.sdk.AppLovinSdk;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.player.cricketfirst.util.Utility;
import com.onesignal.OneSignal;

import org.jetbrains.annotations.NotNull;


public class MyApplication extends Application implements LifecycleObserver, Application.ActivityLifecycleCallbacks {

    public static final String ONESIGNAL_APP_ID = "56783a0d-9ab1-4cfa-8247-40d16177b4a5";
    static ExampleAppOpenManager appOpenManager;
    private static MyApplication mInstance;
    private Activity currentActivity;
    private AppOpenAd appOpenAd = null;
    private static Context context;
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        if (Utility.getIsGoogleAppOpenContext(context).matches("1"))
        {
            registerActivityLifecycleCallbacks(this);
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        }
    }

    public static void initAppOpenAdAgain() {
        appOpenManager = new ExampleAppOpenManager(context);
    }

    public static class ExampleAppOpenManager implements LifecycleObserver, MaxAdListener {
        MaxAppOpenAd appOpenAd;
        Context context;

        public ExampleAppOpenManager(Context context) {
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
            this.context = context;
            appOpenAd = new MaxAppOpenAd(Utility.getLoviOpenIDContext(context), context);
            appOpenAd.setListener(this);
            appOpenAd.loadAd();

        }

        private void showAdIfReady() {
            if (isShowingAd ||appOpenAd == null || !AppLovinSdk.getInstance(context).isInitialized()) return;

            if (isLoadSplash)
            {
                if (appOpenAd.isReady()) {
                    isShowingAd = true;
                    appOpenAd.showAd(Utility.getLoviOpenIDContext(context));
                } else {
                    appOpenAd.loadAd();
                }
            }

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
            showAdIfReady();
        }

        @Override
        public void onAdLoaded(final MaxAd ad) {
        }

        @Override
        public void onAdLoadFailed(final String adUnitId, final MaxError error) {
            isShowingAd = false;
        }

        @Override
        public void onAdDisplayed(final MaxAd ad) {
        }

        @Override
        public void onAdClicked(final MaxAd ad) {
        }

        @Override
        public void onAdHidden(final MaxAd ad) {
            appOpenAd.loadAd();
            isShowingAd = false;
        }

        @Override
        public void onAdDisplayFailed(final MaxAd ad, final MaxError error) {
            appOpenAd.loadAd();
            isShowingAd = false;
        }
    }

    private static boolean isShowingAd = false;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void showAdIfAvailable() {
            if (isAdAvailable()) {
                FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        appOpenAd = null;
//                        isShowingAd = false;
                        fetchAd();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NotNull AdError adError) {
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                    }
                };

                appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                appOpenAd.show(currentActivity);

            } else {
                fetchAd();
            }
    }

    public void fetchAd() {
        if (isAdAvailable()) {
            return;
        }

        AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NotNull AppOpenAd ad) {
                appOpenAd = ad;
            }

            @Override
            public void onAdFailedToLoad(@NotNull LoadAdError loadAdError) {
            }

        };
//        Log.e("AppopenGOpen--)",""+Utils.getGgOpenContext(context));
        if (Utility.getGOpenIDContext(context) != null) {
            AdRequest request = getAdRequest();
            AppOpenAd.load(this, Utility.getGOpenIDContext(context), request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

        }

    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public boolean isAdAvailable() {
        return appOpenAd != null;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }

    @OnLifecycleEvent(ON_START)
    public void onStart() {
        showAdIfAvailable();
    }
}
