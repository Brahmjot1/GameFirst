package com.player.cricketfirst.activity;

import static com.player.cricketfirst.activity.MainActivity.homeWebUrl;
import static com.player.cricketfirst.util.Utility.isStringNullOrEmpty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.player.cricketfirst.R;
import com.player.cricketfirst.async.GetMatchDetailsAsync;
import com.player.cricketfirst.model.RequestModel;
import com.player.cricketfirst.model.ResponseModel;
import com.player.cricketfirst.util.UrlController;
import com.player.cricketfirst.util.Utility;
import com.squareup.picasso.Picasso;

public class MatchDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar probrMain;
    private String matchID = "", team1Name = "", team2Name = "";
    Utility utility;
    private WebView webNote,webNoteBottom;
    private TextView action_bar_title,txtTeam1Preview,txtTeam2Preview;
    private ImageView imgTopBanner, imgeLiveBanner;
    private LinearLayout banner_container;
    public static ResponseModel ResopnseMain;
    UrlController urlController;
    public String teamWebUrl = "";
    LinearLayout relMatchDetail, relExpertTeam;
    LinearLayout ivWebPrime;
    CardView lLovinNative,cardNews;

    public void setHeaderView(String title) {
        View v = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
        action_bar_title = v.findViewById(R.id.action_bar_title);
        action_bar_title.setText(title);
        ImageView imgNavigation = v.findViewById(R.id.imgNavigation);
        ImageView ivWebMain = v.findViewById(R.id.ivWebMain);
        ivWebPrime = v.findViewById(R.id.ivWebPrime);

        if (homeWebUrl != null && !homeWebUrl.trim().isEmpty()) {
            ivWebMain.setVisibility(View.VISIBLE);
        } else {
            ivWebMain.setVisibility(View.GONE);
        }


        ivWebMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (homeWebUrl != null && !homeWebUrl.trim().isEmpty()) {
                    Utility.openUrl(MatchDetailsActivity.this, homeWebUrl);
                }
            }
        });

        ivWebPrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = teamWebUrl;
                if (url != null && !url.trim().isEmpty()) {
                    Utility.openUrl(MatchDetailsActivity.this, url);
                }
            }
        });
        imgNavigation.setImageResource(R.drawable.ic_back);
        getSupportActionBar().setCustomView(v);
        imgNavigation.setOnClickListener(view -> finish());
    }

    Boolean isAllFabsVisible;
    FrameLayout adLayout, adLayoutLovin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        team2Name = getIntent().getStringExtra("team2Name");
        team1Name = getIntent().getStringExtra("team1Name");
        matchID = getIntent().getStringExtra("matchID");
        initView();
    }

    private void initView() {
        utility = new Utility();
        urlController = new UrlController(MatchDetailsActivity.this);
        adLayout = findViewById(R.id.adLayout);
        lLovinNative = findViewById(R.id.lLovinNative);
        adLayoutLovin = findViewById(R.id.adLayoutLovin);
        cardNews = findViewById(R.id.cardNews);
        webNote = findViewById(R.id.webNote);
        txtTeam1Preview = findViewById(R.id.txtTeam1Preview);
        webNoteBottom = findViewById(R.id.webNoteBottom);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView(team1Name + " vs " + team2Name);

        String toolName = team1Name + " vs " + team2Name;
        txtTeam1Preview.setText(toolName);
        probrMain = findViewById(R.id.probrMain);
        relMatchDetail = findViewById(R.id.relMatchDetail);
        relExpertTeam = findViewById(R.id.relExpertTeam);

        imgTopBanner = findViewById(R.id.imgTopBanner);
        imgeLiveBanner = findViewById(R.id.imgeLiveBanner);
        banner_container = findViewById(R.id.banner_container);

        probrMain.setVisibility(View.VISIBLE);
        isAllFabsVisible = false;

        RequestModel requestModel = new RequestModel();
        requestModel.setMatchId(matchID);
        new GetMatchDetailsAsync(MatchDetailsActivity.this, requestModel);

        relMatchDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toolName = team1Name + " vs " + team2Name;
                startActivity(new Intent(MatchDetailsActivity.this, MatchFullViewActivity.class).putExtra("toolName", toolName));
            }
        });

        relExpertTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MatchDetailsActivity.this, MatchPreviewActivity.class));

            }
        });
    }

    public void setHomeData(Activity activity, ResponseModel responseModel) {
        probrMain.setVisibility(View.GONE);


        if (!isStringNullOrEmpty(responseModel.getIsApplovinAdshow())) {
             if (responseModel.getIsApplovinAdshow().matches("2")) {
                utility.showLovinInter(MatchDetailsActivity.this, responseModel.getAdFailUrl());
            }
             else if(responseModel.getIsApplovinAdshow().matches("1"))
             {
                 utility.showGoogleInter(MatchDetailsActivity.this);
             }
        }
//        if (responseModel.getNativeAdType() != null) {
            if (Utility.getIsLovinAdShow(MatchDetailsActivity.this).matches("2")) {
                lLovinNative.setVisibility(View.VISIBLE);
                adLayoutLovin.setVisibility(View.VISIBLE);
                adLayout.setVisibility(View.GONE);
                utility.showLovinNative(MatchDetailsActivity.this,  Utility.getLovinNative(MatchDetailsActivity.this), adLayoutLovin);
            }
            else if (Utility.getIsLovinAdShow(MatchDetailsActivity.this).matches("1")) {
                lLovinNative.setVisibility(View.VISIBLE);
                adLayoutLovin.setVisibility(View.VISIBLE);
                adLayout.setVisibility(View.GONE);
                utility.showGoogleNative(MatchDetailsActivity.this, adLayoutLovin);
            }
            else {
                lLovinNative.setVisibility(View.GONE);
            }
//        }

        if(!Utility.isStringNullOrEmpty(responseModel.getMatchDetails().getNewsUrl()))
        {
            cardNews.setVisibility(View.VISIBLE);

            cardNews.setOnClickListener(v -> {
                Intent in = new Intent(MatchDetailsActivity.this, WebActivity.class);
                in.putExtra("title", "News");
                in.putExtra("url", responseModel.getMatchDetails().getNewsUrl());
                startActivity(in);
            });
        }
        else
        {
            cardNews.setVisibility(View.GONE);
        }


        if (responseModel.getLiveMatchBanner() != null) {

            if (!isStringNullOrEmpty(responseModel.getLiveMatchBanner().getImage())) {
                Glide.with(MatchDetailsActivity.this)
                        .load(responseModel.getLiveMatchBanner().getImage())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .into(imgeLiveBanner);

                imgeLiveBanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utility.openUrl(MatchDetailsActivity.this, responseModel.getLiveMatchBanner().getUrl());
                    }
                });
            }
        }

        if (responseModel.getTeamName1() != null && responseModel.getTeamName2() != null) {
            setHeaderView(responseModel.getTeamName1() + " vs " + responseModel.getTeamName2());
        }

        if (responseModel.getMiniAds() != null) {
            urlController.showMiniAdsDialog(MatchDetailsActivity.this, responseModel.getMiniAds());
        }

        if (responseModel.getPrimeSiteUrl() != null) {
            teamWebUrl = responseModel.getPrimeSiteUrl();
        }

        if (teamWebUrl != null && !teamWebUrl.trim().isEmpty()) {
            ivWebPrime.setVisibility(View.VISIBLE);
        } else {
            ivWebPrime.setVisibility(View.GONE);
        }

        if (!isStringNullOrEmpty(responseModel.getHomeNote())) {
            webNote.setVisibility(View.VISIBLE);
            webNote.getSettings().setJavaScriptEnabled(true);
            webNote.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webNote.getSettings().setLoadWithOverviewMode(false);
            webNote.setScrollContainer(true);
            webNote.loadDataWithBaseURL(null, responseModel.getHomeNote(), "text/html", "UTF-8", null);
        } else {
            webNote.setVisibility(View.GONE);
        }
        if (!isStringNullOrEmpty(responseModel.getButtomeNote())) {
            webNoteBottom.setVisibility(View.VISIBLE);
            webNoteBottom.getSettings().setJavaScriptEnabled(true);
            webNoteBottom.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webNoteBottom.getSettings().setLoadWithOverviewMode(false);
            webNoteBottom.setScrollContainer(true);
            webNoteBottom.loadDataWithBaseURL(null, responseModel.getButtomeNote(), "text/html", "UTF-8", null);
        }


        if (!Utility.isStringNullOrEmpty(responseModel.getTopImage())) {
            imgTopBanner.setVisibility(View.VISIBLE);
            if (responseModel.getTopImage().contains(".gif")) {
                Glide.with(activity)
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
                        Utility.openUrl(MatchDetailsActivity.this, responseModel.getTopUrl());
                    }

                }
            });
        } else {
            imgTopBanner.setVisibility(View.GONE);
        }
        ResopnseMain = responseModel;
        if (ResopnseMain != null) {
            if (ResopnseMain.getBottemBanner() != null) {
                if (ResopnseMain.getBottemBanner().getType().matches("1")) {
                    utility.showGoogleBanner(MatchDetailsActivity.this, banner_container);
                }
                else if (ResopnseMain.getBottemBanner().getType().matches("2")) {
                    utility.showLovinBanner(MatchDetailsActivity.this, banner_container);
                } else if (ResopnseMain.getBottemBanner().getType().matches("3")) {
                    utility.LoadBannerAd(MatchDetailsActivity.this, banner_container, responseModel.getBottemBanner());
                }
            }
        }

    }
}