package com.player.cricketfirst.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.player.cricketfirst.R;
import com.player.cricketfirst.adapter.NotificationAdapter;
import com.player.cricketfirst.async.NotificationDataAsync;
import com.player.cricketfirst.model.ResponseModel;
import com.player.cricketfirst.util.UrlController;
import com.player.cricketfirst.util.Utility;
import com.squareup.picasso.Picasso;

public class NotificationActivity extends AppCompatActivity {

    private ImageView ivBack;
    private RecyclerView rvNotificationlist;
    private TextView txtNofound;
    private WebView webNote;
    private ImageView imgTopBanner;
    private LinearLayout banner_container;
    Utility utility;
    UrlController urlController;
    CardView cardNative;
    FrameLayout adLayoutLovin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initView();
        ivBack.setOnClickListener(v -> finish());
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        cardNative = findViewById(R.id.cardNative);
        adLayoutLovin = findViewById(R.id.adLayoutLovin);
        rvNotificationlist = findViewById(R.id.rvHowToEarnlist);
        txtNofound = findViewById(R.id.txtNofound);
        webNote = findViewById(R.id.webNote);
        utility = new Utility();
        urlController = new UrlController(NotificationActivity.this);
        imgTopBanner = findViewById(R.id.imgTopBanner);
        banner_container = findViewById(R.id.banner_container);
        new NotificationDataAsync(NotificationActivity.this);
    }

    public void setData(ResponseModel responseModel) {

        if(Utility.getIsLovinAdShow(NotificationActivity.this).matches("2")) {
            utility.showLovinInter(NotificationActivity.this, responseModel.getAdFailUrl());
        }
        else if(Utility.getIsLovinAdShow(NotificationActivity.this).matches("1"))
        {
            utility.showGoogleInter(NotificationActivity.this);
        }

        if (responseModel.getNotificationList() != null && responseModel.getNotificationList().size() > 0) {
            cardNative.setVisibility(View.GONE);
            if (!Utility.isStringNullOrEmpty(responseModel.getHomeNote())) {
                webNote.setVisibility(View.VISIBLE);
                webNote.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webNote.getSettings().setLoadWithOverviewMode(false);
                webNote.setScrollContainer(true);
                webNote.getSettings().setJavaScriptEnabled(true);
                webNote.loadDataWithBaseURL(null, responseModel.getHomeNote(), "text/html", "UTF-8", null);
            }

            txtNofound.setVisibility(View.GONE);
            NotificationAdapter mAdapter = new NotificationAdapter(NotificationActivity.this, responseModel.getNotificationList());
            rvNotificationlist.setAdapter(mAdapter);
            rvNotificationlist.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

            if (responseModel.getMiniAds() != null) {
                urlController.showMiniAdsDialog(NotificationActivity.this, responseModel.getMiniAds());
            }

            if (!Utility.isStringNullOrEmpty(responseModel.getTopImage())) {
                imgTopBanner.setVisibility(View.VISIBLE);
                if (responseModel.getTopImage().contains(".gif")) {
                    Glide.with(getApplicationContext())
                            .load(responseModel.getTopImage())
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .into(imgTopBanner);
                } else {
                    Picasso.get().load(responseModel.getTopImage()).into(imgTopBanner);
                }
                imgTopBanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!Utility.isStringNullOrEmpty(responseModel.getTopUrl())) {
                            Utility.openUrl(NotificationActivity.this, responseModel.getTopUrl());
                        }

                    }
                });
            } else {
                imgTopBanner.setVisibility(View.GONE);
            }

            if (responseModel != null) {
                if (responseModel.getBottemBanner() != null) {
                    if (responseModel.getBottemBanner().getType().matches("1")) {
                        utility.showGoogleBanner(NotificationActivity.this, banner_container);
                    }else if (responseModel.getBottemBanner().getType().matches("2")) {
                        utility.showLovinBanner(NotificationActivity.this, banner_container);
                    } else if (responseModel.getBottemBanner().getType().matches("3")) {
                        utility.LoadBannerAd(NotificationActivity.this, banner_container, responseModel.getBottemBanner());
                    }

                }
            }

        } else {
//            urlControl.dismissLoader();
            txtNofound.setVisibility(View.VISIBLE);
            if (Utility.getIsLovinAdShow(NotificationActivity.this).matches("2")) {
                cardNative.setVisibility(View.VISIBLE);
                adLayoutLovin.setVisibility(View.VISIBLE);
                utility.showLovinNative(NotificationActivity.this,  Utility.getLovinNative(NotificationActivity.this), adLayoutLovin);
            }
            else if (Utility.getIsLovinAdShow(NotificationActivity.this).matches("1")) {
                cardNative.setVisibility(View.VISIBLE);
                adLayoutLovin.setVisibility(View.VISIBLE);
                utility.showGoogleNative(NotificationActivity.this, adLayoutLovin);
            }
            else {
                cardNative.setVisibility(View.GONE);
            }
        }
    }
}