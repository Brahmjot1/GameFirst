package fantasy.livematch.firstscore.activity;

import static fantasy.livematch.firstscore.activity.MatchDetailsActivity.ResopnseMain;
import static fantasy.livematch.firstscore.util.Utility.isStringNullOrEmpty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fantasy.livematch.firstscore.R;
import fantasy.livematch.firstscore.adapter.TeamPreviewAdapter;
import fantasy.livematch.firstscore.model.ResponseModel;
import fantasy.livematch.firstscore.util.UrlController;
import fantasy.livematch.firstscore.util.Utility;

public class MatchPreviewActivity extends AppCompatActivity {

    private RecyclerView rcTeamList;
    private TextView txtNoData;
    TeamPreviewAdapter teamPreviewAdapter;
    ImageView ivBack;
    Utility utility;
    TextView txtNote;
    private LinearLayout banner_container_bottom,banner_container_top;
    UrlController urlController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_preview);

        initView();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {

        ivBack=findViewById(R.id.ivBack);
        rcTeamList=findViewById(R.id.rcTeamList);
        txtNote=findViewById(R.id.txtNote);
        txtNoData=findViewById(R.id.txtNoData);
        utility=new Utility();
        banner_container_bottom = findViewById(R.id.banner_container_bottom);
        banner_container_top = findViewById(R.id.banner_container_top);

        utility = new Utility();
        urlController = new UrlController(MatchPreviewActivity.this);
        if (ResopnseMain != null) {
            setData(ResopnseMain);
        }
    }

    public void setData(ResponseModel responseModel) {

        if (responseModel.getTeampreview()!=null)
        {
            if (!isStringNullOrEmpty(responseModel.getTeampreview().getIsShowIndstrial())) {
                 if (responseModel.getTeampreview().getIsShowIndstrial().matches("2")) {
                    utility.showLovinInter(MatchPreviewActivity.this,responseModel.getTeampreview().getAdFailUrl());
                }
                 else if(responseModel.getTeampreview().getIsShowIndstrial().matches("1"))
                 {
                     utility.showGoogleInter(MatchPreviewActivity.this);
                 }
            }
            if (responseModel!=null)
            {
                if (responseModel.getTeampreview().getTopAds()!=null)
                {
                    if (responseModel.getTeampreview().getTopAds().getType()!=null)
                    {
                        if (responseModel.getTeampreview().getTopAds().getType().matches("1"))
                        {
                            utility.showGoogleBanner(MatchPreviewActivity.this, banner_container_top);
                        }
                        else if (responseModel.getTeampreview().getTopAds().getType().matches("2"))
                        {
                            utility.showLovinBanner(MatchPreviewActivity.this, banner_container_top);
                        }
                        else  if (responseModel.getTeampreview().getTopAds().getType().matches("3"))
                        {
                            utility.LoadBannerAd(MatchPreviewActivity.this, banner_container_top, responseModel.getTeampreview().getTopAds());
                        }
                    }
                }


                if (responseModel.getTeampreview().getBottemBanner()!=null)
                {
                    if (responseModel.getTeampreview().getBottemBanner().getType()!=null)
                    {
                        if (responseModel.getTeampreview().getBottemBanner().getType().matches("1"))
                        {
                            utility.showGoogleBanner(MatchPreviewActivity.this, banner_container_bottom);
                        }
                        else if (responseModel.getTeampreview().getBottemBanner().getType().matches("2"))
                        {
                            utility.showLovinBanner(MatchPreviewActivity.this, banner_container_bottom);
                        }
                        else  if (responseModel.getTeampreview().getBottemBanner().getType().matches("3"))
                        {
                            utility.LoadBannerAd(MatchPreviewActivity.this, banner_container_bottom, responseModel.getTeampreview().getBottemBanner());
                        }
                    }

                }
            }
        }

        if (!isStringNullOrEmpty(responseModel.getTeamNote()))
        {
            txtNote.setVisibility(View.VISIBLE);
            txtNote.setText(responseModel.getTeamNote());
        }
        else
        {
            txtNote.setVisibility(View.GONE);
        }
        if (responseModel.getTeamList() != null && responseModel.getTeamList().size() > 0) {
            rcTeamList.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            rcTeamList.setLayoutManager(new LinearLayoutManager(MatchPreviewActivity.this, RecyclerView.VERTICAL, false));
            teamPreviewAdapter = new TeamPreviewAdapter(MatchPreviewActivity.this,responseModel.getTeamList());
            rcTeamList.setAdapter(teamPreviewAdapter);
        } else {
            rcTeamList.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
            txtNoData.setText("To Be Updated!");
        }

        if (responseModel.getMiniAds() != null) {
            urlController.showMiniAdsDialog(MatchPreviewActivity.this, responseModel.getMiniAds());
        }
    }
}