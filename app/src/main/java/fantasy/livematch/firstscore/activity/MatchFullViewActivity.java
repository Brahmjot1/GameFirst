package fantasy.livematch.firstscore.activity;


import static fantasy.livematch.firstscore.activity.MatchDetailsActivity.ResopnseMain;
import static fantasy.livematch.firstscore.util.Utility.isStringNullOrEmpty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fantasy.livematch.firstscore.R;
import fantasy.livematch.firstscore.model.ResponseModel;
import fantasy.livematch.firstscore.util.UrlController;
import fantasy.livematch.firstscore.util.Utility;

public class MatchFullViewActivity extends AppCompatActivity {

    private WebView webLoad;
    private TextView txtNoData, txtTooltitle;
    ImageView ivBack;
    Utility utility;
    private LinearLayout banner_container_bottom, banner_container_top;
    UrlController urlController;
    String toolText;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_full_view);

        Intent intent = getIntent();
        toolText = intent.getStringExtra("toolName");
        initView();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (ResopnseMain != null) {
            setData(ResopnseMain);
        }
    }

    public void setData(ResponseModel responseModel) {

        if (responseModel.getFullDetails() != null) {
            if (!isStringNullOrEmpty(responseModel.getFullDetails().getIsShowIndstrial())) {
                if (responseModel.getFullDetails().getIsShowIndstrial().matches("2")) {
                    utility.showLovinInter(MatchFullViewActivity.this, responseModel.getFullDetails().getAdFailUrl());
                }
                else if(responseModel.getFullDetails().getIsShowIndstrial().matches("1"))
                {
                    utility.showGoogleInter(MatchFullViewActivity.this);
                }
            }

            if (responseModel != null) {
                if (responseModel.getFullDetails().getTopAds() != null) {
                    if (responseModel.getFullDetails().getTopAds().getType() != null) {
                        if (responseModel.getFullDetails().getTopAds().getType().matches("1")) {
                            utility.showGoogleBanner(MatchFullViewActivity.this, banner_container_top);
                        } else if (responseModel.getFullDetails().getTopAds().getType().matches("2")) {
                            utility.showLovinBanner(MatchFullViewActivity.this, banner_container_top);
                        } else if (responseModel.getFullDetails().getTopAds().getType().matches("3")) {
                            utility.LoadBannerAd(MatchFullViewActivity.this, banner_container_top, responseModel.getFullDetails().getTopAds());
                        }
                    }
                }


                if (responseModel.getFullDetails().getBottemBanner() != null) {
                    if (responseModel.getFullDetails().getBottemBanner().getType() != null) {
                        if (responseModel.getFullDetails().getBottemBanner().getType().matches("1")) {
                            utility.showGoogleBanner(MatchFullViewActivity.this, banner_container_bottom);
                        } else if (responseModel.getFullDetails().getBottemBanner().getType().matches("2")) {
                            utility.showLovinBanner(MatchFullViewActivity.this, banner_container_bottom);
                        } else if (responseModel.getFullDetails().getBottemBanner().getType().matches("3")) {
                            utility.LoadBannerAd(MatchFullViewActivity.this, banner_container_bottom, responseModel.getFullDetails().getBottemBanner());
                        }
                    }

                }
            }
        }


        if (responseModel.getMatchDetails() != null) {
            webLoad.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/Medium.ttf\")}body {font-family: MyFont;font-size: small;text-align: justify;}</style></head><body>";
            String pas = "</body></html>";
            String myHtmlString = pish + responseModel.getMatchDetails().getData() + pas;
            webLoad.getSettings().setJavaScriptEnabled(true);
            webLoad.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webLoad.getSettings().setLoadWithOverviewMode(false);
            webLoad.setScrollContainer(true);
            webLoad.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);
            webLoad.setBackgroundColor(Color.parseColor("#F2F7FD"));
        } else {
            webLoad.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
            txtNoData.setText("No data found.");
        }

        if (responseModel.getMiniAds() != null) {
            urlController.showMiniAdsDialog(MatchFullViewActivity.this, responseModel.getMiniAds());
        }
    }


    private void initView() {

        utility = new Utility();
        urlController = new UrlController(MatchFullViewActivity.this);
        webLoad = findViewById(R.id.webView);
        ivBack = findViewById(R.id.ivBack);
        txtNoData = findViewById(R.id.txtNoData);
        txtTooltitle = findViewById(R.id.txtTooltitle);
        banner_container_bottom = findViewById(R.id.banner_container_bottom);
        banner_container_top = findViewById(R.id.banner_container_top);

        if (toolText != null)
            txtTooltitle.setText(toolText);
    }

}
