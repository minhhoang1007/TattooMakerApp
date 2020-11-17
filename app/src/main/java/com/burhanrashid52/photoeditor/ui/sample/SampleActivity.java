package com.burhanrashid52.photoeditor.ui.sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.burhanrashid52.photoeditor.EditImageActivity;
import com.burhanrashid52.photoeditor.R;
import com.burhanrashid52.photoeditor.common.Common;
import java.util.ArrayList;
import com.google.android.gms.ads.InterstitialAd;
import android.app.ProgressDialog;
import java.util.Random;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SampleActivity extends AppCompatActivity {
    private static final String TAG = "SampleActivity";
    @BindView(R.id.btnBackDes)
    ImageView btnBackDes;
    @BindView(R.id.revChooseDesgin)
    RecyclerView revChooseDesgin;
    ArrayList<ModelSample> imgChooseDes;
    SampleAdapter adapter;
    String fileTattoo;
    ProgressDialog progressDialog;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desgin_choose);
        ButterKnife.bind(this);
        initData();
        initView();
        initAds();
    }
    private void initAds(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(Common.inter_id_admob);
    }
    private void initData(){
        imgChooseDes = new ArrayList<>();
        imgChooseDes.add(new ModelSample("", "1.jpg"));
        imgChooseDes.add(new ModelSample("", "2.jpg"));
        imgChooseDes.add(new ModelSample("", "3.jpg"));
        imgChooseDes.add(new ModelSample("", "4.jpg"));
        imgChooseDes.add(new ModelSample("", "5.jpg"));
        imgChooseDes.add(new ModelSample("", "6.jpg"));
        imgChooseDes.add(new ModelSample("", "7.jpg"));
        imgChooseDes.add(new ModelSample("", "8.jpg"));
        imgChooseDes.add(new ModelSample("", "9.jpg"));
        imgChooseDes.add(new ModelSample("", "10.jpg"));
        imgChooseDes.add(new ModelSample("", "11.jpg"));
        imgChooseDes.add(new ModelSample("", "12.jpg"));
        imgChooseDes.add(new ModelSample("", "13.jpg"));
        imgChooseDes.add(new ModelSample("", "14.jpg"));
        imgChooseDes.add(new ModelSample("", "15.jpg"));
        imgChooseDes.add(new ModelSample("", "16.jpg"));
        imgChooseDes.add(new ModelSample("", "17.jpg"));
        imgChooseDes.add(new ModelSample("", "18.jpg"));
        imgChooseDes.add(new ModelSample("", "19.jpg"));
        imgChooseDes.add(new ModelSample("", "20.jpg"));
        imgChooseDes.add(new ModelSample("", "21.jpg"));
        imgChooseDes.add(new ModelSample("", "22.jpg"));
        imgChooseDes.add(new ModelSample("", "23.jpg"));
        imgChooseDes.add(new ModelSample("", "24.jpg"));
        imgChooseDes.add(new ModelSample("", "25.jpg"));
        imgChooseDes.add(new ModelSample("", "26.jpg"));
        imgChooseDes.add(new ModelSample("", "27.jpg"));
        imgChooseDes.add(new ModelSample("", "28.jpg"));
        imgChooseDes.add(new ModelSample("", "29.jpg"));
        imgChooseDes.add(new ModelSample("", "30.jpg"));
        imgChooseDes.add(new ModelSample("", "31.jpg"));
        imgChooseDes.add(new ModelSample("", "a1.jpg"));


    }
    private void initView(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        Intent intent = getIntent();
        fileTattoo = intent.getStringExtra("fileTattoo");
        revChooseDesgin.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        adapter = new SampleAdapter(imgChooseDes);
        revChooseDesgin.setLayoutManager(linearLayoutManager);
        revChooseDesgin.setAdapter(adapter);
        adapter.setOnClickLisener(new SampleAdapter.OnClickLisener() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), EditImageActivity.class);
                intent.putExtra("desgin", "file:///android_asset/" + imgChooseDes.get(position).getImgDes());
                intent.putExtra("fileTattoo",  fileTattoo);
                startActivity(intent);
                finish();
            }
        });
    }
    @OnClick(R.id.btnBackDes)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        progressDialog.show();
        Random rd = new Random();
        int rands = rd.nextInt(10);
        if(rands < Common.is_random) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    progressDialog.dismiss();
                    mInterstitialAd.show();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    progressDialog.dismiss();
                    SampleActivity.super.onBackPressed();
                }

                @Override
                public void onAdClosed() {
                    SampleActivity.super.onBackPressed();
                }
            });
        }else{
            progressDialog.dismiss();
            SampleActivity.super.onBackPressed();
        }
    }
}