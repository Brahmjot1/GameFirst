package fantasy.livematch.firstscore.activity;

import static fantasy.livematch.firstscore.activity.MainActivity.responseHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import fantasy.livematch.firstscore.R;
import fantasy.livematch.firstscore.adapter.OfferListAdapter;
import fantasy.livematch.firstscore.util.Utility;

public class OfferlistActivity extends AppCompatActivity {

    ImageView ivBack;
    RecyclerView rvOfferlist;
    CardView cardNative;
    FrameLayout adLayoutLovin;
    TextView txtNofound;
    Utility utility;
    OfferListAdapter offerListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offerlist);
        findView();
        ivBack.setOnClickListener(v -> finish());

        setAdapter();
    }

    private void setAdapter() {

        if (responseHome!=null)
        {
            if (responseHome.getOfferList()!=null || responseHome.getOfferList().size()>0)
            {
                txtNofound.setVisibility(View.GONE);
                offerListAdapter=new OfferListAdapter(OfferlistActivity.this,responseHome.getOfferList());
                rvOfferlist.setAdapter(offerListAdapter);
                rvOfferlist.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
            }
            else
            {
                txtNofound.setVisibility(View.VISIBLE);
            }

        }
    }

    private void findView() {
        ivBack=findViewById(R.id.ivBack);
        rvOfferlist=findViewById(R.id.rvOfferList);
        cardNative=findViewById(R.id.cardNative);
        adLayoutLovin=findViewById(R.id.adLayoutLovin);
        txtNofound=findViewById(R.id.txtNofound);
        utility = new Utility();

        if (Utility.getIsLovinAdShow(OfferlistActivity.this).matches("2")) {
            adLayoutLovin.setVisibility(View.VISIBLE);
            cardNative.setVisibility(View.VISIBLE);
            utility.showLovinNative(OfferlistActivity.this, Utility.getLovinNative(OfferlistActivity.this), adLayoutLovin);
        }
        else if (Utility.getIsLovinAdShow(OfferlistActivity.this).matches("1")) {
            cardNative.setVisibility(View.VISIBLE);
            adLayoutLovin.setVisibility(View.VISIBLE);
            utility.showGoogleNative(OfferlistActivity.this, adLayoutLovin);
        }
    }
}