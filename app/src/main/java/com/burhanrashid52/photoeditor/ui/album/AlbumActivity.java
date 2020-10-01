package com.burhanrashid52.photoeditor.ui.album;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.burhanrashid52.photoeditor.EditImageActivity;
import com.burhanrashid52.photoeditor.R;
import com.burhanrashid52.photoeditor.common.Common;
import com.burhanrashid52.photoeditor.ui.idea.DetailIdeaActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlbumActivity extends AppCompatActivity {
    private String TAG = "AlbumActivity";
    private final int CODE_REFRESH = 123;
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
        ButterKnife.bind(this);
        initData();
        initView();
    }
    private void initView(){
        if (Common.listAlbum.size() > 0) {
            revAlbum.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
            adapater = new AlbumAdapater(Common.listAlbum);
            revAlbum.setLayoutManager(linearLayoutManager);
            revAlbum.setAdapter(adapater);
            adapater.setOnClickLisener(new AlbumAdapater.OnClickLisener() {
                @Override
                public void onClickItem(int position) {
                    Intent intent = new Intent(getApplicationContext(), DetailAlbumActivity.class);
                    intent.putExtra("album", Common.listAlbum.get(position));
                    startActivity(intent);
                }
            });
            revAlbum.setVisibility(View.VISIBLE);
            noAlbum.setVisibility(View.GONE);

        } else {
            revAlbum.setVisibility(View.GONE);
            noAlbum.setVisibility(View.VISIBLE);
        }

    }
//    private void updateData(){
//        Log.e(TAG, "updateData: "  );
//        imgAlbumList = new ArrayList<>();
//        File folder = new File(Common.folderPath);
//        if (folder != null && folder.exists()) {
//            File[] files = folder.listFiles();
//            if (files != null && files.length > 0) {
//                imgAlbumList.addAll(Arrays.asList(files));
//            } else {
//                imgAlbumList = new ArrayList<>();
//            }
//        }
//        Log.e(TAG, "updateData: " + imgAlbumList.size()  );
//
//        if (imgAlbumList.size() > 0) {
//            revAlbum.setVisibility(View.VISIBLE);
//            noAlbum.setVisibility(View.GONE);
//        } else {
//            revAlbum.setVisibility(View.GONE);
//            noAlbum.setVisibility(View.VISIBLE);
//        }
//        //albumAdapter.setVideoList(Constans.listAlbum);
//    }
    private void initData(){
        Common.listAlbum = new ArrayList<>();
        File folder = new File(Common.folderPath);
        if (folder != null && folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null && files.length > 0) {
                Log.e(TAG, "initData: 1" );
                Common.listAlbum.addAll(Arrays.asList(files));
            } else {
                Log.e(TAG, "initData: 2" );
                Common.listAlbum = new ArrayList<>();
            }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_REFRESH){
            Common.listAlbum = new ArrayList<>();
            File folder = new File(Common.folderPath);
            if (folder != null && folder.exists()) {
                File[] files = folder.listFiles();
                if (files != null && files.length > 0) {
                    Common.listAlbum.addAll(Arrays.asList(files));
                } else {
                    Common.listAlbum = new ArrayList<>();
                }
            }
            initView();
        }
    }
    @OnClick(R.id.btnBackAlbum)
    public void onViewClicked() {
        onBackPressed();
    }
}