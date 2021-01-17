package com.pinterest.tattoo.realistic.tatuajes2.ui.unlock;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pinterest.tattoo.realistic.tatuajes2.R;
import com.pinterest.tattoo.realistic.tatuajes2.utils.SharedPrefsUtils;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.oneadx.android.oneads.AdReward;

import java.util.ArrayList;
import java.util.List;

public class UnlockTattooActivity extends AppCompatActivity {
    private String TAG = "UnlockTattooActivity";
    RecyclerView revUnlock;
    TextView txtNameUnlock;
    RelativeLayout relUnlock;
    ImageView imgClose;
    List<String> mlistUnlock;
    int name;
    LinearLayout lineUnlock;
    ImageView imgButtonUnlock;
    UnlockTattooAdapter unlockTattooAdapter;
    ProgressDialog progressDialog;
    //private RewardedAd rewardedAd;
    private AdReward adReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_unlock_tattoo);
        initView();
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        revUnlock = findViewById(R.id.revUnlock);
        imgClose = findViewById(R.id.btnCloseUnlock);
        relUnlock = findViewById(R.id.relUnlock);
        txtNameUnlock = findViewById(R.id.txtNameUnlock);
        lineUnlock = findViewById(R.id.lineUnlock);
        imgButtonUnlock = findViewById(R.id.imgButtonUnlock);
        Intent intent = getIntent();
        name = intent.getIntExtra("name", 0);
        initData();
        initAds();
        if (name == 1) {
            txtNameUnlock.setText("New Year Pack");
            lineUnlock.setBackground(getResources().getDrawable(R.drawable.new_year_screen));
            imgButtonUnlock.setImageDrawable(getResources().getDrawable(R.drawable.new_year_button));
        } else if (name == 2) {
            txtNameUnlock.setText("Lunar New Year Pack");
            lineUnlock.setBackground(getResources().getDrawable(R.drawable.lunar_new_year_screen));
            imgButtonUnlock.setImageDrawable(getResources().getDrawable(R.drawable.lunar_button));
        } else if (name == 3) {
            txtNameUnlock.setText("New Arrivals");
            lineUnlock.setBackground(getResources().getDrawable(R.drawable.new_arrivals_screen));
            imgButtonUnlock.setImageDrawable(getResources().getDrawable(R.drawable.new_arrivals_button));
        } else if (name == 22) {
            txtNameUnlock.setText("Unlock New Tattoo Pack");
            lineUnlock.setBackground(getResources().getDrawable(R.drawable.new_year_screen));
            imgButtonUnlock.setImageDrawable(getResources().getDrawable(R.drawable.new_year_button));
        } else if (name == 33) {
            txtNameUnlock.setText("Unlock New Mini Pack");
            lineUnlock.setBackground(getResources().getDrawable(R.drawable.cute_pack_screen));
            imgButtonUnlock.setImageDrawable(getResources().getDrawable(R.drawable.cute_pack_button));
        } else if (name == 44) {
            txtNameUnlock.setText("Unlock New Tattoo Pack");
            lineUnlock.setBackground(getResources().getDrawable(R.drawable.pack_screen));
            imgButtonUnlock.setImageDrawable(getResources().getDrawable(R.drawable.pack_button
            ));
        } else if (name == 55) {
            txtNameUnlock.setText("Mid - Autumn Festival");
            lineUnlock.setBackground(getResources().getDrawable(R.drawable.mid_autumn_screen));
            imgButtonUnlock.setImageDrawable(getResources().getDrawable(R.drawable.mid_autumn_button));
        } else if (name == 66) {
            txtNameUnlock.setText("Unlock New Piercing Pack");
            lineUnlock.setBackground(getResources().getDrawable(R.drawable.piercing_screen));
            imgButtonUnlock.setImageDrawable(getResources().getDrawable(R.drawable.piercing_button));
        } else if (name == 77) {
            lineUnlock.setBackground(getResources().getDrawable(R.drawable.christmas_screen));
            imgButtonUnlock.setImageDrawable(getResources().getDrawable(R.drawable.christmas_button));
            txtNameUnlock.setText("Unlock New Christmas Pack");
        }
        revUnlock.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        revUnlock.setLayoutManager(gridLayoutManager);
        unlockTattooAdapter = new UnlockTattooAdapter(mlistUnlock);
        revUnlock.setAdapter(unlockTattooAdapter);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        relUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adReward.show(new AdReward.AdRewardListener() {
                    @Override
                    public void onRewardedAdOpened() {

                    }

                    @Override
                    public void onRewardedAdClosed() {
                        if (name == 1) {
                            SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("unlock", true);
                        } else if (name == 2) {
                            SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("unlocktwo", true);
                        } else if (name == 3) {
                            SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("unlockthree", true);
                        } else if (name == 22) {
                            SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("ulock2", true);
                        } else if (name == 33) {
                            SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("ulock3", true);
                        } else if (name == 44) {
                            SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("ulock4", true);
                        } else if (name == 55) {
                            SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("ulock5", true);
                        } else if (name == 77) {
                            SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("ulock7", true);
                        }
                        onBackPressed();
                    }

                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                    }

                    @Override
                    public void onRewardedAdFailedToShow() {

                    }
                });
