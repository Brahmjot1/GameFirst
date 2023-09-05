package com.player.cricketfirst.util;

import static com.player.cricketfirst.activity.SplashScreenActivity.responseMain;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.player.cricketfirst.R;
import com.player.cricketfirst.activity.MatchDetailsActivity;
import com.player.cricketfirst.activity.WebActivity;
import com.player.cricketfirst.model.CategoryModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import rubikstudio.library.LuckyWheelView;
import rubikstudio.library.model.LuckyItem;


public class Utility {

    ProgressDialog dialogwait;
    public static final String DATE_FORMAT_STANDARDIZED_UTC = "yyyy-MM-dd";
    UrlController urlControl;
    MaxAd nativeAd, nativeAdExit;
    int timerCountSpin;
    private static InterstitialAd mInterstitialAd;
    public void showWaitDialog(Activity activity) {
        dialogwait = ProgressDialog.show(activity, "", "Ad loading. Please wait...", true);
        dialogwait.show();
    }

    public void dismissWaitDialog() {
        if (dialogwait != null) {
            dialogwait.dismiss();
        }
    }

    public static void putIDString(Context activity, String key, String val) {
        SharedPreferences pref = activity.getSharedPreferences("SCRATCHID", Context.MODE_PRIVATE);
        pref.edit().putString(key, val).apply();
    }

