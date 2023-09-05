package fantasy.livematch.firstscore.activity;

import static fantasy.livematch.firstscore.MyApplication.getContext;
import static fantasy.livematch.firstscore.activity.SplashScreenActivity.isLoadSplash;
import static fantasy.livematch.firstscore.util.Utility.isStringNullOrEmpty;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.onesignal.OneSignal;
import fantasy.livematch.firstscore.R;
import fantasy.livematch.firstscore.adapter.ApplistAdapter;
import fantasy.livematch.firstscore.adapter.CricketHomeListAdapter;
import fantasy.livematch.firstscore.adapter.HomeStoryAdapter;
import fantasy.livematch.firstscore.adapter.MenulistAdapter;
import fantasy.livematch.firstscore.async.DownloadImageShareAsync;
import fantasy.livematch.firstscore.model.CategoryModel;
import fantasy.livematch.firstscore.model.ResponseModel;
import fantasy.livematch.firstscore.util.AdEventListener;
import fantasy.livematch.firstscore.util.Global_App;
import fantasy.livematch.firstscore.util.Utility;
import fantasy.livematch.firstscore.util.font.BoldTextView;
import fantasy.livematch.firstscore.util.font.RegularTextView;
import fantasy.livematch.firstscore.util.recyclerviewpager.PagerAdapter;
import fantasy.livematch.firstscore.util.recyclerviewpager.PagerModel;
import fantasy.livematch.firstscore.util.recyclerviewpager.RecyclerViewPager;
import fantasy.livematch.firstscore.util.storyview.StoryView;
import fantasy.livematch.firstscore.util.storyview.callback.OnStoryChangedCallback;
import fantasy.livematch.firstscore.util.storyview.callback.StoryClickListeners;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import in.myinnos.androidscratchcard.ScratchCard;

