package fantasy.livematch.firstscore.adapter;

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
import fantasy.livematch.firstscore.R;
import fantasy.livematch.firstscore.model.CategoryModel;
import fantasy.livematch.firstscore.util.Utility;

import java.util.ArrayList;

public class MoreAppsAdapter extends RecyclerView.Adapter<MoreAppsAdapter.MyViewholder> {
    Activity mActivity;
    ArrayList<CategoryModel> appsList;

    public MoreAppsAdapter(Activity mActivity, ArrayList<CategoryModel> appsList) {
        this.mActivity = mActivity;
        this.appsList = appsList;

    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_more_apps, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        holder.txtAppName.setText(appsList.get(position).getTitle());

        Glide.with(mActivity)
                .load(appsList.get(position).getBgImage())
                .into(holder.ivBgBanner);
        Glide.with(mActivity)
                .load(appsList.get(position).getIcone())
                .into(holder.ivSmallIcon);
        holder.txtInstall.setText(appsList.get(position).getBtnName());

        holder.cardApps.setOnClickListener(new View.OnClickListener() {
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
        CardView cardApps;
        TextView txtAppName, txtInstall;
        ImageView ivBgBanner, ivSmallIcon;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            txtAppName = itemView.findViewById(R.id.txtAppName);
            txtInstall = itemView.findViewById(R.id.txtInstall);
            ivBgBanner = itemView.findViewById(R.id.ivBgBanner);
            ivSmallIcon = itemView.findViewById(R.id.ivSmallIcon);
            cardApps = itemView.findViewById(R.id.cardApps);
        }
    }
}
