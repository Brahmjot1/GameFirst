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
import com.player.cricketfirst.adapter.MoreAppsAdapter;
import com.player.cricketfirst.async.MoreAppDataAsync;
import com.player.cricketfirst.model.ResponseModel;
import com.player.cricketfirst.util.UrlController;
import com.player.cricketfirst.util.Utility;
import com.squareup.picasso.Picasso;

public class MoreAppActivity extends AppCompatActivity {

    private ImageView ivBack;
    private RecyclerView rvMoreApp;
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
        setContentView(R.layout.activity_more_app);

        initView();
        ivBack.setOnClickListener(v -> finish());
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        cardNative = findViewById(R.id.cardNative);
        rvMoreApp = findViewById(R.id.rvMoreApp);
        txtNofound = findViewById(R.id.txtNofound);
        webNote = findViewById(R.id.webNote);
        adLayoutLovin = findViewById(R.id.adLayoutLovin);
        utility = new Utility();

        urlController = new UrlController(MoreAppActivity.this);
        imgTopBanner = findViewById(R.id.imgTopBanner);
        banner_container = findViewById(R.id.banner_container);
        new MoreAppDataAsync(MoreAppActivity.this);
    }

    public void setData(ResponseModel responseModel) {

       if (Utility.getIsLovinAdShow(MoreAppActivity.this).matches("2")) {
            utility.showLovinInter(MoreAppActivity.this, responseModel.getAdFailUrl());
        }
       else if(Utility.getIsLovinAdShow(MoreAppActivity.this).matches("1"))
       {
           utility.showGoogleInter(MoreAppActivity.this);
       }

        if (responseModel.getAppList() != null && responseModel.getAppList().size() > 0) {
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
            MoreAppsAdapter mAdapter = new MoreAppsAdapter(MoreAppActivity.this, responseModel.getAppList());
            rvMoreApp.setAdapter(mAdapter);
            rvMoreApp.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

            if (responseModel.getMiniAds() != null) {
                urlController.showMiniAdsDialog(MoreAppActivity.this, responseModel.getMiniAds());
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
                            Utility.openUrl(MoreAppActivity.this, responseModel.getTopUrl());
                        }

                    }
                });
            } else {
                imgTopBanner.setVisibility(View.GONE);
            }

            if (responseModel != null) {
                if (responseModel.getBottemBanner() != null) {
                    if (responseModel.getBottemBanner().getType().matches("1")) {
                        utility.showGoogleBanner(MoreAppActivity.this, banner_container);
                    }else if (responseModel.getBottemBanner().getType().matches("2")) {
                        utility.showLovinBanner(MoreAppActivity.this, banner_container);
                    } else if (responseModel.getBottemBanner().getType().matches("3")) {
                        utility.LoadBannerAd(MoreAppActivity.this, banner_container, responseModel.getBottemBanner());

                    }
                }
            }

        } else {
            txtNofound.setVisibility(View.VISIBLE);
            if (Utility.getIsLovinAdShow(MoreAppActivity.this).matches("2")) {
                cardNative.setVisibility(View.VISIBLE);
                adLayoutLovin.setVisibility(View.VISIBLE);
                utility.showLovinNative(MoreAppActivity.this,  Utility.getLovinNative(MoreAppActivity.this), adLayoutLovin);
            }
            else if (Utility.getIsLovinAdShow(MoreAppActivity.this).matches("1")) {
                cardNative.setVisibility(View.VISIBLE);
                adLayoutLovin.setVisibility(View.VISIBLE);
                utility.showGoogleNative(MoreAppActivity.this, adLayoutLovin);
            }
            else {
                cardNative.setVisibility(View.GONE);
            }
        }
    }
}