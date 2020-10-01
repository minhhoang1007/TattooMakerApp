package com.burhanrashid52.photoeditor.ui.idea;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.burhanrashid52.photoeditor.BuildConfig;
import com.burhanrashid52.photoeditor.R;
import com.burhanrashid52.photoeditor.common.Common;

import java.io.File;
import java.io.FileOutputStream;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_idea);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        Intent intent = getIntent();
        intValue = intent.getIntExtra("stt", 0);
        imgDetailIdea.setImageResource(intValue);
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