package com.pinterest.tattoo.realistic.tatuajes2.ui.detail;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.pinterest.tattoo.realistic.tatuajes2.R;
import com.pinterest.tattoo.realistic.tatuajes2.common.Common;
import com.pinterest.tattoo.realistic.tatuajes2.ui.main.MainActivity;
import com.oneadx.android.oneads.AdSize;
import com.oneadx.android.oneads.adbanner.AdBanner;

public class ComppleteActivity extends AppCompatActivity {
    private String TAG = "CompleteActivity";
    ImageView btnBack, btnHome, btnShare, btnMore, imgComplete;
    private TextView txtComplete;
    private FrameLayout frameAds;
    private String txtsaved;
    private Uri myUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBar();
        setContentView(R.layout.activity_compplete);
        initView();
        initAds();
    }
    private void statusBar(){
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black90));
    }
    private void initView(){
        Intent intent = getIntent();
        txtsaved = intent.getStringExtra("saved");
        myUri = Uri.parse(intent.getStringExtra("imageUri"));
        btnBack = findViewById(R.id.btnBackComplete);
        btnBack.setColorFilter(Color.WHITE);
        btnHome = findViewById(R.id.btnHome);
        btnShare = findViewById(R.id.btnShareComplete);
        btnMore = findViewById(R.id.btnMoreComplete);
        imgComplete = findViewById(R.id.imgComplete);
        txtComplete = findViewById(R.id.txtSave);
        frameAds = findViewById(R.id.frameAdsComplete);
        txtComplete.setText("Saved: " + Common.folderPath + txtsaved);
        imgComplete.setImageURI(myUri);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHome);
                finish();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareComplete(myUri);
            }
        });
       btnMore.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                moreComplete();
           }
       });
    }
    private void initAds(){
//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
//        adView.setAdUnitId(Common.banner_id_admob);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//        frameAds.addView(adView);
        AdBanner adBanner = new AdBanner(this, frameAds, new AdBanner.AdBannerListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onError() {

            }
        });
        adBanner.setAdSize(AdSize.BIG_BANNER);
        adBanner.load();
    }
    private void shareComplete(Uri uri){
        //Uri imageUri = Uri.fromFile(myFile);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Image"));
    }
    private void moreComplete(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "oneadx@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Report message");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Say something");
        startActivity(Intent.createChooser(emailIntent, "Select an app for sending Email"));
//        Intent i = new Intent(Intent.ACTION_SEND);
//        i.setType("message/rfc822");
//        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
//        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
//        i.putExtra(Intent.EXTRA_TEXT   , "body of email");
//        try {
//            startActivity(Intent.createChooser(i, "Send mail..."));
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//        }
    }

}