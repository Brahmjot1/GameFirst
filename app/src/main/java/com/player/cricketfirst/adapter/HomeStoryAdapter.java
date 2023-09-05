package com.player.cricketfirst.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.player.cricketfirst.R;
import com.player.cricketfirst.model.CategoryModel;
import com.player.cricketfirst.util.Utility;

import java.util.ArrayList;


public class HomeStoryAdapter extends RecyclerView.Adapter<HomeStoryAdapter.ViewHolder> {

    private Activity activity;
    private LayoutInflater mLayoutInflater;
    ArrayList<CategoryModel> data;
    Utility utility;

    public HomeStoryAdapter(final Activity context, ArrayList<CategoryModel> data) {
        activity = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.data = data;
        utility = new Utility();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_homestoryicon, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.probr.setVisibility(View.VISIBLE);

        utility.showImageLotteGIF(activity,data.get(position).getImage(), holder.imageBanner, holder.image_gif, holder.probr);

        holder.txtTitleStory.setText(data.get(position).getTitle());

        holder.relStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=data.get(position).getUrl() ;
                Utility.openUrl(activity,url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageBanner;
        private ProgressBar probr;
        private TextView  txtTitleStory;
        private ImageView image_gif;
        private RelativeLayout relStory;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBanner = itemView.findViewById(R.id.ivBanner);
            probr = itemView.findViewById(R.id.probr);
            txtTitleStory = itemView.findViewById(R.id.txtTitleStory);
            image_gif = itemView.findViewById(R.id.ivGIF);
            relStory = itemView.findViewById(R.id.relStory);

        }
    }
}