//                if (rewardedAd.isLoaded()) {
//                    Activity activityContext = UnlockTattooActivity.this;
//                    RewardedAdCallback adCallback = new RewardedAdCallback() {
//                        @Override
//                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//                        }
//
//                        @Override
//                        public void onRewardedAdOpened() {
//                            super.onRewardedAdOpened();
//                        }
//
//                        @Override
//                        public void onRewardedAdClosed() {
//                            super.onRewardedAdClosed();
//                            if (name == 1) {
//                                SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("unlock", true);
//                            } else if (name == 2) {
//                                SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("unlocktwo", true);
//                            } else if (name == 3) {
//                                SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("unlockthree", true);
//                            } else if (name == 22) {
//                                SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("ulock2", true);
//                            } else if (name == 33) {
//                                SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("ulock3", true);
//                            } else if (name == 44) {
//                                SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("ulock4", true);
//                            } else if (name == 55) {
//                                SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("ulock5", true);
//                            } else if (name == 77) {
//                                SharedPrefsUtils.getInstance(getApplicationContext()).putBoolean("ulock7", true);
//                            }
//                            onBackPressed();
//                        }
//
//                        @Override
//                        public void onRewardedAdFailedToShow(int i) {
//                            super.onRewardedAdFailedToShow(i);
//                        }
//                    };
//                    rewardedAd.show(activityContext, adCallback);
//                } else {
//                    Log.e(TAG, "The rewarded ad wasn't loaded yet.");
//                }
            }
        });
    }

    private void initAds() {
        adReward = new AdReward(this);
        adReward.load();
        progressDialog.dismiss();
//        rewardedAd = new RewardedAd(this, Common.rewar_id_admob);
//        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
//            @Override
//            public void onRewardedAdLoaded() {
//                // Ad successfully loaded.
//                Log.e(TAG, "onRewardedAdLoaded: done" );
//                progressDialog.dismiss();
//
//            }
//
//            public void onRewardedAdFailedToLoad(int var1) {
//                Log.e(TAG, "onRewardedAdFailedToLoad: failed" );
//            }
//        };
//        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    private void initData() {
        mlistUnlock = new ArrayList<>();
        if (name == 1) {
            mlistUnlock.add("NewYear/NewYear_0.png");
            mlistUnlock.add("NewYear/NewYear_1.png");
            mlistUnlock.add("NewYear/NewYear_2.png");
            mlistUnlock.add("NewYear/NewYear_3.png");
            mlistUnlock.add("NewYear/NewYear_4.png");
            mlistUnlock.add("NewYear/NewYear_5.png");
            mlistUnlock.add("NewYear/NewYear_6.png");
            mlistUnlock.add("more.png");
        } else if (name == 2) {
            mlistUnlock.add("Lunar/Lunar_0.png");
            mlistUnlock.add("Lunar/Lunar_1.png");
            mlistUnlock.add("Lunar/Lunar_2.png");
            mlistUnlock.add("Lunar/Lunar_3.png");
            mlistUnlock.add("Lunar/Lunar_4.png");
            mlistUnlock.add("Lunar/Lunar_5.png");
            mlistUnlock.add("Lunar/Lunar_6.png");
            mlistUnlock.add("more.png");
        } else if (name == 3) {
            mlistUnlock.add("pack_6_10.png");
            mlistUnlock.add("pack_6_11.png");
            mlistUnlock.add("pack_6_12.png");
            mlistUnlock.add("pack_6_13.png");
            mlistUnlock.add("pack_6_14.png");
            mlistUnlock.add("pack_6_15.png");
            mlistUnlock.add("pack_6_16.png");
            mlistUnlock.add("more.png");
        } else if (name == 22) {
            mlistUnlock.add("pack_2_0.png");
            mlistUnlock.add("pack_2_1.png");
            mlistUnlock.add("pack_2_2.png");
            mlistUnlock.add("pack_2_3.png");
            mlistUnlock.add("pack_2_4.png");
            mlistUnlock.add("pack_2_5.png");
            mlistUnlock.add("pack_2_6.png");
            mlistUnlock.add("more.png");
        } else if (name == 33) {
            mlistUnlock.add("pack_3_0.png");
            mlistUnlock.add("pack_3_1.png");
            mlistUnlock.add("pack_3_2.png");
            mlistUnlock.add("pack_3_3.png");
            mlistUnlock.add("pack_3_4.png");
            mlistUnlock.add("pack_3_5.png");
            mlistUnlock.add("pack_3_6.png");
            mlistUnlock.add("more.png");
        } else if (name == 44) {
            mlistUnlock.add("pack_4_0.png");
            mlistUnlock.add("pack_4_1.png");
            mlistUnlock.add("pack_4_2.png");
            mlistUnlock.add("pack_4_3.png");
            mlistUnlock.add("pack_4_4.png");
            mlistUnlock.add("pack_4_5.png");
            mlistUnlock.add("pack_4_6.png");
            mlistUnlock.add("more.png");
        } else if (name == 55) {
            mlistUnlock.add("pack_5_1.png");
            mlistUnlock.add("pack_5_2.png");
            mlistUnlock.add("pack_5_3.png");
            mlistUnlock.add("pack_5_4.png");
            mlistUnlock.add("pack_5_5.png");
            mlistUnlock.add("pack_5_6.png");
            mlistUnlock.add("pack_5_7.png");
            mlistUnlock.add("more.png");
        }
//        else if(name == 66){
//            mlistUnlock.add("pack_6_10.png");
//            mlistUnlock.add("pack_6_11.png");
//            mlistUnlock.add("pack_6_12.png");
//            mlistUnlock.add("pack_6_13.png");
//            mlistUnlock.add("pack_6_14.png");
//            mlistUnlock.add("pack_6_15.png");
//            mlistUnlock.add("pack_6_16.png");
//            mlistUnlock.add("more.png");
//        }
        else if (name == 77) {
            mlistUnlock.add("Christmas/Christmas_0.png");
            mlistUnlock.add("Christmas/Christmas_1.png");
            mlistUnlock.add("Christmas/Christmas_2.png");
            mlistUnlock.add("Christmas/Christmas_3.png");
            mlistUnlock.add("Christmas/Christmas_4.png");
            mlistUnlock.add("Christmas/Christmas_5.png");
            mlistUnlock.add("Christmas/Christmas_6.png");
            mlistUnlock.add("more.png");
        }

    }
}