package com.burhanrashid52.photoeditor.ui.unlock;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.burhanrashid52.photoeditor.R;
import com.burhanrashid52.photoeditor.common.Common;
import com.burhanrashid52.photoeditor.utils.SharedPrefsUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

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
    UnlockTattooAdapter unlockTattooAdapter;
    ProgressDialog progressDialog;
    //private InterstitialAd mInterstitialAd;
    private RewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent intent = getIntent();
        name = intent.getIntExtra("name", 0);
        initData();
        initAds();
        if (name == 1) {
            txtNameUnlock.setText("New Year Pack");
        } else if (name == 2) {
            txtNameUnlock.setText("Lunar New Year Pack");
        } else if (name == 3) {
            txtNameUnlock.setText("New Arrivals");
        } else if (name == 22) {
            txtNameUnlock.setText("Unlock New Tattoo Pack");
        } else if (name == 33) {
            txtNameUnlock.setText("Unlock New Mini Pack");
        } else if (name == 44) {
            txtNameUnlock.setText("Unlock New Tattoo Pack");
        } else if (name == 55) {
            txtNameUnlock.setText("Mid - Autumn Festival");
        } else if (name == 66) {
            txtNameUnlock.setText("Unlock New Piercing Pack");
        } else if (name == 77) {
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
                if (rewardedAd.isLoaded()) {
                    Activity activityContext = UnlockTattooActivity.this;
                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        }

                        @Override
                        public void onRewardedAdOpened() {
                            super.onRewardedAdOpened();
                        }

                        @Override
                        public void onRewardedAdClosed() {
                            super.onRewardedAdClosed();
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
                        public void onRewardedAdFailedToShow(int i) {
                            super.onRewardedAdFailedToShow(i);
                        }
                    };
                    rewardedAd.show(activityContext, adCallback);
                } else {
                    Log.e(TAG, "The rewarded ad wasn't loaded yet.");
                }
//                mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                mInterstitialAd.setAdListener(new AdListener() {
//                    @Override
//                    public void onAdLoaded() {
//                        progressDialog.dismiss();
//                        mInterstitialAd.show();
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(int errorCode) {
//                        progressDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onAdClosed() {
//
//                    }
//                });
            }
        });
    }

    private void initAds() {
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(Common.inter_id_admob);
        rewardedAd = new RewardedAd(this, Common.rewar_id_admob);
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                Log.e(TAG, "onRewardedAdLoaded: " );
                progressDialog.dismiss();

            }

            public void onRewardedAdFailedToLoad(int var1) {
                Log.e(TAG, "onRewardedAdFailedToLoad: " );
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
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