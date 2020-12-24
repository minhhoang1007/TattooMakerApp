package com.pinterest.tattoo.realistic.tatuajes.ui.mydesign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pinterest.tattoo.realistic.tatuajes.R;
import com.pinterest.tattoo.realistic.tatuajes.common.Common;
import com.pinterest.tattoo.realistic.tatuajes.ui.detail.DetailAlbumActivity;
import com.oneadx.android.oneads.AdInterstitial;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlbumActivity extends AppCompatActivity implements IAlbumView {
    private String TAG = "AlbumActivity";
    private final int CODE_REFRESH = 123;
    public static final int DELETE_CODE = 123;
    IAlbumPresenter<IAlbumView> presenter;
    @BindView(R.id.btnBackAlbum)
    ImageView btnBackAlbum;
    @BindView(R.id.revAlbum)
    RecyclerView revAlbum;
    @BindView(R.id.noAlbum)
    ImageView noAlbum;
    AlbumAdapater adapater;
    ProgressDialog progressDialog;
    // private InterstitialAd mInterstitialAd;
    private AdInterstitial adInterstitial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBar();
        setContentView(R.layout.activity_album);
        presenter = new AlbumPresenter<IAlbumView>(this);
        presenter.onAttact(this);
        ButterKnife.bind(this);
        initData();
        initView();
        initAds();
    }
    private void statusBar(){
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black90));
    }
    private void initAds(){
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(Common.inter_id_admob);
        adInterstitial = new AdInterstitial(this);
        adInterstitial.load();
    }
    @Override
    public void onResume() {
        super.onResume();
        initView();
        updateData();
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        revAlbum.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        adapater = new AlbumAdapater(Common.listAlbum);
        revAlbum.setLayoutManager(linearLayoutManager);
        adapater.notifyDataSetChanged();
        revAlbum.setAdapter(adapater);
        adapater.setOnClickLisener(new AlbumAdapater.OnClickLisener() {
            @Override
            public void onClickItem(int position) {
                presenter.clickItem(Common.listAlbum.get(position));
//                    Intent intent = new Intent(getApplicationContext(), DetailAlbumActivity.class);
//                    intent.putExtra("album", Common.listAlbum.get(position));
//                    startActivity(intent);
            }
        });
        if (Common.listAlbum.size() > 0) {
            revAlbum.setVisibility(View.VISIBLE);
            noAlbum.setVisibility(View.GONE);

        } else {
            revAlbum.setVisibility(View.GONE);
            noAlbum.setVisibility(View.VISIBLE);
        }

    }

    private void updateData() {
        Log.e(TAG, "updateData: ");
        Common.listAlbum = new ArrayList<>();
        File folder = new File(Common.folderPath);
        if (folder != null && folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null && files.length > 0) {
                Log.e(TAG, "initData: 1");
                Common.listAlbum.addAll(Arrays.asList(files));
            } else {
                Log.e(TAG, "initData: 2");
                Common.listAlbum = new ArrayList<>();
            }
        }
        if (Common.listAlbum.size() > 0) {
            revAlbum.setVisibility(View.VISIBLE);
            noAlbum.setVisibility(View.GONE);
        } else {
            revAlbum.setVisibility(View.GONE);
            noAlbum.setVisibility(View.VISIBLE);
        }
        adapater.setVideoList(Common.listAlbum);
    }

    private void initData() {
        Common.listAlbum = new ArrayList<>();
        File folder = new File(Common.folderPath);
        if (folder != null && folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null && files.length > 0) {
                Log.e(TAG, "initData: 1");
                Common.listAlbum.addAll(Arrays.asList(files));
            } else {
                Log.e(TAG, "initData: 2");
                Common.listAlbum = new ArrayList<>();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REFRESH || (requestCode == DELETE_CODE && resultCode == DetailAlbumActivity.DELETE)){
            updateData();
        }
    }

    @OnClick(R.id.btnBackAlbum)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }
    @Override
    public void onBackPressed() {
//        progressDialog.show();
//        Random rd = new Random();
//        int rands = rd.nextInt(10);
//        if(rands < Common.is_random) {
//            adInterstitial.show(new AdListener() {
//                @Override
//                public void onAdClosed() {
//                    progressDialog.dismiss();
//                    AlbumActivity.super.onBackPressed();
//                }
//            });
//        }else{
//            progressDialog.dismiss();
            AlbumActivity.super.onBackPressed();
       // }
    }
}