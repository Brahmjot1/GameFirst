package fantasy.livematch.firstscore.adapter;

import static fantasy.livematch.firstscore.util.Utility.isStringNullOrEmpty;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import fantasy.livematch.firstscore.R;
import fantasy.livematch.firstscore.model.CategoryModel;
import fantasy.livematch.firstscore.util.Utility;

import java.util.ArrayList;

public class MenulistAdapter extends RecyclerView.Adapter<MenulistAdapter.MyViewholder> {
    Activity mActivity;
    ArrayList<CategoryModel> appsList;

    public MenulistAdapter(Activity mActivity, ArrayList<CategoryModel> appsList) {
        this.mActivity = mActivity;
        this.appsList = appsList;

    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_drawer, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        holder.txtTitle.setText(appsList.get(position).getTitle());

        Glide.with(mActivity)
                .load(appsList.get(position).getIcone())
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

        if (!isStringNullOrEmpty(appsList.get(position).getLable())) {
            holder.txtLable.setVisibility(View.VISIBLE);
            holder.llabel.setVisibility(View.VISIBLE);
            holder.txtLable.setText(appsList.get(position).getLable());
        } else {
            holder.txtLable.setVisibility(View.GONE);
            holder.llabel.setVisibility(View.GONE);
        }

        if (!isStringNullOrEmpty(appsList.get(position).getIsAds())) {

            if (appsList.get(position).getIsAds().matches("1"))
            {
                holder.lAds.setVisibility(View.VISIBLE);
            }
            else{
                holder.lAds.setVisibility(View.GONE);
            }
        } else {
            holder.lAds.setVisibility(View.GONE);
        }

        Log.e("isblick--)",""+appsList.get(position).getIsblink());
        if (!isStringNullOrEmpty(appsList.get(position).getIsblink())) {

            if (appsList.get(position).getIsblink().matches("1"))
            {
                holder.relTask.setBackground(mActivity.getDrawable(R.drawable.bg_icon_square));
            }
            else{
                holder.relTask.setBackground(null);
            }
        } else {
            holder.relTask.setBackground(null);
        }

        if (!isStringNullOrEmpty(appsList.get(position).getIsblink())) {
            if (appsList.get(position).getIsblink().matches("1")) {
                holder.relTask.setVisibility(View.VISIBLE);
                Animation anim = new AlphaAnimation(0.2f, 1.0f);
                anim.setDuration(400); //You can manage the blinking time with this parameter
                anim.setStartOffset(20);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(Animation.INFINITE);
                holder.relTask.startAnimation(anim);
            }
        }

        holder.relTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = appsList.get(position).getUrl();
                if (url != null) {
                    Utility.openUrl(mActivity,url);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
       RelativeLayout relTask;
       ImageView ivIcon;
       TextView txtTitle,txtLable;
       LinearLayout lAds,llabel;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            relTask=itemView.findViewById(R.id.relTask);
            ivIcon=itemView.findViewById(R.id.ivIcon);
            txtTitle=itemView.findViewById(R.id.txtTitle);
            txtLable=itemView.findViewById(R.id.txtLable);
            lAds=itemView.findViewById(R.id.lAds);
            llabel=itemView.findViewById(R.id.llabel);
        }
    }
}
