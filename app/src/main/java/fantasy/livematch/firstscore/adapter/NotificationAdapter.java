package fantasy.livematch.firstscore.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import fantasy.livematch.firstscore.R;
import fantasy.livematch.firstscore.model.CategoryModel;
import fantasy.livematch.firstscore.util.Utility;

import java.util.ArrayList;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Activity activity;
    ArrayList<CategoryModel> data;

    public NotificationAdapter(final Activity context, ArrayList<CategoryModel> data) {
        activity = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (data.get(position).getTitle() != null) {
            holder.txtTitle.setText(data.get(position).getTitle());
        }
        if (data.get(position).getDescription() != null) {
            holder.txtDesc.setText(data.get(position).getDescription());
        }

        if (!Utility.isStringNullOrEmpty(data.get(position).getIcon())) {

            holder.cardIcon.setVisibility(View.VISIBLE);
            holder.ivIcon.setVisibility(View.VISIBLE);
            Glide.with(activity)
                    .load(data.get(position).getIcon())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.ivIcon);

        }
        else
        {
            holder.cardIcon.setVisibility(View.GONE);
        }

        holder.relTask.setOnClickListener(v -> {

            Utility.openUrl(activity, data.get(position).getUrl());

        });
        if (!Utility.isStringNullOrEmpty(data.get(position).getImage() )) {
            holder.relBgImage.setVisibility(View.VISIBLE);
            if (data.get(position).getImage().contains(".gif")) {
                holder.ivGIF.setVisibility(View.VISIBLE);
                holder.ivBanner.setVisibility(View.GONE);

                Glide.with(activity)
                        .load(data.get(position).getImage())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(holder.ivGIF);
            } else {
                holder.ivGIF.setVisibility(View.GONE);
                holder.ivBanner.setVisibility(View.VISIBLE);
                Glide.with(activity)
                        .load(data.get(position).getImage())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(holder.ivBanner);
            }
        }
        else
        {
            holder.relBgImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivIcon, ivGIF;
        private ImageView ivBanner;
        private TextView txtTitle, txtDesc;
        private RelativeLayout relTask,relBgImage;
        private CardView cardIcon;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            ivGIF = itemView.findViewById(R.id.ivGIF);
            cardIcon = itemView.findViewById(R.id.cardIcon);
            ivBanner = itemView.findViewById(R.id.ivBanner);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            relBgImage = itemView.findViewById(R.id.relBgImage);
            relTask = itemView.findViewById(R.id.relTask);
            txtDesc = itemView.findViewById(R.id.txtDesc);
        }
    }
}