    public static String getIDString(Context activity, String key) {
        SharedPreferences pref = activity.getSharedPreferences("SCRATCHID", Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public static void putSpinIDString(Context activity, String key, String val) {
        SharedPreferences pref = activity.getSharedPreferences("SPINID", Context.MODE_PRIVATE);
        pref.edit().putString(key, val).apply();
    }

    public static String getSpinIDString(Context activity, String key) {
        SharedPreferences pref = activity.getSharedPreferences("SPINID", Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat(DATE_FORMAT_STANDARDIZED_UTC).format(new Date());
    }

    public static boolean checkInternetConnection(Activity context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null && cm.getActiveNetworkInfo() != null) {
                return cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected();
            }
        }
        return false;
    }

    public void showLovinNative(Activity activity, String nativeID, FrameLayout frameLayout) {
        if (nativeID != null) {
            MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(nativeID, activity);
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                    if (nativeAd != null) {
                        nativeAdLoader.destroy(nativeAd);
                    }
                    nativeAd = ad;
                    frameLayout.removeAllViews();
                    frameLayout.addView(nativeAdView);
                    frameLayout.setVisibility(View.VISIBLE);
                    Log.e("NativeLoad--)", "" + ad);
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    Log.e("NativeFailed--)", "" + error);
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {

                }
            });


            nativeAdLoader.loadAd();
        }

    }

    public void showGoogleNative(Activity activity,FrameLayout frameLayout)
    {
        AdLoader.Builder builder = new AdLoader.Builder(activity, Utility.getGoogleNative(activity));

        builder.forNativeAd(unifiedNativeAd -> {
            populateUnifiedNativeAdView(activity, frameLayout, unifiedNativeAd);

        }).withAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        com.google.android.gms.ads.nativead.NativeAdOptions adOptions = new com.google.android.gms.ads.nativead.NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void populateUnifiedNativeAdView(Context context, FrameLayout frameLayout, NativeAd nativeAd) {
        LayoutInflater inflater = LayoutInflater.from(context);
        NativeAdView adView = (NativeAdView) inflater.inflate(R.layout.layout_big_native_ad_mob1, null);
        if (frameLayout != null) {
            frameLayout.removeAllViews();
            frameLayout.addView(adView);
            frameLayout.setVisibility(View.VISIBLE);
        }
        try {
            com.google.android.gms.ads.nativead.MediaView mediaView = adView.findViewById(R.id.mediaView);
            mediaView.setMediaContent(nativeAd.getMediaContent());
            adView.setMediaView(mediaView);

            adView.setHeadlineView(adView.findViewById(R.id.adTitle));
            adView.setCallToActionView(adView.findViewById(R.id.callToAction));
            adView.setBodyView(adView.findViewById(R.id.adDescription));
            adView.setIconView(adView.findViewById(R.id.adIcon));
            adView.setAdvertiserView(adView.findViewById(R.id.adAttribute));


            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

            if (nativeAd.getBody() == null) {
                adView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                adView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            }

            if (nativeAd.getCallToAction() == null && adView.getCallToActionView() != null) {
                adView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else if (adView.getCallToActionView() != null) {
                adView.getCallToActionView().setVisibility(View.VISIBLE);
                ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
            }


            if (nativeAd.getIcon() == null) {
                adView.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }
            adView.getMediaView().setVisibility(View.VISIBLE);
            adView.setNativeAd(nativeAd);
            VideoController vc = nativeAd.getMediaContent().getVideoController();
            vc.mute(true);
            if (vc.hasVideoContent()) {
                vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                    @Override
                    public void onVideoEnd() {
                        super.onVideoEnd();
                    }
                });
            }


            adView.setNativeAd(nativeAd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadInter(Activity activity)
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(activity,Utility.getGoogleInter(activity), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        Log.e("Inter--)","isLoaded");
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.e("InterloadAdError--)"," "+loadAdError);

                        mInterstitialAd = null;
                    }
                });
    }

    public void showLovinNativeExit(Activity activity, String nativeID, FrameLayout frameLayout) {
        if (nativeID != null) {
            MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(nativeID, activity);
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                    if (nativeAdExit != null) {
                        nativeAdLoader.destroy(nativeAdExit);
                    }
                    nativeAdExit = ad;
                    frameLayout.removeAllViews();
                    frameLayout.addView(nativeAdView);
                    frameLayout.setVisibility(View.VISIBLE);
                    Log.e("NativeLoad--)", "" + ad);
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    Log.e("NativeFailed--)", "" + error);
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {

                }
            });

            nativeAdLoader.loadAd();
        }
    }

    public void setCustomAds(Activity activity, CategoryModel categoryModel, FrameLayout relView) {
        if (categoryModel != null) {
            View viewCustom = activity.getLayoutInflater().inflate(R.layout.layout_custom_banner, null);
            RelativeLayout relPopupTop = viewCustom.findViewById(R.id.relPopupTop);
            LottieAnimationView custLottieView = viewCustom.findViewById(R.id.custLottieView);
            ImageView custBannerTop = viewCustom.findViewById(R.id.custBannerTop);
            if (categoryModel.getImage().contains(".json")) {
                custBannerTop.setVisibility(View.GONE);
                custLottieView.setVisibility(View.VISIBLE);
                custLottieView.setAnimationFromUrl(categoryModel.getImage());
                custLottieView.setRepeatCount(LottieDrawable.INFINITE);
            } else {
                custBannerTop.setVisibility(View.VISIBLE);
                custLottieView.setVisibility(View.GONE);
                Glide.with(activity).load(categoryModel.getImage()).into(custBannerTop);


            }
            relView.addView(viewCustom);
            relPopupTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Redirect(activity, categoryModel);
                }
            });
        }

    }

    public static String changeDateFormat(String currentFormat, String requiredFormat, String dateString) {
        String result = "";
        if (isStringNullOrEmpty(dateString)) {
            return result;
        }
        SimpleDateFormat formatterOld = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat formatterNew = new SimpleDateFormat(requiredFormat, Locale.getDefault());
        Date date = null;
        try {
            date = formatterOld.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            result = formatterNew.format(date);
        }
        return result;
    }

    public static void setFCMRegId(Context activity, String regId) {
        SharedPreferences pref = activity.getSharedPreferences("DEVICETOKEN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (regId != null) {
            editor.putString("FCMregId", regId);
        } else {
            editor.putString("FCMregId", "");
        }
        editor.apply();
    }

    public static void setNotificationData(Context activity, String NDATA) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(activity).edit();
        editor.putString("NDATA", NDATA);
        editor.commit();
    }

    public static boolean AppInstalledOrNot(Activity activity, String uri) {
        PackageManager pm = activity.getPackageManager();
        try {
            pm.getPackageInfo(uri, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static CategoryModel getNotificationData(Context activity) {
        try {
            String data = PreferenceManager.getDefaultSharedPreferences(activity).getString("NDATA", "");
            return new Gson().fromJson(data, CategoryModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void LoadBannerAd(Activity activity, LinearLayout banner, CategoryModel model) {
        banner.setVisibility(View.VISIBLE);
        View layout2 = LayoutInflater.from(activity).inflate(R.layout.ad_buttome, banner, false);
        ImageView imgTopBanner = (ImageView) layout2.findViewById(R.id.imgTopBanner);

        if (model != null) {

            Log.e("getImage--)", "" + model.getImage());
            if (!Utility.isStringNullOrEmpty(model.getImage())) {
                imgTopBanner.setVisibility(View.VISIBLE);
                if (model.getImage().contains(".gif")) {
                    Glide.with(activity).load(model.getImage()).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).into(imgTopBanner);
                } else {
                    Picasso.get().load(model.getImage()).into(imgTopBanner);
                }
                imgTopBanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!Utility.isStringNullOrEmpty(model.getUrl())) {
                            Utility.openUrl(activity, model.getUrl());
                        }

                    }
                });
            } else {
                imgTopBanner.setVisibility(View.GONE);
            }
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        banner.addView(layout2, params);
        banner.setBackgroundColor(Color.WHITE);
    }

    public static void setIsFromnotification(Activity activity, boolean value) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("IsFromnotification", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean("IsFromnotification", value);
        prefsEditor.commit();
    }

    public static void setIDArray(Activity activity, ArrayList<String> IDArray) {
        SharedPreferences pref = activity.getSharedPreferences("dialogIDArray", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(IDArray);
        editor.putString("dialogIDArray", json);
        editor.apply();
    }

    public static ArrayList<String> getIDArray(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("dialogIDArray", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("dialogIDArray", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> arrayList = gson.fromJson(json, type);
        return arrayList;
    }

    public static void cleanDialogID(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("dialogIDArray", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static boolean verifyInstallerId(Activity context) {
        // A list with valid installers package name
        List<String> validInstallers = new ArrayList<>(Arrays.asList("com.android.vending", "com.google.android.feedback"));

        // The package name of the app that has installed your app
        final String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());

        // true if your app has been downloaded from Play Store
        return installer != null && validInstallers.contains(installer);
    }

    public void showImageLotteGIF(Activity mActivity, String imgURL, ImageView ivBanner, ImageView ivGIF, ProgressBar progressBar) {
        if (imgURL != null && imgURL.length() > 0) {

            if (imgURL.contains(".gif")) {
                ivGIF.setVisibility(View.VISIBLE);
                ivBanner.setVisibility(View.GONE);
                Glide.with(mActivity).load(imgURL).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        return false;
                    }
                }).into(ivGIF);
            } else {

                ivGIF.setVisibility(View.GONE);
                ivBanner.setVisibility(View.VISIBLE);
                Glide.with(mActivity).load(imgURL).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        return false;
                    }
                }).into(ivBanner);
            }

        }


    }

    public static void ShomeHomePopup(Activity activity, CategoryModel popData) {
        if (activity != null) {
            final Dialog dialog1 = new Dialog(activity);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog1.setContentView(R.layout.dialogue_homepopup);
            dialog1.setCancelable(false);
            Button btnOk = dialog1.findViewById(R.id.btnSubmit);
            TextView txtTitle = dialog1.findViewById(R.id.txtTitle);
            TextView btnCancel = dialog1.findViewById(R.id.btnCancel);
            ProgressBar probrBanner = dialog1.findViewById(R.id.probrBanner);
            ImageView imgBanner = dialog1.findViewById(R.id.imgBanner);
            txtTitle.setText(popData.getTitle());
            TextView txtMessage = dialog1.findViewById(R.id.txtMessage);
            txtMessage.setText(popData.getDescription());
            if (!isStringNullOrEmpty(popData.getIsForce()) && popData.getIsForce().equals("1")) {
                btnCancel.setVisibility(View.GONE);
            } else {
                btnCancel.setVisibility(View.VISIBLE);
            }

            if (!isStringNullOrEmpty(popData.getBtnName())) {
                btnOk.setText(popData.getBtnName());
            }
            if (!Utility.isStringNullOrEmpty(popData.getImage())) {
                if (popData.getImage().contains(".gif")) {
                    imgBanner.setVisibility(View.VISIBLE);
                    probrBanner.setVisibility(View.GONE);
                    Glide.with(activity).load(popData.getImage()).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).into(imgBanner);
                } else {
                    probrBanner.setVisibility(View.VISIBLE);
                    imgBanner.setVisibility(View.VISIBLE);
                    Picasso.get().load(popData.getImage()).into(imgBanner, new Callback() {
                        @Override
                        public void onSuccess() {
                            probrBanner.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            probrBanner.setVisibility(View.GONE);
                        }
                    });
                }

            } else {
                imgBanner.setVisibility(View.GONE);
                probrBanner.setVisibility(View.GONE);
            }
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!activity.isFinishing()) {
                        dialog1.dismiss();
                    }
                }
            });

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!activity.isFinishing()) {
                        dialog1.dismiss();
                    }
                    Utility.Redirect(activity, popData);
                }
            });

            Display display = activity.getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            WindowManager.LayoutParams lp;
            lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog1.getWindow().getAttributes());
            lp.width = (int) (width);
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog1.getWindow().setAttributes(lp);
            if (!activity.isFinishing() && !dialog1.isShowing()) {
                dialog1.show();
            }
        }
    }

    public static void setDate(Activity activity, String CurrDate) {
        SharedPreferences pref = activity.getSharedPreferences("CurrDate", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("CurrDate", CurrDate);
        editor.apply();
    }

    public static String getDate(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("CurrDate", Context.MODE_PRIVATE);
        return pref.getString("CurrDate", "0");
    }

    public static void setTodayOpen(Activity activity, int spin) {
        SharedPreferences pref = activity.getSharedPreferences("todayOpen", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("todayOpen", spin);
        editor.apply();
    }

    public static int getTodayOpen(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("todayOpen", Context.MODE_PRIVATE);
        return pref.getInt("todayOpen", 0);
    }

    public static void setTotalOpen(Activity activity, int spin) {
        SharedPreferences pref = activity.getSharedPreferences("TotalOpen", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("TotalOpen", spin);
        editor.apply();
    }

    public static int getTotalOpen(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("TotalOpen", Context.MODE_PRIVATE);
        return pref.getInt("TotalOpen", 1);
    }

    public static void setHomePopID(Activity activity, String regId) {
        SharedPreferences pref = activity.getSharedPreferences("HomePopID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("HomePopID", regId);
        editor.apply();
    }


    public static String getHomePopID(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("HomePopID", Context.MODE_PRIVATE);
        return pref.getString("HomePopID", "");
    }


    public static void setIsLovinAdShow(Activity activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("isLovinAd", id);
        editor.apply();
    }

    public static String getIsLovinAdShow(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("isLovinAd", "0");
    }

    public static void setIsGoogleAppOpen(Activity activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("isGoogleAppOpen", id);
        editor.apply();
    }

    public static void setIsGoogleAppOpenContext(Context activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("isGoogleAppOpen", id);
        editor.apply();
    }

    public static String getIsGoogleAppOpen(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("isGoogleAppOpen", "0");
    }

    public static String getIsGoogleAppOpenContext(Context activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("isGoogleAppOpen", "0");
    }

    public static void setGoogleAppOpen(Activity activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("gAppopen", id);
        editor.apply();
    }
    public static String getGoogleAppOpen(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("gAppopen", "0");
    }

    public static void setLovinBanner(Activity activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lovinBanner", id);
        editor.apply();
    }

    public static String getLovinBanner(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("lovinBanner", "");
    }

    public static void setGoogleBanner(Activity activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("GoogleBanner", id);
        editor.apply();
    }

    public static String getGoogleBanner(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("GoogleBanner", "");
    }

    public static void setLovinNative(Activity activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lovinNative", id);
        editor.apply();
    }

    public static String getLovinNative(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("lovinNative", "");
    }

    public static void setGoogleNative(Activity activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("GoogleNative", id);
        editor.apply();
    }

    public static String getGoogleNative(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("GoogleNative", "");
    }

    public static void setLovinReward(Activity activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lovinReward", id);
        editor.apply();
    }

    public static String getLovinReward(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("lovinReward", "");
    }

    public static void setLovinInter(Activity activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lovinInter", id);
        editor.apply();
    }

    public static String getLovinInter(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("lovinInter", "");
    }

    public static void setGoogleInter(Activity activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("GoogleInter", id);
        editor.apply();
    }

    public static String getGoogleInter(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("GoogleInter", "");
    }

    public static void setLovinAppopen(Activity activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lovinAppopen", id);
        editor.apply();
    }

    public static String getLovinAppopen(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("lovinAppopen", "");
    }

    public static void setLoviOpenIDContext(Context activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lovinAppopenCon", id);
        editor.apply();
    }

    public static String getLoviOpenIDContext(Context activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("lovinAppopenCon", "");
    }

    public static void setGOpenIDContext(Context activity, String id) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("GAppopenCon", id);
        editor.apply();
    }

    public static String getGOpenIDContext(Context activity) {
        SharedPreferences pref = activity.getSharedPreferences("ADS", Context.MODE_PRIVATE);
        return pref.getString("GAppopenCon", "");
    }

    public static void setReferData(Activity activity, String regId) {
        SharedPreferences pref = activity.getSharedPreferences("ReferData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ReferData", regId);
        editor.apply();
    }


    public static String getReferData(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("ReferData", Context.MODE_PRIVATE);
        return pref.getString("ReferData", "");
    }

    public static void setAdID(Activity activity, String regId) {
        SharedPreferences pref = activity.getSharedPreferences("AdID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("AdID", regId);
        editor.apply();
    }


    public static String getAdID(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("AdID", Context.MODE_PRIVATE);
        return pref.getString("AdID", "");
    }


    public static boolean getIsFromnotification(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("IsFromnotification", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean("IsFromnotification", false);
        }
        return false;
    }

    public static String getFCMRegId(Context activity) {
        String regId = "";
        if (activity != null) {
            SharedPreferences pref = activity.getSharedPreferences("DEVICETOKEN", Context.MODE_PRIVATE);
            if (pref != null) {
                regId = pref.getString("FCMregId", "");
            }
        }
        return regId;
    }

    public static void setAppVersion(Context activity, String regId) {
        SharedPreferences pref = activity.getSharedPreferences("AppVersion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("AppVersion", regId);
        editor.apply();
    }

    public static String getAppVersion(Context activity) {
        String regId = "";
        if (activity != null) {
            SharedPreferences pref = activity.getSharedPreferences("AppVersion", Context.MODE_PRIVATE);
            if (pref != null) {
                regId = pref.getString("AppVersion", "");
            }
        }
        return regId;
    }

    public static void setAsync(Context activity, String Key, String regId) {
        SharedPreferences pref = activity.getSharedPreferences(Key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Key, regId);
        editor.apply();
    }

    public static boolean isStringNullOrEmpty(String text) {
        return (text == null || text.trim().equals("null") || text.trim().length() <= 0);
    }


    public static String getAsync(Context activity, String Key) {
        String regId = "";
        if (activity != null) {
            SharedPreferences pref = activity.getSharedPreferences(Key, Context.MODE_PRIVATE);
            if (pref != null) {
                regId = pref.getString(Key, "");
            }
        }
        return regId;
    }

    public static void setEnableDiseble(final Activity activity, final View v) {
        v.setEnabled(false);
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        v.setEnabled(true);
                    }
                });
            }
        }, 2000);
    }

    public static String updateTimeRemaining(long timeDiff) {
        if (timeDiff > 0) {
            int seconds = (int) (timeDiff / 1000) % 60;
            int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
            int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
            int days = (int) (timeDiff / (1000 * 60 * 60 * 24));
            if (days > 0) {
                return String.format(Locale.getDefault(), "%02d days left", days);
            } else {
                return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours + (days * 24), minutes, seconds);
            }
        } else {
            return "Time's up!!";
        }
    }

    public static String MinitTimeRemaining(long timeDiff) {
        if (timeDiff > 0) {
            int seconds = (int) (timeDiff / 1000) % 60;
            int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        } else {
            return "Time's up!!";
        }
    }


    public static long convertTimeInMillis(String dateTimeFormat, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date mDate = sdf.parse(date);
            return mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void Notify(final Activity activity, String title, String message) {
        if (activity != null) {
            final Dialog dialog1 = new Dialog(activity);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog1.setContentView(R.layout.dialogue_notify);
            dialog1.setCancelable(false);
            Button btnOk = dialog1.findViewById(R.id.btnSubmit);
            TextView txtTitle = dialog1.findViewById(R.id.txtTitle);
            txtTitle.setText(title);
            TextView txtMessage = dialog1.findViewById(R.id.txtMessage);
            txtMessage.setText(message);
            btnOk.setOnClickListener(v -> {
                if (!activity.isFinishing()) {
                    dialog1.dismiss();
                }
            });
            Display display = activity.getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            WindowManager.LayoutParams lp;
            lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog1.getWindow().getAttributes());
            lp.width = (int) (width - (width * 0.07));
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog1.getWindow().setAttributes(lp);
            if (!activity.isFinishing()) {
                dialog1.show();
            }
        }
    }

    public static void UpdateApp(final Activity activity, String isForupdate, final String appurl, String msg) {
        if (activity != null) {
            final Dialog dialog1 = new Dialog(activity);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog1.setContentView(R.layout.dialogue_updateapp);
            Button btnUpdate = dialog1.findViewById(R.id.btnUpdate);
            Button btnCancel = dialog1.findViewById(R.id.btnCancel);
            TextView txtMessage = dialog1.findViewById(R.id.txtMessage);
            txtMessage.setText(msg);
            View viewDivider = dialog1.findViewById(R.id.viewDivider);
            if (isForupdate.equals("1")) {
                dialog1.setCancelable(false);
                btnUpdate.setVisibility(View.VISIBLE);
                viewDivider.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                btnUpdate.setBackgroundResource(R.drawable.btn_bg_diloage);
            } else {
                dialog1.setCancelable(true);
                btnUpdate.setVisibility(View.VISIBLE);
                viewDivider.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
            }
            btnUpdate.setOnClickListener(v -> {
                if (!activity.isFinishing() && !isForupdate.equals("1")) {
                    dialog1.dismiss();
                }
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appurl));
                    activity.startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity, "No application can handle this request." + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            });
            btnCancel.setOnClickListener(view -> {
                if (!activity.isFinishing()) {
                    dialog1.dismiss();
                }
            });
            Display display = activity.getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            int hight = display.getHeight();
            WindowManager.LayoutParams lp;
            lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog1.getWindow().getAttributes());
            lp.width = (int) (width - (width * 0.07));
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog1.getWindow().setAttributes(lp);
            if (!activity.isFinishing()) {
                dialog1.show();
            }
        }
    }

    public static void NotifyFinish(final Activity activity, String title, String message) {
        if (activity != null) {
            final Dialog dialog1 = new Dialog(activity);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog1.setContentView(R.layout.dialogue_notify);
            dialog1.setCancelable(false);
            Button btnOk = dialog1.findViewById(R.id.btnSubmit);
            TextView txtTitle = dialog1.findViewById(R.id.txtTitle);
            txtTitle.setText(title);
            TextView txtMessage = dialog1.findViewById(R.id.txtMessage);
            txtMessage.setText(message);
            btnOk.setOnClickListener(v -> {
                if (!activity.isFinishing()) {
                    dialog1.dismiss();
                    activity.finish();
                }
            });
            Display display = activity.getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            WindowManager.LayoutParams lp;
            lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog1.getWindow().getAttributes());
            lp.width = (int) (width - (width * 0.07));
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog1.getWindow().setAttributes(lp);
            if (!activity.isFinishing()) {
                dialog1.show();
            }
        }
    }

    public static void Redirect(Activity context, CategoryModel model) {
        if (model.getScreenNo() != null && model.getScreenNo().length() > 0) {
            switch (model.getScreenNo()) {
                case "2":
                    Intent in1 = new Intent(context, MatchDetailsActivity.class);
                    in1.putExtra("matchID", model.getMatchId());
                    in1.putExtra("team1Name", "");
                    in1.putExtra("team2Name", "");
                    context.startActivity(in1);
                    break;
                case "3":
                    Intent in = new Intent(context, WebActivity.class);
                    in.putExtra("title", model.getTitle());
                    in.putExtra("url", model.getUrl());
                    context.startActivity(in);
                    break;
                case "4":
                    openUrl(context, model.getUrl());

                    break;
            }
        }
    }

    public static void openUrl(Context c, String url) {
        if (!isStringNullOrEmpty(url)) {
            if (url.contains("play.google.com/store/apps/details") || url.contains("market.android.com/details")) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    c.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    openUrlInChrome(c, url);
                }
            } else {
                openUrlInChrome(c, url);
            }
        }
    }

    private static void openUrlInChrome(Context c, String url) {
        if (!isStringNullOrEmpty(url)) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            try {
                intent.setPackage("com.android.chrome");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    intent.setPackage(null);
                    c.startActivity(intent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void showSpinClaim(Activity activity) {
        Dialog dialogSpin = new Dialog(activity, R.style.UploadDialog);
        dialogSpin.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSpin.setCancelable(false);
        dialogSpin.setCanceledOnTouchOutside(false);
        dialogSpin.setContentView(R.layout.dialog_winning_spin);

        LottieAnimationView ltCelebrate = dialogSpin.findViewById(R.id.ltCelebrate);
        LottieAnimationView ltCelebrateFire = dialogSpin.findViewById(R.id.ltCelebrateFire);
        TextView tvTitle = dialogSpin.findViewById(R.id.tvTitle);
        TextView tvDescription = dialogSpin.findViewById(R.id.tvDescription);
        TextView btnClaim = dialogSpin.findViewById(R.id.btnClaim);
        TextView tvTimerText = dialogSpin.findViewById(R.id.tvTimerText);
        CardView cardWin = dialogSpin.findViewById(R.id.cardWin);
        ImageView ivClose = dialogSpin.findViewById(R.id.ivClose);
        ImageView ivLogo = dialogSpin.findViewById(R.id.ivLogo);

        if (responseMain != null) {
            tvTitle.setText(responseMain.getSpinData().getTitle());
            tvDescription.setText(responseMain.getSpinData().getDescription());
            btnClaim.setText(responseMain.getSpinData().getBtnName());
            btnClaim.setTextColor(Color.parseColor(responseMain.getSpinData().getTextColor()));

            Drawable mDrawable = ContextCompat.getDrawable(activity, R.drawable.bg_claim_bonus);
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(responseMain.getSpinData().getBtnColor()), PorterDuff.Mode.SRC_IN));
            btnClaim.setBackground(mDrawable);
            timerCountSpin = Integer.parseInt(responseMain.getSpinData().getTimer());


            if (!Utility.isStringNullOrEmpty(responseMain.getSpinData().getLogo())) {
                Glide.with(activity).load(responseMain.getSpinData().getLogo()).into(ivLogo);
            }
            ltCelebrateFire.setVisibility(View.VISIBLE);
            ltCelebrateFire.playAnimation();

            if (responseMain.getSpinData().getBgColor() != null) {
                cardWin.setCardBackgroundColor(Color.parseColor(responseMain.getSpinData().getBgColor()));
            }

            PropertyValuesHolder scalex = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f);
            PropertyValuesHolder scaley = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.8f);
            ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(btnClaim, scalex, scaley);
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.setRepeatMode(ValueAnimator.REVERSE);
            anim.setDuration(400);
            anim.start();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    new CountDownTimer((timerCountSpin * 1000), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Display Data by Every Second
                            timerCountSpin = timerCountSpin - 1;
                            tvTimerText.setText("" + timerCountSpin);
                        }

                        @Override
                        public void onFinish() {
                            tvTimerText.setVisibility(View.INVISIBLE);
                            ivClose.setVisibility(View.VISIBLE);
                        }
                    }.start();
                }
            }, 1500);

        }

        btnClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialogSpin != null && dialogSpin.isShowing()) {
                    dialogSpin.dismiss();
                }
                if (!isStringNullOrEmpty(responseMain.getSpinData().getUrl())) {
                    Utility.openUrl(activity, responseMain.getSpinData().getUrl());
                }

            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSpin != null && dialogSpin.isShowing()) {
                    dialogSpin.dismiss();
                }
            }
        });

        if (dialogSpin != null) {
            dialogSpin.show();
        }
    }

    public void showSpinDialog(Activity activity) {
        urlControl = new UrlController(activity);
        urlControl.showAdLoader();
        Dialog dialogSpin = new Dialog(activity, R.style.UploadDialog);
        dialogSpin.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSpin.setCancelable(false);
        dialogSpin.setCanceledOnTouchOutside(false);
        dialogSpin.setContentView(R.layout.dialog_spin);

        ImageView ivCloseSpin = dialogSpin.findViewById(R.id.ivCloseSpin);
        ImageView ivSpinNow = dialogSpin.findViewById(R.id.btnSpin);
        LinearLayout relMiniAds = dialogSpin.findViewById(R.id.relMiniAds);
        LuckyWheelView luckyWheel_spinner = dialogSpin.findViewById(R.id.luckyWheel_spinner);
        PropertyValuesHolder scalex = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f);
        PropertyValuesHolder scaley = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.8f);
        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(ivSpinNow, scalex, scaley);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setDuration(400);
        List<LuckyItem> spinnerLists = new ArrayList<>();
        if (responseMain != null) {
            if (responseMain.getSpinData() != null) {
                ArrayList<Bitmap> bitmap = new ArrayList<>();
                for (int ii = 0; ii < responseMain.getSpinData().getBlock().size(); ii++) {
                    if (responseMain.getSpinData().getBlock().get(ii).getIcon() != null && !isStringNullOrEmpty(responseMain.getSpinData().getBlock().get(ii).getIcon())) {
                        Glide.with(activity).asBitmap().load(responseMain.getSpinData().getBlock().get(ii).getIcon()).into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                bitmap.add(resource);

                                if (bitmap.size() == responseMain.getSpinData().getBlock().size()) {
                                    for (int i = 0; i < responseMain.getSpinData().getBlock().size(); i++) {
                                        LuckyItem luckyItem1 = new LuckyItem();
                                        luckyItem1.text = responseMain.getSpinData().getBlock().get(i).getBlock_points();
                                        luckyItem1.color = Color.parseColor("#" + responseMain.getSpinData().getBlock().get(i).getBlock_bg());
                                        luckyItem1.id = responseMain.getSpinData().getBlock().get(i).getBlock_id();
                                        luckyItem1.icon = R.drawable.coins;
                                        luckyItem1.bitmap = bitmap.get(i);
                                        spinnerLists.add(luckyItem1);
                                        luckyWheel_spinner.setData(spinnerLists);
                                        luckyWheel_spinner.setRound(6);
                                        luckyWheel_spinner.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
                                            @Override
                                            public void LuckyRoundItemSelected(int index) {
                                                Handler handler1 = new Handler();
                                                handler1.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (dialogSpin != null && dialogSpin.isShowing()) {
                                                            dialogSpin.dismiss();
                                                        }
                                                        showSpinClaim(activity);
                                                    }
                                                }, 300);
                                            }
                                        });
                                        urlControl.dismissADLoader();
                                        anim.start();
                                        if (dialogSpin != null) {
                                            dialogSpin.show();
                                        }
                                    }
                                }

                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
                    } else {
                        bitmap.add(null);
                    }

                }

            } else {
                urlControl.dismissADLoader();
            }

        } else {
            urlControl.dismissADLoader();
        }


        ivCloseSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSpin != null && dialogSpin.isShowing()) {
                    dialogSpin.dismiss();
                }
            }
        });

        ivSpinNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim.pause();

                Utility.putSpinIDString(activity, responseMain.getSpinData().getId(), Utility.getCurrentDate());

                int indexStop = Integer.parseInt(responseMain.getSpinData().getStopPos());
                luckyWheel_spinner.startLuckyWheelWithTargetIndex(indexStop);
            }
        });
    }

    public void showGoogleInter(Activity activity) {

        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        } else {
            Log.e("TAG", "The interstitial ad wasn't ready yet.");
        }

        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    Log.e("TAG", "Ad was clicked.");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.e("TAG", "Ad dismissed fullscreen content.");
                    mInterstitialAd = null;
                    loadInter(activity);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Log.e("TAG", "Ad failed to show fullscreen content.");
                    mInterstitialAd = null;
                }

                @Override
                public void onAdImpression() {
                    Log.e("TAG", "Ad recorded an impression.");
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.e("TAG", "Ad showed fullscreen content.");
                }
            });
        }


    }

    public void showLovinInter(Activity activity, String adFaileUrl) {
        urlControl = new UrlController(activity);
        urlControl.showAdLoader();
        String lovinID = Utility.getLovinInter(activity);
        MaxInterstitialAd interstitialAd = new MaxInterstitialAd(lovinID, activity);
        interstitialAd.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                urlControl.dismissADLoader();
                Log.e("lovinInter--)", " " + ad.getAdUnitId());
                if (interstitialAd.isReady()) {
                    interstitialAd.showAd();
                }
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.e("lovinInterError--)", " " + error);
                urlControl.dismissADLoader();
                openUrl(activity, adFaileUrl);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                Log.e("lovinInterError1--)", " " + error);
            }
        });
        interstitialAd.loadAd();

    }

    public void showLovinInterDismiss(Activity activity, AdEventListener adEventListener) {
        urlControl = new UrlController(activity);
        urlControl.showAdLoader();
        String lovinID = Utility.getLovinInter(activity);
        MaxInterstitialAd interstitialAd = new MaxInterstitialAd(lovinID, activity);
        interstitialAd.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                urlControl.dismissADLoader();
                Log.e("lovinInter--)", " " + ad.getAdUnitId());
                if (interstitialAd.isReady()) {
                    interstitialAd.showAd();
                }
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {
                adEventListener.onAdClosed();
            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.e("lovinInterError--)", " " + error);
                urlControl.dismissADLoader();
                adEventListener.onAdClosed();
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                adEventListener.onAdClosed();
                Log.e("lovinInterError1--)", " " + error);
            }
        });
        interstitialAd.loadAd();

    }

    public int getRandomNumber() {
        int random = new Random().nextInt((3 - 1) + 1) + 1;
        return random;
    }

    public void showLovinBanner(Activity activity, LinearLayout frameLayout) {
        if (frameLayout != null) {
            String lovinID = Utility.getLovinBanner(activity);
            MaxAdView adView = new MaxAdView(lovinID, MaxAdFormat.BANNER, activity);
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int heightPx = 150;
            adView.setLayoutParams(new ViewGroup.LayoutParams(width, heightPx));
            frameLayout.addView(adView);
            adView.loadAd();
        }
    }

    public void showGoogleBanner(Activity activity, LinearLayout banner) {
        if (banner != null) {
            banner.setVisibility(View.VISIBLE);
            AdView adView = new AdView(activity);
            adView.setAdUnitId(Utility.getGoogleBanner(activity));
            banner.addView(adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = getAdSize(activity);
            adView.setAdSize(adSize);
            adView.loadAd(adRequest);
        }
    }


    private AdSize getAdSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }
}
