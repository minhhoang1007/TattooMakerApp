package com.pinterest.tattoo.realistic.tatuajes.ui.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.pinterest.tattoo.realistic.tatuajes.BuildConfig;
import com.pinterest.tattoo.realistic.tatuajes.R;
import com.pinterest.tattoo.realistic.tatuajes.common.Common;
import java.io.File;
import java.io.FileOutputStream;
import com.bumptech.glide.Glide;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailIdeaActivity extends AppCompatActivity {
    private static final String TAG = "DetailIdeaActivity";
    @BindView(R.id.btnBackItemIdea)
    ImageView btnBackItemIdea;
    @BindView(R.id.imgDetailIdea)
    ImageView imgDetailIdea;
    @BindView(R.id.btnSaveIdea)
    ImageView btnSaveIdea;
    @BindView(R.id.btnShareIdea)
    ImageView btnShareIdea;
    int intValue;
    String fileStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_idea);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        btnSaveIdea.setColorFilter(Color.WHITE);
        btnShareIdea.setColorFilter(Color.WHITE);
        Intent intent = getIntent();
        //intValue = intent.getIntExtra("stt", 0);
        fileStr = intent.getStringExtra("fileStr");
        Glide.with(this)
                .asBitmap()
                .load("file:///android_asset/" + fileStr) // or URI/path
                .into(imgDetailIdea);
        //imgDetailIdea.setImageResource(intValue);
    }
    private void saveImage(){
        //String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()+ "/Camera/Your_Directory_Name";
        File myDir = new File(Common.folderPath);
        myDir.mkdirs();
        String fname = "Image-" + intValue + ".png";
        File file = new File(myDir, fname);
        System.out.println(file.getAbsolutePath());
        Bitmap finalBitmap = BitmapFactory.decodeResource(getResources(), intValue);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(this, "Save Successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaScannerConnection.scanFile(this, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
    }
    private void shareImage(){
        Uri imageUri = Uri.parse("android.resource://"+ BuildConfig.APPLICATION_ID+ + intValue);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(intent, "Share Image"));
    }
    @OnClick({R.id.btnBackItemIdea, R.id.btnSaveIdea, R.id.btnShareIdea})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBackItemIdea:
                onBackPressed();
                break;
            case R.id.btnSaveIdea:
                saveImage();
                break;
            case R.id.btnShareIdea:
                shareImage();
                break;
        }
    }
}