package com.pinterest.tattoo.realistic.tatuajes2.ui.idea;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pinterest.tattoo.realistic.tatuajes2.R;
import com.pinterest.tattoo.realistic.tatuajes2.ui.detail.DetailIdeaActivity;

import java.util.ArrayList;

import com.oneadx.android.oneads.AdInterstitial;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdeaActivity extends AppCompatActivity {
    private static final String TAG = "IdeaActivity";
    @BindView(R.id.btnBackIdea)
    ImageView btnBackIdea;
    @BindView(R.id.revIdea)
    RecyclerView revIdea;
    ArrayList<String> imgList;
    IdeaAdapter adapter;
    ProgressDialog progressDialog;
    // private InterstitialAd mInterstitialAd;
    private AdInterstitial adInterstitial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBar();
        setContentView(R.layout.activity_idea);
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
    private void initData(){
        imgList =  new ArrayList<>();
        imgList.add("tattoo_idea_1.png");
        imgList.add("tattoo_idea_2.png");
        imgList.add("tattoo_idea_3.png");
        imgList.add("tattoo_idea_4.png");
        imgList.add("tattoo_idea_5.png");
        imgList.add("tattoo_idea_6.png");
        imgList.add("tattoo_idea_7.png");
        imgList.add("tattoo_idea_8.png");
        imgList.add("tattoo_idea_9.png");
        imgList.add("tattoo_idea_10.png");
        imgList.add("tattoo_idea_11.png");
        imgList.add("tattoo_idea_12.png");
        imgList.add("tattoo_idea_13.png");
        imgList.add("tattoo_idea_14.png");
        imgList.add("tattoo_idea_15.png");
        imgList.add("tattoo_idea_16.png");
        imgList.add("tattoo_idea_17.png");
        imgList.add("tattoo_idea_18.png");
        imgList.add("tattoo_idea_19.png");
        imgList.add("tattoo_idea_21.png");
        imgList.add("tattoo_idea_22.png");
        imgList.add("tattoo_idea_23.png");
        imgList.add("tattoo_idea_24.png");
        imgList.add("tattoo_idea_25.png");
        imgList.add("tattoo_idea_26.png");
        imgList.add("tattoo_idea_27.png");
        imgList.add("tattoo_idea_28.png");
        imgList.add("tattoo_idea_29.png");
        imgList.add("tattoo_idea_30.png");
        imgList.add("tattoo_idea_31.png");
        imgList.add("tattoo_idea_32.png");
        imgList.add("tattoo_idea_33.png");
        imgList.add("tattoo_idea_34.png");
        imgList.add("tattoo_ideas_35.png");
        imgList.add("tattoo_ideas_36.png");
        imgList.add("tattoo_ideas_37.png");
        imgList.add("tattoo_ideas_38.png");
        imgList.add("tattoo_ideas_39.png");
        imgList.add("tattoo_ideas_40.png");
        imgList.add("tattoo_ideas_41.png");
        imgList.add("tattoo_ideas_42.png");
        imgList.add("tattoo_ideas_43.png");
        imgList.add("tattoo_ideas_44.png");
        imgList.add("tattoo_ideas_45.png");
        imgList.add("tattoo_ideas_46.png");
        imgList.add("tattoo_ideas_47.png");
        imgList.add("tattoo_ideas_48.png");
        imgList.add("tattoo_ideas_49.png");
        imgList.add("tattoo_ideas_50.png");
        imgList.add("tattoo_ideas_51.png");
        imgList.add("tattoo_ideas_52.png");
        imgList.add("tattoo_ideas_53.png");
        imgList.add("tattoo_ideas_54.png");
        imgList.add("tattoo_ideas_55.png");
        imgList.add("tattoo_ideas_56.png");
        imgList.add("tattoo_ideas_57.png");
        imgList.add("tattoo_ideas_58.png");
        imgList.add("tattoo_ideas_59.png");
        imgList.add("tattoo_ideas_60.png");
        imgList.add("tattoo_ideas_61.png");
        imgList.add("tattoo_ideas_62.png");
        imgList.add("tattoo_ideas_63.png");
        imgList.add("tattoo_ideas_64.png");
        imgList.add("tattoo_ideas_65.png");
        imgList.add("tattoo_ideas_66.png");
        imgList.add("tattoo_ideas_67.png");
        imgList.add("tattoo_ideas_68.png");
        imgList.add("tattoo_ideas_69.png");
        imgList.add("tattoo_ideas_70.png");
    }
    private void initView(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        revIdea.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        adapter = new IdeaAdapter(imgList);
        revIdea.setLayoutManager(linearLayoutManager);
        revIdea.setAdapter(adapter);
        adapter.setOnClickLisener(new IdeaAdapter.OnClickLisener() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), DetailIdeaActivity.class);
                intent.putExtra("fileStr", imgList.get(position));
                startActivity(intent);
            }
        });
    }
    @OnClick(R.id.btnBackIdea)
    public void onViewClicked() {
        onBackPressed();
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
//                    IdeaActivity.super.onBackPressed();
//                }
//            });
//        }else{
//            progressDialog.dismiss();
            IdeaActivity.super.onBackPressed();
        //}
    }
}