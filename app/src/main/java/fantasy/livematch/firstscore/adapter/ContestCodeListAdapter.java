package fantasy.livematch.firstscore.adapter;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import fantasy.livematch.firstscore.R;
import fantasy.livematch.firstscore.model.CategoryModel;
import fantasy.livematch.firstscore.util.Utility;
import fantasy.livematch.firstscore.util.font.MediumTextView;

import java.util.List;


public class ContestCodeListAdapter extends RecyclerView.Adapter<ContestCodeListAdapter.MyViewHolder> {

    private List<CategoryModel> data;
    private Activity activity;

    public ContestCodeListAdapter(Activity activity, List<CategoryModel> countryList) {
        this.activity = activity;
        this.data = countryList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CategoryModel current = data.get(position);
        if (!Utility.isStringNullOrEmpty(current.getAppName())) {
            holder.txtAppName.setText(current.getAppName());
        }

        if (!Utility.isStringNullOrEmpty(current.getContestCode())) {
            holder.txtContestCode.setText(current.getContestCode());
        }

        if (!Utility.isStringNullOrEmpty(current.getWinAmount())) {
            holder.WinAMount.setText("₹ " + current.getWinAmount());
        }
        if (!Utility.isStringNullOrEmpty(current.getContestSize())) {
            holder.txtSize.setText(current.getContestSize());
        }

        if (!Utility.isStringNullOrEmpty(current.getAmount())) {
            holder.joinAmount.setText("₹ " + current.getAmount());
        }

        if (!Utility.isStringNullOrEmpty(current.getIsMultipal())) {
            if (current.getIsMultipal().equals("1")) {
                holder.txtMultiJoin.setText("YES");
            } else {
                holder.txtMultiJoin.setText("NO");
            }
        }

        holder.imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager)activity.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("code", current.getContestCode());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(activity, "Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contestcodelist, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private MediumTextView txtAppName;
        private MediumTextView joinAmount;
        private MediumTextView WinAMount;
        private MediumTextView txtContestCode;
        private MediumTextView txtMultiJoin;
        private MediumTextView txtSize;
        private ImageView imgCopy;

        MyViewHolder(View view) {
            super(view);
            txtAppName = view.findViewById(R.id.txtAppName);
            joinAmount = view.findViewById(R.id.joinAmount);
            txtMultiJoin = view.findViewById(R.id.txtMultiJoin);
            WinAMount = view.findViewById(R.id.WinAMount);
            txtSize = view.findViewById(R.id.txtSize);
            txtContestCode = view.findViewById(R.id.txtContestCode);
            imgCopy = view.findViewById(R.id.imgCopy);
        }
    }
}