public class MainActivity extends AppCompatActivity {
    ResponseModel responseMain;
    public static ResponseModel responseHome;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    CardView cardSuggestion,cardOfferList,cardWebTop,cardWebBottom;
    private RelativeLayout loutSlider,relPrediction,relCricketNews;
    private RecyclerViewPager pagerSlider;
    private BoldTextView txtUpcoming;
    public static String homeWebUrl = "";
    CardView cardNews;
    ImageView ivOfferlist;
    private RecyclerView rcList, rvApplist;
    private LinearLayout bannerContainer;
    private ImageView menuAdBanner;
    private WebView webNote, webNoteBottom;
    private NavigationView navView;
    private LinearLayout MenuShareApp;
    private LinearLayout menuContactUs, menuMoreApps;
    private LinearLayout menuPrivacyPolicy;
    private LinearLayout menuRateApp;
    private ImageView imgStory, ivNotification, ivWebMain;
    private LinearLayout MenuAboutUs;
    Utility utility;
    private LinearLayout txtMenuFooter;
    private RegularTextView txtAppVersion;
    private CricketHomeListAdapter cricketHomeListAdapter;
    private ApplistAdapter applistAdapter;
    private MenulistAdapter menulistAdapter;
    Dialog dialogExit;
    CardView cardNative;
    String emailID, privacyPolicy;
    private LinearLayout loutInflate;
    FrameLayout adLayoutLovin;
    private boolean isScratchCardScratched = false;
    private int timerCount;
    RecyclerView rvMenulist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int nightModeFlags =
                getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {

            case Configuration.UI_MODE_NIGHT_YES:
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }
        isLoadSplash = true;
        initView();

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (responseMain.getScratchCard() != null && (Utility.getIDString(MainActivity.this, responseMain.getScratchCard().getId()).length() == 0 || !Utility.getIDString(MainActivity.this, responseMain.getScratchCard().getId()).equals(Utility.getCurrentDate()))) {
            showScratchCardDialog();
        } else {
            if (dialogExit != null) {
                if (!dialogExit.isShowing()) {
                    if (Utility.getIsLovinAdShow(MainActivity.this).matches("1")) {
                        showInterstitial();
                    } else {
                        dialogExit.show();
                    }
                }
            }
        }
    }

    public void showHomeNoteDialog(ResponseModel responseModel) {
        Dialog dialogNote = new Dialog(MainActivity.this, R.style.UploadDialog);
        dialogNote.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNote.setCancelable(false);
        dialogNote.setCanceledOnTouchOutside(false);
        dialogNote.setContentView(R.layout.dialog_home_note);

        TextView tvTitleHome = dialogNote.findViewById(R.id.tvTitleHome);
        ImageView ivCloseHome = dialogNote.findViewById(R.id.ivCloseHome);
        WebView webViewHome = dialogNote.findViewById(R.id.webViewHome);
        if (dialogNote != null) {
            dialogNote.show();
        }
        if (!isStringNullOrEmpty(responseModel.getHomeDilogeNote())) {
            webViewHome.setVisibility(View.VISIBLE);
            webViewHome.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webViewHome.getSettings().setLoadWithOverviewMode(false);
            webViewHome.setScrollContainer(true);
            webViewHome.getSettings().setJavaScriptEnabled(true);
            webViewHome.loadDataWithBaseURL(null, responseModel.getHomeDilogeNote(), "text/html", "UTF-8", null);
        }
        if (!isStringNullOrEmpty(responseModel.getHomeDilogeTitle())) {
            tvTitleHome.setText(responseModel.getHomeDilogeTitle());
        }
        ivCloseHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogNote != null && dialogNote.isShowing()) {
                    dialogNote.dismiss();
                }
                if (Utility.getIsLovinAdShow(MainActivity.this).matches("2")) {
                    utility.showLovinInter(MainActivity.this, responseModel.getAdFailUrl());
                }
                else if(Utility.getIsLovinAdShow(MainActivity.this).matches("1"))
                    {
                        utility.showGoogleInter(MainActivity.this);
                    }
            }
        });

    }

    private void showScratchCardDialog() {

        timerCount = Integer.parseInt(responseMain.getScratchCard().getTimer());
        Dialog dialogScratchCard = new Dialog(MainActivity.this, android.R.style.Theme_Black_NoTitleBar);
        ColorDrawable cd = new ColorDrawable(getColor(R.color.colorPrimaryDark));
        dialogScratchCard.getWindow().setStatusBarColor(getColor(R.color.colorPrimaryDark));
        dialogScratchCard.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        dialogScratchCard.getWindow().setBackgroundDrawable(cd);
        dialogScratchCard.setContentView(R.layout.dialog_scratch_card);
        dialogScratchCard.setCancelable(false);
        LinearLayout layoutParent = dialogScratchCard.findViewById(R.id.layoutParent);


        LinearLayout layoutContent = dialogScratchCard.findViewById(R.id.layoutContent);
        layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScratchCardScratched) {
                    if (!isStringNullOrEmpty(responseMain.getScratchCard().getUrl())) {
                        Utility.openUrl(MainActivity.this, responseMain.getScratchCard().getUrl());
                    }
                }
            }
        });
        if (!isStringNullOrEmpty(responseMain.getScratchCard().getBgColor())) {
            layoutParent.setBackgroundColor(Color.parseColor(responseMain.getScratchCard().getBgColor()));
        }
        ImageView ivClose = dialogScratchCard.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogScratchCard.dismiss();
                if (dialogExit != null) {
                    if (!dialogExit.isShowing()) {
                        if (Utility.getIsLovinAdShow(MainActivity.this).matches("1")) {
                            showInterstitial();
                        } else {
                            dialogExit.show();
                        }
                    }
                }
            }
        });

        TextView btnAction = dialogScratchCard.findViewById(R.id.btnAction);
        PropertyValuesHolder scalex = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.2f);
        PropertyValuesHolder scaley = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f);
        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(btnAction, scalex, scaley);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setDuration(200);

        ImageView ivLogo = dialogScratchCard.findViewById(R.id.ivLogo);
        Glide.with(MainActivity.this)
                .load(responseMain.getScratchCard().getLogo())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(ivLogo);

        ImageView ivBackImage = dialogScratchCard.findViewById(R.id.ivBackImage);
        Glide.with(MainActivity.this)
                .load(responseMain.getScratchCard().getBackImage())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(ivBackImage);
        ScratchCard scratchCard = dialogScratchCard.findViewById(R.id.scratchCard);
        scratchCard.setScratchWidth(getResources().getDimensionPixelSize(R.dimen.size_20dp));

        LottieAnimationView animation_view = dialogScratchCard.findViewById(R.id.animation_view);
        animation_view.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animation_view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                anim.start();
            }
        });

        scratchCard.setOnScratchListener(new ScratchCard.OnScratchListener() {
            @Override
            public void onScratch(ScratchCard scratchCard, float visiblePercent) {
                if (visiblePercent > 0.4) {
                    scratchCard.setVisibility(View.GONE);
                    animation_view.setVisibility(View.VISIBLE);
                    animation_view.playAnimation();
                    Utility.putIDString(MainActivity.this, responseMain.getScratchCard().getId(), Utility.getCurrentDate());
                    isScratchCardScratched = true;
                }
            }
        });

        Glide.with(this)
                .asBitmap()
                .load(responseMain.getScratchCard().getFrontImage())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(getResources().getDimensionPixelSize(R.dimen.size_10dp))))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        try {
                            scratchCard.setScratchDrawable(new BitmapDrawable(getResources(), resource));
                            dialogScratchCard.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        TextView tvTimerText = dialogScratchCard.findViewById(R.id.tvTimerText);
        tvTimerText.setText(responseMain.getScratchCard().getTimer());

        TextView tvTitle = dialogScratchCard.findViewById(R.id.tvTitle);
        tvTitle.setText(responseMain.getScratchCard().getTitle());
        tvTitle.setTextColor(Color.parseColor(responseMain.getScratchCard().getTextColor()));

        TextView tvDescription = dialogScratchCard.findViewById(R.id.tvDescription);
        tvDescription.setText(responseMain.getScratchCard().getDescription());
        tvDescription.setTextColor(Color.parseColor(responseMain.getScratchCard().getTextColor()));

        TextView tvNote = dialogScratchCard.findViewById(R.id.tvNote);
        tvNote.setText(responseMain.getScratchCard().getNote());
        tvNote.setTextColor(Color.parseColor(responseMain.getScratchCard().getTextColor()));

        if (!isStringNullOrEmpty(responseMain.getScratchCard().getBtnName())) {
            btnAction.setText(responseMain.getScratchCard().getBtnName());
        }
//        if (!isStringNullOrEmpty(responseMain.getScratchCard().getBtnColor())) {
//            btnAction.getBackground().setTint(Color.parseColor(responseMain.getScratchCard().getBtnColor()));
//        }
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScratchCardScratched) {
                    if (!isStringNullOrEmpty(responseMain.getScratchCard().getUrl())) {
                        Utility.openUrl(MainActivity.this, responseMain.getScratchCard().getUrl());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please scratch above view first.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogScratchCard.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                showAfterInterstitialPopup();
            }
        });
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                new CountDownTimer((timerCount * 1000), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Display Data by Every Second
                        timerCount = timerCount - 1;
                        tvTimerText.setText("" + timerCount);
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

    private void showInterstitial() {

        utility.showLovinInterDismiss(MainActivity.this, new AdEventListener() {
            @Override
            public void onAdClosed() {
                if (!dialogExit.isShowing()) {
                    dialogExit.show();
                }
            }
        });


    }

    public void setHeaderView(String title) {
        View v = LayoutInflater.from(this).inflate(R.layout.actionbar_home, null);
        TextView action_bar_title = v.findViewById(R.id.action_bar_title);
        action_bar_title.setText(title);
        ImageView imgNavigation = v.findViewById(R.id.imgNavigation);
        imgStory = v.findViewById(R.id.imgStory);
        ivNotification = v.findViewById(R.id.ivNotification);
        ivWebMain = v.findViewById(R.id.ivWebMain);
        Glide.with(MainActivity.this)
                .load(R.drawable.imggifstory)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(imgStory);
        imgNavigation.setImageResource(R.drawable.baseline_menu_24);
        getSupportActionBar().setCustomView(v);
        imgNavigation.setOnClickListener(view -> {
            try {
                drawerLayout.openDrawer(Gravity.LEFT);
            } catch (Exception r) {
                r.printStackTrace();
            }
        });


    }

    private void initView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView(getResources().getString(R.string.app_name));
        utility = new Utility();
        rvMenulist = findViewById(R.id.rvMenulist);
        loutSlider = findViewById(R.id.loutSlider);
        ivOfferlist = findViewById(R.id.ivOfferlist);
        relPrediction = findViewById(R.id.relPrediction);
        relCricketNews = findViewById(R.id.relCricketNews);
        cardNews = findViewById(R.id.cardNews);
        cardSuggestion = findViewById(R.id.cardSuggestion);
        cardOfferList = findViewById(R.id.cardOfferList);
        cardWebTop = findViewById(R.id.cardWebTop);
        cardWebBottom = findViewById(R.id.cardWebBottom);
        adLayoutLovin = findViewById(R.id.adLayoutLovin);
        cardNative = findViewById(R.id.cardNative);
        loutInflate = findViewById(R.id.loutInflate);
        pagerSlider = findViewById(R.id.pager);
        txtUpcoming = findViewById(R.id.txtUpcoming);
        rcList = findViewById(R.id.rcList);
        rvApplist = findViewById(R.id.rvApplist);
        bannerContainer = findViewById(R.id.banner_container);
        menuAdBanner = findViewById(R.id.menuAdBanner);
        webNote = findViewById(R.id.webNote);
        webNoteBottom = findViewById(R.id.webNoteBottom);
        navView = findViewById(R.id.nav_view);
        MenuShareApp = findViewById(R.id.MenuShareApp);
        menuContactUs = findViewById(R.id.menuContactUs);
        menuMoreApps = findViewById(R.id.menuMoreApps);
        menuPrivacyPolicy = findViewById(R.id.menuPrivacyPolicy);
        menuRateApp = findViewById(R.id.menuRateApp);
        MenuAboutUs = findViewById(R.id.MenuAboutUs);
        txtMenuFooter = findViewById(R.id.txtMenuFooter);
        txtAppVersion = findViewById(R.id.txtAppVersion);

        relPrediction.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,CricketListActivity.class)));

        menuContactUs.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.setPackage("com.google.android.gm");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailID});
            i.putExtra(Intent.EXTRA_SUBJECT, "");
            i.putExtra(Intent.EXTRA_TEXT, "");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawers();
                }
            }, 1000);
        });

        MenuAboutUs.setOnClickListener(v -> {
            Intent in = new Intent(MainActivity.this, WebActivity.class);
            in.putExtra("title", "About Us");
            in.putExtra("url", "https://cricketfirst.co.in/api/aboutus.html");
            startActivity(in);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawers();
                }
            }, 1000);
        });


        menuRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName(); // package name of the app
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawers();
                    }
                }, 1000);
            }
        });

        menuPrivacyPolicy.setOnClickListener(v -> {
            Intent in = new Intent(MainActivity.this, WebActivity.class);
            in.putExtra("title", "Privacy Policy");
            in.putExtra("url", privacyPolicy);
            startActivity(in);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawers();
                }
            }, 1000);
        });

        menuMoreApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MoreAppActivity.class));
            }
        });

        OneSignal.promptForPushNotifications();
        responseMain = new Gson().fromJson(Utility.getAsync(MainActivity.this, "HomeData"), ResponseModel.class);
        setHomeData(MainActivity.this, responseMain);
    }

    public void setHomeData(Activity activity, ResponseModel responseMain) {

        responseHome = responseMain;

        loutInflate.setVisibility(View.VISIBLE);
        if (loutInflate != null) {
            loutInflate.removeAllViews();
        }
        if (responseMain.getIsShowMoreApps() != null) {
            menuMoreApps.setVisibility(View.VISIBLE);
            if (responseMain.getIsShowMoreApps().matches("1")) {
                menuMoreApps.setVisibility(View.VISIBLE);
            } else {
                menuMoreApps.setVisibility(View.GONE);
            }
        } else {
            menuMoreApps.setVisibility(View.GONE);
        }

        if (!Utility.isStringNullOrEmpty(responseMain.getHomeDilogeNote())) {
            showHomeNoteDialog(responseMain);
        }

        if (responseMain.getManuList() != null && responseMain.getManuList().size() > 0) {
            rvMenulist.setVisibility(View.VISIBLE);
            cardSuggestion.setVisibility(View.VISIBLE);
            rvMenulist.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
            rvMenulist.setNestedScrollingEnabled(false);
            menulistAdapter = new MenulistAdapter(MainActivity.this, responseMain.getManuList());
            rvMenulist.setAdapter(menulistAdapter);
        } else {
            rvMenulist.setVisibility(View.GONE);
            cardSuggestion.setVisibility(View.GONE);
        }

        if (responseMain.getHomeSlider() != null && responseMain.getHomeSlider().size() > 0) {
            pagerSlider.setVisibility(View.VISIBLE);
            loutSlider.setVisibility(View.VISIBLE);
            pagerSlider.setClear();
            ArrayList<CategoryModel> dataSlider = responseMain.getHomeSlider();
            for (int i = 0; i < dataSlider.size(); i++) {
                pagerSlider.addItem(new PagerModel(dataSlider.get(i).getImage(), "", getApplicationContext()));
            }
            pagerSlider.start();
            Log.e("pagerSlider--)", "" + dataSlider.size());
            pagerSlider.setOnItemClickListener(new PagerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Utility.Redirect(activity, responseMain.getHomeSlider().get(position));
                }
            });
        } else {
            pagerSlider.setVisibility(View.GONE);
            loutSlider.setVisibility(View.GONE);
        }


        if (Utility.getIsLovinAdShow(MainActivity.this).matches("2")) {
            adLayoutLovin.setVisibility(View.VISIBLE);
            cardNative.setVisibility(View.VISIBLE);
            utility.showLovinNative(MainActivity.this, Utility.getLovinNative(MainActivity.this), adLayoutLovin);
        }
        else if (Utility.getIsLovinAdShow(MainActivity.this).matches("1")) {
            adLayoutLovin.setVisibility(View.VISIBLE);
            cardNative.setVisibility(View.VISIBLE);
            utility.showGoogleNative(MainActivity.this, adLayoutLovin);
        }


        if (responseMain.getIconList() != null) {
            View iconView = getLayoutInflater().inflate(R.layout.inflate_home_iconlist, null);
            RecyclerView rvIconlist = iconView.findViewById(R.id.rvIconlist);
            loutInflate.setVisibility(View.VISIBLE);
            HomeStoryAdapter homeStoryAdapter = new HomeStoryAdapter(MainActivity.this, responseMain.getIconList());
            rvIconlist.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            rvIconlist.setAdapter(homeStoryAdapter);
            loutInflate.addView(iconView);
        }
        else{
            loutInflate.setVisibility(View.GONE);
        }

        if (responseMain.getSpinData() != null && (Utility.getSpinIDString(MainActivity.this, responseMain.getSpinData().getId()).length() == 0 || !Utility.getSpinIDString(MainActivity.this, responseMain.getSpinData().getId()).equals(Utility.getCurrentDate()))) {
            utility.showSpinDialog(MainActivity.this);
        }
        if (responseMain.getPrivacyPolicy() != null) {
            privacyPolicy = responseMain.getPrivacyPolicy();
        }

        Log.e("Ofefer--)",""+responseMain.getOfferListMain());

        if (!Utility.isStringNullOrEmpty(responseMain.getOfferListMain())) {
            cardOfferList.setVisibility(View.VISIBLE);
            Glide.with(MainActivity.this)
                    .load(responseMain.getOfferListMain())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(ivOfferlist);
            ivOfferlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,OfferlistActivity.class));
                }
            });
        }
        else
        {
            ivOfferlist.setVisibility(View.GONE);
            cardOfferList.setVisibility(View.GONE);
        }

        if (!Utility.isStringNullOrEmpty(responseMain.getHomeSiteUrl())) {
            ivWebMain.setVisibility(View.VISIBLE);
            homeWebUrl = responseMain.getHomeSiteUrl();
        } else {
            ivWebMain.setVisibility(View.GONE);
        }

        if (responseMain.getMailId() != null) {
            emailID = responseMain.getMailId();
        }
        if (responseMain.getCricketList() != null && responseMain.getCricketList().size() > 0) {
            rcList.setVisibility(View.VISIBLE);
            rcList.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
            rcList.setNestedScrollingEnabled(false);
            cricketHomeListAdapter = new CricketHomeListAdapter(MainActivity.this, responseMain.getCricketList());
            rcList.setAdapter(cricketHomeListAdapter);
            txtUpcoming.setVisibility(View.VISIBLE);
        } else {
            txtUpcoming.setVisibility(View.GONE);
            rcList.setVisibility(View.GONE);
        }

        if(!Utility.isStringNullOrEmpty(responseMain.getNewsUrl()))
        {
            cardNews.setVisibility(View.VISIBLE);
            relCricketNews.setOnClickListener(v -> {
                Intent in = new Intent(MainActivity.this, WebActivity.class);
                in.putExtra("title", "News");
                in.putExtra("url", responseMain.getNewsUrl());
                startActivity(in);
            });
        }
        else
        {
            cardNews.setVisibility(View.GONE);
        }

        if (responseMain.getAdAppList() != null && responseMain.getAdAppList().size() > 0) {
            rvApplist.setVisibility(View.VISIBLE);
            cardSuggestion.setVisibility(View.VISIBLE);
            rvApplist.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));
            rvApplist.setNestedScrollingEnabled(false);
            applistAdapter = new ApplistAdapter(MainActivity.this, responseMain.getAdAppList());
            rvApplist.setAdapter(applistAdapter);
        } else {
            rvApplist.setVisibility(View.GONE);
            cardSuggestion.setVisibility(View.GONE);
        }

        if (responseMain.getStoryView() != null && responseMain.getStoryView().size() > 0) {
            imgStory.setVisibility(View.VISIBLE);
            imgStory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new StoryView.Builder(getSupportFragmentManager())
                            .setStoriesList(responseMain.getStoryView())
                            .setStoryDuration(5000)
                            .setTitleText("")
                            .setSubtitleText("")
                            .setStoryClickListeners(new StoryClickListeners() {
                                @Override
                                public void onDescriptionClickListener(int position) {
                                    if (!isStringNullOrEmpty(responseMain.getStoryView().get(position).getClickUrl())) {

                                        Utility.openUrl(MainActivity.this, responseMain.getStoryView().get(position).getClickUrl());
                                    }
                                }

                                @Override
                                public void onTitleIconClickListener(int position) {

                                }
                            })
                            .setOnStoryChangedCallback(new OnStoryChangedCallback() {
                                @Override
                                public void storyChanged(int position) {
                                }
                            })
                            .setStartingIndex(0)
                            .setRtl(false)
                            .build()
                            .show();
                }
            });
        } else {
            imgStory.setVisibility(View.GONE);
        }

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
            }
        });

        ivWebMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (homeWebUrl != null && !homeWebUrl.trim().isEmpty()) {
                    Utility.openUrl(MainActivity.this, homeWebUrl);
                }
            }
        });
        if (responseMain.getExitDialoge() != null) {
            loadHomeExit(MainActivity.this, responseMain.getExitDialoge());
        } else {
            loadHomeExit(MainActivity.this, null);
        }

        if (responseMain.getHomeDiloage() != null && !isStringNullOrEmpty(responseMain.getHomeDiloage().getId())) {
            CategoryModel popData = responseMain.getHomeDiloage();
            if (!isStringNullOrEmpty(responseMain.getHomeDiloage().getIsShowEverytime()) && responseMain.getHomeDiloage().getIsShowEverytime().equals("1")) {
                Utility.setHomePopID(activity, responseMain.getHomeDiloage().getId());
                Utility.ShomeHomePopup(activity, popData);
            } else {
                if (!Utility.getHomePopID(activity).equals(responseMain.getHomeDiloage().getId())) {
                    if (!isStringNullOrEmpty(popData.getPackagename())) {
                        if (!Utility.AppInstalledOrNot(activity, popData.getPackagename())) {
                            Utility.setHomePopID(activity, responseMain.getHomeDiloage().getId());
                            Utility.ShomeHomePopup(activity, popData);
                        }
                    } else {
                        Utility.setHomePopID(activity, responseMain.getHomeDiloage().getId());
                        Utility.ShomeHomePopup(activity, popData);
                    }
                }
            }
        }

        if (!isStringNullOrEmpty(responseMain.getHomeNote())) {
            webNote.setVisibility(View.VISIBLE);
            cardWebTop.setVisibility(View.VISIBLE);
            webNote.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webNote.getSettings().setLoadWithOverviewMode(false);
            webNote.setScrollContainer(true);
            webNote.getSettings().setJavaScriptEnabled(true);
            webNote.loadDataWithBaseURL(null, responseMain.getHomeNote(), "text/html", "UTF-8", null);
        }
        else
        {
            cardWebTop.setVisibility(View.GONE);
        }

        if (!isStringNullOrEmpty(responseMain.getButtomeNote())) {
            webNoteBottom.setVisibility(View.VISIBLE);
            cardWebBottom.setVisibility(View.VISIBLE);
            webNoteBottom.getSettings().setJavaScriptEnabled(true);
            webNoteBottom.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webNoteBottom.getSettings().setLoadWithOverviewMode(false);
            webNoteBottom.setScrollContainer(true);
            webNoteBottom.loadDataWithBaseURL(null, responseMain.getButtomeNote(), "text/html", "UTF-8", null);
        }
        else
        {
            cardWebBottom.setVisibility(View.GONE);
        }

        if (responseMain != null) {
            if (responseMain.getBottemBanner() != null) {
                if (responseMain.getBottemBanner().getType() != null) {
                    if (responseMain.getBottemBanner().getType().matches("1")) {
                        utility.showGoogleBanner(MainActivity.this,bannerContainer);
                    } else if (responseMain.getBottemBanner().getType().matches("2")) {
                        utility.showLovinBanner(MainActivity.this, bannerContainer);
                    } else if (responseMain.getBottemBanner().getType().matches("3")) {
                        utility.LoadBannerAd(MainActivity.this, bannerContainer, responseMain.getBottemBanner());
                    }
                }
            }
        }

        if (responseMain.getMenuBanner() != null) {
            if (!isStringNullOrEmpty(responseMain.getMenuBanner().getImage())) {
                menuAdBanner.setVisibility(View.VISIBLE);
                if (responseMain.getMenuBanner().getImage().contains(".gif")) {
                    Glide.with(activity)
                            .load(responseMain.getMenuBanner().getImage())
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .into(menuAdBanner);
                } else {
                    Picasso.get().load(responseMain.getMenuBanner().getImage()).into(menuAdBanner);
                }
                menuAdBanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isStringNullOrEmpty(responseMain.getMenuBanner().getUrl())) {
                            Utility.openUrl(MainActivity.this, responseMain.getMenuBanner().getUrl());
                        }

                    }
                });
            } else {
                menuAdBanner.setVisibility(View.GONE);
            }
        }


        if (responseMain.getAppVersion() != null) {
            try {
                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                String version = pInfo.versionName;
                txtAppVersion.setText("Version: " + version);
                if (!responseMain.getAppVersion().equals(version)) {
                    Utility.UpdateApp(MainActivity.this, responseMain.getIsForupdate(), responseMain.getAppUrl(), responseMain.getUpdateMessage());
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            if (Utility.getIsFromnotification(MainActivity.this)) {
                Utility.setIsFromnotification(MainActivity.this, false);
                Utility.Redirect(MainActivity.this, Utility.getNotificationData(MainActivity.this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        MenuShareApp.setOnClickListener(v -> {
            if (responseMain.getShareUrl() != null && !responseMain.getShareUrl().isEmpty()) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 74);
                    } else {
                        shareImageData1(MainActivity.this, responseMain.getShareUrl(), "", responseMain.getShareMessage());
                    }
                } else {
                    shareImageData1(MainActivity.this, responseMain.getShareUrl(), "", responseMain.getShareMessage());
                }
            } else {
                shareImageData1(MainActivity.this, "", "", responseMain.getShareMessage());
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawers();
                }
            }, 1000);
        });
    }

    public void loadHomeExit(Activity activity, CategoryModel popData) {
        if (activity != null) {
            dialogExit = new Dialog(activity);
            dialogExit.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogExit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogExit.setContentView(R.layout.dialogue_home_exit);
            dialogExit.setCancelable(false);
            TextView lblLoadingAds = dialogExit.findViewById(R.id.lblLoadingAds);
            FrameLayout frameLayoutNativeAd = dialogExit.findViewById(R.id.fl_adplaceholder);
            FrameLayout frameLayoutNativeAdLovin = dialogExit.findViewById(R.id.fl_adplaceholder_lovin);
            Button btnYesExit = dialogExit.findViewById(R.id.btnYesExit);
            LinearLayout lWatch = dialogExit.findViewById(R.id.lWatch);
            TextView txtWatch = dialogExit.findViewById(R.id.txtWatch);
            TextView txtTitle = dialogExit.findViewById(R.id.txtTitle);
            TextView btnNoExit = dialogExit.findViewById(R.id.btnNoExit);
            LinearLayout lDataExit = dialogExit.findViewById(R.id.lDataExit);
            ProgressBar probrBanner = dialogExit.findViewById(R.id.probrBanner);
            RelativeLayout relPopup = dialogExit.findViewById(R.id.relPopup);
            ImageView imgBanner = dialogExit.findViewById(R.id.imgBanner);
            LinearLayout layoutAds = dialogExit.findViewById(R.id.layoutAds);

            if (popData != null) {
                lDataExit.setVisibility(View.VISIBLE);
                layoutAds.setVisibility(View.GONE);
                if (popData != null) {
                    lDataExit.setVisibility(View.VISIBLE);
                    txtTitle.setText(popData.getTitle());
                    TextView txtMessage = dialogExit.findViewById(R.id.txtMessage);
                    txtMessage.setText(popData.getDescription());
                    if (!isStringNullOrEmpty(popData.getBtnName())) {
                        txtWatch.setText(popData.getBtnName());
                    }

                    if (!isStringNullOrEmpty(popData.getImage())) {
                        if (popData.getImage().contains(".gif")) {
                            imgBanner.setVisibility(View.VISIBLE);
                            probrBanner.setVisibility(View.GONE);
                            Glide.with(activity)
                                    .load(popData.getImage())
                                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                    .into(imgBanner);
                        } else {
                            probrBanner.setVisibility(View.VISIBLE);
                            imgBanner.setVisibility(View.VISIBLE);

                            Glide.with(activity)
                                    .load(popData.getImage())
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                                            probrBanner.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .into(imgBanner);
                        }

                    } else {

                        imgBanner.setVisibility(View.GONE);
                        probrBanner.setVisibility(View.GONE);
                    }

                    relPopup.setOnClickListener(v -> Utility.Redirect(activity, popData));

                    lWatch.setOnClickListener(v -> {
                        if (!activity.isFinishing()) {
                            dialogExit.dismiss();
                        }
                        Utility.Redirect(activity, popData);
                    });
                } else {
                    lDataExit.setVisibility(View.GONE);
                }
            } else if (Utility.getIsLovinAdShow(MainActivity.this).matches("1")) {
                frameLayoutNativeAdLovin.setVisibility(View.VISIBLE);
                frameLayoutNativeAd.setVisibility(View.GONE);
                layoutAds.setVisibility(View.VISIBLE);
                lDataExit.setVisibility(View.GONE);
                lblLoadingAds.setVisibility(View.GONE);

                utility.showLovinNativeExit(MainActivity.this, Utility.getLovinNative(MainActivity.this), frameLayoutNativeAdLovin);
            } else {
                layoutAds.setVisibility(View.GONE);
                frameLayoutNativeAdLovin.setVisibility(View.GONE);
                frameLayoutNativeAd.setVisibility(View.GONE);
                lDataExit.setVisibility(View.GONE);
            }
            btnNoExit.setOnClickListener(v -> {
                if (!activity.isFinishing()) {
                    dialogExit.dismiss();

                    if (Utility.getIsLovinAdShow(MainActivity.this).matches("2")) {
                        utility.showLovinInter(MainActivity.this, responseMain.getAdFailUrl());
                    }
                    else if(Utility.getIsLovinAdShow(MainActivity.this).matches("1"))
                    {
                        utility.showGoogleInter(MainActivity.this);
                    }

                }

            });

            btnYesExit.setOnClickListener(view -> {
                if (!activity.isFinishing()) {
                    dialogExit.dismiss();
                }
                isLoadSplash = false;
                finishAffinity();
            });

            Display display = activity.getWindowManager().getDefaultDisplay();
            int width = display.getWidth() - 18;
            WindowManager.LayoutParams lp;
            lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialogExit.getWindow().getAttributes());
            lp.width = (int) (width);
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialogExit.getWindow().setAttributes(lp);
        }
    }

    public void shareImageData1(Activity activity, String img, String pkg, String msg) {
        Intent share;
        if (img.trim().length() > 0) {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/FantasyBuzz/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String[] str = img.trim().split("/");
            String extension = "";
            if (str[str.length - 1].contains(".")) {
                extension = str[str.length - 1].substring(str[str.length - 1].lastIndexOf("."));
            }
            if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".gif")) {
                extension = "";
            } else {
                extension = ".png";
            }
            File file = new File(dir, str[str.length - 1] + extension);
            if (file.exists()) {
                try {
                    share = new Intent(Intent.ACTION_SEND);
                    Uri uri = null;
                    if (Build.VERSION.SDK_INT >= 24) {
                        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        uri = FileProvider.getUriForFile(activity.getApplicationContext(), activity.getPackageName(), file);
                    } else {
                        uri = Uri.fromFile(file);
                    }
                    share.setType("image/*");
                    if (img.contains(".gif")) {
                        share.setType("image/gif");
                    } else {
                        share.setType("image/*");
                    }
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    share.putExtra(Intent.EXTRA_SUBJECT, Global_App.APPNAME);
                    share.putExtra(Intent.EXTRA_TEXT, msg);
                    activity.startActivity(Intent.createChooser(share, "Share"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (Utility.checkInternetConnection(activity)) {
                new DownloadImageShareAsync(activity, file, img, "", msg).execute();
            }
        } else {
            try {
                share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_SUBJECT, "");
                share.putExtra(Intent.EXTRA_TEXT, msg);
                share.setType("text/plain");
                activity.startActivity(Intent.createChooser(share, "Share"));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 74) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MenuShareApp.performClick();
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}