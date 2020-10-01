package com.burhanrashid52.photoeditor.ui.album;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.burhanrashid52.photoeditor.R;
import java.io.File;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailAlbumActivity extends AppCompatActivity {
    private String TAG = "DetailAlbumActivity";
    @BindView(R.id.btnBackAlbumDetail)
    ImageView btnBackAlbumDetail;
    @BindView(R.id.imgAlbumDetail)
    ImageView imgAlbumDetail;
    @BindView(R.id.btnDeleteAlbum)
    ImageView btnDeleteAlbum;
    @BindView(R.id.btnShareAlbum)
    ImageView btnShareAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_album);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        Intent intent = getIntent();
        File myFile = (File) intent.getSerializableExtra("album");
        Uri urimg =  Uri.fromFile(myFile);
        Glide.with(this)
                .load(urimg) // or URI/path
                .into(imgAlbumDetail);
    }
    @OnClick({R.id.btnBackAlbumDetail, R.id.btnDeleteAlbum, R.id.btnShareAlbum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBackAlbumDetail:
                onBackPressed();
                break;
            case R.id.btnDeleteAlbum:
                break;
            case R.id.btnShareAlbum:
                break;
        }
    }
}