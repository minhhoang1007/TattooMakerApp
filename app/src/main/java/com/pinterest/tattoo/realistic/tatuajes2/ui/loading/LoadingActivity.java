package com.pinterest.tattoo.realistic.tatuajes2.ui.loading;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.pinterest.tattoo.realistic.tatuajes2.R;
import com.pinterest.tattoo.realistic.tatuajes2.common.Common;
import com.pinterest.tattoo.realistic.tatuajes2.data.AppDataHelper;
import com.pinterest.tattoo.realistic.tatuajes2.data.IApiHelper;
import com.pinterest.tattoo.realistic.tatuajes2.ui.main.MainActivity;
import com.pinterest.tattoo.realistic.tatuajes2.BuildConfig;
import com.oneadx.android.oneads.AdInterstitial;
import com.oneadx.android.oneads.AdListener;
import com.oneadx.android.oneads.OneAds;

public class LoadingActivity extends AppCompatActivity {
    private String TAG = "LoadingActivity";
    AppDataHelper appDataHelper;
    private boolean isLoadDataAdsSuccess = false;
    private boolean isInitAdsSuccess = false;
    private AdInterstitial adInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBar();
        setContentView(R.layout.activity_loading);
        appDataHelper = new AppDataHelper(this);
        loadAds();
        initAds();
    }

    private void statusBar() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black90));
    }

//    private void initView() {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//    }

    private void initAds() {
        OneAds.init(this, new AdNativeCustom(), new OneAds.OneAdsListener() {
            @Override
            public void onSuccess() {
                adInterstitial =
                        new AdInterstitial(LoadingActivity.this);
                adInterstitial.showSplashAd(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        isInitAdsSuccess = true;
                        intentMain();
                    }
                });
            }

            @Override
            public void onError() {
                isInitAdsSuccess = true;
                intentMain();
            }
        });
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
    private void intentMain() {
        if (isInitAdsSuccess && isLoadDataAdsSuccess) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}