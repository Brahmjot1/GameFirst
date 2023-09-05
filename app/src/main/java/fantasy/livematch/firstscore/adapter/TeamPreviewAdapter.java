package fantasy.livematch.firstscore.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import fantasy.livematch.firstscore.R;
import fantasy.livematch.firstscore.model.CategoryModel;

import java.util.List;


public class TeamPreviewAdapter extends RecyclerView.Adapter<TeamPreviewAdapter.MyViewHolder> {

    private List<CategoryModel> data;
    private Activity activity;


    public TeamPreviewAdapter(Activity activity, List<CategoryModel> countryList) {
        this.activity = activity;
        this.data = countryList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CategoryModel current = data.get(position);


        holder.txtPredication.setText("Prediction : "+String.valueOf(position+1));
        Log.e("Which--)", "" + current.getType());
        if (current.getTeamType() != null && current.getTeamType().length() > 0) {
            holder.txtTitle.setVisibility(View.VISIBLE);
            holder.txtTitle.setText(current.getTeamType());
        } else {
            holder.txtTitle.setVisibility(View.GONE);
        }

        if (current.getImage() != null && current.getImage().length() > 0) {
            holder.probr.setVisibility(View.VISIBLE);
            Picasso.get().load(current.getImage()).into(holder.imgBanner, new Callback() {
                @Override
                public void onSuccess() {
                    holder.probr.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    holder.probr.setVisibility(View.GONE);
                }
            });
        } else {
            holder.probr.setVisibility(View.GONE);
            holder.imgBanner.setImageResource(R.drawable.ic_comminsoon);
        }


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_teampreview, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle,txtPredication;
        private ImageView imgBanner;
        private ProgressBar probr;
        LinearLayout lMain;

        MyViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtPredication = view.findViewById(R.id.txtPredication);
            imgBanner = view.findViewById(R.id.imgBanner);
            probr = view.findViewById(R.id.probr);
            lMain = view.findViewById(R.id.lMain);

        }
    }
}

