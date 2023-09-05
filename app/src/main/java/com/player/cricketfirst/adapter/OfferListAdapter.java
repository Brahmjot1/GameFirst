package com.player.cricketfirst.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.player.cricketfirst.R;
import com.player.cricketfirst.model.CategoryModel;
import com.player.cricketfirst.util.Utility;

import java.util.ArrayList;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.MyViewholder> {
    Activity mActivity;
    ArrayList<CategoryModel> appsList;

    public OfferListAdapter(Activity mActivity, ArrayList<CategoryModel> appsList) {
        this.mActivity = mActivity;
        this.appsList = appsList;

    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_offer_list, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        if (!Utility.isStringNullOrEmpty(appsList.get(position).getTitle()))
        {
            holder.txtTitle.setText(appsList.get(position).getTitle());
        }

        if (!Utility.isStringNullOrEmpty(appsList.get(position).getDescription()))
        {
            holder.txtDesc.setText(appsList.get(position).getDescription());
        }

        Glide.with(mActivity)
                .load(appsList.get(position).getImage())
                .into(holder.ivBgBanner);
        if (!Utility.isStringNullOrEmpty(appsList.get(position).getBtnName()))
        {
            holder.txtBtnName.setText(appsList.get(position).getBtnName());
        }


        holder.cardOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = appsList.get(position).getUrl();
                if (url != null) {
                    Utility.openUrl(mActivity, url);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        CardView cardOffer;
        TextView txtTitle, txtDesc,txtBtnName;
        ImageView ivBgBanner;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            ivBgBanner = itemView.findViewById(R.id.ivBgBanner);
            txtBtnName = itemView.findViewById(R.id.txtBtnName);
            cardOffer = itemView.findViewById(R.id.cardOffer);
        }
    }
}
