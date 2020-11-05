package com.burhanrashid52.photoeditor.ui.mydesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.burhanrashid52.photoeditor.R;
import com.burhanrashid52.photoeditor.common.Common;
import com.burhanrashid52.photoeditor.ui.detail.DetailAlbumActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        presenter = new AlbumPresenter<IAlbumView>(this);
        presenter.onAttact(this);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        updateData();
    }

    private void initView() {
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
}