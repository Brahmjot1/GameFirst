package fantasy.livematch.firstscore.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import fantasy.livematch.firstscore.R;
import fantasy.livematch.firstscore.activity.MatchDetailsActivity;
import fantasy.livematch.firstscore.model.CategoryModel;
import fantasy.livematch.firstscore.util.Global_App;
import fantasy.livematch.firstscore.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CricketHomeListAdapter extends RecyclerView.Adapter<CricketHomeListAdapter.MyViewHolder> {

    private List<CategoryModel> data;
    private Activity activity;

    public CricketHomeListAdapter(Activity activity, List<CategoryModel> countryList) {
        this.activity = activity;
        this.data = countryList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CategoryModel current = data.get(position);
        if (current.getTeam1Image() != null && !current.getTeam1Image().isEmpty()) {
            if (current.getTeam1Image().contains(".gif")) {
                Glide.with(activity)
                        .load(current.getTeam1Image())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .into(holder.imgTeam1);
            } else {
                Picasso.get().load(current.getTeam1Image()).into(holder.imgTeam1);
            }
        }
        if (current.getTeam2Image() != null && !current.getTeam2Image().isEmpty()) {
            if (current.getTeam2Image().contains(".gif")) {
                Glide.with(activity)
                        .load(current.getTeam2Image())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .into(holder.imgTeam2);
            } else {
                Picasso.get().load(current.getTeam2Image()).into(holder.imgTeam2);
            }
        }
        if (!Utility.isStringNullOrEmpty(current.getTeam1FullName())) {
            holder.txtTeam1Name.setText(current.getTeam1FullName());
        }
        else if (!Utility.isStringNullOrEmpty(current.getTeam1Name())) {
            holder.txtTeam1Name.setText(current.getTeam1Name());
        }

        if (!Utility.isStringNullOrEmpty(current.getTeam1Score())) {
            holder.txtTeam1Score.setVisibility(View.VISIBLE);
            holder.txtTeam1Score.setText(current.getTeam1Score());
        }

        if (!Utility.isStringNullOrEmpty(current.getTeam2Score())) {
            holder.txtTeam2Score.setVisibility(View.VISIBLE);
            holder.txtTeam2Score.setText(current.getTeam2Score());
        }

        if (!Utility.isStringNullOrEmpty(current.getOdd1())) {
            holder.txtOdd1.setVisibility(View.VISIBLE);
            holder.txtOdd1.setText(current.getOdd1());
        }
        else
        {
            holder.txtOdd1.setVisibility(View.GONE);
        }

        if (!Utility.isStringNullOrEmpty(current.getOdd2())) {
            holder.txtOdd2.setVisibility(View.VISIBLE);
            holder.txtOdd2.setText(current.getOdd2());
        }
        else
        {
            holder.txtOdd2.setVisibility(View.GONE);
        }

        if (!Utility.isStringNullOrEmpty(current.getTeam2FullName())) {
            holder.txtTeam2Name.setText(current.getTeam2FullName());
        }
        else if (!Utility.isStringNullOrEmpty(current.getTeam2Name())) {
            holder.txtTeam2Name.setText(current.getTeam2Name());
        }

        holder.txtSerisName.setText(current.getSeriesName());
        if (holder.timer != null) {
            holder.timer.cancel();
        }
        holder.txtDate.setText(Utility.changeDateFormat("yyyy-MM-dd HH:mm:ss", "MMM dd | hh:mm aa", current.getStartDate()));
        if (current.getNote() != null && current.getNote().length() > 0) {
            holder.txtNote.setVisibility(View.VISIBLE);
            holder.txtNote.setText(current.getNote());

            try {
                if (current.getNoteColor() != null && current.getNoteColor().length() > 0) {

                    holder.txtNote.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(current.getNoteColor())));
                    //  holder.txtNote.setTextColor(Color.parseColor(current.getNoteColor()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            holder.txtNote.setVisibility(View.GONE);
        }
        if (!Utility.isStringNullOrEmpty(current.getImgUrl())) {
            holder.imgBanner.setVisibility(View.VISIBLE);
            holder.loutMainBanner.setVisibility(View.VISIBLE);
            if (current.getImgUrl().contains(".gif")) {
                Glide.with(activity)
                        .load(current.getImgUrl())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .into(holder.imgBanner);
            } else {
                Picasso.get().load(current.getImgUrl()).into(holder.imgBanner);
            }
            holder.imgBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Utility.isStringNullOrEmpty(current.getClickUrl())) {
                        Uri uri = Uri.parse(current.getClickUrl());
                        Utility.openUrl(view.getContext(), current.getClickUrl());
                       /* Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            activity.startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }*/
                    }

                }
            });

        } else {
            holder.imgBanner.setVisibility(View.GONE);
            holder.loutMainBanner.setVisibility(View.GONE);
        }
        if (!Utility.isStringNullOrEmpty(current.getIsOnlyImg()) && current.getIsOnlyImg().equals("1")) {
            holder.loutMain.setVisibility(View.GONE);
        } else {
            holder.loutMain.setVisibility(View.VISIBLE);
        }


        if (current.getLable() != null) {
            holder.txtLabel.setText(current.getLable());
            holder.txtLabel.setVisibility(View.VISIBLE);
        } else {
            holder.txtLabel.setVisibility(View.GONE);
        }

        holder.loutMain.setOnClickListener(v -> {
            if (current.getIsShowDetails() != null && current.getIsShowDetails().equals("1")) {
                Intent in = new Intent(activity, MatchDetailsActivity.class);
                in.putExtra("matchID", current.getId());
                in.putExtra("team1Name", current.getTeam1Name());
                in.putExtra("team2Name", current.getTeam2Name());
                activity.startActivity(in);
            } else {
                Utility.Notify(activity, Global_App.APPNAME, "This match will open soon. stay tuned!");
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_cricket, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView loutMain;
        private ImageView imgTeam1;
        private TextView txtTeam1Name;
        private TextView txtDate, txtNote, txtLabel;
        CardView loutMainBanner;
        private ImageView imgTeam2;
        private ImageView imgBanner;
        private TextView txtTeam2Name, txtSerisName,txtOdd1,txtOdd2,txtTeam1Score,txtTeam2Score;
        private CountDownTimer timer;
        LinearLayout lObb;
        MyViewHolder(View view) {
            super(view);
            loutMain = view.findViewById(R.id.loutMain);
            imgTeam1 = view.findViewById(R.id.imgTeam1);
            txtTeam1Name = view.findViewById(R.id.txtTeam1Name);
            txtLabel = view.findViewById(R.id.txtLabel);
            txtDate = view.findViewById(R.id.txtDate);
            imgTeam2 = view.findViewById(R.id.imgTeam2);
            imgBanner = view.findViewById(R.id.imgBanner);
            loutMainBanner = view.findViewById(R.id.loutMainBanner);
            txtTeam2Name = view.findViewById(R.id.txtTeam2Name);
            txtNote = view.findViewById(R.id.txtNote);
            txtSerisName = view.findViewById(R.id.txtSerisName);
            lObb = view.findViewById(R.id.lObb);
            txtOdd1 = view.findViewById(R.id.txtOdd1);
            txtOdd2 = view.findViewById(R.id.txtOdd2);
            txtTeam1Score = view.findViewById(R.id.txtTeam1Score);
            txtTeam2Score = view.findViewById(R.id.txtTeam2Score);
        }
    }


}
