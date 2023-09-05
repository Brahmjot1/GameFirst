package com.player.cricketfirst.adapter;

import static com.player.cricketfirst.util.Utility.isStringNullOrEmpty;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.player.cricketfirst.R;
import com.player.cricketfirst.model.CategoryModel;
import com.player.cricketfirst.util.Utility;

import java.util.ArrayList;

public class ApplistAdapter extends RecyclerView.Adapter<ApplistAdapter.MyViewholder> {
    Activity mActivity;
    ArrayList<CategoryModel> appsList;

    public ApplistAdapter(Activity mActivity, ArrayList<CategoryModel> appsList) {
        this.mActivity = mActivity;
        this.appsList = appsList;

    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_app_list, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        holder.txtAppName.setText(appsList.get(position).getTitle());

        Glide.with(mActivity)
                .load(appsList.get(position).getImage())
                .into(holder.ivIcon);

        if (!isStringNullOrEmpty(appsList.get(position).getLable())) {
            holder.txtLable.setVisibility(View.VISIBLE);
            holder.txtLable.setText(appsList.get(position).getLable());
        } else {
            holder.txtLable.setVisibility(View.GONE);
        }

        holder.cardApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = appsList.get(position).getUrl();
                Utility.openUrl(mActivity,url);
            }
        });

    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        LinearLayout cardApps;
        TextView txtAppName, txtLable;
        ImageView ivIcon;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            txtAppName = itemView.findViewById(R.id.txtAppName);
            txtLable = itemView.findViewById(R.id.txtLable);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            cardApps = itemView.findViewById(R.id.cardApps);
        }
    }
}
