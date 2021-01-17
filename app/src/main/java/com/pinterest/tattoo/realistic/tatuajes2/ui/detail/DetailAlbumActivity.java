package com.pinterest.tattoo.realistic.tatuajes2.ui.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.pinterest.tattoo.realistic.tatuajes2.R;
import java.io.File;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailAlbumActivity extends AppCompatActivity {
    private String TAG = "DetailAlbumActivity";
    public static final int DELETE = 345;
    @BindView(R.id.btnBackAlbumDetail)
    ImageView btnBackAlbumDetail;
    @BindView(R.id.imgAlbumDetail)
    ImageView imgAlbumDetail;
    @BindView(R.id.btnDeleteAlbum)
    ImageView btnDeleteAlbum;
    @BindView(R.id.btnShareAlbum)
    ImageView btnShareAlbum;
    File myFile;
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
        myFile = (File) intent.getSerializableExtra("album");
        Uri urimg =  Uri.fromFile(myFile);
        Log.e(TAG, "initView: " + urimg);
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
                showAlertDialog();
                break;
            case R.id.btnShareAlbum:
                shareImage();
                break;
        }
    }
    private void shareImage(){
        //Uri imageUri = Uri.parse("android.resource://"+ BuildConfig.APPLICATION_ID+ + myFile.getAbsolutePath());
        Uri imageUri = Uri.fromFile(myFile);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(intent, "Share Image"));
    }
    private void showAlertDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Do you want to delete?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               deleteFile(myFile);
                setResult(DELETE);
                onBackPressed();
                Toast.makeText(DetailAlbumActivity.this, "Delete Complete", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public boolean deleteFile(File fullFileName) {
        //File f = new File(fullFileName);
        boolean isdeleted = fullFileName.delete();
        return isdeleted;
    }
}