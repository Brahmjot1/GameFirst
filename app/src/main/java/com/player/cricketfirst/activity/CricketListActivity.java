package com.player.cricketfirst.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.cricketfirst.R;
import com.player.cricketfirst.adapter.CricketHomeListAdapter;
import com.player.cricketfirst.model.ResponseModel;
import com.player.cricketfirst.util.Utility;

public class CricketListActivity extends AppCompatActivity {

    ImageView ivBack;
    RecyclerView rvList;
    TextView txtNodata;
    ResponseModel responseMain;
    LinearLayout bannerBottom;
    Utility utility;
    private CricketHomeListAdapter cricketHomeListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket_list);
        findView();

        ivBack.setOnClickListener(v -> finish());

        responseMain = new Gson().fromJson(Utility.getAsync(CricketListActivity.this, "HomeData"), ResponseModel.class);
        setHomeData(CricketListActivity.this, responseMain);
    }

    private void setHomeData(Activity mActivity, ResponseModel responseMain) {

        if (Utility.getIsLovinAdShow(CricketListActivity.this).matches("2")) {
            utility.showLovinInter(CricketListActivity.this, responseMain.getAdFailUrl());
        }
        else if(Utility.getIsLovinAdShow(CricketListActivity.this).matches("1"))
        {
            utility.showGoogleInter(CricketListActivity.this);
        }

        if (responseMain.getCricketList() != null && responseMain.getCricketList().size() > 0) {
            rvList.setVisibility(View.VISIBLE);
            txtNodata.setVisibility(View.GONE);
            rvList.setLayoutManager(new LinearLayoutManager(CricketListActivity.this, RecyclerView.VERTICAL, false));
            rvList.setNestedScrollingEnabled(false);
            cricketHomeListAdapter = new CricketHomeListAdapter(CricketListActivity.this, responseMain.getCricketList());
            rvList.setAdapter(cricketHomeListAdapter);
        } else {
            rvList.setVisibility(View.GONE);
            txtNodata.setVisibility(View.VISIBLE);
        }

        if (responseMain.getBottemBanner() != null) {
            if (responseMain.getBottemBanner().getType() != null) {
                if (responseMain.getBottemBanner().getType().matches("1")) {
                    utility.showGoogleBanner(CricketListActivity.this,bannerBottom);
                } else if (responseMain.getBottemBanner().getType().matches("2")) {
                    utility.showLovinBanner(CricketListActivity.this, bannerBottom);
                } else if (responseMain.getBottemBanner().getType().matches("3")) {
                    utility.LoadBannerAd(CricketListActivity.this, bannerBottom, responseMain.getBottemBanner());
                }
            }
        }
    }

    private void findView() {
        utility = new Utility();
        ivBack=findViewById(R.id.ivBack);
        rvList=findViewById(R.id.rvCricketList);
        txtNodata=findViewById(R.id.txtNofound);
        bannerBottom=findViewById(R.id.banner_container);
    }
}