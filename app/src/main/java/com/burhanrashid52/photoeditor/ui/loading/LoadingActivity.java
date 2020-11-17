package com.burhanrashid52.photoeditor.ui.loading;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;
import com.burhanrashid52.photoeditor.R;
import com.burhanrashid52.photoeditor.common.Common;
import com.burhanrashid52.photoeditor.data.AppDataHelper;
import com.burhanrashid52.photoeditor.data.IApiHelper;
import com.burhanrashid52.photoeditor.ui.main.MainActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.burhanrashid52.photoeditor.BuildConfig;
import android.os.Handler;

public class LoadingActivity extends AppCompatActivity {
    private String TAG = "LoadingActivity";
    AppDataHelper appDataHelper;
    private boolean isLoadDataAdsSuccess = false;
    ImageView btnClose;
    FrameLayout frameAds;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        appDataHelper = new AppDataHelper(this);
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
        initView();
        loadAds();
    }
    private void initView(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        btnClose = findViewById(R.id.btnCloseLoading);
        frameAds = findViewById(R.id.frameAdsLoading);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void initAds(){
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(Common.banner_id_admob);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                progressDialog.dismiss();
                btnClose.setVisibility(View.VISIBLE);
            }
        });
        frameAds.addView(adView);
    }
    private void loadAds() {
        appDataHelper.getAds(new IApiHelper.CallBackData<String>() {
            @Override
            public void onSuccess(String value) {
                Log.e("TAG", "onSuccess: " + value);
                if (!value.isEmpty()) {
                    Log.e("TAG", "onSuccess: " + value);
                    try {
                        JSONObject jsonObject = new JSONObject(value);
                        Common.is_show_banner = jsonObject.getBoolean("is_show_banner");
                        Common.is_show_inter = jsonObject.getBoolean("is_show_inter");
                        Common.is_show_native = jsonObject.getBoolean("is_show_native");
                        Common.is_load_failed = jsonObject.getBoolean("is_load_failed");
                        Common.is_show_admob = jsonObject.getBoolean("isAdmob");
                        if (BuildConfig.DEBUG) {
                            Common.is_show_admob = true;
                            Common.is_random = 7;
                        }
                        if (!BuildConfig.DEBUG) {
                            Common.banner_id_admob = jsonObject.getString("ad_banner_id");
                            Common.inter_id_admob = jsonObject.getString("ad_inter_id");
                            Common.native_id_admob = jsonObject.getString("ad_native_id");
                            Common.rewar_id_admob = jsonObject.getString("ad_reward_id");
                            Common.is_random = jsonObject.getInt("is_random");
                        }
                        isLoadDataAdsSuccess = true;
                        showClose();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFail(String mess) {

            }

        });
    }
    private void showClose(){
        if(isLoadDataAdsSuccess){
            initAds();
        }
    }
}