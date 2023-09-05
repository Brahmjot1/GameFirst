package com.player.cricketfirst.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.player.cricketfirst.R;
import com.player.cricketfirst.util.Utility;


public class WebActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ProgressBar probrMain;
    private WebView webView;
    String url = "";
    String title = "";
    private LinearLayout banner_container;
    Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        initView();

    }

    public void setHeaderView(String title) {
        View v = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
        TextView action_bar_title = v.findViewById(R.id.action_bar_title);
        action_bar_title.setText(title);
        ImageView imgNavigation = v.findViewById(R.id.imgNavigation);
        ImageView ivWebMain = v.findViewById(R.id.ivWebMain);
        LinearLayout ivWebPrime = v.findViewById(R.id.ivWebPrime);

        ivWebMain.setVisibility(View.GONE);
        ivWebPrime.setVisibility(View.GONE);
        imgNavigation.setImageResource(R.drawable.ic_back);
        getSupportActionBar().setCustomView(v);
        imgNavigation.setOnClickListener(view -> finish());
    }

    private void initView() {
        utility = new Utility();
        if (Utility.getIsLovinAdShow(WebActivity.this).matches("2"))
        {
            utility.showLovinInter(WebActivity.this,"");
        }
        else if(Utility.getIsLovinAdShow(WebActivity.this).matches("1"))
        {
            utility.showGoogleInter(WebActivity.this);
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView(title);
        probrMain = findViewById(R.id.probrMain);
        banner_container = findViewById(R.id.banner_container);
        probrMain.setVisibility(View.VISIBLE);
        webView = findViewById(R.id.webView);
        /*adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);*/
        // Utility.LoadBannerAd(WebActivity.this, banner_container);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadWithOverviewMode(false);
        webView.setScrollContainer(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                probrMain.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                probrMain.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                probrMain.setVisibility(View.VISIBLE);
                Toast.makeText(WebActivity.this, "Error:" + description, Toast.LENGTH_SHORT).show();

            }
        });
        webView.loadUrl(url);

        webView.setOnLongClickListener(v -> true);
        webView.setLongClickable(false);
    }


}