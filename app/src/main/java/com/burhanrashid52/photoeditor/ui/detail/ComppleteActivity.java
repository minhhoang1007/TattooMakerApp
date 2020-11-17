package com.burhanrashid52.photoeditor.ui.detail;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.burhanrashid52.photoeditor.R;
import com.burhanrashid52.photoeditor.common.Common;
import com.burhanrashid52.photoeditor.ui.main.MainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class ComppleteActivity extends AppCompatActivity {
    private String TAG = "CompleteActivity";
    ImageView btnBack, btnHome, btnShare, btnMore, imgComplete;
    TextView txtComplete;
    FrameLayout frameAds;
    String txtsaved;
    Uri myUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compplete);
        initView();
        initAds();
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
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(Common.banner_id_admob);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        frameAds.addView(adView);
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
                "mailto", "abcdeghk@gmail.com", null));
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