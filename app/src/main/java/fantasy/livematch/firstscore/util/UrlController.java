package fantasy.livematch.firstscore.util;

import static fantasy.livematch.firstscore.util.Utility.isStringNullOrEmpty;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import fantasy.livematch.firstscore.R;
import fantasy.livematch.firstscore.model.CategoryModel;

import java.util.ArrayList;

public class UrlController {

    Activity mActivity;
    public static String STATUS_ERROR = "0";
    public static String STATUS_SUCCESS = "1";
    public static String APPNAME = "Game First";
    public static String msg_Service_Error = "Oops! This service is taking too much time to respond. please check your internet connection & try again.";
    public Dialog dialogLoader, dialogMiniAds;

    public UrlController(Activity activity) {
        mActivity = activity;
        dialogLoader = new Dialog(activity, R.style.UploadDialog);
        dialogLoader.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLoader.setCancelable(true);
        dialogLoader.setCanceledOnTouchOutside(true);
        dialogLoader.setContentView(R.layout.dialog_loader);

        dialogMiniAds = new Dialog(activity, R.style.UploadDialog);
        dialogMiniAds.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMiniAds.setCancelable(false);
        dialogMiniAds.setCanceledOnTouchOutside(false);
        dialogMiniAds.setContentView(R.layout.dialog_mini_ads);
    }

    public void showAdLoader() {

        if (dialogLoader != null) {
            dialogLoader.show();
        }
    }

    public void dismissADLoader() {
        if (dialogLoader != null && dialogLoader.isShowing()) {
            dialogLoader.dismiss();
        }
    }

    public void showMiniAdsDialog(Activity activity, CategoryModel categoryModel) {
        RelativeLayout relMiniAds = dialogMiniAds.findViewById(R.id.relMiniAds);
        ImageView ivCloseMiniAds = dialogMiniAds.findViewById(R.id.ivCloseMiniAds);
        ProgressBar probrBanner = dialogMiniAds.findViewById(R.id.probrBanner);
        ImageView imgBannerMiniAds = dialogMiniAds.findViewById(R.id.imgBannerMiniAds);

        if (categoryModel != null) {

            Log.e("getIDArray--)", "" + Utility.getIDArray(activity));
            if (Utility.getIDArray(activity) == null) {
                Log.e("Contains-)", "First");
                ArrayList<String> mList = new ArrayList<>();
                mList.add(categoryModel.getId());
                Utility.setIDArray(activity, mList);
                if (!isStringNullOrEmpty(categoryModel.getImage())) {
                    probrBanner.setVisibility(View.VISIBLE);
                    imgBannerMiniAds.setVisibility(View.VISIBLE);
                    Glide.with(activity)
                            .load(categoryModel.getImage())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                                    probrBanner.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(imgBannerMiniAds);

                } else {
                    imgBannerMiniAds.setVisibility(View.GONE);
                }

                relMiniAds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialogMiniAds != null) {
                            dialogMiniAds.dismiss();
                        }
                        Utility.Redirect(activity, categoryModel);
                    }
                });

                ivCloseMiniAds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialogMiniAds != null) {
                            dialogMiniAds.dismiss();
                        }
                    }
                });

                if (dialogMiniAds != null) {
                    dialogMiniAds.show();
                }
            } else if (!Utility.getIDArray(activity).contains(categoryModel.getId())) {
                Log.e("Contains-)", "YES");

                ArrayList<String> mList = Utility.getIDArray(activity);
                mList.add(categoryModel.getId());
                Utility.setIDArray(activity, mList);
                if (!isStringNullOrEmpty(categoryModel.getImage())) {
                    probrBanner.setVisibility(View.VISIBLE);
                    imgBannerMiniAds.setVisibility(View.VISIBLE);
                    Glide.with(activity)
                            .load(categoryModel.getImage())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                                    probrBanner.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(imgBannerMiniAds);

                } else {
                    imgBannerMiniAds.setVisibility(View.GONE);
                }

                relMiniAds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialogMiniAds != null) {
                            dialogMiniAds.dismiss();
                        }
                        Utility.Redirect(activity, categoryModel);
                    }
                });

                ivCloseMiniAds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialogMiniAds != null) {
                            dialogMiniAds.dismiss();
                        }
                    }
                });

                if (dialogMiniAds != null) {
                    dialogMiniAds.show();
                }
            } else {
                Log.e("Contains-)", "NOT");
            }
        }
    }
}
